import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameLogic implements Serializable {
    private int[][] board;
    private int currentplayer = 1;
    private ArrayList<Integer[]> winners = new ArrayList<>();
    private int lastmovei;
    private int lastmovej;
    private int state = 0;
    private int round = 2;
    private int seconds = 0;
    private int gamemode;

    public GameLogic(int size) {
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                board[i][j] = 0;
        }
        lastmovei = size;
        lastmovej = size;
    }

    public void makemove(int row, int col) {
        board[row][col] = currentplayer;
        lastmovei = row;
        lastmovej = col;
        round += 1;
    }

    public void switchplayer() {
        if (currentplayer == 1)
            currentplayer = 2;
        else
            currentplayer = 1;
    }

    public int getsize() {
        return board.length;
    }

    public int[][] getboard() {
        return board;
    }

    public int getgamemode() {
        return gamemode;
    }

    public void setgamemode(int g) {
        gamemode = g;
    }

    public int getcurrentplayer() {
        return currentplayer;
    }

    public boolean validmove(int i, int j) {
        if (board[i][j] != 0)
            return false;
        if (isempty())
            return true;

        for (int k = 0; k < getsize(); k++) {
            for (int h = 0; h < getsize(); h++)
                if (board[k][h] == 3 || board[k][h] == 4)
                    return false;
        }

        if (i > 0 && i < getsize() - 1 && j > 0 && j < getsize() - 1) {
            if (board[i - 1][j] == 0 && board[i + 1][j] == 0 && board[i][j - 1] == 0 && board[i][j + 1] == 0
                    && board[i - 1][j - 1] == 0 && board[i + 1][j + 1] == 0 && board[i - 1][j + 1] == 0
                    && board[i + 1][j - 1] == 0)
                return false;
            else
                return true;
        }
        if (i == 0 && j > 0 && j < getsize() - 1) {
            if (board[i + 1][j] == 0 && board[i][j - 1] == 0 && board[i][j + 1] == 0
                    && board[i + 1][j + 1] == 0 && board[i + 1][j - 1] == 0)
                return false;
            else
                return true;
        }
        if (i == getsize() - 1 && j > 0 && j < getsize() - 1) {
            if (board[i - 1][j] == 0 && board[i][j - 1] == 0 && board[i][j + 1] == 0
                    && board[i - 1][j - 1] == 0 && board[i - 1][j + 1] == 0)
                return false;
            else
                return true;
        }
        if (i > 0 && i < getsize() - 1 && j == 0) {
            if (board[i - 1][j] == 0 && board[i + 1][j] == 0 && board[i][j + 1] == 0
                    && board[i + 1][j + 1] == 0 && board[i - 1][j + 1] == 0)
                return false;
            else
                return true;
        }
        if (i > 0 && i < getsize() - 1 && j == getsize() - 1) {
            if (board[i - 1][j] == 0 && board[i + 1][j] == 0 && board[i][j - 1] == 0
                    && board[i - 1][j - 1] == 0 && board[i + 1][j - 1] == 0)
                return false;
            else
                return true;
        }

        if (i == 0 && j == 0) {
            if (board[i + 1][j] == 0 && board[i][j + 1] == 0 && board[i + 1][j + 1] == 0)
                return false;
            else
                return true;
        }

        if (i == 0 && j == getsize() - 1) {
            if (board[i + 1][j] == 0 && board[i][j - 1] == 0 && board[i + 1][j - 1] == 0)
                return false;
            else
                return true;
        }

        if (i == getsize() - 1 && j == 0) {
            if (board[i - 1][j] == 0 && board[i][j + 1] == 0 && board[i - 1][j + 1] == 0)
                return false;
            else
                return true;
        }

        if (i == getsize() - 1 && j == getsize() - 1) {
            if (board[i - 1][j] == 0 && board[i][j - 1] == 0 && board[i - 1][j - 1] == 0)
                return false;
            else
                return true;
        }
        return false;
    }

    boolean checkwin(int i, int j, int player) {
        int inarow = 0;
        int h = 0;
        // VERTICAL
        // down

        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board[k][j] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, j });
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // up
        for (int k = i - 1; k > i - 5; k -= 1) {
            try {
                if (board[k][j] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, j });
                } else
                    break;
            } catch (Exception e) {
            }
        }

        if (inarow >= 4) {
            winners.add(new Integer[] { i, j });
            state = 1;
            return true;
        }

        inarow = 0;
        winners.clear();

        // HORIZONTAL
        // right
        for (int k = j + 1; k < j + 5; k += 1) {
            try {
                if (board[i][k] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { i, k });
                } else
                    break;
            } catch (Exception e) {
            }
        }

        // left
        for (int k = j - 1; k > j - 5; k -= 1) {
            try {
                if (board[i][k] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { i, k });
                } else
                    break;
            } catch (Exception e) {
            }
        }

        if (inarow >= 4) {
            winners.add(new Integer[] { i, j });
            state = 1;
            return true;
        }

        inarow = 0;
        winners.clear();
        // DIAGONAL RIGHT
        // down
        h = j + 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board[k][h] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, h });
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
                if (board[k][h] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, h });
                } else
                    break;
                if (h > j - 5)
                    h -= 1;
                else
                    break;
            } catch (Exception e) {
            }
        }

        if (inarow >= 4) {
            winners.add(new Integer[] { i, j });
            state = 1;
            return true;
        }

        winners.clear();
        inarow = 0;
        // DIAGONAL LEFT
        // down
        h = j - 1;
        for (int k = i + 1; k < i + 5; k += 1) {
            try {
                if (board[k][h] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, h });
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
                if (board[k][h] == player) {
                    inarow += 1;
                    winners.add(new Integer[] { k, h });
                } else
                    break;
                if (h < j + 5)
                    h += 1;
            } catch (Exception e) {
            }
        }

        if (inarow >= 4) {
            winners.add(new Integer[] { i, j });
            state = 1;
            return true;
        }

        winners.clear();
        return false;

    }

    public ArrayList<Integer[]> getwinners() {
        return winners;
    }

    public void save(String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
    }

    public void load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        GameLogic loadedobj = (GameLogic) in.readObject();

        board = loadedobj.getboard();
        currentplayer = loadedobj.getcurrentplayer();
        winners = loadedobj.getwinners();
        lastmovei = loadedobj.getlastmovei();
        lastmovej = loadedobj.getlastmovej();
        state = loadedobj.getstate();
        round = loadedobj.getround();
        seconds = loadedobj.getseconds();
    }

    public void reset(int size) {
        for (int i = 0; i < getsize(); i++) {
            for (int j = 0; j < getsize(); j++)
                board[i][j] = 0;
        }

        currentplayer = 1;
        round = 2;
        seconds = 0;
        state = 0;
        winners.clear();
        lastmovei = size;
        lastmovej = size;
    }

    public boolean isempty() {
        for (int i = 0; i < getsize(); i++) {
            for (int j = 0; j < getsize(); j++)
                if (board[i][j] != 0)
                    return false;
        }
        return true;
    }

    public int getlastmovei() {
        return lastmovei;
    }

    public int getlastmovej() {
        return lastmovej;
    }

    public void setlastmovei(int i) {
        lastmovei = i;
    }

    public void setlastmovej(int j) {
        lastmovej = j;
    }

    public int getstate() {
        return state;
    }

    public int getround() {
        return round;
    }

    public int getseconds() {
        return seconds;
    }

    public void setseconds(int secs) {
        seconds = secs;
    }

    public void setround(int r) {
        round = r;
    }

    
}
