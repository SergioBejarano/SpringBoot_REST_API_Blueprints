package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.services.impl.SubsamplingFilter;
import org.junit.Assert;
import org.junit.Test;

public class BlueprintFilterTest {
    @Test
    public void shouldRemoveConsecutiveDuplicatesInRedundancyFilter() {
        Blueprint bp = new Blueprint("test", "redundant", new Point[] {
                new Point(0, 0), new Point(1, 1), new Point(1, 1), new Point(2, 2), new Point(2, 2), new Point(3, 3)
        });
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(4, filtered.getPoints().size());
        Assert.assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        Assert.assertEquals(new Point(1, 1), filtered.getPoints().get(1));
        Assert.assertEquals(new Point(2, 2), filtered.getPoints().get(2));
        Assert.assertEquals(new Point(3, 3), filtered.getPoints().get(3));
    }

    @Test
    public void shouldNotRemoveNonConsecutiveDuplicatesInRedundancyFilter() {
        Blueprint bp = new Blueprint("test", "nonconsecutive", new Point[] {
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(1, 1), new Point(3, 3)
        });
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(5, filtered.getPoints().size());
        Assert.assertEquals(new Point(1, 1), filtered.getPoints().get(1));
        Assert.assertEquals(new Point(1, 1), filtered.getPoints().get(3));
    }

    @Test
    public void shouldReturnSamePointsIfNoDuplicatesInRedundancyFilter() {
        Blueprint bp = new Blueprint("test", "noduplicates", new Point[] {
                new Point(0, 0), new Point(1, 1), new Point(2, 2)
        });
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(3, filtered.getPoints().size());
        Assert.assertEquals(new Point(2, 2), filtered.getPoints().get(2));
    }

    @Test
    public void shouldReturnEmptyIfNoPointsInRedundancyFilter() {
        Blueprint bp = new Blueprint("test", "empty", new Point[] {});
        RedundancyFilter filter = new RedundancyFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(0, filtered.getPoints().size());
    }

    @Test
    public void shouldSubsampleEveryOtherPointInSubsamplingFilter() {
        Blueprint bp = new Blueprint("test", "subsample", new Point[] {
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4)
        });
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(3, filtered.getPoints().size());
        Assert.assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        Assert.assertEquals(new Point(2, 2), filtered.getPoints().get(1));
        Assert.assertEquals(new Point(4, 4), filtered.getPoints().get(2));
    }

    @Test
    public void shouldNotFailWithSinglePointInSubsamplingFilter() {
        Blueprint bp = new Blueprint("test", "single", new Point[] {
                new Point(5, 5)
        });
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(1, filtered.getPoints().size());
        Assert.assertEquals(new Point(5, 5), filtered.getPoints().get(0));
    }

    @Test
    public void shouldNotReturnAnyPointsIfEmptyInSubsamplingFilter() {
        Blueprint bp = new Blueprint("test", "empty", new Point[] {});
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(0, filtered.getPoints().size());
    }

    @Test
    public void shouldSubsampleEvenNumberOfPointsInSubsamplingFilter() {
        Blueprint bp = new Blueprint("test", "even", new Point[] {
                new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)
        });
        SubsamplingFilter filter = new SubsamplingFilter();
        Blueprint filtered = filter.filter(bp);
        Assert.assertEquals(2, filtered.getPoints().size());
        Assert.assertEquals(new Point(0, 0), filtered.getPoints().get(0));
        Assert.assertEquals(new Point(2, 2), filtered.getPoints().get(1));
    }
}
