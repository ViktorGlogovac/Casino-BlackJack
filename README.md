# BlackJack (Twenty-One)

## About the Project

The software will emulate the card game Blackjack, enabling the player to compete against a 
randomly-generated card dealer. The game will adhere to the fundamental [rules](https://bicyclecards.com/how-to-play/blackjack/) 
of the casino-style Blackjack (also known as Twenty-One). The objective for the player in the game is to outscore the 
dealer by drawing cards whose total sum is as close to 21 as possible.

<ins>Gameplay:</ins>
* The user will be prompted to make a profile (username,password,deposit money, etc.)
* Before each round, the user will specify the amount of money they wish to wager.
* The application will handle dealing cards, and calculating scores
* The software will monitor the user's funds and provide updates on whether they have won or lost.
* The application also features game saving and loading functionality, enabling the user to resume their game from the point of interruption.


## Who will use it?

This application could be used by anyone who enjoys card games, wants to practice Blackjack, or is looking 
for a fun way to pass the time. It could be particularly interesting for people learning to play Blackjack, 
as it will allow them to play the game at their own pace and without the pressure of playing with real money.

## Why is this project of interest to you?

This project is of interest to me because I enjoy card games and I am excited about the opportunity to recreate one 
in a computer program. I have always had fun playing poker or blackjack with my friend, therefore I believe I would 
enjoy creating this game while also improving my programing skills. 

## User Stories:

### <ins>Phase 0:</ins>
* As a user, I want to be able to add an account to the game (username, password, etc.)
* As a user, I want to be able to deposit money to my wallet.
* As a user, I want to be able to place a bet of my choice before each hand so that I can decide how much I'm willing to risk.
* As a user, I want to be dealt cards and see their values so that I can make decisions based on my hand.
* As a user, I want to be able to 'Hit' or 'Stand' during my turn so that I can control the progression of the game.
* As a user, I want to see the dealer's moves and cards so that I can know the state of the game.
* As a user, I want to know immediately if I've won or lost the hand, and see my updated money total so I can keep track of my progress.


### <ins>Phase 2:</ins>
* As a user, I want to be able to save my account to the file (If I so choose)
* As a user, I want to be able to load my account from the file (If I so choose)


## Phase 4: Task 2
### <ins>Example Of Event Log In Console<ins>
Thu Aug 10 01:59:13 PDT 2023
User: user1 created.

Thu Aug 10 01:59:18 PDT 2023
$1000.0 deposited.

Thu Aug 10 01:59:22 PDT 2023
Loss added.

Thu Aug 10 01:59:22 PDT 2023
$100 withdrawn.

Thu Aug 10 01:59:31 PDT 2023
$100.0 deposited.

Thu Aug 10 01:59:31 PDT 2023
Win added.

Thu Aug 10 01:59:37 PDT 2023
Loss added.

Thu Aug 10 01:59:37 PDT 2023
$500 withdrawn.

## Phase 4: Task 3
  The uml diagram for this project describes the relationships between the methods in the code. Starting off with the classes in the model 
package we notice the Account and AccountManager classes both implement the Writable interface. These is used so that the Accounts are stored 
onto the Json file. We also notice both Account and AccountManager have dependencies to Event and EventLog, which is used to store the events 
such as depositing and withdrawing money. AccountManager contains multiple accounts, therefore it has an aggression which is a specialized form 
of association with Account. Similarly, Deck has multiple cards causing it to be an association to Cards class. Moving to the Ui package we 
noticed AccountScreen Ui having an association with Json Writer and Reader, which is used to write and read to/from the file. It implements 
the ActionListener interface to interact with the buttons and extends the JFrame for the gui components. It as well has an association for 
accountManager and Account so that it can keep track of the accounts in the casino and keep track of the current account logged in. Similarly, 
the GameUi also implements the ActionListener and extends the JFrame for the same reasons. The GameUi also keeps track of the current account 
therefore it has an association with the Account class.

  If I had to do this project again I would organize my code slightly differently. Firstly, I noticed that I would have been cleaner to make my 
accountManager contain a set of account rather than an ArrayList since a set would not contain duplicates and using an array I would have to write 
extra code to achieve the same outcome. Secondly, I would separate my gui and ui components from my game or accountScreen navigator. Doing so I would 
have cleaner code making it easier for debugging. Finally, I could implement interfaces or abstract classes for screen which both the gameUI and 
accountScreenUi might use therefore limiting the repeated code. 



