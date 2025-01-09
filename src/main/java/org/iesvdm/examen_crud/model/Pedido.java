package org.iesvdm.examen_crud.model;

import java.sql.Date;
import java.util.Objects;

public class Pedido {
    private int id;
    private double total;
    private Date fecha;
    private int id_cliente;
    private int id_comercial;
    private String NombreCliente;
    private String nombreComercial;



    public Pedido(){}

    public Pedido(int id, double total, Date fecha, int id_cliente, int id_comercial, String nombreCliente, String nombreComercial) {
        this.id = id;
        this.total = total;
        this.fecha = fecha;
        this.id_cliente = id_cliente;
        this.id_comercial = id_comercial;
        this.NombreCliente = nombreCliente;
        this.nombreComercial = nombreComercial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_comercial() {
        return id_comercial;
    }

    public void setId_comercial(int id_comercial) {
        this.id_comercial = id_comercial;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return id == pedido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", total=" + total +
                ", fecha=" + fecha +
                ", id_cliente=" + id_cliente +
                ", id_comercial=" + id_comercial +
                ", NombreCliente='" + NombreCliente + '\'' +
                ", nombreComercial='" + nombreComercial + '\'' +
                '}';
    }
}
