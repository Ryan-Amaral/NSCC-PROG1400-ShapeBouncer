package shape;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import application.ShapesPanel;

public class Cube extends AShape{
	
	private int[] xPoints = new int[8];
	private int[] yPoints = new int[8];

	public Cube(int xCenterPos, int yCenterPos){
		this.xPos = xCenterPos - xCenterPos / 2; // SOMETHING ELSE
		this.yPos = yCenterPos - yCenterPos / 2; // SOMETHING ELSE
		this.centerXPos = xCenterPos;
		this.centerYPos = yCenterPos;
		// random velocity between -10 and 10
		this.xVelocity = random.nextInt(20) - 10;
		this.yVelocity = random.nextInt(20) - 10;
		this.collisionRadius = 53; // SOMETHING ELSE
		this.hitsLeft = AShape.DEFAULT_HITS;
		this.color = colors[random.nextInt(colors.length)];
		this.color2 = colors[random.nextInt(colors.length)];
		this.color3 = colors[random.nextInt(colors.length)];
		this.color4 = colors[random.nextInt(colors.length)];
		
		// the x points that make up this shape
		xPoints[0] = centerXPos - 37; // bl1
		xPoints[1] = centerXPos - 37; // tl1
		xPoints[2] = centerXPos + 12; // tr1
		xPoints[3] = centerXPos + 12; // br1
		xPoints[4] = centerXPos - 12; // bl2
		xPoints[5] = centerXPos - 12; // tl2
		xPoints[6] = centerXPos + 37; // tr2
		xPoints[7] = centerXPos + 37; // br2
		// the y points that make up this shape
		yPoints[0] = centerYPos - 37; // bl1
		yPoints[1] = centerYPos + 12; // tl1
		yPoints[2] = centerYPos + 12; // tr1
		yPoints[3] = centerYPos - 37; // br1
		yPoints[4] = centerYPos - 12; // bl2
		yPoints[5] = centerYPos + 37; // tl2
		yPoints[6] = centerYPos + 37; // tr2
		yPoints[7] = centerYPos - 12; // br2
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
		GeneralPath cube = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		cube.moveTo(xPoints[0], yPoints[0]);
		// make the path for first square of the shape
		for(int i = 1; i < 4; i++){
			cube.lineTo(xPoints[i], yPoints[i]);
		}
		cube.lineTo(xPoints[0], yPoints[0]);
		cube.moveTo(xPoints[4], yPoints[4]);// move the thing to second square
		// make the path for second square of the shape
		for(int i = 5; i < 8; i++){
			cube.lineTo(xPoints[i], yPoints[i]);
		}
		cube.lineTo(xPoints[4], yPoints[4]);
		// connection points
		cube.moveTo(xPoints[0], yPoints[0]);
		cube.lineTo(xPoints[4], yPoints[4]);
		cube.moveTo(xPoints[1], yPoints[1]);
		cube.lineTo(xPoints[5], yPoints[5]);
		cube.moveTo(xPoints[2], yPoints[2]);
		cube.lineTo(xPoints[6], yPoints[6]);
		cube.moveTo(xPoints[3], yPoints[3]);
		cube.lineTo(xPoints[7], yPoints[7]);
		cube.closePath();
		
		// decide fill or draw, based on hits left
		if(hitsLeft < DEFAULT_HITS / 2){ // draw if hits are less than half
			g.draw(cube);
		}else{ // fill otherwise
			g.fill(cube);
		}
	}
	
}
