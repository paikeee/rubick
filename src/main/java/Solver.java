import java.util.*;

public class Solver {

    Cube cube;

    public Solver(Cube cube) {
        this.cube = cube;
    }

    public void solveCube() {
        cube.controlStack();
        while (!checkFlower()) {
            flower();
        }
        if (!checkRightCross()) {
            cross();
            crossCorrector();
        }
        while (!checkAnglesWhite()) {
            angles();
        }
        while (!checkEdges()) {
            edges();
        }
        if (!checkYellowCross()) {
            if (!checkYellowLine()) {
                if (!checkYellowClock()) {
                    cube.rotateSideRight(0);
                    rightHand(0);
                    rightHand(0);
                    cube.rotateSideLeft(0);
                    checkYellowLine();
                }
            }
        }
        checkYellowAngles();
        while (!checkYellowFull()) {
            fullYellow();
        }
        if (!cubeIsReady()) {
            finalStep();
        }
        cube.controlStack();
    }

    public void solveFlower() {
        cube.controlStack();
        while (!checkFlower()) {
            flower();
        }
    }

    public void solveCross() {
        if (!checkRightCross()) {
            cross();
            crossCorrector();
        }
    }

    public void solveAngles() {
        while (!checkAnglesWhite()) {
            angles();
        }
    }

    public void solveEdges() {
        while (!checkEdges()) {
            edges();
        }
    }

    public void solveYellowCross() {
        if (!checkYellowCross()) {
            if (!checkYellowLine()) {
                if (!checkYellowClock()) {
                    cube.rotateSideRight(0);
                    rightHand(0);
                    rightHand(0);
                    cube.rotateSideLeft(0);
                    checkYellowLine();
                }
            }
        }
    }

    public void solveYellowFull() {
        checkYellowAngles();
        while (!checkYellowFull()) {
            fullYellow();
        }
    }

    // Сборка цветочка
    private void flower() {
        for (int s = 0; s < 5; s++) {
            int count = 0;
            int i = 0;
            int j = 1;
                while (count < 4) {
                    if (cube.getGameTiles()[s][i][j].color == 'W') {
                        int i1 = i;
                        int j1 = j;
                        if (s != 1 && s != 3) {
                            if (i != 1) {
                                cube.rotateSideRight(s);
                                j1 = j + j - i;
                            }
                            if (s == 0) {
                                turn0(j1);
                            } else {
                                turn24(s, j1);
                            }
                        } else {
                            if (i == 1) {
                                cube.rotateSideRight(s);
                                i1 = j;
                                j1 = i;
                            }
                            if (s == 1) {
                                cube.rotateRowRight(1, i1);
                            } else {
                                cube.rotateRowLeft(3, i1);
                            }
                            cube.rotateSideRight(4);
                            j1 = j1 + j1 - i1;
                            turn24(4, j1);
                        }
                    }
                    if (count != 1) {
                        i++;
                        j--;
                    } else {
                        j = (j + 2) % 3;
                    }
                    count++;
                }
            }

    }

    public boolean checkFlower() {
        Tile[][] body = cube.getGameTiles()[5];
        return body[0][1].color == 'W' && body[1][2].color == 'W' &&
                body[1][0].color == 'W' && body[2][1].color == 'W';
    }

    // Сборка белого креста
    private void cross() {
        cube.rotateColumnUp(5, 0);
        cube.rotateColumnUp(5, 0);
        cube.rotateColumnUp(5, 2);
        cube.rotateColumnUp(5, 2);
        cube.rotateSideRight(5);
        cube.rotateSideRight(2);
        cube.rotateColumnUp(5, 0);
        cube.rotateColumnUp(5, 0);
        cube.rotateColumnUp(5, 2);
        cube.rotateColumnUp(5, 2);
    }

    public boolean checkRightCross() {
        Tile[][][] body = cube.getGameTiles();
        return body[0][1][1].color == body[0][0][1].color && body[1][1][1].color == body[1][0][1].color &&
                body[3][1][1].color == body[3][0][1].color && body[4][1][1].color == body[4][0][1].color && checkWhiteCross();
    }

    private boolean checkWhiteCross() {
        Tile[][][] body = cube.getGameTiles();
        return body[2][0][1].color == 'W' && body[2][1][0].color == 'W' &&
                body[2][1][2].color == 'W' && body[2][2][1].color == 'W';
    }

