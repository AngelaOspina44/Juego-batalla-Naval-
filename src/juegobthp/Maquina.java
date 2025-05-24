package juegobthp;

public class Maquina extends Jugador {
    public Maquina(Tablero tablero) {
        super(tablero);
    }

    @Override
    public void disparar(int x, int y, Tablero tableroEnemigo) {
        tableroEnemigo.recibirDisparo(x, y);
    }
}
