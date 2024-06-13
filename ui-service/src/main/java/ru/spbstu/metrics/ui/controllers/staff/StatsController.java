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
import java.time.ZoneId;
import java.util.Objects;

@Controller
@RequestMapping("/staff/stats")
public final class StatsController {
    public static final ModeTable DEFAULT_MODE_TABLE = ModeTable.ALL_VISITITS;

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
        modeTable = Objects.requireNonNullElse(modeTable, DEFAULT_MODE_TABLE);
        numPage = Objects.requireNonNullElse(numPage, 1);

        model.addAttribute("title", "Metrics | stats");
        model.addAttribute("clientToken", token);
        model.addAttribute("modeTables", ModeTable.values());
        model.addAttribute("selectedValue", modeTable);


        String[] tableHeaders;
        Object[][] tableData;
        switch (modeTable) {
            case ALL_CLICK -> {
                tableHeaders = new String[]{"URL", "Название тега", "id тега", "классы тега", "Время нажатия"};
                val page = apiClientService.getClickActivity(token, numPage);
                tableData = page.getContent().stream()
                        .map(click -> new Object[]{
                                click.getPageUrl(),
                                click.getElementName(),
                                click.getElementId(),
                                click.getClasses(),
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(click.getTimestamp()), ZoneId.of("Europe/Moscow"))
                        }).toArray(Object[][]::new);
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("currentPage", page.getCurrentPage());
            }
            case ALL_VISITITS -> {
                tableHeaders = new String[]{"URL", "ip адрес", "Время нажатия"};
                val page = apiClientService.getViewActivity(token, numPage);
                tableData = page.getContent().stream()
                        .map(view -> new Object[]{
                                view.getPageUrl(),
                                view.getIpAddress(),
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(view.getTimestamp()), ZoneId.of("Europe/Moscow"))
                        }).toArray(Object[][]::new);
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("currentPage", page.getCurrentPage());
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
