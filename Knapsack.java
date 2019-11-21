import java.lang.String;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Knapsack{

  public static void main(String[] args){
    int n, capacity;
    ArrayList<Item> items = new ArrayList<Item>();
    Scanner input = readfile(args[0]);
    n = input.nextInt();
    for(int i = 0; i < n; ++i){
      Item a = new Item(input.nextInt(), input.nextInt(), input.nextInt());
      items.add(a);
    }
    capacity = input.nextInt();

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
  /*
  *   build branch and bound somewhere around here
  */

}

class Item{
  private int key;
  private int weight, value;
  private double ratio;

  public Item(int k, int v, int w){
    this.key = k;
    this.weight = w;
    this.value = v;
    this.ratio = w/v;
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

  public double getRatio(){
    return this.ratio;
  }

  public String toString(){
    return (this.key + " " + this.value + " " + this.weight);
  }
}
