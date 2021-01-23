package com.farazpardazan.cardmanagementsystem.service.card;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.domain.QCard;
import com.farazpardazan.cardmanagementsystem.domain.User;
import com.farazpardazan.cardmanagementsystem.repository.CardRepository;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Hossein Baghshahi
 */
@Service
public class DefaultCardService implements CardService {

    private final CardRepository cardRepository;

    private final UserService userService;

    public DefaultCardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    @Override
    public Long addCard(Card card) {
        try {
            card = cardRepository.save(card);
        }catch (Exception ex){
            throw new AddNewCardException("Error adding new card.");
        }

        return card.getId();
    }

    @Override
    public void deleteCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        card.setActive(false);
        cardRepository.save(card);
    }

    @Override
    public Page<Card> getAllCurrentUserActiveCards(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Predicate predicate = QCard.card.owner.id.eq(currentUser.getId()).and(QCard.card.active);
        Page<Card> userCards = cardRepository.findAll(predicate,pageable);

        return userCards;
    }

    @Override
    public Page<Card> getAllActiveCards(Pageable pageable) {
        return cardRepository.findAll(QCard.card.active,pageable);
    }
}
