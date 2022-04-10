package com.arthur.entities;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class Armory extends Entity{
	
	protected double x;
	protected double y;
	
	public static BufferedImage armorySprite = Game.spritesheet.getSprite(17, 17, 16, 16);

	public Armory(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		
	}

}
