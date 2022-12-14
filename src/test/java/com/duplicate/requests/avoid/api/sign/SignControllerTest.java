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

import com.duplicate.requests.avoid.api.sign.model.Sign;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location = classpath:application-default.yml" })
public class SignControllerTest {

    @Autowired
    MockMvc mockMvc; // 임시 서버

    @Test
    void testSignUp() {

        Sign signInfo = new Sign();
        signInfo.setEmail("unazuv@fagdo.nu");
        signInfo.setName("Lucas Pope");
        signInfo.setPassword("1q2w3e4r!#@$QWER");

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(signInfo);

            MvcResult result = mockMvc.perform(
                    post("/api/sign/up")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestJson))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void testSignIn() {

    }
}
