package ui.model;

import java.awt.*;

public class Chessboard {
    public static final int LENGTH = 8;
    private Chessbox[][] chessboxes = new Chessbox[LENGTH][LENGTH];

    public Chessboard() {
       initChessboard();
    }
    private void initChessboard(){
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
}
