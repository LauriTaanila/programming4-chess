import javax.swing.*;

public class King extends Piece {

    public King(char newcolor) {
        super(newcolor, new ImageIcon("pictures/whiteKing.png"), 100);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("pictures/blackKing.png"));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {


        // capturing
        if (!checkCapture(move, board)) {
            return false;
        }

        //Seems messy, but it checks if move is only 1 square long.
        if (move.getEnd_row() - move.getStart_row() == 0 || move.getEnd_row() - move.getStart_row() == 1 || move.getEnd_row() - move.getStart_row() == -1)
        {
            if (move.getEnd_column() - move.getStart_column() == 0 || move.getEnd_column() - move.getStart_column() == 1 || move.getEnd_column() - move.getStart_column() == -1)
            {
                return true;
            }
        }

        if (move.getEnd_column() - move.getStart_column() == 0 || move.getEnd_column() - move.getStart_column() == 1 || move.getEnd_column() - move.getStart_column() == -1)
        {
            if (move.getEnd_row() - move.getStart_row() == 0 || move.getEnd_row() - move.getStart_row() == 1 || move.getEnd_row() - move.getStart_row() == -1)
            {
                return true;
            }
        }

        return false;
    }
}