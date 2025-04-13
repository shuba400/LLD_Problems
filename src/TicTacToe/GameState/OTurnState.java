package TicTacToe.GameState;

public class OTurnState implements GameState{

    @Override
    public void next(final GameContext context) {
        context.setState(new XTurnState());
    }

    @Override
    public void won(GameContext context) {
        context.setState(new OWonState());
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
