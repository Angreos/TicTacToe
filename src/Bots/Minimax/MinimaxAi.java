package Bots.Minimax;

import GameLogic.Game;
import GameLogic.WinCheck;

public abstract class MinimaxAi {
  private static final char[] PLAYERS = Game.getPossibleCharacters();
  private static final char EMPTY = '\u0000';

  public static int[] miniMaxGame(Game g) {
    char currentPlayer = g.getCurrentTurn();
    char opponent = getOpponent(currentPlayer);

    char[][] board = g.getBoard();
    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = new int[2];

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == EMPTY) {
          board[i][j] = currentPlayer; // Simulate move
          int score = miniMax(board, 0, false, currentPlayer, opponent);
          board[i][j] = EMPTY; // Undo move

          if (score > bestScore) {
            bestScore = score;
            bestMove[0] = i;
            bestMove[1] = j;
          }
        }
      }
    }

    return bestMove;
  }

  private static int miniMax(char[][] board, int depth, boolean isMaximizing, char currentPlayer, char opponent) {
    char winner = WinCheck.checkForWinningPosition(board);
    if (winner != EMPTY) {
      return winner == currentPlayer ? 10 - depth : depth - 10;
    }

    if (isBoardFull(board)) {
      return 0; // Draw
    }

    if (isMaximizing) {
      int maxScore = Integer.MIN_VALUE;
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == EMPTY) {
            board[i][j] = currentPlayer; // Simulate move
            int score = miniMax(board, depth + 1, false, currentPlayer, opponent);
            board[i][j] = EMPTY; // Undo move
            maxScore = Math.max(maxScore, score);
          }
        }
      }
      return maxScore;
    } else {
      int minScore = Integer.MAX_VALUE;
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == EMPTY) {
            board[i][j] = opponent; // Simulate opponent's move
            int score = miniMax(board, depth + 1, true, currentPlayer, opponent);
            board[i][j] = EMPTY; // Undo move
            minScore = Math.min(minScore, score);
          }
        }
      }
      return minScore;
    }
  }

  private static char getOpponent(char currentPlayer) {
    for (char player : PLAYERS) {
      if (player != currentPlayer) {
        return player;
      }
    }
    throw new IllegalStateException("Opponent not found!");
  }

  private static boolean isBoardFull(char[][] board) {
    for (char[] row : board) {
      for (char cell : row) {
        if (cell == EMPTY) {
          return false;
        }
      }
    }
    return true;
  }
}
