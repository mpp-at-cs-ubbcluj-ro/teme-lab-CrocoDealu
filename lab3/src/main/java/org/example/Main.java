package org.example;

import org.example.domain.User;
import org.example.repository.UserRepository;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties p = new Properties();


        try {
            p.load(new FileReader("./lab3/config.properties"));
        } catch (Exception e) {
            System.out.println("Config not found " + e);
            System.out.println((new File(".")).getAbsolutePath());
        }

        JdbcUtils ju = new JdbcUtils(p);
        String url = p.getProperty("jdbc.url");
        UserRepository userRepository = new UserRepository(p);
        User user = new User(1, "username4", "password3", "name3");
        userRepository.save(user);
        user.setId(2);
        user.setName("name11");
        userRepository.update(user);
        for (User u: userRepository.findByPassword("password3")) {
            System.out.println(u);
        }
    }
}