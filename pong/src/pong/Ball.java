package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	public double x, y;
	public int width, height;
	
	public double dx, dy;
	public double speed = 1.6;
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 5;
		this.height = 5;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)x, (int)y, width, height);
	}
}
