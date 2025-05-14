/**
 * @author Gabriel Sullivan
 * Major: Computer Science
 * Creation date: 5/8/25
 * Due date: 5/16/25
 * Course: CPSC243
 * Professor: Mr. Nye
 * Assignment: #6
 * Filename: BaccaratApp.java
 * Purpose: This file contains the GUI for the Baccarat game.
 * Card files from: https://cazwolf.itch.io/pixel-fantasy-cards
 */

package baccarat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * the class to handle the UI
 */
public class BaccaratApp extends Application {
  /**
   * the game object
   */
  private BaccaratCasinoGame game;
  
  /**
   * the various text objects that will need to be updated
   */
  private Text playerText, bankerText, playerValueText, bankerValueText, balanceText, winningsText, wagerText, messageText;
  
  /**
   * the various button objects that will need to be updated
   */
  private Button nextRoundButton, doneButton, tieBetButton, playerBetButton, bankerBetButton, playerPairBetButton, bankerPairBetButton, loadLastButton;
  
  /**
   * an array containing the visible card images
   */
  private ArrayList<ImageView> playerVisualCards = new ArrayList<>();
  private ArrayList<ImageView> bankerVisualCards = new ArrayList<>();
  
  /**
   * the popup to handle entering the amount to bet
   */
  private StackPane overlay;
  
  /**
   * the current bet to be submitted
   */
  private String currentBet;
  
  /**
   * the amount input field for betting
   */
  private TextField numField = new TextField();

  /**
   * renders the header
   * @return VBox the header pane
   */
  private VBox renderHeader() {
    // render title
    Text title = new Text("Baccarat");
    title.setFont(Font.font("Liberation Mono", 36));
    title.setFill(Color.web("#FEFAE0"));
    
    // display message
    messageText = new Text(400, 140, "Press begin to start the round.");
    messageText.setFont(Font.font("Liberation Mono", 20));
    messageText.setFill(Color.web("#FEFAE0"));
    
    VBox top = new VBox(10, title, messageText);
    
    top.setPadding(new Insets(20));
    top.setAlignment(Pos.CENTER);
    top.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    
    return top;
  }

  /**
   * renders the table
   * @return StackPane the table pane
   */
  private StackPane renderTable() {
    // render table
    Rectangle tableShape = new Rectangle(1000, 530);
    tableShape.setFill(Color.web("#1B512D"));
    tableShape.setStroke(Color.web("#BC6C25"));
    // round the corners
    tableShape.setArcWidth(10);
    tableShape.setArcHeight(10);
    tableShape.setStrokeWidth(15);
    
    playerText = new Text();
    bankerText = new Text();
    playerValueText = new Text();
    bankerValueText = new Text();
    
    HBox playerDetails = new HBox(renderHand("Player", playerText, playerVisualCards, playerValueText), renderHand("Banker", bankerText, bankerVisualCards, bankerValueText));
    playerDetails.setAlignment(Pos.CENTER);
    
    // add spots to select bet later
    tieBetButton = new Button("Tie");
    tieBetButton.setOnAction(e -> {
      getBet("tie");
    });
    tieBetButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 2px 0px 2px;");
    tieBetButton.setPrefWidth(200);
    
    playerBetButton = new Button("Player");
    playerBetButton.setOnAction(e -> {
      getBet("player");
    });
    playerBetButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 2px 0px 2px;");
    playerBetButton.setPrefWidth(200);
    
    bankerBetButton = new Button("Banker");
    bankerBetButton.setOnAction(e -> {
      getBet("banker");
    });
    bankerBetButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 2px 0px 2px;");
    bankerBetButton.setPrefWidth(200);
    
    playerPairBetButton = new Button("Player Pair");
    playerPairBetButton.setOnAction(e -> {
      getBet("ppair");
    });
    playerPairBetButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 2px 0px 2px;");
    playerPairBetButton.setPrefWidth(200);
    
    bankerPairBetButton = new Button("Banker Pair");
    bankerPairBetButton.setOnAction(e -> {
      getBet("bpair");
    });
    bankerPairBetButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px 2px 0px 2px;");
    bankerPairBetButton.setPrefWidth(200);

    loadLastButton = new Button("Load Last Bets");
    loadLastButton.setOnAction(e -> {
      try {
        game.submitLastWagers();
      } catch(BaccaratException ex) {
        messageText.setText(ex.getMessage());
      }
      
      startRound();
    });
    loadLastButton.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 2px;");
    loadLastButton.setPrefWidth(200);
    loadLastButton.setDisable(true);
    
    // center betting area made of buttons
    VBox bettingAreaBox = new VBox(20, tieBetButton, playerBetButton, bankerBetButton, playerPairBetButton, bankerPairBetButton, loadLastButton);
    bettingAreaBox.setAlignment(Pos.CENTER);
    bettingAreaBox.setSpacing(0);
    
    VBox onTable = new VBox(playerDetails, bettingAreaBox);
    onTable.setPadding(new Insets(20));
    
    StackPane center = new StackPane(tableShape, onTable);
    center.setPadding(new Insets(20));
    center.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    return center;
  }
  
