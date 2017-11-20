package sme.com.br.wifichooser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import android.net.wifi.ScanResult;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WifiActivity extends AppCompatActivity {

    WifiManipulado wifi = new WifiManipulado();

    Firebase bd;
    Firebase save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        Firebase.setAndroidContext(this);


    }
    public void more (View view) {
        Intent intent = new Intent(WifiActivity.this, Info.class);
         startActivity(intent);
    }
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Redes");



    public void onClick(View view){
        WifiManager wifimanager=(WifiManager) getApplicationContext(). getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wc = new WifiConfiguration();
        wc.status = WifiConfiguration.Status.ENABLED;
       final TextView result = (TextView) findViewById(R.id.value);
        final TextView Mostrando = (TextView) findViewById(R.id.WifiLevel);
        final List<ScanResult> results = wifi.getConections(this);
        String SSID="";
        int Level=-200;
        int i = 0;
        String Nivel="";
        String Crypto="";
        String PassWord="gustavo1";
        Context contexto = getApplicationContext();
        String texto = "Redes Encontradas";
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, texto,duracao);
        toast.show();
        if(results!=null){

          final   StringBuilder info = new StringBuilder();

            for(ScanResult connection: results){
                if (connection.level>=-40){
                    Nivel="ÓTIMO";
                    SSID=connection.SSID;
                }else if (connection.level<=-41 && connection.level>=-70){
                    Nivel="BOM";
                    SSID=connection.SSID;
                }else if (connection.level<=-71){
                    Nivel="RUIM";
                    SSID=connection.SSID;
                }
                result.setText(info.append(connection.SSID).append("\n").append("MAC: ").append(connection.BSSID).append("\n").append("Crypto: ").append(connection.capabilities).append("\n").append(Nivel).append("\n\n\n"));
                i+=1;
                myRef.setValue("teste\n"+SSID);

                          }
            Mostrando.setText(""+i);

            List<String> myList = new ArrayList<String>(){
                {
                    add("AAA");
                    add("BBB");
                }
            };

            bd=new Firebase("https://wifichooser-39ed0.firebaseio.com/"+myList);




            wc.SSID ="\""+SSID+"\"";

            wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            if (PassWord=="")
            {

            }
                wc.preSharedKey="\""+PassWord+"\"";
                //wifimanager.disconnect();
                //int netId = wifimanager.addNetwork(wc);
               // wifimanager.enableNetwork(netId, true);
               // wifimanager.reconnect();

            // / connect to and enable the connection

        } else {
            result.setText("Sorry, no connections");
        }

    }

}
