package SnakeAndFoodGame;

import Chess.Board.Board;
import SolutionRunner.SolutionRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



/*
N * N
Snake will grow when eat food
Food placed --> randomly
wall detection
.....H
.....S
.....S
......

Cell --> int i,int j,char val;
Interface ---> moveUp,moveDown,moveLeft,moveRight,display,addScore(),resetGame()
Board ---> Array<Array<Cell>>,mark(),printBoard()
Snake ---> Array<Cell>,moveUp,moveDown,moveLeft,moveDown,(snakeDie),(scoreMove),isDead
PlacementStrategy ---> getCell() ---> Food Strategy
Observer --> snakeDie,scoreMove
 */

class Cell {
    int i;
    int j;
    char val;
    Cell(int i,int j,char val){
        this.i = i;
        this.j = j;
        this.val = val;
    }
    void setVal(char  val){
        this.val = val;
    }
}


class GameBoard{
    int gridSize;
    Cell[][] board;
    GameBoard (int gridSize){
        this.gridSize = gridSize;
        board = new Cell[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                board[i][j] = new Cell(i,j,'.');
            }
        }
    }

    void setIndex(int i,int j,char val){
        board[i][j].setVal(val);
    }

    Cell getIndex(int i,int j){
        if(i < 0 || j < 0 || i >= gridSize || j >= gridSize){
            return null;
        }
        return board[i][j];
    }

    void printBoard(){
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize;j++){
                System.out.printf("%c",board[i][j].val);
            }
            System.out.println();
        }
    }
}

interface Observer{
    void addScore();
    void snakeDie();
}

class Snake{
    GameBoard board;
    ArrayList<Cell> cells;
    ArrayList<Observer> observers;
//    boolean isDead;

    Snake(GameBoard board,Cell firstIndex){
        this.board = board;
        firstIndex.setVal('S');
        cells = new ArrayList<>(List.of(firstIndex));
        observers = new ArrayList<>();
    }

    void notifyScoreIncrease(){
        for (Observer observer: observers){
            observer.addScore();
        }
    }

    void notifySnakeDie(){
        for(Observer observer: observers){
            observer.snakeDie();
        }
    }

    private void moveNew(int dir){
        Cell currentCell = cells.getLast();
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};
        Cell newCell = board.getIndex(currentCell.i + dx[dir], currentCell.j + dy[dir]);
        if(newCell == null || newCell.val == 'S'){
            notifySnakeDie();
            return;
        }
        boolean isFood = (newCell.val == '*');
        cells.add(newCell);
        newCell.setVal('S');
        if(isFood){
            notifyScoreIncrease();
        } else {
            cells.getFirst().setVal('.');
            cells.removeFirst();
        }
    }

    void moveUp(){
        moveNew(0);
    }

    void moveDown(){
        moveNew(1);
    }

    void moveLeft(){
        moveNew(2);
    }

    void moveRight(){
        moveNew(3);
    }
}

interface PlacementStrategy{
    Cell getCell();
}

class FoodPlacementStrategy implements PlacementStrategy{
    GameBoard gameBoard;
    Random rand;

    FoodPlacementStrategy(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        rand = new Random();
    }
    @Override
    public Cell getCell(){
        int n = gameBoard.gridSize;
        ArrayList<Cell> cells = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(gameBoard.getIndex(i,j).val != 'S'){
                    cells.add(gameBoard.getIndex(i,j));
                }
            }
        }
        return cells.get(Math.abs(rand.nextInt()) % cells.size());
    }
}

class SnakeInterfaceObserver implements Observer{
    SnakeInterface snakeInterface;
    SnakeInterfaceObserver(SnakeInterface snakeInterface){
        this.snakeInterface = snakeInterface;
    }

    @Override
    public void addScore() {
        snakeInterface.updateScore();
    }

    @Override
    public void snakeDie() {
        snakeInterface.resetState();
    }
}

class SnakeInterface{
    GameBoard gameBoard;
    int gridSize;
    Snake snake;
    PlacementStrategy foodPlacementStrategy;
    SnakeInterfaceObserver snakeInterfaceObserver;
    int score;

    SnakeInterface(){
        this.gridSize = 5;
        gameBoard = new GameBoard(gridSize);
        snake = new Snake(gameBoard,gameBoard.getIndex(0,0));
        foodPlacementStrategy = new FoodPlacementStrategy(gameBoard);
        snakeInterfaceObserver = new SnakeInterfaceObserver(this);
        snake.observers.add(snakeInterfaceObserver);
        score = 0;
        setFood();
    }

    void moveUp(){
        snake.moveUp();
    }

    void moveDown(){
        snake.moveDown();
    }

    void moveLeft(){
        snake.moveLeft();
    }

    void moveRight(){
        snake.moveRight();
    }

    void resetState(){
        System.out.println("Game over, new state is here\n\n\n");
        gameBoard = new GameBoard(gridSize);
        snake = new Snake(gameBoard,gameBoard.getIndex(0,0));
        score = 0;
        this.print();
    }

    void updateScore(){
        this.score += 1;
        this.setFood();
    }

    void setFood(){
        Cell a = foodPlacementStrategy.getCell();
        System.out.printf("Food cell - %d %d\n",a.i,a.j);
        a.setVal('*');
    }

    void print(){
        gameBoard.printBoard();
        System.out.println();
        System.out.printf("Current Score - %d\n\n\n",score);
    }
}


public class SnakeAndFoodSolutionRunner implements SolutionRunner {
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        SnakeInterface snakeInterface = new SnakeInterface();
        snakeInterface.print();
        while (true){
            String userinput = scanner.next();
            char c = userinput.charAt(0);
            System.out.println(c);
            if(c == 'a'){
                snakeInterface.moveLeft();
            } else if(c == 'd'){
                snakeInterface.moveRight();
            } else if(c == 's') {
                snakeInterface.moveDown();
            } else if(c == 'w'){
                snakeInterface.moveUp();
            }

            snakeInterface.print();
        }
    }
}
