

import javax.swing.*;

public class Piece {

    private char color;
    private ImageIcon icon;
    private int value;

    Piece(char color, ImageIcon icon, int value) {
        this.color = color;
        this.icon = icon;
        this.value = value;
    }

    public boolean tryMove(Move move, Piece board[][]) {
        return false;
    }


    //checks vertical and horizontal movement. Returns true if move is legal
    public boolean checkLine(Move move, Piece board[][]) {
        // check straight line
        if ((move.getEnd_row() - move.getStart_row()) != 0 && (move.getEnd_column() - move.getStart_column()) != 0) {
            return false;
        }
                
        int rdirection, cdirection;

        // Test moving the piece. Returns true if move is possible. Returns false if piece collides with another.
        if (move.getStart_row() == move.getEnd_row()) {
            cdirection = (move.getEnd_column() - move.getStart_column()) / Math.abs((move.getEnd_column() - move.getStart_column()));
            int current_column = move.getStart_column() + cdirection;
            while (current_column != move.getEnd_column()) {
                if (board[current_column][move.getStart_row()] != null && current_column != move.getEnd_column()) {
                    return false;
                }
                current_column += cdirection;
            }
        } else {
            rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
            int current_row = move.getStart_row() + rdirection;
            while (current_row != move.getEnd_row()) {
                if (board[move.getStart_column()][current_row] != null && current_row != move.getEnd_row()) {
                    return false;
                }
                current_row += rdirection;
            }
        }
        return true;
    }

    //checks diagonal movement. Returns true if move is legal
    public boolean checkDiagonalLine(Move move, Piece board[][]) {

        //checks that the move is diagonal
        if (Math.abs((move.getEnd_row() - move.getStart_row())) != Math.abs((move.getEnd_column() - move.getStart_column()))) {
            return false;
        }

        int rdirection, cdirection;
        // calculates the direction of the move
        rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
        cdirection = (move.getEnd_column() - move.getStart_column()) / Math.abs((move.getEnd_column() - move.getStart_column()));

        // checks if there are any pieces between start and destination. If a piece is found returns false.
        int current_row = move.getStart_row() + rdirection;
        int current_column = move.getStart_column() + cdirection;
        while (current_column != move.getEnd_column() && current_row != move.getEnd_row()) {
            if (board[current_column][current_row] != null && move.getStart_row() != move.getEnd_row() && move.getStart_column() != move.getEnd_column()) {
                return false;
            }
            current_row += rdirection;
            current_column += cdirection;

        }
        return true;
    }

    //checks if move captures. Returns true if move is legal
    public boolean checkCapture(Move move, Piece board[][]) {

        if (board[move.getEnd_column()][move.getEnd_row()] != null) {
            if (board[move.getEnd_column()][move.getEnd_row()].getColor() == getColor()) {
                return false;
            }
            //adds the taken piece to move-object
            move.setTakenPiece(board[move.getEnd_column()][move.getEnd_row()]);
        }

        return true;
    }

    public char getColor() {
        return color;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getValue() {
        return value;
    }

}