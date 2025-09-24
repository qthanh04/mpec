async function khachHangSearch(tenKhachHang = "",  dienThoai = "", email = "", facebook = "", diaChi = "",page = 1, size = 10) {
    return ajaxGet(`v1/admin/khach-hang/search?ten-khach-hang=${tenKhachHang}&dien-thoai=${dienThoai}&email=${email}&facebook=${facebook}&dia-chi=${diaChi}&page=${page}&size=${size}`);
}

async function khachHangSearchTex(text,page = 1, size = 10) {
    return ajaxGet(`v1/admin/khach-hang/search-text?text=${text}&page=${page}&size=${size}`);
}

async function khachHangUpload(khachHang) {
    return ajaxPost(`v1/admin/khach-hang/upload`, khachHang,1);
}

async function khachHangUpdate(khachHang) {
    return ajaxPut(`v1/admin/khach-hang/update`, khachHang,1);
}

async function khachHangHoaDonUpdate(khachHang) {
    return ajaxPut(`v1/admin/khach-hang/hoa-don/update`, khachHang,1);
}

async function khachHangDelete(khachHangId) {
    return ajaxDelete(`v1/admin/khach-hang/delete?id=${khachHangId}`);
}