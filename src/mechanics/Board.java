package mechanics;

import pieces.*;
import java.util.Stack;

public class Board {

    public Piece[][] board = new Piece[8][8];
    private Stack<Move> moveHistory = new Stack<Move>();

    Board() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Rook('w');
        }
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Rook('b');
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
                    System.out.print(board[i][j].getIcon());
                }
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.println(" ---------------------------------");
        }
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    public void test() {

        Move m = new Move(6, 2, 1, 3, board[6][2]);

        if (board[6][2].tryMove(m, board)) {
            System.out.println("LESS GOO");
            movePiece(m);

            moveHistory.add(m);

            System.out.println(moveHistory.peek().getTakenPiece().getIcon());
        } else {
        }

    }

    public void movePiece(Move move) {
        moveHistory.add(move);

        board[move.getStart_row()][move.getStart_column()] = null;

        board[move.getEnd_row()][move.getEnd_column()] = move.getMovingPiece();

        printBoard();
    }
}
