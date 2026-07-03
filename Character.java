public abstract class Character {

    //Variables
     protected Position position;
     protected String direction;
     protected char symbol;
     protected int startX;
     protected int startY;

    //Getters for Position and Direction
    public Position getPosition(){
           return position;
    }
    public String getDirection(){
        return direction;
    }
    public char getSymbol() {
        return symbol;
    }

    //Setters for Position and Direction
    public void setPosition(Position position){
        this.position=position;
    }
    public void setDirection(String direction){
        this.direction=direction;
    }

    //Constructor
    Character(int x, int y , String direction){
        this.direction=direction;
        this.startX=x;
        this.startY=y;
        position=new Position(x, y);
    }

    //Abstract move method: blueprint for pacman and ghost
    public abstract void move();

    //ResetPosition method: called upon when a character loses a life
    public void ResetPosition(){
        this.position=new Position(startX, startY);
    }
}
