package p7;

public class AlmacenajeContenedoresTiemposRyP {

    public static void main(String[] args) {
        // Ajusta la ruta de los ficheros según donde los tengas guardados
        String[] ficheros = {
            "src/CasosPrueba/test00.txt",
            "src/CasosPrueba/test01.txt",
            "src/CasosPrueba/test02.txt",
            "src/CasosPrueba/test03.txt",
            "src/CasosPrueba/test04.txt",
            "src/CasosPrueba/test05.txt",
            "src/CasosPrueba/test06.txt",
            "src/CasosPrueba/test07.txt",
            "src/CasosPrueba/test08.txt",
            "src/CasosPrueba/test09.txt"
        };

        System.out.println("Tiempos de ejecución - Branch and Bound");
        System.out.println("---------------------------------------");

        for (String fichero : ficheros) {
            long inicio = System.currentTimeMillis();

            AlmacenajeContenedoresRyP problema = new AlmacenajeContenedoresRyP(fichero);
            problema.resolver();

            long fin = System.currentTimeMillis();
            long tiempoTotal = fin - inicio;

            if (tiempoTotal > 60000) {
                System.out.println(fichero + "\tFdT");
            } else {
                System.out.println(fichero + "\t" + tiempoTotal + " ms");
            }
        }
    }
}