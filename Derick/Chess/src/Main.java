import java.util.Scanner;

public class Main {
    public static char[][] board = new char[8][8];
    public static boolean isValidMovement = true;
    public static final char WHITE_KNIGHT = 'N'; // 변수 선언 및 초기화

    public static boolean white_king_moved = false;
    public static boolean black_king_moved = false;
    public static boolean white_rook_moved_right = false;
    public static boolean black_rook_moved_right = false;
    public static boolean white_rook_moved_left = false;
    public static boolean black_rook_moved_left = false;
    public static boolean white_turn = true;
    public static boolean black_turn = true;

    public static boolean[][] king_positions = new boolean[8][8];

    public static int king_row = 0;
    public static int king_column = 0;

    //boolean 값 양쪽 4개씩
    //if first turn
    //룩꺼 하나
    //킹 하나
    //다 false로 시작해서 코드에서 true로 바뀜, 룩하고 킹 무브먼트에서 바꿔줌(다 각각 한번 움직였는지 안 움직였는지 확인)
    //k movement 에서 isvalid movement가 팔스 버트 디퍼런스를 확인 하고 boolean 값을 바꿈
    //  저기서 트루로 움직임->킹이 움직인다 -> 운직일 것이다 -> 더이상 casting is impossible
    //오른쪽 rook 하고 왼쪽 rook 다른 boolean으로 만들어야함
    //vert and horiz에서 확인 + 퀸도 쓰니까 이프 스테이트먼트로 만들어서 퀸 하고 룩을 구분해야함
    public static void init() {
        int size = 8;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
//                if ((i == 0) && (j == 0 || j == 7)) {
//                    board[i][j] = 'r';
//                } else if ((i == 7) && (j == 1 || j == 6)) {
//                    board[i][j] = WHITE_KNIGHT;
//                    //WHITE_KNIGHT = 'N'
//                } else if ((i == 7) && (j == 2 || j == 5)) {
//                    board[i][j] = 'B';
//                } else if ((i == 7 && j == 3)) {
//                    board[i][j] = 'Q';
//                } else if ((i == 7 && j == 4)) {
//                    board[i][j] = 'K';
//                } else if (i == 1) {
//                    board[i][j] = 'p';
//                } else if ((i == 7) && (j == 0 || j == 7)) {
//                    board[i][j] = 'R';
//                } else if ((i == 0) && (j == 1 || j == 6)) {
//                    board[i][j] = 'n';
//                    //black Knight
//                } else if ((i == 0) && (j == 2 || j == 5)) {
//                    board[i][j] = 'b';
//                } else if (i == 0 && j == 3) {
//                    board[i][j] = 'q';
//                } else if (i == 0 && j == 4) {
//                    board[i][j] = 'k';
//                } else if (i == 6) {
//                    board[i][j] = 'P';
//                }
            }
        }

        board[3][3] = 'k';
        board[1][5] = 'B';
        board[1][2] = 'R';
        board[2][1] = 'R';
        board[4][5] = 'R';
        board[5][4] = 'R';
        board[7][7] = 'K';
    }

    public static char[] selectPiece() {
        System.out.print("Which piece will you move?: ");
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
                for (int i = 1; i < diff_column_C; i++) {
                    if ((board[originalRow + i][originalColumn + i]) != '.') {
                        isValidMovement = false;
                        System.out.println("It is an invalid movement");
                        break;
                    }
                }
            }
        } else {
            isValidMovement = false;
        }

        return isValidMovement;
    }

    public static boolean black_can_move() {
        switch (board[originalRow][originalColumn]) {
            case 'q':
                if (original_boundary(originalRow + 1, originalColumn) && (!blackcharecterchoosen(originalRow + 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && (!blackcharecterchoosen(originalRow - 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && (!blackcharecterchoosen(originalRow, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && (!blackcharecterchoosen(originalRow, originalColumn - 1))) {
                    return true;
                } else {
                    System.out.print("The queen can not move. ");
                    return false;
                }
                //boundary,
            case 'p':

                if (original_boundary(originalRow - 1, originalColumn) && (board[originalRow - 1][originalColumn] == '.')) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && whitecharecterchoosen(originalRow - 1, originalColumn - 1)) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && whitecharecterchoosen(originalRow - 1, originalColumn + 1)) {
                    return true;
                } else {
                    return false;
                }

            case 'k':
                if (original_boundary(originalRow + 1, originalColumn) && (!blackcharecterchoosen(originalRow + 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && (!blackcharecterchoosen(originalRow, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && (!blackcharecterchoosen(originalRow, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && (!blackcharecterchoosen(originalRow - 1, originalColumn))) {
                    return true;
                } else {
                    return false;
                }

            case 'r':
                if (original_boundary(originalRow + 1, originalColumn) && (!blackcharecterchoosen(originalRow + 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && (!blackcharecterchoosen(originalRow - 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && (!blackcharecterchoosen(originalRow, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && (!blackcharecterchoosen(originalRow, originalColumn + 1))) {
                    return true;
                } else {
                    return false;
                }

            case 'b':
                if (original_boundary(originalRow + 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && (!blackcharecterchoosen(originalRow + 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && (!blackcharecterchoosen(originalRow - 1, originalColumn + 1))) {
                    return true;
                } else {
                    return false;
                }

            case 'n':

                if (original_boundary(originalRow - 2, originalColumn - 1) && (!blackcharecterchoosen(originalRow - 2, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 2) && (!blackcharecterchoosen(originalRow - 1, originalColumn - 2))) {
                    return true;
                } else if (original_boundary(originalRow - 2, originalColumn + 1) && (!blackcharecterchoosen(originalRow - 2, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 2) && (!blackcharecterchoosen(originalRow - 1, originalColumn + 2))) {
                    return true;
                } else if (original_boundary(originalRow + 2, originalColumn - 1) && (!blackcharecterchoosen(originalRow + 2, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 2) && (!blackcharecterchoosen(originalRow + 1, originalColumn - 2))) {
                    return true;
                } else if (original_boundary(originalRow + 2, originalColumn + 1) && (!blackcharecterchoosen(originalRow + 2, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 2) && (!blackcharecterchoosen(originalRow + 1, originalColumn + 2))) {
                    return true;
                } else {
                    return false;
                }
        }

        return false;
    }

    public static boolean white_can_move() {
        switch (board[originalRow][originalColumn]) {
            case 'Q':
                if (original_boundary(originalRow + 1, originalColumn) && ((board[originalRow + 1][originalColumn] == '.') || (blackcharecterchoosen(originalRow + 1, originalColumn)))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && ((board[originalRow - 1][originalColumn] == '.') || (blackcharecterchoosen(originalRow - 1, originalColumn)))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && ((board[originalRow - 1][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && ((board[originalRow - 1][originalColumn + 1] == '.') || (blackcharecterchoosen(originalRow - 1, originalColumn + 1)))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && ((board[originalRow + 1][originalColumn - 1] == '.') || (blackcharecterchoosen(originalRow + 1, originalColumn - 1)))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 1) && ((board[originalRow + 1][originalColumn + 1] == '.') || (blackcharecterchoosen(originalRow + 1, originalColumn + 1)))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && ((board[originalRow][originalColumn + 1] == '.') || (blackcharecterchoosen(originalRow, originalColumn + 1)))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && ((board[originalRow][originalColumn - 1] == '.') || (blackcharecterchoosen(originalRow, originalColumn - 1)))) {
                    return true;
                } else {
                    System.out.print("The queen can not move. ");
                    return false;
                }
                //boundary,
            case 'P':

                if (original_boundary(originalRow - 1, originalColumn) && ((board[originalRow - 1][originalColumn] == '.'))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && blackcharecterchoosen(originalRow - 1, originalColumn - 1)) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && blackcharecterchoosen(originalRow - 1, originalColumn + 1)) {
                    return true;
                } else {
                    return false;
                }

            case 'K':
                if (original_boundary(originalRow + 1, originalColumn) && ((board[originalRow + 1][originalColumn] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && ((board[originalRow][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && ((board[originalRow][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 1) && ((board[originalRow + 1][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && ((board[originalRow - 1][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && ((board[originalRow - 1][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && ((board[originalRow + 1][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && ((board[originalRow - 1][originalColumn] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn))) {
                    return true;
                } else {
                    return false;
                }

            case 'R':
                if (original_boundary(originalRow + 1, originalColumn) && ((board[originalRow + 1][originalColumn] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn) && ((board[originalRow - 1][originalColumn] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn - 1) && ((board[originalRow][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow, originalColumn + 1) && ((board[originalRow][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow, originalColumn + 1))) {
                    return true;
                } else {
                    return false;
                }

            case 'B':
                if (original_boundary(originalRow + 1, originalColumn + 1) && ((board[originalRow + 1][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 1) && ((board[originalRow - 1][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 1) && ((board[originalRow + 1][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 1) && ((board[originalRow - 1][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn + 1))) {
                    return true;
                } else {
                    return false;
                }

            case 'N':

                if (original_boundary(originalRow - 2, originalColumn - 1) && ((board[originalRow - 2][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow - 2, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn - 2) && ((board[originalRow - 1][originalColumn - 2] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn - 2))) {
                    return true;
                } else if (original_boundary(originalRow - 2, originalColumn + 1) && ((board[originalRow - 2][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow - 2, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow - 1, originalColumn + 2) && ((board[originalRow - 1][originalColumn + 2] == '.') || blackcharecterchoosen(originalRow - 1, originalColumn + 2))) {
                    return true;
                } else if (original_boundary(originalRow + 2, originalColumn - 1) && ((board[originalRow + 2][originalColumn - 1] == '.') || blackcharecterchoosen(originalRow + 2, originalColumn - 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn - 2) && ((board[originalRow + 1][originalColumn - 2] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn - 2))) {
                    return true;
                } else if (original_boundary(originalRow + 2, originalColumn + 1) && ((board[originalRow + 2][originalColumn + 1] == '.') || blackcharecterchoosen(originalRow + 2, originalColumn + 1))) {
                    return true;
                } else if (original_boundary(originalRow + 1, originalColumn + 2) && ((board[originalRow + 1][originalColumn + 2] == '.') || blackcharecterchoosen(originalRow + 1, originalColumn + 2))) {
                    return true;
                } else {
                    return false;
                }
        }

        return false;
    }


    public static boolean vert_horiz() {

        isValidMovement = false;

        //세로

        if (originalRow == lastRow) {
            if (lastColumn > originalColumn) {
                for (int i = originalColumn + 1; i <= lastColumn; i++) {
                    if ((board[originalRow][i]) == '.') {
                        isValidMovement = true;
                        break;
                    }
                }
            } else if (lastColumn < originalColumn) {
                for (int i = originalColumn - 1; i >= lastColumn; i--) {
                    if ((board[originalRow][i]) == '.') {
                        isValidMovement = true;
                        break;
                    }
                }
            }

            ///가로
        } else if (originalColumn == lastColumn) {
            if (originalRow < lastRow) {
                for (int i = originalRow + 1; i <= lastRow; i++) {
                    if ((board[i][originalColumn]) == '.') {
                        isValidMovement = true;
                        break;
                    }
                }
            } else if (originalRow > lastRow) {
                for (int i = originalRow - 1; i >= lastRow; i--) {
                    if ((board[i][originalColumn]) == '.') {
                        isValidMovement = true;
                        break;
                    }
                }
            }
        }

        if (isValidMovement) {
            if (board[originalRow][originalColumn] == 'R') {
                if ((originalColumn == 7) && (originalRow == 7)) {
                    white_rook_moved_right = true;
                } else if ((originalColumn == 0) && (originalRow == 7)) {
                    white_rook_moved_left = true;
                }
            } else if (board[originalRow][originalColumn] == 'r') {
                if ((originalColumn == 7) && (originalRow == 0)) {
                    black_rook_moved_right = true;
                } else if ((originalColumn == 0) && (originalRow == 0)) {
                    black_rook_moved_left = true;
                }
            }
        }

        return isValidMovement;
    }

    public static boolean original_boundary(int a, int b) {
        return (a <= 7 && (a >= 0) && (b <= 7) && (b >= 0));
    }

    public static boolean canceling_white() {
        //둘다 원래 위치, 사이 비어있음, 2칸 사이, choosen by king
        isValidMovement = true;

        if (!((lastRow == 7) && ((lastColumn == 7) || (lastColumn == 0)))) {
            isValidMovement = false;
            return isValidMovement;
        }

        if ((board[7][4] == 'K') && ((board[7][7] == 'R') && (originalColumn < lastColumn))) {

            if ((board[originalRow][originalColumn + 1] != '.') || (board[originalRow][originalColumn + 2] != '.')) {
                isValidMovement = false;
            }
        } else if ((board[7][4] == 'K') && ((board[7][0] == 'R') && (originalColumn > lastColumn))) {
            if ((board[originalRow][originalColumn - 1] != '.') || (board[originalRow][originalColumn - 2] != '.') || (board[originalRow][originalColumn - 3] != '.')) {
                isValidMovement = false;
            }
        } else {
            isValidMovement = false;
        }

        if (!(isValidMovement)) {
            System.out.println("Invalid movement");
        }

        return isValidMovement;
    }

    public static boolean castling_black() {
        //둘다 원래 위치, 사이 비어있음, 2칸 사이, choosen by king
        isValidMovement = true;

        if (!((lastRow == 0) && ((lastColumn == 7) || (lastColumn == 0)))) {
            isValidMovement = false;
            return isValidMovement;
        }

        if ((board[0][4] == 'k') && ((board[0][7] == 'r') && (originalColumn < lastColumn))) {

            if ((board[originalRow][originalColumn + 1] != '.') || (board[originalRow][originalColumn + 2] != '.')) {
                isValidMovement = false;
            }
        } else if ((board[0][4] == 'k') && ((board[0][0] == 'r') && (originalColumn > lastColumn))) {
            if ((board[originalRow][originalColumn - 1] != '.') || (board[originalRow][originalColumn - 2] != '.') || (board[originalRow][originalColumn - 3] != '.')) {
                isValidMovement = false;
            }
        } else {
            isValidMovement = false;
        }

        if (board[lastRow][lastColumn] != 'r') {
            isValidMovement = false;
        }

        if (!(isValidMovement)) {
            System.out.println("Invalid movement");
        }

        return isValidMovement;
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

    public static boolean whitecharecterchoosen(int row, int column) {
        boolean white_character = false;
        if ((board[row][column]) == 'K') {
            white_character = true;
        } else if ((board[row][column]) == 'Q') {
            white_character = true;
        } else if ((board[row][column]) == 'P') {
            white_character = true;
        } else if ((board[row][column]) == 'B') {
            white_character = true;
        } else if ((board[row][column]) == 'N') {
            white_character = true;
        } else if ((board[row][column]) == 'R') {
            white_character = true;
        }

        return white_character;
    }

    public static boolean teamKill_prevent_white() {
        boolean white_character = false;
        if ((board[lastRow][lastColumn]) == 'K') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'Q') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'P') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'B') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'N') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'R') {
            white_character = true;
        }
        return white_character;
    }

    public static boolean blackcharecterchoosen() {
        boolean black_character = false;
        if ((board[originalRow][originalColumn]) == 'k') {
            black_character = true;
        } else if ((board[originalRow][originalColumn]) == 'q') {
            black_character = true;
        } else if ((board[originalRow][originalColumn]) == 'p') {
            black_character = true;
        } else if ((board[originalRow][originalColumn]) == 'b') {
            black_character = true;
        } else if ((board[originalRow][originalColumn]) == 'n') {
            black_character = true;
        } else if ((board[originalRow][originalColumn]) == 'r') {
            black_character = true;
        }
        return black_character;
    }

    public static boolean blackcharecterchoosen(int row, int column) {
        boolean black_character = false;
        if ((board[row][column]) == 'k') {
            black_character = true;
        } else if ((board[row][column]) == 'q') {
            black_character = true;
        } else if ((board[row][column]) == 'p') {
            black_character = true;
        } else if ((board[row][column]) == 'b') {
            black_character = true;
        } else if ((board[row][column]) == 'n') {
            black_character = true;
        } else if ((board[row][column]) == 'r') {
            black_character = true;
        }
        return black_character;
    }

    public static boolean teamKill_prevent_black() {
        boolean white_character = false;
        if ((board[lastRow][lastColumn]) == 'k') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'q') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'p') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'b') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'n') {
            white_character = true;
        } else if ((board[lastRow][lastColumn]) == 'r') {
            white_character = true;
        }

        return white_character;
    }

    public static boolean black_king() {
        if (black_king_checkmated()) {
            print_board();
            System.out.println("Black won the game");
            return true;
        }else{
            return false;
        }
    }

    public static boolean knight_movement() {
        isValidMovement = false;
        int horiz_diff = Math.abs(originalRow - lastRow);
        int vert_diff = Math.abs(originalColumn - lastColumn);
        if ((horiz_diff == 2) && (vert_diff == 1)) {
            isValidMovement = true;
        } else if ((vert_diff == 2) && (horiz_diff == 1)) {
            isValidMovement = true;
        }
        return isValidMovement;
    }

    public static boolean black_king(boolean isblackkingdead) {
        if (isblackkingdead) {
            return true;
        }
        boolean black_dead = true;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == 'k') {
                    black_dead = false;
                    break;
                }
            }
        }

        if (black_dead) {
            System.out.println("White wins the game");
        }
        return black_dead;
    }

    public static boolean diag_pawn_white() {
        isValidMovement = false;
        int row_diff = lastRow - originalRow;
        int col_diff = Math.abs(lastColumn - originalColumn);
        if (((row_diff == -1) && (col_diff == 1)) && (teamKill_prevent_black())) {
            isValidMovement = true;
        }

        return isValidMovement;
    }

    public static boolean diag_pawn_black() {
        isValidMovement = false;
        int row_diff = lastRow - originalRow;
        int col_diff = Math.abs(lastColumn - originalColumn);
        if (((row_diff == 1) && (col_diff == 1)) && (teamKill_prevent_white())) {
            isValidMovement = true;
        }

        return isValidMovement;
    }

    public static boolean p_movement() {
        isValidMovement = false;
        if ((board[lastRow][lastColumn]) == '.') {
            if (originalColumn == lastColumn) {
                if (board[originalRow][originalColumn] == 'P' && ((originalRow - lastRow) == 1) || (originalRow - lastRow) == 2) {
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

    public static boolean p_movement_start() {
        int vert_movement = Math.abs(originalRow - lastRow);

        if (!isValidMovement) {
            isValidMovement = ((originalRow == 6) || (originalRow == 1)) && (vert_movement == 2);
        }
        return isValidMovement;
    }

    public static boolean black_p_movement() {
        isValidMovement = false;
        if ((board[lastRow][lastColumn]) == '.') {
            if (originalColumn == lastColumn) {
                if (board[originalRow][originalColumn] == 'p' && ((originalRow - lastRow) == -1) || (originalRow - lastRow) == -2) {
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

    public static char white_pawn_tranform() {
        System.out.print("reached limit, 당신은 끝에 도달하였습니다, 원하는 기물(피스)를 정해주세요~(영어): ");
        char[] tranformor = takeUserInput();
        while (!((tranformor.length == 1) && ((tranformor[0] == 'Q' || tranformor[0] == 'N' || tranformor[0] == 'B' || tranformor[0] == 'R')))) {
            System.out.println("Invalid transformation");
            System.out.println("원하는 기물(피스)를 정해주세요~(영어): ");
            tranformor = takeUserInput();
        }
        return tranformor[0];
    }

    public static char black_pawn_tranform() {
        System.out.print("reached limit, 당신은 끝에 도달하였습니다, 원하는 기물(피스)를 정해주세요~(영어): ");
        char[] tranformor = takeUserInput();
        while (!((tranformor.length == 1) && ((tranformor[0] == 'q' || tranformor[0] == 'n' || tranformor[0] == 'b' || tranformor[0] == 'r')))) {
            System.out.println("Invalid transformation");
            System.out.println("원하는 기물(피스)를 정해주세요~(영어): ");
            tranformor = takeUserInput();
        }
        return tranformor[0];
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
        if ((isValidMovement) && (board[originalRow][originalColumn] == 'K')) {
            white_king_moved = true;
        } else if ((isValidMovement) && (board[originalRow][originalColumn] == 'k')) {
            black_king_moved = true;
        }
        return isValidMovement;
    }

    public static void king_position() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[y][x] == 'k') {
                    king_row = y;
                    king_column = x;
                    if (y == 0) {
                        king_positions[0][0] = false;
                        king_positions[0][1] = false;
                        king_positions[0][2] = false;
                    } else if (y == 7) {
                        king_positions[2][0] = false;
                        king_positions[2][1] = false;
                        king_positions[2][2] = false;
                    }

                    if (x == 0) {
                        king_positions[0][0] = false;
                        king_positions[1][0] = false;
                        king_positions[2][0] = false;
                    } else if (x == 7) {
                        king_positions[0][2] = false;
                        king_positions[1][2] = false;
                        king_positions[2][2] = false;
                    }
                }
            }
        }
    }

    public static void opponents() {
        for (int w = 0; w < 8; w++) {
            for (int z = 0; z < 8; z++) {
                king_positions[w][z] = true;
            }
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                switch (board[x][y]) {
                    case 'P' -> {
                        int P_row = x;
                        int P_column = y;
                        if (original_boundary(P_row - 1, P_column + 1)&&((board[P_row - 1][P_column + 1] == '.')||(board[P_row - 1][P_column + 1] == 'k'))){
                            king_positions[P_row - 1][P_column + 1] = false;
                        }
                        if(original_boundary(P_row - 1, P_column - 1)&&((board[P_row - 1][P_column - 1] == '.'||(board[P_row - 1][P_column - 1] == 'k')))){
                            king_positions[P_row - 1][P_column - 1] = false;
                        }
                    }
                    case 'Q' -> {
                        int R_row = x;
                        int R_column = y;
                        boolean behind = true;
                        for (int a = 1; R_row + a < 8; a++) {
                            if (((board[R_row + a][R_column] == '.')||(board[R_row + a][R_column] == 'k')) && (behind)) {
                                king_positions[R_row + a][R_column] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_row - a > -1; a++) {
                            if (((board[R_row - a][R_column] == '.')||(board[R_row - a][R_column] == 'k')) && (behind)) {
                                king_positions[R_row - a][R_column] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_column + a < 8; a++) {
                            if (((board[R_row][R_column + a] == '.')||(board[R_row][R_column + a] == 'k')) && (behind)) {
                                king_positions[R_row][R_column + a] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_column - a > -1; a++) {
                            if (((board[R_row][R_column - a] == '.')||(board[R_row][R_column - a] == 'k')) && (behind)) {
                                king_positions[R_row][R_column - a] = false;
                            } else {
                                behind = false;
                            }
                        }

                        int B_row = x;
                        int B_column = y;
                        //++, --, +-, -+
                        behind = true;
                        int s = 1;
                        while ((B_row + s < 8) && (B_column + s < 8)) {
                            if (((board[B_row + s][B_column + s] == '.')||(board[B_row + s][B_column + s] == 'k')) && (behind)) {
                                king_positions[B_row + s][B_column + s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }
                        behind = true;
                        s = 1;
                        while ((B_row - s > -1) && (B_column + s < 8)) {
                            if (((board[B_row - s][B_column + s] == '.')||(board[B_row - s][B_column + s] == 'k')) && (behind)) {
                                king_positions[B_row - s][B_column + s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }

                        behind = true;
                        s = 1;
                        while ((B_row + s < 8) && (B_column - s > -1)) {
                            if (((board[B_row + s][B_column - s] == '.')||(board[B_row + s][B_column - s] == 'k')) && (behind)) {
                                king_positions[B_row + s][B_column - s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }


                        behind = true;
                        s = 1;
                        while ((B_row - s > -1) && (B_column - s > -1)) {
                            if (((board[B_row - s][B_column - s] == '.')||(board[B_row - s][B_column - s] == 'k')) && (behind)) {
                                king_positions[B_row - s][B_column - s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }

                    }
                    case 'R' -> {
                        int R_row = x;
                        int R_column = y;
                        boolean behind = true;
                        for (int a = 1; R_row + a < 8; a++) {
                            if (((board[R_row + a][R_column] == '.')||(board[R_row + a][R_column] == 'k')) && (behind)) {
                                king_positions[R_row + a][R_column] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_row - a > -1; a++) {
                            if (((board[R_row - a][R_column] == '.')||(board[R_row - a][R_column] == 'k')) && (behind)) {
                                king_positions[R_row - a][R_column] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_column + a < 8; a++) {
                            if (((board[R_row][R_column + a] == '.')||(board[R_row][R_column + a] == 'k')) && (behind)) {
                                king_positions[R_row][R_column + a] = false;
                            } else {
                                behind = false;
                            }
                        }
                        behind = true;
                        for (int a = 1; R_column - a > -1; a++) {
                            if (((board[R_row][R_column - a] == '.')||(board[R_row][R_column - a] == 'k')) && (behind)) {
                                king_positions[R_row][R_column - a] = false;
                            } else {
                                behind = false;
                            }
                        }
                    }
                    case 'B' -> {
                        int B_row = x;
                        int B_column = y;
                        //++, --, +-, -+
                        boolean behind = true;
                        int s = 1;
                        while ((B_row + s < 8) && (B_column + s < 8)) {
                            if (((board[B_row + s][B_column + s] == '.')||(board[B_row + s][B_column + s] == 'k')) && (behind)) {
                                king_positions[B_row + s][B_column + s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }
                        behind = true;
                        s = 1;
                        while ((B_row - s > -1) && (B_column + s < 8)) {
                            if (((board[B_row - s][B_column + s] == '.')||(board[B_row - s][B_column + s] == 'k')) && (behind)) {
                                king_positions[B_row - s][B_column + s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }

                        behind = true;
                        s = 1;
                        while ((B_row + s < 8) && (B_column - s > -1)) {
                            if (((board[B_row + s][B_column - s] == '.')||(board[B_row + s][B_column - s] == 'k')) && (behind)) {
                                king_positions[B_row + s][B_column - s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }


                        behind = true;
                        s = 1;
                        while ((B_row - s > -1) && (B_column - s > -1)) {
                            if (((board[B_row - s][B_column - s] == '.')||(board[B_row - s][B_column - s] == 'k')) && (behind)) {
                                king_positions[B_row - s][B_column - s] = false;
                            } else {
                                behind = false;
                            }
                            s++;
                        }
                    }
                    case 'N' -> {
                        int N_row = x;
                        int N_column = y;
                        if((original_boundary(N_row + 2, N_column +1)&&((board[N_row + 2][N_column +1] == '.')||(board[N_row + 2][N_column +1] == 'k')))){
                            king_positions[N_row + 2][N_column +1] = false;
                        }
                        if((original_boundary(N_row + 2, N_column -1)&&((board[N_row + 2][N_column -1] == '.')||(board[N_row + 2][N_column -1] == 'k')))){
                            king_positions[N_row + 2][N_column -1] = false;
                        }
                        if((original_boundary(N_row - 2, N_column +1)&&((board[N_row - 2][N_column +1] == '.')||(board[N_row - 2][N_column +1] == 'k')))){
                            king_positions[N_row - 2][N_column +1] = false;
                        }
                        if((original_boundary(N_row - 2, N_column -1)&&((board[N_row - 2][N_column -1] == '.')||(board[N_row - 2][N_column -1] == 'k')))){
                            king_positions[N_row - 2][N_column -1] = false;
                        }
                        if((original_boundary(N_row + 1, N_column +2)&&((board[N_row + 1][N_column +2] == '.')||(board[N_row + 1][N_column +2] == 'k')))){
                            king_positions[N_row + 1][N_column +2] = false;
                        }
                        if((original_boundary(N_row - 1, N_column +2)&&((board[N_row - 1][N_column +2] == '.')||(board[N_row - 1][N_column +2] == 'k')))){
                            king_positions[N_row - 1][N_column +2] = false;
                        }
                        if((original_boundary(N_row  - 1, N_column -2)&&((board[N_row -1][N_column -2] == '.')||(board[N_row - 2][N_column - 2] == 'k')))){
                            king_positions[N_row - 1][N_column -2] = false;
                        }
                        if((original_boundary(N_row + 1, N_column - 2)&&((board[N_row +1][N_column -2] == '.')||(board[N_row + 1][N_column - 2] == 'k')))){
                            king_positions[N_row + 1][N_column - 2] = false;
                        }
                    }
                }
            }
        }
        for (int q = 0; q < 8; q++) {
            for (int w = 0; w < 8; w++) {
                if (king_positions[q][w]) {
                    System.out.print(" T ");
                } else {
                    System.out.print(" F ");
                }
            }
            System.out.println(" ");
        }
    }

    public static boolean black_king_checkmated() {
        boolean Checkmate = true;
        for(int x = 0; x<8; x++){
            for(int y = 0; y<8; y++){
                if(board[y][x] == 'k'){
                    for (int z = -1; z<2; z++){
                        for (int q = -1; q<2; q++){
                            if((original_boundary(y + z, x + q))&&(king_positions[y + z][x + q])){
                                Checkmate = false;
                            }
                        }
                    }
                }
            }
        }
        return Checkmate;
    }

    public static void main(String[] args) { //arguments
        boolean black_k_dead = false;
        boolean white_K_dead = false;

        init();
        opponents();

        while (!((black_king()) || (black_king(black_k_dead)))) {
            print_board();
            while (white_turn) {
                setOriginalRowAndColumn();
                boolean can_move = white_can_move();


                if ((!original_boundary(originalRow, originalColumn)) || (!whitecharecterchoosen()) || (!can_move)) {
                    System.out.println("Invalid Original Position");
                    continue;
                }

                setLastRowAndColumn();

                if ((!((destination_boundary()) && (board[originalRow][originalColumn] == 'K'))) && (!(destination_boundary()) || teamKill_prevent_white())) {
                    System.out.println("Invalid movement, please type again: ");
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
                        isValidMovement = p_movement() || p_movement_start() || diag_pawn_white();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'P';
                            if (lastRow == 0) {
                                board[lastRow][lastColumn] = white_pawn_tranform();
                            }
                        }
                        break;

                    case 'K':
                        isValidMovement = k_movement();

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'K';
                        } else {
                            boolean canceling_allowed = !((white_king_moved) || (white_rook_moved_left) || (white_rook_moved_right));

                            if (canceling_white() && (canceling_allowed)) {
                                if (originalColumn > lastColumn) {
                                    board[originalRow][originalColumn] = '.';
                                    board[lastRow][lastColumn] = '.';
                                    board[7][1] = 'K';
                                    board[7][2] = 'R';
                                } else {
                                    board[originalRow][originalColumn] = '.';
                                    board[7][7] = '.';
                                    board[7][6] = 'K';
                                    board[7][5] = 'R';
                                }
                            }
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
                    case 'N':
                        isValidMovement = knight_movement();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'N';
                        } else {
                            System.out.println("Knight movement Failure");
                        }
                }

                if (isValidMovement) {
                    white_turn = false;
                    black_turn = true;
                }
            }

            print_board();

            if (black_king(black_k_dead)) {
                black_k_dead = true;
                black_turn = false;
            }

            while (black_turn) {
                setOriginalRowAndColumn();
                boolean can_move = black_can_move();

                if ((!original_boundary(originalRow, originalColumn)) || (!blackcharecterchoosen()) || (!can_move)) {
                    System.out.println("Invalid Original Position");
                    continue;
                }

                setLastRowAndColumn();

                if ((!destination_boundary()) && (teamKill_prevent_black()) && (!((board[lastRow][lastColumn] == 'r') && (board[originalRow][originalColumn] == 'k')))) {
                    //!destination_boundary
                    //teamkill_prevent_black
                    //last position is not r
                    System.out.println("Invalid Destination Position");
                    continue;
                }


                switch (board[originalRow][originalColumn]) {
                    case 'q':
                        isValidMovement = (diagonals()) || (vert_horiz());

                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'q';
                            black_turn = false;
                        }
                        break;

                    case 'p':
                        isValidMovement = black_p_movement() || p_movement_start() || diag_pawn_black();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'p';
                            black_turn = false;
                            if (lastRow == 7) {
                                board[lastRow][lastColumn] = black_pawn_tranform();
                            }
                        }
                        break;
                    case 'k':

                        boolean canceling_allowed = !((black_king_moved) || (black_rook_moved_left) || (black_rook_moved_right));

                        if ((castling_black()) && (canceling_allowed)) {
                            if (originalColumn > lastColumn) {
                                board[originalRow][originalColumn] = '.';
                                board[lastRow][lastColumn] = '.';
                                board[0][1] = 'k';
                                board[0][2] = 'r';
                            } else {
                                board[originalRow][originalColumn] = '.';
                                board[0][7] = '.';
                                board[0][6] = 'k';
                                board[0][5] = 'r';
                            }
                        }

                        print_board();

                        if (!canceling_allowed) {
                            isValidMovement = k_movement();

                            if (isValidMovement) {
                                board[originalRow][originalColumn] = '.';
                                board[lastRow][lastColumn] = 'K';
                            }
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
                    case 'n':
                        isValidMovement = knight_movement();
                        if (isValidMovement) {
                            board[originalRow][originalColumn] = '.';
                            board[lastRow][lastColumn] = 'n';
                        }
                }
                if (isValidMovement) {
                    black_turn = false;
                    white_turn = true;
                }
            }
        }
    }
}