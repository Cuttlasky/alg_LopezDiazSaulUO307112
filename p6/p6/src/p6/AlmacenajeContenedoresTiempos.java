package p6;

public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {
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

        System.out.println("Tiempos de ejecución contenedores");
        System.out.println();
        System.out.println("Fichero\t\tTiempo (ms)");
        System.out.println();

        int i = 0;
        for (String fichero : ficheros) {
            long inicio = System.currentTimeMillis();

            AlmacenajeContenedores problema = new AlmacenajeContenedores(fichero);
            problema.resolver();

            long fin = System.currentTimeMillis();
            long tiempoTotal = fin - inicio;

            if (tiempoTotal > 60000) {
                System.out.println("test " + i + "\tFdT");
            } else {
                System.out.println("test " + i + "\t" + tiempoTotal + " ms");
            }
            i++;
        }
    }
}