package act2Monitores;

public class Puente {
	private boolean norte;
	private boolean sur;
	private int vecesNorte = 0;
	private int vecesSur = 0;

	public synchronized void desdeNorte() {
		while (sur) {
			System.out.println("Vienen desde el sur, esperaré a que pase.");

			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		norte = true; // Una vez cruza el coche del sur al norte, ponemos el norte en true para poder pasar
	}

	public synchronized void desdeSur() {
		while (norte) {
			System.out.println("Vienen desde el norte, esperaré a que pase.");

			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		sur = true; // Una vez cruza el coche del norte al sur, ponemos el sur en true para poder pasar
	}

	public synchronized void desdeNorteFin() {
		System.out.println("Coche del NORTE pasando el puente.");
		norte = false; // Una vez cruze el puente, este ya no ocupa el puente y se notifica a los hilos
		notifyAll();
		vecesNorte++; // Sumamos 1 al contador de veces desde el Norte.
		System.out.println("Coche del NORTE ha pasado el puente.");
		System.out.println("Ya han pasado el puente " + vecesNorte + " coches desde el NORTE.");

	}

	public synchronized void desdeSurFin() {
		System.out.println("Coche del SUR pasando por el puente.");
		sur = false; // Una vez cruze el puente, este ya no ocupa el puente y se notifica a los hilos
		notifyAll();
		vecesSur++; // Sumamos 1 al contador de coches desde el Sur.
		System.out.println("Coche del SUR ha cruzado.");
		System.out.println("Ya han pasado el puente " + vecesSur + " coches del SUR.");
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class CocheNorte extends Thread {
	private Puente puente; // Objeto Puente dará acceso a los métodos.

	public CocheNorte(Puente puente) {
		this.puente = puente;
	}

	@Override
	public void run() { // Ejecutamos los hilos
		puente.desdeNorte(); // Se inicia de cualquier lado

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}

		puente.desdeNorteFin(); // Termina de cruzar el puente.
	}
}

class CocheSur extends Thread {
	private Puente puente;

	public CocheSur(Puente puente) {
		this.puente = puente;
	}

	@Override
	public void run() {
		puente.desdeSur();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		puente.desdeSurFin();
	}
}
