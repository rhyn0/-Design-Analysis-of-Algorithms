import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;



public class MyGraphTest{
  public MyGraphTest(){}

  public static void main(String[] args){
    ArrayList<HashSet<Integer>> answer = new ArrayList<HashSet<Integer>>();
    ArrayList<HashSet<Integer>> myG = new ArrayList<HashSet<Integer>>();
    List<Integer> set1 = Arrays.asList(4);
    List<Integer> set2 = Arrays.asList(1,2,3,4,5);
    List<Integer> set3 = Arrays.asList(6,7,8,9);
    List<Integer> setSolo = Arrays.asList(10);
    List<Integer> set4 = Arrays.asList(11,12,13,14);
    answer.add(new HashSet<>(set1));
    answer.add(new HashSet<>(set2));
    answer.add(new HashSet<>(set3));
    answer.add(new HashSet<>(setSolo));
    answer.add(new HashSet<>(set4));

    MyGraph graph = new MyGraph();
    try {
      graph.readfile_graph("bfstest1.txt");
    }
    catch(FileNotFoundException e){
      System.out.println(e);
    }
    MyGraph.initialize_search(graph);
    System.out.println(graph.bipartiteCheck());
    // myG = graph.connectCheck();
    //
    // if (!myG.containsAll(answer)){
    //   for(int i = 0; i < answer.size(); ++i){
    //     if (!myG.get(i).equals(answer.get(i))){
    //       System.out.println("Position -" + i + "- was wrong, was " + myG.get(i).toString());
    //     }
    //   }
    //   System.out.println(myG);
    // }

  }
}
