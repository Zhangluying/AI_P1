package dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//use APSP to solve the shortest path problem
public class APSP {
	//set 99999999 as a infinity number
	public static final int MAX_VALUE = 99999999;
	public static int[][] apsp(int[][] nums) {
		int n = nums.length;
		int[][][] dp = new int[n+1][n][n];
		int[][] res= new int[n][n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) { 
				//it dose not use any node from i to j
				dp[0][i][j] = nums[i][j];
				res[i][j]=MAX_VALUE;
				//distance to itself is considered to be 0
				if(i==j) res[i][j]=0;
			}
		}
		
		/**
		 * the shortest path from node i to node j using nodes{0...k-1} as
		   intermediate nodes either uses the node k-1 as an intermediate 
		   node or not. If it dose not use the node k-1, then dp[k][i][j]
		   must be the same as dp[k-1][i][j]. In the case it dose use the node 
		   k-1, then dp[k][i][j] can be written as the sum of paths from i to k-1 
		   and from k-1 to j, and each of those paths would only use the set of 
		   nodes {0...k-2} as the intermediate nodes.
		 */
		for(int k=1;k<=n;k++) {
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					if(i==j) continue;
					dp[k][i][j] = Math.min(dp[k-1][i][j], dp[k-1][i][k-1]+dp[k-1][k-1][j]);
					res[i][j] = Math.min(res[i][j], dp[k][i][j]);
				}	
			}
		}		
		return res;
	}

	public static void main(String[] args) throws IOException {
		File file = new File("E://java/workspace/Algo/src/dp/edges.txt");
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		//there are 100 vertices
		int[][] nums = new int[100][100];
		for(int m=0;m<nums.length;m++) {
			for(int n=0;n<nums.length;n++) {
				if(m!=n) nums[m][n]=MAX_VALUE;
			}
			
		}
		
		String str = null;
		while((str = bufferedReader.readLine()) != null)
		{
			//read fromIndex, toIndex, edgeCost from file
			int i=Integer.parseInt(str.split(",")[0]);
			int j=Integer.parseInt(str.split(",")[1]);
			int dis=Integer.parseInt(str.split(",")[2]);
			nums[i][j]=dis;
			nums[j][i]=dis;
		}
		
		
		int[][] res=apsp(nums);
		//input the start point, end point and get the result
		Scanner in = new Scanner(System.in);
		System.out.println("Start Point: ");
		int start = in.nextInt();
		System.out.println("End Point: ");
		int end = in.nextInt();
		System.out.println("Shortest path is: " + res[start][end]);
	
		inputStream.close();
		bufferedReader.close();

	}

}
