package Menus;

import General.ControlEntradas;
import static Persistencia.HibernateConfig.getEntityManagerInstance;
import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;

public class MenuCliente extends Menu {

    private static Cliente cliente;

    public static void mostrarMenu(Cliente clienteAux) {
        cliente = clienteAux;
        String dniCliente = clienteAux.getDni();
        String nombreApellidosCliente = cliente.getNombre() + " " + cliente.getApellido1() + " " + cliente.getApellido2();
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               *** BANCO G2 --> %s (%s)
                               1 --> Consultar saldo
                               2 --> Retirar saldo
                               3 --> Ingresar saldo
                               4 --> Hacer una transferencia
                               5 --> Cambiar contraseña
                               0 --> Salir""".formatted(nombreApellidosCliente, dniCliente));
            switch (ControlEntradas.pedirInt()) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    retirarSaldo();
                    break;
                case 3:
                    ingresarSaldo();
                    break;
                case 4:
                    enviarTransferencia();
                    break;
                case 5:
                    cambiarPasswd();
                    break;
                case 0:
                    System.out.println("Hasta pronto, " + cliente.getNombre());
                    cliente = null;
                    seguir = false;
                    break;
                default:
                    System.out.println("Opción incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    private static void consultarSaldo() {
        try {
            impCuenta.mostrarSaldoCuentas(cliente);
        } catch (NullPointerException ex) {
            System.out.println("No tienes cuentas asociadas. Puedes acudir a nuestras oficinas para crear una cuenta nueva.");
        }
    }

    private static void retirarSaldo() {
        Cuenta c = impCuenta.chooseCuenta(cliente, -1);
        System.out.println("Selecciona la cantidad a retirar: ");
        double cantidad = ControlEntradas.pedirCantidad();
        if (cantidad > c.getSaldo()) {
            System.out.println("No dispones de saldo suficiente.");
        } else {
            c.setSaldo(c.getSaldo() - cantidad);
            impCuenta.updateCuenta(c);
            System.out.println("Se ha retirado " + cantidad + "€. Nuevo saldo disponible: " + c.getSaldo() + "€");
        }
    }

    private static void ingresarSaldo() {
        Cuenta c = impCuenta.chooseCuenta(cliente, -1);
        System.out.println("Selecciona la cantidad a ingresar: ");
        double cantidad = ControlEntradas.pedirCantidad();
        c.setSaldo(c.getSaldo() + cantidad);
        impCuenta.updateCuenta(c);
        System.out.println("Se ha ingresado " + cantidad + "€. Nuevo saldo disponible: " + c.getSaldo() + "€");
    }

    /**
     * este método controla que nunca pueda pasarq que envíes un dinero y lo
     * pierdas sin que llegue al destinatario, esto se hace con ayuda de
     * getEntityManagerInstance().getTransaction().commit();
     *
     * es lo mismo que hacíamos en la primera evaluación con
     * setAutoCommit(false), commit() y rollback().
     */
    private static void enviarTransferencia() {
        Cuenta cuentaOrigen = impCuenta.chooseCuenta(cliente, -1);
        Cuenta cuentaDestino = null;
        System.out.println("Introduce el número de cuenta del destinatario (0 para salir): ");
        int destinatario = ControlEntradas.pedirInt();
        if (destinatario == 0) {
            System.out.println("Transferencia cancelada.");
            return;
        }
        try {
            cuentaDestino = impCuenta.readCuenta(destinatario);
        } catch (NullPointerException ex) {
            System.out.println("La cuenta con número " + destinatario + " no existe.");
            return;
        }
        System.out.println("Introduce la cantidad que deseas enviar: ");
        double cantidad = ControlEntradas.pedirCantidad();
        if (cantidad <= cuentaOrigen.getSaldo()) {
            System.out.println("Introduce un concepto: ");
            String concepto = ControlEntradas.SC.nextLine();
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - cantidad);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + cantidad);
            getEntityManagerInstance().getTransaction().begin();
            getEntityManagerInstance().merge(cuentaOrigen);
            getEntityManagerInstance().merge(cuentaDestino);
            getEntityManagerInstance().getTransaction().commit();
            XML.XMLManager.guardarTransferencia(String.valueOf(cuentaOrigen.getNumCuenta()), cliente.getDni(), String.valueOf(cuentaDestino.getNumCuenta()), String.valueOf(cantidad), concepto);
            System.out.println("Se han enviado " + cantidad + "€ a la cuenta con número " + cuentaDestino.getNumCuenta());
        } else {
            System.out.println("No dispones de saldo suficiente.");
        }
    }

    private static void cambiarPasswd() {
        System.out.println("Introduce la contraseña actual: ");
        String oldPasswd = ControlEntradas.pedirCampo(4, 10);
        System.out.println("Introduce la nueva contraseña: ");
        String newPasswd = ControlEntradas.pedirCampo(4, 10);
        System.out.println("Confirma la nueva contraseña: ");
        String confirmacionNewPasswd = ControlEntradas.pedirCampo(4, 10);
        impCliente.updatePasswdCliente(cliente, oldPasswd, newPasswd, confirmacionNewPasswd);
    }
}
