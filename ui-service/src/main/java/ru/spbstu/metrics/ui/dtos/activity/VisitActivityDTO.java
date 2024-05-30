package ru.spbstu.metrics.ui.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class VisitActivityDTO extends TokenDTO {
    private String pageUrl;
    private String ipAddress;
    private Long timestamp;
}
