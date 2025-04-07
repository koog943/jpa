package jpabook.jpashop.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
* RestController가 아닌 html을 반환하는 test케이스는 별로다
* 실패한케이스이다.
* mockmvc에서의 redirect에 응답값이 제대로 받아지지 않기떄문이다.
* .*/


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LoginControllerTest {

    @Autowired
    LoginController loginController;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void login() throws Exception {

        Member member = new Member();
        member.setName("qwe");
        member.setPassword("qwe123");

        memberRepository.save(member);

        LoginForm loginForm = new LoginForm();
        loginForm.setLoginId(member.getName());
        loginForm.setPassword(member.getPassword());

        String json = objectMapper.writeValueAsString(loginForm);

        //:redirect를하면 302에러를 반환해줌
        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("loginId", loginForm.getLoginId())
                        .param("password", loginForm.getPassword()))
                .andDo(print())
                .andReturn();
    }

}
