package Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeFormatTest {

    @Test
    public void seconds() throws Exception{
        assertEquals("1.000s", TimeFormat.seconds(1000000000));
        assertEquals("0.100s", TimeFormat.seconds(100000000));
        assertEquals("1.000ms", TimeFormat.seconds(1000000));
    }

}