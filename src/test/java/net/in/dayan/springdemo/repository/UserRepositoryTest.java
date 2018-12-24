package net.in.dayan.springdemo.repository;

import net.in.dayan.springdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void save() {
        User user = new User();
        user.setId(1);
        user.setLogin("admin");
        user.setPassword("111111");
        userRepository.save(user);
    }

    @Test
    public void findByLogin() {
        User user = userRepository.findByLogin("admin");
        System.out.println(user.getId());
    }


}