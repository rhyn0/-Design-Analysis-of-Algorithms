import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Dijkstra  {
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();

   boolean directed;
   int nvertices;
   int nedges;

   public Dijkstra(){
   }

	 public static int[][] findShortPaths(String filename){
		 Dijkstra d = new Dijkstra();
		 ArrayList<HashMap<Integer, Integer>> pq = new ArrayList<HashMap<Integer, Integer>>();
		 int h, parent, pWeight = 0, w = 0, size = 0;

		 try{
			 d.readfile_graph(filename);
		 }
		 catch (FileNotFoundException e){
			 System.err.println("File not found");
			 System.exit(-1);
		 }
		 int[][] ret = new int[d.nvertices][3];
		 for(int i = 0; i < d.nvertices; ++i){
			 ret[i] = new int[]{0,0,0};
		 }
		 for (int i = 0; i < d.nvertices; ++i){
			 pq.add(i, new HashMap<Integer, Integer>(Map.of(i + 1, 2147483647))); //using MAX_INT value for java
		 }
		 pq.set(0, new HashMap<Integer, Integer>(Map.of(1, 0)));

		 for (int j = 0; j < d.nvertices; ++j){ //only needs to run for number of vertices since we 'remove' a vertex on each call
			 h = d.extractMin(pq);
			 parent = (int)(pq.get(h).keySet().toArray()[0]);
			 pWeight = (int)(pq.get(h).values().toArray()[0]);
			 ret[size] = new int[]{h + 1, pWeight, parent};
			 ++size;
			 // make weight negative in the Priority Queue only so it can't be re-read and doesn't mess up indexing
			 pq.set(h, new HashMap<Integer, Integer>(Map.of(h + 1, -1)));



			 for (int i = 0; i < d.vertices.get(h).edges.size(); ++i){
				 w = d.vertices.get(h).weights.get(i);
				 //im sorry that the data structures are super confusing
				 if ((int)(pq.get(d.vertices.get(h).edges.get(i).key - 1).values().toArray()[0]) > pWeight + w){
					 pq.set(d.vertices.get(h).edges.get(i).key - 1, new HashMap<Integer, Integer>(Map.of(h + 1, pWeight + w)));
				 }
			 }

		 }
		 return ret;
	 }

	 public int extractMin(ArrayList<HashMap<Integer, Integer>> pq){
		 int min = 2147483647;
		 int ret = 0;
		 for(int i = 0; i < this.nvertices; ++i){
			// ignore all values below 0, dont constantly add the start node over and over
			 if(((int)(pq.get(i).values().toArray()[0]) < min) && ((int)(pq.get(i).values().toArray()[0]) >= 0)){
			 	ret = i;
				min = (int)(pq.get(i).values().toArray()[0]);
			}
		 }
		 return ret;
	 }

   void readfile_graph(String filename) throws FileNotFoundException  {
      int x,y, w;
        //read the input
      FileInputStream in = new FileInputStream(new File(filename));
      Scanner sc = new Scanner(in);
      int temp = sc.nextInt(); // if 1 directed; 0 undirected
      directed = (temp == 1);
    	nvertices = sc.nextInt();
      for (int i=0; i<=nvertices-1; i++){
         Vertex tempv = new Vertex(i+1);   // kludge to store proper key starting at 1
         vertices.add(tempv);
      }

		nedges = sc.nextInt();   // m is the number of edges in the file
      int nedgesFile = nedges;
		for (int i=1; i<=nedgesFile ;i++)	{
			// System.out.println(i + " compare " + (i<=nedges) + " nedges " + nedges);
         x=sc.nextInt();
			y=sc.nextInt();
			w = sc.nextInt();
         //  System.out.println("x  " + x + "  y:  " + y  + " i " + i);
			insert_edge(x,y,directed, w);
		}

	}

   static void process_vertex_early(Vertex v)	{
		timer++;
		v.entry_time = timer;
		//     System.out.printf("entered vertex %d at time %d\n",v.key, v.entry_time);
	}

	static void process_vertex_late(Vertex v)	{
		timer++;
		v.exit_time = timer;
		//     System.out.printf("exit vertex %d at time %d\n",v.key , v.exit_time);
	}

	static void process_edge(Vertex x,Vertex y) 	{
		int c = edge_classification(x,y);
		if (c == BACK) System.out.printf("back edge (%d,%d)\n",x.key,y.key);
		else if (c == TREE) System.out.printf("tree edge (%d,%d)\n",x.key,y.key);
		else if (c == FORWARD) System.out.printf("forward edge (%d,%d)\n",x.key,y.key);
		else if (c == CROSS) System.out.printf("cross edge (%d,%d)\n",x.key,y.key);
		else System.out.printf("edge (%d,%d)\n not in valid class=%d",x.key,y.key,c);
	}

	static void initialize_search(Dijkstra g)	{
		for(Vertex v : g.vertices)		{
			v.processed = v.discovered = false;
			v.parent = null;
		}
	}

	static final int TREE = 1, BACK = 2, FORWARD = 3, CROSS = 4;
	static int timer = 0;

	static int edge_classification(Vertex x, Vertex y)	{
		if (y.parent == x) return(TREE);
		if (y.discovered && !y.processed) return(BACK);
		if (y.processed && (y.entry_time > x.entry_time)) return(FORWARD);
		if (y.processed && (y.entry_time < x.entry_time)) return(CROSS);
		System.out.printf("Warning: self loop (%d,%d)\n",x,y);
		return -1;
	}

	void insert_edge(int x, int y, boolean directed, int weight) 	{
		Vertex one = vertices.get(x-1);
      Vertex two = vertices.get(y-1);
      one.edges.add(two);
			one.weights.add(weight);
		vertices.get(x-1).degree++;
		if(!directed)
			insert_edge(y,x,true, weight);
		else
			nedges++;
	}
   void remove_edge(Vertex x, Vertex y)  {
		if(x.degree<0)
			System.out.println("Warning: no edge --" + x + ", " + y);
		x.edges.remove(y);
		x.degree--;
	}

	void print_graph()	{
		for(Vertex v : vertices)	{
			System.out.println("vertex: "  + v.key);
			for(int i = 0; i < v.edges.size(); ++i){
				System.out.print("  adjacency list: " + v.edges.get(i).key + " w: " + v.weights.get(i));
			}
			System.out.println();
		}
	}

	public void print_matrix(int[][] mat){
		for(int i = 0; i < mat.length; ++i){
			for(int j = 0; j < mat[i].length; ++j){
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

   class Vertex implements Comparable<Vertex> {
      int key;
      int degree;
      int component;
      int color = -1;    // use mod numColors, -1 means not colored
      boolean discovered = false;
      boolean processed = false;
      int entry_time = 0;
      int exit_time = 0;
      Vertex parent = null;
      ArrayList<Vertex> edges = new ArrayList<Vertex>();
			ArrayList<Integer> weights = new ArrayList<Integer>();

      public Vertex(int tkey){
         key = tkey;
      }

      public int compareTo(Vertex other){
         if (this.key > other.key){
            return 1;
         }         else if (this.key < other.key) {
            return -1;
         }
         else
            return 0;
         }
      }

	Vertex unProcessedV(){
      for(Vertex v:  vertices)  {
         if (! v.processed ) return v;
      }
   return null;
   }
}
