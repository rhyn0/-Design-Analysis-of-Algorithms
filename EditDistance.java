import java.lang.String;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;


public class EditDistance{

  public static void main(String[] args){
    Scanner input = readfile(args[0]);
    String x = input.nextLine();
    String y = input.nextLine();
    int[][] table = new int[y.length() + 1][x.length() + 1];

    for(int i = 0; i <= y.length(); ++i){ //iteration to fill the table
      for(int j = 0; j <= x.length(); ++j){
        if(i == 0)
          table[i][j] = 2 * j;
        else if (j == 0)
          table[i][j] = 2 * i;
        else if (y[i - 1] == x[j - 1])
          table[i][j] = table[i - 1][j - 1];
        else
          table[i][j] = Math.min(1 + table[i - 1][j - 1], 2 + table[i - 1][j], 2 + table[i][j - 1]);
      }
    }

    //traceback

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
    }
    return sc;
  }

  public void traceback(String x, String y, ArrayList<Integer> answer, int minEdit){

  }
}
