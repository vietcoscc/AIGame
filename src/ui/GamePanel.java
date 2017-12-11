package ui;


import sounds.SoundManager;
import ui.model.Chessboard;
import ui.model.Chessman;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ui.HomePanel.H_BUTTON;
import static ui.HomePanel.W_BUTTON;
import static ui.PanelManager.HOME_PANEL;

public class GamePanel extends JPanel implements ActionListener {
    private final ImageIcon imageExit = new ImageIcon(getClass().getResource("/images/Exit.png"));
    private final ImageIcon imageExit2 = new ImageIcon(getClass().getResource("/images/Exit2.png"));


    private CardLayout cardLayout;
    private Container container;
    private JButton btnExit;
    private int panelWidth;
    private int panelHeight;
    private Chessboard chessboard;
    private Chessman[] opponent_chess, player_chess;
    private boolean isInitial = false;
    public GamePanel(Container container, CardLayout cardLayout) {
        this.container = container;
        this.cardLayout = cardLayout;
        setLayout(null);
        initComponent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!isInitial){
            initComponent();
            isInitial = true;
        }
        System.out.println("paintComponent");
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        panelWidth = getWidth();
        panelHeight = getHeight();
        drawChessboard(graphics2D);
    }

    private void drawChessboard(Graphics2D graphics2D) {
        chessboard.draw(graphics2D);
        for (int i = 0; i < 3; i++) {
            opponent_chess[i].draw(graphics2D);
            player_chess[i].draw(graphics2D);
        }
    }

    private void initComponent() {

        chessboard = new Chessboard();
        opponent_chess = new Chessman[3];
        opponent_chess[0] = new Chessman(0, 0, false);
        opponent_chess[1] = new Chessman(4, 0, false);
        opponent_chess[2] = new Chessman(7, 0, false);
        player_chess = new Chessman[3];
        player_chess[0] = new Chessman(0, 7, true);
        player_chess[1] = new Chessman(3, 7, true);
        player_chess[2] = new Chessman(7, 7, true);

        setBackground(Color.GRAY);
        btnExit = new JButton(imageExit);
        btnExit.setSize(W_BUTTON, H_BUTTON);
        btnExit.setLocation(getHeight() + (getWidth() - btnExit.getWidth() - getHeight()) / 2, getHeight() - btnExit.getHeight());
        btnExit.setActionCommand("Exit");
        btnExit.addActionListener(this);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnExit.setIcon(imageExit2);
                Clip clip = SoundManager.getSound(getClass().getResource("/sounds/button.wav"));
                clip.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setIcon(imageExit);
            }
        });
        add(btnExit);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                cardLayout.show(container, HOME_PANEL);
                break;
            default:
        }
    }
}
