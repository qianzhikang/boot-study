package com.qzk.plus;

import com.qzk.plus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootMybatisPlusApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setName("qzk");

    }

}
