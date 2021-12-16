package commerce.amazoncommerce.shoppingcartapplication.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import commerce.amazoncommerce.shoppingcartapplication.R;
import commerce.amazoncommerce.shoppingcartapplication.databinding.FragmentProductDetailsBinding;
import commerce.amazoncommerce.shoppingcartapplication.databinding.FragmentShopBinding;
import commerce.amazoncommerce.shoppingcartapplication.viewmodels.ShopViewmodel;


public class ProductDetailsFragment extends Fragment {

    FragmentProductDetailsBinding fragmentProductDetailsBinding;
    ShopViewmodel shopViewmodel;


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           fragmentProductDetailsBinding = FragmentProductDetailsBinding.inflate( inflater, container,false );
           return fragmentProductDetailsBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        shopViewmodel = new ViewModelProvider( requireActivity() ).get( ShopViewmodel.class);
        fragmentProductDetailsBinding.setShopViewModel( shopViewmodel );

    }
}