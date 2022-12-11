public class Utils {

    public static long leastCommonMultiple(final long fst, final long... nums) {
        long lcm = fst;
        for (final long num : nums) {
            lcm = lcm * (num / greatestCommonDivisor(lcm, num));
        }

        return lcm;
    }

    private static long greatestCommonDivisor(long fst, long snd) {
        while (snd > 0) {
            final long tmp = snd;
            snd = fst % snd;
            fst = tmp;
        }

        return fst;
    }
}
