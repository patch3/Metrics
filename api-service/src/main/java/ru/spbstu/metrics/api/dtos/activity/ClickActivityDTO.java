package ru.spbstu.metrics.api.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClickActivityDTO extends TokenDTO{
    private String tagName;
    private String tagId;
    private String classList;
    private Long timestamp;
}