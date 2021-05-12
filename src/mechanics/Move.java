package mechanics;
import javafx.util.Pair;
import pieces.Piece;

public class Move {
    private int start_column;
    private int start_row;
    private int end_column;
    private int end_row;
    private Piece movingPiece;
    private Piece takenPiece;


    public Move(Pair<Integer,Integer> startSquare,Pair<Integer,Integer> endSquare, Piece movingPiece) {
        this.start_row = startSquare.getValue();
        this.start_column = startSquare.getKey();
        this.end_row = endSquare.getValue();
        this.end_column = endSquare.getKey();
        this.movingPiece = movingPiece;
    }


    public int getStart_column() {
        return this.start_column;
    }

    public int getStart_row() {
        return this.start_row;
    }

    public int getEnd_column() {
        return this.end_column;
    }

    public int getEnd_row() {
        return this.end_row;
    }

    public Piece getMovingPiece() {
        return this.movingPiece;
    }

    public Piece getTakenPiece() {
        return this.takenPiece;
    }
    
    public void setTakenPiece(Piece takenPiece){
        this.takenPiece = takenPiece;
    }
    
}
