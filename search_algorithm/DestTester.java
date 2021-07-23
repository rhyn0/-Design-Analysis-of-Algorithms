import java.util.*;

public class DestTester {

  public static void main(String[] args) {
    int height = 35, safest = 34;
    ArrayList<Integer> answer = new ArrayList<>(Arrays.asList(height, safest, safest, 11, 5, 10, 15, 35, -1));
    DestructiveTest gen = new DestructiveTest();
    List<Integer> calc = gen.findHighestSafeRung(height, safest);
    boolean match = true;
    int i = 0;
    while ((match) && (i < 9)) {
      if (!answer.get(i).equals(calc.get(i))) {
        match = false;
      }
      i++;
    }
    System.out.println(calc);
    if (match)
      System.out.println("Congratulations all the terms match.");
    else
      System.out.println("your int at position  " + (i - 1) + " should be '" + answer.get(i - 1) + "' you returned '"
          + calc.get(i - 1) + "'");
  }
}