    // Корректировка белого креста
    private void crossCorrector() {
        ArrayList<Integer> sides = new ArrayList<>(4);
        sides.add(0);
        sides.add(1);
        sides.add(4);
        sides.add(3);
        ArrayList<Integer> incorrect = new ArrayList<>(4);
        for (Integer side : sides) {
            if (cube.getGameTiles()[side][1][1].color != cube.getGameTiles()[side][0][1].color) {
                incorrect.add(side);
            }
        }
        switch (incorrect.size()) {
            case 2 -> {
                int first = incorrect.get(0);
                int second = incorrect.get(1);
                switch (sides.indexOf(second) - sides.indexOf(first)) {
                    case 1 -> pifPaf(first);
                    case 3 -> pifPaf(3);
                    case 2 -> parallel(sides.get(sides.indexOf(second) - 1));
                }
            }
            case 3 -> {
                int first = incorrect.get(0);
                int second = incorrect.get(1);
                int third = incorrect.get(1);
                if (first == 0) {
                    if (second == 4) {
                        parallel(third);
                    } else if (third == 3) {
                        parallel(first);
                    } else {
                        parallel(second);
                    }
                } else {
                    parallel(second);
                }
                if (cube.getGameTiles()[first][1][1].color == cube.getGameTiles()[first][0][1].color) {
                    incorrect.remove(0);
                } else {
                    incorrect.remove(2);
                }
                crossCorrector();
            }
            case 4 -> {
                cube.rotateSideRight(2);
                crossCorrector();
            }
        }
    }

    // Сборка белых углов и первого слоя
    private void angles() {
        boolean correction = false;
        int[] sides = new int[] {0, 1, 4, 3};
        for (int s = 0; s < 4; s++) {
            for (int i = 0; i < 3; i += 2) {
                for (int j = 0; j < 3; j += 2) {
                    if (cube.getGameTiles()[sides[s]][i][j].color == 'W') {
                        correction = true;
                        goToYellow(sides[s], i, j);
                    }
                    if (correction) {
                        break;
                    }
                }
                if (correction) {
                    break;
                }
            }
            if (correction) {
                break;
            }
        }
        if (!correction) {
            moveToWhite();
        }
    }

    private void goToYellow(int s, int i, int j) {
        if (j == 0) {
            rightHand(s);
            if (i == 2) {
                rightHand(s);
            }
        } else {
            leftHand(s);
            if (i == 2) {
                leftHand(s);
            }
        }
        moveToWhite();
    }

    private void moveToWhite() {
        boolean correction = false;
        Tile[][][] body = cube.getGameTiles();
        if (checkYellowForWhites()) {
            for (int i = 0; i < 3; i += 2) {
                for (int j = 0; j < 3; j += 2) {
                    if (body[5][i][j].color == 'W') {
                        goToWhite(i, j);
                        correction = true;
                    }
                    if (correction) {
                        break;
                    }
                }
                if (correction) {
                    break;
                }
            }
        } else {
            if (body[2][0][0].color == 'W') {
                if (body[0][0][2].color != body[0][1][1].color || body[1][0][0].color != body[1][1][1].color) {
                    rightHand(1);
                }
            }
            if (body[2][0][2].color == 'W') {
                if (body[3][0][2].color != body[3][1][1].color || body[0][0][0].color != body[0][1][1].color) {
                    rightHand(0);
                }
            }
            if (body[2][2][2].color == 'W') {
                if (body[4][0][2].color != body[4][1][1].color || body[3][0][0].color != body[3][1][1].color) {
                    rightHand(3);
                }
            }
            if (body[2][2][0].color == 'W') {
                if (body[1][0][2].color != body[1][1][1].color || body[4][0][0].color != body[4][1][1].color) {
                    rightHand(4);
                }
            }
        }
    }

