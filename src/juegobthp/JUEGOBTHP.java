package juegobthp;

public class JUEGOBTHP {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrameJuego ventana = new JFrameJuego();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
