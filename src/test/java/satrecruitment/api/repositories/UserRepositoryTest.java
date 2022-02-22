package satrecruitment.api.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import sat.recruitment.api.models.User;
import sat.recruitment.api.repositories.UserRepository;
import sat.recruitment.api.repositories.impl.UserRepositoryImpl;
import sat.recruitment.api.services.UserService;
import sat.recruitment.api.services.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@TestPropertySource(properties = {"user.test.file.path=src/test/resources/users.txt"})
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Value("${user.test.file.path}")
    String userTestFilePath;

    @Autowired
    UserRepository<User> userRepository;

    @Autowired
    UserService userService;

    User user;
    List<User> usersFile;

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() { return new UserServiceImpl(); }
        @Bean
        public UserRepository<User> userRepository() { return new UserRepositoryImpl(); }
    }

    @Test
    public void readFile(){
        givenAFile();
        whenReadTheFile();
        thenCheckTheUsers();
    }

    public void givenAFile(){
        usersFile = new ArrayList<>();
    }
    public void whenReadTheFile(){
        usersFile = userRepository.readFile(userTestFilePath);
    }
    public void thenCheckTheUsers(){
        assertFalse(usersFile.isEmpty());
        assertEquals(usersFile.get(0).getName(), "Franco");
        assertEquals(usersFile.get(0).getAddress(), "Alvear y Colombres");
    }

    @Test
    public void writeFile() {
        givenAnUser();
        whenWriteOneFile();
        thenCheckTheUserWrited();
    }
    public void givenAnUser(){
        user = new User();
        user.setName("Rafael");
        user.setEmail("rafael@email.com");
        user.setAddress("Canada");
        user.setPhone("123456789");
        user.setUserType("Normal");
        user.setMoney(50.0);
    }
    public void whenWriteOneFile(){
        userRepository.writeFile(userTestFilePath, user);
    }
    public void thenCheckTheUserWrited(){
        List<User> usersFile = userRepository.readFile(userTestFilePath);
        assertFalse(usersFile.isEmpty());
        assertEquals(usersFile.get(0).getName(), "Franco");
        assertEquals(usersFile.get(0).getEmail(), "Franco.Perez@gmail.com");
        assertEquals(usersFile.get(0).getPhone(), "+534645213542");
        assertEquals(usersFile.get(0).getAddress(), "Alvear y Colombres");
        assertEquals(usersFile.get(0).getUserType(), "Premium");
        assertEquals(java.util.Optional.ofNullable(usersFile.get(0).getMoney()), java.util.Optional.of(112234.0));
    }
}