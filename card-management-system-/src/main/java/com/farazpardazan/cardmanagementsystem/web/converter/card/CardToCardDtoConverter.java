package com.farazpardazan.cardmanagementsystem.web.converter.card;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.web.dto.card.CardDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Hossein Baghshahi
 */
@Component
public class CardToCardDtoConverter implements Converter<Card, CardDto> {
    @Override
    public CardDto convert(Card card) {
        CardDto cardDto = new CardDto();
        cardDto.setName(card.getName());
        cardDto.setCardNumber(card.getCardNumber());
        return cardDto;
    }
}
