import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Ferry {
    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehiculos = new ArrayList<>();
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos
    private List<Step> path;

    public Ferry(int length, List<Integer> vehiculos) {
        this.boatLength = length;
        this.vehiculos = vehiculos;
        this.dp = new boolean[vehiculos.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehiculos.size() + 1];
        this.sumatorio[0] = 0;
        this.path = new LinkedList<>();

        inicializarSumatorio();
    }

    private void inicializarSumatorio() {
        for (int i = 1; i <= vehiculos.size(); i++) {
            this.sumatorio[i] = this.sumatorio[i - 1] + vehiculos.get(i - 1);
        }
    }

    public void run() {
        dp[0][0] = true;

        for (int i = 1; i <= vehiculos.size(); i++) {
            boolean placedAny = false;
            int v = vehiculos.get(i - 1);

            for (int l = 0; l <= boatLength; l++) {
                if (!dp[i - 1][l]) {
                    continue;
                }

                if (l + v <= boatLength) {
                    dp[i][l + v] = true;
                    placedAny = true;
                }

                if (sumatorio[i] - l <= boatLength) {
                    dp[i][l] = true;
                    placedAny = true;
                }
            }

            if (!placedAny) {
                break;
            }
        }
    }

    public int getMaximumNumberOfVehicles() {
        for (int i = vehiculos.size(); i >= 0; i--) {
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void printData() {
        System.out.printf("Length of parallel lanes for starboard and port on the ferry: %d\n", boatLength);
        System.out.printf("The vehicles have the following lengths:\n");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.printf("\tVehicle %d: %d\n", i + 1, vehiculos.get(i));
        }
    }

    public void printSolutionTable() {
        System.out.printf("\nTable with calculations:\n");
        System.out.printf("%4s", "V/L");
        for (int i = 0; i <= boatLength; i++) {
            System.out.printf("%4d", i);
        }
        System.out.printf("\n");
        for (int i = 0; i <= vehiculos.size(); i++) {
            System.out.printf("%4d", i);
            for (int l = 0; l <= boatLength; l++) {
                if (dp[i][l]) {
                    System.out.printf("%4s", "T");
                } else {
                    System.out.printf("%4s", "F");
                }
            }
            System.out.printf("\n");
        }
    }

    public void printPossibleAssignation() {
        System.out.printf("\nPossible assignation:\n");
        int maxV = getMaximumNumberOfVehicles();
        if (maxV == 0)
            return;

        for (int l = 0; l <= boatLength; l++) {
            if (dp[maxV][l]) {
                processAssignation(maxV, l);
                break;
            }
        }
    }

    private void processAssignation(int i, int l) {
        if ((i == 0) && (l == 0)) {
            printPath();
            return;
        }

        int v = vehiculos.get(i - 1);

        if (sumatorio[i] - l <= boatLength && dp[i - 1][l]) {
            path.add(0, new Step(i - 1, l, i, l, i, "estribor"));
            processAssignation(i - 1, l);
            return;
        }

        if (l >= v && dp[i - 1][l - v]) {
            path.add(0, new Step(i - 1, l - v, i, l, i, "babor"));
            processAssignation(i - 1, l - v);
        }
    }

    private void printPath() {
        int portLength = 0;
        int starboardLength = 0;
        for (var step : path) {
            if (step.movement().equals("babor")) {
                portLength += vehiculos.get(step.vehicle() - 1);
            } else {
                starboardLength += vehiculos.get(step.vehicle() - 1);
            }
            System.out.printf(
                    "Vehicle %d (length %d) -- From (%d, %d) -- To (%d, %d) -- Position: %s -- Port lengh: %d -- Starboard length: %d\n",
                    step.vehicle(), vehiculos.get(step.vehicle() - 1),
                    step.previousI(), step.previousL(),
                    step.currentI(), step.currentL(),
                    step.movement(), portLength, starboardLength);
        }
    }

    record Step(int previousI, int previousL, int currentI, int currentL, int vehicle, String movement) {
    }
}