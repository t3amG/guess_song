import java.util.*;

public class Game {

  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private int rowSpawn;
  private String notesPic;
  private String losePic;
  private WavPlayer mainSong;
  private WavPlayer titleSong;
  private WavPlayer winnerSong;
  private WavPlayer loserSong;
  private WavPlayer losingLives;
  private String mainWitch;
  private int lives;
  private boolean readyToStart;
  private String[] songNames = { "7 Rings", "Bad Guy", "Beautiful Girls", "Blessed", "Bohemian Rhapsody", "Carry On",
      "Could You Be Loved", "Doo Wop", "Feel it Still", "I Like That", "High Hopes", "Hollaback Girl", "Imported",
      "Lipgloss", "Lovely", "Man Down", "Milkshake", "Miss Independent", "Money", "Old Town Road", "Ordinary People",
      "Press", "Pumped Up Kicks", "Runnin", "Shea Butter Baby", "Shallow", "Stressed Out", "Suge", "Tap", "Temperature",
      "Time", "Toast", "We are young", "When I See You", "You Stay" };
  private String[] oldSongNames = { "Ain't No Mountain High Enough", "Dancing Queen", "Don't Stop Me Now",
      "Feeling Good", "I want it that way", "I will survive", "Jolene", "Killing me Softly", "Lets get it on",
      "My Girl", "No Scrubs", "Poison", "Rock With You", "This Woman's Work", "Unbreak My Heart", "Wannabe",
      "When Doves Cry" };
  // private String[] bonusSongNames = {"1950","It Wasn't Me","Kid Cudi","Party In
  // The U.S.A","Rise Up","Salao","Set Fire to the Rain","Single Ladies"};

  private List<WavPlayer> songs;
  private List<WavPlayer> oldSongs;
  // private List<WavPlayer> bonusSongs;
  private int counter;
  private int score;
  List songTitles;
  List oldSongTitles;
  private String[] notesGet = { "images/get.png", "images/quarterGet.png", "images/bonusNote.png" };
  private int goal;
  private String winPic;

  public Game() {

    readyToStart = true;
    lives = 3;
    mainWitch = "images/witch.png";
    grid = new Grid(5, 10);
    grid.fullscreen();
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    titleSong = new WavPlayer("songs/Holy Mountain.wav");
    // notesPic = "images/get.png";
    counter = 0;
    goal = 5;
    winPic = "images/WON.png";
    // titleSong.startSound();

    // if(user clicks enter then go to this screen)
    // titleSong.stop();
    losingLives = new WavPlayer("songs/Lose a Life.wav");
    // loserSong = new WavPlayer("songs/.wav");
    winnerSong = new WavPlayer("songs/We Are The Champions.wav");
    mainSong = new WavPlayer("songs/Coconut Oil.wav");
    notesPic = notesGet[(int) (Math.random() * 3)];
    losePic = "images/LOST.png";
    updateTitle();

    // songTitles = Arrays.asList(songNames);
    // oldSongTitles = Arrays.asList(oldSongs);

    // bonusSongs = new ArrayList<WavPlayer>();
    // for (int i = 0; i < bonusSongNames.length; i++) {
    // bonusSongs.add(new WavPlayer("songs/" + bonusSongNames[i] + ".wav"));
    // }

    oldSongs = new ArrayList<WavPlayer>();
    for (int i = 0; i < oldSongNames.length; i++) {
      oldSongs.add(new WavPlayer("songs/" + oldSongNames[i] + ".wav"));
    }

    songs = new ArrayList<WavPlayer>();
    for (int i = 0; i < songNames.length; i++) {
      songs.add(new WavPlayer("songs/" + songNames[i] + ".wav"));
    }

  }

  public void play() {
    
    // title begin
    grid.setBackground("images/titleScreen2.gif");

    titleSong.startSound();

    // if(user clicks enter then go to this screen)
    while (grid.checkLastKeyPressed() == -1) {
      Grid.pause(100);
    }
    titleSong.pauseSound();
    grid.setMovableBackground("images/mainpic.jpg", 0, 0, 1.0, 1.0);
    // grid.setBackground("images/mainpic.jpg");
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

    endOfGame();

  }

  public void clearScreen() {
    for (int i = 0; i < grid.getNumRows(); i++) {
      for (int j = 0; j < grid.getNumCols(); j++) {
        Location newLoc = new Location(i, j);
        if (grid.getImage(newLoc) != null) {
          grid.setImage(newLoc, null);
        }
      }
    }
  }

  public void endOfGame() {
    if (score == goal) {
      mainSong.pauseSound();
      grid.setBackground(winPic);
      clearScreen();
    }

    if (lives == 0) {
      mainSong.pauseSound();
      losingLives.startSound();
      grid.setBackground(losePic);
      clearScreen();
      // losingLives.pause()
      // loserSong.startSound();
    }

    grid.pause(8000);
    grid.close();
  }

  public void handleKeyPress() {
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    // check last key pressed
    // System.out.println(key);
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
        // System.out.println("Row#: " + userRow);
      }

      // shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, mainWitch);

    }
    // set up a key to move down the grid 'Down Arrow'
    if (key == 40) {
      // check case if you are out of bounds or if you move pass the 4 end at the
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
        // System.out.println("Row#: " + userRow);
      }

      // shift the user picture up in the aaray
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, mainWitch);

    }
    // set up a key to move down the grid 'Down Arrow'

  }

  public void populateRightEdge() {
    
    //which row gets a note
    rowSpawn = (int) (Math.random() * grid.getNumRows());
    
    //which note might get placed
    double noteSpawn = Math.random();
    if(noteSpawn < 0.45){
      notesPic = notesGet[0];
    } else if (noteSpawn > 0.55){
      notesPic = notesGet[1];
    } else {
      notesPic = notesGet[2];
    }

    //actually show a note
    double chanceOfNote = Math.random();
    if (chanceOfNote > .33) {
      Location tempLoc = new Location(rowSpawn, grid.getNumCols() - 1);      
      grid.setImage(tempLoc, notesPic);
    }
  }

  public void scrollLeft() {
    // System.out.println("ScrollingLeft");

    for (int i = 0; i < grid.getNumRows(); i++) {
      for (int j = 0; j < grid.getNumCols(); j++) {
        Location temp = new Location(i, j);
        // System.out.println(grid.getImage(temp));

        for(int k = 0; k < notesGet.length; k++){
          notesPic  = notesGet[k];

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
  }

  public void handleCollision(Location loc) {
    // if the witch touches a note then
    // mainSong.stop();
    // int num = Math.random() * song.size();
    // song.get(num).play();

    int tempR = loc.getRow();
    int tempC = loc.getCol();
    boolean collision = false;

    if (grid.getImage(loc) != null) {
      if ((tempR == userRow && tempC == 0) || (tempR == userRow - 1 && tempC == 0)) {
        collision = true;
        grid.setImage(loc, null);
        Location tempLoc = new Location(tempR, tempC);
        grid.setImage(tempLoc, "images/collision.png");
        grid.pause(1000);
        mainSong.pauseSound();

        //if(quater note is picked){
      //     int num1 = (int) (Math.random() * oldSongs.size());

      //     System.out.println(num1);
      //     oldSongs.get(num1).startSound();
      //     String guess = grid.showInputDialog("What is this song? **Write the name of the song**");
      //     guess = guess.toLowerCase().replaceAll("\\W", "");
      //     String answer = oldSongs.get(num).getFileName().toLowerCase().replaceAll("\\W", "");
      //     answer = answer.substring(5, answer.length() - 3);
  
      //     System.out.println(answer);
  
      //     if (answer.equals(guess)) {
      //       // correct
      //       System.out.println("Correct");
      //       score++;
      //       System.out.println(score);
      //       Location ifLoc = new Location(userRow, 0);
      //       grid.setImage(ifLoc, mainWitch);
      //     } else {
      //       // incorrect
      //       System.out.println("WRONG");
      //       score += 0;
      //       lives--;
      //       System.out.println(score);
      //       System.out.println(lives);
      //       Location ifLoc = new Location(userRow, 0);
      //       grid.setImage(ifLoc, mainWitch);
      //     }
      //     oldSongs.get(num).pauseSound();
      //     oldSongs.remove(num);
      //     mainSong.startSound();
      //   }
      // }
  
      //if(regular note is touched){
        int num2 = (int) (Math.random() * songs.size());

        System.out.print(num2);
        System.out.println(songs.get(num2));

        songs.get(num2).startSound();
        String guess = grid.showInputDialog("What is this song? **Write the name of the song**");
        guess = guess.toLowerCase().replaceAll("\\W", "");
        String answer = songs.get(num2).getFileName().toLowerCase().replaceAll("\\W", "");
        answer = answer.substring(5, answer.length() - 3);

        System.out.println(answer);

        if (answer.equals(guess)) {
          // correct
          System.out.println("Correct");
          score++;
          System.out.println(score);
          Location ifLoc = new Location(userRow, 0);
          grid.setImage(ifLoc, mainWitch);
        } else {
          // incorrect
          System.out.println("WRONG");
          score += 0;
          lives--;
          System.out.println(score);
          System.out.println(lives);
          Location ifLoc = new Location(userRow, 0);
          grid.setImage(ifLoc, mainWitch);
        }


        songs.get(num2).pauseSound();
        songs.remove(num2);
        mainSong.startSound();
     
      }
      // }

      //if(bonusNote is touched){
    //   int num3 = (int) (Math.random() * bonusSongs.size());

    //   System.out.println(num3);
    //   songs.get(num3).startSound();
    //   String guess = grid.showInputDialog("What is this song? **Write the name of the song**");
    //   guess = guess.toLowerCase().replaceAll("\\W", "");
    //   String answer = songs.get(num3).getFileName().toLowerCase().replaceAll("\\W", "");
    //   answer = answer.substring(5, answer.length() - 3);

    //   System.out.println(answer);

    //   if (answer.equals(guess)) {
    //     // correct
    //     System.out.println("Correct");
    //     score++;
    //     System.out.println(score);
    //     Location ifLoc = new Location(userRow, 0);
    //     grid.setImage(ifLoc, mainWitch);
    //   } else {
    //     // incorrect
    //     System.out.println("WRONG");
    //     score += 0;
    //     lives--;
    //     System.out.println(score);
    //     System.out.println(lives);
    //     Location ifLoc = new Location(userRow, 0);
    //     grid.setImage(ifLoc, mainWitch);
    //   }
    //   songs.get(num3).pauseSound();
    //   songs.remove(num3);
    //   mainSong.startSound();
    // }
    // System.out.println(collision);
  }
}

  public int getScore() {
    return score;
  }

  public void titleScreen() {

  }

  public void updateTitle() {
    grid.setTitle("Score:  " + getScore() + "  Lives: " + lives);
  }

  public boolean isGameOver() {
    return lives == 0 || score == goal;
  }

  public static void main(String[] args) {
    while(true){
      Game game = new Game();
      game.play();
    }
  }
}