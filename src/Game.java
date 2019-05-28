public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  
  public Game() {

    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setBackground("mainpic.jpg");
    grid.setImage(new Location(userRow, 0), "user.gif");
  }
  
  public void play() {

    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
  }
  
  public void handleKeyPress(){
    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);
    if(key == 38){
      //call method to do the work 
    //set up a key to move up the grid 'Up Arrow'
      //check case if you are out of bounds or if you move pass the 0 end at the bottom of the array
      //change the field for user
      userRow--;
      //shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, "user.gif");
      Location oldLoc = new Location(userRow +1, 0);
      grid.setImage(oldLoc, null);
    }
    //set up a key to move down the grid 'Down Arrow'

  }
  
  public void populateRightEdge(){

  }
  
  public void scrollLeft(){

  }
  
  public void handleCollision(Location loc) {

  }
  
  public int getScore() {
    return 0;
  }
  
  public void updateTitle() {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    return false;
  }
    
  public static void main(String[] args) {
    Game game = new Game();
    game.play();
  }
}