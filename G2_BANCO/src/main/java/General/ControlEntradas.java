package General;

import LogicaNegocio.Cliente;
import java.util.Scanner;

public interface ControlEntradas {

    Scanner SC = new Scanner(System.in);

    public static String pedirCampo(int longitudExacta) {
        String campoParaDevolver = "";
        while (true) {
            campoParaDevolver = SC.nextLine();
            if (campoParaDevolver.equals("")) {
                return campoParaDevolver;
            }
            if (esInt(campoParaDevolver) && campoParaDevolver.length() == longitudExacta) {
                return campoParaDevolver;
            }
            System.out.println("Formato incorrecto (es opcional, pero debe tener " + longitudExacta + " dígitos). Inténtalo de nuevo: ");
        }
    }

    /**
     * nos permite pedir Strings con diferentes tamaños
     *
     * @param longitudMin el tamaño mínimo del String que se desea obtener
     * @param longitudMax el tamaño máximo, si es menor que 1 es porque no se
     * necesita
     * @return
     */
    public static String pedirCampo(int longitudMin, int longitudMax) {
        boolean seguir = true;
        String campoParaDevolver = "";
        String infoLongitudMax = ")";
        if (longitudMax > 0) {
            infoLongitudMax = " | max = " + longitudMax + ")";
        }
        while (seguir) {
            campoParaDevolver = SC.nextLine();
            if (campoParaDevolver.length() < longitudMin || campoParaDevolver.length() > longitudMax) {
                System.out.println("La longitud no es correcta (min = " + longitudMin + infoLongitudMax);
                System.out.println("Inténtalo de nuevo: ");
            } else {
                seguir = false;
            }
        }
        return campoParaDevolver;
    }
        public static String pedirCampoSINDigito(int longitudMin, int longitudMax) {
        boolean seguir = true;
        String campoParaDevolver = "";
        String infoLongitudMax = ")";
        if (longitudMax > 0) {
            infoLongitudMax = " | max = " + longitudMax + ")";
        }
        while (seguir) {
            campoParaDevolver = SC.nextLine();
            if (campoParaDevolver.length() < longitudMin || campoParaDevolver.length() > longitudMax) {
                System.out.println("La longitud no es correcta (min = " + longitudMin + infoLongitudMax);
                System.out.println("Inténtalo de nuevo: ");
            } else if (!contieneSoloLetras(campoParaDevolver)) {
                System.out.println("No se permiten dígitos ni caracteres especiales en este campo. Inténtalo de nuevo: ");
            } else {
                seguir = false;
            }
        }
        return campoParaDevolver;
    }

    private static boolean contieneSoloLetras(String cadena) {
        return cadena.matches("[a-zA-Z\\s]+");
    }

    private static boolean contieneDigitos(String cadena) {
        for (char c : cadena.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static String pedirDNI() {
        boolean seguir = true;
        String dni = "";
        System.out.println("Introduce el DNI del cliente (000 para salir): ");

        do {
            dni = SC.nextLine();
            if (comprobarDNI(dni) || dni.equals("000")) {
                seguir = false;
            } else {
                System.out.println("El DNI no es válido, inténtalo de nuevo (000 para salir): ");
            }
        } while (seguir);
        return dni.toUpperCase();
    }

    public static String pedirDNI(String msg) {
        boolean seguir = true;
        String dni = "";
        System.out.println(msg);

        do {
            dni = SC.nextLine();
            if (comprobarDNI(dni) || dni.equals("000")) {
                seguir = false;
            } else {
                System.out.println("El DNI no es válido, inténtalo de nuevo (000 para salir): ");
            }
        } while (seguir);
        return dni.toUpperCase();
    }

    public static boolean comprobarDNI(String DNI) {
        if (DNI.matches("[0-9]{8}[A-Z a-z]")) {
            return true;
        }
        return false;
    }

    public static String pedirPassword(Cliente cli) {
        boolean seguir = true;
        String password = "";
        System.out.println("Introduzca su Contraseña: ");

        do {
            password = SC.nextLine();
            if (cli.checkPassword(password)) {
                seguir = false;
            } else {
                System.out.println("La contraseña no es válida, intentalo de nuevo");
            }

        } while (seguir);
        return password;
    }

    public static double pedirCantidad() {
        boolean salir = false;
        String entrada = "";
        while (!salir) {
            entrada = SC.nextLine();
            if (esDouble(entrada)) {
                if (Double.parseDouble(entrada) > 0) {
                    salir = true;
                } else {
                    System.out.println("No se puede ingresar una cantidad negativa. Vuelve a intentarlo.");
                }
            } else {
                System.out.println("Formato incorrecto. Vuelve a intentarlo.");
            }
        }
        return Double.valueOf(entrada);
    }

    public static int pedirInt() {
        boolean noEsInt = true;
        String entrada = "";
        while (noEsInt) {
            entrada = SC.nextLine();
            if (esInt(entrada)) {
                noEsInt = false;
            } else {
                System.out.println("Formato incorrecto. Vuelve a intentarlo.");
            }
        }
        return Integer.parseInt(entrada);
    }

    /**
     *
     * @param entrada una cadena de caracteres
     * @return true si la entrada es Integer
     */
    private static boolean esInt(String entrada) {
        try {
            Integer.valueOf(entrada);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static boolean esDouble(String entrada) {
        try {
            Double.valueOf(entrada);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
