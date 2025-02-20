package model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "produktua", indices = @Index(value = {"id"}, unique = true))
public class Produktua implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;
    String izena;
    String ekoizlea;
    String deskripzioa;
    double prezioa;
    int imagePath;

    public Produktua(int id, String izena, String ekoizlea, String deskripzioa, double prezioa, int imagePath) {
        this.id = id;
        this.izena = izena;
        this.ekoizlea = ekoizlea;
        this.deskripzioa = deskripzioa;
        this.prezioa = prezioa;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getEkoizlea() {
        return ekoizlea;
    }

    public void setEkoizlea(String ekoizlea) {
        this.ekoizlea = ekoizlea;
    }

    public String getDeskripzioa() {
        return deskripzioa;
    }

    public void setDeskripzioa(String deskripzioa) {
        this.deskripzioa = deskripzioa;
    }

    public double getPrezioa() {
        return prezioa;
    }

    public void setPrezioa(double prezioa) {
        this.prezioa = prezioa;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Produktua{" +
                "id=" + id +
                ", izena='" + izena + '\'' +
                ", ekoizlea='" + ekoizlea + '\'' +
                ", deskripzioa='" + deskripzioa + '\'' +
                ", prezioa=" + prezioa +
                ", imagePath=" + imagePath +
                '}';
    }
}
