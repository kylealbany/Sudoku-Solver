import java.util.Arrays;
import java.lang.Math;

public class solver {
	
	
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		
		//unsolved puzzle, need to replace with better input method
		int[][] puzzle={{0,0,2,0,0,0,0,1,0},{8,0,0,0,6,0,4,0,9},{1,0,0,3,0,2,0,0,8},{0,1,0,0,0,3,8,4,0},{6,2,8,5,0,4,9,3,1},{0,4,9,6,0,0,0,7,0},{5,0,0,4,0,1,0,0,3},{2,0,1,0,3,0,0,0,4},{0,9,0,0,0,0,1,0,0}};
		
		solve(puzzle);
		
		final long endTime = System.currentTimeMillis();
		System.out.printf("Total execution time: %.6f seconds",(endTime-startTime)/(double)1000);
	}
	
	
	public static boolean solve(int[][] puzzle){
		
		//check if there are no empty spaces, if there aren't then it is solved
		if(findNext(puzzle)==null){
			System.out.println(Arrays.deepToString(puzzle));
			return true;
		}
		
		//finds coordinates of next empty box
		
		int i=findNext(puzzle)[0];
		int j=findNext(puzzle)[1];
		
		//iterates through possible numbers to put in box
		for(int num=1;num<=9;num++){
			//number isn't already in row, column, or box
			if(!(inRow(puzzle,num,i)||inCol(puzzle,num,j)||inBox(puzzle,num,i,j))){
				puzzle[i][j]=num; //test number
				//using number, try to recursively solve puzzle. If it works, return true
				if(solve(puzzle)){
					return true;
				}
				//number didn't work after recursion, reassign it to 0 (empty)
				puzzle[i][j]=0;
			}
		}
		
		
		
		return false;
	}
	
	
	//finds next box with no number in it, returns null if entire puzzle is full
	public static int[] findNext(int[][] puzzle){
		int[] coords=new int[2];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(puzzle[i][j]==0){
					coords[0]=i;
					coords[1]=j;	
					return coords;
				}
			}
		}
		return null;
	}
	
	
	//checks if the number already occurs in the row
	public static boolean inRow(int[][] puzzle,int num,int row){
		for(int i=0;i<9;i++){
			if(puzzle[row][i]==num){
				return true;
			}
		}
		
		
		
		return false;
	}
	
	//checks if the number already occurs in the column
	public static boolean inCol(int[][] puzzle,int num,int col){
		for(int i=0;i<9;i++){
			if(puzzle[i][col]==num){
				return true;
			}
		}
		
		
		
		return false;
	}
	
	//checks if the number already occurs in its 3x3 box
	public static boolean inBox(int[][] puzzle,int num,int row,int col){
		for(int i=3*(int)Math.floor(row/3);i<3*(int)Math.floor(row/3)+3;i++){
			for(int j=3*(int)Math.floor(col/3);j<3*(int)Math.floor(col/3)+3;j++){
				if(puzzle[i][j]==num){
					return true;
				}
			}
		}
		
		return false;
	}
}
