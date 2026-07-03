public class Board {

    private int[][] grid;
    private int rows;
    private int cols;
    private int totalDots;
    private int dotsEaten;

    // The maze layout
    private int[][] mazeLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 3, 2, 1},
            {1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1},
            {1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // Constructor
    public Board() {
        this.rows = mazeLayout.length;
        this.cols = mazeLayout[0].length;
        this.grid = new int[rows][cols];

        // Copy maze to grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = mazeLayout[i][j];
            }
        }

        // Count total dots
        this.totalDots = countDots();
        this.dotsEaten = 0;
    }
    private int countDots() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    // Check wall
    public boolean isWall(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        // Check boundaries
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            return true;
        }
        return grid[y][x] == 1;
    }


    public boolean hasDot(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            return false;
        }

        return grid[y][x] == 2;
    }


    public void removeDot(Position pos) {
        int x = pos.getX();
        int y = pos.getY();

        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            if (grid[y][x] == 2) {
                grid[y][x] = 0;  // Replace dot with empty space
                dotsEaten++;
            }
        }
    }

    public boolean allDotsEaten() {
        return dotsEaten >= totalDots;
    }

    public int getTotalDots() {
        return totalDots;
    }

    public int getDotsRemaining() {
        return totalDots - dotsEaten;
    }

    public int getDotsEaten() {
        return dotsEaten;
    }

    // Keep your ANSI colors at the top of the Board class!
    private final String RESET = "\u001B[0m";
    private final String BLUE = "\u001B[34m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String PINK = "\u001B[35m";
    private final String CYAN = "\u001B[36m";
    private final String WHITE = "\u001B[37m";
    private final String ORANGE = "\u001B[38;5;208m"; // Special ANSI for Orange

    public void display(Pacman pacman, Blinky blinky, Pinky pinky, Inky inky, Clyde clyde) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        char[][] display = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) display[i][j] = '#';
                else if (grid[i][j] == 2) display[i][j] = '.';
                else if (grid[i][j] == 3) display[i][j] = '*';  // <--- ADD THIS
                else display[i][j] = ' ';
            }
        }

        placeGhost(display, blinky);
        placeGhost(display, pinky);
        placeGhost(display, inky);
        placeGhost(display, clyde);

        Position pacmanPos = pacman.getPosition();
        display[pacmanPos.getY()][pacmanPos.getX()] = 'C';

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char current = display[i][j];

                if (current == '#') {
                    // Solid arcade blue walls
                    System.out.print(BLUE + "██" + RESET);
                } else if (current == '.') {
                    // Small dot
                    System.out.print(WHITE + " ·" + RESET);
                } else if (current == '*') {                        // <--- ADD THIS
                    System.out.print(YELLOW + " ⭐" + RESET);     // <--- ADD THIS
                } else if (current == 'C') {
                    // DIRECTIONAL PACMAN!
                    String dir = pacman.getDirection();
                    if (dir.equals("RIGHT")) System.out.print(YELLOW + " ᗧ" + RESET);
                    else if (dir.equals("LEFT")) System.out.print(YELLOW + " ᗤ" + RESET);
                    else if (dir.equals("UP")) System.out.print(YELLOW + " ᗢ" + RESET);
                    else if (dir.equals("DOWN")) System.out.print(YELLOW + " ᗣ" + RESET);
                    else System.out.print(YELLOW + " ᗧ" + RESET);
                } else if (current == 'B') {
                    System.out.print(RED + " Ѧ" + RESET); // Classic arcade ghost shape
                } else if (current == 'P') {
                    System.out.print(PINK + " Ѧ" + RESET);
                } else if (current == 'I') {
                    System.out.print(CYAN + " Ѧ" + RESET);
                } else if (current == 'O') {
                    System.out.print(ORANGE + " Ѧ" + RESET);
                } else if (current == 'F') {
                    System.out.print(" 👻"); // Actual Ghost emoji when scared!
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Helper method to place a single ghost
    private void placeGhost(char[][] display, Ghost ghost) {
        Position ghostPos = ghost.getPosition();
        int x = ghostPos.getX();
        int y = ghostPos.getY();

        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            if (ghost.getIsFrightened()) {
                display[y][x] = 'F';
            } else {
                display[y][x] = ghost.getSymbol();
            }
        }
    }
    public boolean hasPowerPellet(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (x < 0 || x >= cols || y < 0 || y >= rows) return false;
        return grid[y][x] == 3;
    }

    public void removePowerPellet(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            if (grid[y][x] == 3) {
                grid[y][x] = 0;
                dotsEaten++; // Counts towards winning the game
            }
        }
    }
    // Get board dimensions
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


}