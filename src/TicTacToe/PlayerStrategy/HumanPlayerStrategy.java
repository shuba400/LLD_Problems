package TicTacToe.PlayerStrategy;

import TicTacToe.Board;
import TicTacToe.Position;
import java.util.Scanner;

public class HumanPlayerStrategy implements PlayerStrategy {

    private final Scanner scanner;

    public HumanPlayerStrategy(){
        scanner = new Scanner(System.in);
    }

    @Override
    public Position makeMove(final Board board) {
        Position curr;
        while (true) {
            System.out.println("Make a move: ");
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            curr = new Position(r,c);
            if(board.isValidMove(curr)){
                break;
            }
            System.out.print("Move is not valid, it should be between 0,2");
        }
        return curr;
    }
}
