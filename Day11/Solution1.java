import monkeys.Monkey;
import monkeys.MonkeyFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        final List<Monkey> monkeys = new ArrayList<>();

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            do {
                br.readLine();
                monkeys.add(MonkeyFactory.createMonkey(br.readLine(), br.readLine(),
                        br.readLine(), br.readLine(), br.readLine()));
            } while (br.readLine() != null);
        }

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