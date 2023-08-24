package com.ascstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.ascstudios.main.Game;
import com.ascstudios.main.Sound;
import com.ascstudios.world.Camera;
import com.ascstudios.world.World;

public class Player extends Entity{
	
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.7;
	
	private int frames = 0, maxFrames= 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private BufferedImage playerDamage;
	
	private boolean hasGun = false;
	
	public int ammo = 0;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	public boolean shoot = false, mouseShoot = false;
	
	public double life = 100, maxLife = 100;
	public int mx, my;
	
	public boolean jump = false;
	public boolean isJumping = false;
	
	public int Z = 0;
	
	public int jumpFrames = 30, jumpCur = 0;
	
	public boolean jumpUp = false, jumpDown = false;
	
	public int jumpspd = 2;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		playerDamage = Game.spritesheet.getSprite(0, 32, 16, 16);
		
		for(int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
	}
	
	public void tick() {
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if(isJumping == true) {
			if (jumpUp) {
				jumpCur += 2;
			} else if (jumpDown) {
				jumpCur -= 2;
				if (jumpCur <= 0) {
					isJumping = false;
					jumpDown = false;
					jumpUp = false;
				}
			}
			Z = jumpCur;
			if (jumpCur >= jumpFrames) {
				jumpUp = false;
				jumpDown = true;
				// Verificação
				// System.out.println("Chegou na altura máxima");
			}
		}
		
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY(), Z)) {
			moved = true;
			dir = right_dir;
			x+= speed;
		}else if(left && World.isFree((int)(x-speed), this.getY(), Z)) {
			moved = true;
			dir = left_dir;
			x-= speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed), Z)) {
			moved = true;
			y-= speed;
		}else if(down && World.isFree(this.getX(), (int)(y+speed), Z)) {
			moved = true;
			y+= speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		checkCollisionAmmo();
		checkCollisionLifePack();
		checkCollisionGun();
		
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames== 8) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			if(hasGun && ammo > 0) {
				ammo--;				
				Sound.pei.play();
				//	Criar bala e atirar
				int dx = 0;
				int px = 0;
				int py = 6;
				if(dir == right_dir) {
					px = 16;
					dx = 1;
				}else {
					px = -3;
					dx = -1;
				}
				
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
				Game.bullets.add(bullet);
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;
			if(hasGun && ammo > 0) {
				ammo--;
				//	Criar bala e atirar com mouse
				int px = 0, py = 8;
				double angle = 0;
				if(dir == right_dir) {
					px = 18;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}else {
					px = -8;
					angle = Math.atan2(my - (this.getY() + py - Camera.y), mx - (this.getX() + px - Camera.x));
				}
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
			}
		}
		
		if(life <= 0) {
			//	Game Over!
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		updateCamera();
	}
	
	public void updateCamera() {
		//	Deixar a camera no centro do jogador
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void checkCollisionGun() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				if(Entity.isColliding(this, atual)) {
					hasGun = true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColliding(this, atual)) {
					ammo+= 20;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionLifePack() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof LifePack) {
				if(Entity.isColliding(this, atual)) {
					life+= 10;
					if(life > 100) {
						life = 100;
						Game.entities.remove(atual);
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - Z, null);	
				if(hasGun) {
					//	Desenhar arma para direita
					g.drawImage(Entity.GUN_RIGHT, this.getX()+ 5 - Camera.x, this.getY()+ 1 - Camera.y - Z, null);
				}
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - Z, null);
				if(hasGun) {
					//	Desenhar arma para esquerda
					g.drawImage(Entity.GUN_LEFT, this.getX() - 5 - Camera.x, this.getY() + 2 - Camera.y - Z, null);
				}
			}			
		}else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y - Z,null);
			if(hasGun) {
				if(dir == left_dir) {
					g.drawImage(Entity.GUN_DAMAGE_LEFT, this.getX() - 8 - Camera.x, this.getY() - Camera.y - Z, null);
				}else {
					g.drawImage(Entity.GUN_DAMAGE_RIGHT, this.getX() + 8 - Camera.x, this.getY() - Camera.y - Z, null);
					
				}
			}
		}
		if(isJumping) {
			g.setColor(Color.black);
			g.fillOval(this.getX() - Camera.x + 5, this.getY() - Camera.y + 16, 8, 8);
		}
	}
}