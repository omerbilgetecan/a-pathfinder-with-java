import javax.swing.*;
import java.awt.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int ROW = 15;
    public static final int COL = 15;


    public static int startPosSelection = 1;
    public static JButton startCell;
    public static JButton endCell;
    public static final Color startColor = Color.green;
    public static final Color endColor = Color.red;
    public static final Color defColor = Color.darkGray;
    public static final Color routeColor = Color.BLUE;


    public static Cell startPos;
    public static Cell endPos ;
    public static ArrayList<Cell> gridMap = new ArrayList<>();
    public static ArrayList<Cell> routePos = new ArrayList();

    public static JFrame setWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 810);
        frame.setVisible(true);

        JPanel topBar = new JPanel();
        topBar.setBackground(Color.CYAN);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            for (int i = 0; i < gridMap.size(); i++) {
                gridMap.get(i).button.setBackground(defColor);
                gridMap.get(i).button.setText("");
            }
            startCell = null;
            endCell = null;
            startPosSelection = 1;
        });
        topBar.add(resetButton);
        frame.setLayout(new BorderLayout());
        frame.add(topBar, BorderLayout.NORTH);

        return frame;

    }

    public static void setGrid(JFrame frame, int row, int col) {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(row, col));


        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {

                Vector2 cellPos = new Vector2(j, i);
                JButton cell =  new JButton();
                gridMap.add(new Cell(cell, cellPos));
                cell.setBackground(defColor);

                int index = i;
                cell.addActionListener(e -> {
                    clickButton(cell, cellPos);
                });

                gridPanel.add(cell);
            }




        }
        frame.add(gridPanel, BorderLayout.CENTER);


    }

    public static void clickButton(JButton button,Vector2 cellPos) {
        if(startPosSelection == 1){
            button.setBackground(startColor);
            startCell = button;
            startPosSelection = 0;
            startPos = new Cell(button,new Vector2(cellPos.x, cellPos.y));

        }else if(startPosSelection == 0){
            button.setBackground(endColor);
            endCell = button;
            startPosSelection = 2;
            endPos = new Cell(button,new Vector2(cellPos.x, cellPos.y));
            calculateRoute(startPos, endPos);
        }
    }


    public static void calculateRoute(Cell startCell, Cell endCell) {
        Vector2 startPos = startCell.cellPos;
        Vector2 endPos = endCell.cellPos;
        double bestPoint = Integer.MAX_VALUE;
        Cell bestCell = null;

        for (int i = startPos.x - 1; i <= startPos.x+1; i++) {
            for (int j = startPos.y-1; j <= startPos.y+1; j++) {


                if(i == startPos.x && j == startPos.y){
                    continue;
                }

                if(i >= 0 && j >= 0 && i < COL && j < ROW){
                    System.out.println(i + " " + j);
                    Cell currentCell = gridMap.get(i + j*COL);
                    double point = distance(currentCell.cellPos, startPos) + distance(currentCell.cellPos, endPos);

                    currentCell.button.setText(String.valueOf( (int)point));
                    if(bestPoint > point){
                        bestPoint = point;
                        bestCell = currentCell;
                    }


                }else{
                    continue;
                }
            }
        }
        System.out.println(bestCell.cellPos.x + " " + bestCell.cellPos.y);
        System.out.println(endPos.x + " " + endPos.y);

        if(bestCell.cellPos.x != endPos.x ||  bestCell.cellPos.y != endPos.y){
            System.out.println("best point is " + bestCell.cellPos.x + " " + bestCell.cellPos.y);
            bestCell.button.setBackground(routeColor);
            calculateRoute(bestCell,endCell);
        }


    }

    public static void fillTheRoute() {
        for (int i = 0; i < routePos.size(); i++) {
            routePos.get(i).button.setBackground(routeColor);
        }
    }


    public static double distance(Vector2 firstPos, Vector2 secondPos) {
        int distance = Math.powExact(firstPos.x - secondPos.x, 2);
        distance += Math.powExact(firstPos.y - secondPos.y, 2);
        return Math.sqrt(distance);
    }

    public static void main(String[] args) {

        JFrame frame = setWindow();
        setGrid(frame,ROW,COL);

    }


}