package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import model.Produktua;
import repositories.ProduktuaRepo;

public class ProduktuaViewModel extends AndroidViewModel {

    ProduktuaRepo produktuaRepo;
    MutableLiveData<Produktua> productSelected;

    public ProduktuaViewModel(@NonNull Application application) {
        super(application);
        produktuaRepo = new ProduktuaRepo(application);
    }

    public LiveData<List<Produktua>> get() {
        return produktuaRepo.get();
    }

    public Produktua produktuDetailea(String izena) {
        return produktuaRepo.detaileaLortu(izena);
    }

    public void add(Produktua produktua) {
        produktuaRepo.insert(produktua);
    }

    void delete(Produktua produktua) {
        produktuaRepo.delete(produktua);
    }

    void update(Produktua produktua) {
        produktuaRepo.update(produktua);
    }

    void select(Produktua produktua) {
        productSelected.setValue(produktua);
    }

    MutableLiveData<Produktua> selected() {
        return productSelected;
    }
}
