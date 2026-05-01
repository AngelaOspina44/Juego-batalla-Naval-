package juegobthp.interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import juegobthp.JugadorHumano;
import juegobthp.Maquina;
import juegobthp.Posicion;
import juegobthp.PuntosJugadores;
import juegobthp.Tablero;
import juegobthp.barcos.Acorazado;
import juegobthp.barcos.Barco;
import juegobthp.barcos.Crucero;
import juegobthp.barcos.Destructor;
import juegobthp.iconos.Icono;
import juegobthp.iconos.IconoAcierto;
import juegobthp.iconos.IconoAgua;
import juegobthp.iconos.IconoBarco;

public class JFrameJuego extends javax.swing.JFrame {

    private final int tamañoTablero = 6;
    private JButton[][] botonesJugador;
    private JButton[][] botonesMaquina;

    private JugadorHumano jugador;
    private Maquina maquina;
    private Tablero tableroJugador;
    private Tablero tableroMaquina;

    boolean juegaMaquina = false;
    int barcosJugador = 0;
    int aciertosJugador = 0;
    int aciertosMaquina = 0;
    private ArrayList<Barco> barcosDisponibles;

    private String nombreJugador = "Jugador";
    private final String NOMBRE_MAQUINA = "Maquina";

    private Icono iconoAgua;
    private Icono iconoBarco;
    private Icono iconoAcierto;

    public JFrameJuego(String nombreRealJugador) {
        tableroJugador = new Tablero(tamañoTablero);
        tableroMaquina = new Tablero(tamañoTablero);
        iconoAgua = new IconoAgua();
        iconoBarco = new IconoBarco();
        iconoAcierto = new IconoAcierto();

        nombreJugador = nombreRealJugador.trim();
        if (nombreJugador.isEmpty())
            nombreJugador = "Jugador";

        jugador = new JugadorHumano(tableroJugador);
        maquina = new Maquina(tableroMaquina);

        botonesJugador = new JButton[tamañoTablero][tamañoTablero];
        botonesMaquina = new JButton[tamañoTablero][tamañoTablero];

        barcosDisponibles = new ArrayList<>(
                Arrays.asList(new Crucero(), new Crucero(), new Destructor(), new Destructor(), new Destructor(),
                        new Acorazado()));

        setTitle("Juego - Batalla Naval");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        inicializarTableros();
    }

