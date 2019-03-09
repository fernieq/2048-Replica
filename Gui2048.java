     /**Name: Yiluo Qin
   cs8bwacr
   File header: We have a Graphic User Interface class which allows the users
                to play the game 2048 on the screen.The GUI we creat here is    
                the front end of the game, while Board.java is the backend of
                the game.
**/

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;


/**Class header:We have a main method inside Gui2048 class to launch the interface,
                then we create a grid pane which contains the score, the name of 
                the game, and the tiles of the game.Every time the user presses
                the specific keys, we implement eventhandler class to link the 
                grid with the keys.Additionally, we have a update method to keep
                keep the grids updated after a key is being pressed.In order to
                allow the tiles being updated, we have Rectangle and Text 2D 
                arrays member variables to keep track of the colors of the tiles,
                the font, size, and the number of the texts on each tile.In the 
                start method, we set the organizer, GridPane.Then, we set the 
                scene and add the GridPane onto it.Finally, we set the stage 
                with the scene and show the stage.
**/

public class Gui2048 extends Application
{
    
    /**The main method which calls launch
    **/
    public static void main(String[] args){
        launch(args);
    }
    
    //declare instance variables
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board
    private GridPane pane;
    private GridPane new_pane;
    private StackPane rootPane;
    private Rectangle[][] tile;
    private Text[][] text;
    private int score = 0;
    private Label current_score = new Label("Score: " + score);
    private Scene scene;
    private boolean value = false;

/**Method header: this start metods overrides the start method.It sets the 
                  organizer with its nodes, the scene and the stage.
    @param primaryStage: the name of the stage
**/     
    @Override
    public void start(Stage primaryStage)
    {
        
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
     
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);
        
        //add the name 2048 to the pane
        Text game_2048 = new Text("2048");
        game_2048.setFont(Font.font("Courier", FontWeight.BOLD, 
                         FontPosture.ITALIC, 60));
        game_2048.setFill(Color.BLACK);
        pane.add(game_2048, 0, 0, 2, 1);
        GridPane.setHalignment(game_2048, HPos.CENTER); 
     
        //add the score to the pane
        current_score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35)); 
        current_score.setTextFill(Color.BLACK);
        pane.add(current_score, 2, 0, 2, 1);
        GridPane.setHalignment(current_score, HPos.CENTER);

        int boardsize = board.getGrid().length;
        //create the rectangle references
        tile = new Rectangle[boardsize][];
        for(int i = 0; i < boardsize; i ++){
            tile[i] = new Rectangle[boardsize];
        }

        //create the text references
        text = new Text[boardsize][];
        for(int i = 0; i < boardsize; i ++){
            text[i] = new Text[boardsize];
        }
    
        //initialize the original rectangles to the board
        int[][] grid = board.getGrid();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){
                //set tiles and texts if the number on the grid is 0
                if(grid[i][j] == 0){
                    tile[i][j] = new Rectangle(Constants2048.TILE_WIDTH,
                                               Constants2048.TILE_WIDTH);
                    tile[i][j].setFill(Constants2048.COLOR_EMPTY);
                    text[i][j] = new Text("");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    //add the Rectangle object and Text object to the pane
                    pane.add(tile[i][j], j, i+1, 1, 1);
                    pane.add(text[i][j], j, i+1, 1, 1);
                }
                //set tiles and texts if the number on the grid is 2
                if(grid[i][j] == 2){
                    tile[i][j] = new Rectangle(Constants2048.TILE_WIDTH,
                                               Constants2048.TILE_WIDTH);
                    tile[i][j].setFill(Constants2048.COLOR_2);
                    text[i][j] = new Text("2");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    //add the Rectangle object and Text object to the pane
                    pane.add(tile[i][j], j, i+1, 1, 1);
                    pane.add(text[i][j], j, i+1, 1, 1);
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //set tiles and texts if the number on the grid is 4
                if(grid[i][j] == 4){
                    tile[i][j] = new Rectangle(Constants2048.TILE_WIDTH,
                                               Constants2048.TILE_WIDTH);
                    tile[i][j].setFill(Constants2048.COLOR_2);
                    text[i][j] = new Text("4");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    //add the Rectangle object and Text object to the pane
                    pane.add(tile[i][j], j, i+1, 1, 1);
                    pane.add(text[i][j], j, i+1, 1, 1);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
            }
        }

        //the second GridPane object only for GameOver method
        //it is empty unless GameOver is reached
        new_pane = new GridPane();
        //create the StackPane to stack the two GridPane objects over each other     
        rootPane = new StackPane(); 
        //setting the scene with the root StackPne to the stage
        rootPane.getChildren().addAll(pane,new_pane);
        //link the keyhandler to the scene
        scene = new Scene(rootPane);
        //root StackPane gets the two GridPanes
        scene.setOnKeyPressed(new myKeyHandler());
        //setting the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.show();
}


