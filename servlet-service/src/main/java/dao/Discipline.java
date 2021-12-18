package dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "DISCIPLINE")
public class Discipline {
    @Id
    @Column(name = "DISCIPLINE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer disciplineId;
    @Column(name = "NAME", nullable = false)
    private String name; //Поле не может быт1111111 Строка не может быть пустой
    @Column(name = "LECTURE_HOURS")
    private long lectureHours;

    public Discipline(String name, long lectureHours){
        this.name = name;
        this.lectureHours = lectureHours;
    }
}