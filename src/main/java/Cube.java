import java.util.*;

public class Cube {

    private Tile[][][] body;
    private boolean stackWork = false;
    private final ArrayDeque<Tile[][][]> stackBody = new ArrayDeque<>();
    private int steps = 0;

    public Cube() {

    }

    // Задание правильной конфигурации кубика
    public void setGameTiles() {
        body = new Tile[6][3][3];
        for (int s = 0; s < 6; s++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    body[s][i][j] = new Tile();
                    body[s][i][j].color = Tile.colors[s];
                }
            }
        }
    }

    // Задание случайно конфигурации кубика
    public void randomGameTiles() {
        setGameTiles();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int method = random.nextInt(2);
            int s = random.nextInt(6);
            switch (method) {
                case 0 -> rotateSideRight(s);
                case 1 -> rotateSideLeft(s);
            }
        }
        steps = 0;
    }

    // Смещение ряда влево
    public void rotateRowLeft(int s, int i) {
        if (s == 0 || s == 1 || s == 3 || s == 4) {
            rotationLeft0143(new int[] {0, 1, 4, 3}, i);
            if (i == 0) {
                rotate90Right(2);
            } else {
                rotate90Left(5);
            }
        } else if (s == 2) {
            rotationDown1235(new int[] {1, 2, 3, 5}, i);
            if (i == 0) {
                rotate90Right(0);
            } else {
                rotate90Left(4);
            }
        } else {
            rotationUp1235(new int[] {1, 2, 3, 5}, 2 - i);
            if (i == 0) {
                rotate90Right(4);
            } else {
                rotate90Left(0);
            }
        }
        if (stackWork) {
            addStack();
        }
        steps++;
    }

    // Смещение ряда вправо
    public void rotateRowRight(int s, int i) {
        if (s == 0 || s == 1 || s == 3 || s == 4) {
            rotationRight0143(new int[] {0, 1, 4, 3}, i);
            if (i == 0) {
                rotate90Left(2);
            } else {
                rotate90Right(5);
            }
        } else if (s == 2) {
            rotationUp1235(new int[] {1, 2, 3, 5}, i);
            if (i == 0) {
                rotate90Left(0);
            } else {
                rotate90Right(4);
            }
        } else {
            rotationDown1235(new int[] {1, 2, 3, 5}, 2 - i);
            if (i == 0) {
                rotate90Left(4);
            } else {
                rotate90Right(0);
            }
        }
        if (stackWork) {
            addStack();
        }
        steps++;
    }

    // Смещение столбца вниз
    public void rotateColumnDown(int s, int j) {
        if (s == 1) {
            rotationDown1235(new int[] {1, 2, 3, 5}, j);
            if (j == 0) {
                rotate90Right(0);
            } else {
                rotate90Left(4);
            }
        } else if (s == 3) {
            rotationUp1235(new int[] {1, 2, 3, 5}, 2 - j);
            if (j == 0) {
                rotate90Right(4);
            } else {
                rotate90Left(0);
            }
        }
        else if (s == 0) {
            rotationUp0245(new int[] {0, 2, 4, 5}, 2 - j);
            if (j == 0) {
                rotate90Right(3);
            } else {
                rotate90Left(1);
            }
        }
        else {
            rotationDown0245(new int[] {0, 2, 4, 5}, j);
            if (j == 0) {
                rotate90Right(1);
            } else {
                rotate90Left(3);
            }
        }
        if (stackWork) {
            addStack();
        }
        steps++;
    }

    // Смещение столбца вверх
    public void rotateColumnUp(int s, int j) {
        if (s == 1) {
            rotationUp1235(new int[] {1, 2, 3, 5}, j);
            if (j == 0) {
                rotate90Left(0);
            } else {
                rotate90Right(4);
            }
        } else if (s == 3) {
            rotationDown1235(new int[] {1, 2, 3, 5}, 2 - j);
            if (j == 0) {
                rotate90Left(4);
            } else {
                rotate90Right(0);
            }
        }
        else if (s == 0) {
            rotationDown0245(new int[] {0, 2, 4, 5}, 2 - j);
            if (j == 0) {
                rotate90Left(3);
            } else {
                rotate90Right(1);
            }
        }
        else {
            rotationUp0245(new int[] {0, 2, 4, 5}, j);
            if (j == 0) {
                rotate90Left(1);
            } else {
                rotate90Right(3);
            }
        }
        if (stackWork) {
            addStack();
        }
        steps++;
    }

    // Поворот грани по часовой стрелке
    public void rotateSideRight(int s) {
        switch (s) {
            case 0 -> rotateRowLeft(2, 0);
            case 1 -> rotateColumnDown(2, 0);
            case 2 -> rotateRowLeft(1, 0);
            case 3 -> rotateColumnUp(2, 2);
            case 4 -> rotateRowRight(2, 2);
            case 5 -> rotateRowRight(1, 2);
        }
    }

    // Поворот грани против часовой стрелки
    public void rotateSideLeft(int s) {
        switch (s) {
            case 0 -> rotateRowRight(2, 0);
            case 1 -> rotateColumnUp(2, 0);
            case 2 -> rotateRowRight(1, 0);
            case 3 -> rotateColumnDown(2, 2);
            case 4 -> rotateRowLeft(2, 2);
            case 5 -> rotateRowLeft(1, 2);
        }
    }

    // 8 методов поворота по задействованным граням
    private void rotationLeft0143(int[] sides, int i) {
        Tile current1;
        Tile current2;
        for (int j = 0; j < 3; j++) {
            current1 = body[sides[3]][i][j];
            body[sides[3]][i][j] = body[sides[0]][i][j];
            current2 = body[sides[2]][i][j];
            body[sides[2]][i][j] = current1;
            current1 = body[sides[1]][i][j];
            body[sides[1]][i][j] = current2;
            body[sides[0]][i][j] = current1;
        }
    }

    private void rotationRight0143(int[] sides, int i) {
        Tile current1;
        Tile current2;
        for (int j = 0; j < 3; j++) {
            current1 = body[sides[1]][i][j];
            body[sides[1]][i][j] = body[sides[0]][i][j];
            current2 = body[sides[2]][i][j];
            body[sides[2]][i][j] = current1;
            current1 = body[sides[3]][i][j];
            body[sides[3]][i][j] = current2;
            body[sides[0]][i][j] = current1;
        }
    }

    private void rotationDown1235(int[] sides, int j) {
        Tile current1;
        Tile current2;
        for (int i = 0; i < 3; i++) {
            current1 = body[sides[0]][i][j];
            body[sides[0]][i][j] = body[sides[1]][j][2 - i];
            current2 = body[sides[3]][2 - j][i];
            body[sides[3]][2 - j][i] = current1;
            current1 = body[sides[2]][2 - i][2 - j];
            body[sides[2]][2 - i][2 - j] = current2;
            body[sides[1]][j][2 - i] = current1;
        }
    }

    private void rotationUp1235(int[] sides, int j) {
        Tile current1;
        Tile current2;
        for (int i = 0; i < 3; i++) {
            current1 = body[sides[0]][i][j];
            body[sides[0]][i][j] = body[sides[3]][2 - j][i];
            current2 = body[sides[1]][j][2 - i];
            body[sides[1]][j][2 - i] = current1;
            current1 = body[sides[2]][2 - i][2 - j];
            body[sides[2]][2 - i][2 - j] = current2;
            body[sides[3]][2 - j][i] = current1;
        }
    }

    private void rotationDown0245(int[] sides, int j) {
        Tile current1;
        Tile current2;
        for (int i = 0; i < 3; i++) {
            current1 = body[sides[1]][i][j];
            body[sides[1]][i][j] = body[sides[0]][2 - i][2 - j];
            current2 = body[sides[2]][i][j];
            body[sides[2]][i][j] = current1;
            current1 = body[sides[3]][i][j];
            body[sides[3]][i][j] = current2;
            body[sides[0]][2 - i][2 -j] = current1;
        }
    }

    private void rotationUp0245(int[] sides, int j) {
        Tile current1;
        Tile current2;
        for (int i = 0; i < 3; i++) {
            current1 = body[sides[1]][i][j];
            body[sides[1]][i][j] = body[sides[2]][i][j];
            current2 = body[sides[0]][2 - i][2 -j];
            body[sides[0]][2 - i][2 -j] = current1;
            current1 = body[sides[3]][i][j];
            body[sides[3]][i][j] = current2;
            body[sides[2]][i][j] = current1;
        }
    }

    private void rotate90Right(int s) {
        Tile[][] rotateGrid = new Tile[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotateGrid[j][2 - i] = body[s][i][j];
            }
        }
        body[s] = rotateGrid;
    }

    private void rotate90Left(int s) {
        Tile[][] rotateGrid = new Tile[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotateGrid[2 - j][i] = body[s][i][j];
            }
        }
        body[s] = rotateGrid;
    }

    public Tile[][][] getGameTiles() {
        return body;
    }

    public void controlStack() {
        stackWork = !stackWork;
        addStack();
    }

    // Добавление предыдущего состояния в стек
    private void addStack() {
        Tile[][][] current = new Tile[6][3][3];
        for (int s = 0; s < 6; s++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    current[s][i][j] = new Tile();
                    current[s][i][j].color = body[s][i][j].color;
                }
            }
        }
        stackBody.push(current);
    }

    public Tile[][][] getStackBody() {
        return stackBody.pollLast();
    }

    public int getSteps() {
        return steps;
    }
}
