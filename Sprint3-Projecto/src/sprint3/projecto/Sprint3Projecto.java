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
        usuarios();
        int []posicion = menuInicio();
        if(posicion[1] == 0){
            System.out.println("Usuario o contraseña introducidos erroneos");
            posicion = menuInicio();
            ingresarUsuario(fichero, posicion[0]);
        }else{
            System.out.println("Usuario y contraseña correctos");
        ingresarUsuario(fichero, posicion[0]);
        }
    }

    /**
     * Esta funcion se encarga de comparar si el usuario es un admin o un
     * profesor y mostar el menu correspondiente
     *
     * @param fichero datos del documento classroom.txt
     * @param posicion posicion en la que se encuentra el usuario en el array
     */
    //--------------------------------------SPRINT 1---------------------------------------------------
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

    //--------------------------------------SPRINT 2---------------------------------------------------
    /**
     * Esta funcion muestra al usuario el menu para profesores
     *
     * @param fichero documento classroom.txt
     * @param pos posicion del usuario en el array
     */
    private static void menuProfesor(File fichero, int pos) {
        Scanner lector = new Scanner(System.in);
        boolean salir = false;
        int menu;
        do {
            System.out.println("Bienvenido " + usuario[pos].rol);
            System.out.println("############ GESTION DE AULAS ##############");
            System.out.println("1)Ver los registros");
            System.out.println("2)Crear un registro");
            System.out.println("3)Editar registro");
            System.out.println("4)Eliminar registro");
            System.out.println("5)Cerrar sesión");
            System.out.println("6)Salir");
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
                    menuInicio();
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Vuelva a escribir una opcion valida.");
            }
        } while (salir != true);
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

    //--------------------------------------SPRINT 3---------------------------------------------------
    private static void ingresarUsuario(File fichero, int posicion) {
        //variable para finalizar bucle
        boolean fin = false;
        try {
            while (fin != true) {
                //comparar el usuario con las dos opciones dependiando cual sea mostrara un menu distinto
                if (usuario[posicion].rol.equals("Administrador")) {
                    menuAdministrador(posicion);
                    fin = true;
                } else if (usuario[posicion].rol.equals("Profesor")) {
                    menuProfesor(fichero, posicion);
                    fin = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
        }
    }

    /**
     * Esta funcion se encarga de prefuntar al usuario su usuario y contraseña
     *
     * @return devolvera la posicion del usuario en el array
     */
    private static int[] menuInicio() {        
        Scanner lector = new Scanner(System.in);
        System.out.print("Ingrese el usuario: ");
        String usuarioIngresado = lector.nextLine().trim();
        System.out.print("Ingrese la contraseña: ");
        String contraseña = lector.next().trim();
        int []posicion = new int[2];
        //finalizar bucle
        boolean fin = false;
        try {                   
            //bucle para recorrer el array se detendra cuando ingrese en if o cuando se recorra todo el array
            for (int i = 0; fin != true || i < usuario.length; i++) {
                    if (usuarioIngresado.equals(usuario[i].usuario) && contraseña.equals(usuario[i].contraseña)) {
                        posicion[0] = i;
                        posicion[1] = 1;
                        fin = true;                                    
                }
            }        
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
        }
        return posicion;
    }

    /**
     * Esta funcion es el menu que se le cederá al administrador Que tendra como
     * funciones añadir, modificar, eliminar y lista usuarios.
     *
     * @return devuelve la linea creada
     */
    private static void menuAdministrador(int pos) {
        Scanner lector = new Scanner(System.in);
        boolean salir = false;
        int menu;
        do {
            System.out.println("Bienvenido " + usuario[pos].nombre);
            System.out.println("############ GESTION DE USUARIOS ##############");
            System.out.println("1)Crear usuario");
            System.out.println("2)Listar usuarios");
            System.out.println("3)Modificar usuario");
            System.out.println("4)Eliminar usuario");
            System.out.println("5)Cerrar sesión");
            System.out.println("6)Apagar");
            System.out.print("Seleccione una opcion: ");
            menu = lector.nextInt();
            switch (menu) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    mostrarUsuario();
                    break;
                case 3:
                    
                case 4:
                    
                    break;
                case 5:
                    menuInicio();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Vuelva a escribir una opcion valida.");
            }
        } while (salir != true);
    }

    /**
     * Esta funcion crea el unico usuario admin
     */
    private static void usuarios() {
        try {
            usuario = new Usuario[100];
            usuario[0] = new Usuario();
            usuario[0].rol = "Administrador";
            usuario[0].nombre = "Jose Andres";
            usuario[0].usuario = "joan00";
            usuario[0].contraseña = "1234";
            usuario[1] = new Usuario();
            usuario[1].rol = "Administrador";
            usuario[1].nombre = "Matias";
            usuario[1].usuario = "mat01";
            usuario[1].contraseña = "12345";
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
        }
    }

    /**
     * Esta funcion lista todos los usuarios
     */
    private static void mostrarUsuario() {
        try {
            System.out.println("------------------------------");
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
        }
    }

    /**
     * Esta funcion servira para crear el usuario junto a su contraseña,
     * usuario, nombre real y rol.
     *
     * @return devuelve la linea creada
     */
    private static void crearUsuario() {
        boolean comprobar = false;
        String rol = "null", nombre ="null", nameuser="null", password="null";
        while (comprobar != true) {
            Scanner lector = new Scanner(System.in);
            System.out.print("Rol del usuario(Administrador/Profesor): ");
            rol = lector.nextLine().toLowerCase();
            System.out.print("Nombre y Apellido: ");
            nombre = lector.nextLine().toLowerCase();
            System.out.print("Nombre de usuario: ");
            nameuser = lector.nextLine().trim();
            System.out.print("Contraseña: ");
            password = lector.nextLine().trim();
            //correccion de letras
            rol = rol.substring(0, 1).toUpperCase() + rol.substring(1);
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1,nombre.indexOf(" ")) + nombre.substring(nombre.indexOf(" "), nombre.indexOf(" ") + 2).toUpperCase() + nombre.substring(nombre.indexOf(" ") + 2);
            if (rol.equals("Administrador") || rol.equals("Profesor")) {
                comprobar = true;
            } else {
                System.out.println("El rol introduciso no es valido, vuelve a intentarlo");
            }
        }
        boolean fin = false;
        int cont = 0;
        try {
            while (fin != true) {
                cont++;
                if (usuario[cont] == null) {
                    usuario[cont] = new Usuario();
                    usuario[cont].rol = rol;
                    usuario[cont].nombre = nombre;
                    usuario[cont].usuario = nameuser;
                    usuario[cont].contraseña = password;
                    fin = true;                   
                    System.out.println("Usuario creado");
                }
            }           
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
        }
    }
}
