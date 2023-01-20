import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void move() {
        Board game = new Board(8, 8);
        assertFalse(game.move(0,0,0,0));
    }
}