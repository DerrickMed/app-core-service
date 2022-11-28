package ru.sbrf.sell.appcore.dto;

import lombok.Data;

@Data
public class CategoryDto {
    String name;
    boolean active = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public CategoryDto(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
