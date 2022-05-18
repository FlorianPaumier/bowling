package org.uqac.BowlingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stev.bowling.Game;
import stev.bowling.LastFrame;
import stev.bowling.NormalFrame;

class GameDemoTest {

    private Game game;

    @BeforeEach
    void init(){
        game = new Game();
    }

    @Test
    void testNbRow(){
        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(1));
        }
        game.addFrame(new LastFrame(10));

        //String frame = game.getFrame(1);
        // Regex nb char for row
    }

    @Test
    void testShootDirection(){
        game.addFrame(new NormalFrame(1));
        game.addFrame(new NormalFrame(2));
        game.addFrame(new NormalFrame(4));
        game.addFrame(new NormalFrame(3));
        game.addFrame(new NormalFrame(6));
        game.addFrame(new NormalFrame(5));
        game.addFrame(new NormalFrame(7));
        game.addFrame(new NormalFrame(8));
        game.addFrame(new NormalFrame(9));
        game.addFrame(new LastFrame(10));

        //Dois Avoit une exception car pas dans l'ordre
        System.out.println(game);
    }

    @Test
    void testReset(){

        for (int i = 1; i <= 9; i++) {
            game.addFrame(new NormalFrame(1));
        }
        game.addFrame(new LastFrame(10));
        //game.reset();
    }

    @Test
    void testMinRound(){}

    @Test
    void testMaxRound(){

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
        game.addFrame(new LastFrame(11));

        //Good
    }

    @Test
    void testMinShoot(){
        game.addFrame(new NormalFrame(1).setPinsDown(0, 2));
    }

    @Test
    void testMaxShootNormalFrame(){
        game.addFrame(new NormalFrame(1).setPinsDown(3, 2));
    }

    @Test
    void testMaxShootLastFrame(){
        game.addFrame(new LastFrame(10).setPinsDown(4, 2));
    }

    @Test
    void testMinNbPins(){
        game.addFrame(new NormalFrame(1).setPinsDown(1, 0));
    }

    @Test
    void testMaxNbPins(){
        game.addFrame(new NormalFrame(1).setPinsDown(1, 10));
    }

    @Test
    void testPinsDownAtPosition(){}
    
    @Test
    void testPinsDownAtWrongPosition(){}
}