import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

public class Solution1 {

    public static void main(final String[] args) throws IOException {

        final List<? extends Deque<Character>> stacks;

        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            String line = br.readLine();
            stacks = IntStream.range(0, (line.length() + 1) / 4)
                    .mapToObj(ignored -> new ArrayDeque<Character>())
                    .toList();

            do {
                for (int i = 1, stackIndex = 0; i < line.length(); i += 4, stackIndex++) {
                    if (Character.isLetter(line.charAt(i))) {
                        stacks.get(stackIndex).add(line.charAt(i));
                    }
                }
            } while ((line = br.readLine()).charAt(1) != '1');

            br.readLine();

            while ((line = br.readLine()) != null) {
                final String[] craneMoves = line.split("move | from | to ");

                final Deque<Character> fromStack = stacks.get(Integer.parseInt(craneMoves[2]) - 1);
                final Deque<Character> toStack = stacks.get(Integer.parseInt(craneMoves[3]) - 1);
                for (int i = 0; i < Integer.parseInt(craneMoves[1]); i++) {
                    toStack.push(fromStack.pop());
                }
            }
        }

        stacks.stream()
                .map(Deque::peek)
                .forEach(System.out::print);
    }
}
