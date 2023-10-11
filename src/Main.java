import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    //Arraylist som lagrar data för spelarens position
    static ArrayList<Integer> playerPositions1 = new ArrayList<Integer>();
    static ArrayList<Integer> playerPositions2 = new ArrayList<Integer>();
    //Räknare för antal drag
    static int totalMoves = 0;

    public static void main(String[] args) {
        //Ange sitt namn
        Scanner scanner = new Scanner(System.in);

        boolean playAgain = true; // Variabel för att kontrollera om spelet ska spelas igen

        while (playAgain) {
            playGame(); // Funktion för att spela ett spel

            // Fråga om spelarna vill spela igen
            System.out.println("Vill ni spela igen? (ja/nej)");
            String playAgainInput = scanner.nextLine().toLowerCase();

            if (!playAgainInput.equals("ja")) {
                playAgain = false; // Om spelarna inte vill spela igen, sätt playAgain till false
            }
        }

        System.out.println("Tack för att du spelade! Hejdå.");
    }

    public static void playGame() {
        Scanner scanner = new Scanner(System.in);

        // Rensa positionslistor och återställ antal drag
        playerPositions1.clear();
        playerPositions2.clear();
        totalMoves = 0;

        System.out.println("Namn på spelare 1: ");
        String playerName1 = scanner.nextLine();
        Player player1 = new Player(playerName1);

        System.out.println("Namn på spelare 2: ");
        String playerName2 = scanner.nextLine();
        Player player2 = new Player(playerName2);

        System.out.println("Välkommen, " + player1.getPlayerName() + " och " + player2.getPlayerName() + "!");

        //Skapar spelplanen
        char[][] gameBoard = {
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '}
        };
        //Skriver ut spelplanen
        printGameBoard(gameBoard);

        boolean gameOver = false;

        //While statement för att få spelet att gå runt
        while(!gameOver) {
            //Ber om placering till spelaren
            Scanner scan = new Scanner(System.in);

            //player 1 se till så att man lägger symbol vid ledig position
            int playerPos1;
            do {
                System.out.print(playerName1 + " enter your placement (1-9): ");
                playerPos1 = scan.nextInt();

                if (playerPos1 < 1 || playerPos1 > 9) {
                    System.out.println("Ogiltig input. Ange en siffra mellan 1 och 9.");
                } else if (isPositionTaken(playerPos1, playerPositions1, playerPositions2)) {
                    System.out.println("Positionen är tagen, välj en annan.");
                }
            } while (playerPos1 < 1 || playerPos1 > 9 || isPositionTaken(playerPos1, playerPositions1, playerPositions2));


            placePiece(gameBoard, playerPos1, "Player 1");
            playerPositions1.add(playerPos1);
            totalMoves++;

            printGameBoard(gameBoard);

            // Kontrollera om spelaren 1 har vunnit
            if (checkWinner(playerPositions1).equals("Congratulations Player1 won!")) {
                System.out.println("Congratulation, " + playerName1 + "! You won!");
                gameOver = true;
                break;
            }

            // Kolla om det är oavgjort
            if (isDraw(totalMoves)) {
                System.out.println("Oavgjort! Ingen vann.");
                gameOver = true;
                // Avsluta spelet om det är oavgjort
                break;
            }

            //player 2 se till så att man lägger symbol vid ledig position
            int playerPos2;
            do {
                System.out.print(playerName2 + " enter your placement (1-9): ");
                playerPos2 = scan.nextInt();

                if (playerPos2 < 1 || playerPos2 > 9) {
                    System.out.println("Ogiltig input. Ange en siffra mellan 1 och 9.");
                } else if (isPositionTaken(playerPos2, playerPositions2, playerPositions1)) {
                    System.out.println("Positionen är tagen, välj en annan.");
                }
            } while (playerPos2 < 1 || playerPos2 > 9 || isPositionTaken(playerPos2, playerPositions2, playerPositions1));


            placePiece(gameBoard, playerPos2, "Player 2");
            playerPositions2.add(playerPos2);
            totalMoves++;

            printGameBoard(gameBoard);

            // Kontrollera om spelaren 2 har vunnit
            if (checkWinner(playerPositions2).equals("Congratulations Player2 won!!")) {
                System.out.println("Congratulation, " + playerName2 + "! You won!");
                gameOver = true;
            }
            // Kolla om det är oavgjort
            if (isDraw(totalMoves)) {
                System.out.println("Oavgjort! Ingen vann.");
                gameOver = true;
                // Avsluta spelet om det är oavgjort
                break;
            }
        }
    }

    //deklarera placering och symbol
    public static void placePiece(char[][] gameBoard, int pos, String user) {
        //Ger spelare 1 X och spelare 2 O
        char symbol = ' ';

        if (user.equals("Player 1")) {
            symbol = 'X';
            playerPositions1.add(pos);
        } else if (user.equals("Player 2")) {
            symbol = 'O';
            playerPositions2.add(pos);
        }

        //Switchfunktion för ge rätt position
        switch (pos) {
            case 1:
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }

    }

    //Metod för spelplanen
    private static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    //deklarera vinstdragen
    public static String checkWinner(List<Integer> playerPositions) {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        //Lista av en arraylist för vinster.
        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        //Forloop för vinnare. Går igenom listan för checkwinner
        for (List l : winning) {
            if (playerPositions.containsAll(l)) {
                return "Congratulations Player1 won!";
            } else if (playerPositions2.containsAll(l)) {
                return "Congratulations Player2 won!!";
            } else if (playerPositions.size() + playerPositions2.size() == 9) {
                return "It´s a tie!";
            }
        }

        return "";
    }
    // Funktion för att kontrollera om en position är upptagen
    public static boolean isPositionTaken(int position, List<Integer> playerPositions1, List<Integer> playerPositions2) {
        return playerPositions1.contains(position) || playerPositions2.contains(position);
    }
    // Funktion för att kontrollera oavgjort
    public static boolean isDraw(int totalMoves) {
        return totalMoves == 9;
    }
}

