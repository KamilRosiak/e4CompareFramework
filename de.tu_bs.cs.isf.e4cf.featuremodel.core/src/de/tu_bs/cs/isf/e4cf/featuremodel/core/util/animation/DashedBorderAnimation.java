package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.StyleUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.util.Duration;

public class DashedBorderAnimation implements INodeAnimator{
	private Node node;
	private Duration period;
	private Timeline timeline;
	private int dashLength;
	private int dashDistance;

	/**
	 * Initializes an animation for the Node parameterizing the dashed border and the animation period.
	 * 
	 * @param node affected node
	 * @param dashLength length of the opaque part of the border
	 * @param dashDistance length of the transparent gap between dashes
	 * @param period determines the animation speed/cycle
	 */
	public DashedBorderAnimation(Node node, int dashLength, int dashDistance, double period) {
		this.node = node;
		this.dashLength = dashLength;
		this.dashDistance = dashDistance;
		this.period = Duration.seconds(period);
		this.timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
	}

	@Override
	public void start() {
		IntegerProperty lineOffset = new SimpleIntegerProperty(0);
		lineOffset.addListener((observable, oldValue, newValue) -> {
			StyleUtil.addProperty(node, 
					"-fx-border-style", 
					String.format("segments(%d, %d) phase %d outside line-join round line-cap round", dashLength, dashDistance, newValue));
		});
		KeyValue kv = new KeyValue(lineOffset, dashLength+dashDistance);
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(new KeyFrame(period, kv));
		timeline.play();
	}

	@Override
	public void stop() {
		timeline.stop();
		StyleUtil.removeProperty(node, "-fx-border-style");
	}
	
}
