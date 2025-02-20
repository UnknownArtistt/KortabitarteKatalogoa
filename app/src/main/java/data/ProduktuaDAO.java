package data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import model.Produktua;

@Dao
public interface ProduktuaDAO {

    @Query("SELECT * FROM produktua")
    LiveData<List<Produktua>> getProducts();

    @Query("SELECT * FROM produktua")
    List<Produktua> getProductsSync();

    @Query("SELECT * FROM produktua WHERE izena = :izena")
    Produktua produktuInfo(String izena);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Produktua produktua);

    @Update
    void update(Produktua trophy);

    @Delete
    void delete(Produktua trophy);
}
