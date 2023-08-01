package exemplo_oo;

public class Game {
	
	private Player player;
	private Inimigo inimigo;
	
	//	Inicializador
	public Game() {
		//player = new Player();
		player = new Tipo1();
		if (player instanceof Tipo1) {
			
		} else if (player instanceof Player) {
			
		}
		//	Verificar player que instanciei
		/*
		if (player == null) {
			System.out.println("O player n foi inicializado!");
			player = new Player();
		}
		
		if (player instanceof Player) {
			System.out.println("O Player foi inicializado com sucesso e faz referencia a classe player");
		}
		*/
		inimigo = new Inimigo();
	}
	//	Metodo para pegar o player privado
	public Player getPlayer() {
		return player;
	}
	//	Metodo para pegar o inimigo privado
	public Inimigo getInimigo() {
		return inimigo;
	}
	

	public static void main (String[] args) {
		Game game = new Game();
		Player player = game.getPlayer();
		player.atacarInimigo(game.getInimigo());
		System.out.println(game.getInimigo().life);
	}
}
