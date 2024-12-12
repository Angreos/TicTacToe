package GameLogic;

import java.util.Random;

public class Game {
  private static final Random R = new Random();
  private static final char[] POSSIBLE_CHARACTERS = { 'X', 'O' };

  private static int boards = 0;
  private static int xWon = 0;
  private static int oWon = 0;
  private static int draw = 0;

  private char[][] board;
  private final int SIZE;
  private char currentTurn;

  private boolean gameOver = false;
  private int availableMoves;
  private char winner;

  // constructors
  public Game() {
    availableMoves = 3 * 3;
    currentTurn = getRandomTurn();
    board = new char[3][3];
    SIZE = 3;
    boards++;
  }

  public Game(int size) {
    currentTurn = getRandomTurn();
    board = new char[size][size];
    availableMoves = size * size;
    SIZE = size;
    boards++;
  }

  // getter for class variables
  public static int getoWon() {
    return oWon;
  }

  public static int getDraw() {
    return draw;
  }

  public static int getxWon() {
    return xWon;
  }

  public static int getBoards() {
    return boards;
  }

  // getter for board
  public char[][] getBoard() {
    return board;
  }

  public int getSize() {
    return this.SIZE;
  }
  public static char[] getPossibleCharacters(){
    return POSSIBLE_CHARACTERS;
  } 

  // methods
  public boolean getGameOver() {
    return this.gameOver;
  }

  public char getCurrentTurn() {
    return this.currentTurn;
  }

  public char getRandomTurn() {
    return POSSIBLE_CHARACTERS[R.nextInt(2)];
  }

  private void swapTurn() {
    currentTurn = (currentTurn == POSSIBLE_CHARACTERS[0]) ? POSSIBLE_CHARACTERS[1] : POSSIBLE_CHARACTERS[0];
  }

  public void makeMove(int y, int x) throws IllegalArgumentException {
    // check if position is available
    if (gameOver == true) {
      throw new IllegalArgumentException("Game is already over");
    }
    if (board[y][x] != '\u0000') {
      throw new IllegalArgumentException("Field is unavalable");
    }
    board[y][x] = currentTurn;
    swapTurn();
    availableMoves--;
    char win = WinCheck.checkForWinningPosition(getBoard());
    if (win == POSSIBLE_CHARACTERS[0] || win == POSSIBLE_CHARACTERS[1]) {
      endGame(win);
    } else if (win == '\u0000' && availableMoves == 0) {
      endGame('\u0000');
    }

  }

  private void endGame(char p_winner) throws IllegalArgumentException {
    if (gameOver == false) {
      this.gameOver = true;
      this.winner = p_winner;
      if (winner == '\u0000') {
        draw++;
      } else if (winner == POSSIBLE_CHARACTERS[0]) {
        xWon++;
      } else {
        oWon++;
      }
    } else {
      throw new IllegalArgumentException("Game is already over");
    }
  }


  public void printBoard() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        char place = board[i][j];
        if (place == '\u0000') {
          System.out.print(" ");
        } else {
          System.out.print(place);
        }
        if (j != SIZE - 1) {
          System.out.print(" | ");
        }
      }
      System.out.println();
    }
  }

  public void printGameState() {
    printBoard();
    System.out.println();
    System.out.println(gameOver ? "Game is over" : "Game is ongoing");
    if (gameOver && winner != '\u0000') {
      System.out
          .println("Winner: " + (winner == POSSIBLE_CHARACTERS[0] ? POSSIBLE_CHARACTERS[0] : POSSIBLE_CHARACTERS[1]));
    } else if (gameOver) {
      System.out.println("No winner | Draw");
    } else {
      System.out.println("Current turn: " + currentTurn);
    }
  }

  public static void printAllGamesStats() {
    System.out.println("Games: " + getBoards());
    System.out.println("X Wins: " + getxWon());
    System.out.println("O Wins: " + getoWon());
    System.out.println("Draws: " + getDraw());
  }
}
