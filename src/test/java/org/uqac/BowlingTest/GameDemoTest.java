package org.uqac.BowlingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import stev.bowling.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * game demo test
 */
class GameDemoTest {

    private Game game;

    @BeforeEach
    void init() {
        game = new Game();
    }

    /**
     * Test sur le bon fonctionnement d'un Spare sur un LastFrame
     */
    @Test
    void testLastFrameSpare() {
        Frame frame = new LastFrame(10).setPinsDown(1, 8).setPinsDown(2, 2).setPinsDown(3, 10);
        assertEquals("8/X", frame.toString());
    }

    /**
     * Test sur le bon fonctionnement de 3 Strikes sur un LastFrame
     */
    @Test
    void testLastFrameOnlyStrikes() {
        Frame frame = new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 10).setPinsDown(3, 10);
        assertEquals("XXX", frame.toString());
    }

    /**
     * Test sur la gestion des valeurs négatives sur le nombre de pins down sur un LastFrame
     */
    @Test
    void testLastFrameNegativeValue() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            Frame frame = new LastFrame(10).setPinsDown(1, -2).setPinsDown(2, 10).setPinsDown(3, 10);
        });
        System.out.println(thrown.getMessage());
    }

    /**
     * Test sur la gestion de l'impossibilité de faire un troisième lancé sur un LastFrame
     */
    @Test
    void testLastFrameUnvalidThirdThrow() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            Frame frame = new LastFrame(10).setPinsDown(1, 0).setPinsDown(2, 0).setPinsDown(3, 10);
        });
        System.out.println(thrown.getMessage());
    }

    /**
     * Test sur la gestion des valeurs négatives sur l'index de pins down sur un NormalFrame
     */
    @Test
    void testNormalFrameNegativeThrowIndex() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            Frame frame = new LastFrame(10).setPinsDown(-1, 0).setPinsDown(2, 0);
        });
        System.out.println(thrown.getMessage());
    }

    /**
     * Test sur la gestion des valeurs négatives sur l'index de pins down sur un LastFrame
     */
    @Test
    void testLastFrameNegativeThrowIndex() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            Frame frame = new LastFrame(10).setPinsDown(1, 0).setPinsDown(-3, 0);
        });
        System.out.println(thrown.getMessage());
    }

    /**
     * test frame instance null
     */
    @Test
    void testFrameInstanceNull() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            game.addFrame(null);
        });

        assertEquals("Frame #1 must be an instance of NormalFrame", thrown.getMessage());
    }

    /**
     * test normal score format
     */
    @Test
    void testNormalScoreFormat() {
        Frame frame = new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2);
        assertEquals("12", frame.toString());
    }

    /**
     * test normal score value
     */
    @Test
    void testNormalScoreValue() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2));
        assertEquals(3, game.getCumulativeScore(1));
    }

    @Test
    void testReserveScoreFormat() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 9).setPinsDown(2, 1));
        Frame frame = game.getFrame(0);
        assertEquals("9/", frame.toString());
    }

    @Test
    void testDalotScoreFormat() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 9).setPinsDown(2, 0));
        Frame frame = game.getFrame(0);
        assertEquals("9-", frame.toString());
    }

    @Test
    void testStrikeScoreFormat() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
        Frame frame = game.getFrame(0);
        assertEquals("X ", frame.toString());
    }

    @Test
    void testStrikeLastFrame() {
        Frame frame = new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 8);
        assertEquals("X8", frame.toString());
    }

    @Test
    void testShootDirection() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 1));
        game.addFrame(new NormalFrame(2));


        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            game.addFrame(new NormalFrame(4));
        });

        assertEquals("You must enter the frame number 3 for the third Frame", thrown.getMessage());

    }

    @Test
    void testReset() {

        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(1));
        }
        game.addFrame(new LastFrame(10));
        //game.reset();
    }

    @Test
    void testMinShoot() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            new NormalFrame(1).setPinsDown(0, 2);
        });

        assertEquals("There is no such roll 0", thrown.getMessage());
    }

    @Test
    void testMaxShootNormalFrame() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            new NormalFrame(1).setPinsDown(3, 2);
        });

        assertEquals("There is no such roll 3", thrown.getMessage());
    }

    @Test
    void testMaxShootLastFrame() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            new LastFrame(1).setPinsDown(4, 2);
        });
        assertEquals("There is no such roll 4", thrown.getMessage());
    }


    @ParameterizedTest
    @MethodSource
    void testFrameFormat(Frame frame, String expectedFormat) {
        assertEquals(expectedFormat, frame.toString());
    }


    @ParameterizedTest
    @MethodSource
    void testFrameScore(Game game , int[] scores){
        for (int i = 1; i <= game.m_frames.size(); i++){
            System.out.println(game.getCumulativeScore(i));
            assertEquals(scores[i-1], game.getCumulativeScore(i));
        }
    }

    static Stream<Arguments> testFrameScore() {
        int[] scores1 = new int[]{3, 7, 14, 22, 22, 30, 39, 41, 50, 52};
        Game game1 = (new Game())
                .addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2))
                .addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 5))
                .addFrame(new NormalFrame(1).setPinsDown(1, 7).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 4).setPinsDown(2, 4))
                .addFrame(new NormalFrame(1).setPinsDown(1, 9).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 7))
                .addFrame(new LastFrame(1).setPinsDown(1, 1).setPinsDown(2, 1));

        int[] scores2 = new int[]{9, 24, 29, 49, 59, 59, 65, 85, 96, 109};

        Game game2 = (new Game())
                .addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6))
                .addFrame(new NormalFrame(1).setPinsDown(1, 10))
                .addFrame(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 9))
                .addFrame(new NormalFrame(1).setPinsDown(1, 10))
                .addFrame(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 6))
                .addFrame(new NormalFrame(1).setPinsDown(1, 10))
                .addFrame(new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 8))
                .addFrame(new LastFrame(1).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 3));


        int[] scores3 = new int[]{3, 7, 14, 22, 22, 30, 39, 41, 61, 72};
        Game game3 = (new Game())
                .addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2))
                .addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 2).setPinsDown(2, 5))
                .addFrame(new NormalFrame(1).setPinsDown(1, 7).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 4).setPinsDown(2, 4))
                .addFrame(new NormalFrame(1).setPinsDown(1, 9).setPinsDown(2, 0))
                .addFrame(new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 1))
                .addFrame(new NormalFrame(1).setPinsDown(1, 10))
                .addFrame(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 10).setPinsDown(3, 1));

        return Stream.of(
                Arguments.arguments(game1, scores1),
                Arguments.arguments(game2, scores2),
                Arguments.arguments(game3, scores3)
        );
    }

    static Stream<Arguments> testFrameFormat() {
        return Stream.of(
                Arguments.arguments(new NormalFrame(1), "  "),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 5), "5 "),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 0), "- "),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 10), "X "),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 0), "5-"),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 3), "53"),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5), "5/"),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 0), "--"),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 6), "-6"),
                Arguments.arguments(new NormalFrame(1).setPinsDown(1, 0).setPinsDown(2, 10), "-/"),

                Arguments.arguments(new LastFrame(1), "   "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 5), "5  "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0), "-  "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 10), "X  "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 5).setPinsDown(2, 0), "5-"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 5).setPinsDown(2, 3), "53"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 5).setPinsDown(2, 5), "5/ "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 0), "--"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 6), "-6"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 10), "-/ "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 10).setPinsDown(2, 0), "X- "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 10).setPinsDown(2, 6), "X6 "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 10).setPinsDown(2, 10), "XX "),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 10).setPinsDown(3, 0), "-/-"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 10).setPinsDown(3, 8), "-/8"),
                Arguments.arguments(new LastFrame(1).setPinsDown(1, 0).setPinsDown(2, 10).setPinsDown(3, 10), "-/X")
        );
    }

}
