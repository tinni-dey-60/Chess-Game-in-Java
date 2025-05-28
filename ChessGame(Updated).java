import java.util.*;

abstract class Piece {
    protected String color;
    protected String name;

    public Piece(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board);
}

class Rook extends Piece {
    public Rook(String color) {
        super(color, "R");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        if (sx != dx && sy != dy) return false;
        int xStep = Integer.compare(dx, sx);
        int yStep = Integer.compare(dy, sy);
        int x = sx + xStep, y = sy + yStep;
        while (x != dx || y != dy) {
            if (board[x][y] != null) return false;
            x += xStep;
            y += yStep;
        }
        return true;
    }
}

class Bishop extends Piece {
    public Bishop(String color) {
        super(color, "B");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        if (Math.abs(sx - dx) != Math.abs(sy - dy)) return false;
        int xStep = Integer.compare(dx, sx);
        int yStep = Integer.compare(dy, sy);
        int x = sx + xStep, y = sy + yStep;
        while (x != dx && y != dy) {
            if (board[x][y] != null) return false;
            x += xStep;
            y += yStep;
        }
        return true;
Show quoted text
            if (dx == sx + direction) return true;
            if (((color.equals("white") && sx == 6) || (color.equals("black") && sx == 1)) &&
                dx == sx + 2 * direction && board[sx + direction][sy] == null) return true;
        } else if (Math.abs(sy - dy) == 1 && dx == sx + direction &&
                   board[dx][dy] != null && !board[dx][dy].getColor().equals(color)) {
            return true;
        }
        return false;
    }
}

class Board {
    Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initialize();
    }

    private void initialize() {
        String[] colors = {"black", "white"};
        for (int i = 0; i < 2; i++) {
            String color = colors[i];
            int row = color.equals("black") ? 0 : 7;
            int pawnRow = color.equals("black") ? 1 : 6;
Show quoted text
        // Move the piece
        board[dx][dy] = source;
        board[sx][sy] = null;
        return true;
    }

    public boolean isKingAlive(String color) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] instanceof King && board[i][j].getColor().equals(color))
                    return true;
        return false;
    }
}

public class ChessGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        String currentPlayer = "white";

        while (true) {
            board.display();

            if (!board.isKingAlive("white")) {
                System.out.println("Black wins! Checkmate.");
                break;
            }
            if (!board.isKingAlive("black")) {
                System.out.println("White wins! Checkmate.");
                break;

            }

            System.out.println(currentPlayer + "'s turn. Enter move (e.g., e2 e4):");
            String from = scanner.next();
            String to = scanner.next();

            if (board.move(from, to, currentPlayer)) {
                currentPlayer = currentPlayer.equals("white") ? "black" : "white";
            } else {
                System.out.println("Try again!");
            }
        }
    }
}

