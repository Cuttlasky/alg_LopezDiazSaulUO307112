package p3;

public class PuntosDyV {

	private static double minDistancia = Double.POSITIVE_INFINITY;
	private static double[][] matrizPuntos;
	// metodo que lee la matriz del fichero

	// ordenar la matriz por coordenada x
	public static void main(String arg[]) {
		buscarDistanciaMinima();
		System.out.println("La distancia minima es: " + minDistancia);

	} // main

	public static double buscarDistanciaMinima() {
		Arrays.sort(matrizPuntos, Comparator.compare((matriz) -> matriz[0]));

		int izd = 0;
		int dcha = matrizPuntos.length - 1;
		buscarDistanciaMinimaRec(matrizPuntos, izd, dcha);

		medio = izd + (dcha - izd) / 2;
		medio1 = izd + (dcha - izd) / 2 +1;
		if(minDistancia > medio-medio1) //Revisar esto a ver si esta bien el calculo
		return minDistancia;


	}
	
	public static double buscarDistanciaMinimaRec(double[][] matrizPuntos, int izd, int dcha) {
		
		if (dcha - izd == 1) {
			diferencia = Math.sqrt(Math.pow(matrizPuntos[izd][0] - matrizPuntos[dcha][0], 2)
					+ Math.pow(matrizPuntos[izd][1] - matrizPuntos[dcha][1], 2));
			minDistancia = Math.min(minDistancia, diferencia); //Revisar esto a ver si esta bien el calculo
			
		}
		int mitad = (izd + dcha) / 2;
		buscarDistanciaMinimaRec(matrizPuntos, izd, mitad);
		buscarDistanciaMinimaRec(matrizPuntos, mitad + 1, dcha);
		return minDistancia;
	}
	// revisar limite inicial

} // class