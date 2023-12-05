public class Main {
    public static void main(String[] args) throws Exception {
        int size = 10;
        GameLogic board = new GameLogic(size);
        Ai ai = new Ai(size, board);
        GUI gui = new GUI(board, ai);
    }
}
