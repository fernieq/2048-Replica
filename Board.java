/*Name: Yiluo Qin
 * cs8bwacr
 * File Header: In this file, we have various methods to enable us to 
 *              check if the tiles can be moved or not. In addition,
 *              we have methods to execute the moves. We also have 
 *              constructors to initiate and save the boards. 
*/


import java.util.*;
import java.io.*;

/* Class Header: in this Board class, we have two constructors to both 
 * 		initiate an empty board or a loaded board from a file.
 * 		We also have saveBoard method to save a board for later 
 * 		use. In addition, we have methods to check move the tiles
 * 		and check if the game is over or not.
 */ 		

public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;
    public final int Zero = 0;
    public final int ONE = 1;
    public final int NEG_ONE = -1;    
    public final int TWO = 2;
    public final int FOUR =4;
    public final int HUNDRED=100;


    private final Random random; // a reference to the Random object, passed in 
                                 // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 



/*Method Header: Constructs a fresh board with random tiles
 * @param random: a reference to the Random object
 * @param boardSize: the size of the board
 */
    public Board(Random random, int boardSize) {
        this.random = random;
        GRID_SIZE=boardSize;
    	this.grid = new int [boardSize][boardSize];
    	this.score = Zero;
    	//add two tiles to the empty board  
    	this.addRandomTile();
    	this.addRandomTile();
    }



/*Method Header: Construct a board based off of an input file assume 
 *               board is valid
 * @param random: a reference to the Random object
 * @param inputBoard: the file which has the saved board
 */
    public Board(Random random, String inputBoard) throws IOException {
        //initiate all instance variables
    	this.random = random; 
    	File sourcefile = new File(inputBoard);
    	Scanner fileout = new Scanner(sourcefile);
    	GRID_SIZE = fileout.nextInt();
    	this.grid = new int [GRID_SIZE][GRID_SIZE];
    	this.score = fileout.nextInt();
    	//construct the board 
    	for(int i =Zero; i<GRID_SIZE;i++){
    		for(int j=Zero;j<GRID_SIZE;j++){
	    		grid[i][j]=fileout.nextInt(); 
			}	
		}
	    fileout.close();
	}



/* Method Header: Saves the current board to a file 
 * @param outputBoard: the name of the file you want to save your board to
 */ 
    public void saveBoard(String outputBoard) throws IOException {
	    PrintWriter fileout = new PrintWriter(outputBoard);
	    fileout.println(GRID_SIZE);
	    fileout.println(this.score);
	    for(int i = Zero; i<GRID_SIZE;i++){
		    for(int j = Zero; j<GRID_SIZE;j++){
			    fileout.print(grid[i][j] + " ");
			}
			fileout.print("\n");
		}	
	    fileout.close();
    }



/* Method Header: Adds a random tile (of value 2 or 4) to a 
 * random empty space on the board
*/
  public void addRandomTile() {
	int count = Zero;
	//counting the open spaces
	for(int i = Zero;i<GRID_SIZE;i++){
    	for(int j = Zero; j<GRID_SIZE;j++){
	    	if(grid[i][j]==Zero){
		    	count=count+ONE;
		    }
	    }
	}
	//if no more open spaces left just return
	if(count==Zero)
		return;
	int location = random.nextInt(count);
	int value = random.nextInt(HUNDRED);
	int open_space = NEG_ONE;
	for(int i = Zero;i<GRID_SIZE;i++){
	    for(int j =Zero;j<GRID_SIZE;j++){
		    if(grid[i][j]==Zero){
			    open_space = open_space +ONE;
			    //if the open_space equals the random number
			    if(open_space ==location){
				    if(value<=TWO_PROBABILITY){
					    grid[i][j]=TWO;
				    }
				    else{
		    	        grid[i][j]=FOUR;
			        }
		            return;
		        }
	        }      
	    }
     }
   }  

