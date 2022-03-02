package dto;

import com.dinuberinde.MapperLabel;
import lombok.Data;

@Data
public class Address extends BaseAddress {
    @MapperLabel(name = "ID")
    private Long ID;

    @MapperLabel(name = "STREET")
    private String street;

    @MapperLabel(name = "STREET_NUMBER")
    private long number;

    @MapperLabel(name = "USER_ID")
    private Long userID;

    @MapperLabel(name = "NOT_EXISTING_COLUMN", optional = true)
    private Long notExistingColumn;
}
