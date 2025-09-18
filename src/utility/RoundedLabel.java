package utility; // change to the package you use, e.g., yourproject.utility

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * RoundedLabel (top-only) - draws a rounded background with only the upper
 * corners rounded; lower corners remain square.
 */
public class RoundedLabel extends JLabel {
    private final int cornerRadius;
    private Color borderColor = null; // optional border color

    public RoundedLabel(String text, int radius) {
        super(text, SwingConstants.CENTER);
        this.cornerRadius = Math.max(0, radius);
        // keep custom painting consistent
        super.setOpaque(false);
        setForeground(Color.WHITE);
        setBackground(new Color(33, 150, 243));
        setFont(getFont().deriveFont(Font.BOLD));
    }

    @Override
    public void setOpaque(boolean isOpaque) {
        // prevent superclass from painting full rectangular background
        super.setOpaque(false);
    }

    public void setBorderColor(Color c) {
        this.borderColor = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            super.paintComponent(g);
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            // Round rectangle across full bounds
            RoundRectangle2D rounded = new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius);

            // Rectangle that covers lower half to square off bottom corners
            Rectangle2D bottomRect = new Rectangle2D.Float(0, h / 2f, w, h / 2f);

            // Combine shapes
            Area shape = new Area(rounded);
            shape.add(new Area(bottomRect)); // adds bottom rectangle -> keeps rounded top corners

            // Fill background
            g2.setColor(getBackground());
            g2.fill(shape);

            // Optional border
            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(shape);
            }

            // Draw text/icon using JLabel's painting (use the same graphics)
            super.paintComponent(g2);
        } finally {
            // ALWAYS dispose the copy of Graphics2D inside a finally
            g2.dispose();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.width = Math.max(d.width + 24, cornerRadius * 2 + 20);
        d.height = Math.max(d.height + 8, cornerRadius + 14);
        return d;
    }
}
