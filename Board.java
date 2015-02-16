//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Brandon Williams                                        //
// Date:    1/17/15                                                 //
//------------------------------------------------------------------//

/**     Sample Board
 *
 *      0   1   2   3
 *  0   -   -   -   -
 *  1   -   -   -   -
 *  2   -   -   -   -
 *  3   -   -   -   -
 *
 *  The sample board shows the index values for the columns and rows
 */

/*
* Name: Daniel Vick
* Login: cs8bary
* Date: January 29, 2015
* File: Board.java
*
* Creates a board object that keeps that is populated by values.
* The board has several methods to add random tiles, move the tiles,
* and check if moves are possible. 
* It can construct new boards and open saved boards.
*/

import java.util.*;
import java.io.*;

public class Board
{
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random;
    private int[][] grid;
    private int score;

    // Constructs a fresh board with random tiles
    public Board(int boardSize, Random random)
    {
	// Creates blank board and sets score to 0
        this.random = random;
        this.GRID_SIZE = boardSize;
	grid = new int[boardSize][boardSize];
	score = 0;

	// Adds two random tiles to the board
	this.addRandomTile();
	this.addRandomTile();
    }

    // Construct a board based off of an input file 
    public Board(String inputBoard, Random random) throws IOException
    {
        this.random = random;

	//Reads in a saved board
	Scanner boardIn = new Scanner(new File(inputBoard));
        this.GRID_SIZE = Integer.parseInt(boardIn.nextLine());
	grid = new int[GRID_SIZE][GRID_SIZE];
	this.score = Integer.parseInt(boardIn.nextLine());
	for (int row = 0; row < GRID_SIZE; row ++)
	{
            for (int column = 0; column < GRID_SIZE; column++)
	    {
                grid[row][column] = Integer.parseInt(boardIn.next());
	    }
     	}
	boardIn.close();
    }

    /*
     * Name: saveBoard
     * Purpose: Saves current board to a file
     * Parameters: @outputBoard file to save board in.
     * Return: void
     */
    public void saveBoard(String outputBoard) throws IOException
    {
	// Writes current board to input file
        PrintWriter boardOut = new PrintWriter(outputBoard);
	StringBuilder outString = new StringBuilder();

	// Adds grid size and score to top two lines of save file
	outString.append(this.GRID_SIZE + "\n");
	outString.append(this.score + "\n");

	// Populates saved board
	for (int row = 0; row < GRID_SIZE; row ++)
	{
            for (int column = 0; column < GRID_SIZE; column++)
	    {
                outString.append(grid[row][column] + " ");
	    }
	    outString.append("\n");
	}
	boardOut.print(outString);
	boardOut.close();

    }

    /*
     * Name: addRandomTile
     * Purpose: Adds a random tile (of value 2 or 4) to a random
     * 		empty space on the board
     * Parameters: None.
     * Return: void
     */
    public void addRandomTile()
    {
        int count = 0;
	// Loops through board keeping count of empty spaces
	for (int row = 0; row < GRID_SIZE; row ++)
	{
            for (int column = 0; column < GRID_SIZE; column++)
	    {
                if (this.grid[row][column] == 0) {
		    count++;
		}
	    }
     	}

	// Assigns random location and value
	int randomLoc = this.random.nextInt(count);
	int value = this.random.nextInt(100);
	int emptyCount = 0;

	// Walks board to find randomLoc and assign it to 2 or 4
	for (int row = 0; row < GRID_SIZE; row ++)
	{
            for (int column = 0; column < GRID_SIZE; column++)
	    {
                if (this.grid[row][column] == 0) {
		    if (emptyCount == randomLoc) {
                        if (value < TWO_PROBABILITY) {
			    this.grid[row][column] = 2;
			    return; 
			}
			else { 
			    this.grid[row][column] = 4;
			    return; 
			}
		    }
		    else { 
		     emptyCount++; 
		    }
	        } 
	    }
     	}
    }

    /*
     * Name: isGameOver
     * Purpose: Determines if game is over
     * Parameters: None.
     * Return: boolean representing whether the game is over or not.
     */
    public boolean isGameOver()
    {
	// Checks possible moves in each direction
	if ((this.canMoveUp() || this.canMoveDown() || this.canMoveLeft() 
				|| this.canMoveRight()) != true) {
	    System.out.println("Game Over!");
            return true;
	} 
	else {
        return false;
	}
    }

