package TicTacToe;

import TicTacToe.PlayerStrategy.PlayerStrategy;

public class Player {
    public PlayerStrategy playerStrategy;
    public Symbol symbol;


    public Player(final PlayerStrategy playerStrategy,final Symbol symbol ){
        this.playerStrategy = playerStrategy;
        this.symbol = symbol;
    }
}
