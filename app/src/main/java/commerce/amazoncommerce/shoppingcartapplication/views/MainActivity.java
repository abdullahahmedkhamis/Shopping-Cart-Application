package commerce.amazoncommerce.shoppingcartapplication.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commerce.amazoncommerce.shoppingcartapplication.R;
import commerce.amazoncommerce.shoppingcartapplication.models.CartItem;
import commerce.amazoncommerce.shoppingcartapplication.models.Common;
import commerce.amazoncommerce.shoppingcartapplication.models.UserUtils;
import commerce.amazoncommerce.shoppingcartapplication.viewmodels.ShopViewmodel;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int PICK_IMAG_REQUEST = 111;

    NavController navController;
    ShopViewmodel shopViewModel;

    private int cartQuantity = 0;


    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    private Uri imageUri;
    private ImageView image;
    GeoFire geoFire;
    private AlertDialog waitingDialog;
    private StorageReference storageReference,onlineRef;
    CircleImageView avatarIv,profileImageViwe;
    TextView nameTv, emailTv,phoneTv;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;

    private FirebaseAuth mAuth;

    ValueEventListener onlineValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                databaseReference.onDisconnect().removeValue();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT ).show();
        }
    };

    @Override
    protected void onDestroy() {
//        geoFire.removeLocation( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        profileImageViwe = (CircleImageView) findViewById( R.id.set_profile_imgage );
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        Toolbar toolbar = (Toolbar)findViewById( R.id.toolbarbar );
        setSupportActionBar( toolbar );
        nav=(NavigationView)findViewById( R.id.navmenu );
        drawerLayout=(DrawerLayout)findViewById( R.id.drawer );

        toggle=new ActionBarDrawerToggle( this,drawerLayout,toolbar, R.string.open,R.string.close );
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();


        init();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController);
        shopViewModel = new ViewModelProvider(this).get( ShopViewmodel.class);
        shopViewModel.getCart().observe( this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity = 0;
                for (CartItem cartItem: cartItems){
                   quantity += cartItem.getQuantity();
                }
                cartQuantity = quantity;
                invalidateOptionsMenu();
            }
        } );
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == PICK_IMAG_REQUEST && resultCode == Activity.RESULT_OK)
        {
            if(data != null && data.getData() != null)
            {
                imageUri = data.getData();
                image.setImageURI( imageUri );

                showDialogUpload(  );

            }
        }
    }

    private void showDialogUpload() {

        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
        builder.setTitle( "Change Image" )
                .setMessage( "Do you want to change Image" )
                .setNegativeButton( "Cancel" , (dialog, which) -> dialog.dismiss() )
                .setNegativeButton( "Upload",  (dialog, which) ->{
                    if(imageUri != null)
                    {
                        waitingDialog.setMessage( "Uploading..." );
                        waitingDialog.show();
//                        String unique_name = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        StorageReference Folder = storageReference.child( "image/"+ "unique_name");
                        Folder.putFile( imageUri )
                                .addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        waitingDialog.dismiss();
                                        Snackbar.make( drawerLayout, e.getMessage(),Snackbar.LENGTH_LONG).show();
                                    }
                                } ).addOnCompleteListener( new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if(task.isSuccessful())
                                {
                                    Folder.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Map<String,Object> updateData = new HashMap<>();
                                            updateData.put( "image", uri.toString() );

                                            UserUtils.updateUser( drawerLayout,updateData );
                                        }
                                    } );
                                }
                                waitingDialog.dismiss();  // That was not here
                            }

                        } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                waitingDialog.setMessage( new StringBuilder("Uploading...").append( progress ).append( "%" ) );
                            }
                        } );

//          FirebaseAuth.getInstance().signOut();
//          Intent intent = new Intent( MainActivity.this, MainActivity.class);        // That was made wrong
//          intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//          startActivity( intent );
//          finish();
                    }
                } )
                .setCancelable( false );
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener( dialogInterface -> {
            dialog.getButton( AlertDialog.BUTTON_POSITIVE )
                    .setTextColor( getResources().getColor( android.R.color.holo_red_dark ) );
            dialog.getButton( AlertDialog.BUTTON_NEGATIVE )
                    .setTextColor( getResources().getColor( R.color.colorAccent ) );
        } );
        dialog.show();
    }


    private void init(){

        waitingDialog = new AlertDialog.Builder( this )
                .setCancelable( false )
                .setMessage( "Waiting..." )
                .create();

        storageReference = FirebaseStorage.getInstance().getReference();

    nav.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            switch (item.getItemId())

            {
                case R.id.home:
                    Toast.makeText( MainActivity.this, "Home", Toast.LENGTH_SHORT ).show();
                    drawerLayout.closeDrawer( GravityCompat.START );
                    break;

                case R.id.call:
                    Toast.makeText( MainActivity.this, "Phone", Toast.LENGTH_SHORT ).show();
                    drawerLayout.closeDrawer( GravityCompat.START );
                    break;


                case R.id.setting:
                    Toast.makeText( MainActivity.this, "Settings", Toast.LENGTH_SHORT ).show();
                    drawerLayout.closeDrawer( GravityCompat.START );
                    break;
            }
            return true;
        }
    } );


    View header = nav.getHeaderView( 0 );
    TextView name = (TextView) header.findViewById( R.id.txt_name );
    TextView phone = (TextView) header.findViewById( R.id.txt_phone );
    TextView star = (TextView) header.findViewById( R.id.txt_star);
    image = (ImageView) header.findViewById( R.id.set_profile_imgage );

    name.setText( Common.buildWelcomeMessage() );
    name.setText( Common.currentUser !=null ?  Common.currentUser.getFirstname()  : "Programmer Abdullah" );
    phone.setText( Common.currentUser != null ? Common.currentUser.getPhoneNumber() : "00201150629997" );
    star.setText( Common.currentUser != null ? String.valueOf( Common.currentUser.getRating() ) : "0.0" );

    image.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType( "image/*" );
            intent.setAction( Intent.ACTION_GET_CONTENT );
            startActivityForResult( intent, PICK_IMAG_REQUEST );
        }
    } );

    if(Common.currentUser != null && Common.currentUser.getAvatar() != null && !TextUtils.isEmpty( Common.currentUser.getAvatar() ))
    {
        Glide.with( this )
                .load( Common.currentUser.getAvatar() )
                .into( image );

    }
}

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.cartFragment);
        View actionView = menuItem.getActionView();

        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);

        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}