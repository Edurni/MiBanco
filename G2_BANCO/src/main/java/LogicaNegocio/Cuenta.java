package LogicaNegocio;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "Cuentas")
public class Cuenta implements ElementoBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numCuenta;

    @ColumnDefault("0")
    private double saldo;

    @OneToMany(mappedBy = "numeroCuenta")
    private Set<InfoCuenta> infoCuentas = new HashSet<>();

    public Cuenta() {
    }

    public Cuenta(int numCuenta, double saldo) {
        this.numCuenta = numCuenta;
        this.saldo = saldo;
    }
    
    public Cuenta(double saldo) {
        this.saldo = saldo;
    }

    public int getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(int numCuenta) {
        this.numCuenta = numCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Set<InfoCuenta> getInfoCuentas() {
        return infoCuentas;
    }

    public void setInfoCuentas(Set<InfoCuenta> infoCuentas) {
        this.infoCuentas = infoCuentas;
    }

    

    @Override
    public String toString() {
        return "Cuenta{" + "numCuenta=" + numCuenta + ", saldo=" + saldo + '}';
    }

}
