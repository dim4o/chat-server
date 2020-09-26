package com.dim.chatserver.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dim.chatserver.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class MessageControllerTest {
    private static final String SEND_TEXT_ENDPOINT = "/messages/send_text";
    private static final String SEND_EMOTION_ENDPOINT = "/messages/send_emotion";

    @Autowired
    private MockMvc mockMvc;

    /**
     * TEXT
     */
    @Test
    public void sendTextEmptyPayloadTest() {
        helper(SEND_TEXT_ENDPOINT, "", 412);
    }

    @Test
    public void sendTextNullPayloadTest() {
        helper(SEND_TEXT_ENDPOINT, null, 412);
    }

    @Test
    public void sendTextOutOfRangePayloadTest() {
        helper(SEND_TEXT_ENDPOINT, "a".repeat(161), 412);
    }

    @Test
    public void sendValidTextPayloadTest() {
        helper(SEND_TEXT_ENDPOINT, "b".repeat(160), 201);
        helper(SEND_TEXT_ENDPOINT, "c", 201);
        helper(SEND_TEXT_ENDPOINT, "fhsdkfjsdfg()5435345xcfcvxc{}", 201);
    }

    /**
     * EMOTION
     */
    @Test
    public void sendEmotionNullPayloadTest() {
        helper(SEND_EMOTION_ENDPOINT, null, 412);
    }

    @Test
    public void sendEmotionOutOfRangePayloadTest() {
        helper(SEND_EMOTION_ENDPOINT, "a", 412);
        helper(SEND_EMOTION_ENDPOINT, "a".repeat(11), 412);
    }

    @Test
    public void sendEmotionWithDigitPayloadTest() {
        helper(SEND_EMOTION_ENDPOINT, "ds0f()dggf", 412);
        helper(SEND_EMOTION_ENDPOINT, "ads1()jggk", 412);
        helper(SEND_EMOTION_ENDPOINT, "hdsf()mg2b", 412);
        helper(SEND_EMOTION_ENDPOINT, "9dsf(3gf", 412);
        helper(SEND_EMOTION_ENDPOINT, "{sf()d4gf", 412);
        helper(SEND_EMOTION_ENDPOINT, "d5f()dggf", 412);
        helper(SEND_EMOTION_ENDPOINT, "fdsf()6]gf", 412);
        helper(SEND_EMOTION_ENDPOINT, "d7f()dggf", 412);
        helper(SEND_EMOTION_ENDPOINT, "hdsf(8dggf", 412);
        helper(SEND_EMOTION_ENDPOINT, "dsf9)d[gf", 412);
    }

    @Test
    public void sendValidEmotionPayloadTest() {
        helper(SEND_EMOTION_ENDPOINT, ":)", 201);
        helper(SEND_EMOTION_ENDPOINT, ":-D", 201);
        helper(SEND_EMOTION_ENDPOINT, ":}D", 201);
        helper(SEND_EMOTION_ENDPOINT, ":::---}}}D", 201);
        helper(SEND_EMOTION_ENDPOINT, "abcdefgh", 201);
    }

    private void helper(String endpoint, String payload, int expectedCode) {
        try {

            Message message = new Message(payload);

            System.out.println(asJsonString(message));

            mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                    .content(asJsonString(message)).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)).andExpect(status().is(expectedCode));
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