    private void goToWhite(int i, int j) {
        Tile[][][] body = cube.getGameTiles();
        if (i == 0 && j == 0) {
            if ((body[4][2][0].color == body[4][1][1].color || body[4][2][0].color == body[1][1][1].color) &&
                    (body[1][2][2].color == body[4][1][1].color || body[1][2][2].color == body[1][1][1].color)) {
                rightHand(4);
                rightHand(4);
                rightHand(4);
            } else {
                cube.rotateSideRight(5);
                goToWhite(0, 2);
            }
        } else if (i == 0 && j == 2) {
            if ((body[4][2][2].color == body[4][1][1].color || body[4][2][2].color == body[3][1][1].color) &&
                    (body[3][2][0].color == body[4][1][1].color || body[3][2][0].color == body[3][1][1].color)) {
                rightHand(3);
                rightHand(3);
                rightHand(3);
            } else {
                cube.rotateSideRight(5);
                goToWhite(2, 2);
            }
        } else if (i == 2 && j == 2) {
            if ((body[3][2][2].color == body[0][1][1].color || body[3][2][2].color == body[3][1][1].color) &&
                    (body[0][2][0].color == body[0][1][1].color || body[0][2][0].color == body[3][1][1].color)) {
                rightHand(0);
                rightHand(0);
                rightHand(0);
            } else {
                cube.rotateSideRight(5);
                goToWhite(2, 0);
            }
        } else {
            if ((body[0][2][2].color == body[0][1][1].color || body[0][2][2].color == body[1][1][1].color) &&
                    (body[1][2][0].color == body[0][1][1].color || body[1][2][0].color == body[1][1][1].color)) {
                rightHand(1);
                rightHand(1);
                rightHand(1);
            } else {
                cube.rotateSideRight(5);
                goToWhite(0, 0);
            }
        }
    }

    public boolean checkAnglesWhite() {
        Tile[][][] body = cube.getGameTiles();
        if (body[2][0][0].color == 'W' && body[2][0][2].color == 'W' &&
                body[2][2][0].color == 'W' && body[2][2][2].color == 'W') {
            return checkAnglesOthers();
        }
        return false;
    }

    private boolean checkAnglesOthers() {
        Tile[][][] body = cube.getGameTiles();
        return body[0][0][0].color == 'O' && body[0][0][2].color == 'O' &&
                body[1][0][0].color == 'G' && body[1][0][2].color == 'G' &&
                body[4][0][0].color == 'R' && body[4][0][2].color == 'R' &&
                body[3][0][0].color == 'B' && body[3][0][2].color == 'B';
    }

    private boolean checkYellowForWhites() {
        Tile[][][] body = cube.getGameTiles();
        for (int i = 0; i < 3; i += 2) {
            for (int j = 0; j < 3; j += 2) {
                if (body[5][i][j].color == 'W') {
                    return true;
                }
            }
        }
        return false;
    }

    // Сборка второго слоя
    private void edges() {
        boolean correction = false;
        Tile[][][] body = cube.getGameTiles();
        int count = 0;
        int i = 0;
        int j = 1;
        while (count < 4) {
            if (body[5][i][j].color != 'Y') {
                if (i == 0 && j == 1 && body[4][2][1].color != 'Y') {
                    correction = true;
                    rotateYellow(body[4][2][1].color, body[5][i][j].color, 0);
                } else if (i == 1 && j == 0 && body[1][2][1].color != 'Y') {
                    correction = true;
                    rotateYellow(body[1][2][1].color, body[5][i][j].color, 1);
                } else if (i == 1 && j == 2 && body[3][2][1].color != 'Y') {
                    correction = true;
                    rotateYellow(body[3][2][1].color, body[5][i][j].color, 2);
                } else if (i == 2 && j == 1 && body[0][2][1].color != 'Y'){
                    correction = true;
                    rotateYellow(body[0][2][1].color, body[5][i][j].color,  3);
                }
            }

            if (correction) {
                break;
            }
            if (count != 1) {
                i++;
                j--;
            } else {
                j = (j + 2) % 3;
            }
            count++;
        }
        if (!correction) {
            ArrayList<Integer> sidesNum = new ArrayList<>(4);
            sidesNum.add(0);
            sidesNum.add(1);
            sidesNum.add(4);
            sidesNum.add(3);
            for (int side : sidesNum) {
                for (int j1 = 0; j1 < 3; j1 += 2) {
                    if (body[side][1][j1].color != body[side][1][1].color) {
                        if (j1 == 2) {
                            cube.rotateRowLeft(0, 2);
                            leftHand(side);
                            int index = sidesNum.indexOf(side) + 1;
                            if (index == 4) {
                                index = 0;
                            }
                            rightHand(sidesNum.get(index));
                        } else {
                            cube.rotateRowRight(0, 2);
                            rightHand(side);
                            int index = sidesNum.indexOf(side) - 1;
                            if (index == -1) {
                                index = 3;
                            }
                            leftHand(sidesNum.get(index));
                        }
                        correction = true;
                    }
                    if (correction) {
                        break;
                    }
                }
                if (correction) {
                    break;
                }
            }
        }
    }

