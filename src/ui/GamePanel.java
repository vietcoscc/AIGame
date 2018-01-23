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
    private TextField textInfo;
    private int panelWidth;
    private int panelHeight;

    //
    private Chessboard chessboard; // Bàn cờ
    private Chessman[] opponentChess, playerChess; //  Quân cờ của 2 bên
    //
    public static boolean isPlayingWithComputer = false; // Yes là chơi với máy , No là chơi 2 người với nhau
    private boolean isInitial = false; // Biến check khởi tạo ban đầu
    private int currentMouseI = -1, currentMouseJ = -1; // tọa độ của con trỏ chuột trên bàn cờ (Màu xanh)
    private int currentSelectedI = -1, currentSelectedJ = -1; // tọa độ của ô cờ được chọn (Màu đỏ)

    private boolean isPlayerTurn = true;
    private byte numMoves = 0;

    private  StateBoard stateBoard = StateBoard.getDefaultState();
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
            opponentChess[i].draw(graphics2D);
            playerChess[i].draw(graphics2D);
        }
    }

    private void initComponent() {
        // Khởi tạo bàn cờ
        chessboard = new Chessboard();
        opponentChess = new Chessman[3];
        opponentChess[0] = new Chessman(0, 0, false);
        chessboard.getChessboxes()[0][0].setHasChessman(true);
        opponentChess[1] = new Chessman(4, 0, false);
        chessboard.getChessboxes()[4][0].setHasChessman(true);
        opponentChess[2] = new Chessman(7, 0, false);
        chessboard.getChessboxes()[7][0].setHasChessman(true);
        playerChess = new Chessman[3];
        playerChess[0] = new Chessman(0, 7, true);
        chessboard.getChessboxes()[0][7].setHasChessman(true);
        playerChess[1] = new Chessman(3, 7, true);
        chessboard.getChessboxes()[3][7].setHasChessman(true);
        playerChess[2] = new Chessman(7, 7, true);
        chessboard.getChessboxes()[7][7].setHasChessman(true);

        //setBackground(Color.GRAY);
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
        chessboard.resetMouseEnterdState(currentMouseI, currentMouseJ);
        currentMouseI = -1;
        currentMouseJ = -1;
        if (pos_i >= 0 && pos_i < 8 && pos_j >= 0 && pos_j < 8) {
            currentMouseI = pos_i;
            currentMouseJ = pos_j;
        }
        chessboard.setMouseEntered(currentMouseI, currentMouseJ);
        repaint();
    }

    public void moveChessman(int fromI, int fromJ, int toI, int toJ) {
        Move move = new Move(fromI + fromJ * 8, toI + toJ*8);
        stateBoard.moveChess(move);
        stateBoard.printDebug();

        for (int i = 0; i < 3; i++) {
            if (playerChess[i].getPos_i() == fromI && playerChess[i].getPos_j() == fromJ) {
                playerChess[i].setPositionIJ(toI, toJ);
                chessboard.getChessboxes()[fromI][fromJ].setHasChessman(false);
                chessboard.getChessboxes()[toI][toJ].setHasChessman(true);
                chessboard.resetChessboxSelected(fromI, fromJ);
                chessboard.setMovableBox(fromI, fromJ, false);
                boolean moveToHelicopter = (stateBoard.getCellTypeAt(toI, toJ) == StateBoard.HELICOPTER_GREEN_CELL
                        && stateBoard.getChessAt(toI, toJ) == StateBoard.RED_CHESS) ||
                        (stateBoard.getCellTypeAt(toI, toJ) == StateBoard.HELICOPTER_RED_CELL
                                && stateBoard.getChessAt(toI, toJ) == StateBoard.GREEN_CHESS);

                if (!moveToHelicopter){
                    chessboard.setMovableBox(fromI, fromJ, false);
                    playerChess[i].setCanMoveThisTurn(false);
                }else {
                    currentSelectedI = toI;
                    currentSelectedJ = toJ;
                    chessboard.setChessboxSelected(currentSelectedI,currentSelectedJ);
                    for(int pos = 0; pos < 64; pos++){
                        int row = pos % 8;
                        int col = pos / 8;
                        int cellType = stateBoard.getCellTypeAt(row,col);

                        if(chessboard.getChessboxes()[row][col].isEmptyBox() && cellType != StateBoard.GOAL_RED_CELL ){
                            chessboard.getChessboxes()[row][col].setMovable(true);
                        }
                    }

                }
                endMove(moveToHelicopter);
                repaint();
                return;
            }
            if (opponentChess[i].getPos_i() == fromI && opponentChess[i].getPos_j() == fromJ) {
                opponentChess[i].setPositionIJ(toI, toJ);
                chessboard.getChessboxes()[fromI][fromJ].setHasChessman(false);
                chessboard.getChessboxes()[toI][toJ].setHasChessman(true);
                chessboard.resetChessboxSelected(fromI, fromJ);
                chessboard.setMovableBox(fromI, fromJ, false);
                boolean moveToHelicopter = (stateBoard.getCellTypeAt(toI, toJ) == StateBoard.HELICOPTER_GREEN_CELL
                        && stateBoard.getChessAt(toI, toJ) == StateBoard.RED_CHESS) ||
                        (stateBoard.getCellTypeAt(toI, toJ) == StateBoard.HELICOPTER_RED_CELL
                                && stateBoard.getChessAt(toI, toJ) == StateBoard.GREEN_CHESS);

                if (!moveToHelicopter){
                    chessboard.setMovableBox(fromI, fromJ, false);
                    opponentChess[i].setCanMoveThisTurn(false);
                }else {
                    currentSelectedI = toI;
                    currentSelectedJ = toJ;
                    chessboard.setChessboxSelected(currentSelectedI,currentSelectedJ);
                    for(int pos = 0; pos < 64; pos++){
                        int row = pos % 8;
                        int col = pos / 8;
                        int cellType = stateBoard.getCellTypeAt(row,col);

                        if(chessboard.getChessboxes()[row][col].isEmptyBox() && cellType != StateBoard.GOAL_GREEN_CELL ){
                            chessboard.getChessboxes()[row][col].setMovable(true);
                        }
                    }

                }
                endMove(moveToHelicopter);
                repaint();
                return;
            }
        }
    }

    private void endMove(boolean moveToHelicopter) {
        if (moveToHelicopter){
            return;
        }
        numMoves++;
        if (numMoves >= 2) {
            numMoves = 0;
            isPlayerTurn = !isPlayerTurn;
            for (int i = 0; i < 3; i++) {
                playerChess[i].setCanMoveThisTurn(true);
                opponentChess[i].setCanMoveThisTurn(true);
            }
        }
    }

    private Chessman getChessman(int pos_i, int pos_j) {
        if (pos_i == -1 || pos_j == -1) {
            return null;
        }
        if (chessboard.getChessboxes()[pos_i][pos_j].hasChessman()) {
            for (int i = 0; i < 3; i++) {
                if (playerChess[i].getPos_i() == pos_i && playerChess[i].getPos_j() == pos_j) {
                    return playerChess[i];
                }
                if (opponentChess[i].getPos_i() == pos_i && opponentChess[i].getPos_j() == pos_j) {
                    return opponentChess[i];
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

        if(getChessman(pos_i, pos_j) != null && getChessman(pos_i, pos_j).isYours() != isPlayerTurn){
            return;
        }

        Chessman currentChessman = getChessman(currentSelectedI, currentSelectedJ);
        if (currentChessman != null && currentChessman.canMoveThisTurn() &&
                chessboard.getChessboxes()[pos_i][pos_j].isMovable()) {

            moveChessman(currentSelectedI, currentSelectedJ, pos_i, pos_j);
            return;
        }
        if (pos_i == currentSelectedI && pos_j == currentSelectedJ) {
            if (chessboard.getChessboxes()[currentSelectedI][currentSelectedJ].isSelected()) {
                chessboard.resetChessboxSelected(currentSelectedI, currentSelectedJ);
                chessboard.setMovableBox(currentSelectedI, currentSelectedJ, false);
                currentSelectedI = -1;
                currentSelectedJ = -1;

            } else {
                chessboard.setChessboxSelected(currentSelectedI, currentSelectedJ);
                if (chessboard.getChessboxes()[currentSelectedI][currentSelectedJ].hasChessman()) {
                    chessboard.setMovableBox(currentSelectedI, currentSelectedJ, true);
                }
            }
        } else {
            chessboard.resetChessboxSelected(currentSelectedI, currentSelectedJ);
            chessboard.setMovableBox(currentSelectedI, currentSelectedJ, false);
            currentSelectedI = -1;
            currentSelectedJ = -1;

            if (pos_i >= 0 && pos_i < 8 && pos_j >= 0 && pos_j < 8) {
                currentSelectedI = pos_i;
                currentSelectedJ = pos_j;
                chessboard.setChessboxSelected(currentSelectedI, currentSelectedJ);
                if (chessboard.getChessboxes()[currentSelectedI][currentSelectedJ].hasChessman()) {
                    chessboard.setMovableBox(currentSelectedI, currentSelectedJ, true);
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
