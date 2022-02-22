package sat.recruitment.api.repositories.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sat.recruitment.api.models.User;
import sat.recruitment.api.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository<User> {
    @Override
    public List<User> readFile(String filename) {
        List<User> users = new ArrayList<>();
        try {
            String strLine;
            BufferedReader br = new BufferedReader(new FileReader(filename));

            br.readLine();

            while ((strLine = br.readLine()) != null) {
                User user = this.convertToUser(strLine.split(","));
                users.add(user);
            }
            br.close();
        } catch (IOException io) {
            log.error(io.getMessage(), io);
        }

        return users;
    }

    @Override
    public void writeFile(String filename, User user) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));

            bw.write("\r\n" + user.toString());

            bw.close();
        }catch (IOException io){
            log.error(io.getMessage(), io);
        }
    }

    private User convertToUser(String[] line) {
        User user = new User();
        user.setName(line[0]);
        user.setEmail(line[1]);
        user.setPhone(line[2]);
        user.setAddress(line[3]);
        user.setUserType(line[4]);
        user.setMoney(Double.valueOf(line[5]));

        return user;
    }
}
