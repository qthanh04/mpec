
var url_api = "http://localhost:8181/api/";

var dieuKhoan= $("#dieuKhoan");



$(function () {
    getdk();
})



async function ajaxGet(urls) {
    let rs = null;
    await $.ajax({
        type: 'GET',
        headers: {
            "Authorization": ""
        },
        dataType: "json",
        url : url_api + urls ,
        timeout: 30000,
        success: function (result) {
            rs = result;
        }
    })
    console.log("1");
    console.log(rs );
    return rs;
}


function getdk()
{
    thongTinDieuKhoan().then(rs => {

        let dieukhoan = rs.data.noiDung;
        console.log(dieukhoan);
        dieuKhoan.html(dieukhoan);
    })
}

async function thongTinDieuKhoan() {
    return ajaxGet(`v1/public/chinh-sach/find-by-id?id=1`);
}