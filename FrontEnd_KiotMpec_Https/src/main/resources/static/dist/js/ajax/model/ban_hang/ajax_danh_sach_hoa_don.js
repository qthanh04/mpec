async function searchHoaDon(chinhanhId,maHoaDon="",tenKhachHang="",tenNhanVien="", ngayDau=null, ngayCuoi=null, trangThai=-1,page=1,size=10) {
    return ajaxGet(`v1/admin/hoa-don/search?chi-nhanh-id=${chiNhanhId}&ma-hoa-don=${maHoaDon}&ten-khach-hang=${tenKhachHang}&ten-nhan-vien=${tenNhanVien}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&trang-thai=${trangThai}&page=${page}&size${size}`);
}

async function searchHoaDonByChiNhanh(chiNhanhId,text="",page=1,size=10){
    return ajaxGet(`v1/admin/hoa-don/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function setTrangThaiHoaDon(hoaDonId,trangThai) {
    return ajaxPut(`v1/admin/hoa-don/trang-thai?id=${hoaDonId}&trang-thai=${trangThai}`);
}
