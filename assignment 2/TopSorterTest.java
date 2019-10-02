import java.util.Arrays;
import java.util.ArrayList;

public class TopSorterTest{
  public static void main(String[] args){
    TopSorter ts = new TopSorter();
    ArrayList<Integer> answer = new ArrayList<Integer>();
    answer.add(1);
    answer.add(2);
    answer.add(3);
    answer.add(4);
    answer.add(5);
    ArrayList<Integer> mine;

    mine = ts.topSortGenerator("topSortTest2.txt");
    for(int i = 0; i < answer.size(); ++i){
      if (answer.get(i) != mine.get(i)){
        System.out.println("Position -" + i + "- is wrong. Put " + mine.get(i));
      }
    }
    System.out.println("Finished");
  }
}
