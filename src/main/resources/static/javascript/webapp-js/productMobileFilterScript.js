document.addEventListener('DOMContentLoaded', function() {
    const MOBILE_FILTER_TRIGGER =
        document.getElementById('mobile-filter-container');
    const DESKTOP_FILTER_CONTAINER =
        document.getElementById('filter-desktop-container');
    const PRODUCT_LIST =
        document.getElementById('product-list-category');
    const USER_RATING =
        document.getElementById('user-rating');
    const FOOTER =
        document.getElementById('footer');

    MOBILE_FILTER_TRIGGER.addEventListener(
        'click', function() {
        DESKTOP_FILTER_CONTAINER.classList.toggle('d-none');
        DESKTOP_FILTER_CONTAINER.style.zIndex = '1';
        PRODUCT_LIST.classList.toggle('d-none');
        USER_RATING.classList.toggle('d-none');
        FOOTER.classList.toggle('d-none');
    });
});
