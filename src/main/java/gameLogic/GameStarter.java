package gameLogic;

import table.SudokuBoard;
import table.SudokuElement;
import table.SudokuRow;

public class GameStarter {
    private ValuesSetter valuesSetter = new ValuesSetter();

    public boolean startSudoku(SudokuBoard board) {
        System.out.println("Enter the cell values as shown: \nxyz\n\nx - row coordinate\ny - column coordinate\n" +
                "z - value\n\nAfter typing \"sudoku\" system will resolve your sudoku game.");
        boolean isSudokuTyped = false;
        while (!isSudokuTyped) {
            board = valuesSetter.setValue(board);
            System.out.println(board);
            isSudokuTyped = valuesSetter.getIsSudokuTyped();
        }
        SudokuSolver solver = new SudokuSolver();
        boolean answer = false;
        if (solver.solveSudoku(board)){
            answer = valuesSetter.endGameQuestion();
        }
        return answer;
    }

    public SudokuBoard constructStarterBoard() {
        SudokuBoard board = new SudokuBoard();
        for (int i = 0; i < 9; i++) {
            SudokuRow sudokuRow = new SudokuRow();
            for (int j = 0; j < 9; j++) {
                SudokuElement sudokuElement = new SudokuElement();
                for (int z = 0; z < 9; z++) {
                    sudokuElement.getPossibleNumbers().add(Integer.toString(z + 1));
                }
                sudokuRow.getSudokuRow().add(sudokuElement);
            }
            board.getSudokuBoard().add(sudokuRow);
        }
        System.out.println(board);
        return board;
    }
}
