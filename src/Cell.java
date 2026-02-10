import javax.swing.*;

public class Cell {
    JButton button;
    Vector2 cellPos;

    public Cell(JButton button, Vector2 cellPos) {
        this.button = button;
        this.cellPos = cellPos;
    }
}
