import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board game;

    @BeforeEach
    void setUp() {
        game = new Board(8, 8);
    }

    @Test
    void insertPieceA(){
        Piece expected = Piece.A;

        game.insertPiece(expected, 0, 0);

        Piece actual = game.get(0, 0);
        assertEquals(expected, actual);
    }

    @Nested
    @DisplayName("Tests for move function")
    class move {
        @Test
        void moveSame() {
            //if we move to the same spot, we set the rule that this doesn't count as a move!
            assertFalse(game.move(0, 0, 0, 0));
        }

        @Test
        void startOutOfBounds() {
            assertFalse(game.move(9, 9, 8, 8));
            assertFalse(game.move(-1, -1, 8, 8));
        }

        @Test
        void endOutOfBounds() {
            assertFalse(game.move(0, 0, 8, 8));
            assertFalse(game.move(7, 7, -8, -8));
        }

        @Nested
        @DisplayName("Tests for Moving Piece A")
        class MovePieceA {

            private Piece expected = Piece.A;

            @BeforeEach
            void setupPieceA() {
                game.insertPiece(expected, 0, 0);
            }

            @Nested
            @DisplayName("All valid moves for Piece A")
            class validMoves {
                @Test
                void validHorizontalMove() {

                    game.move(0, 0, 1, 0);
                    Piece actual = game.get(1, 0);

                    assertEquals(expected, actual);
                }

                @Test
                void validVerticalMove() {

                    game.move(0, 0, 0, 1);
                    Piece actual = game.get(0, 1);

                    assertEquals(expected, actual);

                }

            }

            @Test
            void invalidMoves() {
                assertFalse(game.move(0, 0, 1, 1));
                assertFalse(game.move(0, 0, 0, 2));
            }

            @Test
            void testCollision() {
                Piece collider = Piece.A;
                game.insertPiece(collider, 1, 0);

                game.move(0, 0, 1, 0);

                Piece actual = game.get(2, 0);
                assertEquals(expected, actual);

            }

            @Test
            void testCollisionOffBoard() {
                game.insertPiece(expected, 7, 6);

                Piece collider = Piece.A;
                game.insertPiece(collider, 7, 7);

                assertFalse(game.move(7, 6, 0, 1));
                Piece actual = game.get(7, 6);
                assertEquals(expected, actual);

            }
        }

        @Nested
        @DisplayName("Test Movement for Piece B")
        class MovePieceB {

            Piece expectedRook = Piece.B;
            @BeforeEach
            void setup() {
                game.insertPiece(expectedRook, 5, 5);
            }

            @Test
            void givenPieceB_whenPieceBMovesVertically_ThenShowSuccess() {

                game.move(5, 5, 0, 2);

                Piece actual = game.get(5, 7);
                assertEquals(expectedRook, actual);

            }

            @Test
            void givenPieceB_whenPieceBMovesHorizontally_ThenShowSuccess() {

                game.move(5, 5, 2, 0);

                Piece actual = game.get(7, 5);
                assertEquals(expectedRook, actual);

            }

            @Test
            void givenPieceB_whenPieceBMovesDiagonally_ThenShowFailure() {
                Piece expectedRook = Piece.B;
                game.insertPiece(expectedRook, 5, 5);
                assertFalse(game.move(5, 5, 2, 2));

            }

            @Test
            void givenPieceB_whenPieceBCollides_ThenMoveOneSpaceBeforeCollision() {
                //given
                Piece collider = Piece.B;

                game.insertPiece(collider, 7, 5);

                //when
                game.move(5, 5, 2, 0);

                //then
                Piece actual = game.get(6, 5);
                assertEquals(expectedRook, actual);
            }
        }
    }
}
