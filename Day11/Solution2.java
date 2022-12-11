import monkeys.Monkey;
import monkeys.MonkeyFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        final List<Monkey> monkeys = new ArrayList<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            do {
                br.readLine();
                monkeys.add(MonkeyFactory.createSolution2Monkey(br.readLine(), br.readLine(),
                        br.readLine(), br.readLine(), br.readLine()));
            } while (br.readLine() != null);
        }
        setMonkeysLcmDivisors(monkeys);

        for (int i = 0; i < 10_000; i++) {
            for (final Monkey monkey : monkeys) {
                monkey.playRound(monkeys);
            }
        }

        System.out.println(
                monkeys.stream()
                        .map(Monkey::getInspectionsCount)
                        .sorted(Comparator.reverseOrder())
                        .limit(2)
                        .mapToLong(Integer::longValue)
                        .reduce((fst, snd) -> fst * snd)
                        .orElseThrow()
        );
    }

    private static void setMonkeysLcmDivisors(final List<Monkey> monkeys) {
        final long lcm = Utils.leastCommonMultiple(
                monkeys.get(0).getTestDivisor(),
                IntStream.range(1, monkeys.size())
                        .mapToObj(monkeys::get)
                        .mapToLong(Monkey::getTestDivisor)
                        .toArray()
        );
        for (final Monkey monkey : monkeys) {
            monkey.setLcmDivisor(lcm);
        }
    }
}
