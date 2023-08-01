package graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 3;
	
	private BufferedImage image;
	
	private Spritesheet sheet;
	private BufferedImage[] player;
	private int frames = 0;
	private int maxframes = 10;
	private int curAnimation = 0, maxAnimation = 3;
	
	public Game() {
		sheet = new Spritesheet("/spritesheet.png");
		player = new BufferedImage[4];
		player[0] = sheet.getSprite(0, 0, 16, 16);
		player[1] = sheet.getSprite(16, 0, 16, 16);
		player[2] = sheet.getSprite(32, 0, 16, 16);
		player[3] = sheet.getSprite(48, 0, 16, 16);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		//	Redimensionar janela.
		frame.setResizable(false);
		//	Calcula dimensoes e mostra
		frame.pack();
		//	Janela ficar no centro.
		frame.setLocationRelativeTo(null);
		//	Quando pedir para fechar ele fecha mesmo.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//	Visualizar está visível.
		frame.setVisible(true);		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		frames++;
		if (frames > maxframes) {
			frames = 0;
			curAnimation++;
			if (curAnimation > maxAnimation) {
				curAnimation = 0;
			}
		}
	}
	
	public void render() {
		//	BufferStrategy - Sequencia de buffers para otimizar a renderização.
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(143,188,143));
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		//	Renderização do Jogo
		Graphics2D g2 = (Graphics2D) g;
		//	g2.rotate(Math.toRadians(0), 20+8, 20+8); //	8 = metade do tamanho do boneco.
		g2.drawImage(player[curAnimation], 20, 20, null);
		/**/
		g.dispose(); //limpar dados na imagem que foram usados antes, melhora performance
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}
	
	public void run() {
		//	Game loop mais avançado e mais eficiente.
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}	
		
		stop();
	}	
}