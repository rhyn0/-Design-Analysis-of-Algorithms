import java.util.ArrayList;
import java.lang.Math;

public class DestructiveTest {

  public DestructiveTest(){}

  public ArrayList<Integer> findHighestSafeRung(int height, int safest){
    ArrayList<Integer> ret = new ArrayList<Integer>();

    ret.add(0, height);
    ret.add(1, safest);

    int drops, highCheck, safe, step, secondDrop = -1, thirdDrop = -1;
    int firstBreak = -1, secondBreak = -1;
    boolean broken = false;
    step = (int) Math.pow((double) height, 0.5);

    safe = 1; //the lowest possible safe rung is given as >= 1
    drops = highCheck = 0;
    while(highCheck < height && !broken){   //on the ladder and no broken devices
      highCheck += step;
      if (highCheck > height)     //in cases where multiple steps wont equal height
        highCheck = height;
      if (highCheck > safest){
        broken = true;
        firstBreak = highCheck;
      }
      else
        safe = highCheck; //move pointer to last checked safe rung

      ++drops;
      if (highCheck > step){
        if(secondDrop == -1)
          secondDrop = highCheck;
        else if (thirdDrop == -1)
          thirdDrop = highCheck;
      }
    }
    int i = safe + 1;
    while(broken && i < highCheck){
      if(secondDrop == -1)
        secondDrop = i;
      else if (thirdDrop == -1)
        thirdDrop = i;

      ++drops;
      if (i > safest){
        secondBreak = i;
        break;
      }
      else
        safe = i;

      ++i;
    }

    ret.add(2, safe);     //fill out answer array
    ret.add(3, drops);
    ret.add(4, step);
    ret.add(5, secondDrop);
    ret.add(6, thirdDrop);
    ret.add(7, firstBreak);
    ret.add(8, secondBreak);


    return ret;
  }

}
