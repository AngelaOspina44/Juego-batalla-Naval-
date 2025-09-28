package juegobthp;

public class Maquina extends Jugador {
    public Maquina(Tablero tablero) {
        super(tablero);
    }

    @Override
    public boolean disparar(Posicion posicion, Tablero tableroEnemigo) {
        boolean impacto = tableroEnemigo.recibirDisparo(posicion);
        return impacto;
    }
}
