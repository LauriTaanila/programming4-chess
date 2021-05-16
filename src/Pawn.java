
import javax.swing.*;

public class Pawn extends Piece {

    public Pawn(char newcolor) {
        super(newcolor,new ImageIcon("pictures/whitePawn.png"), 1);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("pictures/blackPawn.png"));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {

        //can't move in place
        if (board[move.getEnd_column()][move.getEnd_row()] != null) {
            if (move.getStart_column() == move.getEnd_column()) {
                return false;
            }
        }


        // pawn's diagonal capturing
        if (getColor() == 'b' && move.getStart_column() == move.getEnd_column() - 1 || getColor() == 'b' && move.getStart_column() == move.getEnd_column() + 1) {
            if (move.getStart_row() == move.getEnd_row() - 1) {
                if (board[move.getEnd_column()][move.getEnd_row()] != null) {
                    if (board[move.getEnd_column()][move.getEnd_row()].getColor() != getColor()) {
                        move.setTakenPiece(board[move.getEnd_column()][move.getEnd_row()]);
                        return true;
                    }
                }
            }
        }
        // pawn's diagonal capturing
        if (getColor() == 'w' && move.getStart_column() == move.getEnd_column() - 1 || getColor() == 'w' && move.getStart_column() == move.getEnd_column() + 1) {
            if (move.getStart_row() == move.getEnd_row() + 1) {
                if (board[move.getEnd_column()][move.getEnd_row()] != null) {
                    if (board[move.getEnd_column()][move.getEnd_row()].getColor() != getColor()) {
                        move.setTakenPiece(board[move.getEnd_column()][move.getEnd_row()]);
                        return true;
                    }
                }
            }
        }

        //black moves downwards
        if (getColor() == 'b' && move.getStart_row() == move.getEnd_row() - 1 && move.getStart_column() == move.getEnd_column()) {
            return true;
        }
        //white moves upwards
        if (getColor() == 'w' && move.getStart_row() == move.getEnd_row()  + 1 && move.getStart_column() == move.getEnd_column()) {
            return true;
        }

        return false;
    }

};
