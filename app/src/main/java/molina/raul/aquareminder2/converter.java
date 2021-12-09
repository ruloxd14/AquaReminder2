package molina.raul.aquareminder2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import molina.raul.aquareminder2.menu.AboutActivity;
import molina.raul.aquareminder2.menu.DashboardActivity;

public class converter extends AppCompatActivity {
    //private DatabaseReference mDataBase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mDataBase = FirebaseDatabase.getInstance().getReference();

        /*mDataBase.child("Consumo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("cantidad").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/







        //BottomNavigationView bottomNavigationView = findViewById(R.id.botton_navigation);
        //bottomNavigationView.setSelectedItemId(R.id.home);

        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext()
                                , DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                , AboutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });*/

        /*@Override public void onCreate (Bundle bundle){
            super.onCreate(bundle);
            setContentView(R.layout.pantalla);
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                return;
            }
            int res = extras.getInt("resourseInt");
            ImageView view = (ImageView) findViewById(R.id.something);
            view.setImageResourse(res);
        }*/


    }
}
