package sat.recruitment.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sat.recruitment.api.models.User;
import sat.recruitment.api.services.UserService;

import java.util.List;

@RestController
@Slf4j
public class SatRecruitmentController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User messageBody) {

        List<User> users = userService.readFile();

        userService.isDuplicated(users, messageBody);
        messageBody.setMoney(userService.calculateAmount(messageBody.getUserType(), messageBody.getMoney()));
        userService.writeFile(messageBody);

        return ResponseEntity.ok().build();
    }
}
