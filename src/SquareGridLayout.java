import java.awt.*;

public class SquareGridLayout extends GridLayout {

    public SquareGridLayout(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension original = super.preferredLayoutSize(parent);
        int minSize = Math.min(original.width, original.height);
        return new Dimension(minSize * getColumns(), minSize * getRows());
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            if (ncomponents == 0) {
                return;
            }
            int nrows = getRows();
            int ncols = getColumns();
            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }
            int w = parent.getWidth();
            int h = parent.getHeight();
            int size = Math.min(w / ncols, h / nrows);

            int totalWidth = size * ncols;
            int totalHeight = size * nrows;

            int extraWidth = w - totalWidth;
            int extraHeight = h - totalHeight;

            int paddingLeft = extraWidth / 2;
            int paddingTop = extraHeight / 2;

            for (int c = 0, x = paddingLeft; c < ncols; c++, x += size) {
                for (int r = 0, y = paddingTop; r < nrows; r++, y += size) {
                    int i = r * ncols + c;
                    if (i < ncomponents) {
                        parent.getComponent(i).setBounds(x, y, size, size);
                    }
                }
            }
        }
    }
}