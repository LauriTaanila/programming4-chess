
import java.util.Stack;

public class Board {

    public Piece[][] board = new Piece[8][8];
    private Stack<Move> moveHistory = new Stack<Move>();

    Board() {
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn('w');
        }
        board[0][7] = new Rook('w');
        board[7][7] = new Rook('w');
        board[1][7] = new Knight('w');
        board[2][7] = new Bishop('w');
        board[5][7] = new Bishop('w');
        board[4][7] = new Queen('w');
        board[3][7] = new King('w');


        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn('b');
        }
        board[0][0] = new Rook('b');
        board[7][0] = new Rook('b');
        board[2][0] = new Bishop('b');
        board[5][0] = new Bishop('b');
        board[4][0] = new Queen('b');
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
