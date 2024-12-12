package GameLogic;

public abstract class WinCheck {
  private static char[] POSSIBLE_CHARACTERS = Game.getPossibleCharacters();



  public static char checkForWinningPosition(char[][] board) {
    int diagonal = checkDiagonals(board);
    boolean diagonalWon = diagonal > -1;
    if (diagonalWon) {
      return POSSIBLE_CHARACTERS[diagonal];
    }
    for (int i = 0; i < board.length; i++) {
      int row = checkRow(board, i);
      int col = checkCollumn(board, i);
      boolean rowWon = row > -1;
      boolean colWon = col > -1;
      if (rowWon || colWon) {
        if (rowWon) {
          return POSSIBLE_CHARACTERS[row];
        }
        return POSSIBLE_CHARACTERS[col];
      }
    }
    return '\u0000';
  }

  private static int checkRow(char[][] board, int row) {
    char currentChar = board[row][0];
    for (int i = 1; i <board.length; i++) {
      if (board[row][i] == '\u0000') {
        return -2;
      }
      if (board[row][i] != currentChar) {
        return -1;
      }
    }
    return (currentChar == POSSIBLE_CHARACTERS[0]) ? 0 : 1;
  }

  private static int checkCollumn(char[][] board, int col) {
    char currentChar = board[0][col];
    for (int i = 1; i < board.length; i++) {
      if (board[i][col] == '\u0000') {
        return -2;
      }
      if (board[i][col] != currentChar) {
        return -1;
      }
    }
    return (currentChar == POSSIBLE_CHARACTERS[0]) ? 0 : 1;
  }

  private static int checkDiagonals(char[][] board) {
    int size = board.length;
    boolean gameIsOver = true;
    char currentChar1 = board[0][0];
    char currentChar2 = board[size - 1][0];
    for (int i = 0; i < size; i++) {
      if (board[i][i] == '\u0000' || board[size - i - 1][i] == '\u0000') {
        gameIsOver = false;
      }
      // -x diagonal
      if (board[i][i] != currentChar1) {
        currentChar1 = '\u0000';
      }
      // x diagonal
      if (board[size - i - 1][i] != currentChar2) {
        currentChar2 = '\u0000';
      }
    }
    if (currentChar1 == '\u0000' && currentChar2 == '\u0000') {
      return gameIsOver ? -1 : -2;
    }

    return (currentChar1 == POSSIBLE_CHARACTERS[0]) || (currentChar2 == POSSIBLE_CHARACTERS[0]) ? 0 : 1;
  }
}
