/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility; 

import javax.swing.*;
import java.awt.*;

/**
 * Simple rounded-corner JPanel. It paints a filled rounded rectangle as background
 * and supports a border color if desired.
 */
public class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private Color borderColor = new Color(33, 150, 243); // optional border color

    public RoundedPanel(int cornerRadius) {
        super();
        this.cornerRadius = cornerRadius;
        setOpaque(false); // we paint our background
    }

    public RoundedPanel(int cornerRadius, Color background) {
        this(cornerRadius);
        setBackground(background);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Paint rounded background
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            // background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, w, h, cornerRadius, cornerRadius);

            // optional border
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, cornerRadius, cornerRadius);
        } finally {
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
