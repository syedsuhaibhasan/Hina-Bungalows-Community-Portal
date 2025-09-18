/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

/**
 * Utility class for applying FlatLaf and refreshing existing UI.
 */
public final class UIUtils {

    private UIUtils() { /* no instances */ }

    /**
     * Apply FlatLaf and refresh all existing windows so they adopt the new L&F.
     * Call this BEFORE creating components (preferred) or after (it will update).
     */
    public static void applyFlatLafAndRefresh() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Setup FlatLaf (non-deprecated)
                FlatLightLaf.setup();

                // Global corner rounding (tweak values to taste)
                UIManager.put("Component.arc", 16);       // panels and general components
                UIManager.put("Button.arc", 14);          // buttons
                UIManager.put("TextComponent.arc", 12);   // text fields

                // Update all windows so they pick up new L&F
                for (Window w : Window.getWindows()) {
                    if (w.isDisplayable()) {
                        SwingUtilities.updateComponentTreeUI(w);
                        w.invalidate();
                        w.validate();
                        w.repaint();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Convert an existing JPanel instance into a RoundedPanel visually by
     * transferring its children/layout into a new RoundedPanel and replacing
     * it in its parent. This method tries to preserve layout & constraints
     * for common simple cases. Use it in the constructor right after initComponents().
     *
     * Example usage:
     *     UIUtils.makePanelRounded(headerPanel, 20);
     *
     * NOTE: If you used NetBeans GUI builder, call this AFTER initComponents().
     */
    public static void makePanelRounded(JPanel originalPanel, int cornerRadius) {
        if (originalPanel == null) return;
        if (originalPanel instanceof RoundedPanel) return; // already rounded

        Container parent = originalPanel.getParent();
        if (parent == null) {
            // panel not yet added to a parent; easiest option: replace manually in code
            return;
        }

        // Create replacement rounded panel
        RoundedPanel rounded = new RoundedPanel(cornerRadius);
        rounded.setLayout(originalPanel.getLayout()); // preserve layout manager
        rounded.setBackground(originalPanel.getBackground());
        rounded.setBorder(originalPanel.getBorder());
        rounded.setOpaque(false); // RoundedPanel handles painting

        // Transfer children
        Component[] children = originalPanel.getComponents();
        for (Component c : children) {
            originalPanel.remove(c);
            rounded.add(c);
        }

        // Try to preserve index in parent's component list
        int idx = parent.getComponentZOrder(originalPanel);

        // If parent uses BorderLayout we attempt to preserve constraints by re-adding
        Object constraints = null;
        LayoutManager lm = parent.getLayout();
        if (lm instanceof BorderLayout) {
            // Common case: try to detect the constraint by checking each position
            BorderLayout bl = (BorderLayout) lm;
            String[] positions = new String[] {
                    BorderLayout.NORTH, BorderLayout.SOUTH, BorderLayout.EAST,
                    BorderLayout.WEST, BorderLayout.CENTER
            };
            for (String pos : positions) {
                Component c = bl.getLayoutComponent(parent, pos);
                if (c == originalPanel) {
                    constraints = pos;
                    break;
                }
            }
            parent.remove(originalPanel);
            if (constraints != null) parent.add(rounded, constraints);
            else parent.add(rounded, idx); // fallback
        } else {
            // For other layout managers, we add at the same Z-order index (works for many managers)
            parent.remove(originalPanel);
            parent.add(rounded, idx);
        }

        parent.revalidate();
        parent.repaint();
    }
}