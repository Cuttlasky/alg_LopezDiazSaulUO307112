import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PuntosDyVTiempos {

    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println("Tiempos para PuntosDyV (Divide y Vencerás):");
        System.out.println("n\t\tTiempo (ms)");
        System.out.println("---------------------------");

        for (int n = 1024; ; n *= 2) {
            
            // Reseteamos las variables globales de tu clase DyV para que no haya basura
            PuntosDyV.minDistancia = Double.MAX_VALUE;
            PuntosDyV.bestP1 = null;
            PuntosDyV.bestP2 = null;

            // Usamos la clase Punto que ya existe en PuntosDyV
            List<PuntosDyV.Punto> puntos = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                double x = rand.nextDouble() * 100;
                double y = rand.nextDouble() * 100;
                puntos.add(new PuntosDyV.Punto(x, y));
            }

            long tiempoInicio = System.currentTimeMillis();

            // Ordenación inicial (es parte del tiempo del algoritmo)
            puntos.sort(new Comparator<PuntosDyV.Punto>() {
                @Override
                public int compare(PuntosDyV.Punto p1, PuntosDyV.Punto p2) {
                    return Double.compare(p1.x, p2.x);
                }
            });

            // Llamamos a tu clase externa
            PuntosDyV.resolver(puntos);

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