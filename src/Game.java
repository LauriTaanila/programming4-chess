
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

class Square extends JPanel{
    private int column;
    private int row;
    private Color color;


    Square(int newcolumn,int newrow){
        column = newcolumn;
        row = newrow;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void saveColor(){
        color = getBackground();
    }

    public void changeColor(Color movecolor){
        if(getBackground() == color){
            setBackground(movecolor);
        } else {
            setBackground(color);
        }
    }
}


public class Game extends JFrame implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame frame;
    private JPanel panel;
    private Square[][] chessBoardSquares;
    private JLabel currentPiece;
    private Square startingSquare;
    private Board board;
    private Piece[][] currentboard;
    private char currentTurn = 'w';
    private int xAdjustment;
    private int yAdjustment;
    private JLabel turnlabel;
    private DefaultListModel<String > listModel;
    private boolean gameInProgress;
    private GridBagConstraints gbc;
    private JButton newGameButton;
    private Color movecolor = new Color(155,255,155);
    private JPanel takenpiecespanel;
    private boolean vsComputer;
    private int difficulty;


    public Game() {
        InterfaceSetUp();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public JLabel createLabel(String txt, int width, int height, Color color)
    {
        JLabel label = new JLabel(txt, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }


    public void InterfaceSetUp() {
        frame = new JFrame();
        frame.setTitle("Chessboard");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.addMouseListener(this);

        chessBoardSquares = new Square[8][8];

        gbc = new GridBagConstraints();
        //NEW GAME BUTTON
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipadx = 1;
        gbc.ipady = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;

        //NEW GAME
        creteNewGameWindow();

        //TURN LABEL
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        turnlabel = createLabel("White turn",400,10,new Color(255,255,100));
        turnlabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 8;
        gbc.ipadx = 0;

        panel.add(turnlabel,gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        //NUMBER BELOW THE BOARD
        gbc.anchor = GridBagConstraints.LINE_START; //bottom of space
        int color = 0;

        for(int i = 8;i > 0; i--){
            gbc.gridx = 0;
            gbc.gridy = 10 - i;
            gbc.insets = new Insets(0,50,0,0);  //top padding
            JLabel square = createLabel(Integer.toString(i),50,50,Color.white);
            panel.add(square, gbc);
        }
        gbc.insets = new Insets(0,0,0,0);  //top padding

        //ALPHABETS ALONG THE BOARD
        for(int i = 0;i < 8; i++){
            gbc.gridx = i + 1;
            gbc.gridy = 10;
            JLabel square = createLabel(Character.toString((char) ('A' + i)),50,50,Color.white);
            panel.add(square, gbc);
        }

        //BOARD
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                gbc.gridx = i + 1;
                gbc.gridy = j + 2;
                chessBoardSquares[i][j] = new Square(i,j);
                chessBoardSquares[i][j].setPreferredSize(new Dimension(50, 50));
                chessBoardSquares[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
                if(color % 2 == 0){
                    chessBoardSquares[i][j].setBackground(Color.white);
                } else {
                    chessBoardSquares[i][j].setBackground(Color.gray);
                }
                color++;
                chessBoardSquares[i][j].saveColor();
                panel.add(chessBoardSquares[i][j], gbc);
            }
            color--;
        }


        //MOVE HISTORY
        gbc.insets = new Insets(0,20,0,0);  //top padding
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 12;
        gbc.gridy = 3;
        JLabel movehistorytext = createLabel("Move history", 200, 10, new Color(255,255,100));
        movehistorytext.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(movehistorytext,gbc);
        gbc.gridy = 4;
        gbc.ipady = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 10;
        gbc.weightx = 1;
        listModel = new DefaultListModel<String>();
        JList<String> list = new JList<String>(listModel);
        JScrollPane moveHistory = new JScrollPane(list);
        moveHistory.setPreferredSize(new Dimension(200,200));
        panel.add(moveHistory, gbc);

        //HELP. OPTIONS AND UNDO
        createTopRightButtons();

        //TAKEN PIECES
        gbc.insets = new Insets(10,50,10,0);  //top padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.gridwidth = 12;
        gbc.gridheight = 2;
        takenpiecespanel = new JPanel(new FlowLayout());
        takenpiecespanel.setPreferredSize(new Dimension(250,61));
        takenpiecespanel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(takenpiecespanel,gbc);
    }

    private void createTopRightButtons(){
        gbc.insets = new Insets(0,0,10,0);  //top padding

        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 12;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        JButton helpButton = new JButton("HELP");
        helpButton.setIcon(new ImageIcon("pictures/helpButton.png"));
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "Goal of the game is to capture the opponent's king.\n" +
                                "Click a piece to see its movement\n\n" +
                                "Here are a few tips and tricks to help you get started:\n" +
                                "- Control the center of the board\n" +
                                "- The one controlling more space usually has the advantage\n" +
                                "- Some pieces are more valuable than others\n" +
                                "- Aim to trap your opponent's king\n" +
                                "- Have fun and learn!", "Help",JOptionPane.PLAIN_MESSAGE);
            }
        });

        panel.add(helpButton,gbc);



        gbc.gridy = 1;
        JButton options = new JButton("OPTIONS");
        options.setIcon(new ImageIcon("pictures/optionsButton.png"));
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkbox1 = new JCheckBox("Colourblind mode");
                checkbox1.setBounds(150,100, 50,50);
                frame.add(checkbox1);;
                checkbox1.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if(newGameButton.getBackground().equals(new Color(255, 196, 166))){
                            newGameButton.setBackground(new Color(166,196,255));
                        } else {
                            newGameButton.setBackground(new Color(255,196,166));
                        }

                        if(movecolor.equals(new Color(155,255,155))){
                            movecolor = new Color(255,155,255);
                        } else {
                            movecolor = new Color(155,255,155);
                        }

                    }
                });
                JOptionPane.showMessageDialog(frame,
                        checkbox1, "Options",JOptionPane.PLAIN_MESSAGE);
            }
        });
        panel.add(options,gbc);

        gbc.gridy = 2;
        JButton undo = new JButton("UNDO");
        undo.setIcon(new ImageIcon("pictures/undoButton.png"));
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(vsComputer){
                    undoMoveUI();
                }
                undoMoveUI();
            }
        });
        panel.add(undo,gbc);


    }

    private void creteNewGameWindow(){
        JPanel newGameWindow = new JPanel(new GridBagLayout());
        newGameButton = new JButton("NEW GAME");
        newGameButton.setBackground(new Color(255, 196, 166));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gameInProgress){
                    JOptionPane.showMessageDialog(frame,
                            newGameWindow, "New Game",JOptionPane.PLAIN_MESSAGE);

                } else {
                    board = new Board();
                    for (Square[] array : chessBoardSquares){
                        for (Square square : array){
                            square.removeAll();
                        }
                    }
                    gameInProgress = false;
                    newGameButton.setText("New Game");
                    listModel.clear();
                    panel.validate();
                    panel.repaint();
                }
            }
        });

        JButton vsHumanButton = new JButton("Play vs human");
        vsHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                updateBoard();
                panel.validate();
                panel.repaint();
                gameInProgress = true;
                vsComputer = false;
                newGameButton.setText("Reset");
            }
        });


        JButton vsComputerEasy = new JButton("Difficulty: Easy");
        vsComputerEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                updateBoard();
                panel.validate();
                panel.repaint();
                gameInProgress = true;
                vsComputer = true;
                difficulty = 1;
                newGameButton.setText("Reset");
            }
        });

        JButton vsComputerMedium = new JButton("Difficulty: Medium");
        vsComputerMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                updateBoard();
                panel.validate();
                panel.repaint();
                gameInProgress = true;
                vsComputer = true;
                difficulty = 3;
                newGameButton.setText("Reset");
            }
        });
        JButton vsComputerHard = new JButton("Difficulty: Hard");
        vsComputerHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                updateBoard();
                panel.validate();
                panel.repaint();
                gameInProgress = true;
                vsComputer = true;
                difficulty = 5;
                newGameButton.setText("Reset");
            }
        });

        GridBagConstraints ngwc = new GridBagConstraints();

        ngwc.fill = GridBagConstraints.NONE;
        ngwc.weighty = 2;
        ngwc.weightx = 2;

        ngwc.gridy = 0;
        ngwc.gridx = 1;

        newGameWindow.add(new JLabel("NEW GAME",SwingConstants.CENTER), ngwc);

        ngwc.gridy = 1;
        newGameWindow.add(vsHumanButton, ngwc);

        ngwc.gridx = 1;
        ngwc.gridy = 2;

        newGameWindow.add(new JLabel("\nPLAY VS COMPUTER",SwingConstants.CENTER), ngwc);

        ngwc.gridx = 0;
        ngwc.gridy = 5;
        newGameWindow.add(vsComputerEasy, ngwc);

        ngwc.gridx = 1;
        ngwc.gridy = 5;
        newGameWindow.add(vsComputerMedium, ngwc);

        ngwc.gridx = 3;
        ngwc.gridy = 5;
        newGameWindow.add(vsComputerHard, ngwc);

        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        panel.add(newGameButton,gbc);
    }

    //gets all possible moves for 1 piece. Shows possible moves on the board.
    public void showPossibleMoves(int c, int r){
        List<Move> possiblemoves = board.getPossibleMovesOnePiece(c,r);

        for (Move move : possiblemoves){
            chessBoardSquares[move.getEnd_column()][move.getEnd_row()].changeColor(movecolor);
        }
    }

    //adds all the pieces on to the board
    public void updateBoard(){
        currentboard = board.getBoard();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                if(currentboard[j][i] != null){
                    chessBoardSquares[j][i].add(new JLabel(currentboard[j][i].getIcon()));
                }
            }
        }
    }

    //undoes a move and draws the updated board state
    public void undoMoveUI(){
        Stack<Move> moveHistory = board.getMoveHistory();

        if(moveHistory.empty()){
            return;
        }

        Move undoThisMove = moveHistory.peek();

        chessBoardSquares[undoThisMove.getEnd_column()][undoThisMove.getEnd_row()].removeAll();
        if(undoThisMove.getTakenPiece() != null){
            chessBoardSquares[undoThisMove.getEnd_column()][undoThisMove.getEnd_row()].add(new JLabel(undoThisMove.getTakenPiece().getIcon()));
        }
        chessBoardSquares[undoThisMove.getStart_column()][undoThisMove.getStart_row()].removeAll();
        chessBoardSquares[undoThisMove.getStart_column()][undoThisMove.getStart_row()].add(new JLabel(undoThisMove.getMovingPiece().getIcon()));
        board.undoMove();

        listModel.remove(0);

        changeTurn();
        panel.validate();
        panel.repaint();
    }

    //computer makes a move
    public void computerMove(int difficulty){
        Move compMove = board.computerMove(difficulty);

        chessBoardSquares[compMove.getEnd_column()][compMove.getEnd_row()].removeAll();
        chessBoardSquares[compMove.getEnd_column()][compMove.getEnd_row()].add(new JLabel(compMove.getMovingPiece().getIcon()));
        chessBoardSquares[compMove.getStart_column()][compMove.getStart_row()].removeAll();

        addMoveToMoveHistory(compMove);
        panel.validate();
        panel.repaint();

        changeTurn();
    }

    //swaps turn
    public void changeTurn(){
        if(currentTurn == 'w'){
            currentTurn = 'b';
            turnlabel.setText("Black's turn");
        } else {
            currentTurn = 'w';
            turnlabel.setText("White's turn");
        }

        board.setCurrentTurn(currentTurn);
        if(board.testMate()){
            JOptionPane.showMessageDialog(frame,
                    "CHECKMATE!", "CHECKMATE",JOptionPane.PLAIN_MESSAGE);
        }

        if(board.testCheck()){
            turnlabel.setText(turnlabel.getText() + " CHECK");
        }
    }

    //adds a move to the move history
    public void addMoveToMoveHistory(Move move){
        String startSquare = Character.toString((char) (move.getStart_column() + 97)) + (8 - move.getStart_row());
        String endSquare = Character.toString((char) (move.getEnd_column() + 97)) + (8 - move.getEnd_row());
        String color;
        if(currentTurn == 'w'){
            color = " WHITE";
        } else {
            color = " BLACK";
        }
        listModel.add(0,startSquare + " => " + endSquare + color);

        if(move.getTakenPiece() != null){
            JLabel imagepic = new JLabel(new ImageIcon(move.getTakenPiece().getIcon().getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT)));
            imagepic.setSize(25,25);
            takenpiecespanel.add(imagepic);
            frame.pack();
        }


    }

    public static void main(String args[]) {
        Game test = new Game();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    //inspiration for the chess piece dragging from https://gist.github.com/skd1993/288fb21707263ae03799
    @Override
    public void mousePressed(MouseEvent e) {
        //Starts a new move
        currentPiece = null;
        startingSquare = null;

        int x = e.getX();
        int y = e.getY();


        //BOARD BOUNDS
        if(x < 100 || y <  50|| x > 500 || y > 500){
            return;
        }

        Component c =  panel.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) return;

        Square parent = (Square) c.getParent();

        if(currentboard[parent.getColumn()][parent.getRow()].getColor() != currentTurn){
            return;
        }


        //Saves starting square
        startingSquare = parent;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        currentPiece = (JLabel)c;
        currentPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);

        //Changes cursor to piece image
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        BufferedImage image = new BufferedImage(((JLabel) c).getIcon().getIconWidth(), ((JLabel) c).getIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        ((JLabel) c).getIcon().paintIcon(new JPanel(), image.getGraphics(), 0, 0);

        Cursor a = toolkit.createCustomCursor(image, new Point(this.getX(),this.getY()), "");
        panel.setCursor(a);

        showPossibleMoves(startingSquare.getColumn(),startingSquare.getRow());
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (currentPiece == null) return;
        int x = me.getX() + xAdjustment;
        int xMax = panel.getWidth() - currentPiece.getWidth();
        x = Math.min(x, xMax);
        x = Math.max(x, 0);

        int y = me.getY() + yAdjustment;
        int yMax = panel.getHeight() - currentPiece.getHeight();
        y = Math.min(y, yMax);
        y = Math.max(y, 0);
        currentPiece.setLocation(x, y);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if(startingSquare != null){
            showPossibleMoves(startingSquare.getColumn(),startingSquare.getRow());
        }


        panel.setCursor(null);

        if (currentPiece == null) return;

        //  Make sure the chess piece is no longer painted on the layered pane
        currentPiece.setVisible(false);
        panel.remove(currentPiece);
        currentPiece.setVisible(true);

        //  The drop location should be within the bounds of the chess board
        int xMax = panel.getWidth() - currentPiece.getWidth();
        int x = Math.min(e.getX(), xMax);
        x = Math.max(x, 0);

        int yMax = panel.getHeight() - currentPiece.getHeight();
        int y = Math.min(e.getY(), yMax);
        y = Math.max(y, 0);

        //BOARD BOUNDS
        if(x < 100 || y <  50|| x > 500 || y > 460){
            return;
        }

        Component c =  panel.findComponentAt(x, y);
        Square endingSquare = new Square(0,0);
        Square parent;

        if (c instanceof Square){
            parent = (Square) c;
            endingSquare = parent;
        }
        if(c instanceof JLabel){
            parent = ((Square) c.getParent());
            endingSquare = parent;
        }

        //creates a new Move-object
        Move currentMove = new Move(startingSquare.getColumn(),
                        startingSquare.getRow(),
                        endingSquare.getColumn(),
                        endingSquare.getRow(),
                        currentboard[startingSquare.getColumn()][startingSquare.getRow()]);

        //returns if move is not possible
        if(!board.movePiece(currentMove)){
            return;
        }

        currentboard = board.getBoard();

        //draws the updated board state
        if (c instanceof JLabel)
        {
            parent = (Square) c.getParent();
            parent.remove(0);
            parent.add( currentPiece );
            parent.validate();
        }
        else
        {
            assert c instanceof Square;
            parent = (Square) c;
            parent.add( currentPiece );
            parent.validate();
        }

        addMoveToMoveHistory(currentMove);

        changeTurn();

        //computer possibly makes a move
        if(vsComputer){
            computerMove(difficulty);
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}