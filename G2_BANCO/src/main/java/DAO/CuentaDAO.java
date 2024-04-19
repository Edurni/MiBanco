package DAO;

import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;

public interface CuentaDAO {

    public abstract void createCuenta(Cuenta cuenta);

    public abstract Cuenta readCuenta(int numCuenta);

    public abstract ArrayList<Cuenta> readCuentas();

    public abstract void updateCuenta(Cuenta cuenta);

    public abstract void deleteCuenta(Cuenta cuenta);

    public abstract Cuenta chooseCuenta(Cliente cliente, int titular);

}
