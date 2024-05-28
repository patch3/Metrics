package ru.spbstu.metrics.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ActivityDto {
    String token;
    String action;
    String target;
}
