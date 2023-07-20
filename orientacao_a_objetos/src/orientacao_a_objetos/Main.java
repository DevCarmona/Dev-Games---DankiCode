package orientacao_a_objetos;

public class Main {
	
	private static int exemplo() {
		return 10;
	}
	private static String exemplo2() {
		return "Andre";
	}
	
	
	public void outroMetodo(int[] n1, String[] nome) {
		System.out.println(n1[0]);
		System.out.println(nome[0]);
	}
	
	
	
	
	public static void main(String[] args) {
		/*
		new Player2().nascer();
		Player2 player2 = new Player2();
		player2.nascer();
		
		//main.exemplo();
		exemplo();
		*/
		System.out.println(exemplo());
		System.out.println(exemplo2());
		
		int[] n1 = new int[15];
		n1[0] = 15;
		String[] nomes = new String[2];
		nomes[0] = "Jorge";
		new Main().outroMetodo(n1, nomes);
	}
	
	
	
}