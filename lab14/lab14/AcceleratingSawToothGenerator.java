package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private int now;
    private int goal;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        state = (state + 1);
        now = (state - goal) % period;
        if (now == 0 && state != 1) {
            period = (int) (period * factor);
            goal = state;
        }
        return (((double) now) / period) * 2 - 1;
    }
}
