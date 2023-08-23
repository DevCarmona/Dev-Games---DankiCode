package com.ascstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ascstudios.main.Game;
import com.ascstudios.world.Camera;

public class BulletShoot extends Entity{
	
	private double dx, dy;
	private double spd = 3;
	
	//	Distancia da bala no mapa
	private int life = 40, curLife = 0;
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x+= dx*spd;
		y+= dy*spd;
		
		//	Distancia da bala no mapa
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}

}
