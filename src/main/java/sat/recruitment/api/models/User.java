package sat.recruitment.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @JsonProperty("name")
    @NotBlank(message = "The name is required")
    public String name;

    @JsonProperty("email")
    @Email(message = "The email format is invalid")
    @NotBlank(message = "The email is required")
    public String email;

    @JsonProperty("address")
    @NotBlank(message = "The phone is required")
    public String address;

    @JsonProperty("phone")
    @NotBlank(message = "The address is required")
    public String phone;

    @JsonProperty("user-type")
    @NotBlank(message = "The userType is required")
    public String userType;

    @JsonProperty("money")
    @NotNull(message = "The money is required")
    public Double money;

    @Override
    public String toString() {
        return name +
                "," + email +
                "," + phone +
                "," + address +
                "," + userType +
                "," + money;
    }
}
