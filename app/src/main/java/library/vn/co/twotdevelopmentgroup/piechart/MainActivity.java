package library.vn.co.twotdevelopmentgroup.piechart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final static String urlGoogleChart
            = "http://chart.apis.google.com/chart";
    final static String urlp3Api
            = "?cht=p3&chs=300x150";


    EditText inputA, inputB, inputC;
    TextView labelA,labelB,labelC;
    Button generate;
    WebView pieChart;
    public String[] labelList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        labelList = getResources().getStringArray(R.array.chart_label);

        inputA = (EditText) findViewById(R.id.adata);
        inputB = (EditText) findViewById(R.id.bdata);
        inputC = (EditText) findViewById(R.id.cdata);
        labelA = (TextView) findViewById(R.id.part1);
        labelB = (TextView) findViewById(R.id.part2);
        labelC = (TextView) findViewById(R.id.part3);
        generate = (Button) findViewById(R.id.generate);
        pieChart = (WebView) findViewById(R.id.pie);
        generate.setOnClickListener(generateOnClickListener);

        labelA.setText(labelList[0].toString());
        labelB.setText(labelList[1].toString());
        labelC.setText(labelList[2].toString());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    Button.OnClickListener generateOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            //close the keybpard
            if (arg0 != null && getApplicationContext() != null) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arg0.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            // TODO Auto-generated method stub
            String A = inputA.getText().toString();
            String B = inputB.getText().toString();
            String C = inputC.getText().toString();
            String urlRqs3DPie = urlGoogleChart
                    + urlp3Api+"&chl="+labelList[0]+"|"+labelList[1]+"|"+labelList[2]+"&chd=t:"
                    + A + "," + B + "," + C;

            final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                pieChart.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                // save cache webview
                pieChart.getSettings().setAppCacheMaxSize(1024 * 1024 * 12);
                pieChart.loadUrl(urlRqs3DPie);
            } else {
                pieChart.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
                pieChart.loadUrl(urlRqs3DPie);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
