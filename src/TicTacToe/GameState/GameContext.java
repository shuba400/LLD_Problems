package TicTacToe.GameState;

public class GameContext {
    private GameState currentState;

    public GameContext(){
        currentState = new XTurnState();
    }

    public void next(){
        currentState.next(this);
    }

    public void won(){
        currentState.won(this);
    }

    public void setState(GameState state) {
        this.currentState = state;
    }

    public boolean isGameOver(){
        return currentState.isGameOver();
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
