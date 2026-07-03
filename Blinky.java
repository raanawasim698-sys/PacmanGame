public class Blinky extends Ghost {

    // Constructor
    public Blinky(int x, int y, String direction, Position scatterTarget) {
        super(x, y, direction, "Blinky", "Red", scatterTarget);
        this.symbol = 'B';
    }

    //Blinky's Chase Target: Always Pacman's current position
    @Override
    public Position calculateChaseTarget(Pacman pacman) {
        return pacman.getPosition();
    }

    @Override
    public void move() {
    }

    // Blinky's Movement Logic
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

        // Move in the direction with bigger distance
        if (distX > distY) {
            if (targetX > currentX) {
                return new Position(currentX + 1, currentY);  // RIGHT
            } else {
                return new Position(currentX - 1, currentY);  // LEFT
            }
        }
        else if (distY > distX) {
            if (targetY > currentY) {
                return new Position(currentX, currentY + 1);  // DOWN
            } else {
                return new Position(currentX, currentY - 1);  // UP
            }
        }
        else {
            // Equal distance, go horizontal
            if (targetX > currentX) {
                return new Position(currentX + 1, currentY);
            } else {
                return new Position(currentX - 1, currentY);
            }
        }
    }
}