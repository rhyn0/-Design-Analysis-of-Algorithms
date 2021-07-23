

public class WISTest{

  public static void main(String[] args){
    int[] start = {3,3,1,10,8};
    int[] finish = {7,10,4,13,11};
    int[] weight = {6,9,5,8,10};
    int[] answer = {2,4};
    int[] call;

    call = WgtIntScheduler.getOptSet(start, finish, weight);

    for(int i = 0; i < answer.length; ++i){
      if(call[i] != answer[i]){
        System.out.println("Got index - " + i + " - wrong, got back - " + call[i] + " -");
      }
    }
    System.out.println("Finished!");
  }
}
