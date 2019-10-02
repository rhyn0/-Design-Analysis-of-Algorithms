

public class MyHeap{
  private int capacity;
  private int size;
  private int[] heap;

  public MyHeap(){
      this.capacity = 50;
      this.heap = new int[50 + 1];
      this.size = 0;
  }

  public MyHeap(int capa){
    this.capacity = capa;
    this.size = 0;
    this.heap = new int[capa + 1];
  }

  public boolean buildHeap(int[] values){
    if(values.length > this.capacity){
      return false;
    }
    for (int i = 0; i < this.capacity; ++i){
      this.heap[i + 1] = values[i];
      ++(this.size);
    }
    for (int j = this.size / 2; j >= 1; --j){
      if(this.heap[j] > this.heap[2*j] || this.heap[j] > this.heap[2*j + 1]){
        driftDown(j);
      }
    }
    return true;
  }

  public boolean insert(int key){
    if (this.size == this.capacity){
      return false;
    }
    ++(this.size);
    this.heap[this.size] = key;
    driftUp(this.size);
    return true;
  }

  public int findMin(){
    return this.heap[1];
  }

  public int deleteMin(){
    int temp = this.heap[1];
    this.heap[1] = this.heap[this.size];
    --(this.size);
    driftDown(1);
    return temp;
  }

  public boolean isEmpty(){
    return (this.size == 0);
  }

  public boolean isFull(){
    return (this.size == this.capacity);
  }

  public void driftDown(int index){
    int temp = this.heap[index];
    int child;
    while (index * 2 <= this.size){
      child = index * 2;
      if (child != this.size && (this.heap[child + 1] < this.heap[child])){
        ++child;
      }
      if (this.heap[child] < temp){
        this.heap[index] = this.heap[child];
        index = child;
      }
      else {
        break;
      }
    }
    this.heap[index] = temp;
  }

  public void driftUp(int index){
    int temp = this.heap[index];
    while (index > 1 && temp < this.heap[index / 2]){
      this.heap[index] = this.heap[index / 2];
      index = index / 2;
    }
    this.heap[index] = temp;
  }

  public int getHeapCap(){
    return this.capacity;
  }

  public int getHeapSize(){
    return this.size;
  }

  public static int[] heapSortDecreasing(int[] values){
    int[] temp;
    this.capacity = values.length;
    this.heap = new int[values.length + 1];
    temp = new int[values.length];
    buildHeap(values);
    while(this.size > 1){
      temp[this.size - 1] = deleteMin();
    }
    temp[0] = this.heap[1];
    return temp;
  }
}
