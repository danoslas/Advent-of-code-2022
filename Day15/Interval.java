import java.util.ArrayList;
import java.util.List;

public record Interval(int from, int to) {

    /**
     * Unions all overlapping intervals.
     *
     * @param intervals list of intervals to be united.
     *                  <b>The list must be sorted by the {@link Interval#from} value!</b>
     * @return List of interval unions
     */
    public static List<Interval> unionOverlappingIntervals(final List<Interval> intervals) {
        final List<Interval> joined = new ArrayList<>(intervals.size());
        final Interval fst = intervals.get(0);

        int fstFrom = fst.from;
        int fstTo = fst.to;
        for (int i = 1; i < intervals.size(); i++) {
            final Interval snd = intervals.get(i);

            if (fstTo >= snd.from) {
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

    public int length() {
        return Math.abs(from - to);
    }
}
