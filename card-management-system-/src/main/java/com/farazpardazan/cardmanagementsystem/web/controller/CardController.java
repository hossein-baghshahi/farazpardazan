package com.farazpardazan.cardmanagementsystem.web.controller;

import com.farazpardazan.cardmanagementsystem.domain.Card;
import com.farazpardazan.cardmanagementsystem.service.card.CardService;
import com.farazpardazan.cardmanagementsystem.web.dto.card.CardDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.farazpardazan.cardmanagementsystem.configuration.Constants.URLMapping.CARDS;

/**
 * @author Hossein Baghshahi
 */

@RestController
@RequestMapping(CARDS)
public class CardController {
    private final CardService cardService;

    private final ConversionService conversionService;

    public CardController(CardService cardService, ConversionService conversionService) {
        this.cardService = cardService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity addNewCard(@Validated @RequestBody CardDto cardDto) {
        Long id = cardService.addCard(conversionService.convert(cardDto, Card.class));

        return ResponseEntity.created(URI.create("cards" + "/" + id)).build();
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity deleteCard(@PathVariable("cardNumber") String cardNumber) {
        cardService.deleteCard(cardNumber);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAllCards(Pageable pageable) {
        return ResponseEntity.ok(
                cardService.getAllCurrentUserActiveCards(pageable)
                        .map(card -> conversionService.convert(card, CardDto.class)));

    }

}
