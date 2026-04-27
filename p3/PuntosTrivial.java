import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PuntosTrivial {

   static class Punto {
        double x;
        double y;

        public Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "[%.6f, %.6f]", x, y);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: Debes indicar el nombre del fichero.");
            return;
        }

        String nombreFichero = args[0];
        List<Punto> puntos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            
            String primeraLinea = br.readLine();
            if (primeraLinea == null) return;
            int n = Integer.parseInt(primeraLinea.trim());

            for (int i = 0; i < n; i++) {
                String linea = br.readLine();
                if (linea != null) {
                    
                    String[] partes = linea.split(",");

                    double x = Double.parseDouble(partes[0]);
                    double y = Double.parseDouble(partes[1]);
                    puntos.add(new Punto(x, y));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
            return;
        }

        if (puntos.size() < 2) return;

        double distanciaMinima = Double.MAX_VALUE;
        Punto punto1Min = null;
        Punto punto2Min = null;

        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                Punto p1 = puntos.get(i);
                Punto p2 = puntos.get(j);

                double difX = p1.x - p2.x;
                double difY = p1.y - p2.y;

                double distancia = Math.sqrt((difX * difX) + (difY * difY));

                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    punto1Min = p1;
                    punto2Min = p2;
                }
            }
        }

        System.out.printf(Locale.US, "PUNTOS MÁS CERCANOS: %s %s\n", punto1Min, punto2Min);
        System.out.printf(Locale.US, "SU DISTANCIA MÍNIMA = %f\n", distanciaMinima);
    }
}