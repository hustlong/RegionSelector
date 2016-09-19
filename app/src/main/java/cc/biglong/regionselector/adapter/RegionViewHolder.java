package cc.biglong.regionselector.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cc.biglong.regionselector.R;

/**
 * Created by code on 9/19/16.
 */
public class RegionViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public TextView checked;
    public ViewGroup itemBtn;

    public RegionViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        textView = (TextView) itemView.findViewById(R.id.item_tv);
        checked = (TextView) itemView.findViewById(R.id.checked);
        itemBtn = (ViewGroup) itemView.findViewById(R.id.item_btn);
    }

}
