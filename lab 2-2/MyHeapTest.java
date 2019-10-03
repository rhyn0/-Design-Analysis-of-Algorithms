
public class MyHeapTest{

  public static void main(String[] args){

    MyHeap test1 = new MyHeap();
    if (test1.getHeapCap() != 50){System.out.println("default constr capacity wrong");}
    if (test1.getHeapSize() != 0){System.out.println("default constr size wrong");}

    MyHeap test2 = new MyHeap(20);
    if (test2.getHeapCap() != 20){System.out.println("constr capacity wrong");}
    if (test2.getHeapSize() != 0){System.out.println("constr size wrong");}
    int[] arr = {1,2,3,4,5,6,7,8,9,10};
    if(!test1.buildHeap(arr)){System.out.println("Build error");}
    if(!test1.insert(-1)){System.out.println("insert error");}

    if(test1.findMin() != -1){System.out.println("minimum error");}
    if(test1.deleteMin() != -1){System.out.println("delete error");}
    if(test1.findMin() != 1){System.out.println("delete error");}

    if(test1.isEmpty()){System.out.println("empty error");}
    if(test1.isFull()){System.out.println("full error");}
  }
}
