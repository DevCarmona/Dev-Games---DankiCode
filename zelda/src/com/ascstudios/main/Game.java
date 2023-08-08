package com.ascstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private final int WIDTH = 160;
	private final int HEIGHT = 120;
	private final int SCALE = 4;
	
	private BufferedImage image;
	
	public Game() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Zeldinha Clone");
		frame.add(this);
		frame.setResizable(false);//	N permitir alterar o tamanho da janela.
		frame.pack();
		frame.setLocationRelativeTo(null);//	Janela no centro.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//	Fechar janela.
		frame.setVisible(true);//	Janela inicializada visivel.	
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		
	}
	
	public void render() {
		//	Sequencia de buffers para renderizar os jogos.
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return; //	Quebrando o metodo e retornando.
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(19, 120, 19));
		g.fillRect(0,  0, WIDTH, HEIGHT);
		g= bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime(); //	tempo atual em nanosegundo
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks; //	1 segundo em formato de nano dividido pelo tick.
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {//	Passou 1 segundo após a ultima vez q mostrou a img
				System.out.println("Fps: " + frames);
				frames = 0;
				timer+= 1000;
			}
		}
	}
	
}
