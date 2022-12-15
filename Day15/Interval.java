import java.util.ArrayList;
import java.util.List;

public record Interval(int from, int to) {

    // List of intervals needs to be sorted!
    public static List<Interval> unionOverlappingIntervals(final List<Interval> intervals) {
        final List<Interval> joined = new ArrayList<>(intervals.size());
        final Interval fst = intervals.get(0);

        int fstFrom = fst.from;
        int fstTo = fst.to;
        for (int i = 1; i < intervals.size(); i++) {
            final Interval snd = intervals.get(i);

            if (Interval.isOverlap(fstFrom, fstTo, snd.from, snd.to)) {
                fstFrom = Math.min(fstFrom, snd.from);
                fstTo = Math.max(fstTo, snd.to);
            } else {
                joined.add(new Interval(fstFrom, fstTo));
                fstFrom = snd.from;
                fstTo = snd.to;
            }
        }
        joined.add(new Interval(fstFrom, fstTo));

        return joined;
    }
    private static boolean isOverlap(final int fstFrom, final int fstTo, final int sndFrom, final int sndTo) {
        return (sndFrom <= fstFrom && sndTo >= fstFrom) || (fstFrom <= sndFrom && fstTo >= sndFrom);
    }

    public int length() {
        return Math.abs(from - to);
    }
}
