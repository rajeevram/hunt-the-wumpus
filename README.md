# Hunt The Wumpus
A graphical reproduction of the game "Hunt The Wumpus" developed by Texas Instruments in 1972.

Created By: Rajeev Ram (2018)

##  Overview
"Hunt The Wumpus" was originally a console based game. 
Move the hunter around the dungeon in NESW directions. 

Avoid the slime pits and make sure not to run into the Wumpus.
When you find blood, the Wumpus is near.
Blood + Slime = Goop – extra dangerous

Shoot an arrow to slay him, but be careful! If you shoot in the
wrong the direction the arrow will pierce you in the back!

This rendition of the game includes both the console version (ConsoleMain) and a graphical version (WumpusMain)
with two views – text and image.

<img src='https://imgur.com/IDi76P7.gif' title='Wumpus GUI Walkthrough' width='' alt='Hunt The Wumpus' />

## Tasks (User Stories)
Console Game:

* [X] user can see the map printed to stdout after every move
* [X] user ('O') starts in a random safe location every new game 
* [X] 3-5 slime pits are placed into randomly generated locations every new game 
* [X] wumpus location is randomly determined every game
* [X] user can move through the map using the N,E,S,W keys
* [X] user can track unvisited (X) or visited (empty) squares
* [X] user can walk through edges to the other side of the map
* [X] user sees warning messages printed to stdout: (S)lime, (B)lood, (G)oop
* [X] user can decide to shoot an arrow; or decide to cancel 
* [X] walking into the (W)umpus or (P)it triggers game over – loss condition
* [X] firing the arrow in the wrong direction triggers game over – loss condition
* [X] firing the arrow in the right direction triggers game end – win condition
* [X] user can choose the play a new game after finished

Graphical Game:

* [X] contains same playing rules as the console version
* [X] contains both a text view and an image view
* [X] user can move with the arrow keys
* [X] user can shoot the arrow with the provided buttons
* [X] warning messages (text) are colored
* [X] warning emojis (image)
* [X] both views stay updated, i.e, act as obsevers

## Structure and Design

The main design pattern used in this project is the Observer. The Composite design pattern is used for the graphical view. There are
four packages (Model, View, Controller, Tests) and six classes total excluding the test class. 

Watch the following video for a more detailed  project walkthrough (to-do upload video)

## Code Coverage (JUnit)

Code coverage is 98% for the console version (see tests package): a fixed map is used to test the hunter and messaging functionalities.
