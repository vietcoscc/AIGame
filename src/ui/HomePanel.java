package ui;

import sounds.SoundManager;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ui.PanelManager.GAME_PANEL;

public class HomePanel extends JPanel implements ActionListener {
    public static final int W_BUTTON = 150;
    public static final int H_BUTTON = 50;

    private final ImageIcon imagePlay = new ImageIcon(getClass().getResource("/images/Play.png"));
    private final ImageIcon imagePlay2 = new ImageIcon(getClass().getResource("/images/Play2.png"));
    private final ImageIcon imageOption = new ImageIcon(getClass().getResource("/images/Option.png"));
    private final ImageIcon imageOption2 = new ImageIcon(getClass().getResource("/images/Option2.png"));
    private final ImageIcon imageExit = new ImageIcon(getClass().getResource("/images/Exit.png"));
    private final ImageIcon imageExit2 = new ImageIcon(getClass().getResource("/images/Exit2.png"));
    private final Image imageBackground = new ImageIcon(getClass().getResource("/images/board.jpg")).getImage();

    private JButton btnPlay, btnOption, btnExit;
    private CardLayout cardLayout;
    private Container container;

    public HomePanel(Container container, CardLayout cardLayout) {
        this.container = container;
        this.cardLayout = cardLayout;
        setLayout(null);
        initComponent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(imageBackground, 0, 0, getWidth(), getHeight(), null);
    }

    private void initComponent() {


        btnPlay = new JButton(imagePlay);
        btnPlay.setSize(W_BUTTON, H_BUTTON);
        btnPlay.setLocation(MainFrame.WIDTH / 2 - btnPlay.getWidth() / 2, 300);
        btnPlay.setActionCommand("Play");
        btnPlay.addActionListener(this);
        btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPlay.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnPlay.setIcon(imagePlay2);
                Clip clip = SoundManager.getSound(getClass().getResource("/sounds/button.wav"));
                clip.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPlay.setIcon(imagePlay);
            }
        });
        // phim option trong menu chinh
        btnOption = new JButton(imageOption);
        btnOption.setSize(W_BUTTON, H_BUTTON);
        btnOption.setLocation(MainFrame.WIDTH / 2 - btnOption.getWidth() / 2, 400);
        btnOption.setActionCommand("Option");
        btnOption.addActionListener(this);
        btnOption.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOption.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnOption.setIcon(imageOption2);
                Clip clip = SoundManager.getSound(getClass().getResource("/sounds/button.wav"));
                clip.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnOption.setIcon(imageOption);
            }
        });
        // phim exit trong menu chinh
        btnExit = new JButton(imageExit);
        btnExit.setSize(W_BUTTON, H_BUTTON);
        btnExit.setLocation(MainFrame.WIDTH / 2 - btnExit.getWidth() / 2, 500);
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
        add(btnPlay);
        add(btnOption);
        add(btnExit);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Play":
                cardLayout.show(container, GAME_PANEL);
                break;
            case "Option":
                int result = JOptionPane.showConfirmDialog(this, "Nhấn 'Yes' để chơi với máy\n Nhấn 'No' để chơi 2 người", "", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    GamePanel.isPlayingWithComputer = true;
                    PanelManager panelManager = (PanelManager) container;
                    panelManager.resetGame();

                } else {
                    GamePanel.isPlayingWithComputer = false;
                    PanelManager panelManager = (PanelManager) container;
                    panelManager.resetGame();
                }

                break;
            case "Exit":
                System.exit(0);
                break;

        }
    }
}
