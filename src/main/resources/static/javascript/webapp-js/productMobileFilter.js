document.addEventListener('DOMContentLoaded', function() {
    const MOBILE_FILTER_TRIGGER =
        document.getElementById('mobile-filter-container');
    const DESKTOP_FILTER_CONTAINER =
        document.getElementById('filter-desktop-container');

    MOBILE_FILTER_TRIGGER.addEventListener(
        'click', function() {
        DESKTOP_FILTER_CONTAINER.classList.toggle('d-none');
    });
});
