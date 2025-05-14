/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: Hand.java
 * Purpose: This file contains the Hand class which represents a number of cards as a hand.
 */

package baccarat;

import java.util.ArrayList;

/**
 * This class represents all data and related operations for a hand of playing cards
 * for use in various card games. Implemented in this package for the game Baccarat.
 */
public class Hand {
  /**
   * Dynamic Array structure for storing Cards in the hand
   */
  private ArrayList<Card> hand;

  /**
   * Constructor
   */
  public Hand() {
    hand = new ArrayList<Card>();
  }// end constructor

  /**
   * returns the value of a card at a value
   * @param pos card position
   * @return object of card at said position
   * @throws BaccaratException when the id is out of bounds
   */
  public Card getCard(int pos) throws BaccaratException {
    if (pos < 0 || pos >= getCardCount()) {
      throw new BaccaratException("Position" + pos + " is out of bounds.");
    }

    return hand.get(pos);
  } // end getCard

  /**
   * returns the size of the hand
   * @return int of the hands size
   */
  public int getCardCount() {
    return hand.size();
  } // end GetCardCount

  /**
   * adds a new card to the hand
   * @param c the new card object to be added
   * @throws BaccaratException when the card provided is null
   */
  public void addCard(Card c) throws BaccaratException {
    // check if card is null
    if (c == null) {
      throw new BaccaratException("Card provided for addition is null.");
    }

    hand.add(c);
  } // end addCard

  /** 
   * removes a card from the hand
   * @param c the card to be removed
   * @throws BaccaratException if the card to remove is null
   */
  public void removeCard(Card c) throws BaccaratException {
    // check if card is null
    if (c == null) {
      throw new BaccaratException("Card provided for removal is null.");
    }

    hand.remove(c);
  } // end addCard

  /**
   * clears the hand
   */
  public void clear() {
    hand.clear();
  } // end clear

  /**
   * returns the hand as a string
   * @return String of the hand
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < getCardCount(); i++) {
      try {
        sb.append(getCard(i).toString());
      } catch (BaccaratException e) {
        return "An error occurred while getting card.";
      }

      if (i < getCardCount() - 1) {
        sb.append(", ");
      }
    } // end for

    return sb.toString();
  } // end toString
} // end Hand
