package mechanics;
import pieces.Piece;

public class Move {
    private int start_column;
    private int start_row;
    private int end_column;
    private int end_row;
    private Piece movingPiece;
    private Piece takenPiece;


    public Move(Square startSquare, Square endSquare, Piece movingPiece) {
        this.start_row = startSquare.getRow();
        this.start_column = startSquare.getColumn();
        this.end_row = endSquare.getRow();
        this.end_column = endSquare.getColumn();
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
