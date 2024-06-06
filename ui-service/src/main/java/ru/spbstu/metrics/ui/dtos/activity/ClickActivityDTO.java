package ru.spbstu.metrics.ui.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClickActivityDTO extends TokenActivityDTO {
    private String elementName;
    private String elementId;
    private String classes;
    private Long timestamp;
}
