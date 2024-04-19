package XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * objeto necesario para el correcto funcionamiento de la lectura de documentos
 * xml
 */
public class TransferenciaHandler extends DefaultHandler {

    private final StringBuilder value;
    private String cuentaOrigenText;
    private String dniOrigenText;
    private String cuentaDestinoText;
    private String importeEnviadoText;
    private String fechaEnvioText;
    private String conceptoText;

    public TransferenciaHandler() {
        this.value = new StringBuilder();
    }

    //Código que se ejecuta antes de procesar la etiqueta
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("cuenta-origen")
                || qName.equals("dni-origen")
                || qName.equals("cuenta-destino")
                || qName.equals("importe-enviado")
                || qName.equals("fecha-envio")
                || qName.equals("concepto")) {
            this.value.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.value.append(ch, start, length);
    }

    //Código que se ejecuta tras procesar la etiqueta
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "cuenta-origen":
                cuentaOrigenText = this.value.toString().trim();
                break;
            case "dni-origen":
                dniOrigenText = this.value.toString().trim();
                break;
            case "cuenta-destino":
                cuentaDestinoText = this.value.toString().trim();
                break;
            case "importe-enviado":
                importeEnviadoText = this.value.toString().trim();
                break;
            case "fecha-envio":
                fechaEnvioText = this.value.toString().trim();
                break;
            case "concepto":
                conceptoText = this.value.toString().trim();
                break;
            case "transferencia":
                Transferencia transferencia = (new Transferencia(
                        cuentaOrigenText, dniOrigenText,
                        cuentaDestinoText, importeEnviadoText,
                        fechaEnvioText, conceptoText));
                for (int i = 0; i < Transferencias.transferencias.size(); i++) {
                    if (Transferencias.transferencias.get(i).equals(transferencia)) {
                        return;
                    }
                }
                Transferencias.transferencias.add(transferencia);
                break;
            default:
                break;
        }
    }
}