/* Method header: determines whether the board can move in a certain 
 * direction return true if such a move is possible
 * @param direction: the specified direction to move the tiles
 * @param boolean: return true if the tiles can be moved in the 
 *                 specified direction
 */
    public boolean canMove(Direction direction){
  	boolean value = true;
	
	boolean left = this.canMoveLeft();
	boolean right = this.canMoveRight();
	boolean up = this.canMoveUp();
	boolean down = this.canMoveDown();
	//check which direction is passed in 
	if(direction == Direction.LEFT){
		if(value==left){
			return true;
		}
		return false;
	     }	
	else if(direction == Direction.RIGHT){
     		if(value==right){
			return true;
		}
		return false;
	    }
	else if(direction == Direction.UP){
		if(value==up){
			return true;
		}
		return false;
	   }
	 else if(direction == Direction.DOWN){
		if(value==down){
			return true;
		}
		return false; 
           }
	//if the tiles can not move in any direction, returns false
    	else{
		return false;
            }
	}  




 /* Method header: this helper method only checks if the tiles
 * 		     can move left
 * @param boolean: return true it the tiles can move left
 **/
    private boolean canMoveLeft() {
	for(int i = Zero; i <GRID_SIZE;i++){
		for(int j = ONE; j<GRID_SIZE;j++){
			if(grid[i][j]!=Zero){
				if(grid[i][j]==grid[i][j-ONE]
		   		  ||grid[i][j-ONE]==Zero){
					return true;
				}
	     		}
	     	}
	}
	return false;
     }	



 /* Method header: this helper method only checks if the tiles
 * 		     can move right
 * @param boolean: return true it the tiles can move right
 **/
    private boolean canMoveRight(){
	for(int i = Zero;i<GRID_SIZE;i++){
		for(int j = Zero;j<GRID_SIZE-ONE;j++){
			if(grid[i][j]!=Zero){
				if(grid[i][j]==grid[i][j+ONE]
			 	 ||grid[i][j+ONE]==Zero){
					return true;
				}
		   	}
	     	}
	}
	return false;
      }



 /* Method header: this helper method only checks if the tiles
 * 		     can move up
 * @param boolean: return true it the tiles can move up
 **/
    private boolean canMoveUp(){
	for(int i = ONE;i<GRID_SIZE;i++){
		for(int j = Zero; j<GRID_SIZE;j++){
			if(grid[i][j]!=Zero){
				if(grid[i][j]==grid[i-ONE][j]
				||grid[i-ONE][j]==Zero){
					return true;
				}
		   	}
	      	}
	}
	return false;
      }		



    
 /* Method header: this helper method only checks if the tiles
 * 		     can move down
 * @param boolean: return true it the tiles can move down
 **/
    private boolean canMoveDown(){
	for(int i = Zero;i<GRID_SIZE-ONE;i++){
		for(int j = Zero;j<GRID_SIZE;j++){
			if(grid[i][j]!=Zero){
				if(grid[i][j]==grid[i+ONE][j]
				 ||grid[i+ONE][j]==Zero){
					return true;
				}
		  	}
	    	}
	}
	return false;
    }




/*Method header: move the board in a certain direction return true 
 * 		if such a move is successful
 * @param boolean: return true if the tiles can be moved in the 
 * 		   certain direction and update the score
 * @param dirction: the passed in direction
 */
    public boolean move(Direction direction) {
	//first check which direction are the tiles are called to move     
	boolean value = true;
	boolean left = this.canMove(Direction.LEFT);
	boolean up = this.canMove(Direction.UP);
	boolean right=this.canMove(Direction.RIGHT);
	boolean down=this.canMove(Direction.DOWN);

	//if the tiles can be moved, we then execute the move according
	//to the direction
	if(direction==Direction.LEFT){ 
		if(value==left){
			return this.moveLeft();
		}
	}

	if(direction==Direction.RIGHT){ 
		if(value==right){
			return this.moveRight();
		}
	}

	if(direction==Direction.DOWN){ 
		if(value==down){
			return this.moveDown();
		}
	}
	if(direction==Direction.UP){
		if(value==up){
			return this.moveUp();
		}
	}
	return false; 
    }
	


/*Method header: this helper method only moves the tiles to left
 * 		 if the tiles can be moved
 * @param boolean: return true if the tiles have been successfully 
 * 		   moved to left
 */
    private boolean moveLeft(){

	boolean value = false;
	if(value==this.canMoveLeft())
		return false;
	//if we can move the tiles, we combine tiles first
	for(int i = Zero; i <GRID_SIZE;i++){
	for(int j=Zero; j<GRID_SIZE-ONE;j++){
		if(grid[i][j]!=Zero){
			//if the next tile is zero, we swap the pair
			if(grid[i][j+ONE]==Zero){
				grid[i][j+ONE]=grid[i][j];
				grid[i][j]=Zero;
			}
			//if the next tile equals the previous, combine
			if(grid[i][j]==grid[i][j+ONE]){
				grid[i][j]+=grid[i][j];
				grid[i][j+ONE]=Zero;
				score = score + grid[i][j];
			}	
		}
	   }
	}

	//after the combinations, now we shift the tiles
	for(int i = Zero;i<GRID_SIZE;i++){
	for(int k = GRID_SIZE-ONE;k>Zero;k--){
		for(int j = GRID_SIZE-ONE;j>Zero;j--){
			if(grid[i][j]!=Zero){
				if(grid[i][j-ONE]==Zero){
					grid[i][j-ONE]=grid[i][j];
					grid[i][j]=Zero;
				}
			}
		}
	    }
	 }
	return true;	
        }	





    
