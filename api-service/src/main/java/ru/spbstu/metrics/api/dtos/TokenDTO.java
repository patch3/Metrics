package ru.spbstu.metrics.api.dtos;

import lombok.Data;
import ru.spbstu.metrics.api.dtos.activity.TokenActivityDTO;

@Data
public class TokenDTO extends TokenActivityDTO {
    private String name;
}
