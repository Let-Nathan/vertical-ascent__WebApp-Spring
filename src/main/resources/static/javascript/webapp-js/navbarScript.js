/**
 * Fetch api product categories to create category navbar menu
 */
fetch('/api/product-categories')
    .then((response) => {
        if (!response.ok) {
            throw new Error(
                'Unable to obtain response from API : ' + response.status,
            );
        }
        return response.json();
    })
    .then((data) => {
        data.forEach((productCategory) => {
           menuContainer(productCategory);
        });
    })
    .catch((err) => {
        console.error('An error occurs while retrieving data ==>' + err);
    });

/**
 * Create dynamically each product category container
 *
 * @param {Object} productCategory - Product category data from fetch api
 */
function menuContainer(productCategory) {
    // Init whole list / container for menu //
    const UL_SELECTOR = document.querySelector('.menu-categories');
    const LIST_ELEMENT = document.createElement('li');
    const DIV_CONTAINER = document.createElement('div');
    const A_ELEMENT = document.createElement('a');
    const SPAN_CATEGORY_TITLE = document.createElement('span');
    const JPG = '.jpg';
    // -------------------------------- //

    // <list> element //
    LIST_ELEMENT.className =
        'category ' + productCategory.name + ' nav-menu-list';
    // -------------------------------- //

    // <a> element make container clickable //
    A_ELEMENT.href = '/product-category/' + productCategory.name;
    A_ELEMENT.class = 'aElement';
    A_ELEMENT.style.textDecoration = 'none';
    // -------------------------------- //

    // <div> element with background image //
    DIV_CONTAINER.className =
        'nav-menu-container shadow-sm p-2 mb-3 ' +
        'flex-row col-lg-12 shadow-lg bg-body opacity-75';

    // Todo implement an image collection
    //  (images name != category name = empty container)
    DIV_CONTAINER.style.backgroundImage =
        `url("../images/navbar_category/${productCategory.name}${JPG}")`;
    // -------------------------------- //

    // <span> element with category name
    SPAN_CATEGORY_TITLE.textContent = productCategory.name;
    SPAN_CATEGORY_TITLE.className ='span-menu-text fw-bold text-dark ' +
        'text-uppercase col-4 col-md-1 d-flex flex-column ' +
        'align-items-start justify-content-center border border-3 ' +
        'border-dark opacity-100';
    // -------------------------------- //

    // Assembly of elements  //
    DIV_CONTAINER.append(SPAN_CATEGORY_TITLE);
    A_ELEMENT.append(DIV_CONTAINER);
    LIST_ELEMENT.append(A_ELEMENT);
    UL_SELECTOR.append(LIST_ELEMENT);
    // -------------------------------- //
}

/**
 * Event listener to close the open navbar-menu when a click occurs
 * outside the div.
 */
window.addEventListener('click', function(event) {
    const navbarCollapse = document.querySelector('.navbar-collapse');
    const menuCategories = document.querySelector('.menu-categories');
    // Show is a class name added by bootstrap aria controls
    if (navbarCollapse.classList.contains('show')) {
        if (!menuCategories.contains(event.target)) {
            navbarCollapse.classList.remove('show');
        }
    }
});
