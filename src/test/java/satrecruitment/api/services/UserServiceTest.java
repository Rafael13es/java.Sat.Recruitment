package satrecruitment.api.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import sat.recruitment.api.models.User;
import sat.recruitment.api.repositories.UserRepository;
import sat.recruitment.api.repositories.impl.UserRepositoryImpl;
import sat.recruitment.api.services.UserService;
import sat.recruitment.api.services.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"user.file.path=src/test/resources/users.txt"})
public class UserServiceTest {

    @Autowired
    UserRepository<User> userRepository;

    @Autowired
    UserService userService;

    User user;

    List<User> usersFile;

    String data;

    Boolean isDuplicated;

    Double newMoney;

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() { return new UserServiceImpl(); }
        @Bean
        public UserRepository<User> userRepository() { return new UserRepositoryImpl(); }
    }

    @Test
    public void read_file(){
        givenAFile();
        whenReadTheFile();
        thenCheckTheUsers();
    }
    private void givenAFile() {
        usersFile = new ArrayList<>();
    }
    private void whenReadTheFile() {
        usersFile = userService.readFile();
    }
    private void thenCheckTheUsers() {
        assertFalse(usersFile.isEmpty());
        assertEquals(usersFile.get(0).getName(), "Franco");
        assertEquals(usersFile.get(0).getEmail(), "Franco.Perez@gmail.com");
        assertEquals(usersFile.get(0).getPhone(), "+534645213542");
        assertEquals(usersFile.get(0).getAddress(), "Alvear y Colombres");
        assertEquals(usersFile.get(0).getUserType(), "Premium");
        assertEquals(java.util.Optional.ofNullable(usersFile.get(0).getMoney()), java.util.Optional.of(112234.0));
        assertEquals(usersFile.get(1).getName(), "Agustina");
    }

    @Test
    public void convertStringToUser() {
        givenAString();
        whenConvertStringToUser();
        thenCheckTheUser();
    }
    private void givenAString() {
        data = "Juan,Juan@marmol.com,+5491154762312,Peru 2464,Normal,1234.0";
    }

    private void whenConvertStringToUser() {
        String[] fields = data.split(",");
        user = userRepository.convertToUser(fields);
    }
    private void thenCheckTheUser() {
        assertEquals(user.getName(), "Juan");
        assertEquals(user.getEmail(), "Juan@marmol.com");
        assertEquals(user.getPhone(), "+5491154762312");
        assertEquals(user.getAddress(), "Peru 2464");
        assertEquals(user.getUserType(), "Normal");
        assertEquals(java.util.Optional.ofNullable(user.getMoney()), java.util.Optional.of(1234.0));
    }

    @Test
    public void checkUserNotDuplicated(){
        givenAnUserPremium();
        whenCheckUserNotDuplicate();
        thenCheckNotDuplicated();
    }
    private void givenAnUserPremium() {
        user = new User("Micaela", "mica@gmail.com", "25 de mayo 123", "1234", "Premium", 450.0);
    }
    private void whenCheckUserNotDuplicate() {
        usersFile = userService.readFile();
        isDuplicated = userService.isDuplicated(usersFile, user);
    }
    private void thenCheckNotDuplicated() {
        assertFalse(isDuplicated);
    }

    public void checkUserDuplicated(){
        givenAUserDuplicatedPremium();
        whenCheckUserNotDuplicate();
        thenCheckDuplicated();
    }
    private void givenAUserDuplicatedPremium() {
        user = new User("Maria1", "maria1@email.com", "1234567890", "Tocumen", "Premium", 1382.08);
    }
    private void thenCheckDuplicated() {
        assertFalse(isDuplicated);
    }

    @Test
    public void calculateMoneyForUserPremium(){
        givenAnUserPremium();
        thenCheckTheMoneyForPremiumUser();
    }

    private void thenCheckTheMoneyForPremiumUser() {
        newMoney = user.getMoney();
        assertEquals(java.util.Optional.ofNullable(newMoney), java.util.Optional.of(450.0));
    }

    @Test
    public void calculateMoneyForUserNormal(){
        givenAUserDuplicatedPremium();
        thenCheckTheMoneyForNormalUser();
    }
    private void thenCheckTheMoneyForNormalUser() {
        newMoney = user.getMoney();
        assertEquals(java.util.Optional.ofNullable(newMoney), java.util.Optional.of(1382.08));
    }

    @Test
    public void calculateMoneyForSuperUser(){
        givenASuperUser();
        thenCheckTheMoneyForSuperUser();
    }
    private void givenASuperUser() {
        user = new User("Micaela", "mica@gmail.com", "25 de mayo 123", "1234", "SuperUser", 120.0);
    }
    private void thenCheckTheMoneyForSuperUser() {
        newMoney = user.getMoney();
        assertEquals(java.util.Optional.ofNullable(newMoney), java.util.Optional.of(120.0));
    }
}