import java.util.Scanner;

// Abstract Class
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

// Individual Pieces
class Rook extends Piece {
    public Rook(String color) {
        super(color, "R");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        return sx == dx || sy == dy;
    }
}

class Bishop extends Piece {
    public Bishop(String color) {
        super(color, "B");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        return Math.abs(sx - dx) == Math.abs(sy - dy);
    }
}

class Knight extends Piece {
    public Knight(String color) {
        super(color, "N");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        int x = Math.abs(dx - sx);
        int y = Math.abs(dy - sy);
        return (x == 2 && y == 1) || (x == 1 && y == 2);
    }
}

class Queen extends Piece {
    public Queen(String color) {
        super(color, "Q");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        return new Rook(color).isValidMove(sx, sy, dx, dy, board) ||
               new Bishop(color).isValidMove(sx, sy, dx, dy, board);
    }
}

class King extends Piece {
    public King(String color) {
        super(color, "K");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        return Math.abs(sx - dx) <= 1 && Math.abs(sy - dy) <= 1;
    }
}

class Pawn extends Piece {
    public Pawn(String color) {
        super(color, "P");
    }

    @Override
    public boolean isValidMove(int sx, int sy, int dx, int dy, Piece[][] board) {
        int direction = color.equals("white") ? -1 : 1;
        if (sy == dy && board[dx][dy] == null) {
            if (dx - sx == direction) return true;
            if ((color.equals("white") && sx == 6 || color.equals("black") && sx == 1) &&
                dx - sx == 2 * direction) return true;
        } else if (Math.abs(sy - dy) == 1 && dx - sx == direction &&
                   board[dx][dy] != null && !board[dx][dy].getColor().equals(color)) {
            return true;
        }
        return false;
    }
}

// Board Class
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
            int row = (color.equals("black")) ? 0 : 7;
            int pawnRow = (color.equals("black")) ? 1 : 6;

            board[row][0] = new Rook(color);
            board[row][1] = new Knight(color);
            board[row][2] = new Bishop(color);
            board[row][3] = new Queen(color);
            board[row][4] = new King(color);
            board[row][5] = new Bishop(color);
            board[row][6] = new Knight(color);
            board[row][7] = new Rook(color);

            for (int j = 0; j < 8; j++) {
                board[pawnRow][j] = new Pawn(color);
            }
        }
    }

    public void display() {
        System.out.println("  a  b  c  d  e  f  g  h");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print("-- ");
                } else {
                    System.out.print(board[i][j].getColor().charAt(0) + board[i][j].getName() + " ");
                }
            }
            System.out.println(" " + (8 - i));
        }
        System.out.println("  a  b  c  d  e  f  g  h\n");
    }

    public boolean move(String from, String to, String currentPlayer) {
        int sx = 8 - Character.getNumericValue(from.charAt(1));
        int sy = from.charAt(0) - 'a';
        int dx = 8 - Character.getNumericValue(to.charAt(1));
        int dy = to.charAt(0) - 'a';

        Piece source = board[sx][sy];
        if (source == null || !source.getColor().equals(currentPlayer)) {
            System.out.println("Invalid move: not your piece.");
            return false;
        }

        if (!source.isValidMove(sx, sy, dx, dy, board)) {
            System.out.println("Invalid move for " + source.getName());
            return false;
        }

        if (board[dx][dy] != null && board[dx][dy].getColor().equals(currentPlayer)) {
            System.out.println("Invalid move: cannot capture your own piece.");
            return false;
        }

        board[dx][dy] = source;
        board[sx][sy] = null;
        return true;
    }
}

// Only public class matching filename
public class ChessGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        String currentPlayer = "white";

        while (true) {
            board.display();
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
