package ru.spbstu.metrics.api.dtos;

import java.util.List;

public class PageDTO <T extends TokenDTO> {
    private int countPages;
    private int currentPage;
    private List<T> records;
}
