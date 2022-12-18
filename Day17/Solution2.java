import rocks.Movement;
import rocks.Rock;
import rocks.RockFactory;
import rocks.Shape;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Solution2 {

    private static final int BOTTOM_Y = 0;
    private static final int MIN_CHAMBER_X = 0;
    private static final int MAX_CHAMBER_X = 7 - 1; // We count from 0

    private static final long MAX_ROUNDS = 1_000_000_000_000L;

    private static final int SHAPES_COUNT = Shape.values().length;

    public static void main(final String[] args) throws IOException {
        final String jetPattern;
        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            jetPattern = br.readLine();
        }

        final List<Rock> rocks = new ArrayList<>();
        int jetIndex = 0;

        int minSequenceLengthDouble = SHAPES_COUNT * 2;
        int checkingRound = (SHAPES_COUNT * 2) - 1;
        boolean isCheckingResult = false;

        int maxHeight = 0;
        for (int round = 0; ; round++) {
            final Rock rock = RockFactory.getRock(round, maxHeight);

            while (canRockMove(rock, rocks, Movement.DOWN)) {
                rock.move(Movement.DOWN);
                switch (jetPattern.charAt(jetIndex)) {
                    case '>' -> {
                        if (canRockMove(rock, rocks, Movement.RIGHT)) {
                            rock.move(Movement.RIGHT);
                        }
                    }
                    case '<' -> {
                        if (canRockMove(rock, rocks, Movement.LEFT)) {
                            rock.move(Movement.LEFT);
                        }
                    }
                    default ->
                            throw new IllegalArgumentException("Illegal movement character '" + jetPattern.charAt(jetIndex) + "'.");
                }

                jetIndex = (jetIndex + 1) % jetPattern.length();
            }

            rocks.add(rock);
            maxHeight = Math.max(rock.getTopY() + 1, maxHeight);

            if (round == checkingRound) {
                final Optional<Repetition> repeating = isRepeating(rocks, minSequenceLengthDouble);
                if (repeating.isPresent()) {
                    final Repetition rep = repeating.get();

                    if (isCheckingResult) { // The sequence is really repeating
                        System.out.println(getFinalResult(rep));
                        return;
                    } else { // Check whether the sequence repeats one more time (it is not just local)
                        final List<Rock> sequence = rep.sequence();
                        final int sequenceLength = sequence.size();

                        isCheckingResult = true;
                        minSequenceLengthDouble = sequenceLength * 4;
                        checkingRound += sequenceLength * 2;
                    }
                } else {
                    minSequenceLengthDouble = SHAPES_COUNT * 2;
                    checkingRound += SHAPES_COUNT;
                    isCheckingResult = false;
                }
            }
        }
    }

    private static boolean canRockMove(final Rock rock, final Collection<Rock> rocks, final Movement movement) {
        return switch (movement) {
            case DOWN -> rock.getBottomY() > BOTTOM_Y;
            case RIGHT -> rock.getRightX() < MAX_CHAMBER_X;
            case LEFT -> rock.getLeftX() > MIN_CHAMBER_X;
        } && rocks.stream().noneMatch(r -> rock.isMovementCollision(r, movement));
    }

    private static Optional<Repetition> isRepeating(final List<Rock> rocks, final int minSequenceLengthDouble) {
        for (int i = 0; i < rocks.size(); i += SHAPES_COUNT) {
            for (int j = i + minSequenceLengthDouble; j < rocks.size(); j += SHAPES_COUNT * 2) {
                final int mid = i + (j - i) / 2;

                if (isListsSame(rocks.subList(i, mid), rocks.subList(mid, j))) {
                    return Optional.of(
                            new Repetition(i,
                                    rocks.get(i).getBottomY() - rocks.get(0).getBottomY(),
                                    rocks.subList(i, mid)));
                }
            }
        }

        return Optional.empty();
    }

    private static boolean isListsSame(final List<Rock> fst, final List<Rock> snd) {
        final int distance = snd.get(0).getBottomY() - fst.get(0).getBottomY();

        for (int k = 0; k < fst.size(); k++) {
            final Rock r1 = fst.get(k);
            final Rock r2 = snd.get(k);

            if (r1.getLeftX() != r2.getLeftX() || r2.getBottomY() - r1.getBottomY() != distance) {
                return false;
            }
        }

        return true;
    }

    private static long getFinalResult(final Repetition rep) {
        final List<Rock> sequence = rep.sequence();
        final int sequenceLength = sequence.size();

        final long repeatedRounds = MAX_ROUNDS - rep.beginLength();
        final long repetitions = repeatedRounds / sequenceLength;
        final int remainder = Math.floorMod(repeatedRounds, sequenceLength);

        final int sequenceHeight = sequence.get(sequenceLength - 1).getTopY() - sequence.get(0).getBottomY() + 1;
        final int remainderHeight = sequence.get(remainder).getBottomY() - sequence.get(0).getBottomY();

        return rep.beginHeight() + repetitions * sequenceHeight + remainderHeight;
    }

    private record Repetition(int beginLength, int beginHeight, List<Rock> sequence) {}
}
