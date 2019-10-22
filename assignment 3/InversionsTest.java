

public class InversionsTest{

  public static void main(String[] args){
    int[] arr = new int[5];
    arr[0] = 2;
    arr[1] = 3;
    arr[2] = 8;
    arr[3] = 6;
    arr[4] = 1;
    int check = Inversions.invCounter(arr);
    if (check != 6){
      System.out.println("Error, didn't get right answer. Instead got " + check);
    }
    else{
      System.out.println("Correct!");
    }
  }
}
