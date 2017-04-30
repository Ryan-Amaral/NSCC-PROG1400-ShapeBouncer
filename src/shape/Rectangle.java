package shape;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import application.ShapesPanel;

public class Rectangle extends AShape {
	
	// starting width and height
	private int width = 100;
	private int height = 100;

	public Rectangle(int xCenterPos, int yCenterPos){
		this.xPos = xCenterPos - width / 2;
		this.yPos = yCenterPos - width / 2;
		this.centerXPos = xCenterPos;
		this.centerYPos = yCenterPos;
		// random velocity between -10 and 10
		this.xVelocity = random.nextInt(20) - 10;
		this.yVelocity = random.nextInt(20) - 10;
		this.collisionRadius = (int) ((double) width / 1.75);
		this.hitsLeft = AShape.DEFAULT_HITS;
		this.color = colors[random.nextInt(colors.length)];
		this.color2 = colors[random.nextInt(colors.length)];
		this.color3 = colors[random.nextInt(colors.length)];
		this.color4 = colors[random.nextInt(colors.length)];
	}

	@Override
	protected void draw(Graphics2D g, ShapesPanel sp) {
		// decide if plaid, gradient, or single color
		if(hitsLeft < DEFAULT_HITS / 1.7){ // do solid color
			g.setColor(color);
		}else if (hitsLeft < DEFAULT_HITS / 1.3){ // do gradient
			g.setPaint(new GradientPaint(5, 30, color, 35, 100, color2, true));
		}else{ // do plaid
			BufferedImage buffImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
			Graphics2D gBuff = buffImage.createGraphics();		
			gBuff.setColor(color);
			gBuff.fillRect(0,0,10,10);
			gBuff.setColor(color2);
			gBuff.drawRect(1, 1, 6, 6);
			gBuff.setColor(color3);
			gBuff.fillRect(1, 1, 6, 6);
			gBuff.setColor(color4);
			gBuff.fillRect(4, 4, 3, 3);
			g.setPaint(new TexturePaint(buffImage,new java.awt.Rectangle(10,10)));
		}
		
		// decide fill or draw, based on hits left
		if(hitsLeft < DEFAULT_HITS / 2){ // draw if hits are less than half
			g.drawRect(xPos, yPos, width, height);
		}else{ // fill otherwise
			g.fillRect(xPos, yPos, width, height);
		}
	}
	
}
