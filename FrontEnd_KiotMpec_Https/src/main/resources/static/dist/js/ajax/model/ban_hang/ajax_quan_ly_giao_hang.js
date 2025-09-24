async function searchHoaDonHangChuaGiao(chinhanhId,maHoaDon="",tenKhachHang="",tenNhanVien="", ngayDau, ngayCuoi, trangThai=3,page=1,size=10) {
    return ajaxGet(`v1/admin/hoa-don/search?chi-nhanh-id=${chiNhanhId}&ma-hoa-don=${maHoaDon}&ten-khach-hang=${tenKhachHang}&ten-nhan-vien=${tenNhanVien}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&trang-thai=${trangThai}&page=${page}&size${size}`);
}

async function searchHoaDonByChiNhanh(chiNhanhId,text="",page=1,size=10){
    return ajaxGet(`v1/admin/hoa-don/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function findAllHoaDonChuaGiao(chiNhanhId,trangThai=3,page=1,size=10){
    return ajaxGet(`v1/admin/hoa-don/search-hoa-don-hang-chua-giao?chi-nhanh-id=${chiNhanhId}&trangThai=${trangThai}&page=${page}&size=${size}`);
}

async function findByIdDonViGiaoHang(id){
    return ajaxGet(`v1/public/partner/find-by-id-optional?id=${id}`);
}

