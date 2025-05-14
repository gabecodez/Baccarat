/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: Bettor.java
 * Purpose: This file contains the Bettor class which handles the Bettor as a user
 */

package baccarat;

import java.util.ArrayList;

/**
 * handles the bettor as a user
 */
public class Bettor {
  /**
   * the user's name
   */
  private String name;

  /**
   * the user's start balance
   */
  private double startBal;

  /**
   * the user's current balance
   */
  private double currBal;

  /**
   * the winnings for this round
   */
  private double roundWinnings;

  /**
   * stores wagers
   */
  private ArrayList<Wager> wagers;

  /**
   * stores the last round's wagers
   */
  private ArrayList<Wager> lastRoundWagers = new ArrayList<Wager>();

  /**
   * Constructor
   * @param name the name to be set for the user
   * @param startBal the starting balance to be set
   */
  public Bettor(String name, double startBal) {
    this.name = name;
    this.startBal = startBal;
    this.currBal = startBal;
    wagers = new ArrayList<Wager>();
  } // end constructor

  /**
   * gets the user's name
   * @return String of the user's name
   */
  public String getName() {
    return name;
  }  // end getName

  /**
   * returns an ArrayList of the user's wagers
   * @return ArrayList of objects Wager 
   */
  public ArrayList<Wager> getAllWagers() {
    return wagers;
  } // end getAllWagers
  
  /**
   * returns the amount wagered in total this round
   * @return double the amount wagered
   */
  public double getAmountWagered() {
    double totalAmount = 0;
    
    for(int i = 0; i < wagers.size(); i++) {
      totalAmount += wagers.get(i).getAmountWagered();
    }
    
    return totalAmount;
  }

  /**
   * a function for storing the user's bet for the current round
   * @param wager who to wager on
   * @param amountWagered how much to wager
   * @throws BaccaratException if the user does not have the required balance to place the bet
   */
  public void placeBet(String wager, double amountWagered) throws BaccaratException {
    if(currBal - amountWagered < 0) {
      throw new BaccaratException("Not enough balance to place this bet.");
    }
    
    if(amountWagered <= 0) {
      throw new BaccaratException("Wager must be greater than 0.");
    }
    
    currBal -= amountWagered;
    wagers.add(new Wager(wager, amountWagered, false)); // create a new wager object and add to the ArrayList
  } // end placeBet

  /**
   * Processes the rounds winnings and updates the balance based on the hands provided
   * Returns the summary of the results of the wagers
   * @param player the player hand
   * @param banker the banker hand
   * @return ArrayList of objects Wager with all the wagers, whereas the success member shows if it was
   *         successful or not
   * @throws BaccaratException if an error occurs
   */
  public ArrayList<Wager> collectWinnings(BaccaratHand player, BaccaratHand banker) throws BaccaratException {
    ArrayList<Wager> wagersList = new ArrayList<Wager>();
    roundWinnings = 0;

    // loop through wagers
    for(int i = 0; i < wagers.size(); i++) {
      int compareResult = player.compareTo(banker); // get the comparison
      Wager currWager = wagers.get(i);
      
      // handle each process for the amount gained for each wager
      switch(currWager.getWager()) {
        case "tie":
          if(compareResult == 0) {
            currWager.setSuccess(true);
            currWager.setAmountResult(currWager.getAmountWagered() * 8);
          }
          break;
        case "player":
          if(compareResult == 1) {
            currWager.setSuccess(true);
            currWager.setAmountResult(currWager.getAmountWagered());
          }
          break;
        case "banker":
          if(compareResult == -1) {
            currWager.setSuccess(true);
            double amountWon = currWager.getAmountWagered() * 2;
            currWager.setAmountResult(amountWon - (amountWon * 0.05));
          }
          break;
        case "ppair":
          // check if they have a pair
          if(player.getCard(0).rank == player.getCard(1).rank) {
            currWager.setSuccess(true);
            currWager.setAmountResult(currWager.getAmountWagered() * 11);
          }
          break;
        case "bpair":
          // check if they have a pair
          if(banker.getCard(0).rank == banker.getCard(1).rank) {
            currWager.setSuccess(true);
            currWager.setAmountResult(currWager.getAmountWagered() * 11);
          }
          break;
      }

      wagersList.add(currWager);
    }

    // loop through the transactions again and add to the balance
    for(int i = 0; i < wagersList.size(); i++) {
      Wager currWager = wagersList.get(i);

      // if its successful, add the amount wagered back to the balance and the amount earned to the balance
      if(currWager.getSuccess()) {
        currBal += (currWager.getAmountWagered() + currWager.getAmountResult());
        roundWinnings += currWager.getAmountResult();
      }
    }

    return wagersList; // return the wagers
  } // end collectWinnings

  /**
   * returns the last round's wagers
   * @return ArrayList of objects Wager
   */
  public ArrayList<Wager> getLastBet() {
    return lastRoundWagers;
  } // end getLastBet

  /**
   * resets the wagers ArrayList
   */
  public void resetBet() {
    lastRoundWagers = new ArrayList<Wager>();
    for (int i = 0; i < wagers.size(); i++) {
      Wager currWager = wagers.get(i);
      lastRoundWagers.add(new Wager(currWager.getWager(), currWager.getAmountWagered(), currWager.getSuccess()));
    }
    
    wagers.clear();
  } // end resetBet

  /**
   * returns the user's starting balance
   * @return double of the user's starting balance
   */
  public double getStartBal() {
    return startBal;
  } // end getStartBal

  /**
   * return the user's current balance
   * @return double of the user's current balance
   */
  public double getBalance() {
    return currBal;
  } // end getBalance

  /**
   * gets the round winnings
   * @return a double of the round winnings
   */
  public double getRoundWinnings() {
    return roundWinnings;
  } // end getRoundWinnings

  /**
   * returns the total winnings
   * @return winnings as double
   */
  public double getTotalWinnings() {
    return getBalance() - getStartBal(); // current balance minus the starting balance
  } // end getTotalWinnings
} // end Bettor class
