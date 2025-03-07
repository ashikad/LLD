package TicTacToe;

import java.util.*;

public class Game {

    Deque<Player> players;
    Board gameBoard;

    Game(){
        initializeGame();
    }

    public void initializeGame(){

        players = new LinkedList<>();
        PlayingPieceX xPiece = new PlayingPieceX();
        Player player1 = new Player("Player1",xPiece);

        players = new LinkedList<>();
        PlayingPieceO oPiece = new PlayingPieceO();
        Player player2 = new Player("Player2",oPiece);

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);

    }

    public String startGame(){

        boolean noWinner = true;

        while(noWinner){

            //taking out the player who has the current turn and after his turn is completed, we put him at the back.
            Player playerTurn = players.removeFirst();

            gameBoard.printBoard();
            List<HashMap<Integer,Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()){
                noWinner = false;
                continue;
            }

            System.out.println("Player:"+playerTurn.name+" Enter row,column to play : ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] rowColumn = s.split(",");
            int inputRow = Integer.parseInt(rowColumn[0]);
            int inputColumn = Integer.parseInt(rowColumn[1]);

            boolean addedPiece = gameBoard.addPiece(inputRow,inputColumn,playerTurn.playingPiece);
            if(!addedPiece){
                System.out.println("Incorrect Position choose, please try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean isWinner = isThereWinner(inputRow,inputColumn,playerTurn.playingPiece.pieceType);
            if(isWinner) {
                gameBoard.printBoard();
                return playerTurn.name;
            }
        }
        return "It's a Tie!";
    }


    public boolean isThereWinner(int inputRow, int inputCol, PieceType inputPieceType){

        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch= true;

        for(int i=0;i< gameBoard.size;i++){
            if(gameBoard.board[inputRow][i] == null || gameBoard.board[inputRow][i].pieceType!=inputPieceType) {
                rowMatch = false;
            }

        }
        for(int i=0;i< gameBoard.size;i++){
            if(gameBoard.board[i][inputCol] == null || gameBoard.board[i][inputCol].pieceType!=inputPieceType) {
                colMatch = false;
            }

        }
        for(int i=0,j=0;i<gameBoard.size;i++,j++){
            if(gameBoard.board[i][j]==null || gameBoard.board[i][j].pieceType!=inputPieceType)
                diagonalMatch=false;
        }
        for(int i=0,j= gameBoard.size-1;i< gameBoard.size;i++,j--){
            if(gameBoard.board[i][j]==null || gameBoard.board[i][j].pieceType!=inputPieceType)
                antiDiagonalMatch=false;
        }

        return rowMatch||colMatch||diagonalMatch||antiDiagonalMatch;
    }

}
