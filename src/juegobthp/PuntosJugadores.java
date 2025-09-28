/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package juegobthp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class PuntosJugadores {
    private static final String CSV_NOMBRE_ARCHIVO = "puntos_jugadores.csv";
    private static final String CSV_HEADER = "nombre,victorias,derrotas";
    private static final String CSV_DELIMITADOR = ",";

    // --- NUEVO DISEÑO ---
    // Escribe la lista completa de puntajes al archivo CSV, sobrescribiendo el contenido existente.
    // Guarda la lista de puntajes en el archivo CSV (sobrescribe todo el archivo)
    private static void guardarPuntajesEnArchivo(List<Puntaje> puntajes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_NOMBRE_ARCHIVO))) {
            writer.println(CSV_HEADER);
            for (Puntaje p : puntajes) {
                writer.println(p.getNombre() + CSV_DELIMITADOR + p.getVictorias() + CSV_DELIMITADOR + p.getDerrotas());
            }
        } catch (IOException e) {
            System.err.println("No se pudo guardar el archivo de puntajes: " + e.getMessage());
        }
    }

    // Agrega un nuevo jugador con 0 victorias y derrotas si no existe. El archivo CSV se actualiza en cada acción.
    // Agrega un jugador solo si no existe
    public static void agregarJugador(String nombre) {
        List<Puntaje> puntajes = cargarPuntajes();
        String nombreLimpio = nombre.trim();
        for (Puntaje p : puntajes) {
            if (p.getNombre().equalsIgnoreCase(nombreLimpio)) {
                System.out.println("El jugador '" + nombreLimpio + "' ya existe.");
                return;
            }
        }
        puntajes.add(new Puntaje(nombreLimpio));
        guardarPuntajesEnArchivo(puntajes);
    }

    // Incrementa las victorias del jugador y actualiza el CSV.
    // Suma una victoria al jugador si existe
    public static void sumarVictoria(String nombre) {
        List<Puntaje> puntajes = cargarPuntajes();
        String nombreLimpio = nombre.trim();
        for (Puntaje p : puntajes) {
            if (p.getNombre().equalsIgnoreCase(nombreLimpio)) {
                p.sumarVictoria();
                guardarPuntajesEnArchivo(puntajes);
                return;
            }
        }
        System.out.println("No se encontró al jugador '" + nombreLimpio + "'.");
    }

    // Incrementa las derrotas del jugador y actualiza el CSV.
    // Suma una derrota al jugador si existe
    public static void sumarDerrota(String nombre) {
        List<Puntaje> puntajes = cargarPuntajes();
        String nombreLimpio = nombre.trim();
        for (Puntaje p : puntajes) {
            if (p.getNombre().equalsIgnoreCase(nombreLimpio)) {
                p.sumarDerrota();
                guardarPuntajesEnArchivo(puntajes);
                return;
            }
        }
        System.out.println("No se encontró al jugador '" + nombreLimpio + "'.");
    }

    public static List<Puntaje> cargarPuntajes() {
        List<Puntaje> puntajes = new ArrayList<Puntaje>();
        File puntajesArchivo = new File(CSV_NOMBRE_ARCHIVO);

        if (!puntajesArchivo.exists()) {
            return puntajes;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(puntajesArchivo));
            String linea = reader.readLine();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(CSV_DELIMITADOR);
                String nombre = datos[0];
                int victorias = Integer.parseInt(datos[1]);
                int derrotas = Integer.parseInt(datos[2]);

                Puntaje puntaje = new Puntaje(nombre);
                puntaje.setVictorias(victorias);
                puntaje.setDerrotas(derrotas);
                puntajes.add(puntaje);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return puntajes;
    }
}