    private void rotateYellow(char s, char s2, int pos) {
        switch (s) {
            case 'O' -> {
                switch (pos) {
                    case 0 -> {
                        cube.rotateSideRight(5);
                        cube.rotateSideRight(5);
                    }
                    case 1 -> cube.rotateSideLeft(5);
                    case 2 -> cube.rotateSideRight(5);
                }
            }
            case 'G' -> {
                switch (pos) {
                    case 0 -> cube.rotateSideLeft(5);
                    case 2 -> {
                        cube.rotateSideRight(5);
                        cube.rotateSideRight(5);
                    }
                    case 3 -> cube.rotateSideRight(5);
                }
            }
            case 'R' -> {
                switch (pos) {
                    case 1 -> cube.rotateSideRight(5);
                    case 2 -> cube.rotateSideLeft(5);
                    case 3 -> {
                        cube.rotateSideRight(5);
                        cube.rotateSideRight(5);
                    }
                }
            }
            case 'B' -> {
                switch (pos) {
                    case 0 -> cube.rotateSideRight(5);
                    case 1 -> {
                        cube.rotateSideRight(5);
                        cube.rotateSideRight(5);
                    }
                    case 3 -> cube.rotateSideLeft(5);
                }
            }
        }
        putEdge(s, s2);
    }

    private void putEdge(char s, char s2) {
        ArrayList<Character> sidesColor = new ArrayList<>(4);
        sidesColor.add('O');
        sidesColor.add('G');
        sidesColor.add('R');
        sidesColor.add('B');
        ArrayList<Integer> sidesNum = new ArrayList<>(4);
        sidesNum.add(0);
        sidesNum.add(1);
        sidesNum.add(4);
        sidesNum.add(3);
        int first = sidesColor.indexOf(s);
        int second = sidesColor.indexOf(s2);
        switch (second - first) {
            case 1, -3 -> {
                cube.rotateSideLeft(5);
                leftHand(sidesNum.get(first));
                rightHand(sidesNum.get(second));
            }
            case -1, 3 -> {
                cube.rotateSideRight(5);
                rightHand(sidesNum.get(first));
                leftHand(sidesNum.get(second));
            }
        }
    }

    public boolean checkEdges() {
        Tile[][][] body = cube.getGameTiles();
        int[] sides = new int[] {0, 1, 4, 3};
        for (int s = 0; s < 4; s++) {
            if (body[sides[s]][1][0].color != body[sides[s]][1][1].color ||
                    body[sides[s]][1][2].color != body[sides[s]][1][1].color)
                return false;
        }
        return true;
    }

    // Сборка желтого креста
    public boolean checkYellowCross() {
        Tile[][][] body = cube.getGameTiles();
        return body[5][0][1].color == 'Y' && body[5][1][0].color == 'Y' &&
                body[5][1][2].color == 'Y' && body[5][2][1].color == 'Y';
    }

    private boolean checkYellowLine() {
        Tile[][][] body = cube.getGameTiles();
        if (body[5][0][1].color == 'Y' && body[5][2][1].color == 'Y') {
            cube.rotateSideRight(1);
            rightHand(1);
            cube.rotateSideLeft(1);
            return true;
        } else if (body[5][1][0].color == 'Y' && body[5][1][2].color == 'Y') {
            cube.rotateSideRight(4);
            rightHand(4);
            cube.rotateSideLeft(4);
            return true;
        }
        return false;
    }

    private boolean checkYellowClock() {
        Tile[][][] body = cube.getGameTiles();
        if (body[5][1][0].color == 'Y' && body[5][0][1].color == 'Y') {
            cube.rotateSideRight(0);
            rightHand(0);
            rightHand(0);
            cube.rotateSideLeft(0);
            return true;
        } else if (body[5][1][2].color == 'Y' && body[5][0][1].color == 'Y') {
            cube.rotateSideRight(1);
            rightHand(1);
            rightHand(1);
            cube.rotateSideLeft(1);
            return true;
        } else if (body[5][1][2].color == 'Y' && body[5][2][1].color == 'Y') {
            cube.rotateSideRight(4);
            rightHand(4);
            rightHand(4);
            cube.rotateSideLeft(4);
            return true;
        } else if (body[5][2][1].color == 'Y' && body[5][1][0].color == 'Y') {
            cube.rotateSideRight(3);
            rightHand(3);
            rightHand(3);
            cube.rotateSideLeft(3);
            return true;
        }
        return false;
    }

