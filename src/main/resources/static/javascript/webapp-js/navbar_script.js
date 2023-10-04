fetch('api/product-categories')
    .then(response => {
        if (!response.ok) {
            throw new Error('Unable to obtain response from API' + err);
        }
        console.log('Response ==>' + response);
        return response.json();
    })
    .then(data => {
        data.forEach(item => {
           menuContainer(item);
        })
        console.log('ok');
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

    listElement.className = "category " + item.name;
    divContainer.className = "container-fluid d-flex flex-column align-items-start";

    aElement.id = item.id;
    aElement.href = "product-category/" + item.id;

    divContainer.append(aElement);
    listElement.append(divContainer);
    ulSelector.append(listElement);

}
