package pieces;
import java.lang.Math;

import mechanics.Move;

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

    public boolean checkLine(Move move, Piece board[][]) {
        //System.out.println(move.getStart_column() + " " + move.getStart_row() + " to " + move.getEnd_column() + " " + move.getEnd_row());
        // check straight line
        if ((move.getEnd_row() - move.getStart_row()) != 0 && (move.getEnd_column() - move.getStart_column()) != 0) {
            return false;
        }
                
        int rdirection, cdirection;

        // Test moving the piece. Returns true if move is possible. Returns true if piece collides with another.
        if (move.getStart_row() == move.getEnd_row()) {
            cdirection = (move.getEnd_column() - move.getStart_column()/ Math.abs((move.getEnd_column() - move.getStart_column())));
            int current_column = move.getStart_column();
            while (current_column != move.getEnd_column()) {
                current_column += cdirection;
                if (board[move.getStart_row()][current_column] != null && current_column != move.getEnd_column()) {
                    return false;
                }
            }
        } else {
            rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
            int current_row = move.getStart_row();
            while (current_row != move.getEnd_row()) {
                current_row += rdirection;
                if (board[current_row][move.getStart_column()] != null && current_row != move.getEnd_row()) {
                    System.out.println(current_row + " " + move.getStart_row() + " to " + move.getEnd_column() + " " + move.getEnd_row());
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkDiagonalLine(Move move, Piece board[][]) {

        if (Math.abs((move.getEnd_row() - move.getStart_row())) != Math.abs((move.getEnd_column() - move.getStart_column()))) {
            return false;
        }

        int rdirection, cdirection;
        // calculaters the direction of the move
        rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
        cdirection = (move.getEnd_column() - move.getStart_column()) / Math.abs((move.getEnd_column() - move.getStart_column()));

        // checks if there are any pieces between start and destination
        int current_row = move.getStart_row(); 
        int current_column = move.getStart_column(); 
        while (move.getStart_row() != move.getEnd_row() && move.getStart_column() != move.getEnd_column()) {
            current_row += rdirection;
            current_column += cdirection;
            if (board[current_row][current_column] != null && move.getStart_row() != move.getEnd_row() && move.getStart_column() != move.getEnd_column()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCapture(Move move, Piece board[][]) {
        if (board[move.getEnd_row()][move.getEnd_column()] != null) {
            if (board[move.getEnd_row()][move.getEnd_column()].getColor() == getColor()) {
                return false;
            }
            move.setTakenPiece(board[move.getEnd_row()][move.getEnd_column()]);
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