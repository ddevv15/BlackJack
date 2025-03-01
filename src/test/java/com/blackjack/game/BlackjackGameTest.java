/*
 * Name: Kirtan Manojkumar Patel
 * NetID: kpate452
 * 
 * Name: Dev KishorKumar Shah
 * NetID: dshah69
 * 
 * Project2: Blackjack Game
 */

package com.blackjack.game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class BlackjackGameTest { // BlackjackGameTest class

    @BeforeAll
    static void setupJavaFXRuntime() throws Exception {
        javafx.application.Platform.startup(() -> {
        });
    }

    @Test
    void testGameCreation() {
        BlackjackGame game = new BlackjackGame();
        assertNotNull(game, "Game should be instantiated");
    }

    // Test Card class
    @Test
    void testCardCreation() {
        Card card = new Card("Hearts", 1);
        assertEquals("Hearts", card.getSuit(), "Card suit should be Hearts");
        assertEquals(1, card.getValue(), "Card value should be 1");
    }

    // Test BlackjackDealer class
    BlackjackDealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new BlackjackDealer();
    }

    @Test
    void testDeckGenerationAndShuffling() {
        assertEquals(52, dealer.deckSize(),
                "Deck should contain 52 cards after generation and before drawing any card");
    }

    @Test
    void testDealHand() {
        ArrayList<Card> hand = dealer.dealHand();
        assertEquals(2, hand.size(), "Dealt hand should contain 2 cards");
        assertEquals(50, dealer.deckSize(), "Deck should have 50 cards left after dealing one hand");
    }

    @Test
    void testDrawOne() {
        dealer.drawOne();
        assertEquals(51, dealer.deckSize(), "Deck should have 51 cards left after drawing one card");
    }

    // Test BlackjackGameLogic class
    BlackjackGameLogic gameLogic;

    @BeforeEach
    void setUpGameLogic() {
        gameLogic = new BlackjackGameLogic();
    }

    @Test
    void testHandTotal() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("Hearts", 1));
        hand.add(new Card("Spades", 13));
        assertEquals(21, gameLogic.handTotal(hand), "Hand total should be 21 for an Ace and a King");
    }

    @Test
    void testWhoWon() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 1));
        playerHand.add(new Card("Spades", 10));
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Clubs", 10));
        dealerHand.add(new Card("Diamonds", 9));
        assertEquals("Player Wins with Blackjack!!", gameLogic.whoWon(playerHand, dealerHand),
                "Player should win with a Blackjack against dealer's 19");
    }

    @Test
    void testHandTotalWithMultipleAces() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("Hearts", 1));
        hand.add(new Card("Spades", 1));
        hand.add(new Card("Diamonds", 5));
        assertEquals(17, gameLogic.handTotal(hand), "Hand total should correctly handle multiple Aces.");
    }

    @Test
    void testEvaluateBankerDraw() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Clubs", 10));
        dealerHand.add(new Card("Diamonds", 6));
        assertTrue(gameLogic.evaluateBankerDraw(dealerHand), "Dealer should draw with a total of 16");
        dealerHand.add(new Card("Hearts", 2));
        assertFalse(gameLogic.evaluateBankerDraw(dealerHand), "Dealer should not draw with a total of 18");
    }

    @Test
    void testDeckRegeneration() {
        for (int i = 0; i < 52; i++) {
            dealer.drawOne();
        }
        assertEquals(0, dealer.deckSize(), "Deck should be empty after 52 draws.");
        dealer.generateDeck();
        dealer.shuffleDeck();
        assertEquals(52, dealer.deckSize(), "Deck should be full after regeneration and shuffling.");
    }

    @Test
    void testDeckContainsCorrectValues() {
        dealer.shuffleDeck();
        boolean containsAce = false;
        boolean containsFaceOrTen = false;
        for (int i = 0; i < 52; i++) {
            Card card = dealer.drawOne();
            if (card.getValue() == 1)
                containsAce = true;
            if (card.getValue() == 10)
                containsFaceOrTen = true;
        }
        assertTrue(containsAce, "Deck should contain Aces.");
        assertTrue(containsFaceOrTen, "Deck should contain face cards or cards valued at 10.");
    }

    @Test
    void testCardLabel() {
        Card card = new Card("Spades", 11);
        assertEquals("J of Spades", card.getLabel(), "Card label should correctly represent a Jack of Spades.");
    }

    @Test
    void testIsBlackjack() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("Hearts", 1)); // Ace
        hand.add(new Card("Diamonds", 10)); // 10
        assertEquals("Player Wins with Blackjack!!", gameLogic.whoWon(hand, new ArrayList<>()), "Hand with Ace and 10 should be a Blackjack.");
    }

    @Test
    void testHandTotalExceeding21() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card("Hearts", 10));
        hand.add(new Card("Clubs", 10));
        hand.add(new Card("Spades", 5)); // Total should be 25
        assertEquals(25, gameLogic.handTotal(hand), "Hand total should exceed 21.");
    }

    @Test
    void testDealerStandsOnSoft17() {
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Hearts", 1)); // Ace
        dealerHand.add(new Card("Diamonds", 6)); // 6
        assertFalse(gameLogic.evaluateBankerDraw(dealerHand), "Dealer should stand on soft 17.");
    }

    @Test
    void testPlayerBustResultsInDealerWin() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 10));
        playerHand.add(new Card("Clubs", 10));
        playerHand.add(new Card("Spades", 5)); // Bust
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Diamonds", 9));
        assertEquals("Dealer Wins!!", gameLogic.whoWon(playerHand, dealerHand), "Dealer should win if player busts.");
    }

    @Test
    void testPushWhenBothHaveSameTotal() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 10));
        playerHand.add(new Card("Clubs", 7)); // 17
        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Diamonds", 7));
        dealerHand.add(new Card("Spades", 10)); // 17
        assertEquals("Push!!", gameLogic.whoWon(playerHand, dealerHand), "Should be a push when both have the same total.");
    }

    @Test
    void testTieWithNonBlackjackHands() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 10));
        playerHand.add(new Card("Diamonds", 7));

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Spades", 10));
        dealerHand.add(new Card("Clubs", 7));

        assertEquals("Push!!", gameLogic.whoWon(playerHand, dealerHand), "It should be a tie (push) with non-blackjack hands of equal value.");
    }

    @Test
    void testPlayerWinningWithHigherTotal() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 9));
        playerHand.add(new Card("Diamonds", 9));

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Spades", 8));
        dealerHand.add(new Card("Clubs", 8));

        assertEquals("Player Wins!!", gameLogic.whoWon(playerHand, dealerHand), "Player should win with a higher total than the dealer.");
    }

    @Test
    void testDealerWinningByPlayerBust() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 10));
        playerHand.add(new Card("Diamonds", 8));
        playerHand.add(new Card("Clubs", 5)); // Player busts with 23

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Spades", 9));
        dealerHand.add(new Card("Clubs", 7));

        assertEquals("Dealer Wins!!", gameLogic.whoWon(playerHand, dealerHand), "Dealer should win if the player busts.");
    }


    @Test
    void testPlayerBlackjackVersusDealerBlackjack() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card("Hearts", 1)); // Ace
        playerHand.add(new Card("Diamonds", 10)); // 10 for a blackjack

        ArrayList<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card("Spades", 1)); // Ace
        dealerHand.add(new Card("Clubs", 10)); // 10 for a blackjack

        assertEquals("Push!!", gameLogic.whoWon(playerHand, dealerHand), "Game should result in a push if both player and dealer have blackjack.");
    }

}
