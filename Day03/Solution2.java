import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Solution2 {

    public static void main(final String[] args) throws IOException {
        int prioritiesSum = 0;
        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            String rucksack;
            while ((rucksack = br.readLine()) != null) {
                prioritiesSum += prioritize(
                        intersect(
                                rucksack.chars().mapToObj(ch -> (char) ch).toList(),
                                br.readLine().chars().mapToObj(ch -> (char) ch).toList(),
                                br.readLine().chars().mapToObj(ch -> (char) ch).toList()
                        ).iterator()
                                .next()
                );
            }
        }

        System.out.println(prioritiesSum);
    }

    @SafeVarargs
    private static <T> Collection<T> intersect(final Collection<? extends T>... collections) {
        final Set<T> intersection = new HashSet<>(collections[0]);
        for (int i = 1; i < collections.length; i++) {
            intersection.retainAll(collections[i]);
        }

        return intersection;
    }

    private static int prioritize(final char character) {
        if ('a' <= character && character <= 'z') {
            return character - 'a' + 1;
        }

        return character - 'A' + 27;
    }
}
