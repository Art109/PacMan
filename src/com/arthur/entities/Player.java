package com.arthur.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arthur.graficos.UI;
import com.arthur.main.Game;
import com.arthur.world.Camera;
import com.arthur.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	private int frames = 0, maxFrames= 8 , index = 0 , maxIndex = 1, iFrame = 0, maxIFrame = 60;

	private int vida = 3 ;
	
	public BufferedImage[] rightSprite;
	public BufferedImage[] leftSprite;
	public BufferedImage coracaoSprite = Game.spritesheet.getSprite(17,33, 16,16);
	
	public int lastDir = 1;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightSprite = new BufferedImage[2];
		leftSprite = new BufferedImage[2];
		
		for(int i = 0; i < 2 ;i++) {
			rightSprite[i] = Game.spritesheet.getSprite(33, 0 + (16 * i), 16, 16);
		}
		
		for(int i = 0; i < 2 ;i++) {
			leftSprite[i] = Game.spritesheet.getSprite(48, 0 + (16 * i), 16, 16);
		}
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = -1;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
		}
		
		pegaMoeda();
		pegaWeapon();
		colideEnemy();
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void pegaMoeda() {
		for( int i = 0; i < Game.moedas.size() ; i++) {
			Entity current = Game.moedas.get(i);
			if(Entity.isColidding(this,current)) {
				Game.moedasAtual++;
				Game.moedas.remove(i);
				return;
			}
		}
	}
	
	public void pegaWeapon() {
		for( int i = 0; i < Game.entities.size() ; i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Weapon) {
				if(Entity.isColidding(this,current)) {
					Enemy.fearMode = true;
					Game.entities.remove(i);
					return;
				}
			}	
		}
	}

	public void colideEnemy() {
		for( int i = 0; i < Game.entities.size() ; i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Enemy) {
				if(Entity.isColidding(this,current)) {
					vida--;
					iFrame++;
					if(iFrame == maxIFrame){
						iFrame = 0;
					}
					return;
				}
			}	
		}
	}
	
	public void render(Graphics g) {
		if(lastDir == 1) {
			g.drawImage(rightSprite[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else {
			g.drawImage(leftSprite[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		for(int i = 0 ; i < vida ; i++){
			g.drawImage(coracaoSprite, 150 + (12 * i),220, null);
		}

		
	}

	


}
