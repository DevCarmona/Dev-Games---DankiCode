package orientacao_a_objetos;

public class Main extends Player {
	//	Atributos
	private String nome= "Andre";
	private int[] numeros;
	public static final int VIDA_MAXIMA = 100;
	
	//	Método construtor
	public Main(int n1, int n2) {
		//	Chama diretamente a classe pai que no caso é Player.
		super(n1, n2);
		numeros = new int[100];
	}
	
	public String getNome() {
		return nome;
	}
	
	public static void main(String[] args) {
		Main m = new Main(100, 200);
		System.out.println(Main.VIDA_MAXIMA);
	}
}