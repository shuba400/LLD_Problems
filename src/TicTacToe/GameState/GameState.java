package TicTacToe.GameState;

public interface GameState {
    void next(final GameContext context);
    void won(final GameContext context);
    boolean isGameOver();
}
