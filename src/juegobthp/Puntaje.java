/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegobthp;

/**
 *
 * @author alex
 */
public class Puntaje {
  private int victorias;
  private int derrotas;
  private String nombre;

  public Puntaje(String nombre) {
    this.nombre = nombre;
    this.victorias = 0;
    this.derrotas = 0;
  }

  public String getNombre() {
    return nombre;
  }

  public int getVictorias() {
    return victorias;
  }

  public int getDerrotas() {
    return derrotas;
  }

  public void setVictorias(int victorias) {
    this.victorias = victorias;
  }

  public void setDerrotas(int derrotas) {
    this.derrotas = derrotas;
  }

  public void sumarVictoria() {
    this.victorias++;
  }

  public void sumarDerrota() {
    this.derrotas++;
  }
}
