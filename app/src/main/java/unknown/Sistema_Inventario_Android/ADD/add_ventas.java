package unknown.Sistema_Inventario_Android.ADD;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import unknown.Sistema_Inventario_Android.ENTIDADES_TABLAS.vent;
import unknown.Sistema_Inventario_Android.TABLAS.ConexionSQLiteHelper;
import unknown.Sistema_Inventario_Android.R;
import unknown.Sistema_Inventario_Android.MAIN_OPTIONS.Ventas;
import unknown.Sistema_Inventario_Android.TABLAS.tab_client;
import unknown.Sistema_Inventario_Android.TABLAS.tab_inventario;
import unknown.Sistema_Inventario_Android.TABLAS.tab_vent;

public class add_ventas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView back;
    ListView lista;
    FloatingActionButton y;
    Spinner cliente,producto,cantidad,moneda;
    Button add;
    ArrayAdapter<String> adapterp;
    List<String> b = new ArrayList<String>();
    List<String> idProd = new ArrayList<>();

    int grade;
    int pos=0;
    ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
    public void onBackPressed(){
        backf();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ventas);
        grade = Integer.parseInt(getIntent().getExtras().get("grade").toString());
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                backf();
            }
        });
        cliente=(Spinner) findViewById(R.id.spinner_cliente);
        producto=(Spinner) findViewById(R.id.spinner_cproducto);
        cantidad=(Spinner) findViewById(R.id.spinner_cantidad);
        moneda=(Spinner) findViewById(R.id.spinner_moneda);

        producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    updatecant(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerData();
        lista= (ListView) findViewById(R.id.lista_productos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
                promptDialogdelet();
            }
        });
        adapterp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        add=(Button)  findViewById(R.id.add_producto);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SQLiteDatabase db=conn.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);
                c.moveToPosition(producto.getSelectedItemPosition());
                if(!checklist(c.getString(0),adapterp)){
                adapterp.add(c.getString(0)+"\n"+cantidad.getSelectedItem().toString()+"x"+(c.getString(2)));
                lista.setAdapter(adapterp);
                }else{
                    Toast.makeText(getApplicationContext(),"El item "+c.getString(0).toString()+" ya esta en la factura \nPara modificar el item seleccione el item en la lista",Toast.LENGTH_SHORT).show();
                }
                c.close();
                db.close();
            }
        });
        y = (FloatingActionButton) findViewById(R.id.b_add);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

    }
    private void promptDialogdelet() {
        final EditText edtText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Eliminar de la factura?");
        builder.setCancelable(false);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterp.remove(adapterp.getItem(pos));
                lista.setAdapter(adapterp);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
    public boolean checklist(String n,ArrayAdapter<String> s){
        int l= n.length();
        for (int i=0;i<s.getCount();i++){
            if(n.equals(s.getItem(i).toString().substring(0,l))){
                return true;
            }
        }
        return false;
    }
    private void updatecant(int position){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);

        if (checkdb(c)) {
            c.moveToPosition(position);
            for(int i=1;i<=Double.parseDouble(c.getString(2).toString());i++){
                b.add(""+i);
            }
            String[] myArray = new String[b.size()];
            b.toArray(myArray);
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, myArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cantidad.setAdapter(adapter);
            c.close();
            db.close();
            b.clear();
        }
    }
    private void SpinnerData(){
        List<String> a = new ArrayList<String>();
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                a.add(c.getString(0) + "  Precio: "+ c.getString(1));
            }while(c.moveToNext());
            String[] myArray = new String[a.size()];
            a.toArray(myArray);
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, myArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            producto.setAdapter(adapter);
            c.close();
            db.close();
            a.clear();
        }

        conn=new ConexionSQLiteHelper(this, tab_client.TABLA_CLIENTE,null,1);
        db=conn.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM "+ tab_client.TABLA_CLIENTE+" ORDER BY " + tab_client.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                a.add(c.getString(0));
            }while(c.moveToNext());
            String[] myArray = new String[a.size()];
            a.toArray(myArray);
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, myArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cliente.setAdapter(adapter);
            c.close();
            db.close();
            a.clear();
        }
    }
    private boolean isExist(String n){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_inventario.TABLA_INVENTARIO,null,1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ tab_inventario.TABLA_INVENTARIO+" ORDER BY " + tab_inventario.CAMPO_NOMBRE + " asc",null);
        if (checkdb(c)) {
            c.moveToFirst();
            do{
                if(n==c.getString(0) || n.equals(c.getString(0)) ) {
                    db.close();
                    return true;
                }
            }while(c.moveToNext());
            db.close();
            return false;

        } else{
            c.close();
            db.close();
            return false;
        }
    }
    public Boolean checkdb(Cursor c){
        Boolean rowExists;

        if (c.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }
    private void backf(){
        Intent intent = new Intent (getApplicationContext(), Ventas.class);
        intent.putExtra("grade",grade);
        startActivityForResult(intent,1);
        finish();
    }
    private boolean isInt(String numero){
        try {
            int num = Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isDob(String numero){
        try {
            double num = Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isStr(String ed_text){
        if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void registrar() {
        //cliente,producto,cantidad,moneda
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, tab_vent.TABLA_VENTA,null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        //values.put(tab_vent.CAMPO_COMPRADOR,cliente.getSelectedItem().toString());
        //values.put(tab_vent.CAMPO_PRODUCTOS,producto.getSelectedItem().toString());

        int index;
        int count = lista.getAdapter().getCount();
        Object object;
        char id_producto;
        String value;

        Cursor c = db.rawQuery("SELECT "+ tab_vent.ID_VENTAS+" FROM "+ tab_vent.TABLA_VENTA+" ORDER BY " + tab_vent.ID_VENTAS+ " asc",null);

        String Row="0";

        if (checkdb(c)) {
            c.moveToLast();
            Row=""+(Integer.parseInt(c.getString(0))+1);
        }

        for (index = 0; index < count; index++ ) {

            object =  lista.getAdapter().getItem(index);
            value = object.toString();
            id_producto = value.charAt (0);

            values.put(tab_vent.ID_VENTAS,Row);
            values.put(tab_vent.CAMPO_COMPRADOR,cliente.getSelectedItem().toString());
            values.put(tab_vent.CAMPO_PRODUCTOS, Character.toString(id_producto));

            db.insert(tab_vent.TABLA_VENTA, tab_vent.ID_VENTAS,values);
        }

/*
        int count = lista.getAdapter().getCount();
        System.out.print(count +"contadoooor");

        int index;
        String value = "";

        Object object;
        char id_producto;
        for (index = 0; index < count ; index++) {
            object =  lista.getAdapter().getItem(index);
            System.out.print(object);

            value = object.toString();
            id_producto = value.charAt (0);
        }

*/

        c = db.rawQuery("SELECT * FROM "+ tab_vent.TABLA_VENTA,null);
        c.moveToFirst();
        Toast.makeText(getApplicationContext(),"Venta Procesada",Toast.LENGTH_SHORT).show();
        c.close();
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
