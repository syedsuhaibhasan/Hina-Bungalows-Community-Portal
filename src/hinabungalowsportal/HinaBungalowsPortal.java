/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hinabungalowsportal;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
/**
 *
 * @author humai
 */
public class HinaBungalowsPortal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UIManager.put("Component.arc", 16);      // general component corner radius
        UIManager.put("Button.arc", 14);         // button radius
        UIManager.put("TextComponent.arc", 12);  // text fields
        FlatLightLaf.setup(); // install FlatLaf L&F

        SwingUtilities.invokeLater(() -> {
            // open login window or admin dashboard
            form.login loginWindow = new form.login();
            loginWindow.setVisible(true);
        });       
    }
    
}
