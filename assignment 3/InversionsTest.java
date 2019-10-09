

public class InversionsTest{

  public static void main(String[] args){
<<<<<<< HEAD:assignment 3/InversionsTest.java
    int[] arr = new int[1];
    arr[0] = 2;
    // arr[1] = 3;
    // arr[2] = 8;
    // arr[3] = 6;
    // arr[4] = 1;
=======
    int[] arr = new int[5];
    arr[0] = 2;
    arr[1] = 3;
    arr[2] = 8;
    arr[3] = 6;
    arr[4] = 1;
>>>>>>> ed1e64f21fc13d333d1d7dd669b0993875eac39b:assignment 3/InversionsTest.java
    int check = Inversions.invCounter(arr);
    if (check != 6){
      System.out.println("Error, didn't get right answer. Instead got " + check);
    }
    else{
      System.out.println("Correct!");
    }
  }
}
