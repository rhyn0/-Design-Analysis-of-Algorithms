import java.lang.String;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.Comparable;
import java.util.Comparator;
import java.lang.String;
import java.lang.Integer;
import java.util.Collections;


public class Knapsack{

  public static void main(String[] args){
    int n, capacity;
    ArrayList<Item> items;
    Scanner input = readfile(args[0]);
    n = input.nextInt();
    items = new ArrayList<Item>(n);
    for(int i = 0; i < n; ++i){
      Item a = new Item(input.nextInt(), input.nextInt(), input.nextInt());
      items.add(a);
    }
    capacity = input.nextInt();
    //printArray(sortRatio(items));
    //option 1
     //callBrute(n, items, capacity);
     //callGreedy(n, items, capacity);
     //callDynamic(n, items, capacity);
    //option 2
    callBB(n, items, capacity);

  }

  public static Scanner readfile(String filename){
    File ret;
    Scanner sc= null;
    try{
      ret = new File(filename);
      sc = new Scanner(ret);
    }
    catch(Exception e){
      System.err.println("File wasn't found: " + e);
      System.exit(-1);
    }
    return sc;
  }

  public static void callBB(int n, ArrayList<Item> items, int capacity){
    ArrayList<Item> answer = null, ratS = sortRatio(items);
    ArrayList<BBNode> pq = new ArrayList<>(n);
    int[] ans = new int[n];
    String bits;
    Item choice;
    BBNode curr, temp, best = new BBNode(-1, 0, 0, "");

    curr = new BBNode(-1, 0, 0, ""); //level, value, weight, bitString
    curr.setBound(bound(curr, capacity, ratS));
    pq.add(curr);
    while(!pq.isEmpty()){
      curr = pq.remove(0);
      //System.out.println(curr);
      choice = ratS.get(curr.getLevel() + 1);
      System.out.println("branching for level: " + (curr.getLevel() + 1));
      if(curr.getBound() > best.getValue()){
        temp = new BBNode(curr.getLevel() + 1, curr.getValue() + choice.getValue(), curr.getWeight() + choice.getWeight(), curr.getBits() + "1");
        if ((temp.getWeight() <= capacity) && temp.getValue() > best.getValue()){
          best = temp;
        //  System.out.println("updating max val to: " + best.getValue() + " with bitString: " + best.getBits() + " at level: " + best.getLevel());
        }
        temp.setBound(bound(temp, capacity, ratS));
        // System.out.println(temp);
        if(temp.getBound() > best.getValue())
          pq.add(temp);
        temp = new BBNode(curr.getLevel() + 1, curr.getValue(), curr.getWeight(), curr.getBits() + "0");
        temp.setBound(bound(temp, capacity, ratS));
        // System.out.println(temp);
        if (temp.getBound() > best.getValue())
          pq.add(temp);
        Collections.sort(pq);
      }
    }

    bits = best.getBits();
    for(int i = 0; i < bits.length(); ++i){
      if(bits.charAt(i) == '1')
        ans[ratS.get(i).getKey() - 1] = 1;
    }
    answer = bitsToIndex(ans, items);
    printAnswer("Branch and Bound", answer, best.getValue(), best.getWeight());
  }

  //return t.getValue() + dp[capacity - t.getWeight()];
  //would work if doing dfs-like approach
  protected static float bound(BBNode t, int capacity, ArrayList<Item> rs){
    if ((t.getWeight() >= capacity) || (t.getLevel() == rs.size() - 1))
      return 0;
    else
      return t.getValue() + (capacity - t.getWeight()) * rs.get(t.getLevel() + 1).getRatio();
  }

  public static void printAnswer(String function, ArrayList<Item> solution, int v, int w){
    System.out.printf("Using %s the best feasible solution found:\tValue %d, Weight %d\n", function, v, w);
    for(int i = 0; i < solution.size(); ++i)
      System.out.print(solution.get(i).getKey() + " ");

  }

  public static void callBrute(int n, ArrayList<Item> items, int capacity){
    ArrayList<Item> answer;
    int[] ans = null, bits = new int[n];
    int totValue = 0, totWeight = 0;
    int tempV, tempW;
    for(int k = 0; k < n; ++k)
      bits[k] = 0;
    for (int i = 0; i < Math.pow(2,n); ++i){
      int j = 0;
      tempV = tempW = 0;
      while((j < n - 1) && (bits[j] != 0)){
        bits[j] = 0;
        ++j;
      }
      bits[j] = 1;
      for(int x = 0; x < n; ++x){
        if(bits[x] == 1){
          tempW += items.get(x).getWeight();
          tempV += items.get(x).getValue();
        }
      }
      if((tempV > totValue) && (tempW <= capacity)){
        totValue = tempV;
        totWeight = tempW;
        ans = bits.clone();
      }
    }
    for(int j = 0; j < n; ++j)
      System.out.print(ans[j]);
    System.out.println();
    answer = bitsToIndex(ans, items);
    printAnswer("Brute force", answer, totValue, totWeight);
  }

