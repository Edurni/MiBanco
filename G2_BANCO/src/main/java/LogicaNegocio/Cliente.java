package LogicaNegocio;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.mindrot.jbcrypt.BCrypt;

@Entity(name = "Clientes")
public class Cliente implements ElementoBancario {

    @Id
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(nullable = false, length = 25)
    private String nombre;

    @Column(nullable = false, length = 25)
    private String apellido1;

    @Column(nullable = false, length = 25)
    private String apellido2;

    @ManyToOne
    @JoinColumn(name = "IDSucursal")
    private Sucursal sucursal;

    @Column(nullable = false, length = 11)
    private String fechaDeAlta;

    @Column(nullable = false, length = 60) // Longitud suficiente para almacenar el hash de BCrypt
    private String password;

    private String tlf;

    @OneToMany(mappedBy = "dniCliente")
    private Set<InfoCuenta> infoCuentas = new HashSet<>();

    /**
     * cascade es muy importante ya que crea InfoCliente antes de crear Cliente
     * y además si se borra o actualiza lo hace en los dos sitios.
     *
     * JoinColumn nos dice que la relación queda marcada en esta tabla.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_info_cliente", referencedColumnName = "id_info_cliente")
    private InfoCliente infoCliente;

    public Cliente(String dni, String nombre, String apellido1, String apellido2, Sucursal sucursal, String fechaDeAlta, String password, String tlf, InfoCliente infoCliente) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sucursal = sucursal;
        this.fechaDeAlta = fechaDeAlta;
        this.password = password;
        this.tlf = tlf;
        this.infoCliente = infoCliente;
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return "Cliente{" + "dni=" + dni + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", sucursal=" + sucursal + ", fechaDeAlta=" + fechaDeAlta + ", password=" + password + ", tlf=" + tlf + ", infoCliente=" + infoCliente + '}';
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getFechaDeAlta() {
        return fechaDeAlta;
    }

    public void setFechaDeAlta(String fechaDeAlta) {
        this.fechaDeAlta = fechaDeAlta;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public Set<InfoCuenta> getInfoCuentas() {
        return infoCuentas;
    }

    public void setInfoCuentas(Set<InfoCuenta> infoCuentas) {
        this.infoCuentas = infoCuentas;
    }

    public InfoCliente getInfoCliente() {
        return infoCliente;
    }

    public void setInfoCliente(InfoCliente infoCliente) {
        this.infoCliente = infoCliente;
    }

    public void setPassword(String password) {
        // Hash de la contraseña antes de almacenarla en la base de datos
        //gensalt() genera automáticamente una sal (un valor aleatorio) para fortalecer el hash.
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String candidate) {
        return BCrypt.checkpw(candidate, this.password);
    }
}
