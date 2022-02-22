package sat.recruitment.api.utils;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ErrorMessage {

    DUPLICATED_USER("User is duplicated");

    private final String message;
}
