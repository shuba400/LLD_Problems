package TicTacToe;

import TicTacToe.GameState.GameContext;
import TicTacToe.GameState.GameState;
import TicTacToe.GameState.OWonState;
import TicTacToe.GameState.XWonState;
import TicTacToe.PlayerStrategy.PlayerStrategy;

public class TicTacToeBoardGame {
    private final Board board;
    private final Player xPlayer;
    private final Player oPlayer;
    private Player currPlayer;
    private final GameContext gameContext;
    public TicTacToeBoardGame(final PlayerStrategy xplayerStrategy, final PlayerStrategy oPlayerStrategy, final Board board){
        this.xPlayer = new Player(xplayerStrategy,Symbol.X);
        this.oPlayer = new Player(oPlayerStrategy,Symbol.O);
        this.gameContext = new GameContext();
        this.board = board;
        this.currPlayer = xPlayer;
    }
    void play(){
        do{
            board.printBoard();
            Position move = currPlayer.playerStrategy.makeMove(board);
            board.makeMove(move, currPlayer.symbol);
            board.checkGameState(gameContext);
            switchPlayer();
        } while (!gameContext.isGameOver());
        announceResult();
    }
    void switchPlayer(){
        currPlayer = (currPlayer == xPlayer) ? oPlayer : xPlayer;
    }
    void announceResult(){
        final GameState state = gameContext.getCurrentState();
        if(state instanceof XWonState){
            System.out.printf("X has won the game\n");
        }
        else if(state instanceof OWonState){
            System.out.printf("O has won the game\n");
        }
        else System.out.println("Its a drawwww!!!");
        board.printBoard();
    }
}
