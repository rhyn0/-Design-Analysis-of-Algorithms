import java.util.ArrayList;

public class Inversions{

  public static int invCounter(int[] arr){
    int catcher = 0;
    int middle = (arr.length - 1)/2;
    ArrayList<Integer> mine = new ArrayList<Integer>(arr.length);
    for(int i = 0; i < arr.length; ++i){
      mine.add(arr[i]);
    }

    catcher += mSort(mine, 0, middle);
    catcher += mSort(mine, middle + 1, arr.length - 1);
    return catcher + merge(mine, 0, middle, arr.length - 1);
  }

  public static int mSort(ArrayList<Integer> arr, int left, int right){
    int catcher = 0;
    if (left < right){
      catcher += mSort(arr, left, (left + right)/2);
      catcher += mSort(arr, ((left + right)/2) + 1, right);
      return catcher + merge(arr, left, (left + right)/2, right);
    }
    return catcher;
  }

  public static int merge(ArrayList<Integer> arr, int left, int m, int right){
    int ret = 0;
    int size1 = m - left + 1;
    int size2 = right - m;

    int L[] = new int[size1];
    int R[] = new int[size2];

    for(int i = 0; i < size1; ++i)
      L[i] = arr.get(left + i);
    for (int j = 0; j < size2; ++j)
      R[j] = arr.get(m + 1 + j);
    int i = 0, j = 0;
    int k = left;
    while (i < size1 && j < size2){
      if (L[i] < R[j]){
        arr.set(k, L[i]);
        ++i;
      }
      else{
        arr.set(k, R[j]);
        ++j;
        ret += (size1 - i);
      }
      ++k;
    }
    while (i < size1){
      arr.set(k, L[i]);
      ++i;
      ++k;
    }
    while (j < size2){
      arr.set(k, R[j]);
      ++j;
      ++k;
    }
    return ret;
  }
}
