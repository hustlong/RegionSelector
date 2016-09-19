package cc.biglong.regionselector;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_REGION = 520;

    private ViewGroup regionBtn;
    private TextView regionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        regionBtn = (ViewGroup) findViewById(R.id.item_region);
        regionTv = (TextView) findViewById(R.id.tv_region);

        regionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_region:
                startActivityForResult(new Intent(this,RegionActivity.class)
                        .putExtra("region",regionTv.getText().toString())
                        , REQUEST_CODE_REGION );
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED && resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_REGION && data != null) {
                regionTv.setText(data.getStringExtra("result"));
            }
        }
    }
}
