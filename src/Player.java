import java.util.Scanner;

public class Player {

    private String name;
    private Grid playerGrid;
    private Grid enemyFogGrid;
    private Ship[] ship;
    public Player(String name, Grid playerGrid, Grid enemyFogGrid) {
        this.name = name;
        this.playerGrid = playerGrid;
        this.enemyFogGrid = enemyFogGrid;
        ship = new Ship[5];
        ship[0] = new Ship("Aircraft Carrier", 5);
        ship[1] = new Ship("Battleship", 4);
        ship[2] = new Ship("Submarine", 3);
        ship[3] = new Ship("Cruiser", 3);
        ship[4] = new Ship("Destroyer", 2);
    }

    public void printGrids() {
        enemyFogGrid.printGrid();
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        playerGrid.printGrid();
    }

    public void inputShips() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s, place your ships on the game field\n", getName());
        playerGrid.printGrid();
        int firstRow = -1;
        int firstColumn = -1;
        int secondRow = - 1;
        int secondColumn = -1;
        int i = 0;
        System.out.printf("\nEnter the coordinates of the %s (%d cells): ", ship[i].getName(), ship[i].getCells());
        while(playerGrid.getCountShip() != 5) {
            try {
                if (i > 0 && ship[i - 1].isShipSet() && playerGrid.isValidFlag()) {
                    System.out.printf("\nEnter the coordinates of the %s (%d cells): \n", ship[i].getName(), ship[i].getCells());
                }
                String firstCoordinate = scanner.next();
                firstCoordinate = firstCoordinate.toUpperCase();
                firstRow = firstCoordinate.charAt(0) - 65;
                String temp = firstCoordinate.substring(1);
                firstColumn = Integer.parseInt(temp);
                String secondCoordinate = scanner.next();
                secondCoordinate = secondCoordinate.toUpperCase();
                secondRow = secondCoordinate.charAt(0) - 65;
                String tmp = secondCoordinate.substring(1);
                secondColumn = Integer.parseInt(tmp);
                playerGrid.updateGrid(ship[i], firstRow, firstColumn - 1, secondRow, secondColumn - 1);
                if (playerGrid.isValidFlag()) {
                    playerGrid.printGrid();
                }
                if (ship[i].isShipSet()) {
                    ++i;
                }
            } catch (RuntimeException e) {
                continue;
            }

        }
        String str = "-a";
        while (str.length() != 0) {
            System.out.println("Press Enter and pass the move to another player: ");
            scanner.nextLine();
            str = scanner.nextLine();
        }
        System.out.println("\n".repeat(50));
    }

    public boolean isCorrectCoordinates(String str) {
        if (str != null && Character.isLetter(str.charAt(0))) {
            char c = str.charAt(0);
            if (!(c >= 65 && c <= 74)) return false;
        }
        if (str == null) {
            return false;
        } else if (!Character.isLetter(str.charAt(0))) {
            return false;
        } else if (!Character.isDigit(str.charAt(1))) {
            return false;
        }
        return true;
    }

    //P1 ATTACK P2 IS CURRENTTURN(P2) AND VICE VERSA
    public void currentTurn(Player player2) {
        Scanner scanner = new Scanner(System.in);
        printGrids();
        System.out.printf("%s, it's your turn: \n", getName());
        String str = "a";
        str = scanner.next();
        try {
            int row = str.charAt(0) - 65;
            int col = Integer.parseInt(str.substring(1));
            enemyFogGrid.updateGridAfterHit(player2.playerGrid, row, col - 1);
            player2.playerGrid.updateGridAfterHit(row, col - 1);
            if (player2.playerGrid.isHit(row, col - 1) && !player2.playerGrid.isShipSunk(row, col - 1)) {
                System.out.println("\nYou hit a ship!\n");
            }
            if (player2.playerGrid.isShipSunk(row, col - 1) && !player2.playerGrid.gameOver()) {
                System.out.println("\nYou sank a ship! Specify a new target: \n");
            }
        } catch (RuntimeException e) {
            //continue;
        }
        String checkEnter = "a";
        while (checkEnter.length() != 0 && !playerGrid.gameOver() && !player2.getPlayerGrid().gameOver()) {
            System.out.println("Press Enter and pass the move to another player: ");
            scanner.nextLine();
            checkEnter = scanner.nextLine();
        }
        if (!playerGrid.gameOver() && !player2.getPlayerGrid().gameOver()) {
            System.out.println("\n".repeat(50));
        }
    }

    public String getName() {
        return name;
    }

    public Grid getPlayerGrid() {
        return playerGrid;
    }

    public Grid getEnemyFogGrid() {
        return enemyFogGrid;
    }
}
