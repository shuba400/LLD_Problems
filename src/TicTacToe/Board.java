package TicTacToe;

import TicTacToe.GameState.GameContext;

import java.io.Console;

public class Board {
    int gridSize;
    Symbol [][] grid;
    public Board(final int gridSize){
        this.gridSize = gridSize;
        this.grid = new Symbol[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                grid[i][j] = Symbol.EMPTY;
            }
        }
    }

    public boolean isValidMove(Position position){
        return (position.row >= 0 && position.col >= 0 && position.row < gridSize &&
                position.col < gridSize && grid[position.row][position.col] == Symbol.EMPTY);
    }

    public void makeMove(final Position pos,final Symbol s){
        grid[pos.row][pos.col] = s;
    }

    public void printBoard(){
        for(int i = 0; i < gridSize; i++){
            String line = "";
            for(int j = 0; j < gridSize; j++){
                char c;
                if(grid[i][j] == Symbol.X) c = 'X';
                else if(grid[i][j] == Symbol.O) c = 'O';
                else c = '_';
                line = line.concat(String.valueOf(c)).concat(" ");
            }
            System.out.println(line);
        }
    }

    public void checkGameState(final GameContext context){
        //row
        boolean won = false;
        for(int i = 0; i < gridSize; i++){
            if(isWinningLine(grid[i])){
                won = true;
            }
        }
        //col
        for(int i = 0; i < gridSize; i++){
            final Symbol[] line = new Symbol[gridSize];
            for(int j = 0; j < gridSize; j++) line[j] = grid[j][i];
            if(isWinningLine(line)){
                won = true;
            }
        }
        //diag
        final Symbol[] diagline1 = new Symbol[gridSize];
        final Symbol[] diagline2 = new Symbol[gridSize];
        for(int i = 0; i < gridSize; i++) diagline1[i] = grid[i][i];
        for(int i = 0; i < gridSize; i++) diagline2[i] = grid[gridSize - i - 1][i];
        if(isWinningLine(diagline1) || isWinningLine(diagline2)) won = true;
        if(won) context.won();
        else context.next();
    }
    public boolean isWinningLine(final Symbol[] line){
        if(line[0] == Symbol.EMPTY) return false;
        for(int i = 0; i < gridSize; i++){
            if(line[i] != line[0]) return  false;
        }
        return true;
    }


}
