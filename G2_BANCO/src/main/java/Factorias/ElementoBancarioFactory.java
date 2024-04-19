package Factorias;

import General.ControlEntradas;
import LogicaNegocio.Cliente;
import LogicaNegocio.Sucursal;
import General.TiposElementoBancario;
import ImplementacionesDAO.ImplementacionClienteDAO;
import ImplementacionesDAO.ImplementacionInfoCuentaDAO;
import ImplementacionesDAO.ImplementacionSucursalDAO;
import LogicaNegocio.Cuenta;
import LogicaNegocio.InfoCliente;
import LogicaNegocio.InfoCuenta;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;

public class ElementoBancarioFactory {

    private static final ImplementacionSucursalDAO IMP_SUCURSAL = new ImplementacionSucursalDAO();
    private static final ImplementacionClienteDAO IMP_CLIENTE = new ImplementacionClienteDAO();
    private static final ImplementacionInfoCuentaDAO IMP_INFO_CUENTA = new ImplementacionInfoCuentaDAO();

    public static void crearElementoBancario(TiposElementoBancario tipo) {
        switch (tipo) {
            case SUCURSAL:
                crearSucursal();
                return;
            case CLIENTE:
                crearClienteEInfoCliente();
                return;
            case CUENTA:
                crearCuentaEInfoCuenta();
                return;
        }
        throw new IllegalArgumentException("Tipo de elemento bancario no válido: " + tipo);
    }

    private static void crearCuentaEInfoCuenta() {
        boolean seguir = true;
        String dni = "";

        while (seguir) {
            try {
                dni = ControlEntradas.pedirDNI();
                if (dni.equals("000")) {
                    System.out.println("Creación de cuenta cancelada.");
                    return;
                }
                IMP_CLIENTE.readCliente(dni);
                IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(), IMP_CLIENTE.readCliente(dni), 1));
                System.out.println("Cuenta creada con éxito.");
                seguir = false;
            } catch (NullPointerException npex) {
                System.out.println("No existe el cliente con DNI " + dni + ". Inténtalo de nuevo.");
            }
        }
    }

    private static void crearSucursal() {
        System.out.println("Introduce el país de la sucursal (000 para salir): ");
        String pais = ControlEntradas.pedirCampoSINDigito(1, 25);
        if (pais.equals("000")) {
            System.out.println("Se ha cancelado la creación de la sucursal.");
            return;
        }
        System.out.println("Introduce la ciudad de la sucursal: ");
        String ciudad = ControlEntradas.pedirCampoSINDigito(1, 30);
        System.out.println("Introduce dirección de la sucursal: ");
        String direccion = ControlEntradas.pedirCampo(1, 50);
        Sucursal sucursal = new Sucursal(pais, ciudad, direccion);
        IMP_SUCURSAL.createSucursal(sucursal);
        System.out.println("Sucursal creada correctamente.");
    }

    private static void crearClienteEInfoCliente() {
        String dni = ControlEntradas.pedirDNI();
        if (dni.equals("000")) {
            System.out.println("Creación de cliente cancelada.");
            return;
        }
        try {
            IMP_CLIENTE.readCliente(dni);
            System.out.println("El cliente con DNI " + dni + " ya está creado. Se ha cancelado la operación.");
        } catch (NullPointerException npex) {
            System.out.println("Introduce el nombre del nuevo cliente: ");
            String nombre = ControlEntradas.pedirCampoSINDigito(1, 25);
            System.out.println("Introduce el primer apellido del nuevo cliente: ");
            String apellido1 = ControlEntradas.pedirCampoSINDigito(1, 25);
            System.out.println("Introduce el segundo apellido del nuevo cliente: ");
            String apellido2 = ControlEntradas.pedirCampoSINDigito(1, 25);
            System.out.println("Elige la sucursal del nuevo cliente: ");
            Sucursal sucursal = IMP_SUCURSAL.chooseSucursal();
            String fechaDeAlta = LocalDate.now().toString();
            System.out.println("Introduce la contraseña del nuevo cliente: ");
            String passwd = ControlEntradas.pedirCampo(4, 10);
            String hashedPassword = BCrypt.hashpw(passwd, BCrypt.gensalt());
            System.out.println("Introduce el teléfono del nuevo cliente: ");
            String telf = ControlEntradas.pedirCampo(9);
            System.out.println("Introduce la nacionalidad del nuevo cliente: ");
            String nacionalidad = ControlEntradas.SC.nextLine();
            System.out.println("Introduce el país del nuevo cliente: ");
            String pais = ControlEntradas.SC.nextLine();
            System.out.println("Introduce el código postal del nuevo cliente: ");
            String codPos = ControlEntradas.pedirCampo(5);
            InfoCliente ic = new InfoCliente(nacionalidad, pais, codPos);
            Cliente c = new Cliente(dni, nombre, apellido1, apellido2, sucursal, fechaDeAlta, hashedPassword, telf, ic);
           
            IMP_CLIENTE.createCliente(c); //creamos el cliente
            //IMP_CLIENTE.readCliente(dni);
            //IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(),IMP_CLIENTE.readCliente(dni),1));
            
            System.out.println("Cliente creado con éxito.");
        }
    }
}
