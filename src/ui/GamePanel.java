package ui;


import sounds.SoundManager;
import ui.model.Chessboard;
import ui.model.Chessbox;
import ui.model.Chessman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ui.HomePanel.H_BUTTON;
import static ui.HomePanel.W_BUTTON;
import static ui.PanelManager.HOME_PANEL;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
    private final ImageIcon imageExit = new ImageIcon(getClass().getResource("/images/Exit.png"));
    private final ImageIcon imageExit2 = new ImageIcon(getClass().getResource("/images/Exit2.png"));


    private CardLayout cardLayout; // chuyển đổi qua lại giữa các panel
    private Container container; // chuyển đổi qua lại giữa các panel
    private JButton btnExit;
    private int panelWidth;
    private int panelHeight;
    //
    private Chessboard chessboard; // Bàn cờ
    private Chessman[] opponent_chess, player_chess; //  Quân cờ của 2 bên
    //
    public static boolean isPlayingWithComputer = false; // Yes là chơi với máy , No là chơi 2 người với nhau
    private boolean isInitial = false; // Biến check khởi tạo ban đầu
    private int currentMouse_i = -1, currentMouse_j = -1; // tọa độ của con trỏ chuột trên bàn cờ (Màu xanh)
    private int currentSelected_i = -1, currentSelected_j = -1; // tọa độ của ô cờ được chọn (Màu đỏ)

    //
    public GamePanel(Container container, CardLayout cardLayout) {
        this.container = container;
        this.cardLayout = cardLayout;
        showDialogPlayingMode();
        setLayout(null);
        initComponent();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        panelWidth = getWidth();
        panelHeight = getHeight();
        if (!isInitial) {
            initComponent();
            isInitial = true;
        }
        drawChessboard(graphics2D);

    }

    public void showDialogPlayingMode() {

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
        chessboard.getChessboxes()[0][0].setHasChessman(true);
        opponent_chess[1] = new Chessman(4, 0, false);
        chessboard.getChessboxes()[4][0].setHasChessman(true);
        opponent_chess[2] = new Chessman(7, 0, false);
        chessboard.getChessboxes()[7][0].setHasChessman(true);
        player_chess = new Chessman[3];
        player_chess[0] = new Chessman(0, 7, true);
        chessboard.getChessboxes()[0][7].setHasChessman(true);
        player_chess[1] = new Chessman(3, 7, true);
        chessboard.getChessboxes()[3][7].setHasChessman(true);
        player_chess[2] = new Chessman(7, 7, true);
        chessboard.getChessboxes()[7][7].setHasChessman(true);

//        setBackground(Color.GRAY);
        btnExit = new JButton(imageExit);
        btnExit.setSize(W_BUTTON, H_BUTTON);
        btnExit.setLocation(getHeight() + (getWidth() - btnExit.getWidth() - getHeight()) / 2, getHeight() - btnExit.getHeight());
        btnExit.setActionCommand("Exit");
        btnExit.addActionListener(this);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnExit.setIcon(imageExit2);
                SoundManager.getSound(getClass().getResource("/sounds/button.wav")).start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setIcon(imageExit);
            }
        });
        add(btnExit);

        removeMouseListener(this);
        removeMouseMotionListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                initComponent();
                cardLayout.show(container, HOME_PANEL);
                break;
            default:
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("mouseMoved");
        int pos_i = e.getX() / Chessbox.BOX_WIDTH;
        int pos_j = e.getY() / Chessbox.BOX_HEIGHT;
        System.out.println(pos_i + ":" + pos_j);
        chessboard.resetMouseEnterdState(currentMouse_i, currentMouse_j);
        currentMouse_i = -1;
        currentMouse_j = -1;
        if (pos_i >= 0 && pos_i < 8 && pos_j >= 0 && pos_j < 8) {
            currentMouse_i = pos_i;
            currentMouse_j = pos_j;
        }
        chessboard.setMouseEntered(currentMouse_i, currentMouse_j);
        repaint();
    }

    public void moveChessman(int fromI, int fromJ, int toI, int toJ) {
        for (int i = 0; i < 3; i++) {
            if (player_chess[i].getPos_i() == fromI && player_chess[i].getPos_j() == fromJ) {
                player_chess[i].setPositionIJ(toI, toJ);
                chessboard.getChessboxes()[fromI][fromJ].setHasChessman(false);
                chessboard.getChessboxes()[toI][toJ].setHasChessman(true);
                chessboard.resetChessboxSelected(fromI, fromJ);
                chessboard.setMovableBox(fromI, fromJ, false);
                repaint();
                return;
            }
            if (opponent_chess[i].getPos_i() == fromI && opponent_chess[i].getPos_j() == fromJ) {
                opponent_chess[i].setPositionIJ(toI, toJ);
                chessboard.getChessboxes()[fromI][fromJ].setHasChessman(false);
                chessboard.getChessboxes()[toI][toJ].setHasChessman(true);
                chessboard.resetChessboxSelected(fromI, fromJ);
                chessboard.setMovableBox(fromI, fromJ, false);
                repaint();
                return;
            }
        }
    }

    private Chessman getChessman(int pos_i, int pos_j) {
        if (chessboard.getChessboxes()[pos_i][pos_j].hasChessman()) {
            for (int i = 0; i < 3; i++) {
                if (player_chess[i].getPos_i() == pos_i && player_chess[i].getPos_j() == pos_j) {
                    return player_chess[i];
                }
                if (opponent_chess[i].getPos_i() == pos_i && opponent_chess[i].getPos_j() == pos_j) {
                    return opponent_chess[i];
                }
            }
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("mouseClicked");
        int pos_i = e.getX() / Chessbox.BOX_WIDTH;
        int pos_j = e.getY() / Chessbox.BOX_HEIGHT;
        if (isPlayingWithComputer) {
            if (getChessman(pos_i, pos_j) != null && !getChessman(pos_i, pos_j).isYours()) {
                return;
            }
        }
        if (chessboard.getChessboxes()[pos_i][pos_j].isMovable()) {
            moveChessman(currentSelected_i, currentSelected_j, pos_i, pos_j);
            return;
        }
        if (pos_i == currentSelected_i && pos_j == currentSelected_j) {
            if (chessboard.getChessboxes()[currentSelected_i][currentSelected_j].isSelected()) {
                chessboard.resetChessboxSelected(currentSelected_i, currentSelected_j);
                chessboard.setMovableBox(currentSelected_i, currentSelected_j, false);
                currentSelected_i = -1;
                currentSelected_j = -1;

            } else {
                chessboard.setChessboxSelected(currentSelected_i, currentSelected_j);
                if (chessboard.getChessboxes()[currentSelected_i][currentSelected_j].hasChessman()) {
                    chessboard.setMovableBox(currentSelected_i, currentSelected_j, true);
                }
            }

        } else {
            chessboard.resetChessboxSelected(currentSelected_i, currentSelected_j);
            chessboard.setMovableBox(currentSelected_i, currentSelected_j, false);
            currentSelected_i = -1;
            currentSelected_j = -1;

            if (pos_i >= 0 && pos_i < 8 && pos_j >= 0 && pos_j < 8) {
                currentSelected_i = pos_i;
                currentSelected_j = pos_j;
                chessboard.setChessboxSelected(currentSelected_i, currentSelected_j);
                if (chessboard.getChessboxes()[currentSelected_i][currentSelected_j].hasChessman()) {
                    chessboard.setMovableBox(currentSelected_i, currentSelected_j, true);
                }
            }
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
