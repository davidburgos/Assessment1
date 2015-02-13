package co.mobilemakers.picoyplaca;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;

@DatabaseTable
public class Vehicle {

    public final static String ID = "_id";
    public final static String PLACA = "placa";
    public final static String TYPE = "type";
    public final static String PERMISSION = "permission";

    public enum Type_vehicle {
        //  CAR(1), TAXI(2), MOTORCYCLE(3), ELECTRIC(4);
        CAR(R.id.menu_car), TAXI(R.id.menu_taxi), MOTORCYCLE(R.id.menu_motorcycle), ELECTRIC(R.id.menu_electric_car);
        private int value;

        private Type_vehicle(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum Day {
        Monday(8, 9, 0, 1), Tuesday(2, 3, 4, 5), Wednesday(6, 7, 8, 9), Thursday(0, 1, 2, 3), Friday(4, 5, 6, 7);

        private String value;

        private Day(int val1, int val2, int val3, int val4) {
            this.value = String.valueOf(val1) +
                    String.valueOf(val2) +
                    String.valueOf(val3) +
                    String.valueOf(val4);
        }

        public Boolean hasPenalty(CharSequence placa) {
            return value.contains(placa);
        }
    }

    @DatabaseField(generatedId = true,
            columnName = ID)
    private int _id;
    @DatabaseField(columnName = PLACA)
    private String Placa;
    //@DatabaseField(dataType = DataType.ENUM_INTEGER)
    @DatabaseField(unknownEnumName = "CAR",
            columnName = TYPE)
    private Type_vehicle type_vehicle;
    @DatabaseField(columnName = PERMISSION)
    private Boolean permission;

    public Vehicle() {
        permission = false;
    }

    public String getPlaca() {
        return Placa.trim();
    }

    public void setPlaca(String placa) {
        Placa = placa.trim();
    }

    public String getType_vehicle() {
        return type_vehicle.toString();
    }

    public int getType() {
        return type_vehicle.value;
    }

    public void setType_vehicle(int type_vehicle) {
        switch (type_vehicle){
            case R.id.menu_car:
                this.type_vehicle = Type_vehicle.CAR;
                break;
            case R.id.menu_taxi:
                this.type_vehicle = Type_vehicle.TAXI;
                break;
            case R.id.menu_motorcycle:
                this.type_vehicle = Type_vehicle.MOTORCYCLE;
                break;
            case R.id.menu_electric_car:
                this.type_vehicle = Type_vehicle.ELECTRIC;
                break;
        }
    }

    public Boolean getPermission() {

        Calendar c = Calendar.getInstance();
        c.setTime(c.getTime());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String LastNumber = Placa.substring(Placa.length()-1);

        switch (dayOfWeek){

            case 2:
                permission = Day.Monday.hasPenalty(LastNumber);
                break;
            case 3:
                permission = Day.Tuesday.hasPenalty(LastNumber);
                break;
            case 4:
                permission = Day.Wednesday.hasPenalty(LastNumber);
                break;
            case 5:
                permission = Day.Thursday.hasPenalty(LastNumber);
                break;
            case 6:
                permission = Day.Friday.hasPenalty(LastNumber);
                break;
        }

        return permission;
    }

    public int getId() {
        return _id;
    }

    public int getImageId(){
        int result = R.drawable.car;
        switch (type_vehicle){
            case CAR:
                result = R.drawable.car;
                break;
            case TAXI:
                result = R.drawable.taxi;
                break;
            case MOTORCYCLE:
                result = R.drawable.bike;
                break;
            case ELECTRIC:
                result = R.drawable.electric_car;
                break;
        }
        return result;
    }

    public String getSchedule(){

        String schedule = String.valueOf(R.string.text_title_without_restriction);

        if(permission) {
            schedule = "(7:00 a.m. - 8:30 a.m.) (5:30 p.m. - 7:00 p.m.)";
        }
        return schedule;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "Placa='" + Placa + '\'' +
                ", type_vehicle=" + type_vehicle +
                ", permission='" + permission + '\'' +
                '}';
    }
}
