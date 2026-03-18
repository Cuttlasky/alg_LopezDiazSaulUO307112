import java.util.ArrayList;
import java.util.List;

public class Ferry {
    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehiculos = new ArrayList<>();
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos

    public Ferry(int boatLength, List<Integer> vehiculos) {
        this.boatLength = boatLength;
        this.vehiculos = vehiculos;
        this.dp = new boolean[vehiculos.size() + 1][boatLength + 1];

        this.sumatorio = new int[vehiculos.size() + 1];

        this.sumatorio[0] = 0;
        this.dp[0][0] = true;

        inicializarSumatorio(vehiculos);
    }

    private void inicializarSumatorio(List<Integer> vehiculos) {
        for (int i = 1; i < sumatorio.length; i++) {
            this.sumatorio[i] = this.sumatorio[i - 1] + vehiculos.get(i - 1);
        }
    }

    public void run() {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (sumatorio[i] <= boatLength) {
                this.dp[i][vehiculos.get(i)] = true;
            }
        }
    }
}
