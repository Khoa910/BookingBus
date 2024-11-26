function test() {
    var tabsNewAnim = $('#navbarSupportedContent');
    var activeItemNewAnim = tabsNewAnim.find('.active');

    // Thiết lập vị trí cho selector của mục đang active
    var activeWidthNewAnimHeight = activeItemNewAnim.innerHeight();
    var activeWidthNewAnimWidth = activeItemNewAnim.innerWidth();
    var itemPosNewAnimTop = activeItemNewAnim.position();
    var itemPosNewAnimLeft = activeItemNewAnim.position();

    $(".hori-selector").css({
        "top": itemPosNewAnimTop.top + "px",
        "left": itemPosNewAnimLeft.left + "px",
        "height": activeWidthNewAnimHeight + "px",
        "width": activeWidthNewAnimWidth + "px"
    });

    // Khi click vào mục khác, cập nhật vị trí của mục đang active
    $("#navbarSupportedContent").on("click", "li", function () {
        // Xóa lớp 'active' từ tất cả các mục
        $('#navbarSupportedContent ul li').removeClass("active");

        // Thêm lớp 'active' cho mục được click
        $(this).addClass('active');

        var activeWidthNewAnimHeight = $(this).innerHeight();
        var activeWidthNewAnimWidth = $(this).innerWidth();
        var itemPosNewAnimTop = $(this).position();
        var itemPosNewAnimLeft = $(this).position();

        $(".hori-selector").css({
            "top": itemPosNewAnimTop.top + "px",
            "left": itemPosNewAnimLeft.left + "px",
            "height": activeWidthNewAnimHeight + "px",
            "width": activeWidthNewAnimWidth + "px"
        });
    });
}

$(document).ready(function () {
    setTimeout(function () { test(); }, 200);
});

$(window).on('resize', function () {
    console.log("Window resized");
    setTimeout(function () { test(); }, 200);
});

$(".navbar-toggler").click(function () {
    $(".navbar-collapse").slideToggle(200);
    console.log("Navbar toggled");
    setTimeout(function () { test(); }, 200);
});

// Add active class based on the current page
jQuery(document).ready(function ($) {
    // Get current path and find target link
    var path = window.location.pathname.replace("localhost:8080/", "");

    // Account for home page with empty path or "/" path
    if (path === '' || path === '/') {
        path = 'index.html';  // Hoặc tên tệp của trang chủ
    }

    // Remove 'active' class from all links
    $('#navbarSupportedContent ul li').removeClass('active');

    // Add active class to target link
    var target = $('#navbarSupportedContent ul li a[href="' + path + '"]');

    // Nếu không tìm thấy mục nào, chọn mục trang chủ
    if (target.length === 0) {
        target = $('#navbarSupportedContent ul li a[href="index.html"]');
    }

    target.parent().addClass('active');  // Gán 'active' cho mục được chọn
});

// Add active class on another page linked
$(window).on('load', function () {
    var current = location.pathname;
    console.log("Current pathname on load:", current);

    $('#navbarSupportedContent ul li a').each(function () {
        var $this = $(this);
        // if the current path is like this link, make it active
        if ($this.attr('href').indexOf(current) !== -1) {
            $this.parent().addClass('active');
            $this.parents('.menu-submenu').addClass('show-dropdown');
            $this.parents('.menu-submenu').parent().addClass('active');
            console.log("Link activated:", $this);
        } else {
            $this.parent().removeClass('active');
        }
    });
    test();
});

// Logout function
function logout() {
    localStorage.clear();
    console.log("Logged out and localStorage cleared");
}