/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectou1;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author JANDRES
 */
public class ProjectoU1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Creamos un variable donde indicaremos el archivo a modificar
        File fichero = new File("/GestionAulas-G6/classroom.txt");
        LeerClassroom(fichero);
    }

    /**
     * Esta funcion muestra los datos de las aulas
     *
     * @param fichero archivo classroom.txt donde se encuentan los datos
     */
    private static void LeerClassroom(File fichero) {
        int numLineas = 0;

        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(fichero, "UTF-8");

            while (lectorFichero.hasNext()) {
                numLineas++;
                String[] linea = lectorFichero.nextLine().split(",");
                System.out.println("Id: " + linea[0]);
                System.out.println("Nombre de aula: " + linea[1]);
                System.out.println("Cantidad de alumnos: " + linea[2]);
                System.out.println("Hay pc's: " + linea[3]);
                System.out.println("Cantidad de pc's disponibles: " + linea[4]);
                System.out.println("Hay proyector: " + linea[5]);
                System.out.println("Insonorizada: " + linea[6]);
                System.out.println("");
            }

            lectorFichero.close();
            System.out.println("Hay " + numLineas + " registros de aulas");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");

        }
    }
}