/**Method header: this method updates the board tiles with each tile's number
                  after each key is pressed.The update checks each tile's 
                  number from the backend 2D grid and updates tile color,
                  tile number and etc.
**/
    public void updateGUI(){
        //nested for loop check the updated 2D grid from backend
        int[][] new_grid = board.getGrid();
        for(int i = 0; i < new_grid.length; i ++){
            for(int j = 0; j < new_grid.length; j ++){
                //update the tile if the number on the grid is 0
                if(new_grid[i][j] == 0){
                    tile[i][j].setFill(Constants2048.COLOR_EMPTY);
                    text[i][j].setText("");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 2
                else if(new_grid[i][j] == 2){
                    tile[i][j].setFill(Constants2048.COLOR_2);
                    text[i][j].setText("2");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }                    
                //update the tile if the number on the grid is 4
                else if(new_grid[i][j] == 4){
                    tile[i][j].setFill(Constants2048.COLOR_4);
                    text[i][j].setText("4");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_DARK);
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 8
                else if(new_grid[i][j] == 8){
                    tile[i][j].setFill(Constants2048.COLOR_8);          
                    text[i][j].setText("8");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 16
                else if(new_grid[i][j] == 16){
                    tile[i][j].setFill(Constants2048.COLOR_16);                
                    text[i][j].setText("16");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 32
                else if(new_grid[i][j] == 32){
                    tile[i][j].setFill(Constants2048.COLOR_32);
                    text[i][j].setText("32");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 64
                else if(new_grid[i][j] == 64){
                    tile[i][j].setFill(Constants2048.COLOR_64);
                    text[i][j].setText("64");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_LOW));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 128
                else if(new_grid[i][j] == 128){
                    tile[i][j].setFill(Constants2048.COLOR_128);
                    text[i][j].setText("128");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_MID));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 256
                else if(new_grid[i][j] == 256){
                    tile[i][j].setFill(Constants2048.COLOR_256);
                    text[i][j].setText("256");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_MID));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 512
                else if(new_grid[i][j] == 512){
                    tile[i][j].setFill(Constants2048.COLOR_512);
                    text[i][j].setText("512");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_MID));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 1024
                else if(new_grid[i][j] == 1024){
                    tile[i][j].setFill(Constants2048.COLOR_1024);
                    text[i][j].setText("1024");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 2048
                else if(new_grid[i][j] == 2048){
                    tile[i][j].setFill(Constants2048.COLOR_2048);
                    text[i][j].setText("2048");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 4096
                else if(new_grid[i][j] == 4096){
                    tile[i][j].setFill(Constants2048.COLOR_OTHER);
                    text[i][j].setText("4096");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 8192
                else if(new_grid[i][j] == 8192){
                    tile[i][j].setFill(Constants2048.COLOR_OTHER);
                    text[i][j].setText("8192");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 16384
                else if(new_grid[i][j] == 16384){
                    tile[i][j].setFill(Constants2048.COLOR_OTHER);
                    text[i][j].setText("16384");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is 32768
                else if(new_grid[i][j] == 32768){
                    tile[i][j].setFill(Constants2048.COLOR_OTHER);
                    text[i][j].setText("32768");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
                //update the tile if the number on the grid is even larger
                else{
                    tile[i][j].setFill(Constants2048.COLOR_OTHER);
                    text[i][j].setText("TOO BIG");
                    text[i][j].setFont(Font.font("Courier", FontWeight.BOLD,
                                        Constants2048.TEXT_SIZE_HIGH));
                    text[i][j].setFill(Constants2048.COLOR_VALUE_LIGHT);
                    //set the text to the center of the tile
                    GridPane.setHalignment(text[i][j],HPos.CENTER);
                }
            }
        }
    }

 
/**Class header: myKeyHandler Class implements EventHandler<KeyEvent>, inside 
                 this class, we will allow users to press the four arrow keys 
                 to move the tiles of the board or "S" key to save the board.We
                 link the pressed key to the interface and we update both the 
                 score and tiles of the board.
**/    
   private class myKeyHandler implements EventHandler<KeyEvent> {
  

/**Method header: handles four arrow keys and "S" key.Everytime a key is pressed,
                  we want to check from the backend if the grid is still moveable,
                  then we move the tiles.We add a new random tile to the grid and
                  update the board to the user using updateGUI() method.In 
                  addition, we also print out the current moveing status to the 
                  console.
**/
   @Override
   public void handle(KeyEvent e){
        //if the up arrow key is being pressed
        if(e.getCode()==KeyCode.UP){
            //check if the board is till moveable
            //if it is still moveable, move the tiles accordingly
            if(board.move(Direction.UP)){
                board.addRandomTile(); 
                //update the score of the board 
                score = board.getScore();
                current_score.setText("Score: " + score);
                System.out.println("Moving Up");
                //update the tiles
                updateGUI();
            }
            //check if the game is over
            if(board.isGameOver() && value == false){
                //create the new Rectangle for the new GridPane
                Rectangle final_rectangle = new Rectangle();
                //set the new rectangle height and width according to the scene
                final_rectangle.widthProperty().bind(scene.widthProperty());
                final_rectangle.heightProperty().bind(scene.heightProperty());
                //set the game over color to the rectangle
                final_rectangle.setFill(Constants2048.COLOR_GAME_OVER);
                //create the Game Over text when the game is over
                Text game_over = new Text("Game Over!");                    
                game_over.setFont(Font.font("Courier", FontWeight.BOLD, 70));
                game_over.setFill(Constants2048.COLOR_VALUE_DARK);
                //add the rectangle and the text to the new GridPane
                new_pane.add(final_rectangle, 0, 0);
                new_pane.add(game_over, 0, 0);
                GridPane.setHalignment(game_over, HPos.CENTER);
                value = true;
             }
        }
        //if the down arrow key is being pressed
        if(e.getCode()==KeyCode.DOWN){
            //check if the board is till moveable
            //if it is still moveable, move the tiles accordingly
            if(board.move(Direction.DOWN)){
                board.addRandomTile();
                System.out.println("Moving Down");
                //update the score of the board 
                score = board.getScore();
                current_score.setText("Score: " + score);
                //update the tiles
                updateGUI();    
            }
            //check if the game is over
            if(board.isGameOver() && value == false){
                //create the new Rectangle for the new GridPane
                Rectangle final_rectangle = new Rectangle();
                //set the new rectangle height and width according to the scene
                final_rectangle.widthProperty().bind(scene.widthProperty());
                final_rectangle.heightProperty().bind(scene.heightProperty());
                //set the game over color to the rectangle
                final_rectangle.setFill(Constants2048.COLOR_GAME_OVER);
                //create the Game Over text when the game is over
                Text game_over = new Text("Game Over!");                    
                game_over.setFont(Font.font("Courier", FontWeight.BOLD, 70));
                game_over.setFill(Constants2048.COLOR_VALUE_DARK);
                //add the rectangle and the text to the new GridPane
                new_pane.add(final_rectangle, 0, 0);
                new_pane.add(game_over, 0, 0);
                GridPane.setHalignment(game_over, HPos.CENTER);
                value = true;
            }
        }
        //if the left arrow key is being pressed
        if(e.getCode()==KeyCode.LEFT && value == false){
            //check if the board is till moveable
            //if it is still moveable, move the tiles accordingly
            if(board.move(Direction.LEFT)){
                board.addRandomTile();
                System.out.println("Moving Left");
                //update the score of the board 
                score = board.getScore();
                current_score.setText("Score: " + score);
                //update the tiles
                updateGUI();
            }
            //check if the game is over
            if(board.isGameOver()){
                //create the new Rectangle for the new GridPane
                Rectangle final_rectangle = new Rectangle();
                //set the new rectangle height and width according to the scene
                final_rectangle.widthProperty().bind(scene.widthProperty());
                final_rectangle.heightProperty().bind(scene.heightProperty());
                //set the game over color to the rectangle
                final_rectangle.setFill(Constants2048.COLOR_GAME_OVER);
                //create the Game Over text when the game is over
                Text game_over = new Text("Game Over!");                    
                game_over.setFont(Font.font("Courier", FontWeight.BOLD, 70));
                game_over.setFill(Constants2048.COLOR_VALUE_DARK);
                //add the rectangle and the text to the new GridPane
                new_pane.add(final_rectangle, 0, 0);
                new_pane.add(game_over, 0, 0);
                GridPane.setHalignment(game_over, HPos.CENTER);
                value = true;
            }
        }
        //if the right arrow key is being pressed
        if(e.getCode()==KeyCode.RIGHT && value == false){
            //check if the board is till moveable
            //if it is still moveable, move the tiles accordingly
            if(board.move(Direction.RIGHT)){
                board.addRandomTile();
                System.out.println("Moving Right");
                //update the score of the board 
                score = board.getScore();
                current_score.setText("Score: " + score);
                //update the tiles
                updateGUI();
            }
            //check if the game is over
            if(board.isGameOver()){
                //create the new Rectangle for the new GridPane
                Rectangle final_rectangle = new Rectangle();
                //set the new rectangle height and width according to the scene
                final_rectangle.widthProperty().bind(scene.widthProperty());
                final_rectangle.heightProperty().bind(scene.heightProperty());
                //set the game over color to the rectangle
                final_rectangle.setFill(Constants2048.COLOR_GAME_OVER);
                //create the Game Over text when the game is over
                Text game_over = new Text("Game Over!");                    
                game_over.setFont(Font.font("Courier", FontWeight.BOLD, 70));
                game_over.setFill(Constants2048.COLOR_VALUE_DARK);
                //add the rectangle and the text to the new GridPane
                new_pane.add(final_rectangle, 0, 0);
                new_pane.add(game_over, 0, 0);
                GridPane.setHalignment(game_over, HPos.CENTER);
                value = true;
            }
        }
        //if "S" key is being pressed
        if(e.getCode()==KeyCode.S){
            //save the board to the file
            try {
                board.saveBoard(outputBoard);
                System.out.println("Saving Board to : " + outputBoard);
            } 
            catch (IOException exception) { 
                exception.printStackTrace();
            }
        }
    }
}

    
    /** DO NOT EDIT BELOW */
   

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
