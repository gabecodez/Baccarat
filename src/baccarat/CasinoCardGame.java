/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: CasinoCardGame.java
 * Purpose: This file contains the CasinoCardGame class which is an abstract class.
 */

package baccarat;

/**
 * This class represents a player with their hand. It handles functions for determining if they stand.
 * It is a subclass of Hand.java
 */
public abstract class CasinoCardGame {
  /**
   * the deck of cards
   */
  protected Shoe shoe;

  /**
   * the player hand
   */
  protected Hand player;
  /**
   * the banker hand
   */
  protected Hand banker;

  /**
   * Constructor
   */
  public CasinoCardGame() {
    this.player = new Hand();
    this.banker = new Hand();
  }

  /**
   * Abstract function for handling gameplay
   */
  public abstract void play();
}