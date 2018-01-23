package ui.model;

import javax.swing.*;
import java.awt.*;

public class Chessbox {
    public static final int BOX_WIDTH = 85; // Độ rộng của 1 ô cờ
    public static final int BOX_HEIGHT = 85; //
    private final Image imageGoal = new ImageIcon(getClass().getResource("/images/goal.jpg")).getImage();
    private final Image imageHelicopter = new ImageIcon(getClass().getResource("/images/helicopter_2.png")).getImage();

    private int pos_i, pos_j; // position i j
    private int x, y; //

    private boolean hasMouseEntered = false; // check con trỏ chuột có trỏ vào ô hay không
    private boolean isSelected = false; // check ô có được chọn hay không
    private boolean isMovable = false; // check xem có phải một ô gợi ý vị trí có thể đi
    private boolean hasChessman = false; // check xem ô có quân cờ đang nằm ở trên

    public Chessbox(int pos_i, int pos_j) {
        this.pos_i = pos_i;
        this.pos_j = pos_j;
        x = pos_i * BOX_WIDTH;
        y = pos_j * BOX_HEIGHT;
    }

    public void draw(Graphics2D graphics2D) {

        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setPaint(Color.WHITE);
        if ((pos_i % 2 == 0 && pos_j % 2 != 0) || (pos_i % 2 != 0 && pos_j % 2 == 0)) {
            graphics2D.setPaint(Color.GRAY);
        }
        //
        graphics2D.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.drawRect(x, y, BOX_WIDTH, BOX_HEIGHT);
        //

        //
        graphics2D.setPaint(Color.CYAN);
        graphics2D.drawString("(" + pos_i + "," + pos_j + ")", x, y + 50);
        if (isHelicopter()) {
            graphics2D.drawImage(imageHelicopter, x + 1, y + 1, BOX_WIDTH - 2, BOX_HEIGHT - 2, null);
        }
        if (isGoal()) {
            graphics2D.drawImage(imageGoal, x + 1, y + 1, BOX_WIDTH - 2, BOX_HEIGHT - 2, null);
        }
        //
        if (hasMouseEntered) {
            graphics2D.setPaint(Color.green);
            graphics2D.fillRect(x + 1, y + 1, BOX_WIDTH - 1, BOX_HEIGHT - 1);
        }
        if (isSelected) {
            graphics2D.setPaint(Color.red);
            graphics2D.fillRect(x + 1, y + 1, BOX_WIDTH - 1, BOX_HEIGHT - 1);
        }
        if (isMovable) {
            graphics2D.setPaint(Color.CYAN);
            graphics2D.fillRect(x + 1, y + 1, BOX_WIDTH - 1, BOX_HEIGHT - 1);
        }

    }


    public boolean isGoal() {
        if ((pos_i == 4 && pos_j == 0) || (pos_i == 3 && pos_j == Chessboard.LENGTH - 1)) {
            return true;
        }
        return false;
    }

    public boolean isHelicopter() {
        if ((pos_i == 3 && pos_j == 0) || (pos_i == 4 && pos_j == Chessboard.LENGTH - 1)) {
            return true;
        }
        return false;
    }


    public void setHasMouseEntered(boolean hasMouseEntered) {
        this.hasMouseEntered = hasMouseEntered;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isHasMouseEntered() {
        return hasMouseEntered;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public boolean hasChessman() {
        return hasChessman;
    }

    public void setHasChessman(boolean hasChessman) {
        this.hasChessman = hasChessman;
    }

    public boolean isEmptyBox() {
        return !hasChessman();
    }
}