  public static void callGreedy(int n, ArrayList<Item> items, int capacity){
    ArrayList<Item> answer, ratSorted;
    int[] ans = new int[n];
    int totValue = 0, totWeight = 0;
    Item temp, hold;
    ratSorted = sortRatio(items);
    printArray(ratSorted);
    for(int i = 0; i < n; ++i){
      if(totWeight == capacity){
        System.out.println("breaking for full sack");
        break;
      }

      if ((totWeight + ratSorted.get(i).getWeight()) <= capacity){
        ans[ratSorted.get(i).getKey() - 1] = 1;
        totValue += ratSorted.get(i).getValue();
        totWeight += ratSorted.get(i).getWeight();
      }
    }

    answer = bitsToIndex(ans, items);
    printAnswer("Greedy (not necessarily optimal)", answer, totValue, totWeight);
  }

  public static void callDynamic(int n, ArrayList<Item> items, int capacity){
    ArrayList<Item> answer;
    int[] ans = new int[n];
    int[][] table;
    int weight = capacity;
    for(int k = 0; k < n; ++k)
      ans[k] = 0;
    table = buildTable(items, n, capacity);
    //traceback
    for(int i = n; i > 0; --i){
      if (table[i][weight] != table[i - 1][weight]){
        weight -= items.get(i - 1).getWeight();
        ans[i - 1] = 1;
        if (weight <= 0)
          break;
      }
    }

    answer = bitsToIndex(ans, items);
    printAnswer("Dynamic programming", answer, table[n][capacity], capacity - weight);
  }

  protected static int[][] buildTable(ArrayList<Item> items, int n, int capacity){
    int[][] table = new int[n + 1][capacity + 1];
    for(int i = 0; i <= n; ++i){
      for(int j = 0; j <= capacity; ++j){
        if (i == 0 || j == 0)
          table[i][j] = 0;
        else if (items.get(i - 1).getWeight() <= j)
          table[i][j] = Math.max(table[i - 1][j], table[i - 1][j - items.get(i - 1).getWeight()] + items.get(i - 1).getValue());
        else
          table[i][j] = 0;
      }
    }
    return table;
  }

  public static ArrayList<Item> bitsToIndex(int[] bits, ArrayList<Item> items){
    ArrayList<Item> ret = new ArrayList<>();
    for(int i = 0; i < bits.length; ++i){
      if(bits[i] == 1)
        ret.add(items.get(i));
    }
    return ret;
  }

  //old version of sortRatio
  // Item temp, hold;
  // int s = items.size();
  // for(int i = 0; i < s; ++i){
  //   temp = new Item(0,0,1);
  //   for (int j = 0; j < items.size(); ++j){
  //     if (items.get(j).getRatio() > temp.getRatio())
  //       temp = items.get(j);
  //   }
  //   items.remove(temp);
  //   ret.add(temp);
  // }
  private static ArrayList<Item> sortRatio(ArrayList<Item> items){
    ArrayList<Item> ret = (ArrayList<Item>)items.clone();

    Collections.sort(ret, new RatioSorter());
    return ret;
  }

  private static void printArray(ArrayList<?> arr){
    for(int i = 0; i < arr.size(); ++i)
      System.out.println(arr.get(i));
  }

  private static void printArray(int[] arr){
    for(int i = 0; i < arr.length; ++i)
      System.out.println(arr[i]);
  }
}

class Item implements Comparable<Item>{
  private int key;
  private int weight, value;
  private float ratio;

  public Item(int k, int v, int w){
    this.key = k;
    this.weight = w;
    this.value = v;
    this.ratio = (float) v / w;
  }

  public int getKey(){
    return this.key;
  }

  public int getWeight(){
    return this.weight;
  }

  public int getValue(){
    return this.value;
  }

  public float getRatio(){
    return this.ratio;
  }

  public String toString(){
    return (this.key + " " + this.value + " " + this.weight + " " + this.ratio);
  }

  public int compareTo(Item a){
    return this.getKey() - a.getKey();
  }
}

class RatioSorter implements Comparator<Item>{
  public int compare(Item a, Item b){
    if (a.getRatio() < b.getRatio())
      return 1;
    else if (a.getRatio() > b.getRatio())
      return -1;
    else
      return 0;
  }
}

class BBNode implements Comparable<BBNode>{
  private float bound;
  private int level, value, weight;
  private int[] arr;
  private String bits;

  public BBNode(int l, int v, int w, String b){
    this.level = l;
    this.value = v;
    this.weight = w;
    this.bits = new String(b);
  }

  public int getLevel(){
    return this.level;
  }

  public int getValue(){
    return this.value;
  }

  public int getWeight(){
    return this.weight;
  }

  public void setBound(float b){
    this.bound = b;
  }

  public float getBound(){
    return this.bound;
  }

  public String getBits(){
    return this.bits;
  }

  public String toString(){
    return ("level: " + this.level + " bound: " + this.bound + " bits: " + this.bits + "\nweight: " + this.weight + " value: " + this.value);
  }

  public int compareTo(BBNode a){
    return a.getValue() - this.getValue();
    // if (a.getBound() > this.getBound())
    //   return 1;
    // else if (a.getBound() < this.getBound())
    //   return -1;
    // else
    //   return a.getValue() - this.getValue();
  }
}
