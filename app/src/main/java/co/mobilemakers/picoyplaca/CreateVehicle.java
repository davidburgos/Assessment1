package co.mobilemakers.picoyplaca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateVehicle extends ActionBarActivity {

    private EditText mPlaca;
    private Button mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        mType = (Button)findViewById(R.id.button_type);
        mType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.vehicle_types, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Boolean handled = false;
                switch(item.getItemId())
                {
                    case R.id.menu_car:
                        mType.setText(R.string.type_car);
                        handled = true;
                        break;
                    case R.id.menu_electric_car:
                        mType.setText(R.string.type_electric_car);
                        handled = true;
                        break;
                    case R.id.menu_taxi:
                        mType.setText(R.string.type_taxi);
                        handled = true;
                        break;
                    case R.id.menu_motorcycle:
                        mType.setText(R.string.type_motorcycle);
                        handled = true;
                        break;
                    default:
                        handled = false;
                }

                return handled;
            }
        });
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_vehicle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
