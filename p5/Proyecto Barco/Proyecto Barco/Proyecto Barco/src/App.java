import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "Proyecto Barco\\Proyecto Barco\\src\\files\\test01.txt";
        int length = 0;
        List<Integer> vehiculos = new ArrayList<Integer>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            length = Integer.valueOf(reader.readLine());
            for (String s : reader.readLine().split(" ")) {
                vehiculos.add(Integer.valueOf(s));
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
        Ferry ferry = new Ferry(length, vehiculos);
        ferry.printData();
        ferry.run();
        ferry.printSolutionTable();

        int maxVehiculos = ferry.getMaximumNumberOfVehicles();
        System.out.println(
                "\nHan llegado un total de" + vehiculos.size() + " vehículos  y viajarán" + maxVehiculos + "\n");

        ferry.printPossibleAssignation();
    }
}
