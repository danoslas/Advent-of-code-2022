import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class Solution1 {

    public static void main(final String[] args) throws IOException {
        try (final Stream<String> stream = Files.lines(Path.of("input.txt"))) {
            System.out.println(
                    stream.map(rucksack -> rucksack.chars().mapToObj(ch -> (char) ch).toList())
                            .map(items -> intersect(items.subList(0, items.size() / 2), items.subList(items.size() / 2, items.size())))
                            .map(Collection::iterator)
                            .map(Iterator::next)
                            .mapToInt(Solution1::prioritize)
                            .sum()
            );
        }
    }

    private static <T> Collection<T> intersect(final Collection<? extends T> fst, final Collection<? extends T> snd) {
        final Set<T> intersection = new HashSet<>(fst);
        intersection.retainAll(snd);

        return intersection;
    }

    private static int prioritize(final char character) {
        if ('a' <= character && character <= 'z') {
            return character - 'a' + 1;
        }

        return character - 'A' + 27;
    }
}
