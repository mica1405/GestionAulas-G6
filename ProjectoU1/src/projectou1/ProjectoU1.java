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
     * @param fichero archivo classroom.txt donde se encuentan los datos
     */
    private static void LeerClassroom(File fichero) {
        //Se intentara hacer la siguiente acción
        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(fichero, "UTF-8");
            // Se ejecutara un bucle con la finalidad de que se 
            while(lectorFichero.hasNext()) {
                System.out.println(lectorFichero.nextLine());
            }
            
            lectorFichero.close();
        } catch (Exception e) {
            //En caso de que no se encuentre el archivo pues se imprimo este mensaje de error
            System.out.println("El archivo classrooms no existe o no se encuentra");
        }
    }
    
}
