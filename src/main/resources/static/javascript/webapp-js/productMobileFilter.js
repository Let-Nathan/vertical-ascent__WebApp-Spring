document.addEventListener('DOMContentLoaded', function() {
    const MOBILE_FILTER_TRIGGER = document.getElementById('mobile-filter-container');
    const DESKTOP_FILTER_CONTAINER = document.getElementById('filter-desktop-container');

    MOBILE_FILTER_TRIGGER.addEventListener('click', function() {
        // Ajoutez ou supprimez la classe 'd-none' pour afficher ou masquer le conteneur de filtrage de bureau
        DESKTOP_FILTER_CONTAINER.classList.toggle('d-none');
    });
});
