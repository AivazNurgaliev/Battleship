public class Game {
    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    public static void main(String[] args) {
        Grid userGrid = new Grid();
        Grid enemyFogGrid = new Grid();
        Grid enemyGrid = new Grid();
        Grid userFogGrid = new Grid();
        Player user = new Player("Player 1", userGrid, enemyFogGrid);
        Player enemy = new Player("Player 2", enemyGrid, userFogGrid);
        game(user, enemy);
    }

    public static void game(Player user, Player enemy) {
        user.inputShips();
        enemy.inputShips();
        while (!user.getPlayerGrid().isAllShipSunk() && !enemy.getPlayerGrid().isAllShipSunk()) {
            user.currentTurn(enemy);
            enemy.currentTurn(user);
        }
        if (user.getPlayerGrid().gameOver()) {
            System.out.printf("\nYou sank the last ship. You won. Congratulations!\n");
        } else {
            System.out.printf("\nYou sank the last ship. You won. Congratulations!\n");

        }
    }
}
