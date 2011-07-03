package graph.util;

import java.awt.geom.RectangularShape;
import java.util.Collection;

public class Shapes {
	public static RectangularShape getShape(Collection<RectangularShape> c,
			int x, int y) {
		for (RectangularShape rs : c) {
			if (rs.contains(x, y))
				return rs;
		}
		return null;
	}

	public static void moveShape(RectangularShape rs, int x, int y) {
		rs.setFrameFromCenter(x, y, x + rs.getHeight() / 2, y + rs.getWidth()
				/ 2);
	}
}
