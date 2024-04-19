package Menus;

import General.ControlEntradas;
import ImplementacionesDAO.*;
import LogicaNegocio.Cliente;

/**
 * este menu padre contiene las implementaciones que usarán ambos hijos
 * (MenuAdministrador y MenuCliente). Los atributos son protected para que sus
 * hijos puedan acceder sin necesidad de hacer getters aquí.
 */
public abstract class Menu {

    private static final String ADMIN_PASSWD = "1234";
    private static Cliente clienteAux = null;
    protected static ImplementacionSucursalDAO impSucursal = new ImplementacionSucursalDAO();
    protected static ImplementacionClienteDAO impCliente = new ImplementacionClienteDAO();
    protected static ImplementacionCuentaDAO impCuenta = new ImplementacionCuentaDAO();
    protected static ImplementacionInfoCuentaDAO impInfoCuenta = new ImplementacionInfoCuentaDAO();

    public static void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                          *** BANCO G2
                           1 --> Administrador
                           2 --> Cliente
                           0 --> SALIR""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    System.out.println("Introduce la contraseña:");
                    if (ControlEntradas.SC.nextLine().equals(ADMIN_PASSWD)) {
                        MenuAdministrador.mostrarMenu();
                    } else {
                        System.out.println("Contraseña incorrecta.");
                    }
                    break;
                case 2:
                    if (comprobarCliente()) {
                        MenuCliente.mostrarMenu(clienteAux);
                    }
                    break;
                default:
                    System.out.println("Opción incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    private static boolean comprobarCliente() {
        String dni = ControlEntradas.pedirDNI("Introduce el DNI (000 para salir): ");
        if (dni.equals("000")) {
            return false;
        }
        try {
            System.out.println("Introduce la contraseña: ");
            String passwd = ControlEntradas.SC.nextLine();
            if (impCliente.readCliente(dni).checkPassword(passwd)) {
                clienteAux = impCliente.readCliente(dni);
                return true;
            }
            System.out.println("Credenciales incorrectas.");
            return false;
        } catch (NullPointerException ex) {
            System.out.println("Credenciales incorrectas.");
            return false;
        }
    }
}
