package pieces;
import mechanics.Move;

import javax.swing.*;

public class Bishop extends Piece {
    
    public Bishop(char newcolor) {
        super(newcolor,new ImageIcon("src/pieces/pictures/whiteBishop.png"), 3);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("src/pieces/pictures/blackBishop.png"));
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

