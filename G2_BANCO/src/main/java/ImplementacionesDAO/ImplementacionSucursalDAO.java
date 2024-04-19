package ImplementacionesDAO;

import DAO.SucursalDAO;
import General.ControlEntradas;
import LogicaNegocio.Sucursal;
import java.util.ArrayList;
import java.util.List;
import static General.ControlEntradas.pedirInt;
import static Persistencia.HibernateConfig.getEntityManagerInstance;
import jakarta.persistence.RollbackException;

public class ImplementacionSucursalDAO implements SucursalDAO {

    @Override
    public void createSucursal(Sucursal sucursal) {
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().persist(sucursal);
        getEntityManagerInstance().getTransaction().commit();
    }

    @Override
    public Sucursal readSucursal(int idSucursal) {
        //TODO
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Sucursal> readSucursales() {
        List<Sucursal> sucursales = getEntityManagerInstance().createQuery("Select r FROM Sucursales r", Sucursal.class).getResultList();
        return new ArrayList<Sucursal>(sucursales);
    }

    /**
     * antes de que el administrador pueda llamar a este método debe elegir una
     * sucursal que exista, por lo que no hace falta comprobar que la sucursal
     * existe.
     */
    @Override
    public void updateSucursal(Sucursal sucursal) {
        Sucursal sucursalModificada = auxiliarModificacionSucursal(sucursal);
        getEntityManagerInstance().getTransaction().begin();
        getEntityManagerInstance().merge(sucursalModificada);
        getEntityManagerInstance().getTransaction().commit();
        System.out.println("Sucursal modificada: " + sucursal.toString());
    }

    private Sucursal auxiliarModificacionSucursal(Sucursal sucursal) {
        boolean seguir = true;
        while (seguir) {
            System.out.println("""
                               Pulsa 1 para cambiar la ciudad.
                               Pulsa 2 para cambiar la dirección.
                               Pulsa 0 para GUARDAR Y SALIR.""");
            switch (ControlEntradas.pedirInt()) {
                case 0:
                    return sucursal;
                case 1:
                    System.out.println("La ciudad actual es: " + sucursal.getCiudad());
                    System.out.println("Introduce el nuevo nombre:");
                    sucursal.setCiudad(ControlEntradas.pedirCampoSINDigito(1, 30));
                    break;
                case 2:
                    System.out.println("La dirección actual es " + sucursal.getDireccion());
                    System.out.println("Introduce la nueva dirección:");
                    sucursal.setDireccion(ControlEntradas.pedirCampo(1, 50));
                    System.out.println("La dirección actual es " + sucursal.getDireccion());
            }
        }
        return sucursal;
    }

    /**
     * antes de que el administrador pueda llamar a este método debe elegir una
     * sucursal que exista, por lo que no hace falta comprobar que la sucursal
     * existe.
     */
    @Override
    public void deleteSucursal(Sucursal sucursal) {
        try {
            getEntityManagerInstance().getTransaction().begin();
            getEntityManagerInstance().remove(sucursal);
            getEntityManagerInstance().getTransaction().commit();
            System.out.println("Sucursal eliminada con éxito.");
        } catch (RollbackException ex) {
            System.out.println("No se puede borrar esta sucursal porque tiene clientes asignados.");
        }
    }

    @Override
    public Sucursal chooseSucursal() {
        ArrayList<Sucursal> sucursales = new ImplementacionSucursalDAO().readSucursales();
        while (true) {
            try {
                for (int i = 0; i < sucursales.size(); i++) {
                    int id = sucursales.get(i).getIdSucursal();
                    String pais = sucursales.get(i).getPais();
                    String ciudad = sucursales.get(i).getCiudad();
                    String direccion = sucursales.get(i).getDireccion();
                    System.out.println("\tPulsa " + (i + 1) + " para la sucursal con ID = " + id + ", país = " + pais + ", ciudad = " + ciudad + ", y dirección = " + direccion + ".");
                }
                int eleccion = pedirInt();
                return sucursales.get(eleccion - 1);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Elección incorrecta. Vuelve a intentarlo: ");
            }
        }
    }

}
