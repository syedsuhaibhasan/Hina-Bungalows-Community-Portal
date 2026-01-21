/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author Sohaib Hasan
 */
public class BDutility {
    
    public static void setimage(JFrame frame ,String imagePath, int newwidth, int newheight){
        try{
            InputStream in = openResourceStream(imagePath);
            if (in == null) {
                System.err.println("Image resource not found: " + imagePath);
                return;
            }
            BufferedImage originalImage = ImageIO.read(in);
            BufferedImage resizedImage = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g2 = resizedImage.createGraphics();
            g2.drawImage(originalImage.getScaledInstance(newwidth, newheight, Image.SCALE_SMOOTH), 0, 0, null);
            g2.dispose();
            
            ImageIcon backgroundImage = new ImageIcon(resizedImage);
            JLabel backgroundLabel = new JLabel(backgroundImage);
            
            backgroundLabel.setBounds(0, 0, newwidth, newheight);

            if (frame.getContentPane() instanceof JComponent jc) {
                jc.setOpaque(false);
            }
            Integer bgLayer = Integer.valueOf(JLayeredPane.FRAME_CONTENT_LAYER.intValue() - 1);
            frame.getLayeredPane().add(backgroundLabel, bgLayer);

            // Keep background size in sync with frame size
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    backgroundLabel.setSize(frame.getSize());
                }
            });

            frame.validate();
            
        }catch(Exception ex){
         ex.printStackTrace();   
        }
         
      
    }

     private static InputStream openResourceStream(String imagePath) {
        if (imagePath == null) return null;
        String p = imagePath.trim();
        if (p.isEmpty()) return null;

        InputStream in = BDutility.class.getResourceAsStream(p);
        if (in != null) return in;

        if (!p.startsWith("/")) {
            in = BDutility.class.getResourceAsStream("/" + p);
            if (in != null) return in;
        }

        String normalized = p;
        if (normalized.startsWith("/")) normalized = normalized.substring(1);
        if (normalized.startsWith("utility/images/")) {
            in = BDutility.class.getResourceAsStream("images/" + normalized.substring("utility/images/".length()));
            if (in != null) return in;
            in = BDutility.class.getResourceAsStream("/" + normalized);
            if (in != null) return in;
        }

        String rel = p.startsWith("/") ? p.substring(1) : p;
        InputStream fileIn = openFileStream(Paths.get("src").resolve(rel));
        if (fileIn != null) return fileIn;
        fileIn = openFileStream(Paths.get(System.getProperty("user.dir")).resolve(rel));
        if (fileIn != null) return fileIn;

        return null;
     }

     private static InputStream openFileStream(Path path) {
        try {
            if (path == null) return null;
            if (!Files.exists(path)) return null;
            return Files.newInputStream(path);
        } catch (Exception ex) {
            return null;
        }
     }
     private static HashMap <String, JFrame> formsMap = new HashMap<>();
               
        public static void openForm(String formName, JFrame formInstance){
            JFrame existingForm = formsMap.get(formName);
            
            if(existingForm == null || !existingForm.isVisible()){
             formsMap.put(formName,formInstance);
             formInstance.setVisible(true);
            } 
            else{
                existingForm.toFront();
            }
        }
 
        public static String getPath(String finalPath){
            String projectPath = System.getProperty("user.dir");
            return projectPath + "\\src\\" + finalPath;
        }
        
        public static String getFileExtension(String fileName){
        
            int lastDotIndex = fileName.lastIndexOf(".");
       
            if(lastDotIndex != -1){
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
        
        }
        
        public static BufferedImage scaleImage(BufferedImage originalImage, BufferedImage selectedImage){
            int width = selectedImage.getWidth();
            int height = selectedImage.getHeight();
            BufferedImage scaledImage = new BufferedImage(width, height, originalImage.getType());
            scaledImage.createGraphics().drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),0,0,null);
            return scaledImage;
        }
}
