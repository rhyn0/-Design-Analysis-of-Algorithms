import java.util.ArrayList;
import java.lang.Math;

public class DestructiveTest {

  public DestructiveTest(){}

  /**
   * The situation is as follow, the company is testing whether a newly created device will break at a height.
   * Incrementally go up the rungs of a ladder with number of rungs equal to height.
   * There are only 3 devices created at this time, so don't break more than 2 of them.
   * And the company's time is precious, so minimize the number of drops.
   * Return an array of integers corresponding to the following:
   *  1. height of ladder
   *  2. safest rung on the ladder
   *  3. Highest safe rung checked
   *  4. Number of drops performed
   *  5. Size of incrementing range
   */
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
      /* in cases where steps does not divide height */
      if (highCheck > height)
      {     
        highCheck = height;
      }
      /* if we exceed the safety limit, we get a break, record that */
      if (highCheck > safest)
      {
        broken = true;
        firstBreak = highCheck;
      }
      else
      {
        safe = highCheck; //move pointer to last checked safe rung
      }
      drops++;
      if (highCheck > step)
      {
        if(secondDrop == -1)
        {
          secondDrop = highCheck;
        }
        else if (thirdDrop == -1)
        {
          thirdDrop = highCheck;
        }
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

    /* fill out answer array */
    ret.add(2, safe);     
    ret.add(3, drops);
    ret.add(4, step);
    ret.add(5, secondDrop);
    ret.add(6, thirdDrop);
    ret.add(7, firstBreak);
    ret.add(8, secondBreak);


    return ret;
  }

}
