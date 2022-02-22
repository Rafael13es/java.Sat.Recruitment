package sat.recruitment.api.repositories;

import sat.recruitment.api.models.User;

import java.util.List;

public interface UserRepository <T> {

    List<T> readFile(String filename);
    void writeFile(String filename, T object);
    User convertToUser(String[] line);
}