    /*
     * Name: canMove
     * Purpose: Determines if we can move in a given direction.
     * Parameters: @direction direction to check possible movement in.
     * Return: boolean representing the possiblity to move in given direction.
     */
    public boolean canMove(Direction direction)
    {
	// Checks possible move in input direction
        if (direction.equals(Direction.UP)) { return canMoveUp(); }
        else if (direction.equals(Direction.DOWN)) { return canMoveDown(); }
        else if (direction.equals(Direction.LEFT)) { return canMoveLeft(); }
	else if (direction.equals(Direction.RIGHT)) { return canMoveRight(); }
	else {return false; }
    }

    // Perform a move Operation
    public boolean move(Direction direction)
    {
	
        if (direction.equals(Direction.UP)) {
            this.moveUp();
	}

	else if (direction.equals(Direction.DOWN)) {
            this.moveDown();
	}

	else if (direction.equals(Direction.LEFT)) {
            this.moveLeft();
	}

	else if (direction.equals(Direction.RIGHT)) {
            this.moveRight();
	}
	
        this.makeAbsolute(this.grid);
	return true;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid()
    {
        return grid;
    }

    // Return the score
    public int getScore()
    {
        return score;
    }

    /*
     * Name: canMoveUp
     * Purpose: Checks to see if there is a possible move upward on the board.
     * Parameters: None.
     * Return: boolean representing the possiblity to move up.
     */
    public boolean canMoveUp() {
	// Loops through board checking if each tile can move up
        for (int row = (GRID_SIZE - 1); row > 0; row--) {
            for (int column = 0; column < GRID_SIZE; column++) 
	    {
                if (((grid[row - 1][column] == grid[row][column]) 
				&& (grid[row][column] != 0)) 
				|| ((grid[row][column] != 0)
				&& (grid[row - 1][column] == 0))) {
                    return true;
		}
	    }
	}
	return false;
    }

    /*
     * Name: canMoveDown
     * Purpose: Checks to see if there is a 
     * 		possible move downward on the board.
     * Parameters: None.
     * Return: boolean representing the possiblity to move down.
     */ 
    public boolean canMoveDown() {
	// Loops through board checking if each tile can move down
        for (int row = 0; row < (GRID_SIZE - 1); row++) {
            for (int column = 0; column < GRID_SIZE; column++) 
	    {
                if (((grid[row + 1][column] == grid[row][column])
				&& (grid[row][column] != 0)) 
				|| ((grid[row][column] != 0) 
				&& (grid[row + 1][column] == 0))) {
                    return true;
		}
	    }
	}
	return false;
    }

    /*
     * Name: canMoveLeft
     * Purpose: Checks to see if there is a possible move left on the board.
     * Parameters: None.
     * Return: boolean representing the possiblity to move left.
     */
    public boolean canMoveLeft() {
	// Loops through board checking if each tile can move left
	for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = (GRID_SIZE - 1); column > 0; column--) 
	    {

                if (((grid[row][column - 1] == grid[row][column])
				&& (grid[row][column] != 0)) 
				|| ((grid[row][column] != 0) 
				&& (grid[row][column - 1] == 0))) {
                    return true;
		}
	    }
	}
	return false;
    }

    /*
     * Name: canMoveRight
     * Purpose: Checks to see if there is a possible move right on the board.
     * Parameters: None.
     * Return: boolean representing the possiblity to move right.
     */
    public boolean canMoveRight() {
	// Loops through board checking if each tile can move right
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < (GRID_SIZE - 1); column++) 
	    {

                if (((grid[row][column + 1] == grid[row][column]) 
				&& (grid[row][column] != 0)) 
				|| ((grid[row][column] != 0) && 
				(grid[row][column + 1] == 0))) {
                    return true;
		}
	    }
	}
	return false;
    }

    /*
     * Name: moveUp
     * Purpose: Shifts all tiles up that can move up, and combines all
     * tiles that can be combined. Also increments score.
     * Parameters: None.
     * Return: None
     */
    public void moveUp() {
        for (int column = 0; column < GRID_SIZE; column++) {
            for (int row = 0; row < (GRID_SIZE - 1); row++) {
		// Checks to see if tile can be moved up
                while ((grid[row][column] == 0) && 
				( grid[row + 1][column] != 0))
		{
		    grid[row][column] = grid[row + 1][column];
		    grid[row + 1][column] = 0;
		    if (row != 0) {
		        row = row - 1;
		    }

		}
		// combines tiles with the same value and increments score.
		if (grid[row][column] == grid[row + 1][column]) {
		    grid[row][column] = -(grid[row][column] + 
			    grid[row + 1][column]);

		    this.score += Math.abs(grid[row][column]);

		    grid[row + 1][column] = 0;
		}
	    }
	}
    }

    /*
     * Name: moveDown
     * Purpose: Shifts all tiles down that can move up, and combines all
     * tiles that can be combined. Also increments score.
     * Parameters: None.
     * Return: None
     */
    public void moveDown() {
        for (int column = 0; column < GRID_SIZE; column++) {
            for (int row = (GRID_SIZE - 1); row > 0; row--) {
		// Checks to see if tile can be moved down
                while ((grid[row][column] == 0) 
				&& ( grid[row - 1][column] != 0))
		{
		    grid[row][column] = grid[row - 1][column];
		    grid[row - 1][column] = 0;
		    if (row != (GRID_SIZE - 1)) {
		        row = row + 1;
		    }

		}
		// combines tiles with the same value and increments score.
		if (grid[row][column] == grid[row - 1][column]) {
		    grid[row][column] = -(grid[row][column] + 
			    grid[row - 1][column]);

		    score +=  Math.abs(grid[row][column]);

		    grid[row - 1][column] = 0;
		}
	    }
	}
    }

    /*
     * Name: moveLeft
     * Purpose: Shifts all tiles left that can move up, and combines all
     * tiles that can be combined. Also increments score.
     * Parameters: None.
     * Return: None
     */
    public void moveLeft() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < (GRID_SIZE - 1); column++) {
		// Checks to see if tile can be moved left
                while ((grid[row][column] == 0) && 
				( grid[row][column+1] != 0))
		{
		    grid[row][column] = grid[row][column + 1];
		    grid[row][column + 1] = 0;
		    if (column != 0) {
		        column = column - 1;
		    }

		}
		// combines tiles with the same value and increments score.
		if (grid[row][column] == grid[row][column + 1]) {
		    grid[row][column] = -(grid[row][column] + 
			    grid[row][column + 1]);

		    this.score += Math.abs(grid[row][column]);

		    grid[row][column + 1] = 0;
		}
	    }
	}
    }

    /*
     * Name: moveRight
     * Purpose: Shifts all tiles right that can move up, and combines all
     * tiles that can be combined. Also increments score.
     * Parameters: None.
     * Return: None
     */
    public void moveRight() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = (GRID_SIZE - 1); column > 0; column--) {
		// Checks to see if tile can be moved right
                while ((grid[row][column] == 0) && 
				( grid[row][column - 1] != 0))
		{
		    grid[row][column] = grid[row][column - 1];
		    grid[row][column - 1] = 0;
		    if (column != (GRID_SIZE - 1 )) {
		        column = column + 1;
		    }

		}
		// combines tiles with the same value and increments score.
		if (grid[row][column] == grid[row][column - 1]) {
		    grid[row][column] = (grid[row][column] + 
			    grid[row][column - 1]);

		    this.score += Math.abs(grid[row][column]);

		    grid[row][column - 1] = 0;
		}
	    }
	}
    }

    /*
     * Name: makeAbsolute
     * Purpose: Sets each element of an array to its absolute value.
     * Parameters: None.
     * Return: None
     */
    public void makeAbsolute(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for ( int column = 0; column < GRID_SIZE; column++) {
                if (grid[row][column] < 0) {
                    grid[row][column] = Math.abs((grid[row][column]));
		}
	    }
	}
    }
    

    @Override
    public String toString()
    {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", this.score));
        for (int row = 0; row < GRID_SIZE; row++)
        {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                                    String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
