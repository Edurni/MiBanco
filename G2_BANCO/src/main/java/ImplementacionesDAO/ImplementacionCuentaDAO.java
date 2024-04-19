package ImplementacionesDAO;

import DAO.CuentaDAO;
import General.ControlEntradas;
import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import static Persistencia.HibernateConfig.getEntityManagerInstance;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ImplementacionCuentaDAO implements CuentaDAO {

    @Override
    public void createCuenta(Cuenta cuenta) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().persist(cuenta);
        getEntityManagerInstance().getTransaction().commit();
    }

    @Override
    public Cuenta readCuenta(int numCuenta) {
        Cuenta aux = getEntityManagerInstance().find(Cuenta.class, numCuenta);
        if (aux != null) {
            return aux;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public ArrayList<Cuenta> readCuentas() {
        List<Cuenta> cuentas = getEntityManagerInstance().createQuery("Select c FROM Cuentas c", Cuenta.class).getResultList();
        return new ArrayList<Cuenta>(cuentas);
    }

    @Override
    public void updateCuenta(Cuenta cuenta) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().merge(cuenta);
        getEntityManagerInstance().getTransaction().commit();
    }

    @Override
    public void deleteCuenta(Cuenta cuenta) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().remove(cuenta);
        getEntityManagerInstance().getTransaction().commit();
        if (cuenta.getSaldo() > 0) {
            System.out.println("Se han devuelto " + cuenta.getSaldo() + " euros en efectivo.");
        }
    }

    @Override
    public Cuenta chooseCuenta(Cliente cliente, int titular) throws NullPointerException {
        TypedQuery<Cuenta> query = null;
        switch (titular) {
            case 0:
            case 1:
                query = getEntityManagerInstance().createQuery(
                        "SELECT c FROM Cuentas c JOIN c.infoCuentas ic WHERE ic.dniCliente = :dni AND ic.esTitular = :titular", Cuenta.class);
                query.setParameter("titular", titular);
                break;
            default:
                query = getEntityManagerInstance().createQuery("SELECT c FROM Cuentas c JOIN c.infoCuentas ic WHERE ic.dniCliente = :dni", Cuenta.class);
                break;
        }
        query.setParameter("dni", cliente);
        List<Cuenta> cuentas = query.getResultList();
        if (cuentas.isEmpty()) {
            throw new NullPointerException();
        }
        while (true) {
            try {
                for (int i = 0; i < cuentas.size(); i++) {
                    System.out.println("\t Pulsa " + (i + 1) + " para la cuenta con ID " + cuentas.get(i).getNumCuenta() + " y saldo " + cuentas.get(i).getSaldo());
                }
                int eleccion = ControlEntradas.pedirInt();
                return cuentas.get(eleccion - 1);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Opción incorrecta. Vuelve a intentarlo:");
            }
        }
    }

    public void mostrarSaldoCuentas(Cliente cliente) throws NullPointerException {
        TypedQuery<Cuenta> query = getEntityManagerInstance().createQuery("SELECT c FROM Cuentas c JOIN c.infoCuentas ic WHERE ic.dniCliente = :dni", Cuenta.class);
        query.setParameter("dni", cliente);
        List<Cuenta> cuentas = query.getResultList();
        if (cuentas.isEmpty()) {
            throw new NullPointerException();
        }
        for (int i = 0; i < cuentas.size(); i++) {
            System.out.println("\tID cuenta = " + cuentas.get(i).getNumCuenta() + " | saldo disponible = " + cuentas.get(i).getSaldo());
        }
    }

    public Cuenta buscarCuentaPorNumero(int numCuenta) {
        TypedQuery<Cuenta> query = getEntityManagerInstance().createQuery(
                //: para buscar un parámetro de la consulta (Parametro - valor)
                "SELECT c FROM Cuentas c WHERE c.numCuenta = :numeroCuenta", Cuenta.class);
        query.setParameter("numeroCuenta", numCuenta);
        List<Cuenta> cuentas = query.getResultList();
        if (cuentas.isEmpty()) {
            throw new NullPointerException("No se encontró ninguna cuenta con el número: " + numCuenta);
        }
        // Como solo debe haber una cuenta en la lista
        return cuentas.get(0);
    }
}
