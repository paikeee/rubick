public class Test {

    public boolean testing() {
        for (int i = 0; i < 1000; i++) {
            Cube cube = new Cube();
            cube.randomGameTiles();
            Solver solver = new Solver(cube);
            solver.solveFlower();
            if (!solver.checkFlower()) {
                System.out.print("Error in flower");
                return false;
            }
            solver.solveCross();
            if (!solver.checkRightCross()) {
                System.out.print("Error in cross");
                return false;
            }
            solver.solveAngles();
            if (!solver.checkAnglesWhite()) {
                System.out.print("Error in white angles");
                return false;
            }
            solver.solveEdges();
            if (!solver.checkEdges()) {
                System.out.print("Error in edges");
                return false;
            }
            solver.solveYellowCross();
            if (!solver.checkYellowCross()) {
                System.out.print("Error in yellow cross");
                return false;
            }
            solver.solveYellowFull();
            if (!solver.checkYellowFull()) {
                System.out.print("Error in yellow full");
                return false;
            }
            solver.finalStep();
            if (!solver.cubeIsReady()) {
                System.out.print("Error in final step");
                return false;
            }
        }
        return true;
    }
}
