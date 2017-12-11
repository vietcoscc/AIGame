package ui.model;

import javax.swing.*;
import java.awt.*;

public class Chessman {
    public final Image CHESSMAN_A = new ImageIcon(getClass().getResource("/images/chessman_a.png")).getImage();
    public final Image CHESSMAN_B = new ImageIcon(getClass().getResource("/images/chessman_b.png")).getImage();

    private int pos_i, pos_j;
    private boolean isYours;

    private int x, y;

    public Chessman(int pos_i, int pos_j,boolean isYours) {
        this.pos_i = pos_i;
        this.pos_j = pos_j;
        this.isYours = isYours;
        x = Chessbox.BOX_WIDTH * pos_i;
        y = Chessbox.BOX_HEIGHT * pos_j;

    }

    public void draw(Graphics2D graphics2D) {
        if (isYours) {
            graphics2D.drawImage(CHESSMAN_A, x+10, y+10, Chessbox.BOX_WIDTH-20, Chessbox.BOX_HEIGHT-20, null);
        } else {
            graphics2D.drawImage(CHESSMAN_B, x+10, y+10, Chessbox.BOX_WIDTH-20, Chessbox.BOX_HEIGHT-20, null);
        }
    }

    public int getPos_i() {
        return pos_i;
    }

    public void setPos_i(int pos_i) {
        this.pos_i = pos_i;
    }

    public int getPos_j() {
        return pos_j;
    }

    public void setPos_j(int pos_j) {
        this.pos_j = pos_j;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
