package TicTacToe.GameState;

public class OWonState implements GameState{
    @Override
    public void next(GameContext context) {

    }

    @Override
    public void won(GameContext context) {

    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}
