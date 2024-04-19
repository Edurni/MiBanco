package XML;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public abstract class XMLManager {

    private static final String RUTA_DIRECTORIO_TRANSFERENCIAS = "./transferencias";
    private static final File DIRECTORIO = new File(RUTA_DIRECTORIO_TRANSFERENCIAS);
    private static final DocumentBuilderFactory DOC_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder docBuilder = null;
    private static DOMImplementation implementation = null;
    private static Document document = null;
    private static Source source = null;
    private static Result result = null;
    private static Transformer transformer = null;

    public static void guardarTransferencia(String cuentaOrigenText, String dniOrigenText, String cuentaDestinoText, String importeText, String conceptoText) {
        try {
            //creamos el directorio si no existe
            crearDirectorioTransferencias();

            docBuilder = DOC_BUILDER_FACTORY.newDocumentBuilder();
            implementation = docBuilder.getDOMImplementation();
            document = implementation.createDocument("banco.g2.com", "bancog2", null);
            document.setXmlVersion("1.0");
            Element transferencias = document.createElement("transferencias");
            Element transferencia = document.createElement("transferencia");

            //agregamos las transferencias que ya existían antes de la nueva transferencia
            obtenerTransferencias();
            for (int i = 0; i < Transferencias.transferencias.size(); i++) {
                String origen = Transferencias.transferencias.get(i).getCuentaOrigen();
                String dni = Transferencias.transferencias.get(i).getDniOrigen();
                String destino = Transferencias.transferencias.get(i).getCuentaDestino();
                String importe = Transferencias.transferencias.get(i).getImporteEnviado();
                String fecha = Transferencias.transferencias.get(i).getFechaEnvio();
                String concepto = Transferencias.transferencias.get(i).getConcepto();
                transferencia = agregarTransferencia(origen, dni, destino, importe, fecha, concepto);
                transferencias.appendChild(transferencia);
            }

            //agregamos la transferencia nueva
            transferencia = agregarTransferencia(cuentaOrigenText, dniOrigenText, cuentaDestinoText, importeText, conceptoText);
            transferencias.appendChild(transferencia);
            document.getDocumentElement().appendChild(transferencias);

            //Una vez definida la estructura del XML, hay que crearlo.
            //Si no existe lo crea, si existe lo pisa.
            source = new DOMSource(document);
            result = new StreamResult(new File("transferencias/transferencias_g2.xml"));
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println("Error --> No se ha actualziado el fichero xml.");
        }
    }

    private static void crearDirectorioTransferencias() {
        if (!DIRECTORIO.exists()) {
            DIRECTORIO.mkdirs();
        }
    }

    /**
     * Este método no acepta fecha
     */
    private static Element agregarTransferencia(String cuentaOrigenText, String dniOrigenText, String cuentaDestinoText, String importeText, String conceptoText) {
        Element transferencia = document.createElement("transferencia");

        Element cuentaOrigen = document.createElement("cuenta-origen");
        Text textoCuentaOrigen = document.createTextNode(cuentaOrigenText);
        cuentaOrigen.appendChild(textoCuentaOrigen);
        transferencia.appendChild(cuentaOrigen);

        Element dniOrigen = document.createElement("dni-origen");
        Text textDniOrigen = document.createTextNode(dniOrigenText);
        dniOrigen.appendChild(textDniOrigen);
        transferencia.appendChild(dniOrigen);

        Element cuentaDestino = document.createElement("cuenta-destino");
        Text textCuentaDestino = document.createTextNode(cuentaDestinoText);
        cuentaDestino.appendChild(textCuentaDestino);
        transferencia.appendChild(cuentaDestino);

        Element importeEnviado = document.createElement("importe-enviado");
        Text textImporteEnviado = document.createTextNode(importeText);
        importeEnviado.appendChild(textImporteEnviado);
        transferencia.appendChild(importeEnviado);

        Element fechaEnvio = document.createElement("fecha-envio");
        fechaEnvio.appendChild(document.createTextNode(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()));
        transferencia.appendChild(fechaEnvio);

        Element concepto = document.createElement("concepto");
        Text textConcepto = document.createTextNode(conceptoText);
        concepto.appendChild(textConcepto);
        transferencia.appendChild(concepto);

        return transferencia;
    }

    /**
     * Este método acepta fecha
     */
    private static Element agregarTransferencia(String cuentaOrigenText, String dniOrigenText, String cuentaDestinoText, String importeText, String fechaText, String conceptoText) {
        Element transferencia = document.createElement("transferencia");

        Element cuentaOrigen = document.createElement("cuenta-origen");
        Text textoCuentaOrigen = document.createTextNode(cuentaOrigenText);
        cuentaOrigen.appendChild(textoCuentaOrigen);
        transferencia.appendChild(cuentaOrigen);

        Element dniOrigen = document.createElement("dni-origen");
        Text textDniOrigen = document.createTextNode(dniOrigenText);
        dniOrigen.appendChild(textDniOrigen);
        transferencia.appendChild(dniOrigen);

        Element cuentaDestino = document.createElement("cuenta-destino");
        Text textCuentaDestino = document.createTextNode(cuentaDestinoText);
        cuentaDestino.appendChild(textCuentaDestino);
        transferencia.appendChild(cuentaDestino);

        Element importeEnviado = document.createElement("importe-enviado");
        Text textImporteEnviado = document.createTextNode(importeText);
        importeEnviado.appendChild(textImporteEnviado);
        transferencia.appendChild(importeEnviado);

        Element fechaEnvio = document.createElement("fecha-envio");
        Text textFechaEnvio = document.createTextNode(fechaText);
        fechaEnvio.appendChild(textFechaEnvio);
        transferencia.appendChild(fechaEnvio);

        Element concepto = document.createElement("concepto");
        Text textConcepto = document.createTextNode(conceptoText);
        concepto.appendChild(textConcepto);
        transferencia.appendChild(concepto);

        return transferencia;
    }

    private static void obtenerTransferencias() {
        try {
            if (new File("transferencias/transferencias_g2.xml").exists()) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                TransferenciaHandler handler = new TransferenciaHandler();
                parser.parse(new File("transferencias/transferencias_g2.xml"), handler);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void leerTransferencias() {
        crearDirectorioTransferencias();
        obtenerTransferencias();
        if (Transferencias.transferencias.size() < 1) {
            System.out.println("El historial de transferencias está vacío.");
            return;
        } else {
            System.out.println("TOTAL: " + Transferencias.transferencias.size());
            System.out.println("======================================");
        }
        for (int i = 0; i < Transferencias.transferencias.size(); i++) {
            String origen = Transferencias.transferencias.get(i).getCuentaOrigen();
            String dni = Transferencias.transferencias.get(i).getDniOrigen();
            String destino = Transferencias.transferencias.get(i).getCuentaDestino();
            String importe = Transferencias.transferencias.get(i).getImporteEnviado();
            String fecha = Transferencias.transferencias.get(i).getFechaEnvio().replace("T", " | ");
            String concepto = Transferencias.transferencias.get(i).getConcepto();
            System.out.println("""
                               Número de cuenta de orígen: %s
                               DNI del remitente: %s
                               Número de cuenta de destino: %s
                               Importe enviado: %s
                               Fecha del envío: %s
                               Concepto: %s"""
                    .formatted(origen, dni, destino, importe, fecha, concepto));
            if (i == Transferencias.transferencias.size() - 1) {
                System.out.println("======================================");
            } else {
                System.out.println("--------------------------------------");
            }
        }
    }
}
