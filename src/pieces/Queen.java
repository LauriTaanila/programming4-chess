package pieces;
import mechanics.Move;

import javax.swing.*;

public class Queen extends Piece{
    
    public Queen(char newcolor) {
        super(newcolor,new ImageIcon("C:/Users/Lauri/IdeaProjects/chess/src/pieces/pictures/whiteRook.png"), 8);
        if (getColor() == 'w') {

        }
    }

    public boolean tryMove(Move move, Piece board[][]) {

        // capturing
        if(!checkCapture(move, board)){
            return false;
        }

        if(checkLine(move,board) || checkDiagonalLine(move,board)){
            return true;
        }

        return false;
    }
}
