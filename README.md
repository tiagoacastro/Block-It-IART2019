# Block-It
Console game based on the mobile app "Bloqueio". It consists of a map (which can be customized) dotted by small islands or platforms and two players of different colors. The objective of these players is to reach the border with the same color as them before any other player. <br>
To accomplish this, in each turn they can either move one space or place a barrier (3 spaces wide) to block the opponent's path. The number of barriers available to each player is limited and it too can be customized (it also depends on the map's size). <br>
However, the barriers have some restrictions to where they can be placed: they cannot be adjacent to other barreirs and the central piece of each one can't be next to an island.

All of these rules can also be consulted in-game.

Each player can pick their color and it can either be a human player or a bot. In the case of the latter, two difficulties can be chosen, each one with a different evaluation function. The main algorithm remains the same for both though, that being Mini Max.

## Interface
![image](https://user-images.githubusercontent.com/32617691/60609115-d4213480-9db8-11e9-9f3e-62992fc902ea.png)

Each player is represented by the capital letter of their color and it's barrier by the lowercase equivalent. Each line and column is numbered in order to help barrier placement. Each one is represented by a row/column of 'X's.

## Usage
Either run the BlockIt.jar provided or run the game.BlockIt class without arguments.
