import org.junit.*;
import static org.junit.Assert.*;

public class GameLogicTest {
    private GameLogic gameLogic;
    private int size = 10;

    @Before
    public void setUp() {
        gameLogic = new GameLogic(size);
    }

    @Test
    public void boardconstructortest() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals("Board should be initialized to zero", 0, gameLogic.getboard()[i][j]);
            }
        }
    }

    @Test
    public void makemovetest() {
        gameLogic.makemove(0, 0);
        assertEquals("First move should be by player 1", 1, gameLogic.getboard()[0][0]);
        gameLogic.switchplayer();
        gameLogic.makemove(0, 1);
        assertEquals("Second move should be by player 2", 2, gameLogic.getboard()[0][1]);
    }

    @Test
    public void checkwintest() {
        // Board with a winning condition
        for (int i = 0; i < 5; i++) {
            gameLogic.getboard()[0][i] = 1;
        }

        // Checkwin identifies win
        assertTrue("Should return true as player 1 has a winning condition", gameLogic.checkwin(0, 0, 1));
    }

    @Test
    public void switchpLayerTest() {
        // First, player is 1
        assertEquals("Current player should be 1", 1, gameLogic.getcurrentplayer());
        gameLogic.switchplayer();
        // Players switched
        assertEquals("Current player should be 2", 2, gameLogic.getcurrentplayer());
    }

    @Test
    public void validmovetest() {
        // Board with one symbol in the middle
        gameLogic.getboard()[size / 2][size / 2] = 1;

        // Invalid move
        assertFalse("Should return false as this is an invalid move", gameLogic.validmove(0, 0));
    }

    @Test
    public void emptyboardtest() {
        // Board with one symbol in the middle

        gameLogic.getboard()[size / 2][size / 2] = 1;

        // Board is not empty
        assertFalse("Should return false as the board is not empty", gameLogic.isempty());
    }

    @Test
    public void testReset() {
        // Random board state
        gameLogic.makemove(0, 0);
        gameLogic.setseconds(100);
        gameLogic.switchplayer();
        gameLogic.setround(10);
        gameLogic.getwinners().add(new Integer[] { 0, 0 });

        // Reset
        gameLogic.reset(size);

        // All fields are reset
        assertTrue("Board should be empty after reset", gameLogic.isempty());
        assertEquals("Current player should be reset to 1", 1, gameLogic.getcurrentplayer());
        assertEquals("Round should be reset to 2", 2, gameLogic.getround());
        assertEquals("Seconds should be reset to 0", 0, gameLogic.getseconds());
        assertEquals("State should be reset to 0", 0, gameLogic.getstate());
        assertTrue("Winners should be cleared on reset", gameLogic.getwinners().isEmpty());
        assertEquals("Last move i should be reset to size", size, gameLogic.getlastmovei());
        assertEquals("Last move j should be reset to size", size, gameLogic.getlastmovej());
    }
}