/*Method header: this helper method only moves the tiles to up
 * 		 if the tiles can be moved
 * @param boolean: return true if the tiles have been successfully 
 * 		   moved up
 */
    private boolean moveUp(){
	boolean value = false;
	if(value==this.canMoveUp())
		return false;
	//if we can move the tiles, we combine tiles first
	for(int j=Zero; j<GRID_SIZE;j++){
	for(int i = Zero; i<GRID_SIZE-ONE;i++){	
		if(grid[i][j]!=Zero){
			//if the next tile is zero, we swap the pair
			if(grid[i+ONE][j]==Zero){
				grid[i+ONE][j]=grid[i][j];
				grid[i][j]=Zero;
			}
			//if the next tile equals the previous, combine
			if(grid[i][j]==grid[i+ONE][j]){
				grid[i][j]+=grid[i][j];
				grid[i+ONE][j]=Zero;
				score = score+grid[i][j];
			}	
		}
	    }
	 }

	//after the combinations, now we shift the tiles
	for(int j = Zero;j<GRID_SIZE;j++){
	for(int k = GRID_SIZE-ONE;k>Zero;k--){
		for(int i = GRID_SIZE-ONE;i>Zero;i--){
			if(grid[i][j]!=Zero){
				if(grid[i-ONE][j]==Zero){
					grid[i-ONE][j]=grid[i][j];
					grid[i][j]=Zero;
				}
			}
		}
	    }
	 }
	return true;		
    }	




/*Method header: this helper method only moves the tiles to the right
 * 		 if the tiles can be moved
 * @param boolean: return true if the tiles have been successfully 
 * 		   moved to the right
 */
    private boolean moveRight(){
	boolean value = false;
	if(value==this.canMoveRight())
		return false;
	//if we can move the tiles, we combine tiles first
	for(int i=Zero; i<GRID_SIZE;i++){
	for(int j =GRID_SIZE-ONE; j>Zero;j--){	
		if(grid[i][j]!=Zero){	
			//if the next tile is zero, we swap the pair
			if(grid[i][j-ONE]==Zero){
				grid[i][j-ONE]=grid[i][j];
				grid[i][j]=Zero;
			}
			//if the next tile equals the previous, combine
			if(grid[i][j]==grid[i][j-ONE]){
				grid[i][j]+=grid[i][j];
				grid[i][j-ONE]=Zero;
				score = score + grid[i][j];
			}	
		}
	   }
	 }

	//after the combinations, now we shift the tiles
	for(int i = Zero;i<GRID_SIZE;i++){
	for(int k = GRID_SIZE-ONE;k>Zero;k--){
		for(int j = Zero;j<GRID_SIZE-ONE;j++){
			if(grid[i][j]!=Zero){
				if(grid[i][j+ONE]==Zero){
					grid[i][j+ONE]=grid[i][j];
					grid[i][j]=Zero;
				}
			}
		}
             }
	}
	
	return true;		
	}	



/*Method header: this helper method only moves the tiles to down
 * 		 if the tiles can be moved
 * @param boolean: return true if the tiles have been successfully 
 * 		   moved down
 */
    private boolean moveDown(){
	boolean value = false;
	if(value==this.canMoveDown())
		return false;
	//if we can move the tiles, we combine tiles first
	for(int j=Zero; j<GRID_SIZE;j++){
	for(int i = GRID_SIZE-ONE; i>Zero;i--){	
		if(grid[i][j]!=Zero){
			//if the next tile is zero, we swap the pair
			if(grid[i-ONE][j]==Zero){
				grid[i-ONE][j]=grid[i][j];
				grid[i][j]=Zero;
			}
			//after the combinations, now we shift the tiles
			if(grid[i][j]==grid[i-ONE][j]){
				grid[i][j]+=grid[i][j];
				grid[i-ONE][j]=Zero;
				score = score + grid[i][j];
			}	
		}
	   }
	 }

	//after the combinations, now we shift the tiles
	for(int j = Zero;j<GRID_SIZE;j++){
		for(int k = GRID_SIZE-ONE;k>Zero;k--){
			for(int i = Zero;i<GRID_SIZE-ONE;i++){
				if(grid[i][j]!=Zero){
					if(grid[i+ONE][j]==Zero){
						grid[i+ONE][j]=grid[i][j];
						grid[i][j]=Zero;
					}
				}
			}
		   }
	     }

	return true;		
	}	





/* Method header:  Check to see if we have a game over   
 * @param boolean: return false if we can't move any tiles
*/
  public boolean isGameOver() {
    boolean value = false;
	//check the four can move conditions
    if(value==this.canMove(Direction.UP)
	 &&value==this.canMove(Direction.DOWN)
	 &&value==this.canMove(Direction.LEFT)
	 &&value==this.canMove(Direction.RIGHT)){
        return true;
   	}
	return false;
	}	


 // Return the reference to the 2048 Grid
 public int[][] getGrid() {
        return grid;
 }


 // Return the score
public int getScore() {
        return score;
 }


    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
