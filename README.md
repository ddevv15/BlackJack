
# 🃏 Blackjack Game

A JavaFX-based Blackjack game implemented in Java with Maven. The game features a graphical user interface, betting system, deck management, and complete Blackjack rules.

---

## 🎯 Features

- 🃏 Standard 52-card deck with shuffling
- ✋ Deal, Hit, Stand actions
- 💵 Betting system with win/loss/push logic
- 🂱 Blackjack & bust detection
- 🏦 Dealer logic (hits until 16, stands on 17+ including soft 17)
- 🎨 Interactive JavaFX user interface
- 📊 Display of deck size, totals, and results
- 🧪 Comprehensive JUnit tests

---

## 🛠️ Tech Stack

- **Language:** Java 11  
- **Build Tool:** Maven  
- **GUI:** JavaFX (`javafx-controls`, `javafx-fxml`)  
- **Testing:** JUnit 5  

---

## 📂 Project Structure

ddevv15-blackjack/
├── README.md
├── pom.xml
└── src/
├── main/
│ └── java/
│ └── com/
│ └── blackjack/
│ └── game/
│ ├── BlackjackDealer.java
│ ├── BlackjackGame.java
│ ├── BlackjackGameLogic.java
│ └── Card.java
└── test/
└── java/
└── com/
└── blackjack/
└── game/
└── BlackjackGameTest.java

---

## ⚙️ Setup & Run

### Prerequisites
- Java 11+  
- Maven 3.6+  

### Run the Game
```bash
# Compile and run with JavaFX Maven plugin
mvn clean javafx:run
Run Tests
bash
Copy
Edit
mvn test
🎮 Gameplay Overview
Enter starting money.

Place a bet for each round.

Hit or Stand to play your hand.

Dealer follows Blackjack rules (hits ≤16, stands ≥17).

Winnings update after each round:

Player Blackjack → 2.5× payout

Player win → 2× payout

Push → bet returned

Dealer win → bet lost

If money runs out, the game resets.

🧩 Classes Overview
Card – Represents a single playing card (suit, rank, value).

BlackjackDealer – Manages the deck, shuffling, dealing cards.

BlackjackGameLogic – Contains core game rules & win evaluation.

BlackjackGame – JavaFX GUI, handles bets, rounds, and gameplay flow.

BlackjackGameTest – JUnit tests for deck, logic, and outcomes.

🩺 Troubleshooting
JavaFX not found → Ensure JavaFX is installed and available to Maven.

UI not displaying → Run with the Maven javafx:run goal (not plain mvn exec:java).

Deck regeneration → Dealer auto-regenerates deck when empty.

📜 License
MIT License © 2025
Authors: Dev KishorKumar Shah & Kirtan Manojkumar Patel

