package filtro;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Finestra extends JFrame{
    JPanel panel;
    JLabel etiqueta;
 
 	//Questa funzione ci permette di visualizzare a schermo l'effetto fusione (invecchiamento+blur)
    public Finestra(){
        setTitle("Fusione");
        setLocation(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600); //ridimensioniamo l'immagine ad una grandezza di 600x600
        setResizable(true);
        setVisible(true);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        etiqueta = new JLabel();
        panel.add(etiqueta);
 
        getContentPane().add(panel);
    }
 
    public void setImage(Image imagen){
        panel.removeAll();
 
        ImageIcon icon = new ImageIcon(imagen.getScaledInstance(etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH));
        etiqueta.setIcon(icon);
 
        panel.add(etiqueta);
        panel.updateUI();
    }
}