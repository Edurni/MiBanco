package DAO;

import LogicaNegocio.Sucursal;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;

public interface SucursalDAO {

    public abstract void createSucursal(Sucursal sucursal);

    public abstract Sucursal readSucursal(int idSucursal);

    public abstract ArrayList<Sucursal> readSucursales();

    public abstract void updateSucursal(Sucursal sucursal);

    public abstract void deleteSucursal(Sucursal sucursal);

    public abstract Sucursal chooseSucursal();
}
