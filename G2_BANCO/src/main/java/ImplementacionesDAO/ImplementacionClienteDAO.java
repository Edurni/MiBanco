package ImplementacionesDAO;

import DAO.ClienteDAO;
import General.ControlEntradas;
import LogicaNegocio.Cliente;
import LogicaNegocio.Sucursal;
import java.util.ArrayList;
import java.util.List;
import static Persistencia.HibernateConfig.getEntityManagerInstance;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;

public class ImplementacionClienteDAO implements ClienteDAO {

    @Override
    public void createCliente(Cliente cliente) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().persist(cliente);
        getEntityManagerInstance().getTransaction().commit();
    }

    @Override
    public Cliente readCliente(String dniCliente) throws NullPointerException {
        Cliente aux = getEntityManagerInstance().find(Cliente.class, dniCliente);
        if (aux != null) {
            return aux;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public ArrayList<Cliente> readClientes() {
        List<Cliente> clientes = getEntityManagerInstance().createQuery("SELECT c FROM Clientes c", Cliente.class).getResultList();
        return new ArrayList<>(clientes);
    }

    @Override
    public ArrayList<Cliente> readClientes(Sucursal sucursal) {
        TypedQuery<Cliente> query = getEntityManagerInstance().createQuery("SELECT c FROM Clientes c WHERE c.sucursal = :sucursal", Cliente.class);
        query.setParameter("sucursal", sucursal);
        List<Cliente> clientes = query.getResultList();
        return new ArrayList<>(clientes);
    }

    @Override
    public void updateCliente(Cliente cliente) {
        Cliente aux = getEntityManagerInstance().find(Cliente.class, cliente.getDni());
        if (aux != null) {
            Cliente clienteModificado = auxiliarModificacionCliente(cliente);
            getEntityManagerInstance().getTransaction().begin();
            getEntityManagerInstance().merge(clienteModificado);
            getEntityManagerInstance().getTransaction().commit();
            System.out.println("Cliente modificado: " + aux.toString());
        } else {
            System.out.println("No se ha encontrado el cliente.");
        }
    }

    @Override
    public void updatePasswdCliente(Cliente cliente, String oldPasswd, String newPasswd, String confNewPasswd) {
        if (cliente.checkPassword(oldPasswd)) {
            if (newPasswd.equals(confNewPasswd)) {
                cliente.setPassword(newPasswd);
                getEntityManagerInstance().getTransaction().begin();
                getEntityManagerInstance().merge(cliente);
                getEntityManagerInstance().getTransaction().commit();
                System.out.println("La contraseña se ha actualizado.");
            } else {
                System.out.println("La contraseña nueva y su confirmación no son iguales.");
            }
        } else {
            System.out.println("Contraseña actual incorrecta.");
        }
    }

    /**
     * esta moficiación se utiliza en modo administrador, por lo que se puede
     * cambair todo de un cliente menos su dni y su contraseña. Para cambiar la
     * contraseña debes entrar como cliente (por temas de seguridad).
     *
     * @param cliente
     * @return
     */
    private Cliente auxiliarModificacionCliente(Cliente cliente) {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               Pulsa 1 para cambiar el nombre.
                               Pulsa 2 para cambiar el primer apellido.
                               Pulsa 3 para cambiar el segundo apellido.
                               Pulsa 4 para cambiar la sucursal.
                               Pulsa 5 para cambiar el número de teléfono.
                               Pulsa 6 para cambiar la nacionalidad.
                               Pulsa 7 para cambiar el país.
                               Pulsa 8 para cambiar el código postal.
                               Pulsa 0 para GUARDAR Y SALIR.""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    return cliente;
                case 1:
                    System.out.println("El nombre actual es " + cliente.getNombre());
                    System.out.println("Introduce el nuevo nombre:");
                    cliente.setNombre(ControlEntradas.pedirCampoSINDigito(1, 25));
                    break;
                case 2:
                    System.out.println("El primer apellido actual es " + cliente.getApellido1());
                    System.out.println("Introduce el nuevo apellido:");
                    cliente.setApellido1(ControlEntradas.pedirCampoSINDigito(1, 25));
                    break;
                case 3:
                    System.out.println("El segundo apellido actual es " + cliente.getApellido2());
                    System.out.println("Introduce el nuevo apellido:");
                    cliente.setApellido2(ControlEntradas.pedirCampoSINDigito(1, 25));
                    break;
                case 4:
                    System.out.println("La sucursal actual del cliente es " + cliente.getSucursal().toString());
                    cliente.setSucursal(new ImplementacionSucursalDAO().chooseSucursal());
                    System.out.println("Sucursal modificada, ahora pertenece a la sucursal: " + cliente.getSucursal().toString());
                    break;
                case 5:
                    System.out.println("El número de teléfono actual es " + cliente.getTlf());
                    System.out.println("Introduce el nuevo número de teléfono:");
                    cliente.setTlf(ControlEntradas.pedirCampo(9));
                    break;
                case 6:
                    System.out.println("La nacionalidad actual es " + cliente.getInfoCliente().getNacionalidad());
                    System.out.println("Introduce la nueva nacionalidad:");
                    cliente.getInfoCliente().setNacionalidad(ControlEntradas.pedirCampoSINDigito(1, 25));
                    break;
                case 7:
                    System.out.println("El país actual es " + cliente.getInfoCliente().getPais());
                    System.out.println("Introduce el nuevo país:");
                    cliente.getInfoCliente().setPais(ControlEntradas.pedirCampoSINDigito(1, 25));
                    break;
                case 8:
                    System.out.println("El código postal actual es " + cliente.getInfoCliente().getCodPostal());
                    System.out.println("Introduce el nuevo código postal:");
                    cliente.getInfoCliente().setCodPostal(ControlEntradas.pedirCampo(5));
                    break;
            }
        }
        return cliente;
    }

    @Override
    public void deleteCliente(String dniCliente) {
        Cliente aux = getEntityManagerInstance().find(Cliente.class, dniCliente);
        try {
            if (aux != null) {
                getEntityManagerInstance().getTransaction().begin();
                getEntityManagerInstance().remove(aux);
                getEntityManagerInstance().remove(aux.getInfoCliente());     //borramos también la info del cliente
                getEntityManagerInstance().getTransaction().commit();
                System.out.println("Cliente eliminado: " + aux.toString());
            } else {
                System.out.println("El cliente con DNI " + dniCliente + " no existe.");
            }
        } catch (RollbackException ex) {
            System.out.println("Este cliente no se puede borrar ya que tiene cuentas asociadas.");
        }
    }

}
