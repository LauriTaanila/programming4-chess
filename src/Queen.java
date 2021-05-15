
import javax.swing.*;

public class Queen extends Piece{
    
    public Queen(char newcolor) {
        super(newcolor,new ImageIcon("pictures/whiteQueen.png"), 8);
        if (getColor() == 'b') {
            setIcon(new ImageIcon("pictures/blackQueen.png"));
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
