package XML;

import java.util.Objects;

/**
 * este objeto nos facilita el guardado y la consulta de las transferencias
 */
public class Transferencia {

    private String cuentaOrigen;
    private String dniOrigen;
    private String cuentaDestino;
    private String importeEnviado;
    private String fechaEnvio;
    private String concepto;

    public Transferencia(String cuentaOrigen, String dniOrigen, String cuentaDestino, String importeEnviado, String fechaEnvio, String concepto) {
        this.cuentaOrigen = cuentaOrigen;
        this.dniOrigen = dniOrigen;
        this.cuentaDestino = cuentaDestino;
        this.importeEnviado = importeEnviado;
        this.fechaEnvio = fechaEnvio;
        this.concepto = concepto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getDniOrigen() {
        return dniOrigen;
    }

    public void setDniOrigen(String dniOrigen) {
        this.dniOrigen = dniOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getImporteEnviado() {
        return importeEnviado;
    }

    public void setImporteEnviado(String importeEnviado) {
        this.importeEnviado = importeEnviado;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != this.getClass()) {
            return false;
        }
        final Transferencia other = (Transferencia) obj;
        if (!Objects.equals(this.cuentaOrigen, other.cuentaOrigen)) {
            return false;
        }
        if (!Objects.equals(this.dniOrigen, other.dniOrigen)) {
            return false;
        }
        if (!Objects.equals(this.cuentaDestino, other.cuentaDestino)) {
            return false;
        }
        if (!Objects.equals(this.importeEnviado, other.importeEnviado)) {
            return false;
        }
        if (!Objects.equals(this.fechaEnvio, other.fechaEnvio)) {
            return false;
        }
        return Objects.equals(this.concepto, other.concepto);
    }
    
    
}