    // Сборка желтых углов
    private void checkYellowAngles() {
        ArrayList<Integer> pos = new ArrayList<>(4);
        Tile[][][] body = cube.getGameTiles();
        if (body[5][0][0].color == body[1][1][1].color || body[1][2][2].color ==
                body[1][1][1].color || body[4][2][0].color == body[1][1][1].color) {
            if (body[5][0][0].color == body[4][1][1].color || body[1][2][2].color ==
                    body[4][1][1].color || body[4][2][0].color == body[4][1][1].color) {
                pos.add(0);
            }
        }
        if (body[5][0][2].color == body[3][1][1].color || body[3][2][0].color ==
                body[3][1][1].color || body[4][2][2].color == body[3][1][1].color) {
            if (body[5][0][2].color == body[4][1][1].color || body[3][2][0].color ==
                    body[4][1][1].color || body[4][2][2].color == body[4][1][1].color) {
                pos.add(1);
            }
        }
        if (body[5][2][2].color == body[3][1][1].color || body[3][2][2].color ==
                body[3][1][1].color || body[0][2][0].color == body[3][1][1].color) {
            if (body[5][2][2].color == body[0][1][1].color || body[3][2][2].color ==
                    body[0][1][1].color || body[0][2][0].color == body[0][1][1].color) {
                pos.add(2);
            }
        }
        if (body[5][2][0].color == body[1][1][1].color || body[1][2][0].color ==
                body[1][1][1].color || body[0][2][2].color == body[1][1][1].color) {
            if (body[5][2][0].color == body[0][1][1].color || body[1][2][0].color ==
                    body[0][1][1].color || body[0][2][2].color == body[0][1][1].color) {
                pos.add(3);
            }
        }
        if (pos.size() < 2) {
            cube.rotateSideRight(5);
            checkYellowAngles();
        } else if (pos.size() == 2) {
            anglesCorrector(pos.get(0), pos.get(1));
            checkYellowAngles();
        }
    }

    private void anglesCorrector(int pos1, int pos2) {
        if (pos1 == 0 && (pos2 == 1 || pos2 == 2)) {
            rightHand(1);
            rightHand(1);
            rightHand(1);
            leftHand(0);
            leftHand(0);
            leftHand(0);
        } else if (pos1 == 1 && (pos2 == 2 || pos2 == 3)) {
            rightHand(4);
            rightHand(4);
            rightHand(4);
            leftHand(1);
            leftHand(1);
            leftHand(1);
        } else if (pos1 == 2 && pos2 == 3) {
            rightHand(3);
            rightHand(3);
            rightHand(3);
            leftHand(4);
            leftHand(4);
            leftHand(4);
        } else if (pos1 == 0 && pos2 == 3) {
            rightHand(0);
            rightHand(0);
            rightHand(0);
            leftHand(3);
            leftHand(3);
            leftHand(3);
        }
    }

    private void fullYellow() {
        if (cube.getGameTiles()[1][2][2].color == 'Y' || cube.getGameTiles()[4][2][0].color == 'Y') {
            while (cube.getGameTiles()[5][0][0].color != 'Y') {
                cube.rotateColumnUp(1, 2);
                cube.rotateRowLeft(1, 0);
                cube.rotateColumnDown(1, 2);
                cube.rotateRowRight(1, 0);
            }
        } else {
            cube.rotateSideRight(5);
        }
    }

    public boolean checkYellowFull() {
        Tile[][][] body = cube.getGameTiles();
        for (int i = 0; i < 3; i += 2) {
            for (int j = 0; j < 3; j += 2) {
                if (body[5][i][j].color != 'Y') {
                    return false;
                }
            }
        }
        return true;
    }

