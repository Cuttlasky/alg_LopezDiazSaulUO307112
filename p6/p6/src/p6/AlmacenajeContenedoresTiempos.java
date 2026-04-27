package p6;

public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {
        String fichero = "src/CasosPrueba/test01.txt";

        long inicio = System.currentTimeMillis();

        AlmacenajeContenedores problema = new AlmacenajeContenedores(fichero);
        problema.resolver();

        long fin = System.currentTimeMillis();
        long tiempoTotal = fin - inicio;

        System.out.println();
        System.out.println(tiempoTotal + " ms");
        System.out.println();
    }
}