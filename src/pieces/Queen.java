package pieces;
import mechanics.Move;

public class Queen extends Piece{
    
    public Queen(char newcolor) {
        super(newcolor, 'q', 8);
        if (getColor() == 'w') {
            setIcon(Character.toUpperCase(getIcon()));
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
