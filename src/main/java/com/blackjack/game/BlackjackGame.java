/*
 * Name: Kirtan Manojkumar Patel
 * NetID: kpate452
 * 
 * Name: Dev KishorKumar Shah
 * NetID: dshah69
 * 
 * Project2: Blackjack Game
 */

package com.blackjack.game;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class BlackjackGame extends Application { // BlackjackGame class
    private ArrayList<Card> playerHand = new ArrayList<>();
    private ArrayList<Card> bankerHand = new ArrayList<>();
    private BlackjackDealer theDealer = new BlackjackDealer();
    private BlackjackGameLogic gameLogic = new BlackjackGameLogic();
    private double currentBet = 0;
    private double totalWinnings = 0;

    private Stage primaryStage;
    private Label playerHandLabel = new Label("Player's Hand: ");
    private Label bankerHandLabel = new Label("Banker's Hand: ");
    private Label totalMoneyLabel = new Label("Total Money: $0");
    private TextField betInput = new TextField();
    private Button placeBetButton = new Button("Place Bet");
    private Button hitButton = new Button("Hit");
    private Button standButton = new Button("Stand");
    private Button exitButton = new Button("Exit Game");
    private boolean roundComplete = false;

    private Label roundResultLabel = new Label("");
    private Label playerTotalLabel = new Label();
    private Label bankerTotalLabel = new Label();
    private Label deckSizeLabel = new Label();
    private HBox playerCardsHBox = new HBox(5);
    private HBox bankerCardsHBox = new HBox(5);

    // Method to start the game
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupInitialMoneyInputScreen(primaryStage);
    }

    // Method to setup initial money input screen
    private void setupInitialMoneyInputScreen(Stage primaryStage) {
        VBox initialMoneyLayout = new VBox(20); // Spacing between elements
        initialMoneyLayout.setAlignment(Pos.CENTER);
        initialMoneyLayout.setStyle("-fx-background-color: white; -fx-padding: 40;");

        Label titleLabel = new Label("BLACKJACK");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        Label welcomeMessage = new Label("WELCOME TO THE GAME OF BLACKJACK");
        welcomeMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e2e2e;");

        TextField moneyInput = new TextField();
        moneyInput.setPromptText("ENTER THE STARTING AMOUNT");
        moneyInput.setMaxWidth(300);
        moneyInput.setStyle(
                "-fx-font-size: 14px; -fx-prompt-text-fill: #757575; -fx-border-color: #757575; -fx-border-width: 2;");

        Button startGameButton = new Button("START!");
        startGameButton.setStyle(
                "-fx-background-radius: 15; -fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");

        startGameButton.setMinWidth(100);
        startGameButton.setMinHeight(40);
        startGameButton.setOnAction(e -> {
            try {
                totalWinnings = Double.parseDouble(moneyInput.getText());
                totalMoneyLabel.setText("Total Money: $" + totalWinnings); // Update the total money label
                showMainGameScreen(primaryStage);
            } catch (NumberFormatException ex) {
                moneyInput.setText("");
                moneyInput.setPromptText("Enter a valid number");
            }
        });

        Button exitGameButton = new Button("EXIT");
        exitGameButton.setStyle(
                "-fx-background-radius: 20; -fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
        exitGameButton.setMinWidth(100);
        exitGameButton.setMinHeight(40);
        exitGameButton.setOnAction(e -> {
            primaryStage.close(); // Close the application
        });

        startGameButton.setStyle(
                "-fx-background-radius: 20; -fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
        exitGameButton.setStyle(
                "-fx-background-radius: 20; -fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
        initialMoneyLayout.getChildren().addAll(titleLabel, welcomeMessage, moneyInput, startGameButton,
                exitGameButton);
        Scene scene = new Scene(initialMoneyLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Game");
        primaryStage.show();
    }

    // Method to create styled label
    private Label createStyledLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    // Method to create styled button
    private Button createStyledButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setMinWidth(80);
        return button;
    }

    // Method to style text field
    private void styleTextField(TextField textField, String style) {
        textField.setStyle(style);
        textField.setMinWidth(200);
    }

    // Method to show main game screen
    private void showMainGameScreen(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: black;");

        // Top
        Label titleLabel = createStyledLabel("BLACKJACK",
                "-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20, 0, 20, 0));
        borderPane.setTop(titleBox);

        // Left - Current Money and Bet
        VBox moneyAndBetLayout = new VBox(10);
        moneyAndBetLayout.setPadding(new Insets(10));
        Label currentMoneyLabel = createStyledLabel("Current Money Status", "-fx-text-fill: #ecf0f1;");
        totalMoneyLabel = createStyledLabel("Total Money: $" + totalWinnings, "-fx-text-fill: #2ecc71;");
        Label betLabel = createStyledLabel("This Round's Bet", "-fx-text-fill: #ecf0f1;");
        styleTextField(betInput, "-fx-font-size: 14px; -fx-prompt-text-fill: #bdc3c7; -fx-border-color: #bdc3c7;");
        placeBetButton = createStyledButton("Place Bet", "-fx-background-color: #27ae60; -fx-text-fill: #ecf0f1;");
        moneyAndBetLayout.getChildren().addAll(currentMoneyLabel, totalMoneyLabel, betLabel, betInput, placeBetButton);
        borderPane.setLeft(moneyAndBetLayout);

        // Initialize deck size label with current deck size
        deckSizeLabel.setText("Deck Size: " + theDealer.deckSize());
        deckSizeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Add deck size label under the "Place Bet" button
        moneyAndBetLayout.getChildren().add(deckSizeLabel);

        // Center - Cards and Actions
        VBox interactionLayout = new VBox(10);
        interactionLayout.setAlignment(Pos.CENTER);
        interactionLayout.setPadding(new Insets(10));

        playerCardsHBox = new HBox(10);
        playerCardsHBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        bankerCardsHBox = new HBox(10);
        bankerCardsHBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        playerHandLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        bankerHandLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        VBox bankerCardsLayout = new VBox(5, bankerHandLabel, bankerCardsHBox, bankerTotalLabel);
        bankerCardsLayout.setAlignment(Pos.CENTER);

        VBox playerCardsLayout = new VBox(5, playerHandLabel, playerCardsHBox, playerTotalLabel);
        playerCardsLayout.setAlignment(Pos.CENTER);

        interactionLayout.getChildren().addAll(bankerCardsLayout, playerCardsLayout);

        hitButton = createStyledButton("Hit", "-fx-background-color: #3498db; -fx-text-fill: #ecf0f1;");
        standButton = createStyledButton("Stand", "-fx-background-color: #e74c3c; -fx-text-fill: #ecf0f1;");
        hitButton.setDisable(true);
        standButton.setDisable(true);

        HBox actionButtonsLayout = new HBox(10, hitButton, standButton);
        actionButtonsLayout.setAlignment(Pos.CENTER);

        interactionLayout.getChildren().add(actionButtonsLayout);

        borderPane.setCenter(interactionLayout);

        // Bottom - Round Result and Exit Game button
        roundResultLabel.setStyle("-fx-text-fill: #ecf0f1; -fx-font-size: 18px;");
        HBox resultAndExitLayout = new HBox(10, roundResultLabel, exitButton);
        resultAndExitLayout.setAlignment(Pos.CENTER);
        resultAndExitLayout.setPadding(new Insets(10));
        borderPane.setBottom(resultAndExitLayout);

        // Final setup
        Scene scene = new Scene(borderPane, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack Game");
        setupButtonActions();
        primaryStage.show();
    }

    private void setupButtonActions() {
        hitButton.setOnAction(e -> handlePlayerHit());
        standButton.setOnAction(e -> handlePlayerStand());
        placeBetButton.setOnAction(e -> handlePlaceBet());

        exitButton.setDisable(false);
        exitButton.setOnAction(e -> primaryStage.close());
    }

    // Method to update deck size display
    public void updateDeckSizeDisplay() {
        deckSizeLabel.setText("Deck Size: " + theDealer.deckSize());
    }

    // Method to handle place bet
    private void handlePlaceBet() {
        try {
            double bet = Double.parseDouble(betInput.getText());
            roundResultLabel.setText("");
            if (bet > 0 && bet <= totalWinnings) {
                currentBet = bet;
                totalWinnings -= bet;
                totalMoneyLabel.setText("Total Money: $" + totalWinnings);

                hitButton.setDisable(false);
                standButton.setDisable(false);

                betInput.setDisable(true);
                placeBetButton.setDisable(true);
                startNewRound();
            } else {
                betInput.setText("Invalid bet");
            }
        } catch (NumberFormatException e) {
            betInput.setText("Please enter a valid input");
        }
    }

    // Method to start new round
    private void startNewRound() {
        roundComplete = false;
        roundResultLabel.setText("");
        if (currentBet <= 0)
            return;

        if (theDealer.deckSize() < 20) {
            theDealer.generateDeck();
            theDealer.shuffleDeck();

        }
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
        updateHandDisplays();
        hitButton.setDisable(false);
        standButton.setDisable(false);
        betInput.setDisable(true);
        placeBetButton.setDisable(true);
        updateDeckSizeDisplay();
    }

    // Method to handle player hit
    private void handlePlayerHit() {
        playerHand.add(theDealer.drawOne());
        updateHandDisplays();
        updateDeckSizeDisplay();
        if (gameLogic.handTotal(playerHand) > 21) {
            evaluateWinnings();
        }
    }

    // Method to handle player stand
    private void handlePlayerStand() {
        while (gameLogic.evaluateBankerDraw(bankerHand)) {
            bankerHand.add(theDealer.drawOne());
        }
        updateHandDisplays();
        updateDeckSizeDisplay();
        evaluateWinnings();
        roundComplete = true;
        updateGameUIAfterEvaluation();
    }

    // Method to update hand displays
    private void updateHandDisplays() {
        playerCardsHBox.getChildren().clear();
        bankerCardsHBox.getChildren().clear();

        for (Card card : playerHand) {
            StackPane cardPane = createCardPane(card.getSuit(), card.getRank());
            playerCardsHBox.getChildren().add(cardPane);
        }

        if (!roundComplete && !bankerHand.isEmpty()) {
            ImageView cardBackImageView = new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/card_back.png"))));
            bankerCardsHBox.getChildren().add(cardBackImageView);

            for (int i = 1; i < bankerHand.size(); i++) {
                Card card = bankerHand.get(i);
                StackPane cardPane = createCardPane(card.getSuit(), card.getRank());
                bankerCardsHBox.getChildren().add(cardPane);
            }
        } else {
            for (Card card : bankerHand) {
                StackPane cardPane = createCardPane(card.getSuit(), card.getRank());
                bankerCardsHBox.getChildren().add(cardPane);
            }
        }

        updateTotalDisplay(playerTotalLabel, gameLogic.handTotal(playerHand), playerCardsHBox);
        updateTotalDisplay(bankerTotalLabel, roundComplete ? gameLogic.handTotal(bankerHand) : null, bankerCardsHBox);
    }

    // Method to update total display
    private void updateTotalDisplay(Label totalLabel, Integer total, HBox cardsBox) {

        StackPane totalPane = new StackPane();
        Label totalTextLabel = new Label("Total");
        totalTextLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: White; -fx-font-weight: bold;");
        Circle totalCircle = new Circle(30, Color.YELLOW);

        if (total != null) {
            String color = total > 21 ? "red" : (total == 21 ? "green" : "black");
            totalLabel.setText(total.toString());
            totalLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: " + color + "; -fx-font-weight: bold;");
            totalPane.getChildren().addAll(totalCircle, totalLabel);
            totalPane.setAlignment(Pos.CENTER_RIGHT);
            StackPane.setAlignment(totalLabel, Pos.CENTER);
        } else {
            totalLabel.setText("?");
            totalLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold;");
            totalPane.getChildren().addAll(totalCircle, totalLabel);
            totalPane.setAlignment(Pos.CENTER_RIGHT);
            StackPane.setAlignment(totalLabel, Pos.CENTER);
        }

        HBox totalDisplayBox = new HBox(totalTextLabel, totalPane);
        totalDisplayBox.setAlignment(Pos.CENTER_RIGHT);
        totalDisplayBox.setSpacing(5);

        cardsBox.getChildren().add(totalDisplayBox);

        HBox.setHgrow(cardsBox, Priority.ALWAYS);
    }

    // Method to create card pane
    private StackPane createCardPane(String suit, String rank) {
        StackPane cardPane = new StackPane();
        cardPane.setStyle("-fx-background-color: white; -fx-border-color: black;");
        cardPane.setPrefSize(225, 320);

        String imagePath = "/" + suit + ".png";
        InputStream suitStream = getClass().getResourceAsStream(imagePath);
        if (suitStream == null) {
            System.out.println("Cannot find image at path: " + imagePath);
            return cardPane;
        }

        double suitImageSize = 40;
        double rankLabelFontSize = 40;

        ImageView suitImageView = new ImageView(new Image(suitStream));
        suitImageView.setFitHeight(suitImageSize);
        suitImageView.setFitWidth(suitImageSize);

        Label rankLabel = new Label(rank);
        rankLabel.setStyle(String.format("-fx-font-size: %.0fpx; -fx-text-fill: black;", rankLabelFontSize));

        StackPane.setAlignment(rankLabel, Pos.TOP_LEFT);
        StackPane.setMargin(rankLabel, new Insets(3));
        StackPane.setAlignment(suitImageView, Pos.TOP_LEFT);
        StackPane.setMargin(suitImageView, new Insets(18, 0, 0, 3));
        StackPane.setAlignment(suitImageView, Pos.CENTER);

        ImageView suitImageViewBottom = new ImageView(new Image(suitStream));
        suitImageViewBottom.setFitHeight(suitImageSize);
        suitImageViewBottom.setFitWidth(suitImageSize);
        suitImageViewBottom.setRotate(180);

        Label rankLabelBottom = new Label(rank);
        rankLabelBottom.setStyle(String.format("-fx-font-size: %.0fpx; -fx-text-fill: black;", rankLabelFontSize));
        rankLabelBottom.setRotate(180);

        StackPane.setAlignment(rankLabelBottom, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(rankLabelBottom, new Insets(0, 3, 18, 0));
        StackPane.setAlignment(suitImageViewBottom, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(suitImageViewBottom, new Insets(0, 3, 3, 0));

        cardPane.getChildren().addAll(rankLabel, suitImageView, rankLabelBottom, suitImageViewBottom);

        return cardPane;
    }

    // Method to evaluate winnings
    public void evaluateWinnings() {
        String result = gameLogic.whoWon(playerHand, bankerHand);
        switch (result) {
            case "Player Wins!!":
                totalWinnings += currentBet * 2;
                roundResultLabel.setText("Player Wins!!");
                break;
            case "Player Wins with Blackjack!!":
                totalWinnings += currentBet * 2.5;
                roundResultLabel.setText("Player wins with Blackjack!!");
                break;
            case "Dealer Wins!!":
                roundResultLabel.setText("Dealer wins!!");
                break;
            case "Dealer Wins with Blackjack!!":
                roundResultLabel.setText("Dealer wins with Blackjack!!");
                break;
            case "Push!!":
                totalWinnings += currentBet;
                roundResultLabel.setText("Push");
                break;
        }
        roundComplete = true;
        updateHandDisplays();
        updateGameUIAfterEvaluation();
    }

    // Method to reset game state
    private void resetGameState() {
        playerHand.clear();
        bankerHand.clear();
        currentBet = 0;
        totalWinnings = 0;

        playerTotalLabel.setText("");
        bankerTotalLabel.setText("");
        deckSizeLabel.setText("Deck Size: ");
        hitButton.setDisable(true);
        standButton.setDisable(true);
        placeBetButton.setDisable(false);
        betInput.setDisable(false);
        betInput.clear();
        totalMoneyLabel.setText("Total Money: $0");

        theDealer.generateDeck();
        theDealer.shuffleDeck();

        playerCardsHBox.getChildren().clear();
        bankerCardsHBox.getChildren().clear();
    }

    // Method to update game UI after evaluation
    private void updateGameUIAfterEvaluation() {
        totalMoneyLabel.setText("Total Money: $" + totalWinnings);
        betInput.clear();

        if (totalWinnings <= 0) {

            betInput.setDisable(true);
            placeBetButton.setDisable(true);
            hitButton.setDisable(true);
            standButton.setDisable(true);
            roundResultLabel.setText("Out of money! Returning to start screen in 5 seconds...");

            PauseTransition pause = new PauseTransition(Duration.seconds(5));

            pause.setOnFinished(e -> {
                resetGameState();
                setupInitialMoneyInputScreen(primaryStage);
                roundResultLabel.setText("");
            });

            pause.play();
        } else {
            betInput.setDisable(false);
            placeBetButton.setDisable(false);
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}