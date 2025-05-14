/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: BaccaratException.java
 * Purpose: This file contains the BaccaratException class which handles the Baccarat-based exceptions.
 *          It inherits from the standard Exception class
 */

package baccarat;

/**
 * handles the exceptions
 */
public class BaccaratException extends Exception {
  /**
   * constructor
   * @param message the message of the exception
   */
  public BaccaratException(String message) {
    super(message); // for the default Exception class
  } // end constructor
} // end BaccaratException class
