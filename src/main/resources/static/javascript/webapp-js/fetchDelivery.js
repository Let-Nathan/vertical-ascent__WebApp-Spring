document.addEventListener('DOMContentLoaded', function() {
    const livraisonButton = document.getElementById('delivery-btn');

    if (livraisonButton) {
        livraisonButton.addEventListener('click', function(event) {
            event.preventDefault();

            const urlParams = new URLSearchParams(window.location.search);
            const pannierId = urlParams.get('pannierId');
            window.location.href = `/livraison?pannierId=${pannierId}`;
        });
    }
});
