import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GamePanelTest {
    private GameLogic gameLogic;
    private GamePanel gamePanel;
    private BoardPanel boardPanel; 
    private Ai ai; 
    private GUI gui;

    @Before
    public void setUp() {
        gameLogic = new GameLogic(10);
        boardPanel = new BoardPanel(gameLogic, gui, ai);
        ai = new Ai(10, gameLogic);
        gamePanel = new GamePanel(gameLogic, new GUI(gameLogic, ai), boardPanel, ai);
    }

    @Test
    public void testReset() {
        gameLogic.setseconds(123);
        gameLogic.setround(5);
        
        gamePanel.reset();

        assertEquals("Round label should be reset to '1'", "1", gamePanel.getRoundLabel().getText());
        assertEquals("Timer label should be reset to '00:00:00'", "00:00:00", gamePanel.getTimerLabel().getText());
    }
}