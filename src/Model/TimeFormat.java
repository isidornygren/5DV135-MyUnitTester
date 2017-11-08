package Model;

class TimeFormat {
    /**
     * Formats a small double into a human-readable format
     * @param time the time in nanoseconds to format
     * @return a string formatted in x.xxxs format.
     */
    public static String seconds(double time) {
        String string;
        if (time > Math.pow(10, 9) * 60) { // Minutes
            string = String.format("%.3fmin", time/(Math.pow(10, 9) * 60));
        } else if (time > Math.pow(10, 7)) { // Seconds
            string = String.format("%.3fs", time/(Math.pow(10, 9)));
        } else if (time > Math.pow(10, 4)) { // Milliseconds
            string = String.format("%.3fms", time/(Math.pow(10, 6)));
        } else if (time > 10) { // Microseconds
            string = String.format("%.3fµs", time/(Math.pow(10, 3)));
        } else{ // Nanosecond
            string = String.format("%.3fns", time);
        }
        return string;
    }
}
