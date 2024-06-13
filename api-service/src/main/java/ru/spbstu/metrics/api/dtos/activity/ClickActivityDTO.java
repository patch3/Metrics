package ru.spbstu.metrics.api.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.spbstu.metrics.api.models.activity.ClickActivity;

import java.time.ZoneOffset;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClickActivityDTO extends VisitActivityDTO {
    private String elementName;
    private String elementId;
    private String classes;

    public ClickActivityDTO(ClickActivity clickActivity) {
        super.token = clickActivity.getToken().getToken();
        super.pageUrl = clickActivity.getRequest().getPageUrl();
        super.timestamp = clickActivity.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
        this.elementName = clickActivity.getTag().getElementName();
        this.elementId = clickActivity.getTag().getElementId();
        this.classes = clickActivity.getTag().getClasses();
    }
}
