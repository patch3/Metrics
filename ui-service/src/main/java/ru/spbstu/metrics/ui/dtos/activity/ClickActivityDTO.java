package ru.spbstu.metrics.ui.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClickActivityDTO extends TokenActivityDTO {
    private String tagName;
    private String tagId;
    private String classList;
    private Long timestamp;
}
