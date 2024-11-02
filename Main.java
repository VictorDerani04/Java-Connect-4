import java.util.Scanner;

class C4Board{
    // 2D array corresponding to the connect 4 board
    private int [][] board;
    private int rows = 6;
    private int cols = 7;
    // Set up some colors for the console (might not work in replit or another IDE)
    // "Borrowed" from here:
    //  https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println/5762502#5762502
    //
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Constructor. Creates 6x7 board and initializes to negative one. IDE is
     *  gonna tell you to use "Arrays.fill()" ... don't
     */
    C4Board(){
        board = new int [rows][cols];

        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                board[r][c] = -1;
            }
        }
    }

    /**
     * Resets the 2D array back to negative ones
     */
    void clear(){
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                board[r][c] = -1;
            }
        }
    }

    /**
     * Checks to see if "player" (0 or 1) wins.
     * @param player
     * @return true if the player has won, false otherwise
     */
    boolean isWinner(int player){
        int counter = 0;
        //horizontal check
        for(int r = 0; r<rows; r++){
            counter = 0;
            for(int c = 0; c<cols; c++){
                if(board[r][c] == player){
                    counter++;
                    if(counter == 4){
                        return true;
                    }
                }
                else
                    counter = 0;
            }
        }

        //vertical check
        for(int c = 0; c<cols; c++){
            counter = 0;
            for(int r = 0; r<rows; r++){
                if(board[r][c] == player){
                    counter++;
                    if(counter == 4){
                        return true;
                    }
                }
                else
                    counter = 0;
            }
        }

        //diagonal check
        for(int r = 3; r<rows; r++){
            for(int c = 0; c<cols-3; c++){
                if(board[r][c] == player &&
                        board[r-1][c+1] == player &&
                        board[r-2][c+2] == player &&
                        board[r-3][c+3] == player){
                    return true;
                }
            }
        }

        //diagonal check
        for(int r = 0; r<rows-3; r++){
            for(int c = 0; c<cols-4; c++){
                if(board[r][c] == player &&
                        board[r+1][c+1] == player &&
                        board[r+2][c+2] == player &&
                        board[r+3][c+3] == player){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     //     * Getter for the board
     * @param row
     * @param column
     * @return the board state (-1 for empty, 0 for O, 1 for X)
     */
    int getBoardState(int row,int column){
        return -1;
    }

    /**
     * Setter for the board. If the spot is already occupied,
     *  returns false and makes no move.
     * @param row
     * @param column
     * @param player
     * @return true if the move was valid, false if it was not
     */
    boolean setBoardState(int row,int column,int player){
        board[row][column] = player;
        return true;
    }


    /**
     * Column number goes in, true returned if a move can be placed in
     *  that column, false if not
     * @param column
     * @return True/False if a move can be made in the given column
     */
    boolean isOkayMove(int column){
        if(board[0][column] == -1)
            return true;
        else
            return false;
    }

    /**
     * This is the "computer player" routine. You are welcome to violate
     *   some of the same rules applied to the player routine. You can
     *   assume the computer won't do something stupid, so feel free
     *   to let it directly access the board instead of going through
     *   setBoardState (it will make it easier for the computer to make
     *   hypothetical moves)
     * @param player
     * @return boolean. True of a move was made, false if not (should only be false if the board
     *  is literally full)
     */
    boolean computerMakeMove(int player){
        int counter = 0;
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<cols; c++){
                if(board[r][c] != -1){
                    counter++;
                }
            }
        }

        if(counter == 0){
            board[rows-1][cols/2] = player;
            return true;
        }

        else if(counter >= 0){
            int random = (int) (Math.random()*2)+1;
            if(random == 1){
                for(int r = 0; r<rows; r++){
                    for(int c = 0; c<cols; c++){
                        if(board[r][c] != -1){
                            boolean play;
                            do{
                                play = makeMove(c-1, player);
                            }while(play == false);
                            return true;
                        }
                    }
                }
            }
            else if(random == 2){
                for(int r = 0; r<rows; r++){
                    for(int c = 0; c<cols; c++){
                        if(board[r][c] != -1) {
                            boolean play;
                            do {
                                play = makeMove(c + 1, player);
                            } while (play == false);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * Places a players move depending on which column they picked.
     *  Returns false if the move cannot be made
     * @param column
     * @param player
     * @return true or false depending if move can be made or not
     */
    boolean makeMove(int column, int player){
        if(isOkayMove(column)){
            for(int r = 0; r<rows; r++){
                if(board[r][column] != -1){
                    if(setBoardState(r-1, column, player))
                        return true;
                }
                else if(board[r][column] == -1 && r == rows-1){
                    if(setBoardState(r, column, player))
                        return true;
                }
            }

        }
        isWinner(player);
        return false;
    }

    /**
     * The toString... prints the board. Uses some fancy colors I
     *  stole from stackoverflow
     * @return
     */
    public String toString(){
        String out = "";

        for (int j = 0; j < board[0].length; ++j)
            out+=("| "+String.valueOf(j)+" ");
        out += "|\n";
        for (int j = 0; j < board[0].length; ++j)
            out+="----";
        out+=("-")+"\n";
        for (int[] ints : board) {
            out += ("|");
            for (int anInt : ints) {
                if (anInt == -1)
                    out += ("   ");
                else if (anInt == 0)
                    out += (ANSI_YELLOW + " O " + ANSI_RESET);
                else if (anInt == 1)
                    out += (ANSI_RED + " X " + ANSI_RESET);
                else
                    out += (ANSI_GREEN + "BAD" + ANSI_RESET);
                out += "|";

            }
            out += "\n";
            for (int j = 0; j < board[0].length; ++j)
                out += "----";
            out += ("-") + "\n";
        }
        return out;
    }
}


public class Main {

    /**
     * Plays an entire game of Connect 4.
     * @param c4
     * @return int which is 0 for O winning and 1 for X winning (-1 if it is a tie)
     */
    public static int playAGame(C4Board c4){

        // Set up a scanner
        Scanner scan = new Scanner(System.in);


        scan.close();

        return 0;
    }
    /**
     * The main routine
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        // Declare an instance of the board
        System.out.print("What would you like to play?\nComputer x Computer (0)\n" +
                "Player x Computer (1)\nPlayer x Player (2)\nINPUT: ");
        int option = scan.nextInt();

        // Play a game and print out the winner
        C4Board board = new C4Board();
        System.out.println(board);

        int player = 0;
        int play;
        if (option == 2) {
            do {
                System.out.print("If you want to start or play again, input a positive number. If not input a negative number: ");
                play = scan.nextInt();
                if(play < 0 && player == 0){
                    System.out.println("Doc, are you trying to break my code??? At least play once");
                }
                do {
                    System.out.print("Choose a column: ");
                    int column = scan.nextInt();
                    if (board.makeMove(column, player % 2)) {
                        System.out.println(board);
                        player++;
                    } else if (board.makeMove(column, player % 2) == false) {
                        System.out.println("this column is full. Please input a valid number.");
                    }
                } while (board.isWinner((player - 1) % 2) == false);
                if(play >= 0){
                    board.clear();
                }
                if(player%2 == 0){
                    System.out.println("Congratulations red player! You won!");
                }
                else{
                    System.out.println("Congratulations yellow player! You won!");
                }
                player = 0;
            }while(play >= 0 );
        }

        else if(option == 1){
            do {
                System.out.print("If you want to start or play again, input a positive number. If not input a negative number: ");
                play = scan.nextInt();
                if (play < 0 && player == 0) {
                    System.out.println("Doc, are you trying to break my code??? At least play once");
                }
                do{
                    if (player % 2 == 0) {
                        board.computerMakeMove(0);
                        System.out.println(board);
                    }
                    else if (player % 2 == 1) {
                        System.out.print("Choose a column: ");
                        int column = scan.nextInt();
                        if (board.makeMove(column, player % 2)) {
                            System.out.println(board);
                        }
                        else if (board.makeMove(column, player % 2) == false) {
                            System.out.println("this column is full. Please input a valid number.");
                        }
                    }
                    player++;
                }while(board.isWinner((player - 1) % 2) == false);
                if(play >= 0){
                    board.clear();
                }
                if(player%2 == 0){
                    System.out.println("Congratulations red player! You won!");
                }
                else{
                    System.out.println("Congratulations yellow player! You won!");
                }
                player = 0;
            }while(play >= 0 );
        }
        else if(option == 0){
            do {
                System.out.print("If you want to start or play again, input a positive number. If not input a negative number: ");
                play = scan.nextInt();
                if (play < 0 && player == 0) {
                    System.out.println("Doc, are you trying to break my code??? At least play once");
                }
                do{
                    if (player % 2 == 0) {
                        board.computerMakeMove(0);
                        System.out.println(board);
                    }
                    else if (player % 2 == 1) {
                        board.computerMakeMove(1);
                        System.out.println(board);
                    }
                    player++;
                }while(board.isWinner((player - 1) % 2) == false);
                if(play >= 0){
                    board.clear();
                }
                if(player%2 == 0){
                    System.out.println("Congratulations red player! You won!");
                }
                else{
                    System.out.println("Congratulations yellow player! You won!");
                }
                player = 0;
            }while(play >= 0 );
        }

        // Ask user if they want to play again. If
        //  so, reset the board and repeat. Continue
        //  until they say they don't want to play anymore

        scan.close();
    }
}