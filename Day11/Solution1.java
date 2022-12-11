import monkeys.Monkey;
import monkeys.MonkeyFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final List<Monkey> monkeys = MonkeyFactory.createSolution1Monkeys("input.txt");

        for (int i = 0; i < 20; i++) {
            for (final Monkey monkey : monkeys) {
                monkey.playRound(monkeys);
            }
        }

        System.out.println(
                monkeys.stream()
                        .map(Monkey::getInspectionsCount)
                        .sorted(Comparator.reverseOrder())
                        .limit(2)
                        .reduce((fst, snd) -> fst * snd)
                        .orElseThrow()
        );
    }
}
