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

public class BlackjackGameLogic { // BlackjackGameLogic class

    // Function to check who won
    public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> dealerHand) {
        int playerTotal = handTotal(playerHand);
        int dealerTotal = handTotal(dealerHand);

        System.out.println("Player total: " + playerTotal + ", Dealer total: " + dealerTotal);

        boolean playerBlackjack = isBlackjack(playerHand);
        boolean dealerBlackjack = isBlackjack(dealerHand);

        if (playerBlackjack && dealerBlackjack) {
            return "Push!!";
        } else if (playerBlackjack) {
            return "Player Wins with Blackjack!!";
        } else if (dealerBlackjack) {
            return "Dealer Wins with Blackjack!!";
        } else if (playerTotal == dealerTotal) {
            return "Push!!";
        } else if (playerTotal <= 21 && (playerTotal > dealerTotal || dealerTotal > 21)) {
            return "Player Wins!!";
        } else {
            return "Dealer Wins!!";
        }
    }

    // Function to get hand total
    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            int value = card.getValue();
            if (value == 1) {
                aces++;
                total += 11;
            } else {
                // total += (value > 10) ? 10 : value; // Face cards are worth 10
                total += Math.min(value, 10);
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10; // Convert an Ace from 11 to 1
            aces--;
        }

        return total;
    }

    // Function to evaluate banker draw
    public boolean evaluateBankerDraw(ArrayList<Card> hand) {
        return handTotal(hand) <= 16;
    }

    // Function to check if blackjack
    private boolean isBlackjack(ArrayList<Card> hand) {
        if (hand.size() == 2) {
            int handTotal = handTotal(hand);
            return handTotal == 21;
        }
        return false;
    }
}
