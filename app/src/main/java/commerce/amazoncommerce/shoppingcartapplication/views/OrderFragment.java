package commerce.amazoncommerce.shoppingcartapplication.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import commerce.amazoncommerce.shoppingcartapplication.R;
import commerce.amazoncommerce.shoppingcartapplication.databinding.FragmentCartBinding;
import commerce.amazoncommerce.shoppingcartapplication.databinding.FragmentOrderBinding;
import commerce.amazoncommerce.shoppingcartapplication.viewmodels.ShopViewmodel;

public class OrderFragment extends Fragment {

    NavController navController;
    FragmentOrderBinding fragmentOrderBinding;
    ShopViewmodel shopViewModel;

    public OrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate( R.layout.fragment_order, container, false );
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return fragmentOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewmodel.class);

        fragmentOrderBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopViewModel.resetCart();
                navController.navigate(R.id.action_orderFragment_to_shopFragment);
            }
        });
    }
}