
public class NReinasTiempos {

    public static void main(String[] args) {
        
        // Bucle para los tamaños de tablero: 4, 6, 8, 10, 12, 14
        for (int n = 4; n <= 14; n += 2) {
            
            // Instanciamos el algoritmo
            NReinas algoritmo = new NReinas();
            
            // Cronometramos
            long inicio = System.currentTimeMillis();
            
            algoritmo.resolverNReinas(n);
            
            long fin = System.currentTimeMillis();
            long tiempoTotal = fin - inicio;
            
            // Imprimimos dándole el formato exacto de la captura
            // El %-3d asegura que el "Tiempo" quede perfectamente alineado 
            // tanto para números de 1 cifra (4, 6, 8) como de 2 cifras (10, 12, 14)
            System.out.printf("Tamaño del tablero: %-3d Tiempo: %d\n", n, tiempoTotal);
        }
    }
}