import javax.swing.*;

public class Knight extends Piece {

    public Knight(char newcolor) {
        super(newcolor, new ImageIcon("pictures/whiteKnight.png"), 3);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("pictures/blackKnight.png"));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {


        // capturing
        if (!checkCapture(move, board)) {
            return false;
        }

        if (move.getStart_row() == move.getEnd_row() + 1 || move.getStart_row() == move.getEnd_row() - 1) {
            if (move.getStart_column() == move.getEnd_column() + 2 || move.getStart_column() == move.getEnd_column() - 2) {
                return true;
            }
        }

        if (move.getStart_row() == move.getEnd_row() + 2 || move.getStart_row() == move.getEnd_row() - 2) {
            if (move.getStart_column() == move.getEnd_column() + 1 || move.getStart_column() == move.getEnd_column() - 1) {
                return true;
            }
        }

        return false;
    }
}