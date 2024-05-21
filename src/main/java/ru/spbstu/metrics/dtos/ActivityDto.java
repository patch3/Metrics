package ru.spbstu.metrics.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ActivityDto {
    String type;
    String target;
}
