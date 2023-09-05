 package com.ascstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ascstudios.main.Game;
import com.ascstudios.main.Sound;
import com.ascstudios.world.AStar;
import com.ascstudios.world.Camera;
import com.ascstudios.world.Vector2i;
import com.ascstudios.world.World;

public class Enemy extends Entity{
	
	private double speed = 0.7;
	
	private int frames = 0, maxFrames= 20, index = 0, maxIndex = 1;
	
	private BufferedImage[] sprites;
	
	private int life = 2;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) { //	BufferedImage[] sprite
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		this.sprites[0] = Game.spritesheet.getSprite(16, 16, 16, 16); //	sprite[0]
		this.sprites[1] = Game.spritesheet.getSprite(0, 16, 16, 16); //		sprite[1]
	}
	
	public void tick() {
		// maskx = 8; masky = 9; maskw = 5; masky = 5;
		/*
		if (this.calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 40) {
			if (isCollidingWithPlayer() == false) {
				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY(), damageCurrent)
						&& !isColliding((int) (x + speed), this.getY())) {
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY(), damageCurrent)
						&& !isColliding((int) (x - speed), this.getY())) {
					x -= speed;
				}
				if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed), damageCurrent)
						&& !isColliding(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed), damageCurrent)
						&& !isColliding(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			} else {
				// Estamos colidindo
				if (Game.rand.nextInt(100) < 10) {
					Sound.hurtEffect.setVolume(0.08f);
					Sound.hurtEffect.play();
					Game.player.life -= Game.rand.nextInt(3);
					Game.player.isDamaged = true;
					// System.out.println("Vida: " + Game.player.life);
				}
			}
			else{}
		}
		*/
		//	A* Algoritmo
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i ((int)(x / 16), (int)(y / 16));
			Vector2i end = new Vector2i ((int)(Game.player.x / 16), (int)(Game.player.y / 16));
			path = AStar.findPath(Game.world, start, end);
		}
		followPath(path);
		
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		
		collidingBullet();
		
		if(life <= 0) {
			destroySelf();
			return;
		}
		
		if(isDamaged) {
			this.damageCurrent++;
			if(this.damageCurrent == this.damageFrames) {
				this.damageCurrent = 0;
				this.isDamaged = false;
			}
		}
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullets.size();i++) {
			Entity e = Game.bullets.get(i);
			if (Entity.isColliding(this, e)) {
				isDamaged = true;
				life--;
				Game.bullets.remove(i);
				return;
			}
		}
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, mwidth, mheight);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		
		return enemyCurrent.intersects(player);
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			g.drawImage(Entity.ENEMY_FEEDBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}
}