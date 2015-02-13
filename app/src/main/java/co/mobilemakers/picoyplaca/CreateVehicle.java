package co.mobilemakers.picoyplaca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateVehicle extends ActionBarActivity {

    public final static String FORM_KEY = "PARCEABLE_ARRAY_LIST";
    public static final String FIELD_PLACA = "_placa";
    public static final String FIELD_TYPE  = "_type";
    public static final String FIELD_ID    = "_id";
    public static final String CRUD_TYPE   = "CRUD";
    private EditText mPlaca;
    private Button mType,mButtonCreate, mButtonDelete;
    private int Type,_id;
    Boolean TextReady =false;
    Boolean TypeReady =false;
    Matcher matcher;
    static ArrayList<FormState> FormStateList = new ArrayList<>();

    private static class FormState implements Parcelable {

        public static Creator<FormState> CREATOR = new Creator<FormState>() {
            @Override
            public FormState createFromParcel(Parcel source) {
                return new FormState(source);
            }

            @Override
            public FormState[] newArray(int size) {
                return new FormState[size];
            }
        };

        private String mPlaca;
        private int mType;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mPlaca);
            dest.writeInt(mType);
        }

        FormState(){

        }

        FormState(Parcel source){
            mPlaca = (source.readString());
            mType = (source.readInt());
        }

        public String getPlaca() {
            return this.mPlaca;
        }
        public void setPlaca(String placa) {
            this.mPlaca = placa;
        }

        public int getType() {
            return this.mType;
        }
        public void setType(int type) {
            this.mType = type;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveNewFormState();
        outState.putParcelableArrayList(FORM_KEY, FormStateList);
    }

    private void saveNewFormState() {

        FormState formState = new FormState();

        formState.setPlaca(mPlaca.getText().toString());
        formState.setType(Type);

        FormStateList.add(formState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        wireUpViews();
        setUpListeners();

        Intent temp_intent = getIntent();
        if(temp_intent != null){
            _id = temp_intent.getIntExtra(FIELD_ID, -1);
            if(_id > 0){
                mPlaca.setText(temp_intent.getStringExtra(FIELD_PLACA));
                setTypeText(temp_intent.getIntExtra(FIELD_TYPE, R.id.menu_car));
                mButtonCreate.setText(getString(R.string.button_text_title_update));
                mButtonDelete.setVisibility(View.VISIBLE);
            }
        }

        if(savedInstanceState != null){
            FormStateList = savedInstanceState.getParcelableArrayList(FORM_KEY);
            FormState formState = FormStateList.get(FormStateList.size()-1);

            if(formState != null){
                mPlaca.setText(formState.getPlaca());
                Type = formState.getType();
                setTypeText(Type);
            }
        }
    }

    private void setUpListeners() {

        mType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        final Pattern pattern = Pattern.compile("[A-Z|a-z]{3}-?[0-9]{3}");

        mButtonCreate.setEnabled(false);
        mButtonDelete.setVisibility(View.GONE);

        mPlaca.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matcher = pattern.matcher(s);
                TextReady = matcher.matches();
                mButtonCreate.setEnabled(TextReady);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(FIELD_PLACA, mPlaca.getText().toString());
                intent.putExtra(FIELD_TYPE, Type);
                intent.putExtra(FIELD_ID, _id);
                intent.putExtra(CRUD_TYPE, 0);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                if(_id > 0) {
                    intent.putExtra(FIELD_PLACA, mPlaca.getText().toString());
                    intent.putExtra(FIELD_TYPE, Type);
                    intent.putExtra(FIELD_ID, _id);
                    intent.putExtra(CRUD_TYPE, 1);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    if (TypeReady && TextReady) {
                        intent.putExtra(FIELD_PLACA, mPlaca.getText().toString());
                        intent.putExtra(FIELD_TYPE, Type);
                        intent.putExtra(CRUD_TYPE, 1);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else
                        Toast.makeText(getBaseContext(), getString(R.string.select_all_fields_msg), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void wireUpViews() {
        mType = (Button)findViewById(R.id.button_type);
        mButtonCreate = (Button)findViewById(R.id.button_create);
        mButtonDelete = (Button)findViewById(R.id.button_delete);
        mPlaca = (EditText)findViewById(R.id.edit_text_placa);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.vehicle_types, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Boolean handled;
                handled = setTypeText(item.getItemId());
                return handled;
            }
        });
        popup.show();
    }

    private Boolean setTypeText(int item) {
        Boolean handled;
        switch(item)
        {
            case R.id.menu_car:
                mType.setText(R.string.type_car);
                Type = Vehicle.Type_vehicle.CAR.getValue();
//                Type = R.id.menu_car;
                handled = true;
                break;
            case R.id.menu_electric_car:
                mType.setText(R.string.type_electric_car);
                Type = Vehicle.Type_vehicle.ELECTRIC.getValue();
//                Type = R.id.menu_electric_car;
                handled = true;
                break;
            case R.id.menu_taxi:
                mType.setText(R.string.type_taxi);
                Type = Vehicle.Type_vehicle.TAXI.getValue();
//                Type = R.id.menu_taxi;
                handled = true;
                break;
            case R.id.menu_motorcycle:
                mType.setText(R.string.type_motorcycle);
                Type = Vehicle.Type_vehicle.MOTORCYCLE.getValue();
//                Type = R.id.menu_motorcycle;
                handled = true;
                break;
            default:
                Type = item;
                mType.setText(null);
                handled = false;
        }
        TypeReady=handled;
        return handled;
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
