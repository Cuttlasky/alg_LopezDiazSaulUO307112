package algstudent.s4;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DevoradorTiempos {
    
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
    
        int[] n_nodos = {4, 8, 16, 32, 64, 100, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        
        System.out.println("n\t\tt Coloreado (ms)");
        System.out.println("--------------------------------");

        for (int n : n_nodos) {
            String rutaArchivo = "src/sols/g" + n + ".json"; 
            
            try (FileReader reader = new FileReader(rutaArchivo)) {
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                
                Map<?, ?> grafoBruto = (Map<?, ?>) jsonObject.get("grafo");

                Map<String, List<String>> grafo = new HashMap<>();
                
                for (Map.Entry<?, ?> entry : grafoBruto.entrySet()) {
                    String nodo = entry.getKey().toString();
                    List<String> vecinosString = new ArrayList<>();
                    
                    for (Object vecino : (List<?>) entry.getValue()) {
                        vecinosString.add(vecino.toString());
                    }
                    grafo.put(nodo, vecinosString);
                }

                long startTime = System.currentTimeMillis();
                
                ColoreoGrafo.realizarVoraz(grafo);
                
                long endTime = System.currentTimeMillis();

                long tiempo = endTime - startTime;
                
                System.out.println(n + "\t\t" + tiempo);

            } catch (Exception e) {
                System.out.println(n + "\t\tError al leer " + rutaArchivo + " -> Motivo: " + e.getMessage());
            }
        }
    }
}