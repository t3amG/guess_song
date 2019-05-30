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
  private boolean readyToStart;
  private String[] songNames = { "7 Rings", "Bad Guy", "Bohemian Rhapsody", "Coconut Oil", "Could You Be Loved",
      "Doo Wop", "Hollaback Girl", "Imported", "Money", "Old Town Road", "Runnin", "Temperature", "Time", "Toast" };
  private List<WavPlayer> songs;

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
      // call method to do the work
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
      // call method to do the work
      // set up a key to move up the grid 'Up Arrow'
      // check case if you are out of bounds or if you move pass the 0 end at the
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
    // set up a key to move down the grid 'Down Arrow'

  }

  public void populateRightEdge() {

  }

  public void scrollLeft() {

  }

  public void handleCollision(Location loc) {

    songs[2].startSound();
    lives--;
    System.out.println("Lives:" + lives);

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