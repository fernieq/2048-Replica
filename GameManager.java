/*Name: Yiluo Qin
 * Date:2/2/18
 * File header: This is the file where play the game is being executed, this is where is the board 
		is being called. We also allow the user to quit the game.
 */

import java.util.*;
import java.io.*;


/*Class header: in GameManager class, we have two constrctors to initialize
 * 		an empty board or a loaded board from a file.
 * 		Then, we have the play method to read user's input and play.
 */


public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputBoard; // File to save the board to when exiting
      

    /*ec*/
    private String outputRecord; // file to save the record file, format: [size] wasdwasdwasdawsd
    StringBuilder history = new StringBuilder(); // a string of commands history
    /*ce*/




/*Method header: initialize all instance variables for the GameManager class.  
 * 		This constructor will create a new board with a grid size corresponding 
 * 		to the value passed in the parameter boardSize.
 * @param outputBoard: file to save the board to
 * @param boardSize: the boardsize of the grid
 * @param random: the referenc to the Random class
 */
     public GameManager(String outputBoard, int boardSize, Random random) {
	this.board = new Board(random,boardSize); 	
	this.outputBoard=outputBoard;
	}




/*Method header: This constructor will load a board using the filename passed in via the inputBoard 
 * 		 parameter. It will initialize all instance variables for the GameManager class.
 * @param inputBoard: the name of the file passed in
 * @param outputBoard: file to save the board to 
 * @param random: the reference to the Random class
 */
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
	this.board = new Board(random,inputBoard);
	this.outputBoard = outputBoard;
	}

    


    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      w - Move up
    //      s - Move Down
    //      a - Move Left
    //      d - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputBoard
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal



/*Method header:this method allows user to play the game using the correct keys 
		and to quit the game  
 */
    public void play() throws Exception {
	this.printControls();   	
 	System.out.println(board.toString());	
	//first, we want to check the game is not over
	boolean value = false;
	while(board.isGameOver()==value){
	//use a scanner to read the user's input
	Scanner scanning = new Scanner(System.in);
	String input = new String(scanning.next());
	//now we update the board after each movement
		if(input.equals("w")){
			boolean value1=true;
			//adding one more tile if the tiles move
			if(value1==board.move(Direction.UP)){
				board.addRandomTile();
				System.out.println(board.toString());
			}
			else if(value1!=board.move(Direction.UP)){
				System.out.println(board.toString());
			}
		}

		else if(input.equals("a")){
			boolean value2=true;
			//adding one more tile if the tiles move
			if(value2==board.move(Direction.LEFT)){
				board.addRandomTile();
				System.out.println(board.toString());	
			}
			else if(value2!=board.move(Direction.LEFT)){
				System.out.println(board.toString());
			}
		}

		else if(input.equals("s")){
			boolean value3=true;;
			//adding one more tile if the tiles move
			if(value3==board.move(Direction.DOWN)){
				board.addRandomTile();
				System.out.println(board.toString());
			}
			else if(value3!=board.move(Direction.DOWN)){
				System.out.println(board.toString());
			}
		}

		else if(input.equals("d")){
			boolean value4=true;
			//adding one more tile if the tiles move
			if(value4==board.move(Direction.RIGHT)){
				board.addRandomTile();
				System.out.println(board.toString());
			}
			else if(value4!=board.move(Direction.RIGHT)){
				System.out.println(board.toString());
			}
		}	

		//if the user decides to quit the game, we execute the following elif
		else if(input.equals("q")){	
			board.saveBoard(this.outputBoard);
			return;
         	}
		
		//remind the users to put valid inputs
		else{
			this.printControls();
		}
       	}	
	//if the game is over, we save the board and quit the game
	board.saveBoard(this.outputBoard);
	System.out.println("Game Over!");
	return;
	}



    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
