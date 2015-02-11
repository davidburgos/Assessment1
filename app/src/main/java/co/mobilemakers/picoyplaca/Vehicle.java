package co.mobilemakers.picoyplaca;

/**
 * Created by DavidAlejandroBurgos on 10/02/2015.
 */
public class Vehicle {

    private enum Type_vehicle{
        CAR, TAXI, MOTORCYCLE, ELECTRIC;
    }

    private String Placa;
    private Type_vehicle type_vehicle;
    private Boolean permission;

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
