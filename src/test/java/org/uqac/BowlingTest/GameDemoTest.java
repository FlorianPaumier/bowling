package org.uqac.BowlingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stev.bowling.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  game demo test
 *
 */
class GameDemoTest {

    private Game game;

    @BeforeEach
    void init() {
        game = new Game();
    }

    /**
     * test frame instance null
     *
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
     *
     */
    @Test
    void testNormalScoreFormat() {
        Frame frame = new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2);
        assertEquals("12", frame.toString());
    }

    /**
     * test normal score value
     *
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
        game.addFrame(new NormalFrame(1));
        Frame frame = new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 8);
        assertEquals("X8", frame.toString());
    }

    @Test
    void testShootDirection() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(1));
        game.addFrame(new LastFrame(1));

        //Dois Avoit une exception car pas dans l'ordre
        System.out.println(game);
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
    void testMinRound() {
    }

    @Test
    void testInstanceLastFrame() {

        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(2));
        game.addFrame(new NormalFrame(4));
        game.addFrame(new NormalFrame(3));
        game.addFrame(new NormalFrame(6));
        game.addFrame(new NormalFrame(5));
        game.addFrame(new NormalFrame(7));
        game.addFrame(new NormalFrame(8));
        game.addFrame(new NormalFrame(9));
        game.addFrame(new NormalFrame(10));
    }

    @Test
    void testMinShoot() {
        game.addFrame(new NormalFrame(1).setPinsDown(0, 2));
    }

    @Test
    void testMaxShootNormalFrame() {
        game.addFrame(new NormalFrame(1).setPinsDown(3, 2));
    }

    @Test
    void testMaxShootLastFrame() {
        BowlingException thrown = assertThrows(BowlingException.class, () -> {
            new LastFrame(1).setPinsDown(4, 2);
        });

        assertEquals("There is no such roll 4", thrown.getMessage());
    }

    @Test
    void testMinNbPins() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 0));
    }

    @Test
    void testMaxNbPins() {
        game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
    }

    @Test
    void testPinsDownAtPosition() {
    }

    @Test
    void testPinsDownAtWrongPosition() {
    }
}