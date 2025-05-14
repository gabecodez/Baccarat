/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: Shoe.java
 * Purpose: This file contains the Shoe class which represents a shoe of cards.
 */

package baccarat;

/**
 * This class represents all data and operations for a Shoe composed 
 * of multiple decks of standard playing cards used in various casino 
 * games. Implemented in this package for the game Baccarat.
 */
public class Shoe {
  private Card[] shoe; // Cards in the Shoe
  private int top; // Pointer/Index of top card in Shoe (next card to deal)
  private int reshuffleCount; // the maximum number of cards before a reshuffle is needed

  /**
   * Constructor
   * @throws BaccaratException if the deck is too big
   */
  public Shoe() throws BaccaratException {
    this(1, 52);
  }// end constructor

  /**
   * Constructor
   * @param numDecks The number of decks in the Shoe
   * @param maxNumPerDeck The number of cards / 2
   * @throws BaccaratException if the deck is too big
   */
  public Shoe(int numDecks, int maxNumPerDeck) throws BaccaratException {
    if (maxNumPerDeck > 52) {
      throw new BaccaratException("Decks may only be a max of 52 cards.");
    }
    shoe = new Card[numDecks * 52];

    // loops through four times to populate the deck
    for (int i = 0; i < numDecks; i++) {
      // loops through the size of a deck
      for (int j = 0; j < 52; j++) {
        shoe[j + (i * 52)] = new Card(j); // adds a new card to the array
      }
    }

    reshuffleCount = numDecks * maxNumPerDeck;
  } // end constructor

  /**
   * returns the number of cards left in the deck
   * @return int of the shoe's size
   */
  public int cardsLeft() {
    return shoe.length - top;
  } // end cardsLeft

  /**
   * deals a card from the shoe
   * @return Card object that is being dealt
   * @throws BaccaratException if half of the card has been dealt
   */
  public Card deal() throws BaccaratException {
    top++;
    reshuffleCount--;
    // if half the cards have been dealt out, throw an exception
    if(top >= reshuffleCount) {
      throw new BaccaratException("Half the deck hath been dealt.");
    }

    return shoe[top - 1];
  } // end deal

  /**
   * Return all cards to the shoe and shuffle
   * using the Fisher-Yates shuffling algorithm.
   */
  public void shuffle() {
    top = 0;

    for (int i = shoe.length - 1; i > top; i--) {
      int rnd = (int) (Math.random() * (i + 1));
      Card temp = shoe[rnd];
      shoe[rnd] = shoe[i];
      shoe[i] = temp;
    } // end for

  }// end shuffle

}// end Shoe
