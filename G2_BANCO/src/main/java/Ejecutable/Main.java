package Ejecutable;

import General.DatosDePrueba;
import Menus.Menu;
import static Persistencia.HibernateConfig.getEntityManagerInstance;

public class Main {

    @SuppressWarnings("Unchecked")
    public static void main(String[] args) {
        /**
         * UNOS DATOS DE PRUEBA (POR CIERTO, HAY QUE TENER EN CUENTA QUE SI NO
         * EXISTEN SUCURSALES NO SE PUEDEN CREAR CLIENTES, NO ESTÁ CONTROLADO
         * AÚN).
         */
        DatosDePrueba.fabricar();

        Menu.mostrarMenu();

        //ESTAS SIEMPRE SERÁN LA ÚLTIMAS LÍNEAS DEL MAIN
        getEntityManagerInstance().close();
        System.out.println("*** SE HA CERRADO LA CONEXIÓN CON LA BASE DE DATOS ***");
    }

}
