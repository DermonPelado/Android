package com.example.proyectofinalpro;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {

    /*atributos*/
    private int idProducto;
    private String Vendedor;
    private String Titulo;
    private String Descripcion;
    private String Precio;
    private String Anio;
    private String Materia;
    private String Pago;
    private String Estado;
    private String Foto;


    /*Constructor*/
    public Producto(int idProducto, String titulo, String precio, String foto) { //constructor de todos los productos
        this.idProducto = idProducto;
        Titulo = titulo;
        Precio = precio;
        Foto = foto;

    }

    public Producto(int idProducto, String vendedor, String titulo, String descripcion, String precio, String anio, String materia, String foto) { //constructor de productoxid
        this.idProducto = idProducto;
        Vendedor = vendedor;
        Titulo = titulo;
        Descripcion = descripcion;
        Precio = precio;
        Anio = anio;
        Materia = materia;
        Foto = foto;
    }

    /*Getter y setter*/

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getAnio() {
        return Anio;
    }

    public void setAnio(String anio) {
        Anio = anio;
    }

    public String getMateria() {
        return Materia;
    }

    public void setMateria(String materia) {
        Materia = materia;
    }

    public String getPago() {
        return Pago;
    }

    public void setPago(String pago) {
        Pago = pago;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }




    /*Conversores*/
    @Override  //cuando el listview detecte que hay un objeto hace la conversion a titulo.
    public String toString() {

        return Titulo + "                     $" +Precio;
    }




    /*´Parcelable. Es para que se pueda mandar un objeto x intent*/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idProducto);
        dest.writeString(this.Titulo);
        dest.writeString(this.Descripcion);
        dest.writeString(this.Vendedor);
        dest.writeString(this.Anio);
        dest.writeString(this.Materia);
        dest.writeString(this.Pago);
        dest.writeString(this.Estado);
        dest.writeString(this.Foto);
    }

    protected Producto(Parcel in) {
        this.idProducto = in.readInt();
        this.Titulo = in.readString();
        this.Descripcion = in.readString();
        this.Vendedor = in.readString();
        this.Anio = in.readString();
        this.Materia = in.readString();
        this.Pago = in.readString();
        this.Estado = in.readString();
        this.Foto = in.readString();
    }

    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel source) {
            return new Producto(source);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
