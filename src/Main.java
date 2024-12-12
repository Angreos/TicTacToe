import Bots.Minimax.*;
import GameLogic.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello World form Java!");
    Game firstGame = new Game();
    GameLoop.Run(firstGame);

    System.out.println();
    Game.printAllGamesStats();

  }
}
