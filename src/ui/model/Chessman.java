package ui.model;

import javax.swing.*;
import java.awt.*;

public class Chessman {
    public final Image CHESSMAN_A = new ImageIcon(getClass().getResource("/images/chessman_a.png")).getImage();
    public final Image CHESSMAN_B = new ImageIcon(getClass().getResource("/images/chessman_b.png")).getImage();

    private int pos_i, pos_j; // Tọa độ (vị trí) trên bàn cờ (0-7)
    private boolean isYours; // check quân cờ là bên mình hay bên đối phương

    private int x, y;

    public Chessman(int pos_i, int pos_j, boolean isYours) {
        this.pos_i = pos_i;
        this.pos_j = pos_j;
        this.isYours = isYours;
        x = Chessbox.BOX_WIDTH * pos_i;
        y = Chessbox.BOX_HEIGHT * pos_j;

    }

    public void draw(Graphics2D graphics2D) {
        if (isYours) {
            graphics2D.drawImage(CHESSMAN_A, x + 10, y + 10, Chessbox.BOX_WIDTH - 20, Chessbox.BOX_HEIGHT - 20, null);
        } else {
            graphics2D.drawImage(CHESSMAN_B, x + 10, y + 10, Chessbox.BOX_WIDTH - 20, Chessbox.BOX_HEIGHT - 20, null);
        }
    }

    public void setPositionIJ(int pos_i, int pos_j) {
        this.pos_i = pos_i;
        this.pos_j = pos_j;
        x = Chessbox.BOX_WIDTH * pos_i;
        y = Chessbox.BOX_HEIGHT * pos_j;
    }

    public int getPos_i() {
        return pos_i;
    }

    public int getPos_j() {
        return pos_j;
    }

    public boolean isYours() {
        return isYours;
    }

    public void setYours(boolean yours) {
        isYours = yours;
    }
}
