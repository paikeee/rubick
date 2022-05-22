public class Test {

    public boolean testing() {
        for (int i = 0; i < 1000; i++) {
            Cube cube = new Cube();
            cube.randomGameTiles();
            Solver solver = new Solver(cube);
            solver.solveCube();
            if (!solver.cubeIsReady()) {
                return false;
            }
        }
        return true;
    }
}
