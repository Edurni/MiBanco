package DAO;

import LogicaNegocio.Cliente;
import LogicaNegocio.Cuenta;
import LogicaNegocio.Sucursal;
import java.util.ArrayList;

public interface ClienteDAO {

    public abstract void createCliente(Cliente cliente);

    public abstract Cliente readCliente(String dniCliente);

    public abstract ArrayList<Cliente> readClientes();

    public abstract ArrayList<Cliente> readClientes(Sucursal sucursal);

    public abstract void updateCliente(Cliente cliente);

    public void updatePasswdCliente(Cliente cliente, String oldPasswd, String newpasswd, String confNewPasswd);

    public abstract void deleteCliente(String dniCliente);
}
