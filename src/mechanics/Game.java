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
        frame.setSize(900, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chessBoardSquares = new JButton[8][8];
        panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.LAST_LINE_START;
        for(int i = 0;i < 8; i++){
            for (int j = 0; j < 8; j++){
                gbc.gridx = i;
                gbc.gridy = j + 2;
                JButton button = new JButton(" ");
                panel.add(button, gbc);
                chessBoardSquares[i][j] = button;
            }
        }





    }

    public static void main(String args[]) {
        Game test = new Game();
    }
}