package p6Extra;

import java.util.ArrayList;
import java.util.List;

public class LaberintoTodas {

    private int[][] laberinto;

    public static void main(String[] args) {
        LaberintoTodas lab = new LaberintoTodas();
        lab.leerLaberinto();
        List<List<Integer>> soluciones = lab.resolver();
        System.out.println(soluciones);
    }
    private List<List<Integer>> resolver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resolver'");
    }
    

    private void leerLaberinto() {
        laberinto=new int[10][10];

    }
}