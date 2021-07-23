import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class RodCutter {
  private ArrayList<Integer> rodPrices; // i + 1 equals rod length, with corresponding price at i
  private ArrayList<Integer> table;
  private static Scanner sc;

  public RodCutter() {
  }

  public static void main(String[] args) {
    int noTests, rodMax;
    RodCutter rc = new RodCutter();
    try {
      sc = new Scanner(new FileInputStream(new File("intro_dp/rodOptTest.txt")));
    } catch (FileNotFoundException e) {
      System.err.println(e);
    }
    noTests = sc.nextInt();
    for (int i = 1; i <= noTests; ++i) {
      rc.readFile();

      rc.table.add(rc.rodPrices.get(1));
      for (int j = 2; j < rc.rodPrices.size(); ++j) {
        rodMax = rc.rodPrices.get(j);
        for (int x = 1; x <= (j / 2); ++x) {
          // System.out.print("at index " + j + " ");
          // System.out.println("comparing " + rc.rodPrices.get(j) + " to " + rc.table.get(j - x) + " + " + rc.table.get(x));
          rodMax = Math.max((rc.table.get(j - x) + rc.table.get(x)), rodMax);
          // System.out.println("max was " + rodMax);
        }
        rc.table.add(rodMax);
      }
      rc.printCase(i);
      rc.printOptimal();
    }
  }

  private void readFile() {
    int size;
    size = sc.nextInt();
    table = new ArrayList<Integer>(size + 1);
    table.add(0);
    rodPrices = new ArrayList<Integer>(size + 1);
    rodPrices.add(0);
    for (int i = 1; i <= size; ++i)
      rodPrices.add(sc.nextInt());
  }

  private void printCase(int testNo) {
    System.out.println("Case " + testNo + ":");
    for (int i = 1; i < table.size(); ++i) {
      System.out.println("total for length " + i + "\t = " + table.get(i));
    }
  }

  private void breakDown(ArrayList<Integer> indices, int index) {
    int rodMax = rodPrices.get(index);
    int lIndex = 0, rIndex = 0;
    if (table.get(index) != rodPrices.get(index)) {
      for (int i = 1; i <= index / 2; ++i) {
        if (table.get(i) + table.get(index - i) >= rodMax) {
          rodMax = table.get(i) + table.get(index - i);
          lIndex = i;
          rIndex = index - i;
        }
      }
      indices.set(index, 0);
      indices.set(lIndex, indices.get(lIndex) + 1);
      indices.set(rIndex, indices.get(rIndex) + 1);
    }
  }

  private void printOptimal() {
    int rodMax = rodPrices.get(rodPrices.size() - 1);
    ArrayList<Integer> indices = new ArrayList<Integer>();
    for (int j = 0; j < table.size(); ++j)
      indices.add(0);
    System.out.println("Optimal rod cutting");
    breakDown(indices, table.size() - 1);
    for (int i = table.size() - 2; i > 0; --i) {
      if (indices.get(i) != 0)
        breakDown(indices, i);
    }
    for (int j = 1; j < table.size(); ++j) {
      if (indices.get(j) != 0)
        System.out.println("Number of rods of length " + j + "\t = " + indices.get(j));
    }
    System.out.println("\n");
  }

}
