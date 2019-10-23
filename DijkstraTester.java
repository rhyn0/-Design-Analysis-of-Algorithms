import java.util.Arrays;
import java.util.ArrayList;

public class DijkstraTester{
  public static void main(String[] args){
    Dijkstra ts = new Dijkstra();
    int[][] answer = {
    {1, 0, 1},
    {2, 6, 1},
    {3, 5, 1},
    {4, 4, 1},
    {5, 12, 3}
  };
    int[][] mine;

    mine = Dijkstra.findShortPaths("dijkstra_test1.txt");
    for(int i = 0; i < answer.length; ++i){
      for(int j = 0; j < answer[i].length; ++j){
        if (answer[i][j] != mine[i][j]){
          System.out.println("Position -" + i + ", " + j + "- is wrong. Put " + mine[i][j]);
        }
      }
    }
    System.out.println("Finished");
  }
}
