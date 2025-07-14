package am.aua.npuzzle;
import am.aua.npuzzle.core.*;

/** The {@code Main} class is the entry point of the 15-puzzle game.
 * It is also used for testing purposes.
 */
public class Main
{
    /** Starts the game tests.
    * @param arg Command-line argument which should be the initial puzzle state as a string(currently inactive).
    */
    public static void main(String[] arg)
    {
        String a = "1 2 3 4 : 5 6 7 8 : 9 10 11 12 : 13 14 0 15";
        Puzzle15Array p = new Puzzle15Array(a);
        p.play();

        //System.out.println("");
        //System.out.println(p.emptyPos);
        //"15 2 1 12 : 8 6 5 11 : 4 9 0 7 : 3 14 13 10"


    }
}
