import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.*;
// import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;

//import javax.lang.model.util.SimpleTypeVisitor6;
import javax.swing.*;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.border.Border;
// import javax.swing.JColorChooser;

import javax.imageio.ImageIO;

public class Menu extends GUI{


  public static void launcher(){

    JFrame window = new JFrame("Menu");
    JButton b1 = new JButton();
    b1.setSize(400,400);
   b1.setVisible(true);
   b1.setText("Lancer une partie");
    JPanel p = new JPanel();
    p.setLayout(null);
    p.setPreferredSize(new Dimension(800, 800));

    Font font =new Font("Serif", Font.PLAIN, 20);
      try{
          font = Font.createFont(Font.TRUETYPE_FONT, GUI.class.getResourceAsStream("ka1.ttf"));
      }
      catch(Exception e){}

      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JRootPane root = new JRootPane();
    p.add(root, BorderLayout.CENTER);
    p.add(b1,BorderLayout.CENTER);
    p.setLayout(new BorderLayout());
    window.add(p,BorderLayout.CENTER);
    window.add(b1);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);


    b1.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            //   Iterator<Case> cases_selected = Plateau.plateau.iterator();
            //   while (cases_selected.hasNext()) {

            Plateau.set_element();
            new GUI().display();
            window.setVisible(false);

      }

  });
}
}
