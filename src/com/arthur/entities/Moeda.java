package com.arthur.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.arthur.main.Game;
import com.arthur.world.Camera;

public class Moeda extends Entity {
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	
	public static BufferedImage moedaSprite = Game.spritesheet.getSprite(0, 17,16,16);
	
	private BufferedImage[] spriteMovimento;

	public Moeda(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		
		spriteMovimento = new BufferedImage[4];
		
		for(int i = 0 ; i < 4; i++ ) {
			spriteMovimento[i] = Game.spritesheet.getSprite(0, 17 + (16 * i), 16, 16);
		}
	}
	
	public void tick(){
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(spriteMovimento[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
	}

}
