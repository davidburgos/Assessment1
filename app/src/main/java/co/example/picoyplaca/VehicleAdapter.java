package co.example.picoyplaca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    List<Vehicle> VehicleList;

    public class ViewHolder{
        public final TextView textViewType;
        public final TextView textViewPlaca;
        public final TextView textViewHour;
        public final TextView textViewMessage;
        public final ImageView ImageViewType;

        public ViewHolder(View view){
            textViewType    = (TextView) view.findViewById(R.id.text_view_value_type);
            textViewPlaca   = (TextView) view.findViewById(R.id.text_view_value_placa);
            textViewHour    = (TextView) view.findViewById(R.id.text_view_value_hour);
            textViewMessage = (TextView) view.findViewById(R.id.text_view_value_message);
            ImageViewType   = (ImageView)view.findViewById(R.id.image_view_type);
        }
    }

    public VehicleAdapter(Context context, List<Vehicle> vehicles) {
        super(context, R.layout.vehicle_list_item_entry,vehicles);
        VehicleList = vehicles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = reuseOrGenerateRowView(convertView, parent);
        displayRepoInRow(position, rowView);
        return rowView;
    }

    private void displayRepoInRow(int position, View rowView) {
        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        viewHolder.textViewType.setText(VehicleList.get(position).getType_vehicle());
        viewHolder.textViewPlaca.setText(VehicleList.get(position).getPlaca());
        if(VehicleList.get(position).getPermission()) {
            viewHolder.textViewMessage.setText(getContext().getString(R.string.message_for_warning));
        }else {
            viewHolder.textViewMessage.setText(null);
        }
        viewHolder.textViewHour.setText(VehicleList.get(position).getSchedule());
        viewHolder.ImageViewType.setImageResource(VehicleList.get(position).getImageId());
        viewHolder.ImageViewType.setId(VehicleList.get(position).getId());
    }

    private View reuseOrGenerateRowView(View convertView, ViewGroup parent) {
        View rowView;
        if(convertView != null){
            rowView = convertView;
        }else{
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.vehicle_list_item_entry, parent, false);
            ViewHolder viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        return rowView;
    }
}
