import java.util.*;
import java.lang.Math;

public class DestTester{

  public static void main(String[] args){
    int height =  100, safest = 98;
    int[] arr = {height, safest, safest, 0, 3, -1, -1, -1, -1};
    ArrayList<Integer> answer = new ArrayList<Integer>();
    DestructiveTest gen = new DestructiveTest();
    ArrayList<Integer> calc = gen.findHighestSafeRung(height,safest);
    for (int pos : arr){
      answer.add(pos);
    }
    boolean match = true;
    int i = 0;
    while ((match == true)&&(i<9)){
         if (!(answer.get(i) == calc.get(i)))
            match = false;
         i++;
       }
      System.out.println(calc);
      System.out.println(2 * (int)Math.sqrt(height) - 1);
      if (match == true)
         System.out.println("Congratulations all the ints match." );
      else
         System.out.println("your int at position  " + (i - 1)  + " should be -" + answer.get(i-1)+ "- you returned -" + calc.get(i-1)+"-");
  }
}
