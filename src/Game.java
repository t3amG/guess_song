import java.util.*;

public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private WavPlayer mainSong;
  private WavPlayer titleSong;
  private String mainWitch;
  private int lives;
  private int probOfNoteSpawn;
  private String notesPic;
  private boolean readyToStart;
  private String[] songNames = { "7 Rings", "Bad Guy", "Bohemian Rhapsody", "Could You Be Loved",
      "Doo Wop", "Hollaback Girl", "Imported", "Money", "Old Town Road", "Runnin", "Temperature", "Time", "Toast" };
  private List<WavPlayer> songs;
  private int counter;
  private int score;

  public Game() {

    readyToStart = true;
    lives = 6;
    mainWitch = "images/witch.png";
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    titleSong = new WavPlayer("songs/Holy Mountain.wav");
    notesPic = "images/get.png";
    counter = 0;
    score = 0;

    mainSong = new WavPlayer("songs/Coconut Oil.wav");
    updateTitle();
    
    songs = new ArrayList<WavPlayer>();
    for (int i = 0; i < songNames.length; i++) {
      songs.add(new WavPlayer("songs/" + songNames[i] + ".wav"));
    }

  }

  public void play() {

    //title begin
    grid.setBackground("images/title.png");
    titleSong.startSound();
    
    // if(user clicks enter then go to this screen)
    while(grid.checkLastKeyPressed() == -1){
      Grid.pause(100);
    }
    titleSong.pauseSound();
    grid.setBackground("images/mainpic.jpg");
    mainSong.startSound();

    


    while (!isGameOver()) {
      grid.setImage(new Location(userRow, 0), mainWitch);

      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }

      if (readyToStart && userRow == 2) {
        readyToStart = false;
        handleCollision(new Location(0, 0));
      }

      updateTitle();
      msElapsed += 100;
    }
  }

  public void handleKeyPress() {
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    // check last key pressed
    //System.out.println(key);
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
        //System.out.println("Row#: " + userRow);
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
        //System.out.println("Row#: " + userRow);
      }
      // shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, mainWitch);
    }
  }

  public void populateRightEdge() {
    probOfNoteSpawn = (int) (Math.random() * grid.getNumRows());
    int noteSpawn = (int) (Math.random() * grid.getNumRows());
    //System.out.println(probOfNoteSpawn);
    if (noteSpawn == probOfNoteSpawn) {
      Location tempLoc = new Location(probOfNoteSpawn, grid.getNumCols() - 1);
      grid.setImage(tempLoc, notesPic);
    }
  }

  public void scrollLeft() {
   // System.out.println("ScrollingLeft");

    for (int i = 0; i < grid.getNumRows(); i++) {
      for (int j = 0; j < grid.getNumCols(); j++) {
        Location temp = new Location(i, j);
        //System.out.println(grid.getImage(temp));

        if (j == 0 && notesPic.equals(grid.getImage(temp))) {
          grid.setImage(temp, null);

        } else if (notesPic.equals(grid.getImage(temp))) {
          Location newLoc = new Location(i, j - 1);
          grid.setImage(newLoc, notesPic);
          handleCollision(newLoc);
          grid.setImage(temp, null);
        }

      }
    }
  }

  public void handleCollision(Location loc) {
    //if the witch touches a note then
    //mainSong.stop();
    //int num = Math.random() * song.size();
   // song.get(num).play();
    
    int tempR = loc.getRow();
    int tempC = loc.getCol();
    boolean collision = false;
    if(grid.getImage(loc) != null) {
      if(tempR == userRow && tempC == 0){
        collision = true;
        grid.pause(1000);
        mainSong.pauseSound();
        int num = (int) (Math.random() * songNames.length);
        
        System.out.println(num);
        songs.get(num).startSound();
        
        
        String guess = grid.showInputDialog("What is this song? **Write the name of the song**");
        guess = guess.toLowerCase().replaceAll("\\W","");
        String answer = songNames[num].toLowerCase().replaceAll("\\W","");
        
        System.out.println(answer);

        if(answer.equals(guess)){
          //correct
          System.out.println("Correct");
          score++;
          System.out.println(score);
          Location ifLoc = new Location(userRow, 0);
          grid.setImage(ifLoc, mainWitch);
        } else {
          //incorrect
          System.out.println("WRONG");
          score +=0;
          lives--;
          System.out.println(score);
          System.out.println(lives);
          Location ifLoc = new Location(userRow, 0);
          grid.setImage(ifLoc, mainWitch);
        }
        songs.get(num).pauseSound();
        mainSong.startSound();
      }
    }
    //System.out.println(collision);
  }

  public int getScore() {
    return score;
  }
  
  public void titleScreen(){
   
    int key = grid.checkLastKeyPressed();
    if(key == 13){
      grid.removeBackground();
    }
  }
  public void updateTitle() {
    grid.setTitle("Score:  " + getScore() + "Lives: " + lives);
  }

  public boolean isGameOver() {
    return false;
  }



  public static void main(String[] args) {
    //JOptionPane.showMessageDialog(null, "This is a title screen");
    Game game = new Game();
    game.play();

  }
}