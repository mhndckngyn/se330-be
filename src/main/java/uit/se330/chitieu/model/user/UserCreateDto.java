package uit.se330.chitieu.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreateDto {
    public String email;
    public String name;
    public String currencyId;
}
