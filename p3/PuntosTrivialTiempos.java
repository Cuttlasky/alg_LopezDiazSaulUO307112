import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuntosTrivialTiempos {

    public static void main(String[] args) {
        Random rand = new Random();

        for (int n = 1024; ; n *= 2) {
            
            List<PuntosTrivial.Punto> puntos = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double x = rand.nextDouble() * 100;
                double y = rand.nextDouble() * 100;
                puntos.add(new PuntosTrivial.Punto(x, y));
            }

            long tiempoInicio = System.currentTimeMillis();

            PuntosTrivial.resolver(puntos);

            long tiempoFin = System.currentTimeMillis();
            long tiempoTotal = tiempoFin - tiempoInicio;

            if (tiempoTotal > 60000) {
                System.out.println(n + "\t\tFdT");
                break; 
            } else {
                System.out.println(n + "\t\t" + tiempoTotal);
            }
        }
    }
}