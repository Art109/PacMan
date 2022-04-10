package com.arthur.entities;

import java.awt.image.BufferedImage;

import com.arthur.main.Game;

public class Weapon extends Entity{
	
	public static BufferedImage weaponSprite = Game.spritesheet.getSprite(0, 81, 16, 16);

	public Weapon(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}
	
	

}
