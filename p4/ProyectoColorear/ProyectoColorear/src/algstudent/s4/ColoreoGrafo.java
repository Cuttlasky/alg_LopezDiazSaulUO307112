package algstudent.s4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColoreoGrafo {

    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
        String[] colores = {"red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"};
        
        Map<String, String> solucion = new HashMap<>(); 

        for (String nodo : grafo.keySet()) {
            
            Set<String> coloresVecinos = new HashSet<>();
            List<String> vecinos = grafo.get(nodo);
            
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (solucion.containsKey(vecino)) {
                        coloresVecinos.add(solucion.get(vecino));
                    }
                }
            }
            
            for (String color : colores) {
                if (!coloresVecinos.contains(color)) {
                    solucion.put(nodo, color);
                    break; 
                }
            }
        }
        
        return solucion;
    }
}
