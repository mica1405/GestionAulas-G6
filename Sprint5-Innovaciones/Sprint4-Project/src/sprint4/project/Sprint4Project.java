/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprint4.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author mati
 */
public class Sprint4Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File fichero = new File("Registros/classroom.txt");
        //File fichero = crearFichero();
        boolean encontrarFichero = fichero.exists();
        if (encontrarFichero == false) {
            fichero = crearFichero();
        }
        boolean fin = false;
        Usuario[] usuario = null;
        boolean comprobar = false;
        File ficheroUsuario = new File("usuarios.dat");
        boolean comprobarUsuario = ficheroUsuario.exists();
        if (comprobarUsuario == false) {
            usuarios();
        }
        int posicion = menuInicio(fichero);
    }

    //--------------------------------------SPRINT 1---------------------------------------------------
    /**
     * Funcion que permite leer el fichero
     *
     * @param fichero archivo classroom.txt donde se alnacenan los datos de las
     * aulas
     */
    private static void leerClassroom(File fichero) {
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
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
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
        Usuario[] usuario = null;
        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) archivo.readObject();
            archivo.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        do {
            String menu[] = {"Ver los registros","Crear un registro","Editar registro",
               "Eliminar registro","Cerrar sesión","Apagar"};
           int opciones = JOptionPane.showOptionDialog(null
                                                        ,"Seleccione una opcion: "
                                                        ,"Gestion de aulas"
                                                        ,JOptionPane.NO_OPTION
                                                        ,JOptionPane.PLAIN_MESSAGE
                                                        ,null
                                                        ,menu
                                                        ,"default");
		switch (opciones) {
		case 0:
			leerClassroom(fichero);
			break;
		case 1:
			crearRegistro(fichero);
			break;
		case 2:
			editarClassroom(fichero);
			break;
		case 3:
			eliminarRegistro(fichero);
			break;
                case 4:
			menuInicio(fichero);
                        System.out.println("");
                        salir = true;
			break;
                case 5:
                        salir = true;
                        break;
                    
		}
        } while (salir != true);
    }

    private static File crearFichero() {
        File fichero = new File("Registros/classroom.txt");

        try {
            FileWriter writer = new FileWriter(fichero);

            writer.write("M08,Aula11,25,Si,25,Si,No\n");
            writer.write("M09,Aula10,30,Si,32,Si,Si\n");
            writer.write("M12,Aula26,25,Si,28,Si,No\n");
            writer.write("M04,Aula30,25,Si,27,Si,No\n");
            writer.write("FOL,Aula25,25,No,Null,Si,Si\n");
            writer.write("EIE,Aula25,25,No,Null,Si,No\n");

            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear/escribir en el fichero", "Error de escritura", JOptionPane.WARNING_MESSAGE);
        }
        return fichero;
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
            JOptionPane.showMessageDialog(null, "El registro ha sido creado con exito");
            //En caso de error se imprimira el siguiente mensaje
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear/escribir en el fichero", "Error de escritura", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Esta funcion se encarga de editar un registro
     *
     * @param fichero archivo classroom.txt donde se almacenan los datos de las
     * aulas
     */
    private static void editarClassroom(File fichero) {
        Scanner lector = new Scanner(System.in);
        String lineadeseada, lineanueva;
        // Se busca a partir del ID poder editar la fila donde este se encuentra
        // y que cualquier ID que escribas se convierta en mayusculas.
        lineadeseada = JOptionPane.showInputDialog("Introduce el ID del aula que quieres modificar:").trim().toUpperCase();
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
          JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "Se ha modificado");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha modificado", "Error de ID", JOptionPane.WARNING_MESSAGE);
            }
            writer.close();
            //En caso de error se imprimira el siguiente mensaje
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir/sobreescribir en el fichero", "Error de escritura", JOptionPane.WARNING_MESSAGE);
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
        ID = JOptionPane.showInputDialog("Introduce el ID del aula que quieres eliminar: ").trim().toUpperCase();
        //Elimina toda la linea del ID introducido
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
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "El registro se ha eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "El ID no existe/no se ha encontrado", "Error de ID", JOptionPane.WARNING_MESSAGE);
            }
            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir/sobreescribir en el fichero", "Error de escritura", JOptionPane.WARNING_MESSAGE);
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
        String cantidadPc;
        String[] cambiar = new String[3];
        String id = JOptionPane.showInputDialog(null, "Introduce un nuevo ID (Max 3 caracteres): ").trim().toUpperCase();
        String nombreAula = JOptionPane.showInputDialog(null, "Nombre de aula: ").trim();
        //lector.nextLine();
        String cantidadAlumnos = JOptionPane.showInputDialog(null, "Capacidad de alumnos: ").trim();
        cambiar[0] = JOptionPane.showInputDialog(null, "Hay pc's(Si/No): ").trim().toLowerCase();
        if (cambiar[0].equals("no")) {
            cantidadPc = "Null";
        } else {
            cantidadPc = JOptionPane.showInputDialog(null, "Cantidad de pc's disponibles: ").trim();
        }
        cambiar[1] = JOptionPane.showInputDialog(null, "Hay proyector(Si/No): ").trim().toLowerCase();
        cambiar[2] = JOptionPane.showInputDialog(null, "El aula esta insonorizada(Si/No): ").trim().toLowerCase();
        //Poner las primeras letras en mayuscula
        for (int i = 0; i < cambiar.length; i++) {
            cambiar[i] = cambiar[i].substring(0, 1).toUpperCase() + cambiar[i].charAt(1);
        }
        nombreAula = nombreAula.substring(0, 1).toUpperCase() + nombreAula.substring(1);
        //String donde se guarda la linea de datos en formato CSV

        registroNuevo = id + "," + nombreAula + "," + cantidadAlumnos + "," + cambiar[0] + "," + cantidadPc + "," + cambiar[1] + "," + cambiar[2];
        return registroNuevo;
    }

    //--------------------------------------SPRINT 3---------------------------------------------------
    /**
     * Esta funcion se encarga de comparar si el usuario es un admin o un
     * profesor y mostar el menu correspondiente
     *
     * @param fichero datos del documento classroom.txt
     * @param posicion posicion en la que se encuentra el usuario en el array
     */
    private static void ingresarUsuario(File fichero, int posicion) {
        Usuario[] comprobante = null;
        //variable para finalizar bucle
        boolean fin = false;
        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            comprobante = (Usuario[]) archivo.readObject();
            archivo.close();
        } catch (Exception e) {
            System.out.println("Error al abrir o leer el fichero");
        }
        //comparar el usuario con las dos opciones dependiando cual sea mostrara un menu distinto
        if (comprobante[posicion].rol.equals("Administrador")) {
            menuAdministrador(posicion, fichero);
        } else if (comprobante[posicion].rol.equals("Profesor")) {
            menuProfesor(fichero, posicion);
        }
    }

    /**
     * Esta funcion se encarga de prefuntar al usuario su usuario y contraseña
     *
     * @return devolvera la posicion del usuario en el array
     */
    private static int menuInicio(File fichero) {
        Scanner lector = new Scanner(System.in);
        String usuarioIngresado = JOptionPane.showInputDialog("Ingrese el usuario: ").trim();
        String contraseña = JOptionPane.showInputDialog("Ingrese la contraseña: ").trim();    
        Usuario[] comprobante = null;
        int posicion = 0;
        boolean comprobar = false;
        //finalizar bucle
        boolean fin = false;
        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            comprobante = (Usuario[]) archivo.readObject();
            // Cerramos el fichero
            archivo.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        //bucle para recorrer el array se detendra cuando ingrese en if o cuando se recorra todo el array 

        for (int i = 0; fin != true && i < comprobante.length; i++) {
            if (comprobante[i] != null && comprobante[i].usuario != null) {
                if (usuarioIngresado.equals(comprobante[i].usuario) && contraseña.equals(comprobante[i].contraseña)) {
                    posicion = i;
                    comprobar = true;
                    fin = true;
                }
            }
        }
        if (comprobar == true) {
            JOptionPane.showMessageDialog(null, "Usuario y contraseña correctos");
            ingresarUsuario(fichero, posicion);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña introducidos erroneos", "Error de inicio sesión", JOptionPane.WARNING_MESSAGE);
            menuInicio(fichero);
        }
        return posicion;
    }

    /**
     * Esta funcion es el menu que se le cederá al administrador Que tendra como
     * funciones añadir, modificar, eliminar y lista usuarios.
     *
     * @return devuelve la linea creada
     */
    private static void menuAdministrador(int pos, File fichero) {
        Scanner lector = new Scanner(System.in);
        boolean salir = false;
        
        Usuario[] usuario = null;
        try {
            ObjectInputStream archivo = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) archivo.readObject();
            archivo.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        do {
            
           String menu[] = {"Crear usuario","Enumerar usuarios","Modificar usuario",
               "Eliminar usuario","Cerrar sesión","Apagar"};
           int opciones = JOptionPane.showOptionDialog(null
                                                        ,"Seleccione una opcion: "
                                                        ,"Gestion de usuarios"
                                                        ,JOptionPane.NO_OPTION
                                                        ,JOptionPane.PLAIN_MESSAGE
                                                        ,null
                                                        ,menu
                                                        ,"default");
		switch (opciones) {
		case 0:
			crearUsuario();
			break;
		case 1:
			mostrarUsuario();
			break;
		case 2:
			editarUsuario();
			break;
		case 3:
			eliminarUsuario();
			break;
                case 4:
			menuInicio(fichero);
                        System.out.println("");
                        salir = true;
			break;
                case 5:
                        salir = true;
                        break;
                    
		}
        } while (salir != true);
    }

    /**
     * Esta funcion crea el unico usuario admin
     */
    private static void usuarios() {
        Usuario[] usuario = new Usuario[100];
        usuario[0] = new Usuario();
        usuario[0].rol = "Administrador";
        usuario[0].nombre = "Jose Andres";
        usuario[0].usuario = "joan00";
        usuario[0].contraseña = "1234";
        usuario[1] = new Usuario();
        usuario[1].rol = "Profesor";
        usuario[1].nombre = "Matias Martinez";
        usuario[1].usuario = "mat01";
        usuario[1].contraseña = "12345";
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            // Con writeObject escribimos directamente todo el array de Usuario
            fichero.writeObject(usuario);
            // Cerramos el fichero
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Esta funcion lista todos los usuarios
     */
    private static void mostrarUsuario() {
        Usuario[] usuario = null;
        try {
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) fichero.readObject();
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        int contuser = 0;

        System.out.println("------------------------------------------------------------");
        for (Usuario user : usuario) {
            if (user != null && !user.rol.equalsIgnoreCase(" ")) {
                contuser++;
                System.out.print(contuser + ")Rol: " + user.rol + " | ");
                System.out.print("Nombre: " + user.nombre + " | ");
                System.out.println("Usuario: " + user.usuario);
                System.out.println("------------------------------------------------------------");
            }
        }
        System.out.println("El numero total de usuarios guardados es de " + contuser + ".\n");
    }

    /**
     * Esta funcion servira para crear el usuario junto a su contraseña,
     * usuario, nombre real y rol.
     *
     * @return devuelve la linea creada
     */
    private static void crearUsuario() {
        String[] nuevoUsuario = nuevoUsuario();
        boolean fin = false;
        int cont = 0;
        Usuario[] usuario = null;
        try {
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) fichero.readObject();
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        for (int i = 0; fin != true || usuario[cont] == null; i++) {
            cont++;
            if (usuario[cont] == null || usuario[cont].equals(" ")) {
                usuario[cont] = new Usuario();
                usuario[cont].rol = nuevoUsuario[0];
                usuario[cont].nombre = nuevoUsuario[1];
                usuario[cont].usuario = nuevoUsuario[2];
                usuario[cont].contraseña = nuevoUsuario[3];
                fin = true;
                JOptionPane.showMessageDialog(null, "Usuario y contraseña correctos");
            }
        }
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            // Con writeObject escribimos directamente todo el array de Usuario
            fichero.writeObject(usuario);
            // Cerramos el fichero
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Esta funcion pide al usuario los datos para un nuevo usuario y los guarda
     * en una array
     *
     * @return devuelve el array con los datos cargados
     */
    private static String[] nuevoUsuario() {
        boolean comprobar = false;
        String nuevoUsuario[] = new String[4];
        while (comprobar != true) {
            Scanner lector = new Scanner(System.in);
            nuevoUsuario[0] = JOptionPane.showInputDialog(null, "Rol del usuario(Administrador/Profesor):").toLowerCase();
            nuevoUsuario[1] = JOptionPane.showInputDialog(null, "Nombre y Apellido:").toLowerCase();
            nuevoUsuario[2] = JOptionPane.showInputDialog(null, "Nombre de  usuario:").trim();
            nuevoUsuario[3] = JOptionPane.showInputDialog(null, "Contraseña:").trim();
            //correccion de letras
            nuevoUsuario[0] = nuevoUsuario[0].substring(0, 1).toUpperCase() + nuevoUsuario[0].substring(1);
            nuevoUsuario[1] = nuevoUsuario[1].substring(0, 1).toUpperCase() + nuevoUsuario[1].substring(1, nuevoUsuario[1].indexOf(" ")) + nuevoUsuario[1].substring(nuevoUsuario[1].indexOf(" "), nuevoUsuario[1].indexOf(" ") + 2).toUpperCase() + nuevoUsuario[1].substring(nuevoUsuario[1].indexOf(" ") + 2);
            if (nuevoUsuario[0].equals("Administrador") || nuevoUsuario[0].equals("Profesor")) {
                comprobar = true;
            } else {
                JOptionPane.showMessageDialog(null, "El rol introduciso no es valido, vuelve a intentarlo", "Error de rol", JOptionPane.WARNING_MESSAGE);
            }
        }
        return nuevoUsuario;
    }

    //--------------------------------------SPRINT 4---------------------------------------------------
    /**
     * Esta funcion se encarga de solicitar el nombre del usuario que desea
     * editar, luego lo edita con los nuevos parametros
     */
    private static void editarUsuario() {
        Scanner lector = new Scanner(System.in);
        Usuario[] usuario = null;
        boolean fin = false;
        try {
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            usuario = (Usuario[]) fichero.readObject();
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        String editar = JOptionPane.showInputDialog(null, "Nombre del Usuario que desea editar:");
        editar = editar.substring(0, 1).toUpperCase() + editar.substring(1, editar.indexOf(" ")) + editar.substring(editar.indexOf(" "), editar.indexOf(" ") + 2).toUpperCase() + editar.substring(editar.indexOf(" ") + 2);
        String[] nuevoUsuario = nuevoUsuario();
        for (Usuario user : usuario) {
            if (user != null && user.nombre.equals(editar)) {
                user.rol = nuevoUsuario[0];
                user.nombre = nuevoUsuario[1];
                user.usuario = nuevoUsuario[2];
                user.contraseña = nuevoUsuario[3];
                JOptionPane.showMessageDialog(null, "Usuario editado");
                fin = true;
            }
        }
        try {
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            // Con writeObject escribimos directamente todo el array de Usuario
            fichero.writeObject(usuario);
            // Cerramos el fichero
            fichero.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Esta funcion se encarga de eliminar un usuario
     */
    public static void eliminarUsuario() {
        Scanner lector = new Scanner(System.in);
        Usuario[] usuario = null;

        try {
            //Aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream("usuarios.dat"));

            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            usuario = (Usuario[]) fichero.readObject();

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir o leer el fichero", "Error de lectura", JOptionPane.WARNING_MESSAGE);
        }
        boolean fin = false;
        String nombreBorrar = "";
        // BORRAR DATOS
        // Buscaremos por la clave primaria o varios campos, en este caso por Nombre y borraremos el registro
        while (fin != true) {
            nombreBorrar = JOptionPane.showInputDialog(null, "Introduce el nombre del usuario a eliminar: ");
            nombreBorrar = nombreBorrar.substring(0, 1).toUpperCase() + nombreBorrar.substring(1, nombreBorrar.indexOf(" ")) + nombreBorrar.substring(nombreBorrar.indexOf(" "), 
                    nombreBorrar.indexOf(" ") + 2).toUpperCase() + nombreBorrar.substring(nombreBorrar.indexOf(" ") + 2);
            for (Usuario user : usuario) {
                if (user != null && user.nombre.equals(nombreBorrar)) {
                    user.rol = " ";
                    user.nombre = " ";
                    user.usuario = " ";
                    user.contraseña = " ";
                    user = null;
                    fin = true;
                }
            }
            if (fin == false) {
                JOptionPane.showMessageDialog(null, "Nombre incorrecto", "Error de nombre", JOptionPane.WARNING_MESSAGE);
            }
        }
        // AMPLIACIÓN: Comprobar si se ha encontrado o no ese usuario a borrar e informar al usuario
        boolean comprobar = true;
        for (Usuario user : usuario) {
            if (user != null && user.nombre.equals(nombreBorrar)) {
                comprobar = false;
            }
        }
        if (comprobar == false) {
            JOptionPane.showMessageDialog(null, "ERROR. No se ha borrado correctamente el Usuario", "Error de borrado", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente el usuario");
        }

        // GUARDAR FICHERO
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));

            // Con writeObject escribimos directamente todo el array de Empleados
            fichero.writeObject(usuario);

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el fichero", "Error de guardado", JOptionPane.WARNING_MESSAGE);
        }
    }
}
