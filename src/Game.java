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
  private String[] songNames = { "7 Rings", "Bad Guy", "Bohemian Rhapsody", "Coconut Oil", "Could You Be Loved",
      "Doo Wop", "Hollaback Girl", "Imported", "Money", "Old Town Road", "Runnin", "Temperature", "Time", "Toast" };
  private List<WavPlayer> songs;
  private int counter;

  public Game() {

    readyToStart = true;
    lives = 5;
    mainWitch = "images/witch.png";
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    titleSong = new WavPlayer("songs/Holy Mountain.wav");
    notesPic = "images/get.png";
    counter = 0;

    //titleSong.startSound();
    
    // if(user clicks enter then go to this screen)
    // titleSong.stop();

    mainSong = new WavPlayer("songs/Coconut Oil.wav");
    mainSong.startSound();
    // mainSong.keeplooping();
    updateTitle();
    grid.setImage(new Location(userRow, 0), "images/user.gif");

    // if(user clicks enter then go to this screen)
    grid.setBackground("images/mainpic.jpg");
    grid.setMovableBackground("images/mainpic.jpg", 0, 0, 1.0, 1.0);
    grid.setImage(new Location(userRow, 0), mainWitch);

    songs = new ArrayList<WavPlayer>();
    for (int i = 0; i < songNames.length; i++) {
      songs.add(new WavPlayer("songs/" + songNames[i] + ".wav"));
    }

  }

  public void play() {

    while (!isGameOver()) {
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
    probOfNoteSpawn = (int) (Math.random() * grid.getNumRows());
    int noteSpawn = (int) (Math.random() * grid.getNumRows());
    System.out.println(probOfNoteSpawn);
    if (noteSpawn == probOfNoteSpawn) {
      Location tempLoc = new Location(probOfNoteSpawn, grid.getNumCols() - 1);
      grid.setImage(tempLoc, notesPic);
    }
  }

  public void scrollLeft() {
    System.out.println("ScrollingLeft");

    for (int i = 0; i < grid.getNumRows(); i++) {
      for (int j = 0; j < grid.getNumCols(); j++) {
        Location temp = new Location(i, j);
        System.out.println(grid.getImage(temp));

        if (j == 0 && notesPic.equals(grid.getImage(temp))) {
          grid.setImage(temp, null);

        } else if (notesPic.equals(grid.getImage(temp))) {
          Location newLoc = new Location(i, j - 1);
          grid.setImage(newLoc, notesPic);
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