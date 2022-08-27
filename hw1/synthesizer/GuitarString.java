// todo: Make sure to make this class a part of the synthesizer package
package synthesizer;

//Make sure this class is public
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private double ldequeue = 0.0;
    /* Buffer for storing sound data. */
    private ArrayRingBuffer<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<Double>(Math.round((float) (SR / frequency)));
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
            // todo: Create a buffer with capacity = SR / frequency. You'll need to
            //       cast the result of this divsion operation into an int. For better
            //       accuracy, use the Math.round() function before casting.
            //       Your buffer should be initially filled with zeros.
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            double nn = Math.random() - 0.5;
            buffer.enqueue(nn);
        }
    }
    // todo: Dequeue everything in the buffer, and replace it with random numbers
    //       between -0.5 and 0.5. You can get such a number by using:
    //       double r = Math.random() - 0.5;
    //
    //       Make sure that your random numbers are different from each other.


    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double a = buffer.dequeue();
        double b = buffer.peek();
        ldequeue = a;
        buffer.enqueue(DECAY * (a + b) / 2.0);
        // todo: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // todo: Return the correct thing.
        return ldequeue;
    }
}