  /**
   * renders a hand's details
   * @param playerName a string of the player name
   * @param visualCards the cards to display
   * @return VBox the banker details
   */
  private VBox renderHand(String playerName, Text genericPlayerText, ArrayList<ImageView> visualCards, Text valueText) {
    genericPlayerText.setText(playerName);
    genericPlayerText.setFont(Font.font("Liberation Mono", 24));
    genericPlayerText.setFill(Color.web("#498AB5"));
    
    HBox cardsHolder = new HBox(); // the place for the cards to go
    cardsHolder.setSpacing(10);
    
    // initialize where the cards will be for the player
    for(int i = 0; i < 3; i++) {
      Image cardImage = new Image(getClass().getResourceAsStream("/images/playing_cards/card-back1.png"));
      ImageView card = new ImageView(cardImage);
      card.setFitWidth(96);
      card.setFitHeight(144);
      card.setVisible(false);
      visualCards.add(card);
      cardsHolder.getChildren().addAll(card);
    }
    
    valueText.setText("Value: 0");
    valueText.setFont(Font.font("Liberation Mono", 18));
    valueText.setFill(Color.web("#498AB5"));

    VBox box = new VBox(10, genericPlayerText, cardsHolder, valueText);
    box.setPadding(new Insets(20));
    box.setAlignment(Pos.TOP_CENTER);
    
    return box;
  }
  
  /**
   * renders HUD
   * @returns HBox the bottom menu
   */
  private HBox renderMenu() {
    balanceText = new Text("Balance: $0.00");
    balanceText.setFont(Font.font("Liberation Mono", 15));
    balanceText.setFill(Color.web("#FEFAE0"));

    winningsText = new Text("Winnings: $0.00");
    winningsText.setFont(Font.font("Liberation Mono", 15));
    winningsText.setFill(Color.web("#FEFAE0"));

    wagerText = new Text("Wager: $0.00");
    wagerText.setFont(Font.font("Liberation Mono", 15));
    wagerText.setFill(Color.web("#FEFAE0"));
    
    // the left side of the bottom menu
    VBox userDetails = new VBox(10, balanceText, winningsText, wagerText);
    userDetails.setAlignment(Pos.CENTER);
    
    nextRoundButton = new Button("Begin");
    nextRoundButton.setOnAction(e -> {
      game.nextRound();
      updateHUD();
      messageText.setText("Round: " + game.getRoundNumber());
      
      deactivateAllButtons(); // deactivate all buttons
      hideCards();
      
      // make a pause
      messageText.setText("Place your bets.");
      nextRoundButton.setDisable(true); // disable the next round button
      nextRoundButton.setVisible(false); // hide the next round button
      doneButton.setDisable(false); // disable the next round button
      doneButton.setVisible(true); // hide the next round button
      activateBetButtons();
    });
    
    doneButton = new Button("Done");
    doneButton.setDisable(true); // disable the done button
    doneButton.setVisible(false); // hide the done button
    
    doneButton.setOnAction(e2 -> {
      startRound();
    });
    
    deactivateAllButtons(); // deactivate all the buttons
    nextRoundButton.setDisable(false); // activate the begin buttons
    
    // right side with continue buttons
    HBox buttonBox = new HBox(10, nextRoundButton, doneButton);
    buttonBox.setAlignment(Pos.CENTER);

    // create the horizontal box containing the objects
    HBox bottom = new HBox(10, userDetails, buttonBox);
    bottom.setPadding(new Insets(20));
    bottom.setAlignment(Pos.CENTER);
    bottom.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    return bottom;
  }
  
