package pieces;
import mechanics.Move;

import javax.swing.*;

public class Bishop extends Piece {
    
    public Bishop(char newcolor) {
        super(newcolor,new ImageIcon("C:/Users/Lauri/IdeaProjects/chess/src/pieces/pictures/whiteRook.png"), 3);
        if (getColor() == 'w') {

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

