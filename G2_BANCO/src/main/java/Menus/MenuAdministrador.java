package Menus;

import static Factorias.ElementoBancarioFactory.crearElementoBancario;
import General.ControlEntradas;
import General.TiposElementoBancario;
import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import LogicaNegocio.InfoCuenta;
import LogicaNegocio.Sucursal;
import java.util.ArrayList;

public class MenuAdministrador extends Menu {

    public static void mostrarMenu() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> ADMINISTRADOR
                               1 --> Gestión de sucursales
                               2 --> Gestión de clientes 
                               3 --> Gestión de cuentas
                               4 --> Consulta del historial
                                     de tranferencias
                               0 --> ATRÁS""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    gestionarSucursales();
                    break;
                case 2:
                    gestionarClientes();
                    break;
                case 3:
                    gestionarCuentas();
                    break;
                case 4:
                    consultarTransferencias();
                    break;
                default:
                    System.out.println("Opción Incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    private static void gestionarSucursales() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> ADMINISTRADOR --> GESTIÓN DE SUCURSALES
                               1 --> Crear sucursal
                               2 --> Modificar sucursal
                               3 --> Eliminar sucursal
                               4 --> Listar sucursales
                               0 --> ATRÁS""");
            switch (ControlEntradas.pedirInt()) {
                case 1:
                    crearElementoBancario(TiposElementoBancario.SUCURSAL);
                    break;
                case 2:
                    System.out.println("Selecciona la sucursal que deseas modificar:");
                    impSucursal.updateSucursal(impSucursal.chooseSucursal());
                    break;
                case 3:
                    System.out.println("Selecciona la sucursal que deseas borrar:");
                    impSucursal.deleteSucursal(impSucursal.chooseSucursal());
                    break;
                case 4:
                    ArrayList<Sucursal> sucursales = impSucursal.readSucursales();
                    System.out.println("EXISTEN " + sucursales.size() + " SUCURSALES:");
                    for (Sucursal sucursal : sucursales) {
                        System.out.println("\t" + sucursal.toString());
                    }
                    break;
                case 0:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    private static void gestionarClientes() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> ADMINISTRADOR --> GESTIÓN DE CLIENTES
                               1 --> Crear nuevo cliente
                               2 --> Modificación de datos
                               3 --> Eliminar cliente
                               4 --> Listar clientes
                               0 --> ATRÁS""");
            switch (ControlEntradas.pedirInt()) {
                case 1:
                    crearElementoBancario(TiposElementoBancario.CLIENTE);
                    break;
                case 2:
                    System.out.println("Modificación de datos.");
                    String dniModificar = ControlEntradas.pedirDNI();
                    if (dniModificar.equals("000")) {
                        System.out.println("Se ha cancelado la modificación de datos.");
                    } else {
                        try {
                            impCliente.updateCliente(impCliente.readCliente(dniModificar));
                        } catch (NullPointerException ex) {
                            System.out.println("No existe el cliente con DNI " + dniModificar + ".");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Eliminar cliente. (000 para cancelar eliminación)");
                    String dniBorrar = ControlEntradas.pedirDNI();
                    if (dniBorrar.equals("000")) {
                        System.out.println("Se ha cancelado la eliminación del cliente.");
                    } else {
                            //impCliente.updateCliente(impCliente.readCliente(dniBorrar));
                            impCliente.deleteCliente(dniBorrar);
                    }
                    break;
                case 4:
                    gestionarListadoClientes();
                    break;
                case 0:
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción Incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    private static void gestionarListadoClientes() {
        ArrayList<Cliente> clientes;
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> ADMINISTRADOR --> GESTIÓN DE CLIENTES --> LISTAR CLIENTES
                               1 --> Listar los clientes del banco
                               2 --> Listar los clientes de una sucursal
                               0 --> ATRÁS""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    clientes = impCliente.readClientes();
                    System.out.println("G2 TIENE " + clientes.size() + " CLIENTES:");
                    for (Cliente cliente : clientes) {
                        System.out.println("\t" + cliente.toString());
                    }
                    break;
                case 2:
                    clientes = impCliente.readClientes(impSucursal.chooseSucursal());
                    System.out.println("HAY " + clientes.size() + " CLIENTES:");
                    for (Cliente cliente : clientes) {
                        System.out.println("\t" + cliente.toString());
                    }
                    break;
                default:
                    System.out.println("Opción incorrecta. Vuelve a intentarlo:");
                    break;
            }
        }
    }

    private static void gestionarCuentas() {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> ADMINISTRADOR --> GESTIÓN DE CUENTAS
                               1 --> Crear cuenta
                               2 --> Eliminar cuenta
                               3 --> Listar cuentas
                               4 --> Añadir cliente a cuenta
                               0 --> ATRÁS""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    seguir = false;
                    break;
                case 1:
                    crearElementoBancario(TiposElementoBancario.CUENTA);
                    break;
                case 2:
                    System.out.println("Datos del titular.");
                    String dniEliminar = ControlEntradas.pedirDNI();
                    if (dniEliminar.equals("000")) {
                        System.out.println("Se ha cancelado el borrado de la cuenta.");
                    } else {
                        try {
                            Cuenta cuentaParaBorrar = impCuenta.chooseCuenta(impCliente.readCliente(dniEliminar), 1);
                            impInfoCuenta.deleteInfoCuenta(cuentaParaBorrar);
                            System.out.println("Se ha borrado la cuenta correctamente.");
                        } catch (NullPointerException npex) {
                            System.out.println("El cliente con DNI " + dniEliminar + " no es titular de ninguna cuenta.");
                        }
                    }
                    break;
                case 3:
                    String dniListar = ControlEntradas.pedirDNI();
                    if (dniListar.equals("000")) {
                        System.out.println("Se ha cancelado el listado de cuentas.");
                    } else {
                        try {
                            ArrayList<InfoCuenta> infoCuentas = impInfoCuenta.readInfoCuentas(impCliente.readCliente(dniListar));
                            if (infoCuentas.size() > 0) {
                                for (InfoCuenta infoCuenta : infoCuentas) {
                                    int numCuenta = infoCuenta.getNumeroCuenta().getNumCuenta();
                                    double saldo = impCuenta.readCuenta(infoCuenta.getNumeroCuenta().getNumCuenta()).getSaldo();
                                    System.out.println("\tNúmero de cuenta: " + numCuenta + " | Saldo = " + saldo);
                                }
                            } else {
                                System.out.println("El cliente con DNI " + dniListar + " aún no es propietario de ninguna cuenta.");
                            }
                        } catch (NullPointerException npex) {
                            System.out.println("El cliente con DNI " + dniListar + " no existe.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Datos del interesado.");
                    String dniParaAgregar = ControlEntradas.pedirDNI();
                    if (dniParaAgregar.equals("000")) {
                        System.out.println("Se ha cancelado la operación.");
                        break;
                    }
                    System.out.println("Datos del titular de la cuenta.");
                    String dniTitular = ControlEntradas.pedirDNI();
                    if (dniTitular.equals("000")) {
                        System.out.println("Se ha cancelado la operación.");
                        break;
                    }
                    if (dniTitular.equalsIgnoreCase(dniParaAgregar)) {
                        System.out.println("El DNI del titular es el mismo que el del interesado. Se ha cancelado la operación.");
                        break;
                    }
                    try {
                        Cuenta cuenta = impCuenta.chooseCuenta(impCliente.readCliente(dniTitular), 1);
                        InfoCuenta ic = new InfoCuenta(cuenta, impCliente.readCliente(dniParaAgregar), 0);
                        impInfoCuenta.createInfoCuenta(ic);
                        System.out.println("Operación realizada con éxito.");
                    } catch (NullPointerException npex) {
                        System.out.println("El cliente con DNI " + dniTitular + " no es titular de ninguna cuenta. Se ha cancelado la operación.");
                    }
                    break;
                default:
                    System.out.println("Opción incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    public static void consultarTransferencias() {
        XML.XMLManager.leerTransferencias();
    }
}
