package dto;

import dao.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DIsciplineDto {
    private Integer disciplineId;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long lectureHours;
}
