package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    private int weirdState;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = state + 1;
//        weirdState = state & (state >>> 3) % period;
        weirdState = state & (state >> 3) & (state >> 8) % period;
        return ((double) weirdState / period) * 2 - 1;
    }
}
