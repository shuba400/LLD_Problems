package TicTacToe.GameState;

public class XTurnState implements GameState{
    @Override
    public void next(GameContext context) {
        context.setState(new OTurnState());
    }

    @Override
    public void won(GameContext context) {
        context.setState(new XWonState());
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
