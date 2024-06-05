package ru.spbstu.metrics.ui.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbstu.metrics.ui.dtos.activity.TokenActivityDTO;


@Data
@EqualsAndHashCode(callSuper = true)
public class TokenDTO extends TokenActivityDTO {
    private String name;

    public TokenDTO(String token, String name) {
        super.token = token;
        this.name = name;
    }
}
