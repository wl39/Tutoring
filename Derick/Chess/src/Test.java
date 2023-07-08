import java.util.Scanner;

public class Test {
    public static char[][] board = new char[8][8];
    public static boolean isValidMovement = true;
    public static final char WHITE_KNIGHT = 'N'; // 변수 선언 및 초기화

    public static void init() {
        int size = 8;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
                if ((i == 0) && (j == 0 || j == 7)) {
                    board[i][j] = 'r';
                } else if ((i == 7) && (j == 1 || j == 6)) {
                    board[i][j] = WHITE_KNIGHT;
                    //WHITE_KNIGHT = 'N'
                } else if ((i == 7) && (j == 2 || j == 5)) {
                    board[i][j] = 'B';
                } else if ((i == 7 && j == 3)) {
                    board[i][j] = 'Q';
                } else if ((i == 7 && j == 4)) {
                    board[i][j] = 'K';
                } else if (i == 1) {
                    board[i][j] = 'p';
                } else if ((i == 7) && (j == 0 || j == 7)) {
                    board[i][j] = 'R';
                } else if ((i == 0) && (j == 1 || j == 6)) {
                    board[i][j] = 'n';
                    //black Knight
                } else if ((i == 0) && (j == 2 || j == 5)) {
                    board[i][j] = 'b';
                } else if (i == 0 && j == 3) {
                    board[i][j] = 'q';
                } else if (i == 0 && j == 4) {
                    board[i][j] = 'k';
                } else if (i == 6) {
                    board[i][j] = 'P';
                }
            }
        }

        board[1][0] = '.';

        board[1][4] = '.';
        board[1][3] = '.';

        board[1][7] = '.';

        board[0][1] = '.';
        board[0][6] = '.';

        board[6][0] = '.';

        board[6][4] = '.';
        board[6][3] = '.';

        board[6][7] = '.';

        board[7][1] = '.';
        board[7][6] = '.';

    }

    public static char[] selectPiece() {
        System.out.print("To which position?: ");
        return takeUserInput();
    }

    public static char[] moveTo() {
        System.out.print("To which position will you move to?: ");
        return takeUserInput();
    }

    public static char[] takeUserInput() {
        Scanner sc = new Scanner(System.in); // get user input
        String userInput = sc.nextLine(); // get user input

        return userInput.toCharArray();
    }



    public static int originalRow;
    public static int originalColumn;

    public static void setOriginalRowAndColumn() {
        char[] original_coordinate = selectPiece();

        originalColumn = original_coordinate[0] - 97; // 'a'-> 97:
        originalRow = 7 - (original_coordinate[1] - 49); // '1' -> 49
    }

    public static int lastRow;
    public static int lastColumn;


    public static void setLastRowAndColumn() {
        char[] last_coordinate = moveTo();

        lastColumn = last_coordinate[0] - 97;
        lastRow = 7 - (last_coordinate[1] - 49);
    }


    public static boolean diagonals() {

        isValidMovement = true;

        int diff_row_C = Math.abs(lastRow - originalRow);
        int diff_column_C = Math.abs(lastColumn - originalColumn);

        if (diff_row_C == diff_column_C) {
            if ((originalColumn < lastColumn) && (originalRow > lastRow)) {
                for (int i = 1; i < diff_column_C; i++) {
                    if ((board[originalRow - i][originalColumn + i]) != '.') {
                        isValidMovement = false;
                        System.out.println("It is an invalid move ! ");
                        break;
                    }
                }
            } else if ((originalColumn > lastColumn) && (originalRow > lastRow)) {
                //left top
                for (int i = 1; i < diff_column_C; i++) {
                    if ((board[originalRow - i][originalColumn - i]) != '.') {
                        isValidMovement = false;
                        System.out.println("It is an invalid move £");
                        break;
                    }
                }
            } else if ((originalColumn > lastColumn) && (originalRow < lastRow)) {
                //왼쪽 아래
                for (int i = 1; i < diff_column_C; i++) {
                    if ((board[originalRow + i][originalColumn - i]) != '.') {
                        isValidMovement = false;
                        System.out.println("It is an invalid movement $");
                        break;
                    }
                }

            } else if ((originalColumn < lastColumn) && (originalRow < lastRow)) {
                //오른쪽 아래
                System.out.println("Somethign has happened. %");
                for (int i = 1; i < diff_column_C; i++) {
                    if ((board[originalRow + i][originalColumn + i]) != '.') {
                        isValidMovement = false;
                        System.out.println("It is an invalid movement");
                        break;
                    }
                }
            }
        }else{
            isValidMovement = false;
        }

        return isValidMovement;
    }

    public static boolean vert_horiz() {

        isValidMovement = false;

        if (originalRow == lastRow) {
            for (int i = originalColumn + 1; i < lastColumn; i++) {
                if ((board[originalRow][i]) == '.') {
                    isValidMovement = true;
                    break;
                }
            }
        } else if (originalColumn == lastColumn) {
            for (int i = 0; i < lastRow; i++) {
                if ((board[originalRow][i]) == '.') {
                    isValidMovement = true;
                    break;
                }
            }

        }
        return isValidMovement;
    }

    public static boolean original_boundary() {
        return (originalRow <= 7 && (originalRow >= 0) && (originalColumn <= 7) && (originalColumn >= 0));
    }

    public static boolean destination_boundary() {
        return (lastRow <= 7) && (lastRow >= 0) && (lastColumn <= 7) && (lastColumn >= 0);
    }

    public static void print_board() {
        System.out.println("  a b c d e f g h");
        for (int row = 0; row < 8; row++) {
            System.out.print(8 - row + " ");
            for (int column = 0; column < 8; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    public static boolean whitecharecterchoosen() {
        boolean white_character = false;
        if ((board[originalRow][originalColumn]) == 'K') {
            white_character = true;
        } else if ((board[originalRow][originalColumn]) == 'Q') {
            white_character = true;
        } else if ((board[originalRow][originalColumn]) == 'P') {
            white_character = true;
        } else if ((board[originalRow][originalColumn]) == 'B') {
            white_character = true;
        } else if ((board[originalRow][originalColumn]) == 'N') {
            white_character = true;
        } else if ((board[originalRow][originalColumn]) == 'R') {
            white_character = true;
        }

        return white_character;
    }

    public static boolean blackcharecterchoosen() {
        boolean black_character = false;
        if ((board[lastRow][lastColumn]) == 'k') {
            black_character = true;
        } else if ((board[lastRow][lastColumn]) == 'q') {
            black_character = true;
        } else if ((board[lastRow][lastColumn]) == 'p') {
            black_character = true;
        } else if ((board[lastRow][lastColumn]) == 'b') {
            black_character = true;
        } else if ((board[lastRow][lastColumn]) == 'n') {
            black_character = true;
        } else if ((board[lastRow][lastColumn]) == 'r') {
            black_character = true;
        }
        return black_character;
    }

    public static boolean white_king() {
        boolean white_dead = false;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == 'K') {
                    white_dead = false;
                    break;
                } else {
                    white_dead = true;
                }
            }
        }
        if (white_dead) {
            System.out.println("Black won the game");
        }

        return white_dead;
    }

    public static boolean black_king() {
        boolean black_dead = true;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == 'k') {
                    black_dead = true;
                    break;
                } else {
                    black_dead = false;
                }
            }
        }

        if (black_dead) {
            System.out.println("White wins the game");
        }
        return black_dead;
    }

    public static boolean p_movement() {
        isValidMovement = false;
        if ((board[lastRow][lastColumn]) == '.') {
            if (originalColumn == lastColumn) {
                if (board[originalRow][originalColumn] == 'P' && (originalRow - lastRow) == 1) {
                    isValidMovement = true;
                } else {

                    System.out.println("Wrong Row position");
                }
            } else {
                System.out.println("Wrong Column position");
            }
        }
        return isValidMovement;
    }

    public static boolean black_p_movement() {
        isValidMovement = false;
        if ((board[lastRow][lastColumn]) == '.') {
            if (originalColumn == lastColumn) {
                if (board[originalRow][originalColumn] == 'p' && (originalRow - lastRow) == -1) {
                    isValidMovement = true;
                } else {

                    System.out.println("Wrong Row position");
                }
            } else {
                System.out.println("Wrong Column position");
            }
        }
        return isValidMovement;
    }

    public static boolean k_movement() {
        isValidMovement = false;
        int horiz_diff = Math.abs(originalRow - lastRow);
        int vert_diff = Math.abs(originalColumn - lastColumn);

        if ((vert_diff == 1) && (horiz_diff == 0)) {
            isValidMovement = true;
        } else if ((horiz_diff == 1) && (vert_diff == 0)) {
            isValidMovement = true;
        } else if ((horiz_diff == vert_diff) && (horiz_diff == 1)) {
            isValidMovement = true;
        }
        return isValidMovement;
    }

    public static void main(String[] args) { //arguments
        boolean white_turn = true;
        boolean black_turn = true;

        init();
        print_board();

        while (!((white_king()) || (black_king()))) {

            while (white_turn) {
                setOriginalRowAndColumn();

                if ((!original_boundary()) || (!whitecharecterchoosen())) {
                    System.out.println("Invalid Original Position");
                    continue;
                }

                setLastRowAndColumn();

                if(!(destination_boundary()) || !(board[lastRow][lastColumn] == '.')) {
                    System.out.println("Invalid Destination Position");
                    continue;
                }

                switch (board[originalRow][originalColumn]) {
                    case 'Q':
                        isValidMovement = diagonals() || vert_horiz();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'Q';
                        }

                        break;


                    case 'P':
                        isValidMovement = true;
                        isValidMovement = p_movement();

                        System.out.println(board[originalRow][originalColumn]);

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'P';
                        }
                        break;
                    case 'K':
                        isValidMovement = k_movement();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn]= 'K';
                        }
                        break;
                    case 'B':
                        isValidMovement = diagonals();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'B';
                        }
                        break;
                    case 'R':
                        isValidMovement = vert_horiz();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'R';
                        }
                        break;
                }

                if (isValidMovement) {
                    white_turn = false;
                }
            }

            print_board();


            while (black_turn) {
                setOriginalRowAndColumn();


                while((!original_boundary())||(blackcharecterchoosen())) {
                    System.out.println("Invalid Original Position");
                    continue;
                }

                setLastRowAndColumn();

                if(!(destination_boundary()) || !(board[lastRow][lastColumn] == '.')) {
                    System.out.println("Invalid Destination Position");
                    continue;
                }


                switch (board[originalRow][originalColumn]) {
                    case 'q':
                        isValidMovement = diagonals();
                        isValidMovement = vert_horiz();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'q';
                            black_turn = false;
                        }
                        break;

                    case 'p':
                        isValidMovement = black_p_movement();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'p';
                            black_turn = false;
                        }

                        break;
                    case 'k':
                        isValidMovement = k_movement();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'k';
                            black_turn = false;
                        }
                        break;
                    case 'b':
                        isValidMovement = diagonals();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'b';
                            black_turn = false;
                        }
                        break;
                    case 'r':
                        isValidMovement = vert_horiz();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'r';
                            black_turn = false;
                        }
                        break;
                }
            }
            black_turn = true;
        }
    }
}