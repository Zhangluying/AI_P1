package informedSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

//implement A* with Manhattan distance
//f(n) = g(n) + h(n)
//H(n) = D * (abs ( n.x – goal.x ) + abs ( n.y – goal.y ))
class Vertice{
	int name;
	int fLen;
	int gLen;
	//all nodes from root node to itself
	Set<Integer> parents;
	public Vertice(int name, int fLen, int gLen, Set<Integer> parents) {
		this.name=name;
		this.gLen=gLen;
		this.fLen=fLen;
		this.parents=parents;		
	}
}


public class Astar {	
	//set 99999999 as a infinity number
	public static final int MAX_VALUE = 99999999;
	public static final int D = 10;
	public static int astar(int start, int end, int[][] vertices, int[][] edges) {
		int res=0;
		//define a priorityqueue which can sort vertices in ascending order
		PriorityQueue<Vertice> queue = new PriorityQueue<Vertice>(new Comparator<Vertice>() {

			@Override
			public int compare(Vertice v1, Vertice v2) {
				return v1.fLen - v2.fLen;
			}
			
		});
		
		//add the root node to priorityqueue
		Set<Integer> parent= new HashSet<Integer>();
		parent.add(start);
		Vertice v = new Vertice(start,forhLen(start,end,vertices)+0,0,parent);
		queue.add(v);

		//only stop when we dequeue a goal
		while(queue.poll().name!=end) {
			for(int i=0;i<vertices.length;i++) {
				//find all connecting nodes which is not repeated
				if(edges[start][i]!=0 && edges[start][i]!=MAX_VALUE && !parent.contains(i)) {
					int dis = edges[start][i];
					int hLen =forhLen(i,end,vertices);
					int gLen = dis + v.gLen;
					Set<Integer> parents= new HashSet<Integer>();
					parents.addAll(parent);
					parents.add(i);
					Vertice ve = new Vertice(i,gLen+hLen,gLen,parents);
					queue.add(ve);
				}
			}
			v = queue.peek();
			start = v.name;
			res = v.gLen;
			parent = v.parents;
		}
		
		return res ;
	}
	
	//hLen for each vertice to the end node.
	public static int forhLen(int start,int end,int[][] vertices) {
		return D * (Math.abs (vertices[start][0]-vertices[end][0] ) + Math.abs ( vertices[start][1]-vertices[end][1]));
	}
	
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int[][] vertices = new int[100][2];
		File file1 = new File("E://java/workspace/Algo/src/informedSearch/vertices.txt");
		FileInputStream inputStream1 = new FileInputStream(file1);
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
		String str1 = null;
		int count=0;
		while((str1 = bufferedReader1.readLine()) != null)
		{
			//read SquareX, SquareY from file
			int i=Integer.parseInt(str1.split(",")[1]);
			int j=Integer.parseInt(str1.split(",")[2]);
			vertices[count][0]=i;
			vertices[count][1]=j;
			count++;
		}
		
		int[][] edges = new int[100][100];	
		for(int m=0;m<edges.length;m++) {
			for(int n=0;n<edges.length;n++) {
				if(m!=n) edges[m][n]=MAX_VALUE;
				else edges[m][n]=0;
			}		
		}
		
		File file2 = new File("E://java/workspace/Algo/src/informedSearch/edges.txt");
		FileInputStream inputStream2 = new FileInputStream(file2);
		BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
		String str2 = null;
		while((str2 = bufferedReader2.readLine()) != null)
		{
			//read fromIndex, toIndex, edgeCost from file
			int i=Integer.parseInt(str2.split(",")[0]);
			int j=Integer.parseInt(str2.split(",")[1]);
			int dis=Integer.parseInt(str2.split(",")[2]);
			edges[i][j]=dis;
			edges[j][i]=dis;
		}
		
		Scanner in = new Scanner(System.in);
		System.out.println("Start Point: ");
		int start = in.nextInt();
		System.out.println("End Point: ");
		int end = in.nextInt();
		
		int res = astar(start, end, vertices, edges);
		System.out.println("Shortest path is: " + res);
		
		inputStream1.close();
		bufferedReader1.close();
		inputStream2.close();
		bufferedReader2.close();
	}
}

