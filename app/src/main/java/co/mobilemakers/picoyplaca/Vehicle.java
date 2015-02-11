package co.mobilemakers.picoyplaca;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Vehicle {

    public final static String ID         = "_id";
    public final static String PLACA      = "placa";
    public final static String TYPE       = "type";
    public final static String PERMISSION = "permission";

    public enum Type_vehicle{
        CAR, TAXI, MOTORCYCLE, ELECTRIC
    }

    @DatabaseField(generatedId = true,
                    columnName = ID)    private int _id;
    @DatabaseField(columnName  = PLACA) private String Placa;
    //@DatabaseField(dataType = DataType.ENUM_INTEGER)
    @DatabaseField(unknownEnumName = "CAR",
                        columnName = TYPE)  private Type_vehicle type_vehicle;
    @DatabaseField(columnName = PERMISSION) private Boolean permission;

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getType_vehicle() {
        return type_vehicle.toString();
    }

    public void setType_vehicle(Type_vehicle type_vehicle) {
        this.type_vehicle = type_vehicle;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "Placa='" + Placa + '\'' +
                ", type_vehicle=" + type_vehicle +
                ", permission='" + permission + '\'' +
                '}';
    }
}
