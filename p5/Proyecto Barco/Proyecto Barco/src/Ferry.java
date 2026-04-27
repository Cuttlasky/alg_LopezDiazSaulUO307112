import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ferry {
    private int boatLength; // longitud de los carriles del barco
    private List<Integer> vehiculos = new ArrayList<>();
    private boolean[][] dp; // matriz con las posibles soluciones
    private int[] sumatorio; // suma acumulada de las longitudes de los vehiculos

    public Ferry(String fileName) {
        loadData(fileName);
        this.dp = new boolean[vehiculos.size() + 1][boatLength + 1];

        this.sumatorio = new int[vehiculos.size() + 1];

        this.sumatorio[0] = 0;

        inicializarSumatorio(vehiculos);
    }

    private void inicializarSumatorio(List<Integer> vehiculos) {
        for (int i = 1; i < sumatorio.length; i++) {
            this.sumatorio[i] = this.sumatorio[i - 1] + vehiculos.get(i - 1);
        }
    }

    private void loadData(String file) {
        this.vehiculos = new ArrayList<Integer>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            this.boatLength = Integer.valueOf(reader.readLine());
            for (String s : reader.readLine().split(" ")) {
                this.vehiculos.add(Integer.valueOf(s));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        dp[0][0] = true;
        int numeroVehiculos = 0;
        for (int i = 1; i < vehiculos.size() + 1; i++) {

            boolean cargado=false;
            for (int longitud = boatLength; longitud >= 0; longitud--) {

                if (!dp[i - 1][longitud]) { //si la longitud que miramos no es posible, pasamos a la siguiente
                    continue;
                }
                // meter coche en babor
                if (longitud + vehiculos.get(i - 1) <= boatLength) {
                    dp[i][longitud + vehiculos.get(i - 1)] = true;//ejemplo-> si dp[3][8]=true significa
                                                                //  que existe una forma de meter los primeros
                                                                //  3 coches de forma que babor ocupe 8 metros
                    cargado=true;
                }
                // meter coche en estribor
                if (sumatorio[i] - longitud <= boatLength) {
                    dp[i][longitud] = true; 
                    cargado=true;
                }
                
            }
            if(!cargado){ //si no conseguimos cargar ningun coche, detenemos la busqueda
                break;
            }
            numeroVehiculos=i;
        }
        imprimirMatriz(numeroVehiculos);
        reconstruirSolucion(numeroVehiculos);
    }

    private void reconstruirSolucion(int numeroVehiculos) {
        if (numeroVehiculos == 0) {
            System.out.println("No hay solución (ningún vehículo cabe).");
            return;
        }

        
        int espacioBabor = -1;
        for (int longitud = boatLength; longitud >= 0; longitud--) {
            if (dp[numeroVehiculos][longitud]) {
                espacioBabor = longitud;
                break; // Encontramos la configuración final de Babor
            }
        }
        // Guardamos esta ocupación final para imprimirla al terminar el proceso
        int ocupacionBaborFinal = espacioBabor;
        
        // hacemos un array para almacenar las solucione, como estamos recorriendo al reves, 
        // este array va a haber que imprimirlo al revés
        String[] asignaciones = new String[numeroVehiculos];

        for (int i = numeroVehiculos; i > 0; i--) {
            int v = vehiculos.get(i - 1);

            if (espacioBabor - v >= 0 && dp[i - 1][espacioBabor - v]) {

                asignaciones[i - 1] = "Vehículo " + i + " (longitud " + v + ") a babor.";
                espacioBabor = espacioBabor - v; //hay que restarle la longitud del vehiculo para la siguiente 
                                                 //comprobacion (estamos retrocediendo en el tiempo)
            } else {
                asignaciones[i - 1] = "Vehículo " + i + " (longitud " + v + ") a estribor.";
                //no restamos nada porque meterlo a estribor no quita sitio en babor
            }
        }

        System.out.println("\nPosible asignación:");
        for (int i = 0; i < numeroVehiculos; i++) {
            System.out.println(asignaciones[i]);
        }

        int espacioEstribor = sumatorio[numeroVehiculos] - ocupacionBaborFinal; //ocupacionEstribor=total-ocupacionBabor
        
        System.out.println("Ocupación final: Babor " + ocupacionBaborFinal + "m / Estribor " + espacioEstribor + "m (válido <= " + boatLength + ").");
    }

    private void imprimirMatriz(int numeroVehiculos) {
        System.out.println("Han llegado un total de  "+ vehiculos.size()+" vehiculos"+ " ("+numeroVehiculos+" viajaran)");
        
        System.out.println("\nTabla con los cálculos realizados:");
        
        System.out.print("V/L\t");
        for (int l = 0; l <= boatLength; l++) {
            System.out.print(l + "\t");
        }
        System.out.println();

        for (int i = 0; i <= numeroVehiculos; i++) {
            System.out.print(i + "\t"); // Número del vehículo
            for (int l = 0; l <= boatLength; l++) {
                // Si la celda es true imprimimos T, si es false imprimimos F
                System.out.print((dp[i][l] ? "T" : "F") + "\t");
            }
            System.out.println(); 
        }
    }
    
}
