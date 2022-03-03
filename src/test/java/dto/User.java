package dto;

import com.dinuberinde.MapperDateFormatter;
import com.dinuberinde.MapperDecimalFormatter;
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

    @MapperDateFormatter()
    @MapperLabel(name = "BIRTHDATE2")
    private String birthDateString;

    @MapperLabel(name = "MALE")
    private boolean male;

    @MapperDecimalFormatter(pattern = "#.00#")
    @MapperLabel(name = "HEIGHT")
    private String height;
}
