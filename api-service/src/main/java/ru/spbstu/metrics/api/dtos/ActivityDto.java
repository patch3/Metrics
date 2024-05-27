package ru.spbstu.metrics.api.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ActivityDto {
    String token;
    String action;
    String target;
}
