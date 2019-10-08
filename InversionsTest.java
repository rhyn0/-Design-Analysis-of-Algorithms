

public class InversionsTest{

  public static void main(String[] args){
    int[] arr = {2,3,8,6,1};
    int check = Inversions.invCounter(arr);
    if (check != 5){
      System.out.println("Error, didn't get right answer. Instead got -" + check);
    }
    else{
      System.out.println("Correct!");
    }
  }
}
