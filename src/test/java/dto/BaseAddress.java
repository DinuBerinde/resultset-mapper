package dto;

import com.dinuberinde.MapperLabel;
import lombok.Data;

@Data
public class BaseAddress {

    @MapperLabel(name = "NATION")
    private String nation;

    @MapperLabel(name = "CITY")
    private String city;
}
