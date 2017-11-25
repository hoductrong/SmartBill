package banhang.smartbill.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import banhang.smartbill.Entity.NavigationMenuEntity;
import banhang.smartbill.R;

/**
 * Created by KARATA on 25/11/2017.
 */

public class NavigationMenuAdapter extends ArrayAdapter<NavigationMenuEntity> {
    private int resourceId;
    private List<NavigationMenuEntity> data;
    public NavigationMenuAdapter(@NonNull Context context, int resource, @NonNull List<NavigationMenuEntity> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resourceId,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.imv_icon = (ImageView)view.findViewById(R.id.imv_icon);
            viewHolder.txv_menu_name = (TextView)view.findViewById(R.id.txv_menu_name);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        NavigationMenuEntity entity = this.data.get(position);
        viewHolder.imv_icon.setImageResource(entity.getIcon());
        viewHolder.txv_menu_name.setText(entity.getName());
        return view;
    }

    static class ViewHolder {
        ImageView imv_icon;
        TextView txv_menu_name;
    }


}
