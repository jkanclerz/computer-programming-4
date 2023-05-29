package pl.jkanclerz.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jkanclerz.sales.cart.Cart;
import pl.jkanclerz.sales.cart.CartStorage;
import pl.jkanclerz.sales.product.AlwaysMissingProductDetailsProvider;
import pl.jkanclerz.sales.product.ProductDetailsProvider;

import java.util.UUID;

public class CollectingProductsTest {

    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;

    @BeforeEach
    void setup() {
        cartStorage = new CartStorage();
        productDetailsProvider = new AlwaysMissingProductDetailsProvider();
    }

    @Test
    void itAllowsToCollectProductsToCart() {
        //Arrange
        Sales sales = thereIsSalesModule();
        String productId =  thereIsProduct();
        String customer = thereIsCustomer("Kuba");

        //Act
        sales.addToCart(customer, productId);

        //Assert
        assertThereIsNProductsInCustomersCart(customer, 1);
    }

    private void assertThereIsNProductsInCustomersCart(String customer, int productsCount) {
        Cart customerCart = cartStorage.load(customer).get();

        assert customerCart.itemsCount() == productsCount;
    }

    private String thereIsCustomer(String customerId) {
        return customerId;
    }

    private String thereIsProduct() {
        return UUID.randomUUID().toString();
    }

    private Sales thereIsSalesModule() {
        return new Sales(cartStorage, productDetailsProvider);
    }
}
