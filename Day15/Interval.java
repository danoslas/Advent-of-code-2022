import java.util.ArrayList;
import java.util.List;

public record Interval(int from, int to) {

    /**
     * Unions all overlapping and/or directly adjacent intervals.
     * [1, 4] and [3, 5] -> [1, 5]
     * [1, 3] and [4, 5] -> [1, 5]
     * [1, 2] and [4, 5] -> [1, 2] and [4, 5]
     *
     * @param intervals list of intervals to be united.
     *                  <b>The list must be sorted by the {@link Interval#from} value!</b>
     * @return List of interval unions
     */
    public static List<Interval> unionIntervals(final List<Interval> intervals) {
        final List<Interval> joined = new ArrayList<>(intervals.size());
        final Interval fst = intervals.get(0);

        int fstFrom = fst.from;
        int fstTo = fst.to;
        for (int i = 1; i < intervals.size(); i++) {
            final Interval snd = intervals.get(i);

            if (fstTo + 1 >= snd.from) {
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
