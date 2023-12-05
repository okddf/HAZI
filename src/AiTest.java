import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AiTest {
    private GameLogic gameLogic;
    private Ai ai;
    private final int size = 10;

    @Before
    public void setUp() {
        gameLogic = new GameLogic(size);
        ai = new Ai(size, gameLogic);
    }

    @Test
    public void testAIPicksHighestScoringMove() {
        // Ai has 4 symbols in a row
        for (int i = 0; i < 4; i++) {
            gameLogic.makemove(1, i);
            gameLogic.switchplayer();
            gameLogic.makemove(0, i);
            gameLogic.switchplayer();
        }
        // AI move
        int[] move = ai.aiMove();

        // AI picked the winning move
        assertEquals("AI should pick the row 0 and column 4", 0, move[0]);
        assertEquals("AI should pick the row 0 and column 4", 4, move[1]);
    }

    @Test
    public void testAILastMoveUpdate() {
        // .At initialization lastmoves should be size
        assertEquals("Initial last move i should be size", size, ai.getlastmovei());
        assertEquals("Initial last move j should be size", size, ai.getlastmovej());

        // AI move
        ai.aiMove();

        // Last move is updated
        assertFalse("Last move i should not be size after move", size == ai.getlastmovei());
        assertFalse("Last move j should not be size after move", size == ai.getlastmovej());
    }
}
