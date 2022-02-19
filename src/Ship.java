public class Ship {
    private String name;
    private int cells;
    private boolean isShipSet = false;

    public Ship(String name, int cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public int getCells() {
        return cells;
    }

    public boolean isShipSet() {
        return isShipSet;
    }

    public void setShipSet(boolean shipSet) {
        isShipSet = shipSet;
    }
}
