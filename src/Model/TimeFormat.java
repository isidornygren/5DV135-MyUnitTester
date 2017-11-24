package Model;

class TimeFormat {
    /**
     * Formats a small double into a human-readable format
     * @param time the time in nanoseconds to format
     * @return a string formatted in x.xxxs format.
     */
    public static String seconds(double time) {
        String string = "";
        if (time >= Math.pow(10, 9) * 60) { // Minutes
            double minutes = time / (Math.pow(10, 9) * 60);
            time = time % (Math.pow(10, 9) * 60);
            string = String.format("%.3fmin", minutes);
        }
        if (time > Math.pow(10, 7)) { // Seconds
            string += String.format("%.3fs", time/(Math.pow(10, 9)));
        } else if (time > Math.pow(10, 4)) { // Milliseconds
            string += String.format("%.3fms", time/(Math.pow(10, 6)));
        } else if (time > 10) { // Microseconds
            string += String.format("%.3fµs", time/(Math.pow(10, 3)));
        } else if (time > 0){ // Nanosecond
            string += String.format("%.3fns", time);
        } else if (string == ""){
            string = "0.000s";
        }
        return string;
    }
}
