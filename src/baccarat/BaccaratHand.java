/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: BaccaratHand.java
 * Purpose: This file contains the BaccaratGame class which handles the main game functions.
 */

package baccarat;

import java.util.ArrayList;

/**
 * This class represents a player with their hand. It handles functions for determining if they stand.
 * It is a subclass of Hand.java
 */
public class BaccaratHand extends Hand implements Comparable<BaccaratHand> {
  /**
   * Constructor
   */
  public BaccaratHand() {
    super();
  }// end constructor

  /**
   * returns the value of the hand based on the cards within
   * @return int of sum value
   */
  public int getValue() {
    int sum = 0;
    for (int i = 0; i < getCardCount(); i++) {
      try {
        sum += getCard(i).getValue();
      } catch (BaccaratException e) {
        return -1;
      }
    } // end for
    return sum % 10;
  } // end getValue

  /**
   * Compares the current hand to the other hand
   * @param otherHand the other hand to compare the card to
   * @return int, 1 if greater than, 0 if equal, -1 if less than
   */
  @Override
  public int compareTo(BaccaratHand otherHand) {
    return Integer.compare(this.getValue(), otherHand.getValue());
  } // end compareTo
} // end BaccaratHand class
