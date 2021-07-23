import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TopSorterTest {
  public static void main(String[] args) {
    TopSorter ts = new TopSorter();
    ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    List<Integer> mine;

    mine = ts.topSortGenerator("graph_sorter/topSortTest2.txt");
    for (int i = 0; i < answer.size(); ++i) {
      if (!answer.get(i).equals(mine.get(i))) {
        System.out.println("Position -" + i + "- is wrong. Put " + mine.get(i));
      }
    }
    System.out.println("Finished");
  }
}
