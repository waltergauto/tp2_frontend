package unknown.Sistema_Inventario_Android.TABLAS;

public class tab_vent {
    //Constantes campos tabla ventas
    public static final String TABLA_VENTA="VENTAS";
    public static final String ID_VENTAS="ID";
    public static final String FECHA_VENTA="FECHA_VENTA";
    public static final String NRO_FACTURA="NRO_FACTURA";
    public static final String CAMPO_COMPRADOR="CLIENTE";
    public static final String CAMPO_PRODUCTOS="PRODUCTOS";
    public static final String TOTAL_PARCIAL="TOTAL_PARCIAL";
    public static final String TOTAL="TOTAL";
    //CREAR TABLA VENTAS
    public static final String CREAR_TABLA_VENTA="CREATE TABLE " + "" +TABLA_VENTA + " (" + ID_VENTAS +" " +
            "TEXT, " + FECHA_VENTA +" TEXT, " + NRO_FACTURA +" TEXT," + CAMPO_COMPRADOR +" TEXT," + CAMPO_PRODUCTOS + " TEXT, "+ TOTAL_PARCIAL +" TEXT)";


}
