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

class TopSorter  {
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
   boolean directed;
   int nvertices;
   int nedges;
   int numComp;

   public TopSorter(){
   }

	 public ArrayList<Integer> topSortGenerator(String filename){
		 int[] inDegree;
		 ArrayList<Integer> temp, ret;
		 boolean dag = true;
		 try{
			 readfile_graph(filename);
		 }
		 catch (FileNotFoundException e){
			 System.err.println("File not found");
		 }
		 ret = new ArrayList<>(vertices.size());
		 inDegree = new int[vertices.size() + 1];
		 inDegree[0] = -1;
		 for (int i = 0; i < vertices.size(); ++i){
			 Vertex v = vertices.get(i);
			 for(int j = 0; j < v.degree; ++j){
			 	inDegree[v.edges.get(j).key] += 1;
		 	 }
		 }
		 temp = new ArrayList<Integer>(Arrays.asList(array));
		 while(dag){
			 for (int k = 1; k < temp.size(); ++k){
				 if(!temp.contains(0)){
					 dag = false;
					 break;
				 }
				 if(temp.get(k) == 0){
					 ret.add(k);
					 temp = reduceDegree(temp, k);
					 break;
				 }
			 }
		 }
		 for(int l = ret.size(); l < vertices.size(); ++l){
			 ret.add(-1);
		 }
		 return ret;
	 }

	 public ArrayList<Integer> reduceDegree(ArrayList<Integer> given, int index){
		 Vertex u = vertices.get(k);
		 for (int i = 0; i < u.edges.size(); ++i){
			 given.get(u.edges.get(i)) -= 1;
		 }
		 return given;
	 }

	 public ArrayList<HashSet<Integer>> connectCheck(){
		 ArrayList<HashSet<Integer>> ret = new ArrayList<>();
		 for(Vertex v : vertices){
				if (!v.discovered)
					ret.add(bfs(v));
			}
			List<Integer> size = Arrays.asList(ret.size());
			ret.add(0, new HashSet<>(size));
			return ret;
	 }

	 public HashSet<Integer> bfs(Vertex v){
		 v.discovered = true;
		 HashSet<Integer> component = new HashSet<Integer>();
		 ArrayList<Vertex> queue = new ArrayList<Vertex>();
		 queue.add(v);
		 component.add(v.key);
		 while (queue.size() != 0){
			 for(Vertex u : queue.get(0).edges){
				 if (!u.discovered){
				 	queue.add(u);
					component.add(u.key);
					u.discovered = true;
				 }
			 }
			 queue.remove(0);
		 }
		 return component;
	 }

	 public boolean bipartiteCheck(){
		ArrayList<Vertex> queue = new ArrayList<>();

		for(Vertex u : vertices){
			if(u.color == -1){
		 		queue.add(u);
				u.color = 1;
		 		while(queue.size() != 0){
			 		for(Vertex v : queue.get(0).edges){
				 		if (v.color == -1){
					 		v.color = 1 - queue.get(0).color;
					 		queue.add(v);
				 		}
				 		else if(v.color == queue.get(0).color){
					 		return false;
				 		}
			 		}
					queue.remove(0);
		 		}
			}
		}
		return true;
	}

   void readfile_graph(String filename) throws FileNotFoundException  {
      int x,y;
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
         //  System.out.println("x  " + x + "  y:  " + y  + " i " + i);
			insert_edge(x,y,directed);
		}
		   // order edges to make it easier to see what is going on
		for(int i=0;i<=nvertices-1;i++)	{
			Collections.sort(vertices.get(i).edges);
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

	static void initialize_search(TopSorter g)	{
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

	void insert_edge(int x, int y, boolean directed) 	{
		Vertex one = vertices.get(x-1);
      Vertex two = vertices.get(y-1);
      one.edges.add(two);
		vertices.get(x-1).degree++;
		if(!directed)
			insert_edge(y,x,true);
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
			for(Vertex w : v.edges)
				System.out.print("  adjacency list: " + w.key);
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
