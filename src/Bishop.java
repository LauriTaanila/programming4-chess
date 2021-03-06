

import javax.swing.*;

public class Bishop extends Piece {
    
    public Bishop(char newcolor) {
        super(newcolor,new ImageIcon("pictures/whiteBishop.png"), 3);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("pictures/blackBishop.png"));
        }
    }

    public boolean tryMove(Move move, Piece board[][]) {


        // capturing
        if(!checkCapture(move, board)){
            return false;
        }

        return checkDiagonalLine(move, board);

    }
}

