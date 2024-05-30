package ru.spbstu.metrics.api.dtos.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.spbstu.metrics.api.models.activity.ClickActivity;

import java.time.ZoneOffset;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClickActivityDTO extends TokenDTO{
    private String tagName;
    private String tagId;
    private String classList;
    private Long timestamp;

    public ClickActivityDTO(ClickActivity clickActivity) {
        super.token = clickActivity.getToken().getToken();
        this.tagName = clickActivity.getTagName();
        this.tagId = clickActivity.getTagId();
        this.classList = clickActivity.getClassList();
        this.timestamp = clickActivity.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}
