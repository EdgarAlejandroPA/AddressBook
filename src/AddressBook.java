import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private HashMap<String, String> contactos;
    private String filePath;

    public AddressBook(String filePath) {
        this.contactos = new HashMap<>();
        this.filePath = filePath;
    }

    // Cargar contactos desde el archivo CSV
    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    contactos.put(data[0], data[1]);
                }
            }
            System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    // Guardar contactos en el archivo CSV
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
            System.out.println("Contactos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Listar los contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contactos.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Crear un nuevo contacto
    public void create(String numero, String nombre) {
        contactos.put(numero, nombre);
        System.out.println("Contacto agregado.");
    }

    // Eliminar un contacto
    public void delete(String numero) {
        if (contactos.remove(numero) != null) {
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    // Menú interactivo
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\nMenú de la agenda telefónica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar y salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    list();
                    break;
                case "2":
                    System.out.print("Introduce el número: ");
                    String numero = scanner.nextLine();
                    System.out.print("Introduce el nombre: ");
                    String nombre = scanner.nextLine();
                    create(numero, nombre);
                    break;
                case "3":
                    System.out.print("Introduce el número a eliminar: ");
                    String numeroEliminar = scanner.nextLine();
                    delete(numeroEliminar);
                    break;
                case "4":
                    save();
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!opcion.equals("4"));
    }

    public static void main(String[] args) {
        AddressBook agenda = new AddressBook("contactos.csv");
        agenda.load();
        agenda.menu();
    }
}
