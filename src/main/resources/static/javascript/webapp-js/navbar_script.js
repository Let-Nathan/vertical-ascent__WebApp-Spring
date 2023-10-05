/**
 * Fetch api product categories to create category navbar menu
 */
fetch('api/product-categories')
    .then((response) => {
        if (!response.ok) {
            throw new Error(
                'Unable to obtain response from API' + response.statusText
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
    const ulSelector = document.querySelector('.menu-categories');
    const listElement = document.createElement('li');
    const divContainer = document.createElement('div');
    const aElement = document.createElement('a');
    const spanCategoryTitle = document.createElement('span');
    // -------------------------------- //

    // <list> element //
    listElement.className =
        'category ' + productCategory.name + ' nav-menu-list';
    // -------------------------------- //

    // <a> element make container clickable //
    aElement.href = 'product-category/' + productCategory.id;
    aElement.class = 'aElement';
    aElement.style.textDecoration = 'none';
    // -------------------------------- //

    // <div> element with background image //
    divContainer.className =
        'nav-menu-container shadow-sm p-2 mb-2 container-fluid';
    // Todo replace image name by item.name
    divContainer.style.backgroundImage =
        'url("../images/navbar_category/randonnee-bivouac.jpg")';
    divContainer.style.backgroundRepeat = 'no-repeat';
    divContainer.style.backgroundPosition = 'center';
    divContainer.style.boxShadow = '-10px 0px 10px -5px rgba(0,0,0,0.3)';
    // -------------------------------- //

    // <span> element with category name
    spanCategoryTitle.textContent = productCategory.name;
    spanCategoryTitle.className =
        'fs-6 fw-bold text-light text-uppercase col-4';

    // -------------------------------- //

    // Assembly of elements  //
    divContainer.append(spanCategoryTitle);
    aElement.append(divContainer);
    listElement.append(aElement);
    ulSelector.append(listElement);
    // -------------------------------- //
}

