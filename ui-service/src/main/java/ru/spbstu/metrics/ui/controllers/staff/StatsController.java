package ru.spbstu.metrics.ui.controllers.staff;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spbstu.metrics.ui.mods.ModeTable;
import ru.spbstu.metrics.ui.service.ApiClientService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Controller
@RequestMapping("/staff/stats")
public final class StatsController {
    public static final ModeTable DEFAULT_MODE_TABLE = ModeTable.ALL_CLICK;

    private final ApiClientService apiClientService;

    @Autowired
    public StatsController(ApiClientService apiClientService) {
        this.apiClientService = apiClientService;
    }

    @GetMapping("/{token}")
    public String basePage(
            @PathVariable("token") String token,
            @RequestParam(value = "modeTable", required = false) ModeTable modeTable,
            @RequestParam(value = "numPage", required = false) Integer numPage,
            Model model) {
        model.addAttribute("title", "dl-stat");
        model.addAttribute("clientToken", token);
        model.addAttribute("modeTables", ModeTable.values());
        model.addAttribute("selectedValue", modeTable);

        modeTable = Objects.requireNonNullElse(modeTable, DEFAULT_MODE_TABLE);
        numPage = Objects.requireNonNullElse(numPage, 1);

        String[] tableHeaders;
        Object[][] tableData;
        switch (modeTable) {
            case ALL_CLICK -> {
                tableHeaders = new String[]{"Название тега", "id тега", "классы тега", "Время нажатия"};
                tableData = apiClientService.getClickActivity(token, numPage).stream()
                        .map(click -> new Object[]{
                                click.getTagName(),
                                click.getTagId(),
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(click.getTimestamp()), ZoneOffset.UTC)
                        }).toArray(Object[][]::new);
            }
            case ALL_VISITITS -> {
                tableHeaders = new String[]{"Название тега", "id тега", "Время нажатия"};
                tableData = apiClientService.getViewActivity(token, numPage).stream()
                        .map(click -> new Object[]{
                                click.getPageUrl(),
                                click.getIpAddress(),
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(click.getTimestamp()), ZoneOffset.UTC)
                        }).toArray(Object[][]::new);
                val allVisits = apiClientService.getViewActivity(token, numPage);
                model.addAttribute("clicks", allVisits);
            }
            default -> {
                model.addAttribute("errorMassage", "не верный параметр");
                return "/error";
            }
        }


        model.addAttribute("tableHeaders", tableHeaders);
        model.addAttribute("tableData", tableData);
        return "/staff/table/stat";
    }
}