  private StackPane renderOverlay() {
    Rectangle background = new Rectangle(800, 800);
    background.setFill(Color.WHITE);
    
    Text header = new Text("Enter amount to bet:");
    
    numField.setPromptText("Enter an amount."); // set the prompt
    numField.setMaxWidth(200);
    
    Button submitButton = new Button("Submit Wager"); // submit button
    submitButton.setOnAction(e -> {
      // make validate input as a double
      try {
        game.submitWager(currentBet, Double.parseDouble(numField.getText())); // submits the bet
      } catch (BaccaratException ex) {
        messageText.setText(ex.getMessage());
      }
      // hides the overlay
      overlay.setVisible(false);
      overlay.setManaged(false);
      
      // enable the buttons
      activateBetButtons();
      doneButton.setDisable(false);
      nextRoundButton.setDisable(false);
      updateHUD(); // update te HUD
    });
    
    Button cancelButton = new Button("Cancel"); // cancel button
    cancelButton.setOnAction(e -> {
      // hides the overlay
      overlay.setVisible(false);
      overlay.setManaged(false);
      
      // enable the buttons
      activateBetButtons();
      doneButton.setDisable(false);
      nextRoundButton.setDisable(false);
    });
    
    VBox details = new VBox(header, numField, submitButton, cancelButton);
    details.setAlignment(Pos.CENTER);
    details.setSpacing(10);
    
    overlay = new StackPane(background, details);
    overlay.setVisible(false);
    overlay.setManaged(false);
    return overlay;
  }
  
  /**
   * begins the round, deals out the cards
   */
  private void startRound() {
    doneButton.setDisable(true); // disable the done button
    doneButton.setVisible(false); // hide the done button
    deactivateAllButtons(); // deactivate all buttons
    game.dealCards();
    updateHUD();
    messageText.setText("Initial cards dealt.");
    PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
    pause1.setOnFinished(e1 -> {
      doRound();
    });
    pause1.play();
  }
  
  /**
   * plays through the round
   */
  private void doRound() {
    int playerValue = game.getPlayer().getValue();
    int bankerValue = game.getBanker().getValue();
    
    // check if the player value is an 8 or 9
    if(playerValue == 8 || playerValue == 9) {
      if(bankerValue == 8 || bankerValue == 9) {
        messageText.setText("Player hand has a value of " + playerValue + " and banker hand has a value of " + bankerValue + ". No more cards will be dealt.");
      } else {
        messageText.setText("Player hand has a value of " + playerValue + ".");
      }
      handleRoundEnd();
      return;
    } else if(bankerValue == 8 || bankerValue == 9) {
      if(playerValue == 8 || playerValue == 9) {
        messageText.setText("Player hand has a value of " + playerValue + " and banker hand has a value of " + bankerValue + ". No more cards will be dealt.");
      } else {
        messageText.setText("Banker hand has a value of " + bankerValue + ".");
      }
      handleRoundEnd();
      return;
    }
      
    // otherwise play game
    messageText.setText("Player " + game.doPlayersTurn() + ".");
    updateHUD();

    PauseTransition pause3 = new PauseTransition(Duration.seconds(2));
    pause3.setOnFinished(e3 -> {
      messageText.setText("Banker " + game.doBankersTurn() + ".");
      updateHUD();
      
      handleRoundEnd();
      return;
    });
    pause3.play();
  }
  
  /**
   * does the conclusion of the round
   */
  private void handleRoundEnd() {
    // wait and display win message
    PauseTransition pause4 = new PauseTransition(Duration.seconds(2));
    pause4.setOnFinished(e4 -> {
      
      String result = game.calculateWinner();
      if(result.equals("player")) {
        messageText.setText("Player wins!");
      } else if(result.equals("banker")) {
        messageText.setText("Banker wins!");
      } else if(result.equals("tie")) {
        messageText.setText("It was a tie!");
      }
      
      PauseTransition pause5 = new PauseTransition(Duration.seconds(2));
      pause5.setOnFinished(e5 -> {
        try {
          game.collectWinnings();
        } catch(BaccaratException ex) {
          messageText.setText(ex.getMessage());
        }
        updateHUD();
        messageText.setText("Round winnings: $" + String.format("%.2f", game.getRoundWinnings()));
        
        PauseTransition pause6 = new PauseTransition(Duration.seconds(2));
        pause6.setOnFinished(e6 -> {
          messageText.setText("Click next round to continue.");
          nextRoundButton.setDisable(false); // enable the next round button
          nextRoundButton.setVisible(true); // unhide the next round button
          nextRoundButton.setText("Next round");
        });
        pause6.play();
      });
      pause5.play();
    });
    pause4.play();
  }
  
