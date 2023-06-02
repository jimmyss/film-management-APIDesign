package com.example.jpa_demo;

import com.example.jpa_demo.entity.User;
import com.example.jpa_demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
//    @Transactional// 在测试类对于事务提交方式默认的是回滚。
//    @Rollback(false)//取消自动回滚
    public void testInsertUsers(){
        User users = new User();
        users.setUser_name("w");
        users.setPassword("w");
        this.userRepository.save(users);
    }
}