package commerce.amazoncommerce.shoppingcartapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class SplishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splish );

        SystemClock.sleep( 1000 );
        Intent login = new Intent(SplishActivity.this, RegisterActivity.class);
        startActivity( login );
        finish();
    }
}