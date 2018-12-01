# Scrabble
## Deliverables
### Main & MainController classes
- Input bar (array of clickable letters)
### Board class
- Scrabble board functionalities
- Determines the location of bonus Squares, and their type (double-/triple-, -letter/-word)
### LetterBag class
- Creates the filled bag of Letters (allows for random picking)
- Assigns a value to each letter according to a HashMap

## Demo (Client-Server)
- Each player gets a Scrabble GUI when game starts
- For each player, a turn contains the following steps:
  - Create a word from available letters in hand (or already on board) by clicking OR Skip turn
  - When word input is confirmed and it exists in the dictionary, word is validated and placed on the board
  - Player gets additional points according to letters placed and bonus squares on which they were placed, if applicable
  - Turn ends
- Each player gets 5 turns to try and make the most points


# Project Demo 
## Getting Started 
- Download the Scrabble game from github 
- Open the files in eclipse and run the program 
- Run the Server then the 2 clients 
- Play the game 

## Features 
- Allows a player to create a word and checks it in the dictionary 
- Client server connection 
- Addtional options allow the player to skip, exchange letter, and exchange word
- Keeps track of the players score 

## Demo 
https://youtu.be/9mkHglJ7iT0  
https://youtu.be/YtaRuQlXrA0 (network interaction)
