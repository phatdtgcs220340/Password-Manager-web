var applications = {};
var list = document.getElementById("dropdown-application-list").children;
for (let i = 0; i < list.length; i++) 
    applications[i] = list.item(i);

function search() {
    const searchBar = document.getElementById("search-bar").value; 
    for (let i = 1; i < list.length; i++) 
        if (!applications[i].children.item(0).children.item(1).innerHTML.startsWith(capitalizeFirstLetter(searchBar))) 
            applications[i].classList.add("hidden");
        
        else applications[i].classList.remove("hidden");
}
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}