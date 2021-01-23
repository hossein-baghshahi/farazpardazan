package com.farazpardazan.cardmanagementsystem.service.card;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @author Hossein Baghshahi
 */
public interface CardService {

    Long addCard(Card card);

    public void deleteCard(String cardNumber);

    public Page<Card> getAllCurrentUserActiveCards(Pageable pageable);

    public Page<Card> getAllActiveCards(Pageable pageable);
}
