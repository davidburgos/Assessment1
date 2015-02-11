package co.mobilemakers.picoyplaca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final static String LOG_TAG = DatabaseHelper.class.getSimpleName();
    public  final static String DATABASE_NAME ="vehicles.db";
    private final static int DATABASE_VERSION = 1;
    private Dao<Vehicle,Integer> mContactDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<Vehicle,Integer> getContactDao() throws SQLException {
        if(mContactDao == null){
            mContactDao = getDao(Vehicle.class);
        }
        return mContactDao;
    }

    public void saveVehicle(Vehicle vehicle){
        try{
            if(vehicle!=null){
                Dao<Vehicle, Integer> dao = getContactDao();
                dao.create(vehicle);
            }
        }catch (SQLException e){
            Log.e(LOG_TAG, "Can´t insert Contact into database "+DatabaseHelper.DATABASE_NAME, e);
        }
    }

    public Vehicle getVehicleById(int Id){
        Vehicle vehicle = null;
        Dao<Vehicle, Integer> dao = null;
        try {
            dao = getContactDao();
            vehicle = dao.queryForId(Id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicle;
    }

    public void deleteVehicle(Vehicle vehicle){
        try{
            if(vehicle!=null){
                Dao<Vehicle, Integer> dao = getContactDao();
                dao.delete(vehicle);
            }
        }catch (SQLException e){
            Log.e(LOG_TAG, "Can´t insert Contact into database "+DatabaseHelper.DATABASE_NAME, e);
        }
    }

    public List<Vehicle> retrieveAllVehicles() {
        List<Vehicle> contacts = null;

        try {
            Dao<Vehicle,Integer> contactDao = getContactDao();
            contacts = contactDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Vehicle.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed to create database " + DATABASE_NAME, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @Override
    public void close() {
        super.close();
        mContactDao = null;
    }
}
