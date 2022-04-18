/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHeroLite {
    private static final double CONCERT_A = 440.0;
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create an array which contains 37 guitar strings. */
        synthesizer.GuitarString[] strings = new synthesizer.GuitarString[KEYBOARD.length()];
        for (int i = 0; i < KEYBOARD.length(); i += 1) {
            strings[i] = new synthesizer.GuitarString(CONCERT_A * Math.pow(2, (i - 24.0) / 12.0));
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index >= 0) {
                    strings[index].pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = 0.0;
            for (synthesizer.GuitarString string : strings) {
                sample += string.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for (synthesizer.GuitarString string : strings) {
                string.tic();
            }
        }
    }
}

