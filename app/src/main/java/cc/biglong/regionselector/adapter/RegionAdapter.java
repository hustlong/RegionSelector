package cc.biglong.regionselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.biglong.regionselector.R;

/**
 * Created by code on 9/19/16.
 */
public class RegionAdapter extends RecyclerView.Adapter<RegionViewHolder> {

    private List<String> itemList;
    private String checkedStr;
    private Context context;
    private LayoutInflater layoutInflater;

    private OnItemClickListener listener;


    public RegionAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<String> itemList,String checkedStr) {
        this.itemList = itemList;
        this.checkedStr = checkedStr;
        notifyDataSetChanged();
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RegionViewHolder(layoutInflater.inflate(R.layout.item_region_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final RegionViewHolder holder, final int position) {
        holder.textView.setText(itemList.get(position));
        holder.checked.setVisibility(checkedStr.equals(itemList.get(position))? View.VISIBLE:View.GONE);
        holder.itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(holder,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return isEmpty(itemList) ? 0 : itemList.size();
    }


    private  <D> boolean isEmpty(List<D> list) {
        return (list == null || list.isEmpty());
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(RegionViewHolder holder, int position);
    }


}
