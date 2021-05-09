package mechanics;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    private JFrame frame;

    private JPanel panel;
    private JButton[][] chessBoardSquares;

    public Game() {

        InterfaceSetUp();

        frame.add(panel);
        frame.setVisible(true);
    }
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

        chessBoardSquares = new JButton[8][8];
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

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
        panel.add(b,gbc);

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

        for(int i = 0;i < 8; i++){
            JLabel square;
            
            for (int j = 0; j < 8; j++){
                gbc.gridx = i + 1;
                gbc.gridy = j + 1;
                if(color % 2 == 1){
                    square = createLabel(" ",50,50,Color.black);
                } else {
                    square = createLabel(" ",50,50,Color.lightGray);
                }
                color++;
                panel.add(square, gbc);
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

    public static void main(String args[]) {
        Game test = new Game();
    }
}