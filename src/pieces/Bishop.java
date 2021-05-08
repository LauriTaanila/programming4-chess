package pieces;
import mechanics.Move;

public class Bishop extends Piece {
    
    public Bishop(char newcolor) {
        super(newcolor, 'b', 3);
        if (getColor() == 'w') {
            setIcon(Character.toUpperCase(getIcon()));
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

