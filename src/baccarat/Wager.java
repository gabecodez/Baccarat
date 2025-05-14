/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: Wager.java
 * Purpose: This file contains the Wager class which handles any given wager as an object
 */

package baccarat;

/**
 * handles the wager as an object
 */
public class Wager {
  private String wager; // string representing what they are betting on
  private double amountWagered; // the amount to be wagered
  private Boolean success; // true if they won the wager and false if not
  private double amountResult; // the amount gained or lost by the transaction

  /**
   * Constructor
   * @param wager a String representing on what was bet
   * @param amountWagered the amount wagered
   * @param success true if they won this and false if not
   */
  public Wager(String wager, double amountWagered, Boolean success) {
    this.wager = wager;
    this.amountWagered = amountWagered;
    this.success = success;
    amountResult = 0;
  }

  /**
   * gets on what was wagered
   * @return String of what was wagered
   */
  public String getWager() {
    return wager;
  }

  /**
   * gets the amount wagered
   * @return a double of the amount wagered
   */
  public double getAmountWagered() {
    return amountWagered;
  }

  /**
   * gets the success Boolean
   * @return the Boolean value of success
   */
  public Boolean getSuccess() {
    return success;
  }

  /**
   * sets the success Boolean to true or false
   * @param success what to change the boolean to
   */
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  /**
   * returns the amount won or lost
   * @return the amount won or lost
   */
  public double getAmountResult() {
    return amountResult;
  }

  /**
   * sets the amount won or lost
   * @param amountResult a double to set the amount result to
   */
  public void setAmountResult(double amountResult) {
    this.amountResult = amountResult;
  }
} // end Bettor class
