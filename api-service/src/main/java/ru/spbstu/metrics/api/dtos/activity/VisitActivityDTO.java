package ru.spbstu.metrics.api.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class VisitActivityDTO extends TokenDTO{
    private String pageUrl;
    private Long timestamp;
}
