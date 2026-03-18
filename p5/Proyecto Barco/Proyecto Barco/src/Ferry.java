import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ferry {
    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehiculos = new ArrayList<>();
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos

    public Ferry(String fileName) {
        loadData(fileName);
        this.dp = new boolean[vehiculos.size() + 1][boatLength + 1];

        this.sumatorio = new int[vehiculos.size() + 1];

        this.sumatorio[0] = 0;

        inicializarSumatorio(vehiculos);
    }

    private void inicializarSumatorio(List<Integer> vehiculos) {
        for (int i = 1; i < sumatorio.length; i++) {
            this.sumatorio[i] = this.sumatorio[i - 1] + vehiculos.get(i - 1);
        }
    }

    private void loadData(String file) {
        this.vehiculos = new ArrayList<Integer>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            this.boatLength = Integer.valueOf(reader.readLine());
            for (String s : reader.readLine().split(" ")) {
                this.vehiculos.add(Integer.valueOf(s));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        dp[0][0] = true;
        for (int i = 1; i < vehiculos.size() + 1; i++) {
            for (int l = boatLength; boatLength >= 0; l--) {
                if (!dp[i - 1][l]) {
                    continue;
                }
                // meter coche en babor
                if (l + vehiculos.get(i - 1) <= boatLength) {
                    dp[i][l + vehiculos.get(i - 1)] = true;
                }
                // meter coche en estribor
                if (sumatorio[i] - l <= boatLength) {
                    dp[i][l] = true;
                }
            }
        }
        System.out.println(dp);
    }
}
