package ru.spbstu.metrics.api.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.spbstu.metrics.api.models.activity.VisitActivity;

import java.time.ZoneOffset;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VisitActivityDTO extends VisitActivityFromScriptDTO {
    private String ipAddress;

    public VisitActivityDTO(VisitActivity visitActivity) {
        super.token = visitActivity.getToken().getToken();
        super.pageUrl = visitActivity.getRequest().getPageUrl();
        this.ipAddress = visitActivity.getRequest().getIpAddress().getHostAddress();
        super.timestamp = visitActivity.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
