package shape;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import application.ShapesPanel;

public class Triangle extends AShape{
	
	private int[] xPoints = new int[3];
	private int[] yPoints = new int[3];
	
	public Triangle(int xCenterPos, int yCenterPos){
		this.xPos = xCenterPos - xCenterPos / 2; // SOMETHING ELSE
		this.yPos = yCenterPos - yCenterPos / 2; // SOMETHING ELSE
		this.centerXPos = xCenterPos;
		this.centerYPos = yCenterPos;
		// random velocity between -10 and 10
		this.xVelocity = random.nextInt(20) - 10;
		this.yVelocity = random.nextInt(20) - 10;
		this.collisionRadius = 40;
		this.hitsLeft = AShape.DEFAULT_HITS;
		this.color = colors[random.nextInt(colors.length)];
		this.color2 = colors[random.nextInt(colors.length)];
		this.color3 = colors[random.nextInt(colors.length)];
		this.color4 = colors[random.nextInt(colors.length)];
		
		// the x points that make up this shape
		xPoints[0] = centerXPos + 0; // top middle
		xPoints[1] = centerXPos + 45; // bottom right
		xPoints[2] = centerXPos - 45; // bottom left
		// the y points that make up this shape
		yPoints[0] = centerYPos + 45; // top middle
		yPoints[1] = centerYPos - 30; // bottom right
		yPoints[2] = centerYPos - 30; // bottom left
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
		
		// help from here: https://docs.oracle.com/javase/tutorial/2d/geometry/arbitrary.html
		GeneralPath triangle = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		triangle.moveTo(xPoints[0], yPoints[0]);
		// make the path for the shape
		for(int i = 1; i < xPoints.length; i++){
			triangle.lineTo(xPoints[i], yPoints[i]);
		}
		triangle.closePath(); // makes it a polygon
		// decide fill or draw, based on hits left
		if(hitsLeft < DEFAULT_HITS / 2){ // draw if hits are less than half
			g.draw(triangle);
		}else{ // fill otherwise
			g.fill(triangle);
		}
	}

}
