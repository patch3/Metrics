package ru.spbstu.metrics.api.dtos.activity;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitActivityFromScriptDTO extends TokenActivityDTO {
    protected String pageUrl;
    protected Long timestamp;
}
