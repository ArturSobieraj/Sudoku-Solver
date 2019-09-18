package gameLogic;

import com.sun.tools.jdeprscan.scan.Scan;
import table.SudokuBoard;

import java.util.Scanner;

public class ValuesSetter {

    private boolean isSudokuTyped = false;
    private Scanner scan = new Scanner(System.in);

    public boolean getIsSudokuTyped() {
        return isSudokuTyped;
    }

    public SudokuBoard setValue(SudokuBoard board) {
        System.out.println("Enter coordinates and value: ");
        String coordinates = scan.nextLine();
        if (coordinates.equals("sudoku")) {
            isSudokuTyped = true;
        } else {
            if (coordinates.length() != 3) {
                throw new IllegalStateException("Bad coordinates format");
            }
            int x = Integer.parseInt(coordinates.substring(0, 1));
            int y = Integer.parseInt(coordinates.substring(1, 2));
            String z = coordinates.substring(2, 3);
            board.getRow(x - 1).getElement(y - 1).setValue(z);
            board.getRow(x - 1).getElement(y - 1).getPossibleNumbers().clear();
        }
        return board;
    }

    public boolean endGameQuestion() {
        System.out.println("Would you like to solve another sudoku?\n1 - yes\nelse - no");
        String answer = scan.nextLine();
        return answer.equals("1");
    }
}
