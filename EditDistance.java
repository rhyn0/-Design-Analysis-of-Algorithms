import java.lang.String;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class EditDistance{

  public static void main(String[] args){
    Scanner input = readfile(args[0]);
    String x = input.nextLine();
    String y = input.nextLine();
    System.out.println(x.length());
    System.out.println("x len is 10");

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

  public void traceback(ArrayList<ArrayList<Integer>> table, int xlen, int ylen, int minEdit){

  }
}
