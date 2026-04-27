package p6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores {
    private int capacidad;
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
                capacidad = scanner.nextInt();
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
        backtrack(0, 0);
        imprimirSolucion();
    }

    private void backtrack(int indiceObjeto, int contenedoresUsados) {
        llamadasRecursivas++;
        if (contenedoresUsados >= minContenedores) { //si la opcion que estamos mirando es peor que la mejor, no lo miramos
            return;
        }
        if (indiceObjeto == objetos.length) { //si llegas al final de la lista de objetos, es la mejor solucion, porque 
                                              //llegamos sin pasar por el if de arriba
            minContenedores = contenedoresUsados;

            System.arraycopy(asignacionActual, 0, mejorAsignacion, 0, objetos.length);
            return;
        }
        //mira la opcion de meter el objeto en un contenedor que ya esta creado
        for (int j = 0; j < contenedoresUsados; j++) {
            if (sumaContenedores[j] + objetos[indiceObjeto] <= capacidad) {

                //Avanzar 
                sumaContenedores[j] += objetos[indiceObjeto];//(meter en el contenedor j el objeto que estas insertando)
                asignacionActual[indiceObjeto] = j; //añade a la asignacion actual este contenedor

                // llamada recursiva para ver todas las opciones
                backtrack(indiceObjeto + 1, contenedoresUsados);

                //volver atras para hacer como si no hubieramos hecho nada 
                //y comprobar todas las opciones posibles
                sumaContenedores[j] -= objetos[indiceObjeto];
            }
        }//crea un contenedor nuevo para el objeto que queremos meter
        if (contenedoresUsados + 1 < minContenedores) {
            //[C, C , C, C, C, N, N] 
            //al ponerlo en el indice "contenedoresUsados" es al final de sumaContenedores, entonces se crea
            //uno nuevo donde se mete el objeto que queremos meter

            //Avanzar 
            sumaContenedores[contenedoresUsados] += objetos[indiceObjeto];
            asignacionActual[indiceObjeto] = contenedoresUsados;
            // llamada recursiva para ver todas las opciones
            backtrack(indiceObjeto + 1, contenedoresUsados + 1); //ahora tenemos un contenedor mas, 
                                                                 //por eso contenedoresUsados + 1
            //volver atras para hacer como si no hubieramos hecho nada 
            //y comprobar todas las opciones posibles
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