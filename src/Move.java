
public class Move {
    private int start_column;
    private int start_row;
    private int end_column;
    private int end_row;
    private Piece movingPiece;
    private Piece takenPiece;
    private int value;


    public Move(int start_column, int start_row, int end_column, int end_row, Piece movingPiece) {
        this.start_row = start_row;
        this.start_column = start_column;
        this.end_row = end_row;
        this.end_column =  end_column;
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

    public int getValue() {
        return value;
    }

    public void setTakenPiece(Piece takenPiece){
        this.takenPiece = takenPiece;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
