package mechanics;

import pieces.*;
import java.util.Stack;

public class Board {

    public Piece[][] board = new Piece[8][8];
    private Stack<Move> moveHistory = new Stack<Move>();

    Board() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Rook('b');
        }
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Rook('w');
        }
    }

    public void printBoard() {
        System.out.println("---------------------------------");
        int rank = 8;

        for (int i = 7; i >= 0; i--) {
            System.out.print(i + 1);
            for (int j = 0; j < 8; j++) {
                System.out.print("| ");
                if (board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j].getColor());
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

        board[move.getStart_row()][move.getStart_column()] = null;

        board[move.getEnd_row()][move.getEnd_column()] = move.getMovingPiece();

        printBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }
}
