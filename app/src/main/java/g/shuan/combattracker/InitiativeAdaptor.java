package g.shuan.combattracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class InitiativeAdaptor extends BaseAdapter {

    private ArrayList<Encounter> enc;
    private LayoutInflater thisInflator;

    public InitiativeAdaptor(Context con, ArrayList<Encounter> encounter){
        this.enc = encounter;
        this.thisInflator = (LayoutInflater.from(con));
    }


    @Override
    public int getCount() {
        return enc.size();
    }

    @Override
    public Object getItem(int i) {
        return enc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getValue(int i){
        return -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = thisInflator.inflate(R.layout.initiative_list_layout, viewGroup, false);

        }
        return view;
    }
}
