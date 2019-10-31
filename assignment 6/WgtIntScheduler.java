import java.lang.Math;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;

public class WgtIntScheduler{
  ArrayList<Integer> ret;

  public WgtIntScheduler(){}

  //input parameters are zero indexed
  //output should be the indices + 1
  public static int[] getOptSet(int[] stime, int[]ftime, int[] weight){
    ArrayList<Integer> table = new ArrayList<Integer>();
    ArrayList<Integer> startT = new ArrayList<Integer>(stime.length);
    ArrayList<Integer> finishT = new ArrayList<Integer>(ftime.length);
    ArrayList<Integer> values = new ArrayList<Integer>(weight.length);
    ArrayList<Integer> cp = new ArrayList<Integer>();  //a bunch of arrays, sorry space usage is still O(n)
    int[] arr;

    WgtIntScheduler wt = new WgtIntScheduler();
    startT.add(-1);         //simplest way to keep an arbitrary value at index 0, since we do calculations on indices >= 1
    finishT.add(-1);
    values.add(-1);
    cp.add(-1);
    for(int i = 0; i < stime.length; ++i){  //initialize arrays
      startT.add(stime[i]);
      finishT.add(ftime[i]);
      values.add(weight[i]);
    }
    wt.sortInput(startT, finishT, values);
    wt.computeCP(startT, finishT, cp);   //cp is the array of the index j (j < i) that is the highest compatible job with job i. everything was sorted by finish time

    table.add(0); //indexing at 1, problem size of 0 means 0 value
    for(int x = 1; x < startT.size(); ++x){
      table.add(Math.max(values.get(x) + table.get(cp.get(x)), table.get(x - 1)));
    }
    wt.getAnswer(table, cp, values, startT.size() - 1);
    arr = new int[wt.ret.size()];

    int index = 0;
    for(int i = 0; i < wt.ret.size(); ++i){
      for(int j = 0; j < stime.length; ++j){
          if((stime[j] == startT.get(wt.ret.get(i)) ) && (ftime[j] == finishT.get(wt.ret.get(i)) ) && (weight[j] == values.get(wt.ret.get(i)) )){
            index = j + 1;
            break;
          }
      }
      arr[i] = index;
    }
    Arrays.sort(arr);
    return arr;
  }

  public void getAnswer(ArrayList<Integer> table, ArrayList<Integer> cp, ArrayList<Integer> weight, int n){
    this.ret = new ArrayList<Integer>();
    while(n >= 0){
      if(n == 0)
        return;
      else if(weight.get(n) + table.get(cp.get(n)) > table.get(n - 1)){
        this.ret.add(n);
        n = cp.get(n);
      }
      else
        --n;
    }

  }

  //sorting by finish time
  public void sortInput(ArrayList<Integer> st, ArrayList<Integer> ft, ArrayList<Integer> v){
    int temp, min, index, j = 0;
    while(j < ft.size()){
      min = ft.get(j);
      for(int i = j; i < ft.size(); ++i){
        if(ft.get(i) < min)
          min = ft.get(i);

      }
      index = ft.indexOf(min);
      temp = ft.get(j);
      ft.set(j, min);
      ft.set(index, temp);

      temp = st.get(j);
      st.set(j, st.get(index));
      st.set(index, temp);

      temp = v.get(j);
      v.set(j, v.get(index));
      v.set(index, temp);
      ++j;
    }
  }

  public void computeCP(ArrayList<Integer> st, ArrayList<Integer> ft, ArrayList<Integer> cp){
    int maxIndex = 0;
    cp.add(0); //first job can't be compatible with anything before it
    for(int i = 2; i < st.size(); ++i){
      for(int j = 1; j < i; ++j){      //a job is not compatible with itself, so don't bother checking
        if(ft.get(j) <= st.get(i))
          maxIndex = j;

      }
      cp.add(maxIndex);
      maxIndex = 0;
    }
  }

}
