package ui.model;

import java.awt.*;

public class Chessboard {
    public static final int LENGTH = 8;
    private Chessbox[][] chessboxes = new Chessbox[LENGTH][LENGTH]; // mảng các ô cờ


    public Chessboard() {
        initChessboard();
    }

    private void initChessboard() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                chessboxes[i][j] = new Chessbox(i, j);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                chessboxes[i][j].draw(graphics2D);
            }
        }
    }

    public void resetMouseEnterdState(int i, int j) {
        if (i >= 0 && j >= 0 && i < 8 && j < 8) {
            chessboxes[i][j].setHasMouseEntered(false);
        }

    }

    public void resetChessboxSelected(int i, int j) {
        if (i >= 0 && j >= 0 && i < 8 && j < 8) {
            chessboxes[i][j].setSelected(false);
        }
    }

    public Chessbox[][] getChessboxes() {
        return chessboxes;
    }

    public void setMouseEntered(int i, int j) {
        if (i >= 0 && j >= 0 && i < 8 && j < 8) {
            chessboxes[i][j].setHasMouseEntered(true);
        }
    }

    public void setChessboxSelected(int i, int j) {
        if (i >= 0 && j >= 0 && i < 8 && j < 8) {
            chessboxes[i][j].setSelected(true);
        }
    }

    public void setMovableBox(int pos_i, int pos_j, boolean isMovable) {
        if (isMovable == false) {
            for (int i = 0; i < LENGTH; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    chessboxes[i][j].setMovable(isMovable);
                }
            }
            return;
        }
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if ((i == pos_i + 1) && (j == pos_j - 2) || (i == pos_i - 1) && (j == pos_j - 2)) {
                    if (!chessboxes[i][j].hasChessman()) {
                        if (pos_j - 1 >= 0&&(!chessboxes[pos_i][pos_j-1].hasChessman())) {
                            chessboxes[i][j].setMovable(isMovable);
                        }
                    }
                }
                if ((i == pos_i + 1) && (j == pos_j + 2) || (i == pos_i - 1) && (j == pos_j + 2)) {
                    if (!chessboxes[i][j].hasChessman()) {
                        if (pos_j + 1 < 8&&(!chessboxes[pos_i][pos_j+1].hasChessman())) {
                            chessboxes[i][j].setMovable(isMovable);
                        }
                    }
                }
                if ((i == pos_i + 2) && (j == pos_j + 1) || (i == pos_i + 2) && (j == pos_j - 1)) {
                    if (!chessboxes[i][j].hasChessman()) {
                        if (pos_i + 1 <8&&(!chessboxes[pos_i+1][pos_j].hasChessman())) {
                            chessboxes[i][j].setMovable(isMovable);
                        }
                    }
                }
                if ((i == pos_i - 2) && (j == pos_j + 1) || (i == pos_i - 2) && (j == pos_j - 1)) {
                    if (!chessboxes[i][j].hasChessman()) {
                        if (pos_i - 1 >= 0&&(!chessboxes[pos_i-1][pos_j].hasChessman())) {
                            chessboxes[i][j].setMovable(isMovable);
                        }
                    }
                }
            }
        }
    }
}