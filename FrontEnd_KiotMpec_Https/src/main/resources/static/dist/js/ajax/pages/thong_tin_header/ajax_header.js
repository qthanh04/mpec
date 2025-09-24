$(function () {
    setViewHeader();
    logout();
})

function setViewHeader() {
    $("#role").text(user.tenChucVu);
    $("#name-emp").text(user.hoVaTen);
    let ava = user.urlAnhDaiDien;
    if (ava === null || ava === '') {
        $("#avatar").attr("src", "./dist/img/avatar2.png")
    } else {
        $("#avatar").attr("src", ava);
    }
}

function logout() {
    $("#dang-xuat-btn").click(function () {
        console.log("log out");
        let token = window.sessionStorage.getItem("token");
        if (token != null) {
            window.sessionStorage.removeItem("token");
            window.sessionStorage.removeItem("id");
            location.replace("/dang-nhap")
        } else {
            alterWarning("You haven't yet logged in");
        }
    });
}