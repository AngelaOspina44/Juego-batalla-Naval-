package juegobthp.barcos;

import java.util.ArrayList;
import java.util.List;

import juegobthp.Posicion;

// Clase abstracta que demuestra abstracción
public abstract class Barco {
    protected String nombre;
    protected int tamaño;
    protected List<Posicion> posiciones;
    protected List<Posicion> impactos;
    protected boolean horizontal;

    public Barco(String nombre, int tamaño) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.posiciones = new ArrayList<>();
        this.impactos = new ArrayList<>();
        this.horizontal = true;
    }

    // Método abstracto - polimorfismo
    public abstract String getTipo();

    // Encapsulamiento - getters y setters
    public String getNombre() {
        return nombre;
    }

    public int getTamaño() {
        return tamaño;
    }

    public List<Posicion> getPosiciones() {
        return posiciones;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void setPosiciones(List<Posicion> posiciones) {
        this.posiciones = posiciones;
    }

    public void agregarPosicion(Posicion posicion) {
        if (!posiciones.contains(posicion)) {
            if (posiciones.size() < tamaño) {
                posiciones.add(posicion);
            } else {
                System.out.println("No se pueden agregar más posiciones al barco.");
            }
        }
    }

    public boolean recibirImpacto(Posicion posicion) {
        if (posiciones.contains(posicion) && !impactos.contains(posicion)) {
            impactos.add(posicion);
            return true;
        }
        return false;
    }

    public boolean estaHundido() {
        return impactos.size() == tamaño;
    }

    public boolean estaEnPosicion(Posicion posicion) {
        return posiciones.contains(posicion);
    }

    public boolean estaImpactadoEn(Posicion posicion) {
        return impactos.contains(posicion);
    }
}
