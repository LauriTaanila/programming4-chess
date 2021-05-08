package pieces;
import mechanics.Move;

public class Pawn extends Piece {

    public Pawn(char newcolor) {
        super(newcolor, 'p', 1);
        if (getColor() == 'w') {
            setIcon(Character.toUpperCase(getIcon()));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {

        if (board[move.getEnd_row()][move.getEnd_column()] != null) {
            if (move.getStart_column() == move.getEnd_column()) {
                return false;
            }
        }
        // pawn's diagonal capturing
        if (getColor() == 'w' && move.getStart_column() == move.getEnd_column() - 1 || getColor() == 'w' && move.getStart_column() == move.getEnd_column() + 1) {
            if (move.getStart_row() == move.getEnd_row() - 1) {
                if (board[move.getEnd_row()][move.getEnd_column()] != null) {
                    if (board[move.getEnd_row()][move.getEnd_column()].getColor() != getColor()) {
                        return true;
                    }
                }
            }
        }
        // pawn's diagonal capturing
        if (getColor() == 'b' && move.getStart_column() == move.getEnd_column() - 1 || getColor() == 'b' && move.getStart_column() == move.getEnd_column() + 1) {
            if (move.getStart_row() == move.getEnd_row() + 1) {
                if (board[move.getEnd_row()][move.getEnd_column()] != null) {
                    if (board[move.getEnd_row()][move.getEnd_column()].getColor() != getColor()) {
                        return true;
                    }
                }
            }
        }

        if (getColor() == 'w' && move.getStart_row() == move.getEnd_row() - 1 && move.getStart_column() == move.getEnd_column()) {
            return true;
        }

        if (getColor() == 'b' && move.getStart_row() == move.getEnd_row()  + 1 && move.getStart_column() == move.getEnd_column()) {
            return true;
        }

        return false;
    }

};
