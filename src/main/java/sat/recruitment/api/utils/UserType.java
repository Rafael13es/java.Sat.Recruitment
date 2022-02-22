package sat.recruitment.api.utils;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum UserType {
    SUPERUSER("SuperUser"),
    PREMIUM("Premium"),
    NORMAL("Normal");

    private final String userType;
}
