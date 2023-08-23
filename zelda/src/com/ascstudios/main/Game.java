package com.ascstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.ascstudios.entities.BulletShoot;
import com.ascstudios.entities.Enemy;
import com.ascstudios.entities.Entity;
import com.ascstudios.entities.Player;
import com.ascstudios.graficos.Spritesheet;
import com.ascstudios.graficos.UI;
import com.ascstudios.world.Camera;
import com.ascstudios.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	private final int SCALE = 3;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;	
	
	public static Random rand;
	
	public UI ui;
	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		//	Inicializando objetos.
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, 16,16, spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/map.png");
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
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
	}
	
	public void render() {
		//	Sequencia de buffers para renderizar os jogos.
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return; //	Quebrando o metodo e retornando.
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		//	Renderização do jogo
		// Graphics 2D g2 = (Graphics2D) g;
		world.render(g);
		for(int i = 0 ; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size();i++) {
			bullets.get(i).render(g);
		}
		ui.render(g);
		/**/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE , HEIGHT * SCALE, null);
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("Munição: " + player.ammo, 580, 20);
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime(); //	tempo atual em nanosegundo
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks; //	1 segundo em formato de nano dividido pelo tick.
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();//	Da um foco na janela do jogo.
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

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_P) {
			player.shoot = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX() / 3) + Camera.x;
		player.my = (e.getY() / 3) + Camera.y;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}