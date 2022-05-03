package prj;
import java.util.*;
import java.awt.*;
import javax.swing.*; 
import java. util. concurrent. TimeUnit;
class MAIN
{
    static Solution sol = new Solution();
    static int s =0;
    static  int[][] grid; 
    public static void main(String[] args) throws InterruptedException
    {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("\t\t PLOT SELECTION WITH SHORTEST DISTANCE\n\n");
    	TimeUnit.SECONDS.sleep(3);
    	System.out.println("You want to build a house on an empty land which reaches all buildings in the shortest amount of distance.\nIn this you can only move UP,DOWN,LEFT,RIGHT.You can use 2D grid values of 0,1 or 2\n");
    	 TimeUnit.SECONDS.sleep(3);
    	 System.out.println("enter 0 for empty plot which you can pass by freely \n ");
    	 System.out.println("enter 1 for building which you cannot pass through \n ");
    	 System.out.println("enter 2 for obstacle which you cannot pass through \n ");
    	 TimeUnit.SECONDS.sleep(3);
    	try
    	{
        
        System.out.println("enter the size");
        s=sc.nextInt();
    	}
    	catch(InputMismatchException e)
    	{
    	System.out.println("invalid input");
         System.exit(s);
    	}
        grid = new int[s][s];
        int i,j;
        System.out.println("enter the grid");
        for(i=0;i<s;i++)
        {
            for(j=0;j<s;j++)
            {
                grid[i][j]=sc.nextInt();
            }
        }

        int ans = sol.shortestDistance(grid);
        System.out.println("the shortest distance from all buildings: "+ans);
        if(ans==-1)
        {
        	System.out.println("No shortest path exists");
        }
        new MyGridLayout();

    }
}
class Solution
{
    private final int[] rowDir = {1, -1, 0, 0};
    private  final int[] colDir = {0, 0, 1, -1};
    int grid[][];
    int size = MAIN.s; 
    public int shortestDistance(int[][] grid)
    {
        for(int i=0; i<size;i++)
        {
            for(int j=0; j<size; j++)
            {
                grid[i][j] = MAIN.grid[i][j];
            }
        }
        if (grid == null || grid.length == 0) 
        return -1;
        int rows = grid.length, cols = grid[0].length;
        int[][] Reach = new int[rows][cols];
        int[][] dist = new int[rows][cols];
        int totalBuildings = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++) 
            {
                if (grid[i][j] == 1) 
                {
                    bfs(grid, i, j, Reach, dist);
                    totalBuildings++;
                }
            }
        }

        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                if (Reach[i][j] == totalBuildings &&
                dist[i][j] < minDist) 
                {
                    minDist = dist[i][j];
                }
            }
        }

        return minDist == Integer.MAX_VALUE ?-1 : minDist;
    }

    private void bfs(int[][] grid, int row, int col, int[][] reach, int[][] dist) 
    {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> q = new LinkedList<>(); 
        boolean[][] visited = new boolean[rows][cols];
        q.offer(new int[]{row, col});
        visited[row][col] = true;

        int d = 0;
        while (!q.isEmpty())
        {
            int qsize = q.size();
            d++;
            for (int i = 0; i < qsize; i++)
            {
                int[] cur = q.poll();
                for (int k = 0; k < 4; k++)
                {
                    int rr = cur[0] + rowDir[k];
                    int cc = cur[1] + colDir[k];

                    if (!isValid(grid, rr, cc, visited)) continue;
                    q.offer(new int[]{rr,cc});
                    visited[rr][cc] = true; 
                    reach[rr][cc]++;         
                    dist[rr][cc] += d;  
                }
            }
        }
    }  

    private boolean isValid(int[][] grid, int rr, int cc, boolean[][] visited)
    {
        if (rr > grid.length - 1 ||
        rr < 0 || cc < 0 || cc > grid[0].length - 1) return false;
        if (visited[rr][cc]) return false;
        if(grid[rr][cc] != 0 ) return false;

        return true;
    }
}

class MyGridLayout
{  
    JFrame f;
    MyGridLayout()
    {  
        int size = MAIN.s;
        
        int[][] grid = new int[size][size];
        for(int i=0; i<size;i++)
        {
            for(int j=0; j<size; j++)
            {
                grid[i][j] = MAIN.grid[i][j];
            }
        }

        f=new JFrame();
        JButton b[] = new JButton[size*size];
        int k = 0;
        for(int i=0;i<size;i++)
        {
            for(int j =0; j<size; j++)
            {
                b[k] = new JButton(String.valueOf(grid[i][j]));
                f.add(b[k]);
                k++;
            }
        }

        f.setLayout(new GridLayout(size,size));  

        f.setSize(300,300);  
        f.setVisible(true);  

    }  
}