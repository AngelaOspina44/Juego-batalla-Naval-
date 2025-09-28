package juegobthp.iconos;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Icono {
  private String nombre;
  private String ruta;
  private ImageIcon icono;
  private int ANCHO = 40;
  private int ALTO = 40;

  public Icono(String nombre, String ruta) {
    this.nombre = nombre;
    this.ruta = ruta;
  }

  public Icono(String nombre, String ruta, int ancho, int alto) {
    this.nombre = nombre;
    this.ruta = ruta;
    this.ANCHO = ancho;
    this.ALTO = alto;
  }

  public String getNombre() {
    return nombre;
  }

  public String getRuta() {
    return ruta;
  }

  public ImageIcon cargarIcono() {
    try {
      icono = new ImageIcon(getClass().getResource(ruta));
      Image img = icono.getImage().getScaledInstance(ANCHO, ALTO, Image.SCALE_SMOOTH);
      icono.setImage(img);
      return icono;
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Error al cargar el icono: " + nombre);
      return null;
    }
  }

  public void dibujar(JButton boton) {
    boton.setIcon(cargarIcono());
    boton.setDisabledIcon(cargarIcono());
  }
}
