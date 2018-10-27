package com.snakesandladders;

import javax.swing.*;
import java.awt.*;

/**
 * Created by shash on 10/16/2018.
 */
class ElliptIcon implements Icon {
    private int w, h;
    private Color color;

    public ElliptIcon(int w, int h, Color color) {
        this.w = w;
        this.h = h;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }

    @Override
    public int getIconWidth() {
        return w;
    }

    @Override
    public int getIconHeight() {
        return h;
    }
}