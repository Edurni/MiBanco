package LogicaNegocio;

import jakarta.persistence.*;

@Entity(name = "info_clientes")
public class InfoCliente implements ElementoBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_info_cliente")
    private int idInfoCliente;

    private String nacionalidad;

    private String pais;

    private String codPostal;

    // Constructor, getter y setter
    public InfoCliente(String nacionalidad, String pais, String codPostal) {
        this.nacionalidad = nacionalidad;
        this.pais = pais;
        this.codPostal = codPostal;
    }

    public InfoCliente() {
    }

    @Override
    public String toString() {
        return "InfoCliente{" + "idInfoCliente=" + idInfoCliente + ", nacionalidad=" + nacionalidad + ", pais=" + pais + ", codPostal=" + codPostal + '}';
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

}
