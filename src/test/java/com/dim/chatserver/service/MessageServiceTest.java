package com.dim.chatserver.service;

import static com.dim.chatserver.model.MessageType.SEND_EMOTION;
import static com.dim.chatserver.model.MessageType.SEND_TEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dim.chatserver.model.Message;
import com.dim.chatserver.repo.MessageRepo;
import com.dim.chatserver.util.BaseTest;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class MessageServiceTest extends BaseTest {

    private String[] invalidTextLengthPayload = initInvalidTextLengthData();
    private String[] validTextLengthPayload = initValidLengthTextData();
    private String[] invalidEmotionLenghtPayload = initInvalidEmotionLenghtData();
    private String[] invalidEmotionWithDigitsPayload = initInvalidEmotionWithDigitsData();
    private String[] validEmotionPayload = initValidEmotionData();

    @MockBean
    MessageRepo messageRepo;

    @Autowired
    MessageService messageService;

    @Test
    public void invalidTextMessgeTest() {
        Arrays.stream(invalidTextLengthPayload)
                .forEach(el -> helper(new Message(el, SEND_TEXT), PRECONDITION_FAILED));
    }

    @Test
    public void validTextMessageTest() {
        Arrays.stream(validTextLengthPayload)
                .forEach(el -> helper(new Message(el, SEND_TEXT), CREATED));
    }
    
    @Test
    public void validEmotionMessageTest() {
        Arrays.stream(validEmotionPayload)
                .forEach(el -> helper(new Message(el, SEND_EMOTION), CREATED));
    }
    
    @Test
    public void invalidEmotionLengthMessgeTest() {
        Arrays.stream(invalidEmotionLenghtPayload)
                .forEach(el -> helper(new Message(el, SEND_EMOTION), PRECONDITION_FAILED));
    }
    
    @Test
    public void invalidEmotionWithDigitsMessgeTest() {
        Arrays.stream(invalidEmotionWithDigitsPayload)
                .forEach(el -> helper(new Message(el, SEND_EMOTION), PRECONDITION_FAILED));
    }

    private void helper(Message message, HttpStatus expectedStatus) {
        when(messageRepo.save(message)).thenReturn(-1l); // returns a dummy value
        assertThat(messageService.save(message).getStatusCode()).isSameAs(expectedStatus);
    }
}
