/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: BaccaratCasinoGame.java
 * Purpose: This file contains the BaccaratCasinoGame class which handles the main game functions.
 */
package baccarat;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * handles the game
 */
public class BaccaratCasinoGame extends CasinoCardGame {
  /**
   * the user bettor
   */
  private Bettor bettor;
  private BaccaratHand player;
  private BaccaratHand banker;
  private int roundCounter;

  /**
   * Constructor
   * @param numDecks the number of decks used for the shoe
   * @param maxNumPerDeck the number of cards / 2 in the deck
   * @param startBal the user's starting balance
   */
  public BaccaratCasinoGame(int numDecks, int maxNumPerDeck, double startBal) {
    super();
    
    try {
      this.shoe = new Shoe(numDecks, maxNumPerDeck);
    } catch (BaccaratException e) {
      e.printStackTrace();
      return;
    } // end try-catch

    shoe.shuffle();

    this.player = new BaccaratHand();
    this.banker = new BaccaratHand();
	
    // get the user's name and await their entry and submission
    String username = getUsername();

    bettor = new Bettor(username, startBal);
    roundCounter = 0;
  } // end constructor
  
  @Override
  public void play() {
    
  }
  
  /** 
   * gets the user's name
   * @return String the user's username
   */
  public String getUsername() {
    String username = "";
    // display a popup asking the user for their username and wait for them to submit
    return username;
  }
  
  /** 
   * gets the user's current balance
   * @return double the user's balance
   */
  public double getBettorBal() {
    return bettor.getBalance();
  }
  
  /** 
   * gets the player object
   * @return BaccaratHand the player hand
   */
  public BaccaratHand getPlayer() {
    return player;
  }
  
  /** 
   * gets the banker object
   * @return BaccaratHand the banker hand
   */
  public BaccaratHand getBanker() {
    return banker;
  }
  
  /**
   * returns the bettor object
   * @return Bettor the bettor object
   */
  public Bettor getBettor() {
    return bettor;
  }
  
  /** 
   * gets the current round number
   * @return int the current round number
   */
  public int getRoundNumber() {
    return roundCounter;
  }

  /** 
   * increases the round counter
   */
  public void nextRound() {
    bettor.resetBet();
    clearHands();
    roundCounter += 1;
  }
  
  /** 
   * collects the winnings
   * @return double the winnings of the round
   * @throws BaccaratException if the internal function fails 
   */
  public double collectWinnings() throws BaccaratException {
    bettor.collectWinnings(player, banker);
    return bettor.getRoundWinnings();
  }
  
  /** 
   * returns the winnings
   * @return double the winnings of the round
   */
  public double getRoundWinnings() {
    return bettor.getRoundWinnings();
  }

  /** 
   * gets and sets bets
   * @param wagerPart the string representing the wager
   * @param amount a double representing the amount to be wagered
   * @throws BaccaratException if when submitting the wager something is not in line
   */
  public void submitWager(String wagerPart, double amount) throws BaccaratException {
    // try and place the wager
    bettor.placeBet(wagerPart, amount);
  }
  
  /** 
   * submits the last round's wagers
   * @throws BaccaratException if doing the current wagers does not work
   */
  public void submitLastWagers() throws BaccaratException {
    ArrayList<Wager> lastWagers = bettor.getLastBet(); // get the most recent wagers
      
    // check if its empty
    if(lastWagers.isEmpty()) {
      throw new BaccaratException("You cannot afford your last bets!");
    }

    // tally up the cost of requesting last
    double wagerCost = 0;
    for(int i = 0; i < lastWagers.size(); i++) {
      wagerCost += lastWagers.get(i).getAmountWagered();
    }

    // check if the user can afford it
    if(wagerCost > bettor.getBalance()) {
      throw new BaccaratException("You cannot afford your last bets!");
    }

    // loop through and place the bets
    for(int i = 0; i < lastWagers.size(); i++) {
      Wager currWager = lastWagers.get(i);
      
      bettor.placeBet(currWager.getWager(), currWager.getAmountWagered());
    }
  }

  /**
   * deals cards to the player and banker from the shoe
   */
  public void dealCards() {
    dealHandACard(shoe, player);
    dealHandACard(shoe, banker);
    dealHandACard(shoe, player);
    dealHandACard(shoe, banker);
  }// end dealCards

  /**
   * clears hands of the player and banker
   */
  public void clearHands() {
    player.clear();
    banker.clear();
  }// end clearHands

  /**
   * simulates the player's turn in a game of Baccarat
   * @return a string representing the result to be printed for this turn
   */
  public String doPlayersTurn() {
    if (player.getValue() <= 5) {
      dealHandACard(shoe, player);
      return "draws a third card";
    } else {
      return "stands";
    }
  } // end playersTurn

  /**
   * simulates the banker's turn in a game of Baccarat
   * @return a string representing the result to be printed for this turn
   */
  public String doBankersTurn() {
    // Banker drawing rules:
    if (player.getCardCount() >= 3) {
      // If the player stands, the banker draws if total is 5 or less.
      if (banker.getValue() <= 5) {
        dealHandACard(shoe, banker);
        return "draws a third card";
      }
    } else {
      // If the player draws, banker drawing depends on the player's third card.
      int player_third_value;
      try {
        player_third_value = player.getCard(2).getValue();
      } catch (BaccaratException e) {
        return "stands";
      }
      if (banker.getValue() == 6) {
        if (player_third_value == 6 || player_third_value == 7) {
          dealHandACard(shoe, banker);
          return "draws a third card";
        }
      } else if (banker.getValue() == 5) {
        if (player_third_value >= 4 && player_third_value <= 7) {
          dealHandACard(shoe, banker);
          return "draws a third card";
        }
      } else if (banker.getValue() == 4) {
        if (player_third_value >= 2 && player_third_value <= 7) {
          dealHandACard(shoe, banker);
          return "draws a third card";
        }
      } else if (banker.getValue() == 3) {
        if (player_third_value != 8) {
          dealHandACard(shoe, banker);
          return "draws a third card";
        }
      } else if (banker.getValue() <= 2) {
        dealHandACard(shoe, banker);
        return "draws a third card";
      }

      // Banker stands on 7.
      return "stands";
    }

    return "stands"; // how did you get here
  } // end bankersTurn

  /**
   * gives a hand a new car and adds it, shuffles the shoe if it is
   * @param s the main dealing deck
   * @param x whichever hand shall take this dealing
   */
  public static void dealHandACard(Shoe s, Hand x) {
    try {
      x.addCard(s.deal());
    } catch (BaccaratException e) {
      s.shuffle();
      try {
        x.addCard(s.deal());
      } catch (BaccaratException e2) {
        
      }
    }
  } // end dealHandACard

  /**
   * determines the winner of the game
   * @return a string representing the winner
   */
  public String calculateWinner() {
    // Determine the winner.
    if (player.compareTo(banker) == 1)
      return "player";
    else if (player.compareTo(banker) == -1)
      return "banker";
    else
      return "tie";
  } // end calculateWinner

}// end BaccaratGame
