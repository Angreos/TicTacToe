package GameLogic;

import java.util.InputMismatchException;
import java.util.Scanner;

import Bots.Minimax.MinimaxAi;
import GameLogic.GameMode;

public abstract class GameLoop {
  public static void Run(Game g) {
    if (g.getGameOver() == true) {
      return;
    }
    Scanner scanner = new Scanner(System.in);

    GameMode mode = askForGameMode(scanner);
    switch (mode) {
      case GameMode.pvp:
        pvpStart(g, scanner);
        break;
      case GameMode.playerAi:
        playerAiStart(g, scanner, g.getRandomTurn());
        break;
      case GameMode.doubleAi:
        aiVsAi(g);
        break;
      case GameMode.aiMult:
        try {
          int amount = scanner.nextInt();
          for (int i = 0; i < amount; i++) {
            Game x = new Game();
            if (i == amount - 1) {
              aiVsAiMultiple(x, true);
            } else {
              aiVsAiMultiple(x, false);
            }
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        break;
      default:
        break;
    }
    System.out.println();
    g.printGameState();
    scanner.close();
  }

  private static void aiVsAiMultiple(Game g, boolean printBoards) {
    while (g.getGameOver() == false) {
      try {
        int[] position = MinimaxAi.miniMaxGame(g);
        g.makeMove(position[0], position[1]);
        if (printBoards) {
          g.printBoard();
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void aiVsAi(Game g) {
    while (g.getGameOver() == false) {
      try {
        int[] position = MinimaxAi.miniMaxGame(g);
        g.makeMove(position[0], position[1]);
        g.printBoard();
        System.out.println();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void playerAiStart(Game g, Scanner scanner, char ai) {
    while (g.getGameOver() == false) {
      try {
        int[] position = new int[2];
        if (g.getCurrentTurn() == ai) {
          position = MinimaxAi.miniMaxGame(g);
        } else {
          position = askForWhereToMakeMove(g.getCurrentTurn(), g.getSize(), scanner);
        }
        g.makeMove(position[0], position[1]);
        g.printBoard();
        System.out.println();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void pvpStart(Game g, Scanner scanner) {
    while (g.getGameOver() == false) {
      try {
        int[] position = askForWhereToMakeMove(g.getCurrentTurn(), g.getSize(), scanner);
        g.makeMove(position[0], position[1]);
        g.printBoard();
        System.out.println();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static int[] askForWhereToMakeMove(char turn, int size, Scanner scanner) {
    int row = 0;
    int col = 0;
    boolean correctInput = false;
    do {
      System.out.println("Give the position within the board(" + size + ")");
      try {
        System.out.print("Row: ");
        row = scanner.nextInt();
        System.out.print("Collumn: ");
        col = scanner.nextInt();
      } catch (Exception e) {
        System.out.println("Wrong input");
      }
      if (row > size || col > size) {
        System.out.println("Out of bounds.");
      } else {
        correctInput = true;
      }

    } while (correctInput != true);
    int[] pos = { row - 1, col - 1 };
    return pos;
  }

  private static GameMode askForGameMode(Scanner scanner) {
    String input = "";
    GameMode mode = GameMode.pvp;
    boolean correctInput = false;
    while (correctInput != true) {
      System.out.println("What mode would you like to play?");
      System.out.println("1. Player vs Player");
      System.out.println("2. Player vs AI");
      System.out.println("3. AI vs AI (just spectating)");
      System.out.println("4. AI vs AI (amount)");
      input = scanner.next();
      switch (input) {
        case "1":
          correctInput = true;
          mode = GameMode.pvp;
          break;
        case "2":
          correctInput = true;
          mode = GameMode.playerAi;
          break;
        case "3":
          correctInput = true;
          mode = GameMode.doubleAi;
          break;
        case "4":
          correctInput = true;
          mode = GameMode.aiMult;
          break;

        default:
          System.out.println("Wrong input!");
          break;
      }
    }
    return mode;
  }
}
