
import java.util.Stack;

public class Board {

    public Piece[][] board = new Piece[8][8];
    private Stack<Move> moveHistory = new Stack<Move>();

    Board() {
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Rook('w');

        }
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Rook('b');
        }
    }

    public void printBoard() {
        System.out.println("---------------------------------");
        int rank = 8;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("| ");
                if (board[j][i] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[j][i].getColor());
                }
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.println(" ---------------------------------");
        }
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    public void test() {





    }

    public void movePiece(Move move) {
        moveHistory.add(move);

        board[move.getStart_column()][move.getStart_row()] = null;

        board[move.getEnd_column()][move.getEnd_row()] = move.getMovingPiece();

        printBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }
}
