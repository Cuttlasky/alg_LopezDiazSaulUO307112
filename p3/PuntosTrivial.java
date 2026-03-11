

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PuntosTrivial {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, indica la ruta del archivo de texto como argumento.");
            return;
        }

        try {
            double[][] puntos = leerFichero(args);
            buscarSolucionTrivial(puntos);
        } catch (Exception e) {
            System.out.println("Error procesando el fichero: " + e.getMessage());
        }
    }

    public static double[][] leerFichero(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        List<double[]> listaTemporal = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.trim().split("\\s+");
            if (partes.length >= 2) {
                double x = Double.parseDouble(partes);
                double y = Double.parseDouble(partes[1]);
                listaTemporal.add(new double[]{x, y});
            }
        }
        br.close();
        return listaTemporal.toArray();
    }

    public static void buscarSolucionTrivial(double[][] puntos) {
        double minDistancia = Double.POSITIVE_INFINITY;
        double[] mejorP1 = null;
        double[] mejorP2 = null;
        int n = puntos.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distancia = Math.sqrt(
                        Math.pow(puntos[i] - puntos[j], 2) 
                      + Math.pow(puntos[i][1] - puntos[j][1], 2)
                );

                if (distancia < minDistancia) {
                    minDistancia = distancia;
                    mejorP1 = puntos[i];
                    mejorP2 = puntos[j];
                }
            }
        }

        if (mejorP1 != null && mejorP2 != null) {
            System.out.println("PUNTOS MÁS CERCANOS: [" + mejorP1 + ", " + mejorP1[1] + "] [" 
                                                        + mejorP2 + ", " + mejorP2[1] + "]");
            System.out.println("SU DISTANCIA MÍNIMA = " + minDistancia);
        }
    }
}