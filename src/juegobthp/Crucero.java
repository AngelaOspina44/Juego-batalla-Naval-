/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegobthp;

/**
 *
 * @author alex
 */
public class Crucero extends Barco {
    public Crucero() {
        super("Crucero", 1);
    }
    
    @Override
    public String getTipo() {
        return "Crucero";
    }
}
