public abstract class Ghost extends Character {

    //Attributes Shared by all ghosts
    private String name; //name of each ghost
    private String colour; //colour of ghosts
    protected String mode; //modes of ghosts: scatters,frightened, chaser
    protected boolean IsFrightened; //indicator
    protected int frightenedtimer; //countdown when power pellet is eaten
    protected Position ScatterTarget; // position of ghost when scattered
    private int modeTimer;         // counts down before switching modes
    private int scatterDuration;   // how many turns in SCATTER (e.g., 7)
    private int chaseDuration;     // how many turns in CHASE (e.g., 20)


    //Constructor
    Ghost(int x,int y, String direction, String name, String colour, Position scatterTarget){
        super(x,y,direction);
        this.name = name;
        this.colour = colour;
        this.ScatterTarget = scatterTarget;
        this.symbol = 'G';
        this.mode = "SCATTER";
        this.IsFrightened = false;
        this.frightenedtimer = 0;

    }


    //getters(no setters since name,color and mode wont change)
    public String getName(){
    return name;}

    public String getColor(){
    return colour;}

    public String getMode(){
    return mode;}

    public boolean getIsFrightened(){
     return IsFrightened;
    }

    public Position getScatterTarget(){
    return ScatterTarget;}


    //Setters (only mode changes and IsFrightened changes
    public void setMode(String newMode){
    this.mode = newMode;}

    public void setFrightened(boolean scared, int duration) {
        this.IsFrightened = scared;
        if (scared) {
            this.frightenedtimer = duration;  // e.g., 10
        }
    }


    //Methods: fightened functionality
    public void updateFrightenedTimer(){

    if (IsFrightened && frightenedtimer > 0) {
        frightenedtimer--;
        if (frightenedtimer == 0) {
            IsFrightened = false;
        }}}

    //Methods: mode update
    public void updateMode() {
        if (!IsFrightened) {  // Only switch if not scared
       if  (mode.equals("SCATTER")) {
                    mode = "CHASE";
                } else if (mode.equals("CHASE")) {
                    mode = "SCATTER";
                }
            }
        }

    public abstract Position calculateChaseTarget(Pacman pacman);

    public abstract void move(Pacman pacman);

    public void resetToStartPosition(){
    this.position = new Position(startX, startY);
    this.mode = "SCATTER";
    this.IsFrightened = false;
    this.frightenedtimer = 0;}
}





