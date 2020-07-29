package de.tu_bs.cs.isf.e4cf.graph.core.elements.view;

import java.util.Iterator;

import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.euclidean.Vector;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.gef.geometry.planar.Polyline;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class GraphicalEdge extends Connection {

	/**
	 * Provides a wider shape for a line by constructing a polygon around it 
	 * 
	 * @author Oliver Urbaniak
	 *
	 */
	public static class GraphicalEdgeShapeProvider extends ShapeOutlineProvider {

		/**
		 * Controls the selection box width enclosing the edge.
		 */
		private static final double WIDTH = 0.5;
		
		/**
		 * Controls the selection box offset from both ends of the connection, e.g., a value of 0.0 while enclose the whole 
		 * connection including its arrows. 
		 */
		private static final double OFFSET = 12.0;
		
		@Override
		public IGeometry get() {
			IGeometry geometry = super.get();
			if (geometry instanceof Polyline) {
				Polyline polyline = (Polyline) geometry;
				Point p1 = polyline.getP1();
				Point p2 = polyline.getP2();
				
				Vector lineVec = new Vector(p1.getDifference(p2)).getNormalized();
				Vector orthLineVec = lineVec.getOrthogonalComplement();
				
				Point[] polyPoints = { 
						translate(translate(p1, lineVec,  OFFSET), orthLineVec,  WIDTH/2.0),
						translate(translate(p1, lineVec,  OFFSET), orthLineVec, -WIDTH/2.0),
						translate(translate(p2, lineVec, -OFFSET), orthLineVec, -WIDTH/2.0),
						translate(translate(p2, lineVec, -OFFSET), orthLineVec,  WIDTH/2.0)
				};
				
				Polygon polygon = new Polygon(polyPoints);
				return polygon;
			}
			
			return null;
		}
		
		/**
		 * Translates point <i>p</i> by a vector <i>vec</i> multiplied by <i>alpha</i>
		 * 
		 * @param p
		 * @param vec
		 * @param alpha
		 * @return
		 */
		private Point translate(Point p, Vector vec, double alpha) {
			return p.getTranslated(vec.getMultiplied(alpha).toPoint());
		}
	}
	
    private static final double LINE_INTERACTION_WIDTH = 20.0;

	public static class ArrowHead extends javafx.scene.shape.Polygon {
        public ArrowHead() {
            super(0, 0, 10, 3, 10, -3);
        }
    }
    
    public GraphicalEdge(float weight) {
        setEndDecoration(createdArrowHead(weight));
        setStartDecoration(createdArrowHead(weight));
        
       // increase interaction line width 
        GeometryNode<?> geometry = getLineGeometry();
        geometry.setClickableAreaWidth(LINE_INTERACTION_WIDTH);
    }

	private GeometryNode<?> getLineGeometry() {
		for (Iterator<Node> it = getChildren().iterator(); it.hasNext(); ) {
        	Node n = it.next();
        	if (n instanceof GeometryNode<?>) {
        		GeometryNode<?> geom = (GeometryNode<?>) n;				
        		return geom;
        	}        	
        }
		return null;
	}
    
    public Group createdArrowHead(float weight) {
    	Group box = new Group();
        ArrowHead arrowHead = new ArrowHead();
        arrowHead.setFill(Color.BLACK);
        box.getChildren().add(arrowHead);
        box.getChildren().add(new Label(String.valueOf(weight)));	
        return box;
    }
}