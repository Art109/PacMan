package com.arthur.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.arthur.main.Game;
import com.arthur.main.Sound;
import com.arthur.world.AStar;
import com.arthur.world.Camera;
import com.arthur.world.Vector2i;




public class Enemy extends Entity{
	
	public static BufferedImage Enemysprite = Game.spritesheet.getSprite(65, 0, 16, 16);
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] upEnemy;
	private BufferedImage[] downEnemy;
	
	public static boolean fearMode = false;
	
	public int right_dir = 0, left_dir = 1, up_dir = 2 , down_dir =3;
	public int dir = down_dir;
	
	private int frames = 0, max_frames= 5 , index_x = 0, index_y = 0 , max_index_x = 1, max_index_y = 2;
	private boolean moved = false;

	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightEnemy = new BufferedImage[2];
		leftEnemy = new BufferedImage[2];
		upEnemy = new BufferedImage[3];
		downEnemy = new BufferedImage[3];
		
		for(int i = 0 ; i < 2 ; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(97, 0 + (16 * i), 16, 16);
		}
		
		for(int i = 0 ; i < 2 ; i++) {
			leftEnemy[i] = Game.spritesheet.getSprite(113, 0 + (16 * i), 16, 16);
		}
		
		for(int i = 0 ; i < 3 ; i++) {
			upEnemy[i] = Game.spritesheet.getSprite(97, 0 + (16 * i), 16, 16);
		}
		
		for(int i = 0 ; i < 3 ; i++) {
			downEnemy[i] = Game.spritesheet.getSprite(81, 0 + (16 * i), 16, 16);
		}
	}

	public void tick(){
		depth = 1;
		
		moved = false;
		
		if(fearMode == false) {	
			if(this.isColidingWithPlayer() == false) {
				if(new Random().nextInt(100) < 60) {
					//IA
					followPath(path);
					moved = true;
					if(x < Game.player.getX()) {
						dir = right_dir;
						
					}else if (x > Game.player.getX()) {
						dir = left_dir;
					}
					if(y < Game.player.getY()) {
						dir = up_dir;
					}else if(y > Game.player.getY()) {
						dir = down_dir;
					}
				}
				if(new Random().nextInt(100) < 5) {
					if(path == null || path.size() == 0) {
						Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
						Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
						path = AStar.findPath(Game.world,start,end);
					}
				}
					
		
			}
			else {
	
			}
				
			if(moved) {
				frames++;
				if(frames == max_frames) {
					frames = 0;
					index_x++;
					index_y++;
					if(index_x > max_index_x) {
						index_x = 0;
					}
					if(index_y > max_index_y) {
						index_y = 0;
					}
				}
			}
		}
		else {
			
			if(this.isColidingWithPlayer() == false) {
				if(new Random().nextInt(100) < 60) {
					//IA
					followPath(path);
					moved = true;
					if(x < Game.armory.getX()) {
						dir = right_dir;
						
					}else if (x > Game.armory.getX()) {
						dir = left_dir;
					}
					if(y < Game.armory.getY()) {
						dir = up_dir;
					}else if(y > Game.armory.getY()) {
						dir = down_dir;
					}
				}
				if(new Random().nextInt(100) < 10) {
					if(path == null || path.size() == 0) {
						Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
						Vector2i end = new Vector2i((int)(Game.armory.getX()/16),(int)(Game.armory.getY()/16));
						path = AStar.findPath(Game.world,start,end);
					}
				}
					
				Recuperar();
			}
			else {
				Die();
			}
				
			if(moved) {
				frames++;
				if(frames == max_frames) {
					frames = 0;
					index_x++;
					index_y++;
					if(index_x > max_index_x) {
						index_x = 0;
					}
					if(index_y > max_index_y) {
						index_y = 0;
					}
				}
			}
			
		}
	}
	
	public boolean isColidingWithPlayer() {
		
		Rectangle enemy_current = new Rectangle(this.getX() + maskx,this.getY() + masky,mwidth,mheight);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemy_current.intersects(player);
	}
	
	public void Recuperar() {
		for( int i = 0; i < Game.entities.size() ; i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Armory){
				if(Entity.isColidding(this,current)) {
					fearMode = false;
					return;
				}
			}	
		}
	}
	
	private void Die() {
		for( int i = 0; i < Game.entities.size() ; i++) {
			Entity current = Game.entities.get(i);
			if(current == this){
				Game.entities.remove(i);
			}	
		}
	}
	

	
	public void render(Graphics g) {
		if(dir == right_dir) {
			g.drawImage(rightEnemy[index_x], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
		else if(dir == left_dir) {
			g.drawImage(leftEnemy[index_x], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
		else if(dir == up_dir) {
			g.drawImage(upEnemy[index_y], this.getX()- Camera.x, this.getY() - Camera.y,null);
			} 
		else if(dir == down_dir) {
			g.drawImage(downEnemy[index_y], this.getX()- Camera.x, this.getY() - Camera.y,null);
			}
	}
	
	
}
