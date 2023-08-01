package logica_games;

import java.util.ArrayList;

public class Game implements Runnable {
	
	private boolean isRunning;
	private Thread thread;
	
	private ArrayList<Entity> entities = new ArrayList<>();
	
	public Game() {
		//	Construtor
		entities.add(new Entity());
		entities.add(new Entity());
		entities.add(new Entity());
		entities.add(new Entity());
		for (int i = 0; i < entities.size() ; i++) {
			Entity e = entities.get(0);
		}
		
	}
		
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void tick() {
		//	Atualizar ou update do jogo.
		//System.out.println("Tick");
	}
	
	public void render() {
		//	Renderizar o jogo.
		//System.out.println("Render");
	}

	@Override
	public void run() {
		//	Aqui iremos trabalhar com looping
		while (isRunning) {
			tick();
			render();
			
			//	usado por iniciante
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
	}
}
