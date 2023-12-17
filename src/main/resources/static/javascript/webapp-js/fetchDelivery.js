document.addEventListener('DOMContentLoaded', function() {
    const livraisonButton = document.getElementById('delivery-btn'); // Ajoutez un ID à votre bouton ou lien pour la livraison

    if (livraisonButton) {
        livraisonButton.addEventListener('click', function(event) {
            event.preventDefault();

            // Faites une vérification côté serveur avec Spring Security pour voir si l'utilisateur est connecté ou s'enregistre

            // Une fois que l'utilisateur est vérifié par Spring Security
            const urlParams = new URLSearchParams(window.location.search);
            const pannierId = urlParams.get('pannierId');
            // Redirigez vers /livraison avec l'ID du panier dans l'URL
            window.location.href = `/livraison?pannierId=${pannierId}`;
        });
    }
});
