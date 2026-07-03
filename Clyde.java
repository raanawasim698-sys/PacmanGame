public class Clyde extends Ghost {

    // Constructor
    public Clyde(int x, int y, String direction, Position scatterTarget) {
        super(x, y, direction, "Clyde", "Orange", scatterTarget);
        this.symbol = 'O';
    }

    // Clyde's Chase Target
    @Override
    public Position calculateChaseTarget(Pacman pacman) {
        int pacmanX = pacman.getPosition().getX();
        int pacmanY = pacman.getPosition().getY();
        int clydeX = this.position.getX();
        int clydeY = this.position.getY();

        //
        int distance = Math.abs(pacmanX - clydeX) + Math.abs(pacmanY - clydeY);

        if (distance > 5) {
            // Far away: Chase Pacman aggressively
            return pacman.getPosition();
        } else {
            // Too close: Run away to scatter corner
            return this.ScatterTarget;
        }
    }

    // Clyde's Movement Logic
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