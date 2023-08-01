package exemplo_oo;

public class Player {
	
	private int life = 100;
	
	public void atacarInimigo(Inimigo inimigo) {
		//	Chamando metodo do player
		inimigo.life--;
	}

}
