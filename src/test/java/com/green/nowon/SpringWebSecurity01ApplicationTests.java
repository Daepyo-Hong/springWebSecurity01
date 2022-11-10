package com.green.nowon;

import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import com.green.nowon.security.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootTest
class SpringWebSecurity01ApplicationTests {

    @Autowired
    MemberEntityRepository mrepository;

    @Test
    void contextLoads() {
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void 회원가입테스트() {

        String pass="9999";
        MemberEntity entity=MemberEntity.builder()
                .email("test04@test.com").pass(passwordEncoder.encode(pass)).name("테스트04")
                //.roleSet(new HashSet<>()) //매번 넣기 귀찮으니,  MemberEntity에서 미리만들자!
                .build().addRole(MemberRole.USER);

        System.out.println(entity);
        mrepository.save(entity);
    }

}
