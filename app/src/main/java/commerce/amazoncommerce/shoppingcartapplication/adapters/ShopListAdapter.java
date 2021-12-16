package commerce.amazoncommerce.shoppingcartapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import commerce.amazoncommerce.shoppingcartapplication.databinding.ShopRowBinding;
import commerce.amazoncommerce.shoppingcartapplication.models.Product;

public class ShopListAdapter extends ListAdapter<Product, ShopListAdapter.ShopViewHolder> {


    ShopInterface shopInterface;
    public ShopListAdapter(ShopInterface shopInterface) {
        super( Product.itemCallback );
        this.shopInterface = shopInterface;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        ShopRowBinding shopRowBinding = ShopRowBinding.inflate( layoutInflater,parent,false );
        shopRowBinding.setShopInterfase(shopInterface);
        return new ShopViewHolder( shopRowBinding );
    }


    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
    Product product = getItem( position );
    holder.shopRowBinding.setProduct( product );
    holder.shopRowBinding.executePendingBindings();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        ShopRowBinding shopRowBinding;
        public ShopViewHolder(ShopRowBinding binding) {
            super( binding.getRoot() );
            this.shopRowBinding = binding;
        }
    }

    public interface ShopInterface {
        void addItem(Product product);
        void onItemClick(Product product);

    }
}
