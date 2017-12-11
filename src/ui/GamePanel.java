package ui;


import sounds.SoundManager;
import ui.model.Chessboard;

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
    private boolean isInitial = false;
    private Chessboard chessboard = new Chessboard();

    public GamePanel(Container container, CardLayout cardLayout) {
        this.container = container;
        this.cardLayout = cardLayout;
        setLayout(null);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        panelWidth = getWidth();
        panelHeight = getHeight();
        chessboard.draw(graphics2D);

        if (!isInitial) {
            initComponent();
            isInitial = true;
        }

    }

    private void initComponent() {
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
