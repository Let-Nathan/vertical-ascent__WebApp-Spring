document.addEventListener('DOMContentLoaded', function() {
    let viewCartClicked = false;
    const viewCartButton = document.getElementById('viewCartBtn');
    if (viewCartButton) {
        viewCartButton.addEventListener('click', function(event) {
            event.preventDefault();
            const USERCART = JSON.parse(localStorage.getItem('userCart'));
            console.log('User cart ===>' + USERCART);
            const CARTITEMDTO =
                USERCART != null ? USERCART.map(convertToProductDto) : null;

            if (redirectIfEmpty(CARTITEMDTO)) {
                return;
            }

            sendCartItems(CARTITEMDTO);
        });
    }

    //@Todo Fix bug when add shopping icon redirect with js
    // if (PANNIERICON) {
    //     PANNIERICON.addEventListener('click', function(event) {
    //         event.preventDefault();
    //         if (!viewCartClicked) {
    //             sendCartItems(CARTITEMDTO);
    //             viewCartClicked = true;
    //         }
    //     });
    // }
});

    /**
     * Convert a cart item from local storage to a product DTO representation.
     *
     * @param {Object} item -
     * Cart item from local storage converted to DTO representation.
     * @return {{quantity: number, price: number, name: string, id: number}} -
     * JSON representation of the product DTO.
     */
    function convertToProductDto(item) {
        return {
            id: parseInt(item.productId),
            name: item.productName,
            price: parseInt(item.productPrice),
            quantity: parseInt(item.quantity),
        };
    }

    /**
     * Redirects the user if the cart items are empty.
     *
     * @param {Array|null} cartItems - Local storage cart items.
     * @return {boolean} - Boolean indicating if the cart is empty.
     */
    function redirectIfEmpty(cartItems) {
        if (!cartItems) {
            window.location.href = '/pannier-vide';
            return true;
        }
        return false;
    }

    /**
     * Sends the current user's local storage cart items to an endpoint.
     *
     * @param {Array|Object} cartItems - A potential list or single product.
     */
    function sendCartItems(cartItems) {
        const userId = localStorage.getItem('userId');

        const requestBody = {
            cartItems: cartItems,
        };

        if (userId) {
            requestBody.userId = userId;
        }
        console.log('Request body ==>' +  requestBody);
        fetch('/validate-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (!response) {
                    throw new Error(`Http error ==>  ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                const USERID = localStorage.getItem('userId');
                if (USERID == null || !USERID || USERID === 'null' || USERID === 'undefined') {
                    localStorage.setItem('userId', data.sessionId);
                }
                window.location.href = '/pannier?pannierId=' + data.sessionId;
            })
            .catch((error) => {
                console.log('An error occurred: ' + error);
            });
    }

