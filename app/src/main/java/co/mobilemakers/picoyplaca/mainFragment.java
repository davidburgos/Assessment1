package co.mobilemakers.picoyplaca;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class mainFragment extends ListFragment {

    private DatabaseHelper mDBHelper = null;
    VehicleAdapter mAdapter;
    List<Vehicle> mEntries;

    public mainFragment() {
    }

    private DatabaseHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareListView();
    }

    private void prepareListView() {
        mEntries = getDBHelper().retrieveAllVehicles();
        mAdapter = new VehicleAdapter(getActivity(), mEntries);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Edit task in next version!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_vehicle_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Boolean handled = false;

        switch (item.getItemId()){
            case R.id.action_add_vehicle:
                Vehicle vehicle =new Vehicle();
                vehicle.setPlaca("CLA-024");
                vehicle.setType_vehicle(Vehicle.Type_vehicle.MOTORCYCLE);
                vehicle.setPermission(false);
                getDBHelper().saveVehicle(vehicle);
                mEntries.add(vehicle);
                mAdapter.notifyDataSetChanged();
                handled = true;
                break;
        }
        if(!handled){
            handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

}
