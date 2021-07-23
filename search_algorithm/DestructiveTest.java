import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The way this implementation works is based on a search algorithm.
 * If this were a contiguous sorted array of integers and looking for target 'safest',
 * it would be faster to not check every index but to step over ones until we 'broke' or exceeded the target
 * The optimal value has been found to be the square root of the range.
 * In this case, the range is [0, height] so we only take sqrt(height) but in a range [a, b] we would take sqrt(b - a). 
 */
public class DestructiveTest {

  /**
   * The situation is as follow, the company is testing whether a newly created
   * device will break at a height. Incrementally go up the rungs of a ladder with
   * number of rungs equal to height. There are only 3 devices created at this
   * time, so don't break more than 2 of them. And the company's time is precious,
   * so minimize the number of drops. 
   * Return an array of integers corresponding to the following: 
   *  1. height of ladder 
   *  2. safest rung on the ladder 
   *  3. Highest safe rung checked 
   *  4. Number of drops performed 
   *  5. Size of steps
   *  6. When was the second drop, -1 if never occurred
   *  7. when was the third drop, -1 if never occurred
   *  8. The first break, -1 if never occurred
   *  9. The second break, -1 if never occurred
   */
  public List<Integer> findHighestSafeRung(int height, int safest) {
    ArrayList<Integer> ret = new ArrayList<>();

    ret.add(height);
    ret.add(safest);

    int drops, highCheck, safe, step, secondDrop = -1, thirdDrop = -1;
    int firstBreak = -1, secondBreak = -1;
    boolean broken = false;
    step = (int) Math.pow((double) height, 0.5);

    safe = 1; // the lowest possible safe rung is given as >= 1
    drops = highCheck = 0;
    while (highCheck < height && !broken) { // on the ladder and no broken devices
      highCheck += step;
      /* in cases where steps does not divide height */
      if (highCheck > height) {
        highCheck = height;
      }
      /* if we exceed the safety limit, we get a break, record that */
      if (highCheck > safest) {
        broken = true;
        firstBreak = highCheck;
      } else {
        safe = highCheck; // move pointer to last checked safe rung
      }
      ++drops;
      /* Record the first few drops */
      if (highCheck > step) {
        if (secondDrop == -1) {
          secondDrop = highCheck;
        } else if (thirdDrop == -1) {
          thirdDrop = highCheck;
        }
      }
    }
    /* Since stepping in big range previously, now 1-by-1 find */
    int i = safe + 1;
    while (broken && i < highCheck) {
      /* if these haven't occurred yet */
      if (secondDrop == -1)
        secondDrop = i;
      else if (thirdDrop == -1)
        thirdDrop = i;

      ++drops;
      if (i > safest) {
        secondBreak = i;
        break;
      } else
        safe = i;

      ++i;
    }

    /* fill out answer array */
    ret.addAll(Arrays.asList(safe, drops, step, secondDrop, thirdDrop, firstBreak, secondBreak));

    return ret;
  }

}
