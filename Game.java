/*
    Filip Milidarg
    Ms.Krasteva
    2019-01-15
    Capture the flag
    This is a two player game where each player tries to get to the opponents flag and bring it to their side before their opponent.
    Variable        Type             Description
    c               Console          This is the console that everything will be displayed on.
    choice          String           This will be used to choose which method the user would like to run.
    co1             Color            This will be the colour that represents player 1 and their half of the map.
    co2             Color            This will be the colour that represents player 2 and their half of the map.
    blocks          2D array         This array contains all the coordinates of the blocks on the map. It's used to make sure the players cannot go through blocks.
    get             char             This char is used to know if the user would like to clear highscore.s
*/

import java.awt.*;
import hsa.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Game
{
    Console c;
    String choice;
    Color co1 = new Color (255, 100, 100);
    Color co2 = new Color (170, 0, 200);
    int[] [] blocks = new int [4] [];
    char get;

    public Game ()
    {
	c = new Console ();
    }


    /*
	This method prints clears the screen and prints the title at the center of the top line.
    */
    private void title ()
    {
	c.clear ();
	c.setColor (Color.green);
	c.fillRect (0, 0, 640, 500);
	c.setTextBackgroundColor (Color.green);
	c.print (' ', 32);
	c.println ("Capture The Flag\n");
    }


    /*
	This method will pause the program and can display any text whenever it's called, the char that is entered can also be used elsewhere in the program.
    */
    private void pauseProgram (String text)
    {
	c.print (text);
	c.println ("press any key to continue: ");
	get = c.getChar ();
    }


    /*
	This method pauses the program until the user presses a key.
    */
    private void pauseProgram ()
    {

	c.println ("Press any key to continue: ");
	c.getChar ();
    }


    /*
	This method shows the user all the options for the game and gets their choice in the form of a string.
    */
    public void mainMenu ()
    {
	title ();
	c.print (' ', 33);
	c.println ("1.Instructions");
	c.print (' ', 36);
	c.println ("2.Map 1");
	c.print (' ', 36);
	c.println ("3.Map 2");
	c.print (' ', 36);
	c.println ("4.Map 3");
	c.print (' ', 34);
	c.println ("5.High Scores");
	c.print (' ', 37);
	c.println ("6.Exit\n");
	c.print ("Enter the corresponding number for whatever you would like to do:");
	choice = c.readLine ();
    }


    /*
	This method displays the instructions to the user.
    */
    public void instructions ()
    {
	title ();
	c.println ("To play this game you need to select which map to play on. Then you and a friend will each play as a character(either the orange square or pink square). Your controls to move around will either be w, a, s, and d or i, j, k  and l. Your goal is to go to the other players side, collect their flag and come back to your side before they do this. You can send the enemy back to their side by touching them while they're on your side. This can be used to prevent them from capturing your flag. You cannot go off the screen or go through black blocks on the screen. When you have the flag there will be a blue block at the top of the screen above your player showing you that you have the flag. To quit during the game, press \'p\'.");
	pauseProgram ();
    }


    /*
	This method will draw the map the players have to play on will set the coordinates for the boxes that are drawn. These coordinates are later used to prevent players from going through blocks.
	The if structure is used to determine where the blocks will be. This is based off what map the user has chosen to play on.
    */
    public void map (int type)
    {
	int boxX1, boxY1, boxX2, boxY2;
	c.setColor (Color.green);
	c.fillRect (0, 0, 640, 500);
	c.setColor (co1);
	c.drawLine (320, 0, 320, 500);
	c.setColor (co2);
	c.drawLine (321, 0, 321, 500);
	if (type == 1)
	{
	    blocks [0] = new int [1];
	    blocks [1] = new int [1];
	    blocks [2] = new int [1];
	    blocks [3] = new int [1];
	    blocks [0] [0] = 0;
	    blocks [1] [0] = 0;
	    blocks [2] [0] = 0;
	    blocks [3] [0] = 0;
	}
	else if (type == 2)
	{
	    c.setColor (Color.black);
	    c.fillRect (280, 140, 80, 200);
	    blocks [0] = new int [1];
	    blocks [1] = new int [1];
	    blocks [2] = new int [1];
	    blocks [3] = new int [1];
	    blocks [0] [0] = 280;
	    blocks [1] [0] = 360;
	    blocks [2] [0] = 140;
	    blocks [3] [0] = 340;
	}
	else
	{
	    blocks [0] = new int [16];
	    blocks [1] = new int [16];
	    blocks [2] = new int [16];
	    blocks [3] = new int [16];
	    c.setColor (Color.black);
	    int counter = 0;
	    for (int i = 0 ; i <= 1 ; i++)
	    {
		for (int j = 0 ; j < 8 ; j++)
		{
		    boxX1 = 190 + 210 * i;
		    boxY1 = 30 + j * 60;
		    boxX2 = boxX1 + 30;
		    boxY2 = boxY1 + 30;
		    c.fillRect (boxX1, boxY1, 30, 30);
		    blocks [0] [counter] = boxX1;
		    blocks [1] [counter] = boxX2;
		    blocks [2] [counter] = boxY1;
		    blocks [3] [counter] = boxY2;
		    counter++;
		}
	    }
	}
    }


    /*
	This method draws a flag anywhere on the map in any colour.
    */
    private void flag (int x, int y, Color colour)
    {
	c.setColor (colour);
	c.drawLine (x + 20, y, x + 20, y + 30);
	c.fillRect (x, y, 20, 10);
    }


    /*
	This method will draw both players on the map and gets input to move them around until a winner is crowned.
	The while loop is used to continue getting user input to move the characters until the game is over. The if statement checks what the user has inputed and usees this to move the characters.
	The if statement within this is used to make sure none of the characters are moving into blocks on the map.
	Variable             Type           Description
	moves                int            This counts how many moves have been done and will be used to calculate score
	withFlag1            boolean        This variable is used to check if player 1 is carrying the flag
	withFlag2            boolean        This variable is used to check if player 2 is carrying the flag
	x1                   int            This is one of the characters coordinates
	x2                   int            This is one of the characters coordinates
	y1                   int            This is one of the characters coordinates
	y2                   int            This is one of the characters coordinates
	flx1                 int            This is onew of the flag's coordinates
	fly1                 int            This is onew of the flag's coordinates
	flx2                 int            This is onew of the flag's coordinates
	fly2                 int            This is onew of the flag's coordinates
	controls1            string         These are the characters that are used to control player 1
	controls2            string         These are the characters that are used to control player 2
	ok                   boolean        This is used to check if one of the characters is about to move into a block and will prevent them from doing so
	input1               char           Thois variable is the input that the user is giving to control the characters
    */
    public void character ()
    {
	int moves = 0;
	boolean withFlag1 = false;
	boolean withFlag2 = false;
	char input1 = ' ';
	String controls1, controls2;
	int x1 = 0, x2 = 0;
	int y1 = 240, y2 = 240;
	int flx1, flx2, fly1, fly2;
	flx1 = 30;
	fly1 = 30;
	flx2 = 600;
	fly2 = 460;
	flag (30, 30, Color.black);
	flag (600, 460, Color.black);
	x1 = 20;
	controls1 = "wasd";
	x2 = 610;
	controls2 = "ijkl";
	c.setColor (co1);
	c.fillRect (x1, y1, 10, 10);
	c.setColor (co2);
	c.fillRect (x2, y2, 10, 10);
	int oldX1, oldY1, oldX2, oldY2;
	boolean ok;
	int length3 = blocks [3].length;
	int length2 = blocks [2].length;
	int length1 = blocks [1].length;
	int length0 = blocks [0].length;
	while (true)
	{
	    moves++;
	    ok = true;
	    oldX1 = x1;
	    oldY1 = y1;
	    oldX2 = x2;
	    oldY2 = y2;
	    input1 = c.getChar ();
	    if (input1 == controls1.charAt (0) && y1 - 10 >= 0)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((y1 - 10) < blocks [3] [i] && ((y1 - 10) >= blocks [2] [i])) && (x1 < blocks [1] [i] && x1 >= blocks [0] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    y1 -= 10;
		}
	    }
	    else if (input1 == controls1.charAt (1) && x1 - 10 >= 0)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((x1 - 10) < blocks [1] [i] && ((x1 - 10) >= blocks [0] [i])) && (y1 < blocks [3] [i] && y1 >= blocks [2] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    x1 -= 10;
		}
	    }
	    else if (input1 == controls1.charAt (2) && y1 + 10 <= 490)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((y1 + 10) <= blocks [3] [i] && ((y1 + 10) >= blocks [2] [i])) && (x1 < blocks [1] [i] && x1 >= blocks [0] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    y1 += 10;
		}
	    }
	    else if (input1 == controls1.charAt (3) && x1 + 10 <= 630)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((x1 + 10) <= blocks [1] [i] && ((x1 + 10) >= blocks [0] [i])) && (y1 < blocks [3] [i] && y1 >= blocks [2] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    x1 += 10;
		}

	    }
	    if (input1 == controls2.charAt (0) && y2 - 10 >= 0)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((y2 - 10) < blocks [3] [i] && ((y2 - 10) >= blocks [2] [i])) && (x2 < blocks [1] [i] && x2 >= blocks [0] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    y2 -= 10;
		}
	    }
	    else if (input1 == controls2.charAt (1) && x2 - 10 >= 0)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((x2 - 10) < blocks [1] [i] && ((x2 - 10) >= blocks [0] [i])) && (y2 < blocks [3] [i] && y2 >= blocks [2] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    x2 -= 10;
		}
	    }
	    else if (input1 == controls2.charAt (2) && y2 + 10 <= 490)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((y2 + 10) <= blocks [3] [i] && ((y2 + 10) >= blocks [2] [i])) && (x2 < blocks [1] [i] && x2 >= blocks [0] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    y2 += 10;
		}
	    }
	    else if (input1 == controls2.charAt (3) && x2 + 10 <= 630)
	    {
		for (int i = 0 ; i <= length3 - 1 ; i++)
		{
		    if (((x2 + 10) <= blocks [1] [i] && ((x2 + 10) >= blocks [0] [i])) && (y2 < blocks [3] [i] && y2 >= blocks [2] [i]))
		    {
			ok = false;
			break;
		    }
		}
		if (ok)
		{
		    x2 = x2 + 10;
		}

	    }
	    else if (input1 == 'p')
	    {
		break;
	    }
	    if (x2 == x1 && y2 == y1 && x1 <= 310)
	    {
		x2 = 610;
		y2 = 240;
		withFlag2 = false;
	    }
	    else if (x2 == x1 && y2 == y1 && x1 > 310)
	    {
		x1 = 20;
		y1 = 240;
		withFlag1 = false;
	    }
	    c.setColor (Color.green);
	    c.fillRect (oldX1, oldY1, 10, 10);
	    c.fillRect (oldX2, oldY2, 10, 10);
	    flag (30, 30, Color.black);
	    flag (600, 460, Color.black);
	    if (x1 >= flx2 && x1 < (flx2 + 20) && y1 >= fly2 && y1 < (fly2 + 30))
	    {
		withFlag1 = true;
	    }
	    c.setColor (Color.green);
	    c.fillRect (oldX1, 0, 20, 20);
	    if (withFlag1)
	    {
		c.setColor (Color.blue);
		c.fillRect (x1, 0, 20, 20);
	    }
	    if (x2 >= flx1 && x2 < (flx1 + 20) && y2 >= fly1 && y2 < (fly1 + 30))
	    {
		withFlag2 = true;
	    }
	    c.setColor (Color.green);
	    c.fillRect (oldX2, 0, 20, 20);
	    if (withFlag2)
	    {
		c.setColor (Color.blue);
		c.fillRect (x2, 0, 20, 20);
	    }
	    c.setColor (co1);
	    c.drawLine (320, 0, 320, 500);
	    c.setColor (co2);
	    c.drawLine (321, 0, 321, 500);
	    c.setColor (co1);
	    c.fillRect (x1, y1, 10, 10);
	    c.setColor (co2);
	    c.fillRect (x2, y2, 10, 10);
	    if (x1 < 320 && withFlag1)
	    {
		title ();
		c.println ("Player 1 has won!!!");
		break;
	    }
	    else if (x2 > 310 && withFlag2)
	    {
		title ();
		c.println ("Player 2 has won!!!");
		break;
	    }
	}
	if (input1 != 'p')
	{
	    String name = "this is more than 29 characters";
	    while (true)
	    {
		title ();
		c.print ("Type in your name to see if your score belongs on the score board: ");
		name = c.readLine ();
		if (name.length () > 29)
		{
		    JOptionPane.showMessageDialog (null, "Your name is too long, please enter a shorter name.");
		}
		else
		{
		    break;
		}
	    }
	    highScores (scoreCalculator (moves), name);
	    pauseProgram ();
	}
    }


    /*
	This method edits the current high score list based off the new entry from the end of the last game.
    */
    private void highScores (int newScore, String newName)
    {
	FileWriter hello;
	String r = "";
	String s = "";
	try
	{
	    BufferedReader in1 = new BufferedReader (new FileReader ("HighScores.txt"));
	}
	catch (IOException e)
	{
	    try
	    {
		PrintWriter out1 = new PrintWriter (new FileWriter ("HighScores.txt"));
	    }
	    catch (IOException ei)
	    {
	    }
	}
	try
	{
	    String[] names = new String [10];
	    int[] scores = new int [10];
	    BufferedReader in = new BufferedReader (new FileReader ("HighScores.txt"));
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		s = in.readLine ();
		if (s == null)
		{
		    names [i] = "Empty";
		}
		else
		{
		    names [i] = s;
		}
		r = in.readLine ();
		if (r == null)
		{
		    scores [i] = 0;
		}
		else
		{
		    scores [i] = Integer.parseInt (r);
		}
	    }
	    int place = -1;
	    for (int i = 9 ; i >= 0 ; i--)
	    {
		if (newScore > scores [i])
		{
		    place = i;
		}
	    }
	    if (place != -1)
	    {
		for (int i = 8 ; i >= place ; i--)
		{
		    names [i + 1] = names [i];
		    scores [i + 1] = scores [i];
		}
		names [place] = newName;
		scores [place] = newScore;
	    }
	    PrintWriter out = new PrintWriter (new FileWriter ("HighScores.txt"));
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		out.println (names [i]);
		out.println (scores [i]);
	    }
	    out.close ();
	}
	catch (IOException e)
	{
	}
    }


    /*
	This method calculates the player's score.
    */
    private int scoreCalculator (int num)
    {
	return 10000 - num;
    }


    /*
	This method displays the high scores and gives the user the option of clearing them.
    */
    public void displayHighScores ()
    {
	String r = "";
	String s = "";
	String[] names = new String [10];
	int[] scores = new int [10];
	try
	{
	    BufferedReader in = new BufferedReader (new FileReader ("HighScores.txt"));
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		s = in.readLine ();
		if (s == null)
		{
		    names [i] = "Empty";
		}
		else
		{
		    names [i] = s;
		}
		r = in.readLine ();
		if (r == null)
		{
		    scores [i] = 0;
		}
		else
		{
		    scores [i] = Integer.parseInt (r);
		}
	    }
	    title ();
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		c.print (' ', 30);
		c.print (names [i], 30);
		c.println (scores [i]);
	    }
	    pauseProgram ("Type c to clear the highscores otherwise, ");
	    if (get == 'c')
	    {
		PrintWriter out = new PrintWriter (new FileWriter ("HighScores.txt"));
		out.close ();
	    }
	}
	catch (IOException e)
	{
	    title ();
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		names [i] = "Empty";
		scores [i] = 0;
	    }
	    for (int i = 0 ; i <= 9 ; i++)
	    {
		c.print (' ', 30);
		c.print (names [i], 30);
		c.println (scores [i]);
	    }
	    pauseProgram ();
	}
    }


    /*
	This method shows an animation at the start of the program.
    */
    public void splashScreen ()
    {
	try
	{
	    Animation a = new Animation (c);
	    a.start ();
	    a.join ();
	}
	catch (Exception e)
	{
	}
    }


    /*
    This method says goodbye to the user.
    */
    public void goodbye ()
    {
	title ();
	c.println ("By: Filip Milidrag");
	c.println ("Goodbye");
	pauseProgram ();
    }


    /*
	This method runs all the other ones and will use the choice variabe to decide which methods the user wants to run.
	The while loop keeps the program running until the user wants to exit. The if structure is used to pick which method to run based off the user's choice.
    */
    public static void main (String[] args)
    {
	Game g = new Game ();
	g.splashScreen ();
	while (true)
	{
	    g.mainMenu ();
	    if (g.choice.equals ("1"))
	    {
		g.instructions ();
	    }
	    else if (g.choice.equals ("2"))
	    {
		g.map (1);
		g.character ();
	    }
	    else if (g.choice.equals ("3"))
	    {
		g.map (2);
		g.character ();
	    }
	    else if (g.choice.equals ("4"))
	    {
		g.map (3);
		g.character ();
	    }
	    else if (g.choice.equals ("5"))
	    {
		g.displayHighScores ();
	    }
	    else if (g.choice.equals ("6"))
	    {
		break;
	    }
	    else
	    {
		JOptionPane.showMessageDialog (null, "That was not option");

	    }
	}
	g.goodbye ();
	g.c.close ();
    }
}


