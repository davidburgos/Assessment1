package co.mobilemakers.picoyplaca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class mainFragment extends ListFragment {

    public static final int CREATE_REQUEST = 314;
    private DatabaseHelper mDBHelper = null;
    SharedPreferences mSharedpreferences;
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textViewCity = (TextView)rootView.findViewById(R.id.text_view_title_city);
        mSharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Resources res = getResources();
        String[] cities = res.getStringArray(R.array.listentries);
        int selectedCity = Integer.parseInt(mSharedpreferences.getString("cities", "0"));
        String message = String.format(getString(R.string.text_view_format_title)
                ,cities[selectedCity]);
        textViewCity.setText(message);
        return rootView;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(CREATE_REQUEST == requestCode){
            switch (resultCode){
                case Activity.RESULT_OK:
                    Vehicle vehicle = new Vehicle();
                    vehicle.setPlaca(data.getStringExtra(CreateVehicle.FIELD_PLACA));
                    vehicle.setType_vehicle(data.getIntExtra(CreateVehicle.FIELD_TYPE,1));
                    getDBHelper().saveVehicle(vehicle);
                    mEntries.add(vehicle);
                    mAdapter.notifyDataSetChanged();
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
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
                Intent createVehicle = new Intent(getActivity(), CreateVehicle.class);
                startActivityForResult(createVehicle, CREATE_REQUEST);
                handled = true;
                break;
        }
        if(!handled){
            handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

}