  /**
   * updates the HUD
   */
  private void updateHUD() {
    // add player and banker values
    playerValueText.setText("Value: " + game.getPlayer().getValue());
    bankerValueText.setText("Value: " + game.getBanker().getValue());
    balanceText.setText("Balance: $" + String.format("%.2f", game.getBettorBal()));
    winningsText.setText("Winnings: $" + String.format("%.2f", game.getRoundWinnings()));
    wagerText.setText("Wager: $" + String.format("%.2f", game.getBettor().getAmountWagered()));
    
    // render player cards
    showCards(playerVisualCards, game.getPlayer());
    // render banker cards
    showCards(bankerVisualCards, game.getBanker());
    
    // hide the load last round bets if not round 2
    if(game.getRoundNumber() >= 2) {
      
    } else {
      loadLastButton.setDisable(true);
    }
  }
  
  /**
   * shows the cards
   * @param visualCards the card spaces to be shown
   * @param hand the hand to get the data from
   */
  private void showCards(ArrayList<ImageView> visualCards, BaccaratHand hand) {
    for(int i = 0; i < 3; i++) {
      ImageView card = visualCards.get(i);
      if(i < hand.getCardCount()) {
        Card cardDetails = new Card(0);
        
        try {
          cardDetails = hand.getCard(i);
        } catch(BaccaratException e) {
          messageText.setText(e.getMessage());
        }
        // get the suit as a string
        String lowercaseSuit = cardDetails.getSuit().name().toLowerCase();
        // get the rank as an int
        int rankNum = cardDetails.getRank().ordinal() + 1;
        Image cardImage = new Image(getClass().getResourceAsStream("/images/playing_cards/card-" + lowercaseSuit + "-" + rankNum + ".png"));
        card.setImage(cardImage);
        card.setVisible(true);
      } else {
        card.setVisible(false);
      }
    }
  }
  
  /**
   * hides all the cards
   */
  private void hideCards() {
    for(int i = 0; i < 3; i++) {
      ImageView playerCard = playerVisualCards.get(i);
      playerCard.setVisible(false);
      ImageView bankerCard = bankerVisualCards.get(i);
      bankerCard.setVisible(false);
    }
  }

  /**
   * deactivates all the buttons
   */
  private void deactivateAllButtons() {
    nextRoundButton.setDisable(true);
    doneButton.setDisable(true);
    tieBetButton.setDisable(true);
    playerBetButton.setDisable(true);
    bankerBetButton.setDisable(true);
    playerPairBetButton.setDisable(true);
    bankerPairBetButton.setDisable(true);
    loadLastButton.setDisable(true);
  }

  /**
   * activates the bet buttons
   */
  private void activateBetButtons() {
    tieBetButton.setDisable(false);
    playerBetButton.setDisable(false);
    bankerBetButton.setDisable(false);
    playerPairBetButton.setDisable(false);
    bankerPairBetButton.setDisable(false);
    // hide the load last round bets if not round 2
    if(game.getRoundNumber() >= 2) {
      loadLastButton.setDisable(false);
    } else {
      loadLastButton.setDisable(true);
    }
  }

  /**
   * gets the bet amount
   */
  private void getBet(String betCode) {
    deactivateAllButtons();
    overlay.setVisible(true);
    overlay.setManaged(true);
    currentBet = betCode;
    numField.clear(); // clear the number field
  }
  
  // when the button "done" is pressed, do the round
  
  // make display of cards? improve UI?

  @Override
  public void start(Stage stage) {
    StackPane root = new StackPane();
    root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    Scene scene = new Scene(root, 1600, 900);
    stage.setScene(scene);
    stage.setTitle("Baccarat");
    stage.show();
    int numDecks = 4;
    int maxNumPerDeck = 26;
  
    game = new BaccaratCasinoGame(numDecks, maxNumPerDeck, 1000); // create game
    
    VBox mainScreen = new VBox();
    mainScreen.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

    mainScreen.getChildren().addAll(renderHeader(), renderTable(), renderMenu());
    root.getChildren().addAll(mainScreen, renderOverlay());
    updateHUD();
  }
}
