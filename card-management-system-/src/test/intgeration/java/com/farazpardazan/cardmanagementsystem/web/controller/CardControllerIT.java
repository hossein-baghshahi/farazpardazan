package com.farazpardazan.cardmanagementsystem.web.controller;

import com.farazpardazan.cardmanagementsystem.configuration.AuthenticationConfiguration;
import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.domain.User;
import com.farazpardazan.cardmanagementsystem.repository.CardRepository;
import com.farazpardazan.cardmanagementsystem.service.card.CardService;
import com.farazpardazan.cardmanagementsystem.service.card.DefaultCardService;
import com.farazpardazan.cardmanagementsystem.service.notification.NotificationService;
import com.farazpardazan.cardmanagementsystem.service.notification.SmsNotificationService;
import com.farazpardazan.cardmanagementsystem.service.user.DefaultUserService;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.farazpardazan.cardmanagementsystem.web.dto.card.CardDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Hossein Baghshahi
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(CardController.class)
@Import({AuthenticationConfiguration.class})
@MockBean(classes = {NotificationService.class,UserService.class})
class CardControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CardService cardService;

    @MockBean
    private CardRepository cardRepositoryMocked;

    private CardDto dummyCardDto;

    private Card dummyCard;

    private User dummyUser;

    @BeforeEach
    public void prepareDummies(){
        dummyUser = new User();
        dummyUser.setUsername("javier");
        dummyUser.setName("javier");
        dummyUser.setFamily("bardem");
        dummyUser.setMobileNumber("09124584934");

        dummyCardDto = new CardDto("test","6219861036582365");

        dummyCard = new Card();
        dummyCard.setCardNumber("6219861036582365");
        dummyCard.setOwner(dummyUser);
        dummyCard.setActive(true);
    }

    @Test
    @WithAnonymousUser
    void addCard_NotAuthenticatedUser_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/cards")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "javier", password = "456")
    void addCard_WithBadFormatCardNumber_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new CardDto("test","13121"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "javier", password = "456")
    void addCard_WithValidData_ShouldReturnCreated() throws Exception {
        doReturn(dummyCard).when(cardRepositoryMocked).save(any(Card.class));
        mockMvc.perform(post("/api/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dummyCardDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "javier", password = "456")
    void deleteCard_WithValidCardNumber_ShouldDeleteSuccessfully() throws Exception {
        doReturn(Optional.of(dummyCard)).when(cardRepositoryMocked).findByCardNumber(dummyCard.getCardNumber());
        cardService.deleteCard(dummyCard.getCardNumber());
        dummyCard.setActive(false);

        mockMvc.perform(delete("/api/cards/6219861036582365")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andExpect(status().isNoContent());
    }


    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to json", e);
        }
    }


    @TestConfiguration
    public static class CardControllerTestConfiguration {

        @Bean
        public CardService cardService(CardRepository cardRepository, UserService userService){
            return new DefaultCardService(cardRepository,userService);
        }
    }
}