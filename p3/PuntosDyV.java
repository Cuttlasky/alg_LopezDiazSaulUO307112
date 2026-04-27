import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PuntosDyV {

    static class Punto {
        double x;
        double y;

        public Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%.6f, %.6f]", x, y);
        }
    }

    static Punto bestP1 = null;
    static Punto bestP2 = null;
    static double minDistancia = Double.MAX_VALUE;

    public static void main(String[] args) {
        if (args.length < 1) return;

        List<Punto> puntos = leerFichero(args[0]);
        if (puntos.size() < 2) return;

        puntos.sort(new Comparator<Punto>() {
            @Override
            public int compare(Punto p1, Punto p2) {
                if (p1.x < p2.x) {
                    return -1; // p1 es menor, así que va antes
                } else if (p1.x > p2.x) {
                    return 1;  // p1 es mayor, así que va después
                } else {
                    return 0;  // son  iguales
                }
            }
        });
        //llamamos al algoritmo
        minDistancia = resolver(puntos);

        System.out.println("PUNTOS MÁS CERCANOS: " + bestP1 + " " + bestP2);
        System.out.println("SU DISTANCIA MÍNIMA = " + minDistancia);
    }

    private static double resolver(List<Punto> lista) {
        int n = lista.size();

        // caso base-> si hay pocos puntos, lo resuelve por fuerza bruta
        if (n <= 3) {
            return calcularFuerzaBruta(lista);
        }
        //DIVIDE: busca la mitad y divide
        int mid = n / 2;
        Punto pMedio = lista.get(mid);

        // VENCERÁS: Buscamos récords en la izquierda y en la derecha
        double dIzq = resolver(lista.subList(0, mid));
        double dDer = resolver(lista.subList(mid, n));

        //miramos la mejor distancia de las dos mitades
        double mejorDistancia = Math.min(dIzq, dDer);

        //COMBINAR: miramos lo del medio de las dos mitades por si hubiera un punto en esa distancia
        List<Punto> centro = new ArrayList<>();
        for (Punto p : lista) {
            // Si el punto está a menos de 'delta' de la línea central, lo miramos
            if (Math.abs(p.x - pMedio.x) < mejorDistancia) {
                centro.add(p);
            }
        }

        // Ordenamos los puntos del centro por su altura, para ir mas rapido
        centro.sort(new Comparator<Punto>() {
            @Override
            public int compare(Punto p1, Punto p2) {
                if (p1.y < p2.y) {
                    return -1; // p1 es menor, así que va antes
                } else if (p1.y > p2.y) {
                    return 1;  // p1 es mayor, así que va después
                } else {
                    return 0;  // son  iguales
                }
            }
        });

        // Comparamos los puntos del centro entre sí
        for (int i = 0; i < centro.size(); i++) {
            for (int j = i + 1; j < centro.size(); j++) {

                // si la distancia entre los puntos del centro es mayor que la de los lados, no miramos mas
                if ((centro.get(j).y - centro.get(i).y) >= mejorDistancia) 
                    break;

                double distCentro = calcularDist(centro.get(i), centro.get(j));

                if (distCentro < mejorDistancia) {
                    mejorDistancia = distCentro;
                    // si esta distancia es la mejor de todas, es la que debemos devolver y ademas 
                    // tenemos que actualizar la variable global
                    if (distCentro < minDistancia) {
                        minDistancia = distCentro;
                        bestP1 = centro.get(i);
                        bestP2 = centro.get(j);
                    }
                }
            }
        }
        return mejorDistancia;
    }

    // Métodos auxiliares simples
    private static double calcularDist(Punto p1, Punto p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy); //raiz cuadrada de pitagoras normal
    }

    //es el metodo que se invoca en el caso base para mirar la distancia entre dos puntos
    private static double calcularFuerzaBruta(List<Punto> lista) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                double d = calcularDist(lista.get(i), lista.get(j));
                if (d < min) {
                    min = d;
                    if (d < minDistancia) {
                        minDistancia = d;
                        bestP1 = lista.get(i);
                        bestP2 = lista.get(j);
                    }
                }
            }
        }
        return min;
    }

    private static List<Punto> leerFichero(String ruta) {
        List<Punto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.readLine(); // Saltamos la N [cite: 210]
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                lista.add(new Punto(Double.parseDouble(p[0]), Double.parseDouble(p[1])));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}