package ui.model;

import java.awt.*;

public class Chessbox {
    public static final int BOX_WIDTH = 85; // Độ rộng của 1 ô cờ
    public static final int BOX_HEIGHT = 85; //
    private int pos_i, pos_j; // position i j
    private int x, y; //

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
            graphics2D.setPaint(Color.BLACK);
        }
        //
        graphics2D.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.drawRect(x, y, BOX_WIDTH, BOX_HEIGHT);
        //
        graphics2D.setPaint(Color.CYAN);
        graphics2D.drawString("(" + pos_i + "," + pos_j + ")", x, y + 50);
        if (isHelicopter()) {
            graphics2D.drawString("Helicopter", x, y + 75);
        }
        if (isGoal()) {
            graphics2D.drawString("Goal", x, y + 75);
        }
        //
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
}
