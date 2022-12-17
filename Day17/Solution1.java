import rocks.Movement;
import rocks.Rock;
import rocks.RockFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Solution1 {

    private static final int BOTTOM_Y = 0;
    private static final int MIN_CHAMBER_X = 0;
    private static final int MAX_CHAMBER_X = 7 - 1; // We count from 0

    private static final int MAX_ROUNDS = 2022;

    public static void main(final String[] args) throws IOException {
        final String jetPattern;
        try (final BufferedReader br = Files.newBufferedReader(Path.of("input.txt"))) {
            jetPattern = br.readLine();
        }

        final List<Rock> rocks = new ArrayList<>(MAX_ROUNDS);
        int jetIndex = 0;

        int maxHeight = 0;
        for (int round = 0; round < MAX_ROUNDS; round++) {
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
        }

        System.out.println(maxHeight);
    }

    private static boolean canRockMove(final Rock rock, final Collection<Rock> rocks, final Movement movement) {
        return switch (movement) {
            case DOWN -> rock.getBottomY() > BOTTOM_Y;
            case RIGHT -> rock.getRightX() < MAX_CHAMBER_X;
            case LEFT -> rock.getLeftX() > MIN_CHAMBER_X;
        } && rocks.stream().noneMatch(r -> rock.isMovementCollision(r, movement));
    }
}
