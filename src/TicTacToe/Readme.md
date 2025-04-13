# TicTacToe System Design

## Problem
Standard problem of designing tic-tac-toe game keeping in mind extensibility and using design principals

## Patterns used
- Strategy Pattern - For each player, either its a human or can be extended to robot playing on opposite side.
- State Pattern - For deciding state of the game (XTurn,OTurn,XWon,OWon). 
- Factory Pattern - Ofc, this would have been useful if there was multiple board games, I wil extend this later to having a board game super calss

Tbh using state pattern is seems a bit of overkill but this is a simple problem like this.