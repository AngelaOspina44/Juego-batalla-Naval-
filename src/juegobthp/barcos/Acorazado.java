/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegobthp.barcos;

/**
 *
 * @author alex
 */
public class Acorazado extends Barco {
    public Acorazado() {
        super("Acorazado", 3);
    }

    @Override
    public String getTipo() {
        return "Acorazado";
    }
}
