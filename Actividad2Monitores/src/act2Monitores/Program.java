package act2Monitores;

public class Program {

	public static void main(String[] args) {
		Puente puente = new Puente();
		new CocheNorte(puente).start();

		for (int i = 0; i < 10; i++) {
			new CocheSur(puente).start();
			new CocheNorte(puente).start();

		}
	}
}
