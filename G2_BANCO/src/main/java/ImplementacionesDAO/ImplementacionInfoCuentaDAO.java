package ImplementacionesDAO;

import DAO.InfoCuentaDAO;
import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import LogicaNegocio.InfoCuenta;
import static Persistencia.HibernateConfig.getEntityManagerInstance;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ImplementacionInfoCuentaDAO implements InfoCuentaDAO {

    @Override
    public void createInfoCuenta(InfoCuenta infoCuenta) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().persist(infoCuenta);
        getEntityManagerInstance().getTransaction().commit();
    }

    @Override
    public ArrayList<InfoCuenta> readInfoCuentas(Cliente cliente) {
        //Query query = getEntityManagerInstance().createQuery("SELECT c FROM info_cuentas c JOIN Clientes cl WHERE cl.dni = :dni", InfoCuenta.class);
        Query query = getEntityManagerInstance().createQuery("SELECT c FROM info_cuentas c JOIN c.dniCliente cl WHERE cl.dni = :dni", InfoCuenta.class);
        query.setParameter("dni", cliente.getDni());
        List<InfoCuenta> infoCuentas = query.getResultList();
        return new ArrayList<InfoCuenta>(infoCuentas);
    }
     public void obtenerSaldoCuentas(Cliente cliente) {
         //almacena un grupo de elementos únicos y vale para varias estructuras dedatos como varias estructuras de datos de colección, como listas, conjuntos y mapas.
        Set<InfoCuenta> infoCuentas = cliente.getInfoCuentas();
    if (infoCuentas.isEmpty()) {
        System.out.println("No tienes cuentas asociadas. Puedes acudir a nuestras oficinas para crear una cuenta nueva.");
        return;
    }

    for (InfoCuenta infoCuenta : infoCuentas) {
        Cuenta cuenta = infoCuenta.getNumeroCuenta();
        double saldo = cuenta.getSaldo();
        System.out.println("Saldo de la cuenta " + cuenta.getNumCuenta() + ": " + saldo + "€");
    }
    }

    @Override
    public InfoCuenta readInfoCuenta(Cuenta cuenta) {
        Query query = getEntityManagerInstance().createQuery("SELECT c FROM info_cuentas c WHERE c.numeroCuenta = :nc", InfoCuenta.class);
        query.setParameter("nc", cuenta);
        List<InfoCuenta> infoCuenta = query.getResultList();
        return infoCuenta.get(0);
    }

    @Override
    public void deleteInfoCuenta(Cuenta cuenta) {
        InfoCuenta infoCuenta = readInfoCuenta(cuenta);
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().remove(infoCuenta);
        getEntityManagerInstance().getTransaction().commit();
    }

}
