
import java.util.*;

class ComputerMove {
    private Move move;
    private int value;

    ComputerMove(int value) {
        this.value = value;
    }

    public Move getMove() {
        return move;
    }

    public int getValue() {
        return value;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class Board {

    public Piece[][] board = new Piece[8][8];
    private Stack<Move> moveHistory = new Stack<Move>();
    private char currentTurn;

    Board() {
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn('w');
        }
        board[0][7] = new Rook('w');
        board[7][7] = new Rook('w');
        board[1][7] = new Knight('w');
        board[6][7] = new Knight('w');
        board[2][7] = new Bishop('w');
        board[5][7] = new Bishop('w');
        board[3][7] = new Queen('w');
        board[4][7] = new King('w');


        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn('b');
        }
        board[0][0] = new Rook('b');
        board[7][0] = new Rook('b');
        board[1][0] = new Knight('b');
        board[6][0] = new Knight('b');
        board[2][0] = new Bishop('b');
        board[5][0] = new Bishop('b');
        board[3][0] = new Queen('b');
        board[4][0] = new King('b');
    }

    //Prints the board to console. For debugging purposes.
    public void printBoard() {
        System.out.println("---------------------------------");
        int rank = 8;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("| ");
                if (board[j][i] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[j][i].getColor());
                }
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.println(" ---------------------------------");
        }
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    //Move the piece on the board.
    public boolean movePiece(Move move) {

        if (!board[move.getStart_column()][move.getStart_row()].tryMove(move, board)) {
            return false;
        }

        moveHistory.add(move);

        board[move.getStart_column()][move.getStart_row()] = null;

        board[move.getEnd_column()][move.getEnd_row()] = move.getMovingPiece();

        if (testCheck()) {
            undoMove();
            return false;
        }

        return true;
    }
    //Returns true if king is in check
    public boolean testCheck() {
        {
            int kingRow = 0, kingCol = 0;
            //finds current player's king
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (board[c][r] != null) {
                        if (currentTurn == 'w') {
                            //king is 100 value
                            if (board[c][r].getValue() == 100 && board[c][r].getColor() == 'w') {
                                kingRow = r;
                                kingCol = c;
                                break;
                            }
                        } else {
                            if (board[c][r].getValue() == 100 && board[c][r].getColor() == 'b') {
                                kingRow = r;
                                kingCol = c;
                                break;
                            }
                        }
                    }
                }
            }
            //checks if it is being attacked
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (board[c][r] != null) {
                        if (board[c][r].getColor() != currentTurn) {
                            if (board[c][r].tryMove(new Move(c, r, kingCol, kingRow, board[c][r]), board)) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }
    }

    //returns true if king is in mate
    public boolean testMate() {
        List<Move> possibleMoves = getPossibleMoves();
        if (possibleMoves.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //returns a list of all the possible moves for one chesspiece
    public List<Move> getPossibleMovesOnePiece(int c, int r) {
        {
            List<Move> possibleMoves = new ArrayList<>();

            //checks moves
            for (int endRow = 0; endRow < 8; endRow++) {
                for (int endCol = 0; endCol < 8; endCol++) {
                    Move newmove = new Move(c, r, endCol, endRow, board[c][r]);
                    if (movePiece(newmove)) {
                        possibleMoves.add(newmove);
                        undoMove();
                    }
                }
            }

            return possibleMoves;
        }
    }

    //returns a list of all the possible moves for one color
    public List<Move> getPossibleMoves() {
        {
            List<Move> possibleMoves = new ArrayList<>();

            for (int startRow = 0; startRow < 8; startRow++) {
                for (int startCol = 0; startCol < 8; startCol++) {
                    if (board[startCol][startRow] != null) {
                        if (board[startCol][startRow].getColor() == currentTurn) {
                            //checks moves
                            for (int endRow = 0; endRow < 8; endRow++) {
                                for (int endCol = 0; endCol < 8; endCol++) {
                                    Move newmove = new Move(startCol, startRow, endCol, endRow, board[startCol][startRow]);
                                    if (movePiece(newmove)) {
                                        possibleMoves.add(newmove);
                                        undoMove();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return possibleMoves;
        }
    }

    //undoes last move
    public void undoMove() {

        if (moveHistory.empty()) {
            return;
        }
        Move undoThisMove = moveHistory.peek();

        board[undoThisMove.getStart_column()][undoThisMove.getStart_row()] = undoThisMove.getMovingPiece();
        board[undoThisMove.getEnd_column()][undoThisMove.getEnd_row()] = null;
        if (undoThisMove.getTakenPiece() != null) {
            board[undoThisMove.getEnd_column()][undoThisMove.getEnd_row()] = undoThisMove.getTakenPiece();
        }

        moveHistory.pop();
    }

    //Evaluates the value of the whole board.
    public int getBoardValue() {
        int value = 0;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[c][r] != null) {
                    if (board[c][r].getColor() == 'b') {
                        value += board[c][r].getValue();
                    } else {
                        value += -board[c][r].getValue();
                    }
                }
            }
        }
        return value;
    }

    //Swaps turn
    public void changeTurn() {
        if (currentTurn == 'w') {
            currentTurn = 'b';
        } else {
            currentTurn = 'w';
        }
    }

    //Computer finds best move using minimax-algorithm
    public Move computerMove(int difficulty) {

        ComputerMove bestMove;

        bestMove = minimax(difficulty, -1000, 1000, true);
        movePiece(bestMove.getMove());


        return bestMove.getMove();
    }

    //Minimax-algorithm https://en.wikipedia.org/wiki/Minimax
    public ComputerMove minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {

        int eval;
        ComputerMove bestMove;

        if (depth == 0 || testMate()) {
            bestMove = new ComputerMove(getBoardValue());
            return bestMove;
        }

        if (maximizingPlayer) {
            bestMove = new ComputerMove(-1000);
            List<Move> movelist = getPossibleMoves();

            for (Move move : movelist) {

                movePiece(move);

                changeTurn();
                eval = minimax(depth - 1, alpha, beta, false).getValue();

                if (eval > bestMove.getValue()) {
                    bestMove.setMove(new Move(move.getStart_column(),
                            move.getStart_row(),
                            move.getEnd_column(),
                            move.getEnd_row(),
                            board[move.getEnd_column()][move.getEnd_row()]));
                    bestMove.setValue(eval);
                }

                undoMove();
                changeTurn();
                if (eval > alpha) {
                    alpha = eval;
                    if (beta <= alpha) {
                        break;
                    }
                }

            }
            return bestMove;
        } else {
            bestMove = new ComputerMove(1000);
            List<Move> movelist = getPossibleMoves();

            for (Move move : movelist) {

                //make move
                movePiece(move);

                changeTurn();
                eval = minimax(depth - 1, alpha, beta, true).getValue();

                if (eval < bestMove.getValue()) {
                    bestMove.setMove(new Move(move.getStart_column(),
                            move.getStart_row(),
                            move.getEnd_column(),
                            move.getEnd_row(),
                            board[move.getStart_column()][move.getStart_row()]));
                    bestMove.setValue(eval);
                }
                //undo
                undoMove();
                changeTurn();
                if (eval < beta) {
                    beta = eval;
                    if (beta <= alpha) {
                        break;
                    }
                }


            }

            return bestMove;
        }
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setCurrentTurn(char currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Stack<Move> getMoveHistory() {
        if(moveHistory.empty()){
            return new Stack<Move>();
        }
        return moveHistory;
    }
}
