package mechanics;

import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements MouseListener, MouseMotionListener, ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JPanel[][] chessBoardSquares;
    private JLabel currentPiece;
    private Board board;
    private int xAdjustment;
    private int yAdjustment;

    public Game() {
        InterfaceSetUp();

        frame.add(panel);
        frame.setVisible(true);


    }

    //REMOVE THIS LATER
    public JLabel createLabel(String txt, int width, int height, Color color)
    {
        JLabel label = new JLabel(txt);
        label.setOpaque(true);
        label.setBackground(color);
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }


    public void InterfaceSetUp() {
        frame = new JFrame();
        frame.setTitle("Chessboard");
        frame.setSize(1000, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.addMouseListener(this);


        chessBoardSquares = new JPanel[8][8];

        GridBagConstraints gbc = new GridBagConstraints();
        //NEW GAME BUTTON
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 1;       //reset to default
        gbc.ipady = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(100,20,0,0);  //top padding
        JButton b = new JButton("NEW GAME");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                updateBoard();
                panel.validate();
                panel.repaint();
            }
        });
        panel.add(b,gbc);

        //ALPHABET
        gbc.insets = new Insets(0,100,0,0);  //top padding
        gbc.anchor = GridBagConstraints.LINE_START; //bottom of space

        gbc.weightx = 0;
        gbc.weighty = 0;
        int color = 0;

        for(int i = 8;i > 0; i--){
            gbc.gridx = 0;
            gbc.gridy = 9 - i;
            JLabel square = createLabel(Integer.toString(i),50,50,Color.white);
            panel.add(square, gbc);
        }
        gbc.insets = new Insets(0,0,0,0);  //top padding

        //BOARD
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                gbc.gridx = i + 1;
                gbc.gridy = j + 1;
                chessBoardSquares[j][i] = new JPanel();
                chessBoardSquares[j][i].setPreferredSize(new Dimension(50, 50));
                if(color % 2 == 1){
                    chessBoardSquares[j][i].setBackground(Color.white);
                } else {
                    chessBoardSquares[j][i].setBackground(Color.black);
                }
                color++;
                panel.add(chessBoardSquares[j][i], gbc);
            }
            color--;
        }



        for(int i = 0;i < 8; i++){
            gbc.gridx = i + 1;
            gbc.gridy = 9;
            JLabel square = createLabel(Character.toString((char) ('A' + i)),50,50,Color.white);
            panel.add(square, gbc);
        }

        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 12;
        gbc.gridy = 1;
        gbc.ipady = 30;
        gbc.gridwidth = 5;
        gbc.gridheight = 10;
        gbc.weightx = 1;

        JLabel movehistory = createLabel(" ",200,50,Color.gray);
        panel.add(movehistory, gbc);
    }

    public void updateBoard(){
        Piece[][] currentboard = board.getBoard();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8;j++){
                if(currentboard[j][i] != null){
                    chessBoardSquares[j][i].add(new JLabel(currentboard[j][i].getIcon()));
                }
            }
        }
    }

    public static void main(String args[]) {
        Game test = new Game();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        currentPiece = null;

        Component c =  panel.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel) return;

        System.out.println("hi");

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        currentPiece = (JLabel)c;
        currentPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);

        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        Cursor a = toolkit.createCustomCursor(new ImageIcon("C:/Users/Lauri/IdeaProjects/chess/src/pieces/pictures/whiteRook.png").getImage() , new Point(this.getX(),this.getY()), "");
        panel.setCursor(a);

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if (currentPiece == null) return;

        //  The drag location should be within the bounds of the chess board

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

        Component c =  panel.findComponentAt(x, y);

        if (c instanceof JLabel)
        {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( currentPiece );
            parent.validate();
        }
        else
        {
            Container parent = (Container)c;
            parent.add( currentPiece );
            parent.validate();
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