package sat.recruitment.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.models.User;
import sat.recruitment.api.repositories.UserRepository;
import sat.recruitment.api.services.UserService;

import java.util.List;
import java.util.Optional;

import static sat.recruitment.api.utils.ErrorMessage.DUPLICATED_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository<User> userRepository;

    @Value("${user.file.path}")
    String userFilePath;

    @Override
    public List<User> readFile() {
        return userRepository.readFile(userFilePath);
    }

    @Override
    public void writeFile(User user) {
        userRepository.writeFile(userFilePath, user);
    }

    @Override
    public Boolean isDuplicated(List<User> users, User newUser) {
        Optional<User> user = users.stream().filter(usr -> usr.equals(newUser)).findFirst();
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DUPLICATED_USER.toString());
        }
        return false;
    }

    @Override
    public Double calculateAmount(String userType, Double money){

        double newMoney = money;

        if (userType.equals("Normal")) {
            if (money > 100) {
                Double percentage = Double.valueOf("0.12");
                // If new user is normal and has more than USD100
                var gif = money * percentage;
                newMoney = money + gif;
            }
            if (money < 100) {
                if (money > 10) {
                    var percentage = Double.valueOf("0.8");
                    var gif = money * percentage;
                    newMoney = money + gif;
                }
            }
        }
        if (userType.equals("SuperUser")) {
            if (money > 100) {
                Double percentage = Double.valueOf("0.20");
                Double gif = money * percentage;
                newMoney = money + gif;
            }
        }
        if (userType.equals("Premium")) {
            if (money > 100) {
                Double gif = money * 2;
                newMoney = money + gif;
            }
        }

        return newMoney;
    }
}
