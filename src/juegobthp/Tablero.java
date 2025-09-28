package juegobthp;

import java.util.ArrayList;
import java.util.List;

import juegobthp.barcos.Barco;

public class Tablero {
    private int TAMAÑO;
    private boolean[][] barcos;
    private boolean[][] disparos;

    public Tablero(int tamaño) {
        this.TAMAÑO = tamaño;
        barcos = new boolean[tamaño][tamaño];
        disparos = new boolean[tamaño][tamaño];
    }

    public void limpiar() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                barcos[i][j] = false;
                disparos[i][j] = false;
            }
        }
    }

    public boolean colocarBarco(Posicion posicion, Barco barco) {
        List<Posicion> posicionesTemporales = calcularPosiciones(posicion, barco);
        if (!validarLimites(posicionesTemporales))
            return false;
        if (!validarColisiones(posicionesTemporales, barco))
            return false;

        for (Posicion posicionTemporal : posicionesTemporales) {
            barco.agregarPosicion(posicionTemporal);
            barcos[posicionTemporal.getFila()][posicionTemporal.getColumna()] = true;
        }
        return true;
    }

    private boolean estaEnTablero(Posicion posicion) {
        return posicion.getColumna() >= 0 && posicion.getFila() >= 0 && posicion.getColumna() < TAMAÑO
                && posicion.getFila() < TAMAÑO;
    }

    private boolean validarLimites(List<Posicion> posiciones) {
        for (Posicion posicion : posiciones) {
            if (!estaEnTablero(posicion))
                return false;
        }
        return true;
    }

    private boolean validarColisiones(List<Posicion> posiciones, Barco barco) {
        for (Posicion pos : posiciones) {
            if (barcos[pos.getFila()][pos.getColumna()])
                return false;
        }
        return true;
    }

    private List<Posicion> calcularPosiciones(Posicion posicion, Barco barco) {
        List<Posicion> posiciones = new ArrayList<>();

        for (int j = 0; j < barco.getTamaño(); j++) {
            int columna = posicion.getColumna();
            int fila = posicion.getFila();

            if (barco.isHorizontal())
                posiciones.add(new Posicion(fila, columna + j));
            else
                posiciones.add(new Posicion(fila + j, columna));
        }

        return posiciones;
    }

    public boolean hayBarco(Posicion posicion) {
        return barcos[posicion.getFila()][posicion.getColumna()];
    }

    public boolean yaDisparo(Posicion posicion) {
        return disparos[posicion.getFila()][posicion.getColumna()];
    }

    public boolean recibirDisparo(Posicion posicion) {
        if (estaEnTablero(posicion)) {
            disparos[posicion.getFila()][posicion.getColumna()] = true;
            return barcos[posicion.getFila()][posicion.getColumna()];
        }
        return false;
    }

    public boolean todosLosBarcosDañados() {
        for (int i = 0; i < TAMAÑO; i++) {
            for (int j = 0; j < TAMAÑO; j++) {
                if (barcos[i][j] && !disparos[i][j])
                    return false;
            }
        }
        return true;
    }
}
