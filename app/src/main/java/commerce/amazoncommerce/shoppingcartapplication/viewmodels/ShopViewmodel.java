package commerce.amazoncommerce.shoppingcartapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import commerce.amazoncommerce.shoppingcartapplication.models.CartItem;
import commerce.amazoncommerce.shoppingcartapplication.models.Product;
import commerce.amazoncommerce.shoppingcartapplication.repositories.CartRepo;
import commerce.amazoncommerce.shoppingcartapplication.repositories.ShopRepo;

public class ShopViewmodel extends ViewModel {

    ShopRepo shopRepo = new ShopRepo();
    CartRepo cartRepo = new CartRepo();

    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts() {
        return shopRepo.getProducts();
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<Product> getProduct() {
        return mutableProduct;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Product product) {
        return cartRepo.addItemToCart(product);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }



}
