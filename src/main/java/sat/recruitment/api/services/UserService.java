package sat.recruitment.api.services;

import sat.recruitment.api.models.User;

import java.util.List;

public interface UserService {

    List<User> readFile();
    void writeFile(User user);
    Boolean isDuplicated(List<User> users, User newUser);
    Double calculateAmount(String userType, Double money);
}
