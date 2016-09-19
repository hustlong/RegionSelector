package cc.biglong.regionselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import cc.biglong.regionselector.adapter.RegionAdapter;
import cc.biglong.regionselector.adapter.RegionViewHolder;
import cc.biglong.regionselector.bean.City;
import cc.biglong.regionselector.bean.District;
import cc.biglong.regionselector.bean.Result;
import cc.biglong.regionselector.bean.Root;
import cc.biglong.regionselector.utils.StreamUtils;

/**
 * Created by code on 9/19/16.
 */
public class RegionActivity extends AppCompatActivity implements RegionAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RegionAdapter adapter;

    private String region,province,city,district;


    private List<Result> result_List;
    private List<City> city_List;
    private List<District> district_List;

    private List<String> itemList,provinces,cities;

    private int curPage;

    @Override
    protected void onCreate(Bundle ins) {
        super.onCreate(ins);
        setContentView(R.layout.activity_region);
        Intent intent = getIntent();
        if (intent != null) {
            region = intent.getStringExtra("region");
            String[] tmp = region.split(" ");
            province = tmp[0];
            city = tmp[1];
            district = tmp[2];
            Log.d("TAG","[当前操作]province=" + province);
            Log.d("TAG","[当前操作]city=" + city);
            Log.d("TAG","[当前操作]district=" + district);
        }
        itemList = new ArrayList<>();
        provinces = new ArrayList<>();
        cities = new ArrayList<>();

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RegionAdapter(this);
        adapter.setListener(this);

        recyclerView.setAdapter(adapter);
    }


    private static final int PROVINCE = 0;
    private static final int CITY = 1;
    private static final int DISTRICT = 2;
    @Override
    public void onItemClick(RegionViewHolder holder, int position) {
        switch (curPage) {

            case PROVINCE:
                province = itemList.get(position);
                for (Result result : result_List) {
                    if (province.equals(result.getProvince()))
                        city_List = result.getCity();
                }
                provinces.clear();
                provinces.addAll(itemList);
                itemList.clear();
                for (City city : city_List) {
                    if (this.city.equals(city.getCity()))
                        itemList.add(0,city.getCity());
                    else
                        itemList.add(city.getCity());
                }
                adapter.setData(itemList,city);
                curPage++;
                break;

            case CITY:
                city = itemList.get(position);
                for (City city : city_List) {
                    if (this.city.equals(city.getCity()))
                        district_List = city.getDistrict();
                }
                cities.clear();
                cities.addAll(itemList);
                itemList.clear();
                for (District district : district_List) {
                    if (this.district.equals(district.getDistrict()))
                        itemList.add(0,district.getDistrict());
                    else
                        itemList.add(district.getDistrict());
                }
                adapter.setData(itemList,district);
                curPage++;
                break;

            case DISTRICT:
                district = itemList.get(position);
                setResult(RESULT_OK,new Intent().putExtra("result"
                        ,province + " " + city + " " + district));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        switch (curPage) {
            case PROVINCE:
                finish();
                break;

            case CITY:
                itemList.clear();
                itemList.addAll(provinces);
                adapter.setData(itemList,province);
                curPage--;
                break;

            case DISTRICT:
                itemList.clear();
                itemList.addAll(cities);
                adapter.setData(itemList,city);
                curPage--;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (result_List == null) {
                Gson gson = new Gson();
                Root root = gson.fromJson(StreamUtils.get(this,R.raw.city), Root.class);
                result_List = root.getResult();
            }

            for (Result result : result_List) {
                if (province.equals(result.getProvince()))
                    itemList.add(0,result.getProvince());
                else
                    itemList.add(result.getProvince());
            }
            adapter.setData(itemList,province);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

}
