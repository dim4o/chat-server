package com.dim.chatserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.repo.MessageRepoImpl;
import com.dim.chatserver.util.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class MessageControllerIntegrationTest extends BaseTest {
    private static final String SEND_TEXT_ENDPOINT = "/messages/send_text";
    private static final String SEND_EMOTION_ENDPOINT = "/messages/send_emotion";

    private String[] invalidTextLengthPayload = initInvalidTextLengthData();
    private String[] validTextLengthPayload = initValidLengthTextData();
    private String[] invalidEmotionLenghtPayload = initInvalidEmotionLenghtData();
    private String[] invalidEmotionWithDigitsPayload = initInvalidEmotionWithDigitsData();
    private String[] validEmotionPayload = initValidEmotionData();

    @MockBean
    private MessageRepoImpl messageRepo;

    @Autowired
    private MockMvc mockMvc;

    /** Send text message test cases. **/
    @Test
    public void sendTextInvalidLength() {
        Arrays.stream(invalidTextLengthPayload)
                .forEach(el -> helper(SEND_TEXT_ENDPOINT, el, PRECONDITION_FAILED));
    }

    @Test
    public void sendValidTextPayloadTest() {
        Arrays.stream(validTextLengthPayload)
                .forEach(el -> helper(SEND_TEXT_ENDPOINT, el, CREATED));
    }

    /** Send emotion message test cases. **/
    @Test
    public void sendEmotionInvalidLengthTest() {
        Arrays.stream(invalidEmotionLenghtPayload)
                .forEach(el -> helper(SEND_EMOTION_ENDPOINT, el, PRECONDITION_FAILED));
    }

    @Test
    public void sendEmotionWithDigitPayloadTest() {
        Arrays.stream(invalidEmotionWithDigitsPayload)
                .forEach(el -> helper(SEND_EMOTION_ENDPOINT, el, PRECONDITION_FAILED));
    }

    @Test
    public void sendValidEmotionPayloadTest() {
        Arrays.stream(validEmotionPayload)
                .forEach(el -> helper(SEND_EMOTION_ENDPOINT, el, CREATED));
    }

    private void helper(String endpoint, String payload, HttpStatus expectedStatus) {
        try {
            Message message = new Message(payload);

            when(messageRepo.save(message)).thenReturn(-1l); // returns a dummy value

            mockMvc.perform(MockMvcRequestBuilders.post(endpoint).content(asJsonString(message))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(expectedStatus.value()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert an {@link Object} to it's JSON representation.
     * 
     * @param obj
     * @return a {@link String} with JSON
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
