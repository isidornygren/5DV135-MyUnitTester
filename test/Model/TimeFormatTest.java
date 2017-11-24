package Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeFormatTest {

    @Test
    public void minutes() throws Exception{
        assertEquals("1.000min", TimeFormat.seconds(60000000000L));
    }
    @Test
    public void seconds() throws Exception{
        assertEquals("1.000s", TimeFormat.seconds(1000000000));
    }

    @Test
    public void smallSeconds() throws Exception{
        assertEquals("0.100s", TimeFormat.seconds(100000000));
    }

    @Test
    public void milliseconds() throws Exception{
        assertEquals("1.000ms", TimeFormat.seconds(1000000));
    }

    @Test
    public void microseconds() throws Exception{
        assertEquals("1.000µs", TimeFormat.seconds(1000));
    }

    @Test
    public void nanoseconds() throws Exception{
        assertEquals("1.000ns", TimeFormat.seconds(1));
    }
}