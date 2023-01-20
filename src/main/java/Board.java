public class Board {
    private int width, height;
    private Piece[][] board;

    public Board(int w, int  h) {
        this.board = new Piece[w][h];
        this.width = w;
        this.height = h;
    }

    public boolean move(int startX, int startY, int deltaX, int deltaY) {
        return true;
    }
}
