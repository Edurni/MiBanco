package DAO;

import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import LogicaNegocio.InfoCuenta;
import java.util.ArrayList;

public interface InfoCuentaDAO {

    public abstract void createInfoCuenta(InfoCuenta infoCuenta);

    public abstract ArrayList<InfoCuenta> readInfoCuentas(Cliente cliente);

    public abstract InfoCuenta readInfoCuenta(Cuenta cuenta);

    public abstract void deleteInfoCuenta(Cuenta cuenta);

}
