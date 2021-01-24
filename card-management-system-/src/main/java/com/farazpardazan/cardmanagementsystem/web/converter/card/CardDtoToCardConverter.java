package com.farazpardazan.cardmanagementsystem.web.converter.card;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.service.user.UserService;
import com.farazpardazan.cardmanagementsystem.web.dto.card.CardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class CardDtoToCardConverter implements Converter<CardDto, Card> {
    private final UserService userService;

    public CardDtoToCardConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Card convert(CardDto cardDto) {
        Card card = new Card();
        card.setName(cardDto.getName());
        card.setCardNumber(cardDto.getCardNumber());
        card.setOwner(userService.getCurrentUser());
        card.setActive(true);
        return card;
    }
}
