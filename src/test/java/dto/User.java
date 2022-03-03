package dto;

import com.dinuberinde.MapperLabel;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @MapperLabel(name = "ID")
    private Long id;

    @MapperLabel(name = "NAME")
    private String name;

    @MapperLabel(name = "SURNAME")
    private String surname;

    @MapperLabel(name = "BIRTHDATE")
    private Date birthDate;

    @MapperLabel(name = "BIRTHDATE2", dateToString = true)
    private String birthDateString;

    @MapperLabel(name = "MALE")
    private boolean male;
}
