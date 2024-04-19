package General;

import ImplementacionesDAO.*;
import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import LogicaNegocio.InfoCliente;
import LogicaNegocio.InfoCuenta;
import LogicaNegocio.Sucursal;
import java.time.LocalDate;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/**
 * creamos unos datos de prueba para que haya siempre algo en la base de datos
 */
public abstract class DatosDePrueba {

    private static boolean fabricado = false;
    private static final ImplementacionSucursalDAO IMP_SUCURSAL = new ImplementacionSucursalDAO();
    private static final ImplementacionClienteDAO IMP_CLIENTE = new ImplementacionClienteDAO();
    private static final ImplementacionCuentaDAO IMP_CUENTA = new ImplementacionCuentaDAO();
    private static final ImplementacionInfoCuentaDAO IMP_INFO_CUENTA = new ImplementacionInfoCuentaDAO();

    public static void fabricar() {
        if (!fabricado) {
            Sucursal sucursal1 = new Sucursal("Espana", "Majadahonda", "Calle Zayas, 10");
            Sucursal sucursal2 = new Sucursal("Francia", "Niza", "Calle Algo 123");

            InfoCliente ic1 = new InfoCliente("Espaniola", "Espania", "28220");
            Cliente c1 = new Cliente("12341234V", "Ivan", "Lim", "Blanco", sucursal1, LocalDate.now().toString(), BCrypt.hashpw("1234", BCrypt.gensalt()), "674132588", ic1);

            InfoCliente ic2 = new InfoCliente("Rumana", "Rumanía", "44422");
            Cliente c2 = new Cliente("12341234D", "Dragos Gabriel", "Grecu", "Nicula", sucursal2, LocalDate.now().toString(), BCrypt.hashpw("1234", BCrypt.gensalt()), "", ic2);

            InfoCliente ic3 = new InfoCliente("Espaniola", "Espania", "28220");
            Cliente c3 = new Cliente("12341234E", "Edurne Fatima", "Barrondo", "Martin", sucursal2, LocalDate.now().toString(), BCrypt.hashpw("1234", BCrypt.gensalt()), "687900822", ic3);

            InfoCliente ic4 = new InfoCliente("Espaniola", "Espania", "");
            Cliente c4 = new Cliente("12341234P", "Pedro", "Ardaiz", "Camacho", sucursal2, LocalDate.now().toString(), BCrypt.hashpw("1234", BCrypt.gensalt()), "62734544", ic4);
            try {
                ArrayList<Sucursal> sucursalesYaExistentes = IMP_SUCURSAL.readSucursales();
                if (sucursalesYaExistentes.size() < 1) {
                    IMP_SUCURSAL.createSucursal(sucursal1);
                    IMP_SUCURSAL.createSucursal(sucursal2);
                }
            } catch (Exception ex) {
            }
            try {
                IMP_CLIENTE.createCliente(c1);
            } catch (Exception ex) {
            }
            try {
                IMP_CLIENTE.createCliente(c2);
            } catch (Exception ex) {
            }
            try {
                IMP_CLIENTE.createCliente(c3);
            } catch (Exception ex) {
            }
            try {
                IMP_CLIENTE.createCliente(c4);
            } catch (Exception ex) {
            }
            try {
                ArrayList<Cuenta> cuentasYaExistentes = IMP_CUENTA.readCuentas();
                if (cuentasYaExistentes.size() < 1) {
                    IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(10000), IMP_CLIENTE.readCliente("12341234V"), 1));
                    IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(10000), IMP_CLIENTE.readCliente("12341234D"), 1));
                    IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(10000), IMP_CLIENTE.readCliente("12341234E"), 1));
                    IMP_INFO_CUENTA.createInfoCuenta(new InfoCuenta(new Cuenta(10000), IMP_CLIENTE.readCliente("12341234P"), 1));
                }
            } catch (Exception ex) {
            }
            fabricado = true;
        }
    }
}
