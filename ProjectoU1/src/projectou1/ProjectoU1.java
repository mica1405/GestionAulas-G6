/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectou1;

import java.io.File;
import java.io.FileWriter;
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
     * Funcion que permite leer el fichero
     * @param fichero archivo classroom.txt donde se alnacenan los datos de las
     * aulas
     */
    private static void LeerClassroom(File fichero) {
        int numLineas = 0;
        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(fichero, "UTF-8");
            //bucle que lee linea a linea el fichero           
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

    /**
     * Esta funcion se encarga de crear un nuevo registro de un aula
     *
     * @param fichero archivo classroom.txt donde se almacenan los datos de las
     * aulas
     */
    private static void crearRegistro(File fichero) {
        Scanner lector = new Scanner(System.in);
        String registroNuevo;
        String[] cambiar = new String[3];
        System.out.println("Crear un nuevo registro");
        System.out.print("ID: ");
        String id = lector.next().trim();
        System.out.print("Nombre de aula: ");
        String nombreAula = lector.next().trim();
        lector.nextLine();
        System.out.print("Capacidad de alumnos: ");
        String cantidadAlumnos = lector.next().trim();
        System.out.print("Hay pc's(Si/No): ");
        cambiar[0] = lector.next().trim();
        System.out.print("Cantidad de pc's disponibles: ");
        String cantidadPc = lector.next().trim();
        System.out.print("Hay proyector(Si/No): ");
        cambiar[1] = lector.next().trim();
        System.out.print("El aula esta insonorizada(Si/No): ");
        cambiar[2] = lector.next().trim();

        //Poner las primeras letras en mayuscula
        for (int i = 0; i < cambiar.length; i++) {
            cambiar[i] = cambiar[i].substring(0, 1).toUpperCase() + cambiar[i].charAt(1);
        }
        id = id.substring(0, 1).toUpperCase() + id.substring(1);
        nombreAula = nombreAula.substring(0, 1).toUpperCase() + nombreAula.substring(1);
        //String donde se guarda la linea de datos en formato CSV
        registroNuevo = "\n" + id + "," + nombreAula + "," + cantidadAlumnos + "," + cambiar[0] + "," + cantidadPc + "," + cambiar[1] + "," + cambiar[2];

        try {
            //agregar una nueva linea al fichero(true para agregar una linea y no sobreescribirla)
            FileWriter writer = new FileWriter(fichero, true);
            writer.write(registroNuevo);
            writer.close();
            System.out.println("El registro ha sido creado con exito");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }
}