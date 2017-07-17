import java.util.Iterator;
import java.util.NoSuchElementException;

import com.ait.lienzo.client.core.types.Point2D;
import com.ait.lienzo.shared.core.types.Direction;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roger600.lienzo.client.toolboxNew.grid.AutoGrid;
import org.roger600.lienzo.client.toolboxNew.grid.FixedLayoutGrid;

@RunWith(LienzoMockitoTestRunner.class)
public class GridsTest {

    @Test
    public void testFixed() {
        FixedLayoutGrid grid = new FixedLayoutGrid(5,
                                                   5,
                                                   Direction.EAST,
                                                   2,
                                                   4);
        int i = 0;
        Iterator<Point2D> iterator = grid.iterator();
        while (iterator.hasNext()) {
            Point2D next = iterator.next();
            // TODO: Replace by assertions.
            System.out.println("[" + i + "] - [" + next.getX() + ", " + next.getY() + "]");
            i++;
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testFixedExceedBounds() {
        FixedLayoutGrid grid = new FixedLayoutGrid(5,
                                                   5,
                                                   Direction.EAST,
                                                   2,
                                                   2);
        Iterator<Point2D> iterator = grid.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next();
    }

    @Test
    public void testAuto() {
        AutoGrid grid = new AutoGrid(5,
                                     5,
                                     AutoGrid.GridDirection.HORIZONTAL,
                                     25);
        int i = 0;
        Iterator<Point2D> points = grid.iterator();
        while (i < 5) {
            Point2D next = points.next();
            // TODO: Replace by assertions.
            System.out.println("[" + i + "] - [" + next.getX() + ", " + next.getY() + "]");
            i++;
        }
    }
}
