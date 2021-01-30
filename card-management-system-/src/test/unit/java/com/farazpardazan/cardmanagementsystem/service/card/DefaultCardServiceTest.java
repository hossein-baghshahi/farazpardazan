package com.farazpardazan.cardmanagementsystem.service.card;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.domain.QCard;
import com.farazpardazan.cardmanagementsystem.domain.User;
import com.farazpardazan.cardmanagementsystem.repository.CardRepository;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.querydsl.core.types.Predicate;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

/**
 * @author Hossein Baghshahi
 */
@ExtendWith(SpringExtension.class)
public class DefaultCardServiceTest {

    @Autowired
    private CardService cardService;

    @MockBean
    private CardRepository cardRepositoryMocked;

    @MockBean
    private UserService userServiceMocked;

    private User dummyUser;

    private Card dummyCard;

    @BeforeEach
    public void prepareDummies() {
        dummyUser = new User();
        dummyUser.setUsername("javier");
        dummyUser.setName("javier");
        dummyUser.setFamily("bardem");
        dummyUser.setMobileNumber("09124584934");

        dummyCard = new Card();
        dummyCard.setCardNumber("61239189239495944");
        dummyCard.setOwner(dummyUser);
        dummyCard.setActive(true);
    }

    @Test
    void addCard_DuplicateCard_ShouldThrowAddNewCardException() {
        doThrow(ConstraintViolationException.class).when(cardRepositoryMocked).save(dummyCard);

        assertThatExceptionOfType(AddNewCardException.class)
                .isThrownBy(() -> cardService.addCard(dummyCard));
    }

    @Test
    void addCard_NewCard_ShouldSucceed() {
        doReturn(dummyCard).when(cardRepositoryMocked).save(dummyCard);

        cardService.addCard(dummyCard);
        verify(cardRepositoryMocked, times(1)).save(dummyCard);
    }

    @Test
    void deleteCard_NotPersisted_ShouldThrowCardNotFoundException() {
        doReturn(Optional.empty()).when(cardRepositoryMocked).findByCardNumber(dummyCard.getCardNumber());

        assertThatExceptionOfType(CardNotFoundException.class)
                .isThrownBy(() -> cardService.deleteCard(dummyCard.getCardNumber()));
    }

    @Test
    void deleteCard_PersistedCard_ShouldDeactivateIt() {
        doReturn(Optional.of(dummyCard)).when(cardRepositoryMocked).findByCardNumber(dummyCard.getCardNumber());

        cardService.deleteCard(dummyCard.getCardNumber());
        dummyCard.setActive(false);
        verify(cardRepositoryMocked, times(1)).save(dummyCard);
    }

    @Test
    void getAllCurrentUserActiveCards_ValidInput_ShouldReturnPageResult() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        dummyUser.setId(1L);
        doReturn(dummyUser).when(userServiceMocked).getCurrentUser();
        Predicate predicate = QCard.card.owner.id.eq(dummyUser.getId()).and(QCard.card.active);
        doReturn(new PageImpl<>(Collections.singletonList(dummyCard)))
                .when(cardRepositoryMocked).findAll(predicate, pageRequest);

        cardService.getAllCurrentUserActiveCards(pageRequest);
        verify(cardRepositoryMocked, times(1)).findAll(predicate, pageRequest);
    }

    @TestConfiguration
    public static class CardServiceTestConfiguration {

        @Bean
        public CardService cardService(CardRepository cardRepository, UserService userService) {
            return new DefaultCardService(cardRepository, userService);
        }

    }

}
