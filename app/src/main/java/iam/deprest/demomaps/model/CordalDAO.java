package iam.deprest.demomaps.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CordalDAO {
    //SINGLETON
    private static final CordalDAO ourInstance = new CordalDAO();
    public static CordalDAO getInstance() {
        return ourInstance;
    }
    private CordalDAO() {
    }
    //EIGEN CODE
    ArrayList<Cordal> kunstwerkenList;

    public ArrayList<Cordal> getKunstwerkenList() {
        kunstwerkenList = new ArrayList<>();
        kunstwerkenList.add(new Cordal(new LatLng(50.848874,4.349427), "Zittende man met koffer", Cordal.Review.MOOI));
        kunstwerkenList.add(new Cordal(new LatLng(50.848665, 4.349755), "Koortdanser met koffer", Cordal.Review.MINDER_MOOI));
        kunstwerkenList.add(new Cordal(new LatLng(50.847677, 4.350758), "Man in pak aan balkon", Cordal.Review.MOOIST));
        kunstwerkenList.add(new Cordal(new LatLng(50.847358, 4.34946), "Gebukte man op randje met koffer", Cordal.Review.NIET_MOOI));
        kunstwerkenList.add(new Cordal(new LatLng(50.846139, 4.349625), "Man met oranje deken op balkonnetje", Cordal.Review.LELIJK));
        return kunstwerkenList;
    }
}
