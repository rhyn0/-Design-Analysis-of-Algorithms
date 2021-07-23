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
    // for(int i = 0; i <= x.length(); ++i){ //print the table
    //   for(int j = 0; j <= y.length(); ++j)
    //     System.out.printf("%2d ", table[j][i]);
    //
    //   System.out.println();
    // }

    if (args.length > 1)
      System.out.println(args[file] + "\t" + table[y.length()][x.length()]);
    else
      traceback(x, y, answerKey, table);
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

  public static void traceback(String x, String y, ArrayList<Integer> answerKey, int[][] table){
    int posI = y.length(), posJ = x.length();
    for(int i = answerKey.size() - 1; i >= 0; --i){

      if ((table[posI][posJ] == table[posI - 1][posJ - 1]) && (y.charAt(posI - 1) == x.charAt(posJ - 1))) { //equal and diagonal move
        answerKey.set(i, 0);
        System.out.println("index " + posJ + " is matching, i is: " + i);
        System.out.println("index " + posI + " is matching");
        --posI;
        --posJ;
      }
      else if (table[posI][posJ] == (table[posI - 1][posJ - 1] + 1)){   //wrong and diagonal move
        answerKey.set(i, 1);
        --posI;
        --posJ;
      }
      else if(table[posI][posJ] == (table[posI - 1][posJ] + 2)){  //put a space
        answerKey.set(i,2);
        System.out.println("index " + i + " is wrong in row " + posI);
        --posI;
      }
      else{
        answerKey.set(i,2);

        --posJ;
      }
      if (posI == 0 || posJ == 0){
        finishAnswer(answerKey, posJ, posI);
        break;
      }

    }
    System.out.println(answerKey);
    printAnswer(x, y, answerKey, table[y.length()][x.length()]);
  }

  public static void finishAnswer(ArrayList<Integer> answer, int j, int i){
    while(j > 0){
      answer.set(j - 1, 2);
      --j;
    }
    while(i > 0){
      answer.set(i - 1, 2);
      --i;
    }
  }

  public static void printAnswer(String x, String y, ArrayList<Integer> answer, int minEdit){
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
