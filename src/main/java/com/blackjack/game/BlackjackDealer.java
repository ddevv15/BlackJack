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

import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer { // BlackjackDealer class
    private ArrayList<Card> deck = new ArrayList<>();

    // Constructor
    public BlackjackDealer() {
        generateDeck();
        shuffleDeck();
    }

    // Function to generate deck
    public void generateDeck() {

        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        deck.clear();
        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {
                int value = (i > 10) ? 10 : i;
                deck.add(new Card(suit, value));
            }
        }
        System.out.println("Deck generated.");
    }

    // Function to deal hand
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(drawOne());
        hand.add(drawOne());
        System.out.println("Dealt a hand: " + hand);
        return hand;
    }

    // Function to draw one card
    public Card drawOne() {
        if (deck.isEmpty()) {
            generateDeck();
            shuffleDeck();
        }
        Card card = deck.remove(deck.size() - 1);
        System.out.println("Drew a card: " + card);
        return card;
    }

    // Function to shuffle deck
    public void shuffleDeck() {
        Collections.shuffle(deck);
        System.out.println("Deck shuffled.");
    }

    // Function to get deck size
    public int deckSize() {
        return deck.size();
    }
}
