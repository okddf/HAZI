public class Ai {
    private int[][] scores; // Helper board
    private int lastmovei;
    private int lastmovej;
    private GameLogic board; // state of the board

    public Ai(int size, GameLogic b) {
        scores = new int[size][size];
        board = b;
        lastmovei = size;
        lastmovej = size;
    }

    public int[] aiMove() {
        for (int i = 0; i < board.getsize(); i++) {
            for (int j = 0; j < board.getsize(); j++)
                if (board.validmove(i, j)) {
                    scores[i][j] += calculateMoveScore(i, j);
                } else {
                    scores[i][j] = 0;
                }
        }

        int besti = 0;
        int bestj = 0;
        int bestscore = 0;
        for (int i = 0; i < board.getsize(); i++) {
            for (int j = 0; j < board.getsize(); j++)
                if (scores[i][j] > bestscore) {
                    bestscore = scores[i][j];
                    besti = i;
                    bestj = j;
                }
        }
        lastmovei = besti;
        lastmovej = bestj;
        return new int[] { besti, bestj };
    }

    private int calculateMoveScore(int i, int j) {
        int score = 0;
        int inarow = 0;
        int h = 0;
        // VERTICAL
        // down

        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][j] == 1) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // up
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][j] == 1) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 1);

        inarow = 0;

        // HORIZONTAL
        // right
        for (int k = j + 1; k < j + 5; k += 1) {
            try {
                if (board.getboard()[i][k] == 1) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // left
        for (int k = j - 1; k > j - 5; k -= 1) {
            try {
                if (board.getboard()[i][k] == 1) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 1);
        inarow = 0;

        // DIAGONAL RIGHT
        // down
        h = j + 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][h] == 1) {
                    inarow += 1;
                } else
                    break;
                if (h < j + 5)
                    h += 1;
                else
                    break;
            } catch (Exception e) {
            }
        }

        // up
        h = j - 1;
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][h] == 1) {
                    inarow += 1;
                } else
                    break;
                if (h > j - 5)
                    h -= 1;
                else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 1);
        inarow = 0;
        // DIAGONAL LEFT
        // down
        h = j - 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][h] == 1) {
                    inarow += 1;
                } else
                    break;
                if (h > j - 5)
                    h -= 1;
            } catch (Exception e) {
            }
        }

        // up
        h = j + 1;
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][h] == 1) {
                    inarow += 1;
                } else
                    break;
                if (h < j + 5)
                    h += 1;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 1);
        inarow = 0;

        // player 2

        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][j] == 2) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // up
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][j] == 2) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 2);

        inarow = 0;

        // HORIZONTAL
        // right
        for (int k = j + 1; k < j + 5; k += 1) {
            try {
                if (board.getboard()[i][k] == 2) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // left
        for (int k = j - 1; k > j - 5; k -= 1) {
            try {
                if (board.getboard()[i][k] == 2) {
                    inarow += 1;
                } else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 2);
        inarow = 0;

        // DIAGONAL RIGHT
        // down
        h = j + 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][h] == 2) {
                    inarow += 1;
                } else
                    break;
                if (h < j + 5)
                    h += 1;
                else
                    break;
            } catch (Exception e) {
            }
        }

        // up
        h = j - 1;
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][h] == 2) {
                    inarow += 1;
                } else
                    break;
                if (h > j - 5)
                    h -= 1;
                else
                    break;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 2);
        inarow = 0;
        // DIAGONAL LEFT
        // down
        h = j - 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board.getboard()[k][h] == 2) {
                    inarow += 1;
                } else
                    break;
                if (h > j - 5)
                    h -= 1;
            } catch (Exception e) {
            }
        }

        // up
        h = j + 1;
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board.getboard()[k][h] == 2) {
                    inarow += 1;
                } else
                    break;
                if (h < j + 5)
                    h += 1;
            } catch (Exception e) {
            }
        }

        score += evaluate(inarow, 2);

        return score;
    }

    private int evaluate(int inarow, int player) {
        int score = 0;
        if (player == 1) {
            if (inarow == 1)
                score += 10;
            if (inarow == 2)
                score += 100;
            if (inarow == 3)
                score += 2000;
            if (inarow == 4)
                score += 30000;
        }

        if (player == 2) {
            if (inarow == 1)
                score += 20;
            if (inarow == 2)
                score += 200;
            if (inarow == 3)
                score += 4000;
            if (inarow == 4)
                score += 50000;
        }

        return score;
    }

    public int getlastmovei() {
        return lastmovei;
    }

    public int getlastmovej() {
        return lastmovej;
    }
}
