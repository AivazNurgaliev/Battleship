public class Grid {

    private final int ROWS = 10;
    private final int COLUMNS = 10;
    private char[][] battleGrid;
    private boolean isOkay = false;
    private int countShip = 0;
    private int numberOfHitCells = 0;
    private int numberOfSankShips = 0;
    private boolean isValidFlag = false;

    public boolean isValidFlag() {
        return isValidFlag;
    }

    public Grid() {
        battleGrid = new char[ROWS][COLUMNS];
        for (int i = 0; i < battleGrid.length; i++) {
            for (int j = 0; j < battleGrid[i].length; j++) {
                battleGrid[i][j] = '~';
            }
        }
    }

    public int getNumberOfSankShips() {
        return numberOfSankShips;
    }

    public int getCountShip() {
        return countShip;
    }

    public int getNumberOfHitCells() {
        return numberOfHitCells;
    }

    public boolean isOkay() {
        return isOkay;
    }

    public void printGrid() {
        System.out.println();
        char startLetter = 'A';
        for (int i = 1; i <= battleGrid.length; i++) {
            if (i == 1) System.out.print("  ");
            else if (i <= 10) System.out.print(" ");
            System.out.print(i);
        }
        System.out.println();
        for (int i = 0; i < battleGrid.length; i++) {
            System.out.print(startLetter++ + " ");
            for (int j = 0; j < battleGrid[i].length; j++) {
                System.out.print(battleGrid[i][j]);
                if (j != battleGrid[i].length - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    public boolean isShipSunk(int row, int col) {
        if (row < battleGrid.length && row >= 0 && col >= 0 && col < battleGrid.length) {
            if (battleGrid[row][col] == 'M') {
                return false;
            }
            if (battleGrid[row][col] == 'X') {
                if (row + 1 < battleGrid.length && battleGrid[row + 1][col] == 'O') return false;
                if (row - 1 >= 0 && battleGrid[row - 1][col] == 'O') return false;
                if (col + 1 < battleGrid.length && battleGrid[row][col + 1] == 'O') return false;
                if (col - 1 >= 0 && battleGrid[row][col - 1] == 'O') return false;
            }
        }
        ++numberOfSankShips;
        return true;
    }

    public boolean gameOver() {
        if (isAllShipSunk()) {
            return true;
        }
        return false;
    }

    public boolean isAllShipSunk() {
        boolean flag = true;
        for (int i = 0; i < battleGrid.length; i++) {
            for (int j = 0; j < battleGrid.length; j++) {
                if (battleGrid[i][j] == 'O') {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public void updateGridAfterHit(int row, int col) {
        if (row >= 0 && row <= battleGrid.length && col >= 0 && col <= battleGrid.length) {
            if (battleGrid[row][col] == 'O' || battleGrid[row][col] == 'X') {
                battleGrid[row][col] = 'X';
                numberOfHitCells++;
            } else {
                battleGrid[row][col] = 'M';
            }
        }
    }

    public boolean isHit(int row, int col) {
        if (battleGrid[row][col] == 'X') {
            return true;
        }
        return false;
    }

    public void updateGridAfterHit(Grid grid, int row, int col) {
        if (row >= 0 && row <= grid.battleGrid.length && col >= 0 && col <= grid.battleGrid.length) {
            if (grid.battleGrid[row][col] == 'O' || grid.battleGrid[row][col] == 'X') {
                battleGrid[row][col] = 'X';
                grid.numberOfHitCells++;
            } else {
                battleGrid[row][col] = 'M';
                System.out.println("\nYou missed!\n");
            }
        } else {
        }
    }

    public void updateGrid(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (isValid(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            if (firstRow == secondRow) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstColumn, secondColumn); j <= Math.max(firstColumn, secondColumn); j++) {
                        battleGrid[firstRow][j] = 'O';
                    }
                }
            } else if (firstColumn == secondColumn) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstRow, secondRow); j <= Math.max(firstRow, secondRow); j++) {
                        battleGrid[j][firstColumn] = 'O';
                    }
                }
            }
            ship.setShipSet(true);
            countShip++;
        }
    }

    public boolean isRightLength(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (firstRow == secondRow || firstColumn == secondColumn) {
            if (firstColumn > secondColumn && Math.abs(firstColumn - secondColumn + 1) != ship.getCells()) return true;
            if (firstColumn < secondColumn && Math.abs(firstColumn - secondColumn - 1) != ship.getCells()) return true;
            if (firstRow > secondRow && Math.abs(firstRow - secondRow + 1) != ship.getCells()) return true;
            if (firstRow < secondRow && Math.abs(firstRow - secondRow - 1) != ship.getCells()) return true;
        }
        return false;
    }

    public boolean isValid(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (isRightLength(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            System.out.print("\nError! Wrong length of the Submarine! Try again: ");
            isValidFlag = false;
            return false;
        } else if (!isClose(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            System.out.print("\nError! You placed it too close to another one. Try again: ");
            isValidFlag = false;
            return false;
        } else if ((firstColumn == secondColumn || firstRow == secondRow)
                && isClose(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            isValidFlag = true;
            return true;
        } else {
            System.out.print("\nError! Wrong ship location! Try again: ");
            isValidFlag = false;
            return false;
        }
    }

    public boolean isClose(Ship ship, int firstRow,
                           int firstColumn, int secondRow, int secondColumn) {
        if(firstRow >= 0 && firstRow <= battleGrid.length && secondRow >= 0 && secondColumn <= battleGrid.length) {
            if (firstRow == secondRow) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstColumn, secondColumn); j <= Math.max(firstColumn, secondColumn); j++) {
                        //check if is close to ship, '0'; // check if IndexOutOfBounds occurs!!!
                        if ((firstRow - 1 >= 0) && battleGrid[firstRow - 1][j] == 'O') return false;
                        if ((firstRow + 1 < battleGrid.length) && battleGrid[firstRow + 1][j] == 'O') return false;
                        if ((j - 1) >= 0 && battleGrid[firstRow][j - 1] == 'O') return false;
                        if ((j + 1) < battleGrid.length && battleGrid[firstRow][j + 1] == 'O') return false;
                    }
                }
                return true;
            } else {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstRow, secondRow); j <= Math.max(firstRow, secondRow); j++) {
                        if ((firstColumn - 1 >= 0) && battleGrid[j][firstColumn - 1] == 'O') return false;
                        if ((firstColumn + 1 < battleGrid.length) && battleGrid[j][firstColumn + 1] == 'O') return false;
                        if ((j - 1) >= 0 && battleGrid[j - 1][firstColumn] == 'O') return false;
                        if ((j + 1) < battleGrid.length && battleGrid[j + 1][firstColumn] == 'O') return false;
                    }
                }
                return  true;
            }
        } else {
            System.out.println("Error the coordinates are out of bounds of the grid! Try again: " + firstRow + " " + firstColumn
                    + " " + secondRow + " " + secondColumn);
            return false;
        }
    }
}