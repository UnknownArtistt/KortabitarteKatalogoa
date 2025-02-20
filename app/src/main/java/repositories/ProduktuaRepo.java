package repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.kortabitarteupategiak.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import data.DBAccess;

import data.ProduktuaDAO;
import model.Produktua;

public class ProduktuaRepo {

    ProduktuaDAO produktuaDAO;
    Executor executor = Executors.newSingleThreadExecutor();
    List<Produktua> produktuak = new ArrayList<>();

    private void KatalogoaSortu() {
        produktuak.add(new Produktua(1, "Don Simon Premium", "Don Simon", "Vino accesible y con extra de dolor de tripas, ideal para Kalimotxos", 0.80, R.drawable.donsi));
        produktuak.add(new Produktua(2, "Gran Duque Premium", "Gran Duque", "Vino accesible y con extra de dolor de tripas, ideal para Kalimotxos", 0.60, R.drawable.granduque));
        produktuak.add(new Produktua(3, "Monteabellon Crianza", "Monteabellon", "Vino de calidad, 14 meses en barrica", 17.50, R.drawable.monteabellon));
        produktuak.add(new Produktua(4, "Luis Cañas del año", "Bodegas Luis Cañas", "Vino del año con buena relación calidad precio", 6.20, R.drawable.luiscana));
        produktuak.add(new Produktua(5, "Pack de tintos Protos", "Protos", "Pack de vino tintos Protos, ideal para una cena elegante", 37.80, R.drawable.protos));
        produktuak.add(new Produktua(6, "Emilio Moro tempranillo", "Emilio Moro", "Vino de alta calidad reposado en barrica de roble", 21.0, R.drawable.emiliomoro));
        produktuak.add(new Produktua(7, "Pack Señorio de Nava", "Señorio de Nava", "Pack degustacion de diferentes variedades", 29.99, R.drawable.senorio));
        produktuak.add(new Produktua(8, "Bardos Verdejo Rueda", "Bardos", "Vino fresco y de buena calidad, ideal para maridajes", 7.50, R.drawable.bardos));
    }

    public ProduktuaRepo(Application application) {
        produktuaDAO = DBAccess.obtainInstance(application).getProduktuaDAO();

        executor.execute(() -> {
            if (produktuaDAO.getProductsSync().isEmpty()) {
                KatalogoaSortu();
                for (Produktua prd : produktuak) {
                    this.insert(prd);
                }
            }
        });
    }

    public Produktua detaileaLortu(String izena) {
        return produktuaDAO.produktuInfo(izena);
    }

    public LiveData<List<Produktua>> get() {
        return produktuaDAO.getProducts();
    }

    public void insert(Produktua produktua) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                produktuaDAO.insert(produktua);
            }
        });
    }

    public void update(Produktua produktua) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                produktuaDAO.update(produktua);
            }
        });
    }

    public void delete(Produktua produktua) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                produktuaDAO.delete(produktua);
            }
        });
    }
}
