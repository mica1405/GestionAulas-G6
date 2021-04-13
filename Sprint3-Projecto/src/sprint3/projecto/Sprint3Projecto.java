/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint3.projecto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mati
 */
public class Sprint3Projecto {

    static Usuario[] usuario;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File fichero = new File("Registros/classroom.txt");
        /*boolean fin = false;
        while(fin != true){
            if(usuario != null)
        }*/
        usuarios();
        mostrarUsuario();
        //menuProfesor(fichero);
        ingresarUsuario(fichero);
    }
/**
 *Esta funcion se encargara de solicitar el nombre de usuario 
 * y contraseña, dependiendo del rol del usuario
 * se mostrará un menu u otro
 */
    private static void ingresarUsuario(File fichero) {
        Scanner lector = new Scanner(System.in);
        System.out.print("Ingrese el usuario: ");
        String usuario = lector.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contraseña = lector.next();
        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            Usuario[] usuarios = (Usuario[]) archivo.readObject();
            for (Usuario user : usuarios) {
                if (user != null) {
                    //Segun el tipo de usuario se le redigira a un menu u otro
                    //En caso de Administrador se redigira la funcion menuAdministrador
                    //En caso contrario a menuProfesor
                    if (usuario.equals(user.usuario) && contraseña.equals(user.contraseña)) {
                        if (user.rol.equals("Administrador")) {
                            menuAdministrador();
                            mostrarUsuario();
                        } else if (user.rol.equals("Profesor")) {
                            System.out.println("\n Bienvenido profesor " + user.nombre);
                            menuProfesor(fichero);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
            e.printStackTrace();
        }

    }
/**
 * Estos seran los usuario disponibles de fabrica
 */
    private static void usuarios() {
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            usuario = new Usuario[100];
            usuario[0] = new Usuario();
            usuario[0].rol = "Administrador";
            usuario[0].nombre = "Jose Andres";
            usuario[0].usuario = "joan00";
            usuario[0].contraseña = "joselito";
            System.out.println("-----------------");
            usuario[1] = new Usuario();
            usuario[1].rol = "Profesor";
            usuario[1].nombre = "Pedro Duran";
            usuario[1].usuario = "pedu";
            usuario[1].contraseña = "pedrito";
            fichero.writeObject(usuario);

            fichero.close();
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");

        }
    }

    private static void mostrarUsuario() {

        try {
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) fichero.readObject();
            for (Usuario user : usuario) {
                if (user != null) {
                    System.out.println("Rol: " + user.rol);
                    System.out.println("Nombre: " + user.nombre);
                    System.out.println("Usuario: " + user.usuario);
                    System.out.println("------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
            e.printStackTrace();
        }
    }

    private static void menuProfesor(File fichero) {
        //boolean servira para dar final al bucle del menu
        //int menu servira para seleccionar una opcion
        //Creamos un menu dentro del bucle do-while para que el usuario profesor decida
        //cuando dar fin a sus acciones
        Scanner lector = new Scanner(System.in);
        boolean salir = false;
        int menu;
        do {
            //Creamos un variable donde indicaremos el archivo a modificar
            System.out.println("############ GESTION DE AULAS ############## \n");
            System.out.println("1)Ver los registros");
            System.out.println("2)Crear un registro");
            System.out.println("3)Editar registro");
            System.out.println("4)Eliminar registro");
            System.out.println("5)Salir");
            System.out.print("Seleccione una opcion: ");
            menu = lector.nextInt();
            switch (menu) {
                case 1:
                    LeerClassroom(fichero);
                    break;
                case 2:
                    crearRegistro(fichero);
                    break;
                case 3:
                    EditarClassroom(fichero);
                    break;
                case 4:
                    eliminarRegistro(fichero);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Vuelva a escribir una opcion valida.");
            }
        } while (salir != true);
    }

    /**
     * Funcion que permite leer el fichero
     *
     * @param fichero archivo classroom.txt donde se alnacenan los datos de las
     * aulas
     */
    private static void LeerClassroom(File fichero) {
        //numLineas sera necesario para contar los registros
        int numLineas = 0;
        // Se intentara leer el archivo classroom.txt
        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(fichero, "UTF-8");
            //hasNext dentro del bucle permitira leer todas las filas de classroom
            //Se imprimira todo el contenido de classroom ordenado por tipo de dato
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
            //Se mostrara a partir del contador numLineas el numero de registros en classroom
            System.out.println("Hay " + numLineas + " registros de aulas");
            //En caso de error se imprimira el siguiente mensaje
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
        //registro guardara los nuevos registros creados dentro de la funcion crearNuevaLinea
        String registroNuevo = crearNuevalinea() + "\n";

        try {
            //agregar una nueva linea al fichero(true para agregar una linea y no sobreescribirla)
            //Se escribira en el fichero el registro nuevo
            FileWriter writer = new FileWriter(fichero, true);
            writer.write(registroNuevo);
            writer.close();
            System.out.println("El registro ha sido creado con exito");
            //En caso de error se imprimira el siguiente mensaje
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }

    /**
     * Esta funcion se encarga de editar un registro
     *
     * @param fichero archivo classroom.txt donde se almacenan los datos de las
     * aulas
     */
    private static void EditarClassroom(File fichero) {
        Scanner lector = new Scanner(System.in);
        String lineadeseada, lineanueva;
        // Se busca a partir del ID poder editar la fila donde este se encuentra
        // y que cualquier ID que escribas se convierta en mayusculas.
        System.out.print("Introduce la linea que quieres editar: ");
        lineadeseada = lector.next().trim();
        lineadeseada = lineadeseada.toUpperCase().substring(0, 1) + lineadeseada.substring(1);
        // Array para guardar todas las líneas leídas del fichero
        ArrayList<String> lineas = new ArrayList<>();
        // Abrimos el fichero de texto para leerlo en memoria
        try {
            //Se verificara la existencia del fichero classroom
            Scanner lectorFichero = new Scanner(fichero);
            while (lectorFichero.hasNext()) {
                lineas.add(lectorFichero.nextLine());
            }
            lectorFichero.close();
            //En caso de error se imprimira el siguiente mensaje
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }

        // Abrimos el fichero de texto para sobreescribirlo
        try {
            //Se comprovara que el ID coincida con el ID dentro del fichero
            FileWriter writer = new FileWriter(fichero);
            boolean prueba = false;
            for (String linea : lineas) {
                //En caso de que se encuentre el ID escrito 
                //Se ejecutara la funcion nuevaLinearegistro() con tal de
                //reemplazar los registros de la fila donde se encuentra el ID
                if (lineadeseada.equals(linea.substring(0, 3))) {
                    String registroNuevo = crearNuevalinea();
                    writer.write(registroNuevo + "\n");
                    prueba = true;

                } else {
                    writer.write(linea + "\n");
                }
            }
            //Dependiendo de que la modificacion haya tenido exito o no se 
            //mostrara un mensaje u otro mediante la variable prueba
            if (prueba == true) {
                System.out.println("Se ha modificado");
            } else {
                System.out.println("No se ha modificado. Error ID");
            }
            writer.close();
            //En caso de error se imprimira el siguiente mensaje
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/sobreescribir el fichero");
        }
    }

    /**
     * Esta funcion se encarga de eliminar un registro
     *
     * @param fichero archivo classroom.txt donde se almacenan los datos de las
     * aulas
     */
    private static void eliminarRegistro(File fichero) {
        Scanner lector = new Scanner(System.in);
        //Le pedimos al usuario que ID quiere eliminar
        //Creamos un variable en donde se guarda el ID que quiere eliminar el usuario 
        String ID;
        System.out.print("Introduce el ID la linea que quieres eliminar: ");
        ID = lector.next().trim().toUpperCase();//Elimina toda la linea del ID introducido

        // Array para guardar todos los registros leídos del fichero
        ArrayList<String> registros = new ArrayList<>();

        // Abrimos el fichero de texto para leerlo en memoria
        try {
            Scanner lectorFichero = new Scanner(fichero);
            while (lectorFichero.hasNext()) {
                registros.add(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }

        // Abrimos el fichero de texto para sobreescribirlo
        // Eliminaremos el registro que quiera el usuario
        // Despues cuando el usuario haya introducido el ID que quiere eliminar, la maquina nos dira si se ha eliminado bien o no 
        try {
            FileWriter writer = new FileWriter(fichero);
            boolean idEliminado = false;
            for (String registro : registros) {
                if (!ID.equals(registro.substring(0, 3))) {
                    writer.write(registro + "\n");
                } else {
                    idEliminado = true;
                }
            }
            if (idEliminado == true) {
                System.out.println("El registro se ha eliminado");
            } else {
                System.out.println("ERROR. El ID no existe/no se ha encontrado");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/sobreescribir el fichero");
        }
    }

    /**
     * Esta funcion se encarga de pedir al usuario parametros sobre sobre el
     * nuevo registro, en esta ocasion esta sera usada para reemplazar la vieja
     * informacion de un registro por la nueva
     *
     * @return devuelve la linea creada
     */
    private static String crearNuevalinea() {
        Scanner lector = new Scanner(System.in);
        String registroNuevo;
        String[] cambiar = new String[3];
        System.out.println("Nuevo registro");
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

        registroNuevo = id + "," + nombreAula + "," + cantidadAlumnos + "," + cambiar[0] + "," + cantidadPc + "," + cambiar[1] + "," + cambiar[2];
        return registroNuevo;
    }
    /**
     *Esta funcion es el menu que se le cederá al administrador
     * Que tendra como funciones
     * añadir, modificar, eliminar y lista usuarios.
     * @return devuelve la linea creada
     */
    private static void menuAdministrador() {
        Scanner lector = new Scanner(System.in);
        boolean salir = false;
        int menu;
        do {
            //Creamos un variable donde indicaremos el archivo a modificar
            
            System.out.println("############ GESTION DE USUARIOS ############## \n");
            System.out.println("1)Crear usuario");
            System.out.println("2)Salir");
            System.out.print("Seleccione una opcion: ");
            menu = lector.nextInt();
            switch (menu) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    salir = true;
                    break;
                default:
                    System.out.println("Vuelva a escribir una opcion valida.");
            }
        } while (salir != true);
    }
   /**
     *Esta funcion servira para crear el usuario
     * junto a su contraseña, usuario, nombre real y rol.
     * 
     * @return devuelve la linea creada
     */
    private static void crearUsuario() {
        Scanner lector = new Scanner(System.in);
        System.out.print("Rol del usuario: ");
        String rol = lector.nextLine();
        System.out.print("Nombre real: ");
        String nombre = lector.nextLine();
        System.out.print("Nombre de usuario: ");
        String nameuser = lector.nextLine();
        System.out.print("Contraseña: ");
        String password = lector.nextLine();
        boolean fin = false;
        int cont = 0;
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            while (fin != true) {
                cont++;              
                if (usuario[cont] == null) {
                usuario[cont] = new Usuario();
                usuario[cont].rol = rol;
                usuario[cont].nombre = nombre;
                usuario[cont].usuario = nameuser;
                usuario[cont].contraseña = password;
                    fin = true;
                }

            }
            fichero.close();
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
            e.printStackTrace();
        }
    }
}
