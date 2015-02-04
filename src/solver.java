import java.util.Arrays;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**To do:
 * Fix solution border display
 * Add button to solve instead of waiting
 * Add reset button to clear inputs
 * Change fonts so input/solution text looks nicer
 * Clean up code
 */

public class solver {
	
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		
		createGUI();
        
		//now update GUI with new puzzle, wait until close or reset button is hit
		//fix GUI code 
		
		final long endTime = System.currentTimeMillis();
		System.out.printf("Total execution time: %.6f seconds",(endTime-startTime)/(double)1000);
	}
	
	
	public static void createGUI(){
		JFrame frame = new JFrame("Sudoku Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JTextField f[][]= new JTextField[9][9];
	    JPanel boxes[][]= new JPanel [3][3];
	    JPanel buttons=new JPanel();
		
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                f[x][y]=new JTextField(1);
            }
        }
		
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                boxes[x][y]=new JPanel(new GridLayout(3,3));
            }
        }
        
        frame.setLayout(new GridLayout(3,3,5,5));

        for(int u=0; u<3; u++){
            for(int i=0; i<3; i++){    
                for(int x=0; x<3; x++ ){
                    for(int y=0; y<3; y++){
                        boxes[u][i].add(f[x+u*3][y+i*3]);
                    }
                }
            
            frame.add(boxes[u][i]);
            }
        }

        JButton solve = new JButton("Solve puzzle");
        solve.setVerticalTextPosition(AbstractButton.CENTER);
        solve.setHorizontalTextPosition(AbstractButton.LEADING);
        solve.setMnemonic(KeyEvent.VK_ENTER);
        buttons.add(solve);
        //frame.add(buttons);

        frame.setSize(500,500);
        frame.setVisible(true);
        
        ////////
        ///////remove and replace with wait for button
        try {
            //thread to sleep for the specified number of milliseconds
            Thread.sleep(7000);
        } catch ( java.lang.InterruptedException ie) {
            System.out.println(ie);
        }
        //////
        System.out.println("sleep over");
        
        
        frame.dispose();
        readInput(f);
	}
	
	
	public static void readInput(JTextField[][] inputs){
		
        int[][] puzzle=new int[9][9];
        
        for(int i=0;i<9;i++){
        	for(int j=0;j<9;j++){
        		if(!(inputs[i][j].getText().equals(""))){
        			puzzle[i][j]=Integer.parseInt(inputs[i][j].getText());
        		}
        	}
        }
        
        System.out.println(Arrays.deepToString(puzzle));//remove after added gui updating
        
        solve(puzzle);
		
	}
	
	public static void showSol(int[][] sol){
		
		JFrame frame = new JFrame("Sudoku Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel boxes[][]= new JPanel [3][3];
		
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                boxes[x][y]=new JPanel(new GridLayout(3,3));
            }
        }
        
        frame.setLayout(new GridLayout(3,3,5,5));
		
        JLabel[][] numbers = new JLabel [9][9];
        for(int i=0;i<9;i++){
        	for(int j=0;j<9;j++){
        		numbers[i][j]=new JLabel(Integer.toString(sol[i][j]));
        	}
        }
        
        
        
        for(int u=0; u<3; u++){
            for(int i=0; i<3; i++){    
                for(int x=0; x<3; x++ ){
                    for(int y=0; y<3; y++){
                        boxes[u][i].add(numbers[x+u*3][y+i*3]);
                    }
                }
            
            frame.add(boxes[u][i]);
            }
        }

        frame.setSize(500,500);
        frame.setVisible(true);
        
	}
	
	public static boolean solve(int[][] puzzle){
		
		//check if there are no empty spaces, if there aren't then it is solved
		if(findNext(puzzle)==null){
			//System.out.println(Arrays.deepToString(puzzle));  //change to update GUI
			showSol(puzzle);
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
