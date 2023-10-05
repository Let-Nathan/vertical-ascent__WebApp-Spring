fetch('api/product-categories')
    .then(response => {
        if (!response.ok) {
            throw new Error('Unable to obtain response from API' + err);
        }
        return response.json();
    })
    .then(data => {
        data.forEach(item => {
           menuContainer(item);
        })
    })
    .catch(err => {
        console.error('Error while get data ==>' + err);
    });


function menuContainer(item) {

    const ulSelector = document.querySelector('.menu-categories');
    const listElement = document.createElement('li');
    const divContainer = document.createElement('div');
    const aElement = document.createElement('a');
    const imgBackGround = document.createElement('img');
    const spanCategoryTitle = document.createElement('span');

    listElement.className = "category " + item.name + " nav-menu-list";

    aElement.href = "product-category/" + item.id;
    aElement.class = "aElement";
    aElement.style.textDecoration = "none";

    divContainer.className = "nav-menu-container shadow-sm p-2 mb-2 container-fluid";
    //Todo replace image name by item.name
    divContainer.style.backgroundImage = 'url("../images/navbar_category/randonnee-bivouac.jpg")';
    divContainer.style.backgroundRepeat = "no-repeat";
    divContainer.style.backgroundPosition = "center";
    divContainer.style.boxShadow = "-10px 0px 10px -5px rgba(0,0,0,0.3)";

    // divContainer.style.objectFit = "cover";


    spanCategoryTitle.textContent = item.name;
    spanCategoryTitle.className = "fs-6 fw-bold text-light text-uppercase col-4";

    divContainer.append(spanCategoryTitle);
    // divContainer.append(imgBackGround);
    aElement.append(divContainer);
    listElement.append(aElement);
    ulSelector.append(listElement);

}

