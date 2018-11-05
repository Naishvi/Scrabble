# Scrabble

### **Description**
Scrabble is a multiplayer game that allows the players to create words from an array of letters
they got randomly from a bag. The number of points is determined by the number assigned to
each letter, as well as by bonus boxes on the playing board. JAVA will be our programming
language.

### **Deliverables:**
- Inviting Scrabble interface
- All regular Scrabble game rules
- Picking letters from a bag of letters
- Placing the letters on a playing board

### **Plan**
 **October 22nd:** Fully understanding the flow of a Scrabble game
    - Understanding all the rules of Scrabble
    - Thinking of how to implement each step and component of the game
    - Beginning pseudocode and game layout
### **November 5th:** Completing pseudocode and mapping out the layout of the game
    - Determining and constructing the methods and classes we would need for the implementation of the game and interface layout
     -Understanding how to create a connection between two players and updating the game
### **November 12th**
    - Beginning to program the game’s several per-player steps
    - Using a Bag ADT to get random letters
    - Designing how a player will enter a word and check if the word is in the dictionary
    - Implementing a score field and relevant methods to keep track of both players’ score
### **November 19th:** Continuing to develop the game and beginning testing
    - Finishing up all steps and fixing errors spotted in the program (debugging tool)
    - Looping the game for a two-player party
    - Trying to spot all error-prone contingencies (problematic user input)
### **December 7th:** 
    - Adding any final touch and create a Powerpoint for the final presentation

### **Members**
Naishvi and Mathieu


# Progress Updates (Oct 22 - Nov 30)

## Week 1 (Oct 22 - Oct 27)

### What did your team do last week?
* Gathered information about the classic Scrabble game
  * Number of each type of letters
  * Number of points assigned to each letter
  * Position of the bonus squares on the board
* Created and started considering how to use a dictionary file, containing words in a list format
* Established the functioning of points’ assignment to letters
  * Any letter picked from the bag will be assigned a point once it is picked from the bag
* Started implementing a bag object, using hashmaps to associate letters with numbers (number of points each letter is worth)


### What will you do this week?
* Focus on the creating the GUI
  * Creating a board and letter tiles
* Implementing user input and checking if the word exist in the dictonary 
### Naishvi
* Responsible for checking if the user's word is in the dictionary 
* Determining the point value of the users input
### Mathieu
* Responsible for creating the tiles and board using the JAVAFX
* Determining if user placement of tiles is valid and whether points or word need to be double/tripled

### Any blocking issues/challenges need to be addressed?
* Linking the tile letter with the word created in the letters classes 
## Week 2 (Nov 5 - Nov 12)

### What did your team do last week?
* Together: 
  * Work on improving the layout for the code 
  * Determine if the word from the user input is valid 
  * Test the method that we created to see their performance 
* Naishvi: 
  * Worked on getting the point value from a Hashmap
  * Starting to create methods that will triple/double letter value and word value
* Mathieu
  * Made the group consider the possibility of using enumerated types (“enums”) for Letters (Hashmaps were eventually chosen as the method to be used)
  * Checked redundancy and code format

### What will you do this week?
* Together: 
  * Continue building the Interface of the game
* Naishvi: 
  * Determining when the game will end
  * Finishing the triple/double letter value and word value

* Mathieu: 
  * Designing the visual aspect of each card and making each have a separate image file

### Any blocking issues/challenges need to be addressed?
* Linking the tiles interface to the letter in the Hashmap

