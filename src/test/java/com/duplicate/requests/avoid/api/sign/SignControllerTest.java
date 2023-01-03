package com.duplicate.requests.avoid.api.sign;

// API Post 실행시
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.duplicate.requests.avoid.dto.AccountDto;
import com.duplicate.requests.avoid.dto.AuthDto;
import com.duplicate.requests.avoid.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location = classpath:application-default.yml" })
public class SignControllerTest {

    @Autowired
    MockMvc mockMvc; // 임시 서버

    final String email = "unazuv@fagdo.nu";
    final String password = "1q2w3e4r!#@$QWER";

    @Test
    void testSignUp() {

        UserDto user = new UserDto();
        user.setEmail(email);
        user.setName("Lucas Pope");
        user.setPassword(password);

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(user);

            MvcResult result = mockMvc.perform(
                    post("/api/sign/up")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSignIn() {

        AccountDto accountDto = new AccountDto();
        accountDto.setEmail(email);
        accountDto.setPassword(password);

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(accountDto);

            MvcResult result = mockMvc.perform(
                    post("/api/sign")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void testRefresh() {

        AuthDto authDto = new AuthDto();
        authDto.setRefreshToken(
                // TODO : testSignIn후 RefreshToken 받은 후 실행
                // TODO : 변경 필요
                "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE2NzEyNjAwNjR9.f4-fF64udWz-IJAVchhRxxi-xSR4TP9yp08GFu6cMUQ");

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(authDto);

            MvcResult result = mockMvc.perform(
                    post("/api/sign/refresh")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
