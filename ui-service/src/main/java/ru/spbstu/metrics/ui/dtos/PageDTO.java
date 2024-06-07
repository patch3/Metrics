package ru.spbstu.metrics.ui.dtos;

import lombok.*;
import org.springframework.data.domain.Page;
import ru.spbstu.metrics.ui.dtos.activity.TokenActivityDTO;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T extends TokenActivityDTO> {
    private int totalPages;
    private int currentPage;
    private List<T> content;


    public static <U, T extends TokenActivityDTO> PageDTO<T> of(Page<U> page,
                                                                Function<? super U, ? extends T> converterContent) {
        val pageDTO = new PageDTO<T>();
        pageDTO.setTotalPages(Math.max(page.getTotalPages(), 1));
        pageDTO.setCurrentPage(Math.max(page.getNumber(), 1));
        pageDTO.setContent(page.getContent().stream().map(converterContent).collect(Collectors.toList()));
        return pageDTO;
    }
}