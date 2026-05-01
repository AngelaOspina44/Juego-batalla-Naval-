package juegobthp.interfaz;

import javax.swing.*;
import java.awt.*;

public class PanelFondo extends JPanel {

    private Image imagen;

    public PanelFondo(String ruta) {
        imagen = new ImageIcon(getClass().getResource(ruta)).getImage();
        setLayout(new GridBagLayout()); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}