

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
            System.out.println("CHECKLINE 1 RETURNIGN FALSE");
            return false;
        }
                
        int rdirection, cdirection;

        // Test moving the piece. Returns true if move is possible. Returns true if piece collides with another.
        if (move.getStart_row() == move.getEnd_row()) {
            cdirection = (move.getEnd_column() - move.getStart_column()) / Math.abs((move.getEnd_column() - move.getStart_column()));

            System.out.println("c " + cdirection);
            int current_column = move.getStart_column() + cdirection;
            while (current_column != move.getEnd_column()) {
                System.out.println(current_column + " " + move.getStart_row() + " to " + move.getEnd_column() + " " + move.getEnd_row());
                if (board[current_column][move.getStart_row()] != null && current_column != move.getEnd_column()) {
                    return false;
                }
                current_column += cdirection;
            }
        } else {
            rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
            System.out.println("r " + rdirection);
            int current_row = move.getStart_row() + rdirection;
            while (current_row != move.getEnd_row()) {

                System.out.println(move.getStart_column() + " " + current_row + " to " + move.getEnd_column() + " " + move.getEnd_row());
                if (board[move.getStart_column()][current_row] != null && current_row != move.getEnd_row()) {
                    return false;
                }
                current_row += rdirection;
            }
        }
        return true;
    }

    public boolean checkDiagonalLine(Move move, Piece board[][]) {

        //checks that the move is diagonal
        if (Math.abs((move.getEnd_row() - move.getStart_row())) != Math.abs((move.getEnd_column() - move.getStart_column()))) {
            return false;
        }

        int rdirection, cdirection;
        // calculates the direction of the move
        rdirection = (move.getEnd_row() - move.getStart_row()) / Math.abs((move.getEnd_row() - move.getStart_row()));
        cdirection = (move.getEnd_column() - move.getStart_column()) / Math.abs((move.getEnd_column() - move.getStart_column()));
        System.out.println("r d = " + rdirection + "c d = " + cdirection);

        // checks if there are any pieces between start and destination. If a piece is found returns false.
        int current_row = move.getStart_row() + rdirection;
        int current_column = move.getStart_column() + cdirection;
        while (current_column != move.getEnd_column() && current_row != move.getEnd_row()) {
            System.out.println(current_column + " " + current_row + " to " + move.getEnd_column() + " " + move.getEnd_row());
            if (board[current_column][current_row] != null && move.getStart_row() != move.getEnd_row() && move.getStart_column() != move.getEnd_column()) {
                return false;
            }
            current_row += rdirection;
            current_column += cdirection;

        }
        return true;
    }

    public boolean checkCapture(Move move, Piece board[][]) {

        if (board[move.getEnd_column()][move.getEnd_row()] != null) {
            System.out.println("CAP NOT NULL");
            if (board[move.getEnd_column()][move.getEnd_row()].getColor() == getColor()) {
                System.out.println("CAP RETURNING FALSE");
                return false;
            }
            move.setTakenPiece(board[move.getEnd_column()][move.getEnd_row()]);
        }
        System.out.println("CAP RETURNING TRUE");
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