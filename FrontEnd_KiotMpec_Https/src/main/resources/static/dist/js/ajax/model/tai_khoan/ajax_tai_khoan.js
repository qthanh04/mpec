
var url_tai_khoan = "v1/admin/tai-khoan/";

async function thongTinTaiKhoan(page = 1, size =99999) {
    return ajaxGet(`v1/admin/tai-khoan/find-all?page=${page}&size=${size}`);
}

async function taiKhoanFindById(id) {
    return ajaxGet(`v1/admin/tai-khoan/find-by-id?id=${id}`);
}

async function taiKhoanFindByEmail(email) {
    return ajaxGetNoAsync(`v1/public/user/find-nguoi-dung-by-email?email=${email}`);
}

async function taiKhoanFindBySDT(sdt) {
    return ajaxGetNoAsync(`v1/public/user/find-nguoi-dung-by-sdt?sdt=${sdt}`);
}

async function uploadTaiKhoan(taiKhoan ,chiNhanhId, phongbanId , chucVuId  , vaiTroId ) {
    return ajaxPost(`v1/admin/tai-khoan/upload?chi-nhanh-id=${chiNhanhId}&phong-ban-id=${phongbanId}&chuc-vu-id=${chucVuId}&vai-tro-id=${vaiTroId}`,taiKhoan, 1);
}

async function updateTaiKhoan(taiKhoan ,chiNhanhId, phongbanId , chucVuId  , vaiTroId ) {
    return ajaxPut(`v1/admin/tai-khoan/update?chi-nhanh-id=${chiNhanhId}&phong-ban-id=${phongbanId}&chuc-vu-id=${chucVuId}&vai-tro-id=${vaiTroId}`,taiKhoan, 1);
}

async function taiKhoanUpdate(taiKhoan) {
    return ajaxPut(`v1/admin/tai-khoan/update`,taiKhoan, 1);
}

async function themMoiTaiKhoan(taiKhoan) {
    return ajaxPost(`v1/admin/tai-khoan/upload`,taiKhoan, 1);
}

async function xoaTaiKhoan(id) {
    return ajaxPut(`v1/admin/tai-khoan/delete?id=${id}`, 1);
}

async function khoaTaiKhoan(id) {
    return ajaxPut(`v1/admin/tai-khoan/trang-thai?id=${id}&trang-thai=1`, 1);
}

async function timTaiKhoan(search) {
    return ajaxPut(`v1/admin/tai-khoan/search?search=${search}`, 1);
}

async function findTaiKhoanByChiNhanhAndText(chiNhanhId, text = "",page = 1, size = 5) {
    return ajaxGet(`${url_tai_khoan}search?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

//=================== Function ===================//
function showQRCode(tag, url, width = 100, height = 100) {
    $.ajax({
        type: 'GET',
        headers: {
            "Authorization": ss_lg
        },
        responseType: 'image/png',
        url: url,
        timeout: 30000,
        success: function (data) {
            tag.append(`<img src="data:image/png;base64,${data.data}" width="${width}" height="${height}">`);
        },
        error: function () {
            console.log("error");
            tag.html(`<img src=''>`);
        }
    });
}