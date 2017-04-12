package com.udacity;

import java.util.Random;

public class JokeGenerator {
    private Random random;
    private String[] jokes;

    public JokeGenerator() {
        random = new Random();
        jokes = new String[]{
                "I can't believe I forgot to go to the gym today. That's 7 years in a row now.",
                "What starts with P and ends with E and Has Loads of letters in it?\n" +
                        "Post Office! ",
                "What do you call a midget psychic who escaped from prison?\n" +
                        "A small medium at large. (LOL, I had to look up \"at large\" in this one)",
                "What did one wave say to the other wave? \n" +
                        "Nothing, they just waved. ",
                "Two antennas met on a roof, fell in love and got married. The ceremony wasn't much, but the reception was excellent.\n",
                "What has four wheels, two doors, and flies?\n" +
                        "A garbage truck! "
        };
    }

    public String getJoke() {
        return jokes[random.nextInt(jokes.length-1)];
    }

}
