package commerce.amazoncommerce.shoppingcartapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

//    NavigationView nav;
//    ActionBarDrawerToggle toggle;
//    DrawerLayout drawerLayout;
//    CircleImageView circleImageView;
//    private Uri imageUri;
//    private ImageView image;
//    TextView nameTv, emailTv,phoneTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        //Toolbar toolbar = (Toolbar)findViewById( R.id.toolbar );
//        setSupportActionBar( toolbar );


        //nav=(NavigationView)findViewById( R.id.navmenu );
       // drawerLayout=(DrawerLayout)findViewById( R.id.drawer );

        //toggle=new ActionBarDrawerToggle( this,drawerLayout,toolbar, R.string.open,R.string.close );
        //drawerLayout.addDrawerListener( toggle );
//        toggle.syncState();

//
//        nav.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item)
//            {
//
//                switch (item.getItemId())
//
//                {
//                    case R.id.home:
//                        Toast.makeText( MainActivity.this, "Home", Toast.LENGTH_SHORT ).show();
//                        drawerLayout.closeDrawer( GravityCompat.START );
//                        break;
//
//                    case R.id.call:
//                        Toast.makeText( MainActivity.this, "Phone", Toast.LENGTH_SHORT ).show();
//                        drawerLayout.closeDrawer( GravityCompat.START );
//                        break;
//
//
//                    case R.id.setting:
//                        Toast.makeText( MainActivity.this, "Settings", Toast.LENGTH_SHORT ).show();
//                        drawerLayout.closeDrawer( GravityCompat.START );
//                        break;
//                }
//                return true;
//            }
//        } );
    }
}