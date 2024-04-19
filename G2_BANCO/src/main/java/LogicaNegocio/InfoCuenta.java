package LogicaNegocio;

import jakarta.persistence.*;

@Entity(name = "info_cuentas")
public class InfoCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInfoCuenta;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "numero_cuenta")
    private Cuenta numeroCuenta;

    /**
     * Sin este CascadeType.MERGE no funciona ya que el cliente ya existe una
     * vez creamos la cuenta.
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dni_cliente")
    private Cliente dniCliente;

    //no sé si esto de columnDefinition funciona...
    @Column(name = "es_titular")
    private int esTitular;

    public InfoCuenta() {
    }

    public InfoCuenta(Cuenta cuenta, Cliente cliente, int esTitular) {
        this.numeroCuenta = cuenta;
        this.dniCliente = cliente;
        this.esTitular = esTitular;
    }

    public int getIdInfoCuenta() {
        return idInfoCuenta;
    }

    public void setIdInfoCuenta(int idInfoCuenta) {
        this.idInfoCuenta = idInfoCuenta;
    }

    public Cuenta getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Cuenta numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(Cliente dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getEsTitular() {
        return esTitular;
    }

    public void setEsTitular(int esTitular) {
        this.esTitular = esTitular;
    }

    

}
