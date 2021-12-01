package unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS;

public class vent {

/*
    public static final String ID_VENTAS="ID";
    public static final String FECHA_VENTA="FECHA_VENTA"
    public static final String NRO_FACTURA="NRO_FACTURA";
    public static final String CAMPO_COMPRADOR="CLIENTE";
    public static final String CAMPO_PRODUCTOS="PRODUCTOS";
    public static final String TOTAL_PARCIAL="TOTAL_PARCIAL";
    public static final String TOTAL="TOTAL";
*/
    private String id;
    private String fecha_venta;
    private String nro_factura;
    private String comprador;
    private String productos [];
    private String total_parcial;
    //private String total;

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getNro_factura() {
        return nro_factura;
    }

    public void setNro_factura(String nro_factura) {
        this.nro_factura = nro_factura;
    }

    public String getTotal_parcial() {
        return total_parcial;
    }

    public void setTotal_parcial(String total_parcial) {
        this.total_parcial = total_parcial;
    }
/*
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
*/
    public vent(String n, String c, String [] p){
        this.id = n;
        this.comprador = c;
        this.productos= p;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String[] getProductos() {
        return productos;
    }

    public void setProductos(String[] productos) {
        this.productos = productos;
    }
}
