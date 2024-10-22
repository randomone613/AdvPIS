# Advanced Programming of Interactive Systems Project : Wasted Wars

# Game rules:

When the game starts, the first player is given a mini game, if they win the mini game, the other players drink a sip, if they lose they drink a sip. You have 30s to complete the mini game, if not, you lose.
Then it's the turn of the second player, so on and so forth.
The game ends when a player has drunk 10 sips.
The game can be played from 2 to 6 players.


# Mini game rules :

1) Question for a shot (QFAS):
  A general knowledge question or a multiplication is going to be shown on the screen, answer in the bar and click on validate to submit your answer.
  Answer is one word that starts with a capital letter or a number.

2) Order Game:
  5 cards with random numbers will be placed randomly on the screen, with 5 slots in the middle of the screen. You need to put them in the right order (Ascending or Descending, it will be written on the screen) to win.
  Be careful, once a card is put in a slot, you cannot take it back !

3) Twisted Fingers (TF):
   A keyboard will be shown on the screen with certain keys highlighted in blue or red : press the keys in blue with your left hand and the keys in red with your right hand at the same time to win the game. Between 2 and 3 keys per hand will be highlighted.
   If a key that is not highlighted is pressed, you lose.
   
4) Decibel Challenge:
   Scream loud enough to win the game.



# How to use the game

To launch the game, run the WastedWarsApp.java main class.

Once launched, a window will open showing you a "Start Game" button, and a list of players at the bottom. You can add players by clicking on the "+ Add Player" button, you will add a player, you cannot add more than 4 players to the minimum of 2 players. For the players from 3 to 6, a "-" button will appear at the top the square representing them, by clicking on it you remove said player. You can also modify a player's name by double clicking on the username, a pop-up will appear allowing you to change the username.
By pressing the "escape" key on your keyboard, a pop-up will appear to allow you to quit the app if wanted, you can click on cancel to stay on the app.

When you click on the "Start Game" button, the game will begin. 
The first player will be assigned a random mini-game for them to complete, if they fail to win the game under 30s or lose the game, they gain 1 sip, if they win, all the other players will gain 1 sip.
Once the mini game is over, the turn is over and it becomes the next player's turn; they then also get assigned a random mini-game as well etc...
The game ends once a player reaches 10 sips.
A "Quit Game" button is present in the top right corner, by pressing it, a pop-up will appear asking you if you really want to quit the game. Be careful, once a game is quit, the score will be reset to zero.


# Difficulties encountered

As we began this project, we were well aware that the tight schedule would be a challenge. However, we were determined to bring our vision for the game to life. For the most part, we succeeded. 

That said, we did encounter some difficulties with certain aspects of the implementation. One of our main issues stemmed from our decision to develop the mini-games separately from the main game and integrate them later. This approach led to our biggest setback: we were unable to incorporate all four mini-games into the main game.

Specifically, we couldn't get the "Decibel Challenge" mini-game to function within the main application, despite successfully implementing and running it as a standalone feature. It's frustrating, considering that the other mini-games were integrated without issue.
To test the "Decibel Challenge" mini-game, you can use the **MainTestGame.java** main class in the **src** folder.

We also wish we'd had more time to refine the overall design of the game.









