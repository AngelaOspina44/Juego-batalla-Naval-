package juegobthp;

public class JugadorHumano extends Jugador {
    public JugadorHumano(Tablero tablero) {
        super(tablero);
    }

    @Override
    public boolean disparar(Posicion posicion, Tablero tableroEnemigo) {
        return tableroEnemigo.recibirDisparo(posicion);
    }
}
