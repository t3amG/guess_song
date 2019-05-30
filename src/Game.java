public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private WavPlayer song1;
  private WavPlayer song2;
  private String mainWitch = "images/witch.png";
  private String notesPic = "images/get.png";
  private int probOfNoteSpawn;


  public Game() {

    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "images/user.gif");
    //song1 = new WavPlayer("songs/LH DW.wav");
    //song1.startSound();
   //song2 = new WavPlayer("CB money.wav");
    song2 = new WavPlayer("songs/CB money.wav");
    song2.startSound();

    grid.setBackground("images/mainpic.jpg");
    grid.setMovableBackground("images/mainpic.jpg", 0, 0, 1.0, 1.0);
    grid.setImage(new Location(userRow, 0), mainWitch);
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

  public void handleKeyPress() {
    // check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);
    if (key == 38) {
      // set up a key to move up the grid 'Up Arrow'
      // check case if you are out of bounds or if you move pass the 0 end at the
      // bottom of the array
      Location oldLoc = new Location(userRow, 0);
      grid.setImage(oldLoc, null);
      // change the field for user
      userRow--;
      if (userRow == -1) {
        userRow = grid.getNumRows() - 1;
        System.out.println("Row#: " + userRow);
      }

      // shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, mainWitch);

    }
    // set up a key to move down the grid 'Down Arrow'
    if (key == 40) {
      // check case if you are out of bounds or if you move pass the 4 end at the
      // bottom of the array
      Location oldLoc = new Location(userRow, 0);
      grid.setImage(oldLoc, null);
      // change the field for user
      userRow++;
      if (userRow == 5) {
        userRow = 0;
        System.out.println("Row#: " + userRow);
      }
      // shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, mainWitch);
    }
  }

  public void populateRightEdge() {
    probOfNoteSpawn = (int)( Math.random()*grid.getNumRows());
    System.out.println(probOfNoteSpawn);
    Location tempLoc = new Location(probOfNoteSpawn, grid.getNumCols()-1);
    grid.setImage(tempLoc, notesPic);

  }

  public void scrollLeft() {
    System.out.println("ScrollingLeft");

    for(int i = 0; i <grid.getNumRows(); i++){
      for(int j = 0; j < grid.getNumCols(); j++){
       //System.out.println(i + "," + j);
       Location temp = new Location (i, j);
       System.out.println(grid.getImage(temp));
       
       if(j==0){


      } else if(notesPic.equals(grid.getImage(temp))){
          Location newLoc = new Location(i, j-1);
          grid.setImage(newLoc, notesPic);
          grid.setImage(temp, null);
        }
        
      }
    }
  }
    // grid.setImage(1, imageFileName);
    // Location oldLoc = new Location(userRow, 0);
    // grid.setImage(oldLoc, null);

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