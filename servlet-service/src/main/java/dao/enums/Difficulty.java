package dao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty {
    VERY_EASY("VERY_EASY"),
    NORMAL("NORMAL"),
    VERY_HARD("VERY_HARD"),
    INSANE("INSANE"),
    TERRIBLE("TERRIBLE");

    String name;

    public Difficulty getByName(String name) {
        return Difficulty.valueOf(name);
    }
}