public class Board {
    private int width, height;
    private Piece[][] board;

    private Region region;

    public Board(int w, int  h) {
        this.board = new Piece[w][h];
        this.width = w;
        this.height = h;
    }

    public Piece get(int x, int y) {
        return this.board[x][y];
    }

    public void setRegion(Region reg) {
        this.region = reg;
    }

    public int regionAreaEffect() {
        return this.width * this.height * this.region.getArea();
    }

    public boolean insertPiece(Piece newPiece, int insertX, int insertY) {
        this.board[insertX][insertY] = newPiece;
        return true;
    }

    public boolean checkBounds(int startX, int startY, int endX, int endY) {
        if(startX < 0 || startX >= this.width || startY < 0 || startY >= this.height){
            return false;
        }
        if(endX < 0 || endX >= this.width || endY < 0 || endY >= this.height){
            return false;
        }
        return true;
    }

    public boolean move(int startX, int startY, int deltaX, int deltaY) {
        if(deltaX == 0 && deltaY == 0) {
            return false;
        }

        int endX = startX + deltaX;
        int endY = startY + deltaY;

        if(!checkBounds(startX, startY, endX, endY)){
            return false;
        }

        int signX = 0;
        if(this.board[startX][startY] == Piece.A) {
            if (Math.abs(deltaX) + Math.abs(deltaY) > 1){
                return false;
            }
            if (this.board[endX][endY] instanceof Piece) {
                if (Math.abs(deltaX) == 1) {
                    endX += deltaX;
                } else if (Math.abs(deltaY) == 1) {
                    endY += deltaY;
                }

                if (!checkBounds(startX, startY, endX, endY)) {
                    return false;
                }
            }
        }else if(this.board[startX][startY] == Piece.B) {
            if(Math.abs(deltaX) > 0 && Math.abs(deltaY) > 0) {
                return false;
            }

            boolean collide = false;

            if(Math.abs(deltaX) > 0) {
                signX = (1*Integer.signum(deltaX));
                for(int i = startX; i != endX + signX && collide == false; i += signX) {
                    if(this.board[i][startY] instanceof Piece && i != startX) {
                        endX = i - signX;
                        collide = true;
                    }
                }
            } else if(Math.abs(deltaY) > 0) {
                int signY = (1*Integer.signum(deltaY));
                for(int i = startY; i != endY + signY && collide == false; i += signY) {
                    if(this.board[startX][i] instanceof Piece && i != startY) {
                        endY = i - signY;
                        collide = true;
                    }
                }
            }

        }
        this.board[endX][endY] = this.board[startX][startY];
        this.board[startX][startY] = null;

        return true;
    }
}
