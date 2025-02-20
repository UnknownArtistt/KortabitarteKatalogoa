package data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import model.User;
import model.Produktua;

@Database(entities = {User.class, Produktua.class}, version = 4, exportSchema = false)
public abstract class DBAccess extends RoomDatabase {

    private static volatile DBAccess INSTANCE;

    public abstract UserDAO getUserDAO();
    public abstract  ProduktuaDAO getProduktuaDAO();

    public static DBAccess obtainInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized ((DBAccess.class)) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    DBAccess.class, "kortabitartekatalogoa.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
