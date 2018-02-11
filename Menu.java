import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu {

    private static boolean settings_visible = false;
    public JFrame frame = new JFrame("Menu");
    public JPanel settings = new JPanel();

    public Menu() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new MenuPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public void settings_switcher() {
        if (settings_visible) {
            settings_visible = false;
        } else {
            settings_visible = true;
        }
    }

    public class Slide extends JPanel {
        private JLabel label = new JLabel("Nombre de Métabolites : 40");
        private JLabel label_metabol = new JLabel("Distance max des Métabolites : 2");

        public Slide() {
            JSlider slide = new JSlider();

            slide.setMaximum(40);
            slide.setMinimum(10);
            slide.setValue(40);
            slide.setPaintTicks(true);
            slide.setPaintLabels(true);
            slide.setMinorTickSpacing(10);
            slide.setMajorTickSpacing(10);
            slide.setSnapToTicks(true);
            slide.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    label.setText("Nombre de Métabolites : " + ((JSlider) event.getSource()).getValue());
                    Plateau.set_nb_metabol(((JSlider) event.getSource()).getValue());
                }
            });

            JSlider distance_metabol = new JSlider();

            distance_metabol.setMaximum(4);
            distance_metabol.setMinimum(1);
            distance_metabol.setValue(2);
            distance_metabol.setPaintTicks(true);
            distance_metabol.setPaintLabels(true);
            distance_metabol.setMinorTickSpacing(1);
            distance_metabol.setMajorTickSpacing(1);

            distance_metabol.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    label_metabol.setText("Distance max des Métabolites : " + ((JSlider) event.getSource()).getValue());
                    Metabolite.set_distance_metabol(((JSlider) event.getSource()).getValue());
                }
            });

            label.setBorder(new EmptyBorder(0, 0, 20, 0));
            settings.add(label);
            settings.add(slide);

            label_metabol.setBorder(new EmptyBorder(20, 0, 20, 0));
            settings.add(label_metabol);
            settings.add(distance_metabol);
        }
    }

    public class MenuPane extends JPanel {

        public MenuPane() {

            setBorder(new EmptyBorder(20, 50, 50, 50));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            settings.removeAll();
            settings.setVisible(settings_visible);
            settings.setEnabled(false);
            settings.setBorder(new EmptyBorder(20, 0, 50, 0));
            settings.setLayout(new BoxLayout(settings, BoxLayout.PAGE_AXIS));

            JLabel settings_title = new JLabel("Paramètres");
            settings_title.setBorder(new EmptyBorder(20, 0, 50, 0));
            settings.add(settings_title);

            new Slide();

            JButton jouer = new JButton("Jouer une nouvelle partie");

            jouer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUI.debut_partie();
                }
            });

            JButton param = new JButton("Paramètres");
            param.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settings_switcher();
                    frame.getContentPane().removeAll();
                    frame.add(new MenuPane());
                    frame.pack();
                }
            });

            JButton quit = new JButton("Quitter");
            quit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            add(jouer, gbc);
            if (settings_visible == false) {
                add(param, gbc);
            }
            add(quit, gbc);
            add(settings, gbc);
        }

    }

    public static void main(String[] args) {
        new Menu();
    }

}

/*import java.awt.*;
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

public class Menu{


  public static void launcher(){

    JFrame window = new JFrame("Menu");
    JButton b1 = new JButton();
    b1.setSize(500,400);
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
        	GUI.debut_partie();
        	window.setVisible(false);

      }

  });
}
  
  public static void main(String[] args){
	    launcher();
  }
  
}
*/