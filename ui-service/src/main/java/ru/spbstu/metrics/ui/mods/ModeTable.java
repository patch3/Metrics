package ru.spbstu.metrics.ui.mods;

import lombok.Getter;

@Getter
public enum ModeTable {
    ALL_VISITITS("Все посещения"),
    ALL_CLICK("Все клики");

    private final String displayText;

    ModeTable(String displayText) {
        this.displayText = displayText;
    }
}