    // Финальная сборка всех слоев
    public void finalStep() {
        int[] sides = new int[] {0, 1, 4, 3};
        boolean correct = false;
        if (cube.getGameTiles()[0][2][2].color == cube.getGameTiles()[0][2][1].color &&
                cube.getGameTiles()[1][2][2].color == cube.getGameTiles()[1][2][1].color) {
            while (!cubeIsReady()) {
                cube.rotateSideRight(5);
            }
            correct = true;
        }
        if (cube.getGameTiles()[0][2][2].color != cube.getGameTiles()[0][2][1].color &&
                cube.getGameTiles()[1][2][2].color != cube.getGameTiles()[1][2][1].color &&
                cube.getGameTiles()[4][2][2].color != cube.getGameTiles()[4][2][1].color &&
                cube.getGameTiles()[3][2][2].color != cube.getGameTiles()[3][2][1].color) {
            finalPaf(0);
        }
        if (!correct) {
            int s = 0;
            while (s < 4) {
                if (cube.getGameTiles()[sides[s]][2][0].color == cube.getGameTiles()[sides[s]][2][1].color
                    && cube.getGameTiles()[sides[s]][2][1].color == cube.getGameTiles()[sides[s]][2][2].color) {
                    if (cube.getGameTiles()[sides[s]][2][1].color != cube.getGameTiles()[sides[s]][1][1].color) {
                        cube.rotateSideRight(5);
                        s = -1;
                    } else {
                        finalPaf(sides[s]);
                        if (!cubeIsReady()) {
                            finalPaf(sides[s]);
                        }
                        s = 4;
                    }
                }
                s++;
            }
        }
//        while (s < 4) {
//            if (cube.getGameTiles()[sides[s]][2][0].color == cube.getGameTiles()[sides[s]][2][1].color
//                    && cube.getGameTiles()[sides[s]][2][1].color == cube.getGameTiles()[sides[s]][2][2].color) {
//                if (cube.getGameTiles()[sides[s]][2][1].color != cube.getGameTiles()[sides[s]][1][1].color) {
//                    cube.rotateSideRight(5);
//                    finalStep();
//                } else {
//                    while (!cubeIsReady()) {
//                        finalPaf(sides[s]);
//                    }
//                }
//            }
//            if (cubeIsReady()) {
//                break;
//            }
//            if (s == 3 && !cubeIsReady()) {
//                finalPaf(1);
//                finalStep();
//            }
//            s++;
//        }
    }

    // 7 вспомогательных методов для сборки
    private void turn0(int j) {
        while (cube.getGameTiles()[5][1][2 - j].color == 'W') {
            cube.rotateSideRight(5);
        }
        cube.rotateColumnUp(2, 2 - j);
    }

    private void turn24(int s, int j) {
        while (cube.getGameTiles()[5][1][j].color == 'W') {
            cube.rotateSideRight(5);
        }
        cube.rotateColumnDown(2, j);
        if (s == 2) {
            cube.rotateColumnDown(2, j);
        }
    }

    private void finalPaf(int s) {
        rightHand(s);
        leftHand(s);
        rightHand(s);
        rightHand(s);
        rightHand(s);
        rightHand(s);
        rightHand(s);
        leftHand(s);
        leftHand(s);
        leftHand(s);
        leftHand(s);
        leftHand(s);
    }

    private void pifPaf(int s) {
        cube.rotateColumnUp(s, 2);
        cube.rotateRowRight(s, 0);
        cube.rotateColumnDown(s, 2);
        cube.rotateRowLeft(s, 0);
        cube.rotateColumnUp(s, 2);
    }

    private void parallel(int s) {
        cube.rotateColumnUp(s, 2);
        cube.rotateColumnUp(s, 2);
        cube.rotateColumnUp(s, 0);
        cube.rotateColumnUp(s, 0);
        cube.rotateRowRight(s, 2);
        cube.rotateRowRight(s, 2);
        cube.rotateColumnUp(s, 2);
        cube.rotateColumnUp(s, 2);
        cube.rotateColumnUp(s, 0);
        cube.rotateColumnUp(s, 0);
    }

    private void rightHand(int s) {
        cube.rotateColumnDown(s, 0);
        cube.rotateRowRight(s, 2);
        cube.rotateColumnUp(s, 0);
        cube.rotateRowLeft(s, 2);
    }

    private void leftHand(int s) {
        cube.rotateColumnDown(s, 2);
        cube.rotateRowLeft(s, 2);
        cube.rotateColumnUp(s, 2);
        cube.rotateRowRight(s, 2);
    }

    public boolean cubeIsReady() {
        Tile[][][] body = cube.getGameTiles();
        for (int s = 0; s < 6; s++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (body[s][i][j].color != Tile.colors[s]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
