/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: Card.java
 * Purpose: This file contains the Card class which represents a playing cards for the game.
 */

package baccarat;

/**
 * This Card class represents all data and related operations for a single playing
 * card in a standard deck of playing cards.
 */
public class Card {
  enum Rank {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
  enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES};

  Rank rank;
  Suit suit;

  /**
   * Constructor
   * @param id the value of the card to set
   */
  public Card(int id) {
    rank = Rank.values()[id % 13]; // set the rank
    suit = Suit.values()[id / 13]; // set the suit
  } // end constructor

    /**
   * Constructor
   * @param rank the rank to set
   * @param suit the suit to set
   */
  public Card(Rank rank, Suit suit) {
    this.rank = rank; // set the rank
    this.suit = suit; // set the suit
  } // end constructor

  /**
   * returns the value of the card based on the baccarat scoring system.
   * @return int of the value of the card according to the scoring system
   */
  public int getValue() {
    int rankIndex = rank.ordinal(); // gets the index of it related to enums
    return rankIndex < 9 ? rankIndex + 1 : 0; // If less than 9, add one to the value, otherwise, it is worth 0
  } // end getValue

  /**
   * returns the rank of the card as an enum.
   * @return Rank enum of the rank of the card
   */
  public Rank getRank() {
    return rank;
  } // end getRank

  /**
   * returns the suit of the card as an enum.
   * @return Suit enum of the suit of the card
   */
  public Suit getSuit() {
    return suit;
  } // end getSuit

  /** 
   * returns the string representation of the card
   * @return String of format (e.g.) 2 of CLUBS where CLUBS is the suit and 2 is the rank.
   */
  public String toString() {
    // create the string
    StringBuilder sb = new StringBuilder();

    sb.append(rank);
    sb.append(" of ");
    sb.append(suit);

    return sb.toString();
  } // end toString
}// end Card
