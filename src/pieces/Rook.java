package pieces;
import mechanics.Move;

import javax.swing.*;

public class Rook extends Piece {

    public Rook(char newcolor) {
        super(newcolor, new ImageIcon("src/pieces/pictures/whiteRook.png"), 5);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("src/pieces/pictures/blackRook.png"));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {

        // capturing
        if(!checkCapture(move, board)){
            return false;
        }

        // check if there are any pieces between start and destination
        System.out.println(move.getStart_column() + " " + move.getStart_row() + " to " + move.getEnd_column() + " " + move.getEnd_row());
        //System.out.println(checkLine(move, board));
        return checkLine(move, board);

    }
}
