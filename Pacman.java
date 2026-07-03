public class Pacman extends Character {


    //private attributes: read-only access
    private int score;
    private int lives;

    //Constructor
    public Pacman(int x, int y, String direction) {
        super(x, y, direction);
        this.score = 0;
        this.lives = 3;
        this.symbol = 'M';
    }

    //Getters
    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    //methods and setters
    public void addScore(int points) {
        this.score += points;
    }

    public void eatDot() {
        this.score += 10;
    }

    public void loseLife() {
        this.lives--;
        this.ResetPosition();
    }

    public void resetToStartPosition() {
        this.ResetPosition();
    }

    public boolean isAlive() {
        return lives > 0;
    }

    // movement
    @Override
    public void move() {
        Position nextPos = CalculateNextPosition();
        this.position = nextPos;
    }

    // Calculate next position based on direction
    public Position CalculateNextPosition() {
        switch (direction) {
            case "UP":
                return new Position(position.getX(), position.getY() - 1);
            case "DOWN":
                return new Position(position.getX(), position.getY() + 1);
            case "LEFT":
                return new Position(position.getX() - 1, position.getY());
            case "RIGHT":
                return new Position(position.getX() + 1, position.getY());
            default:
                return this.position;
        }
    }

    // Helper: Get random adjacent position (for future use)
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