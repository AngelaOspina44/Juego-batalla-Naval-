package juegobthp;

public class Tablero {
    private int tamaño;
    private boolean[][] barcos;
    private boolean[][] disparos;

    public Tablero(int tamaño) {
        this.tamaño = tamaño;
        barcos = new boolean[tamaño][tamaño];
        disparos = new boolean[tamaño][tamaño];
    }
    
    public void limpiar() {
        for (int i=0; i<tamaño; i++) {
            for (int j=0; j<tamaño; j++) {
                barcos[i][j] = false;
                disparos[i][j] = false;
            }
        }
    }

    public boolean colocarBarco(int x, int y, int tamañoBarco, boolean horizontal) {
        if (horizontal) {
            if (y + tamañoBarco -1 > tamaño) return false;
            for (int i = y; i < y + tamañoBarco; i++) {
                if (barcos[x][i]) return false;
            }
            for (int i = y; i < y + tamañoBarco; i++) {
                barcos[x][i] = true;
            }
        } else {
            if (x + tamañoBarco -1 > tamaño) return false;
            for (int i = x; i < x + tamañoBarco; i++) {
                if (barcos[i][y]) return false;
            }
            for (int i = x; i < x + tamañoBarco; i++) {
                barcos[i][y] = true;
            }
        }
        return true;
    }

    public boolean hayBarco(int x, int y) {
        return x >= 0 && y >= 0 && x < tamaño && y < tamaño && barcos[x][y];
    }

    public boolean yaDisparo(int x, int y) {
        return disparos[x][y];
    }

    public void recibirDisparo(int x, int y) {
        if (x >= 0 && y >= 0 && x < tamaño && y < tamaño) {
            disparos[x][y] = true;
        }
    }
}
