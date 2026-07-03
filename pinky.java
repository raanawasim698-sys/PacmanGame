class Pinky extends Ghost {

    // Constructor
    public Pinky(int x, int y, String direction, Position scatterTarget) {
        super(x, y, direction, "Pinky", "Pink", scatterTarget);
        this.symbol = 'P';
    }

    // Pinky's Chase Target: 4 cells AHEAD of Pacman
    @Override
    public Position calculateChaseTarget(Pacman pacman) {
        Position pacmanPos = pacman.getPosition();
        int targetX = pacmanPos.getX();
        int targetY = pacmanPos.getY();

        // Look 4 steps ahead in Pacman's direction
        String pacmanDirection = pacman.getDirection();

        if (pacmanDirection.equals("UP")) {
            targetY -= 4;
        }
        else if (pacmanDirection.equals("DOWN")) {
            targetY += 4;
        }
        else if (pacmanDirection.equals("LEFT")) {
            targetX -= 4;
        }
        else if (pacmanDirection.equals("RIGHT")) {
            targetX += 4;
        }

        return new Position(targetX, targetY);
    }
    @Override
    public void move() { }

    // Pinky's Movement Logic
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
        else {  // SCATTER
            target = ScatterTarget;
        }

        Position nextPosition = calculateNextPosition(target);
        this.position = nextPosition;
    }

    // Helper: Random position when scared
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