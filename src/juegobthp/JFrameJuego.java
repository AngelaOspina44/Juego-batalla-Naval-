package juegobthp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import juegobthp.Tablero;

public class JFrameJuego extends javax.swing.JFrame {
    
    private final int TAMANO = 6;
    private JButton[][] botonesJugador;
    private JButton[][] botonesMaquina;

    private JugadorHumano jugador;
    private Maquina maquina;
    private Tablero tableroJugador;
    private Tablero tableroMaquina;
    boolean juegaMaquina = false;
    int barcosJugador = 0; 
    
    public JFrameJuego() {
        
        // Primero crea los tableros antes de usar
        tableroJugador = new Tablero(TAMANO);
        tableroMaquina = new Tablero(TAMANO);

        // Coloca barcos aleatorios en tm
        colocarBarcosAleatorios(tableroMaquina, 6);

        jugador = new JugadorHumano(tableroJugador);
        maquina = new Maquina(tableroMaquina);

        botonesJugador = new JButton[TAMANO][TAMANO];
        botonesMaquina = new JButton[TAMANO][TAMANO];

        setTitle("Juego Batalla Naval");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents(); // si tienes método para inicializar componentes visuales
        inicializarTableros();
    }
    
    private void inicializarTableros() {
    TableroJugador.setLayout(new GridLayout(TAMANO, TAMANO, 2, 2));
    TableroMaquina.setLayout(new GridLayout(TAMANO, TAMANO, 2, 2));
    TableroJugador.removeAll();
    TableroMaquina.removeAll();

    for (int i = 0; i < TAMANO; i++) {
        for (int j = 0; j < TAMANO; j++) {
            final int x = i, y = j;
            botonesJugador[i][j] = new JButton();
            botonesJugador[i][j].setPreferredSize(new Dimension(50, 50));
            botonesJugador[i][j].addActionListener(e -> {
                tableroJugador.colocarBarco(x, y, 2, false);
                botonesJugador[x][y].setText("🛳");
                botonesJugador[x+1][y].setText("🛳");
                barcosJugador++;
                if (barcosJugador == 5) {
                    deshabilitarBotones(botonesJugador);
                }
            });
            

            TableroJugador.add(botonesJugador[i][j]);

            botonesMaquina[i][j] = new JButton();
            botonesMaquina[i][j].setPreferredSize(new Dimension(50, 50));
            botonesMaquina[i][j].addActionListener(e -> {
                jugadorDispara(x, y);
            });
            TableroMaquina.add(botonesMaquina[i][j]);
        }
    }

    TableroJugador.revalidate();
    TableroJugador.repaint();
    TableroMaquina.revalidate();
    TableroMaquina.repaint();
}

    
    private void jugadorDispara(int x, int y) {
        if (!botonesMaquina[x][y].isEnabled()) return;

        boolean acierto = tableroMaquina.hayBarco(x, y);
        if (acierto) {
            botonesMaquina[x][y].setText("✴️");
            botonesMaquina[x][y].setEnabled(true);
        } else {
            botonesMaquina[x][y].setText("🌊O");
            juegaMaquina = true;
            deshabilitarBotones(botonesMaquina);
            disparosMaquina();
            
        }
        habilitarBotones(botonesMaquina);
        System.out.println("Disparo en " + x + "," + y + (acierto ? " - ¡Tocado!" : " - Agua"));
    }
    
    public void deshabilitarBotones(JButton[][] botones) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }
    
    public void habilitarBotones(JButton[][] botones) {
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                botones[i][j].setEnabled(true);
            }
        }
    }
    
    public void actualizarTableros(){
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
                if (tableroJugador.yaDisparo(i, j)) {
                    botonesJugador[i][j].setText("X");
                }
            
            botonesJugador[i][j].setText("");
            botonesJugador[i][j].setEnabled(false);
            tableroMaquina.limpiar();
            colocarBarcosAleatorios(tableroMaquina, 5);

            botonesMaquina[i][j].setText("");
            botonesMaquina[i][j].setEnabled(true);
            }
        }
    }

    public void disparosMaquina() {
        System.out.println("disparosMaquina");
        
        
    Random rand = new Random();
        while (juegaMaquina) {
            int x = rand.nextInt(TAMANO);
            int y = rand.nextInt(TAMANO);
            System.out.println(String.valueOf(x) + " " +String.valueOf(y));

                tableroJugador.recibirDisparo(x, y);
                
                if (tableroJugador.hayBarco(x, y)) {
                    botonesJugador[x][y].setText("✴️");
                }
                else  {
                    botonesJugador[x][y].setText("O");
                    juegaMaquina = false;
                }
                
        }
        
        try {
            //Ponemos a "Dormir" el programa durante los ms que queremos
            Thread.sleep(500);
         } catch (Exception e) {
            System.out.println(e);
         }
    }
    
    
    public void colocarBarcosAleatorios(Tablero tablero, int cantidadBarcos) {
    Random rand = new Random();
        int barcosColocados = 0;
        while (barcosColocados < cantidadBarcos) {
            int x = rand.nextInt(TAMANO);
            int y = rand.nextInt(TAMANO);

            if (!tablero.hayBarco(x, y)) {
                tablero.colocarBarco(x, y, 1, true);
                barcosColocados++;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        TableroJugador = new javax.swing.JPanel();
        TableroMaquina = new javax.swing.JPanel();
        TituloJugador = new javax.swing.JLabel();
        TituloMaquina = new javax.swing.JLabel();
        Titulo = new JLabel("Batalla Naval", JLabel.CENTER);
        ReiniciarJuego = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(0, 153, 204));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        TableroJugador.setBackground(new java.awt.Color(153, 255, 153));
        TableroJugador.setLayout(new java.awt.GridLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 144;
        gridBagConstraints.ipady = 184;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 12, 13, 0);
        panelPrincipal.add(TableroJugador, gridBagConstraints);

        TableroMaquina.setBackground(new java.awt.Color(255, 153, 153));
        TableroMaquina.setLayout(new java.awt.GridLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.ipady = 184;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 13, 12);
        panelPrincipal.add(TableroMaquina, gridBagConstraints);

        TituloJugador.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TituloJugador.setText("Jugador Humano");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 12, 0, 0);
        panelPrincipal.add(TituloJugador, gridBagConstraints);

        TituloMaquina.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TituloMaquina.setText("Maquina");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = -3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 28, 0, 0);
        panelPrincipal.add(TituloMaquina, gridBagConstraints);

        Titulo.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        Titulo.setText("Batalla Naval");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.ipady = -17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 63, 0, 0);
        panelPrincipal.add(Titulo, gridBagConstraints);

        ReiniciarJuego.setText("Reiniciar");
        ReiniciarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReiniciarJuegoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(118, 7, 0, 0);
        panelPrincipal.add(ReiniciarJuego, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ReiniciarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReiniciarJuegoActionPerformed
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {

            botonesJugador[i][j].setText("");
            botonesJugador[i][j].setEnabled(false);
            tableroMaquina.limpiar();
            colocarBarcosAleatorios(tableroMaquina, 5);

            botonesMaquina[i][j].setText("");
            botonesMaquina[i][j].setEnabled(true);
            }
        }
    }//GEN-LAST:event_ReiniciarJuegoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ReiniciarJuego;
    private javax.swing.JPanel TableroJugador;
    private javax.swing.JPanel TableroMaquina;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel TituloJugador;
    private javax.swing.JLabel TituloMaquina;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
