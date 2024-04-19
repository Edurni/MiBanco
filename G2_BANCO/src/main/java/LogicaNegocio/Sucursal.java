package LogicaNegocio;

import jakarta.persistence.*;

@Entity(name = "Sucursales")
public class Sucursal implements ElementoBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSucursal;
    @Column(nullable = false, length = 25)
    private String pais;
    @Column(nullable = false, length = 30)
    private String ciudad;
    @Column(nullable = false, length = 50)
    private String direccion;

    public Sucursal() {
    }

    public Sucursal(String pais, String ciudad, String direccion) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "idSucursal=" + idSucursal + ", pais=" + pais + ", ciudad=" + ciudad + ", direccion=" + direccion + '}';
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
