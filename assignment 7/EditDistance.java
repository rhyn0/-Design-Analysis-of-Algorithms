import java.lang.String;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;
import java.lang.System;


public class EditDistance{

  public static void main(String[] args){
    int file;
    Scanner input = null;
    if((file = getArgs(args)) != -1){
      input = readfile(args[file]);
    }
    else
      System.exit(-1);

    String x = input.nextLine();
    String y = input.nextLine();
    int[][] table = new int[y.length() + 1][x.length() + 1];
    ArrayList<Integer> answerKey = new ArrayList<>();
    for(int i = 0; i < Math.max(x.length(), y.length()); ++i)
      answerKey.add(0);

    for(int i = 0; i <= y.length(); ++i){ //iteration to fill the table
      for(int j = 0; j <= x.length(); ++j){
        if(i == 0)
          table[i][j] = 2 * j;
        else if (j == 0)
          table[i][j] = 2 * i;
        else if (y.charAt(i - 1) == x.charAt(j - 1))
          table[i][j] = table[i - 1][j - 1];
        else
          table[i][j] = Math.min(1 + table[i - 1][j - 1], Math.min(2 + table[i - 1][j], 2 + table[i][j - 1]));
      }
    }
    // for(int i = 0; i <= x.length(); ++i){
    //   for(int j = 0; j <= y.length(); ++j)
    //     System.out.print(table[j][i] + " ");
    //
    //   System.out.println();
    // }

    //traceback
    int posI = y.length(), posJ = x.length();
    for(int i = answerKey.size() - 1; i >= 0; --i){
      if ((table[posI][posJ] == table[posI - 1][posJ - 1]) && (y.charAt(posI - 1) == x.charAt(posJ - 1))) {
        answerKey.set(i, 0);
        --posI;
        --posJ;
      }
      else if (table[posI][posJ] == (table[posI - 1][posJ - 1] + 1)){
        answerKey.set(i, 1);
        --posI;
        --posJ;
      }
      else if(table[posI][posJ] == (table[posI - 1][posJ] + 2)){
        answerKey.set(i,2);
        --posI;
      }
      else{
        answerKey.set(i,2);
        --posJ;
      }
      if (posI == 0){
        --i;
        while(i >= 0){
          answerKey.set(i, 2);
          --i;
        }
        break;
      }
      if (posJ == 0){
        --i;
        while(i >= 0){
          answerKey.set(i, 2);
          --i;
        }
        break;
      }
    }
    // for(int i = 0; i < answerKey.size(); ++i)
    //   System.out.print(answerKey.get(i) + " ");
    // System.out.println();
    if (args.length > 1)
      System.out.println(args[file] + "\t" + table[y.length()][x.length()]);
    else
      traceback(x, y, answerKey, table[y.length()][x.length()]);
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

  public static int getArgs(String[] args){
    if(args.length > 2){
      System.err.println("wrong amount of arguments");
      System.exit(-1);
    }
    for(int i = 0; i < args.length; ++i){
      if(!args[i].equals("p"))
        return i;
    }
    return -1;
  }

  public static void traceback(String x, String y, ArrayList<Integer> answer, int minEdit){
    boolean m = (x.length() >= y.length() ? true : false);
    int lessP = 0;
    System.out.println("Edit distance = " + minEdit);
    for(int i = 0; i < answer.size(); ++i){
      if(m){
        if (answer.get(i) == 0 || answer.get(i) == 1){
          System.out.println(x.charAt(i) + " " + y.charAt(lessP) + " " + answer.get(i));
          ++lessP;
        }
        else
          System.out.println(x.charAt(i) + " - 2");
      }
      else{
        if (answer.get(i) == 0 || answer.get(i) == 1){
          System.out.println(x.charAt(lessP) + " " + y.charAt(i) + " " + answer.get(i));
          ++lessP;
        }
        else
          System.out.println("- " + y.charAt(i) + " 2");
      }
    }
  }
}
