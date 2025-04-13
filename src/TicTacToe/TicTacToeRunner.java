package TicTacToe;


import TicTacToe.PlayerStrategy.HumanPlayerStrategy;
import TicTacToe.PlayerStrategy.PlayerStrategy;

/*
Board --> 3x3 grid
2 players 3x3, 3 symbol in a row (horizontal,vertical and diagonal)
Manage Game state
Implemment move validation
Tracking Player Turn
Detect game ending condition

 */
public class TicTacToeRunner {
    private final Board board;
    private final PlayerStrategy xPlayerStrategy;
    private final PlayerStrategy oPlayerStrategy;
    public TicTacToeRunner(final int gridSize){
        board = new Board(gridSize);
        xPlayerStrategy = new HumanPlayerStrategy();
        oPlayerStrategy = new HumanPlayerStrategy();
    }
    public void run(){
        TicTacToeBoardGame ticTacToeBoardGame = new TicTacToeBoardGame(xPlayerStrategy,oPlayerStrategy,board);
        ticTacToeBoardGame.play();

    }
}
