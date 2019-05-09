/*
    Filip Milidrag
    Ms. Krasteva
    2019-01-18
    Flag Movement
    This animates a flag moving over the screen
*/
import java.awt.*;
import hsa.Console;

public class Animation extends Thread
{
    Console c;
    /*
	This constructor will use the console from the other class as its own.
    */
    public Animation (Console con)
    {
	c = con;
    }


    /*
	This method will show a flag move across the screen
    */
    public void run ()
    {
	c.setColor (Color.green);
	c.fillRect (0, 0, 640, 500);
	for (int i = 0 ; i <= 1000 ; i++)
	{
	    c.setColor (Color.green);
	    c.fillRect (-201 + i, 150, 150, 151);
	    c.setColor (Color.black);
	    c.fillRect (-200 + i, 150, 150, 150);
	    c.setColor (Color.green);
	    c.fillRect (-200 + i, 225, 140, 75);
	    try
	    {
		Thread.sleep (10);
	    }
	    catch (Exception e)
	    {

	    }
	}

    }
}