    private void inicializarTableros() {
        TableroJugador.setLayout(new GridLayout(tamañoTablero, tamañoTablero, 1, 1));
        TableroMaquina.setLayout(new GridLayout(tamañoTablero, tamañoTablero, 1, 1));
        TableroJugador.removeAll();
        TableroMaquina.removeAll();

        tableroJugador.limpiar();
        tableroMaquina.limpiar();

        barcosJugador = 0;
        aciertosJugador = 0;
        aciertosMaquina = 0;

        PuntosJugadores.agregarJugador(nombreJugador);
        PuntosJugadores.agregarJugador(NOMBRE_MAQUINA);

        colocarBarcosAleatorios(tableroMaquina);
        System.out.println("Barcos de la máquina colocados");

        ArrayList<Barco> barcosDisponibles;
        barcosDisponibles = new ArrayList<>(
                Arrays.asList(new Crucero(), new Crucero(), new Acorazado(), new Destructor(),
                        new Destructor(), new Destructor()));

        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                final int fila = i, columna = j;

                botonesJugador[fila][columna] = new JButton();
                botonesJugador[fila][columna].setPreferredSize(new Dimension(50, 50));
                botonesJugador[fila][columna].addActionListener(_ -> {
                    boolean horizontal = Math.random() < 0.5;

                    Barco barco = barcosDisponibles.get(barcosJugador);
                    barco.setHorizontal(horizontal);

                    boolean sePuedeColocar = tableroJugador.colocarBarco(new Posicion(fila, columna), barco);
                    if (sePuedeColocar) {
                        for (Posicion posicion : barco.getPosiciones()) {
                            botonesJugador[posicion.getFila()][posicion.getColumna()].setEnabled(false);
                            iconoBarco.dibujar(botonesJugador[posicion.getFila()][posicion.getColumna()]);
                            // botonesJugador[posicion.getFila()][posicion.getColumna()].setText("🚢");
                        }
                        barcosJugador++;
                    }

                    if (barcosJugador >= barcosDisponibles.size()) {
                        deshabilitarBotones(botonesJugador);
                        habilitarBotones(botonesMaquina);
                    }
                });

                botonesMaquina[fila][columna] = new JButton();
                botonesMaquina[fila][columna].setPreferredSize(new Dimension(50, 50));
                botonesMaquina[fila][columna].addActionListener(_ -> jugadorDispara(new Posicion(fila, columna)));
                botonesMaquina[fila][columna].setEnabled(false);

                TableroMaquina.add(botonesMaquina[fila][columna]);
                TableroJugador.add(botonesJugador[fila][columna]);
            }
        }

        TableroJugador.revalidate();
        TableroJugador.repaint();
        TableroMaquina.revalidate();
        TableroMaquina.repaint();
    }

    private boolean verificarFinDelJuego(String posibleGanador, int aciertos) {
        if (aciertos == 11) {
            definirGanador(posibleGanador);
            return true;
        }
        return false;
    }

    private void definirGanador(String ganador) {
        String perdedor = ganador.equals(nombreJugador) ? NOMBRE_MAQUINA : nombreJugador;

        PuntosJugadores.sumarVictoria(ganador);
        PuntosJugadores.sumarDerrota(perdedor);

        juegaMaquina = false;
        deshabilitarBotones(botonesJugador);
        deshabilitarBotones(botonesMaquina);

        System.out.println("Gana " + ganador.toLowerCase());
        new Ganador(ganador, PuntosJugadores.cargarPuntajes());
    }

    private void jugadorDispara(Posicion posicion) {
        int x = posicion.getFila();
        int y = posicion.getColumna();

        if (!botonesMaquina[x][y].isEnabled() || barcosJugador < 6) {
            return;
        }

        boolean acierto = jugador.disparar(posicion, tableroMaquina);
        if (acierto) {
            iconoAcierto.dibujar(botonesMaquina[x][y]);
            // botonesMaquina[x][y].setText("✴️");
            aciertosJugador++;
        } else {
            iconoAgua.dibujar(botonesMaquina[x][y]);
            // botonesMaquina[x][y].setText("🌊");
            juegaMaquina = true;
            deshabilitarBotones(botonesMaquina);
            disparosMaquina();
        }

        System.out.println("Disparo en " + x + "," + y + (acierto ? " - ¡Tocado! ✴️" : " - Agua 🌊"));

        // if (aciertosJugador == 11) {
        // PuntosJugadores.sumarVictoria(nombreJugador);
        // PuntosJugadores.sumarDerrota(NOMBRE_MAQUINA);

        // juegaMaquina = false;
        // deshabilitarBotones(botonesJugador);
        // deshabilitarBotones(botonesMaquina);
        // System.out.println("Gana jugador");
        // new Ganador("Angela", PuntosJugadores.cargarPuntajes());
        // return;
        // }

        boolean finDePartida = verificarFinDelJuego(nombreJugador, aciertosJugador);
        if (finDePartida)
            return;
        habilitarBotonesNoDisparados(botonesMaquina);
    }

    // activa solo las casillas (botones) que NO han sido disparadas todavía, y
    // bloquea (deshabilita) las que ya fueron usadas
    private void habilitarBotonesNoDisparados(JButton[][] botones) {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                // Solo habilita si el botón no tiene texto (es decir, no se ha disparado ahí)
                if (botones[i][j].getIcon() == null)
                    botones[i][j].setEnabled(true);
                else
                    botones[i][j].setEnabled(false);
            }
        }
    }

    public void deshabilitarBotones(JButton[][] botones) {
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }

    public void habilitarBotones(JButton[][] botones) {
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                botones[i][j].setEnabled(true);
            }
        }
    }

    public void disparosMaquina() {
        System.out.println("disparosMaquina");
        Random rand = new Random();

        while (juegaMaquina) {
            int x = rand.nextInt(tamañoTablero);
            int y = rand.nextInt(tamañoTablero);
            Posicion posicionAleatoria = new Posicion(x, y);

            if (tableroJugador.yaDisparo(posicionAleatoria)) {
                System.out.println("Ya disparó en " + x + "," + y);
                continue;
            }
            boolean acierto = maquina.disparar(posicionAleatoria, tableroJugador);

            if (acierto) {
                iconoAcierto.dibujar(botonesJugador[x][y]);
                // botonesJugador[x][y].setText("✴️");
                aciertosMaquina++;
            } else {
                iconoAgua.dibujar(botonesJugador[x][y]);
                // botonesJugador[x][y].setText("🌊");
                juegaMaquina = false;
            }

            // if (aciertosMaquina == 11) {
            // PuntosJugadores.sumarVictoria(NOMBRE_MAQUINA);
            // PuntosJugadores.sumarDerrota(nombreJugador);

            // juegaMaquina = false;
            // deshabilitarBotones(botonesJugador);
            // deshabilitarBotones(botonesMaquina);
            // System.out.println("Gana maquina");

            // new Ganador(NOMBRE_MAQUINA, PuntosJugadores.cargarPuntajes());
            // }
            boolean finDePartida = verificarFinDelJuego(NOMBRE_MAQUINA, aciertosMaquina);
            if (finDePartida)
                return;
        }
    }

    public void colocarBarcosAleatorios(Tablero tablero) {
        Random rand = new Random();
        for (int i = 0; i < barcosDisponibles.size(); i++) {
            boolean sePudoColocar = false;

            do {
                int x = rand.nextInt(tamañoTablero);
                int y = rand.nextInt(tamañoTablero);
                boolean horizontal = Math.random() < 0.5;

                Barco barco = barcosDisponibles.get(i);
                barco.setHorizontal(horizontal);
                sePudoColocar = tablero.colocarBarco(new Posicion(x, y), barco);
                if (sePudoColocar)
                    System.out.println("x: " + x + " | y: " + y);
                else
                    System.out.println("No se pudo colocar el barco en " + x + "," + y);
            } while (!sePudoColocar);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new PanelFondo("/juegobthp/imagenes/bn.png");
        TableroJugador = new javax.swing.JPanel();
        TableroMaquina = new javax.swing.JPanel();
        TituloJugador = new javax.swing.JLabel();
        TituloMaquina = new javax.swing.JLabel();
        Titulo = new JLabel("Batalla Naval", JLabel.CENTER);
        ReiniciarJuego = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1200, 750));

        //
        panelPrincipal.setBackground(new java.awt.Color(0, 153, 204));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        TableroJugador.setBackground(new java.awt.Color(153, 255, 153));
        TableroJugador.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 12, 13, 0);
        panelPrincipal.add(TableroJugador, gridBagConstraints);

        TableroMaquina.setBackground(new java.awt.Color(255, 153, 153));
        TableroMaquina.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 200;
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
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
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

    private void ReiniciarJuegoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ReiniciarJuegoActionPerformed
        inicializarTableros();
    }// GEN-LAST:event_ReiniciarJuegoActionPerformed

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
