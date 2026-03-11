package p3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PuntosDyV {

    private static double minDistancia = Double.POSITIVE_INFINITY;
    private static double[][] matrizPuntos;

    // método que lee la matriz del fichero
    public static void leerFichero(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;
        List<double[]> listaTemporal = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.trim().split("\\s+");
            if (partes.length >= 2) {
                // CORRECCIÓN 1: Especificar el índice del array 'partes'
                double x = Double.parseDouble(partes);
                double y = Double.parseDouble(partes);
                listaTemporal.add(new double[] { x, y });
            }
        }
        br.close();
        matrizPuntos = listaTemporal.toArray(new double[listaTemporal.size()][]);
    }

    public static void main(String arg[]) {
        try {
            // CORRECCIÓN 2: Pasar el primer argumento del main
            // Asegúrate de pasar la ruta del archivo al ejecutar el programa
            if (arg.length > 0) {
                leerFichero(arg);
                buscarDistanciaMinima();
                System.out.println("La distancia minima es: " + minDistancia);
            } else {
                System.out.println("Por favor, indica la ruta del archivo como argumento.");
            }
        } catch (Exception e) {
            System.out.println("Error procesando el fichero: " + e.getMessage());
        }
    } 

    public static double buscarDistanciaMinima() {
        // CORRECCIÓN 3: Ordenar por la coordenada X (índice 0)
        Arrays.sort(matrizPuntos, Comparator.comparingDouble(fila -> fila));

        int izd = 0;
        int dcha = matrizPuntos.length - 1;

        return buscarDistanciaMinimaRec(matrizPuntos, izd, dcha);
    }

    public static double buscarDistanciaMinimaRec(double[][] matrizPuntos, int izd, int dcha) {
        // Caso base
        if (dcha - izd <= 2) {
            for (int i = izd; i <= dcha; i++) {
                for (int j = i + 1; j <= dcha; j++) {
                    // CORRECCIÓN 4: Usar para X y para Y
                    double diferencia = Math.sqrt(
                            Math.pow(matrizPuntos[i] - matrizPuntos[j], 2)
                                  + Math.pow(matrizPuntos[i] - matrizPuntos[j], 2));

                    minDistancia = Math.min(minDistancia, diferencia);
                }
            }
            return minDistancia;
        }

        // Llamadas recursivas
        int mitad = (izd + dcha) / 2;
        buscarDistanciaMinimaRec(matrizPuntos, izd, mitad);
        buscarDistanciaMinimaRec(matrizPuntos, mitad + 1, dcha);

        // Combinación (Franja central)
        // CORRECCIÓN 5: Obtener la X del punto medio
        double medioX = matrizPuntos[mitad];

        for (int i = izd; i <= dcha; i++) {
            if (Math.abs(matrizPuntos[i] - medioX) < minDistancia) {

                for (int j = i + 1; j <= dcha; j++) {
                    if (matrizPuntos[j] - matrizPuntos[i] >= minDistancia) {
                        break;
                    }
                    
                    double diferencia = Math.sqrt(
                            Math.pow(matrizPuntos[i] - matrizPuntos[j], 2)
                                  + Math.pow(matrizPuntos[i] - matrizPuntos[j], 2));

                    minDistancia = Math.min(minDistancia, diferencia);
                }
            }
        }

        return minDistancia;
    }

}