class Inky extends Ghost {

    // Constructor
    public Inky(int x, int y, String direction, Position scatterTarget) {
        super(x, y, direction, "Inky", "Cyan", scatterTarget);
        this.symbol = 'I';
    }

    // Inky's Chase Target: Completely Random
    @Override
    public Position calculateChaseTarget(Pacman pacman) {
        // Pick a random position on the board
        int randomX = (int)(Math.random() * 20);  // Adjust based on board width
        int randomY = (int)(Math.random() * 20);  // Adjust based on board height
        return new Position(randomX, randomY);
    }

    // Inky's Movement Logic
    @Override
    public void move(Pacman pacman) {
        updateFrightenedTimer();

        Position target;

        if (IsFrightened) {
            target = getRandomAdjacentPosition();
        }
        else if (mode.equals("CHASE")) {
            target = calculateChaseTarget(pacman);
        }
        else {
            target = ScatterTarget;
        }

        Position nextPosition = calculateNextPosition(target);
        this.position = nextPosition;
    }

    @Override
    public void move() {
        // Satisfy Character's contract
    }

    // Helper: Random adjacent position when scared
    private Position getRandomAdjacentPosition() {
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        String randomDir = directions[(int)(Math.random() * 4)];

        int x = position.getX();
        int y = position.getY();

        switch(randomDir) {
            case "UP":    return new Position(x, y - 1);
            case "DOWN":  return new Position(x, y + 1);
            case "LEFT":  return new Position(x - 1, y);
            case "RIGHT": return new Position(x + 1, y);
            default:      return position;
        }
    }

    // Helper: One step towards target
    private Position calculateNextPosition(Position target) {
        int currentX = position.getX();
        int currentY = position.getY();
        int targetX = target.getX();
        int targetY = target.getY();

        int distX = Math.abs(targetX - currentX);
        int distY = Math.abs(targetY - currentY);

        if (distX > distY) {
            if (targetX > currentX) {
                return new Position(currentX + 1, currentY);
            } else {
                return new Position(currentX - 1, currentY);
            }
        }
        else if (distY > distX) {
            if (targetY > currentY) {
                return new Position(currentX, currentY + 1);
            } else {
                return new Position(currentX, currentY - 1);
            }
        }
        else {
            if (targetX > currentX) {
                return new Position(currentX + 1, currentY);
            } else {
                return new Position(currentX - 1, currentY);
            }
        }
    }
}