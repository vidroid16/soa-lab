package dao;

import com.sun.xml.bind.v2.model.core.ID;
import dao.enums.Difficulty;
import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import util.xml.ZonedDateTimeAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity(name = "LABWORK")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LabWork {
    @Id
    @Column(name = "LAB_ID", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Column(name = "NAME", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COORDINATES_ID", nullable = false)
    private Coordinates coordinates; //Поле не может быть null
    @Column(name="CREATION_DATE", nullable = false)
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name="MINIMAL_POINT", nullable = false)
    @Range(min = 0l, message = "Please select positive numbers Only")
    private int minimalPoint; //Значение поля должно быть больше 0
    @Column(name="MAXIMAL_POINT", nullable = false)
    @Range(min = 0, message = "Please select positive numbers Only")
    private float maximumPoint; //Значение поля должно быть больше 0
    @Column(name="AVERAGE_POINT", nullable = false)
    private Long averagePoint; //Поле не может быть null, Значение поля должно быть больше 0
    @Transient
    private Difficulty difficulty;//Поле может быть null
    @Column(name="DIFFICULTY", nullable = false)
    private String difficultyName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DISCIPLINE_ID", nullable = false)
    private Discipline discipline; //Поле не может быть null

    public LabWork(String name, Coordinates coordinates, @Range(min = 0l, message = "Please select positive numbers Only") int minimalPoint,
                   @Range(min = 0l, message = "Please select positive numbers Only") float maximumPoint, Long averagePoint, String difficultyName, Discipline discipline) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.maximumPoint = maximumPoint;
        this.averagePoint = averagePoint;
        this.difficultyName = difficultyName;
        this.discipline = discipline;
        this.creationDate = ZonedDateTime.now();
    }
    @PrePersist
    public void prePersist() {
        if (minimalPoint <= 0 || maximumPoint<=0 || averagePoint<=0) {
            throw new ValidationException("Points must be >0");
        }
    }
    @Override
    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", maximumPoint=" + maximumPoint +
                ", averagePoint=" + averagePoint +
                ", difficulty=" + difficulty +
                ", difficultyName='" + difficultyName + '\'' +
                ", discipline=" + discipline +
                '}';
    }
}
