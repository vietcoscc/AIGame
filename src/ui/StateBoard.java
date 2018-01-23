package ui;

public class StateBoard {
    public static final int GREEN_CHESS = 2;
    public static final int RED_CHESS = 1;
    public static final int GOAL_RED_CELL = 1;
    public static final int GOAL_GREEN_CELL = 2;
    public static final int HELICOPTER_RED_CELL = 3;
    public static final int HELICOPTER_GREEN_CELL = 4;
    int[] state;

    public StateBoard(int[] state) {
        this.state = state;
    }



    public static StateBoard getDefaultState() {
        int[] state = new int[64];
        state[4] = GOAL_RED_CELL & 0x7;
        state[7 * 8 + 3] = GOAL_GREEN_CELL & 0x7;
        state[3] = HELICOPTER_RED_CELL & 0x7;
        state[7 * 8 + 4] = HELICOPTER_GREEN_CELL & 0x7;

        state[0] |= (RED_CHESS & 0x3) << 3;
        state[7] |= (RED_CHESS & 0x3) << 3;
        state[4] |= (RED_CHESS & 0x3) << 3;

        state[7 * 8] |= (GREEN_CHESS & 0x3) << 3;
        state[7 * 8 + 3] |= (GREEN_CHESS & 0x3) << 3;
        state[7 * 8 + 7] |= (GREEN_CHESS & 0x3) << 3;
        return new StateBoard(state);
    }

    public int[] getEncodingState() {
        return state;
    }

    public int getChessAt(int i, int j) {
        int index = getIndex(i, j);
        return getChessAt(index);
    }

    private int getIndex(int i, int j) {
        return i + j * 8;
    }

    public int getCellTypeAt(int i, int j) {
        int index = getIndex(i, j);
        return state[index] & 0x7;
    }

    private int getChessAt(int index){
        return (state[index] >> 3) & 0x3;
    }

    public void moveChess(Move move) {
        int removeIndex = move.getFrom();
        int insertIndex = move.getTo();
        int chessType = getChessAt(removeIndex);
        System.out.println("Chess type " + chessType);
        if (chessType == 0){
            System.err.println("There's no chess to move");
        }
        state[removeIndex] &= ~0x18;
        state[insertIndex] |= (chessType & 0x3) << 3;
    }

    public void printDebug() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(state[i*8 + j] + " ");
            }
            System.out.println();
        }
    }
}
