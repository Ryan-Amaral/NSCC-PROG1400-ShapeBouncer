package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import shape.AShape;

/**
 * The panel where everything goes down. This is where components get created from,
 * where the timer is, etc.
 * @author Ryan
 *
 */
public class ShapesPanel extends JPanel {

	// the delay for the timer
	private final int ANIMATION_DELAY = 30;
	private Timer animationTimer = new Timer(ANIMATION_DELAY,new TimerHandler());
	private List<AShape> movingShapes = new ArrayList<AShape>(); // initialize shape list for shapes we move around the screen
	private Random random = new Random();
	
	/**
	 * Create the panel.
	 */
	public ShapesPanel() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent click) {
				// spawn a new shape where the click was
				movingShapes.add(AShape.createAShape(random.nextInt(6), click.getX(), click.getY()));
			}
		});
		// start timer
		animationTimer.start();
		this.setBackground(Color.BLACK);
		// add 4 shapes to start
		movingShapes.add(AShape.createAShape(random.nextInt(6), 55, 30));
		movingShapes.add(AShape.createAShape(random.nextInt(6), 500, 50));
		movingShapes.add(AShape.createAShape(random.nextInt(6), 400, 55));
		movingShapes.add(AShape.createAShape(random.nextInt(6), 55, 200));
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		// need to update this every time the frame changes
		super.paintComponent(g2d);
		this.setBackground(Color.BLACK);

		// update all shapes
		for(int i = 0; i < movingShapes.size(); i++){
			int xMove = movingShapes.get(i).getTotalXFromStart();
			int yMove = movingShapes.get(i).getTotalYFromStart();
			g2d.translate(xMove, yMove); // apply translation
			movingShapes.get(i).update(g2d, this); // update the shape
			g2d.translate(-xMove, -yMove);// revert translation
			// break to not get out of bounds exception
			if(i >= movingShapes.size()){
				break;
			}
		}

	}
	
	public List<AShape> getMovingShapes() {
		return movingShapes;
	}

	/**
	 * Timer for most things.
	 * @author Ryan
	 *
	 */
	private class TimerHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			repaint(); // apparently calls paintComponent
		}
		
	}

}
