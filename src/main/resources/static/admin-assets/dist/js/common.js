toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-top-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

// Active menu
const activeMenu = () => {
    const menuParentEls = document.querySelectorAll("#main-menu > li > a");
    const path = window.location.pathname;

    menuParentEls.forEach(menuParent => {
        const menuChildEls = menuParent.nextElementSibling.querySelectorAll("ul.nav.nav-treeview > li > a");
        menuChildEls.forEach(menuChild => {
            if (menuChild.getAttribute("href") === path) {
                menuParent.parentElement.classList.add("menu-is-opening", "menu-open");
                menuParent.classList.add("active");
                menuChild.classList.add("active");
            }
        })
    })
}
activeMenu();