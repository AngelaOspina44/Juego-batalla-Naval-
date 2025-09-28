package juegobthp;

import juegobthp.interfaz.Bienvenida;

public class JUEGOBTHP {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Bienvenida ventana = new Bienvenida();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            ventana.setResizable(false);
        });
    }
}
