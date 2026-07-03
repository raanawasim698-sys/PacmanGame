public class Position{

    //Position variables
    private int x;
    private int y;

    //Constructor
    Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    //Getters for x and y
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    //Setters for X and Y
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }

    //isEqual method: to check collision
    public boolean isEqual(Position other){
        if(this.x==other.getX() && this.y==other.getY()){
            return true;}
        else
            return false;
    }

    //toString method to print the position of character
    public String toString(){
                return (x+","+y);
    }
}