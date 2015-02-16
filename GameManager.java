//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  Brandon Willaims                                        //
// Date:    1/17/15                                                 //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;

/*
* Name: Daniel Vick
* Login: cs8bary ­­­ Use your cs8b course­specific account name
* Date: January 29, 2015
* File: GameManager.java
*
* Creates an object to organize the game by creating the board
* or opening a saved game. Then it prompts the user for input
* and processes that input.
*/

public class GameManager
{
    // Instance variables
    private Board board;    // The actual 2048 board
    private String outputFileName;  // File to save the board to when exiting
    
    // GameManager Constructor
    // Generate new game
    GameManager(int boardSize, String outputBoard, Random random)
    {
       this.board = new Board(boardSize, random);
       this.outputFileName = outputBoard;
    }

    // GameManager Constructor
    // Load a saved game
    GameManager(String inputBoard, String outputBoard, Random random)
        throws IOException
    {
        this.board = new Board(inputBoard, random);
	this.outputFileName = outputBoard;
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
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    public void play() throws IOException
    {
	// Prints controls and current board.
	this.printControls();
	//String boardString = this.board.toString();
	//System.out.println(boardString);

	// Creates scanner to receive user input
	Scanner userInput = new Scanner(System.in);

	// While loop that checks if the game is over, if not
	// receives user ipnut and processes it.
        while (this.board.isGameOver() == false) {
	    System.out.println(this.board.toString());
	    System.out.print("Input Command: ");
            String input = (userInput.next());

	    // If user inputs q, the board is saved and the game exits.
	    if (input.equals("q")) {
		System.out.println("Saving game...");
		System.out.println("Quiting game");
	        board.saveBoard(this.outputFileName);
		return;
	    } 
	    // If user inputs w, the board attempts to move up
	    else if (input.equals("w")) {
		
                if (board.canMove(Direction.UP)) {
		    
                    board.move(Direction.UP);
		    board.addRandomTile();

		}
	    } 
	    // If user inputs a, the board attempts to move left
	    else if (input.equals("a")) {
                if (board.canMove(Direction.LEFT)) {
                    board.move(Direction.LEFT);
		    board.addRandomTile();
		}
	    } 
	    // If user inputs s, the board attempts to move down
	    else if (input.equals("s")) {
                if (board.canMove(Direction.DOWN)) {
                    board.move(Direction.DOWN);
		    board.addRandomTile();
		}
	    } 
	    // If user inputs d, the board attempts to move right
	    else if (input.equals("d")) {
                if (board.canMove(Direction.RIGHT)) {
                    board.move(Direction.RIGHT);
		    board.addRandomTile();
		}
	    } // If user inputs a non-valid move, controls are printed and
	      // user is prompted to input a move
	    else {
	        this.printControls();
	    }
        }
    }

    /*
     * Name: printControls
     * Purpose: Prints the controls
     * Parameters: None.
     * Return: void
     */ 
    private void printControls()
    {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
