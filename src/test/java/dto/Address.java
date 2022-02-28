package dto;

import com.dinuberinde.MapperLabel;
import lombok.Data;

@Data
public class Address {
    @MapperLabel(name = "ID")
    private Long ID;

    @MapperLabel(name = "NATION")
    private String nation;

    @MapperLabel(name = "CITY")
    private String city;

    @MapperLabel(name = "STREET")
    private String street;

    @MapperLabel(name = "STREET_NUMBER")
    private long number;

    @MapperLabel(name = "USER_ID")
    private Long userID;
}
