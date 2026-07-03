import java.util.Scanner;

class Game {

    private Board board;
    private Pacman pacman;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;
    private Clyde clyde;
    private boolean isRunning;
    private Scanner scanner;
    private int modeTimer;
    private int modeInterval;

    // Constructor
    public Game() {
        this.board = new Board();
        this.pacman = new Pacman(1, 1, "RIGHT");

        // Place ghosts in empty spaces (middle area)
        this.blinky = new Blinky(9, 4, "LEFT", new Position(18, 1));
        this.pinky = new Pinky(10, 4, "DOWN", new Position(1, 1));
        this.inky = new Inky(9, 5, "UP", new Position(18, 7));
        this.clyde = new Clyde(10, 5, "UP", new Position(1, 7));

        this.isRunning = true;
        this.scanner = new Scanner(System.in);
        this.modeTimer = 0;
        this.modeInterval = 10;
    }
    // The main game loop
    public void start() {
        System.out.println("=== PACMAN GAME ===");
        System.out.println("Controls: W=UP, A=LEFT, S=DOWN, D=RIGHT");
        System.out.println("Eat all dots to win!");
        System.out.println();

        while (isRunning) {
            // Step 1: Display current state
            board.display(pacman, blinky, pinky, inky, clyde);
            displayStatus();

            // Step 2: Get player input
            getPlayerInput();

            // Store old position
            Position oldPacmanPos = new Position(pacman.getPosition().getX(), pacman.getPosition().getY());

            // Step 3: Move Pacman
            pacman.move();
            // Step 4: Check wall collision
            if (board.isWall(pacman.getPosition())) {
                pacman.setPosition(oldPacmanPos);

                System.out.println("Bonk! Pacman hit a wall.");
            }

            checkDotCollection();

            moveCharacters();

            checkCollisions();

            checkWinCondition();


            updateGhostModes();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Game ended
        scanner.close();
    }

    // Get player input
    private void getPlayerInput() {
        System.out.print("Move (W/A/S/D): ");
        String input = scanner.nextLine().toUpperCase();

        if (input.length() > 0) {
            char move = input.charAt(0);

            switch (move) {
                case 'W':
                    pacman.setDirection("UP");
                    break;
                case 'S':
                    pacman.setDirection("DOWN");
                    break;
                case 'A':
                    pacman.setDirection("LEFT");
                    break;
                case 'D':
                    pacman.setDirection("RIGHT");
                    break;
            }
        }
    }

    // Move all characters
    private void moveCharacters() {
        // Move each ghost
        blinky.move(pacman);
        pinky.move(pacman);
        inky.move(pacman);
        clyde.move(pacman);

        // Check wall collisions for ghosts and keep them in bounds
        checkGhostWalls(blinky);
        checkGhostWalls(pinky);
        checkGhostWalls(inky);
        checkGhostWalls(clyde);
    }

    // Helper: Check if ghost hit a wall
    private void checkGhostWalls(Ghost ghost) {
        if (board.isWall(ghost.getPosition())) {
            // Ghost hit wall, move back to previous position
            ghost.resetToStartPosition();
        }
    }

    // Check if Pacman ate a dot
    private void checkDotCollection() {
        Position pacmanPos = pacman.getPosition();

        if (board.hasDot(pacmanPos)) {
            board.removeDot(pacmanPos);
            pacman.addScore(10);
            System.out.println("Pacman ate a dot! +10 points");
        }
        else if (board.hasPowerPellet(pacmanPos)) {
            board.removePowerPellet(pacmanPos);
            pacman.addScore(50);
            System.out.println("⭐ POWER PELLET EATEN! GHOSTS ARE SCARED! ⭐");

            // Turn on Frightened Mode for 15 turns!
            blinky.setFrightened(true, 15);
            pinky.setFrightened(true, 15);
            inky.setFrightened(true, 15);
            clyde.setFrightened(true, 15);
        }
    }

    // Check if ghost caught Pacman
    private void checkCollisions() {
        Position pacmanPos = pacman.getPosition();

        // Check each ghost
        if (collisionWithGhost(pacmanPos, blinky)) {
            handleGhostCollision(blinky);
        }
        if (collisionWithGhost(pacmanPos, pinky)) {
            handleGhostCollision(pinky);
        }
        if (collisionWithGhost(pacmanPos, inky)) {
            handleGhostCollision(inky);
        }
        if (collisionWithGhost(pacmanPos, clyde)) {
            handleGhostCollision(clyde);
        }
    }

    // Helper: Check if Pacman touched a ghost
    private boolean collisionWithGhost(Position pacmanPos, Ghost ghost) {
        return pacmanPos.isEqual(ghost.getPosition());
    }

    // Helper: Handle collision with ghost
    private void handleGhostCollision(Ghost ghost) {
        if (ghost.getIsFrightened()) {
            // Pacman ate the ghost (powered up)
            System.out.println("Pacman ate " + ghost.getName() + "! +200 points!");
            pacman.addScore(200);
            ghost.resetToStartPosition();
        } else {
            // Ghost caught Pacman
            System.out.println(ghost.getName() + " caught Pacman!");
            pacman.loseLife();

            if (!pacman.isAlive()) {
                System.out.println("GAME OVER! No more lives!");
                isRunning = false;
            } else {
                System.out.println("Lives remaining: " + pacman.getLives());
                // Reset positions
                pacman.resetToStartPosition();
                blinky.resetToStartPosition();
                pinky.resetToStartPosition();
                inky.resetToStartPosition();
                clyde.resetToStartPosition();
            }
        }
    }

    // Check if player won
    private void checkWinCondition() {
        if (board.allDotsEaten()) {
            System.out.println("YOU WIN! All dots eaten!");
            System.out.println("Final Score: " + pacman.getScore());
            isRunning = false;
        }
    }

    // Update ghost modes (SCATTER/CHASE alternation)
    private void updateGhostModes() {
        modeTimer++;

        if (modeTimer >= modeInterval) {
            modeTimer = 0;

            // Switch all ghosts' modes
            switchMode(blinky);
            switchMode(pinky);
            switchMode(inky);
            switchMode(clyde);
        }
    }

    // Helper: Switch a ghost's mode
    private void switchMode(Ghost ghost) {
        if (ghost.getMode().equals("SCATTER")) {
            ghost.setMode("CHASE");
        } else if (ghost.getMode().equals("CHASE")) {
            ghost.setMode("SCATTER");
        }
    }

    // Display game status
    private void displayStatus() {
        System.out.println("Score: " + pacman.getScore() +
                " | Lives: " + pacman.getLives() +
                " | Dots Remaining: " + board.getDotsRemaining());
    }
}

