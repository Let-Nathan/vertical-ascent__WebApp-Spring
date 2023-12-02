document.addEventListener('DOMContentLoaded', function() {
    //Get the add product button.
    const ADD_PRODUCT = document.getElementById('add-product');
    //Bootstrap modal initialization.
    const MODAL = new bootstrap.Modal(document.getElementById('addToCartModal'), {
        keyboard: false
    });
    const ITEM_INCREMENT = document.getElementById('item-plus');
    const ITEM_DECREMENT = document.getElementById('item-minus');
    const CART_QUANTITY = document.getElementById('cartQuantity');

    ITEM_INCREMENT.addEventListener('click', function() {
        let quantity = parseInt(CART_QUANTITY.innerText);
        quantity++;
        CART_QUANTITY.innerText = quantity;

        updateLocalStorage(quantity);
    });

    ITEM_DECREMENT.addEventListener('click', function() {
        let quantity = parseInt(CART_QUANTITY.innerText);
        if (quantity > 1) {
            quantity--;
            CART_QUANTITY.innerText = quantity;

            updateLocalStorage(quantity);
        }
    });

    /**
     * Add an event listener on click to the product button. We will add product
     * information to the local storage and display modal every time a product
     * is successfully add.
     */
    ADD_PRODUCT.addEventListener('click', function() {
        const productId = ADD_PRODUCT.getAttribute('data-product-id');
        const productName = ADD_PRODUCT.getAttribute('data-product-name');
        const productPrice = ADD_PRODUCT.getAttribute('data-product-price');
        const productQuantity = ADD_PRODUCT.getAttribute('data-product-quantity');

        //Simple user verification, we will re check data before create
        // cart user and shopping session.
        if (productQuantity === '0') {
            alert('Product not in stock');
            return;
        }

        let cart = JSON.parse(localStorage.getItem('userCart')) || [];
        let productInCart = cart.find(item => item.productId === productId);
        let quantity = 1;

        if (productInCart) {
            //If product is already in local storage we increment quantity by 1.
            productInCart.quantity += 1;
            quantity = productInCart.quantity;
        } else {
            //If no product, we initialized one with a default quantity.
            cart.push({
                productId: productId,
                productName: productName,
                productPrice: productPrice,
                quantity: 1
            });
        }
        //Update the local storage.
        localStorage.setItem('userCart', JSON.stringify(cart));

        //Display modal with updated quantity
        document.getElementById('cartQuantity').innerText = quantity;
        MODAL.show();
    });

    function updateLocalStorage(quantity) {
        const productId = ADD_PRODUCT.getAttribute('data-product-id');
        let cart = JSON.parse(localStorage.getItem('userCart')) || [];
        let productInCart = cart.find(item => item.productId === productId);

        if (productInCart) {
            productInCart.quantity = quantity;
        }

        localStorage.setItem('userCart', JSON.stringify(cart));
    }
});
