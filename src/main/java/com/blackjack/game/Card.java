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

public class Card { // Card class
    private String suit;
    private int value;
    private String label;

    // Constructor
    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
        this.label = createLabel(value, suit);
        System.out.println("Created a " + this);
    }

    // Function to create label
    private String createLabel(int value, String suit) {
        String valueLabel;
        switch (value) {
            case 1:
                valueLabel = "A";
                break;
            case 11:
                valueLabel = "J";
                break;
            case 12:
                valueLabel = "Q";
                break;
            case 13:
                valueLabel = "K";
                break;
            default:
                valueLabel = String.valueOf(value);
        }
        return valueLabel + " of " + suit;
    }

    // Function to get label
    public String getLabel() {
        return label;
    }

    // Function to get suit
    public String getSuit() {
        return suit;
    }

    // Function to get rank
    public String getRank() {
        // Function returns a String representation of the rank based on the card's
        // value
        switch (value) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(value); // Converting numeric values directly to String
        }
    }

    // Function to get value
    public int getValue() {
        return value;
    }

    // Function to get string representation
    public String toString() {
        return "Card{ " +
                "Suit:" + suit +
                ", value=" + value +
                '}';
    }

}
