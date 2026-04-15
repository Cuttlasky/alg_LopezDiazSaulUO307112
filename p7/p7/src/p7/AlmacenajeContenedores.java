package p7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores {
    private int C;
    private Integer[] objetos;

    private int[] sumaContenedores;
    private int[] asignacionActual;

    private int minContenedores;
    private int[] mejorAsignacion;

    private long llamadasRecursivas;

    public AlmacenajeContenedores(String archivo) {
        leerFichero(archivo);
        sumaContenedores = new int[objetos.length];
        asignacionActual = new int[objetos.length];
        mejorAsignacion = new int[objetos.length];

        minContenedores = objetos.length;
        llamadasRecursivas = 0;
        Arrays.sort(objetos, Collections.reverseOrder());
    }

    private void leerFichero(String archivo) {
        try {
            Scanner scanner = new Scanner(new File(archivo));
            if (scanner.hasNextInt()) {
                C = scanner.nextInt();
            }
            List<Integer> listaObjetos = new ArrayList<>();
            while (scanner.hasNextInt()) {
                listaObjetos.add(scanner.nextInt());
            }
            objetos = listaObjetos.toArray(new Integer[0]);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Fichero no encontrado " + archivo);
            System.exit(1);
        }
    }

    public void resolver() {
        backtrack(0, 0,sumaTotal());
        imprimirSolucion();
    }

    private void backtrack(int indiceObjeto, int contenedoresUsados, int sumaRestante) {

        // LowerBound
        // calcular el numero minimo teorico de contenedores adicionales necesarios

        int lowerBound = (sumaRestante + C -1) / C;
        
        llamadasRecursivas++;
        if (contenedoresUsados + lowerBound >= minContenedores) {
            return;
        }
        if (indiceObjeto == objetos.length) {
            minContenedores = contenedoresUsados;

            System.arraycopy(asignacionActual, 0, mejorAsignacion, 0, objetos.length);
            return;
        }
        for (int j = 0; j < contenedoresUsados; j++) {
            if (sumaContenedores[j] + objetos[indiceObjeto] <= C) {
                sumaContenedores[j] += objetos[indiceObjeto];
                asignacionActual[indiceObjeto] = j;

                backtrack(indiceObjeto + 1, contenedoresUsados);

                sumaContenedores[j] -= objetos[indiceObjeto];
            }
        }

        if (contenedoresUsados + 1 < minContenedores) {
            sumaContenedores[contenedoresUsados] += objetos[indiceObjeto];
            asignacionActual[indiceObjeto] = contenedoresUsados;

            backtrack(indiceObjeto + 1, contenedoresUsados + 1);

            sumaContenedores[contenedoresUsados] -= objetos[indiceObjeto];
        }
    }

    private void imprimirSolucion() {
        System.out.println("Lista de contenedores y objetos contenidos:");

        List<List<Integer>> contenedores = new ArrayList<>();
        for (int i = 0; i < minContenedores; i++) {
            contenedores.add(new ArrayList<>());
        }

        for (int i = 0; i < objetos.length; i++) {
            contenedores.get(mejorAsignacion[i]).add(objetos[i]);
        }

        for (int i = 0; i < minContenedores; i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int obj : contenedores.get(i)) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }

        System.out.println("El número de contenedores necesario es " + minContenedores);
        System.out.println("Llamadas recursivas: " + llamadasRecursivas);
    }

    public static void main(String[] args) {
        AlmacenajeContenedores problema = new AlmacenajeContenedores(args[0]);
        problema.resolver();
    }
}