async function findHangHoa(id = 0) {
    return ajaxGet(`v1/admin/hang-hoa/find-by-id?id=${id}`);
}

async function findFirstHH(hangHoaId=0) {
    return ajaxGet(`v1/admin/lich-su-gia-ban/find-distinct-by-hang-hoa-id?hangHoaId=${hangHoaId}`);
}

async function findGiaBanByHangHoaId(donViHangHoaId=0) {
    return ajaxGet(`v1/admin/lich-su-gia-ban/find-gia-ban-hien-tai?don-vi-hang-hoa-id=${donViHangHoaId}`);
}

async function findKhachHangById(id= 0) {
    return ajaxGet(`v1/admin/khach-hang/find-by-id?id=${id}`);
}

async function findHoaDonById(id= 0) {
    return ajaxGet(`v1/admin/hoa-don/find-by-id?id=${id}`);
}

async function uploadHoaDon(nguoiDungId,khachHangId,chiNhanhId,hoaDonDto) {
    return ajaxPost(`v1/admin/hoa-don/upload?nguoi-dung-id=${nguoiDungId}&khach-hang-id=${khachHangId}&chi-nhanh-id=${chiNhanhId}`, hoaDonDto);
}

async function uploadHoaDonChiTiet(hoaDonChiTietForm) {
    return ajaxPost(`v1/admin/hoa-don-chi-tiet/upload-by-form`, hoaDonChiTietForm);
}

async function uploadKhachHang(khachHang) {
    return ajaxPost(`v1/admin/khach-hang/upload`,khachHang);
}

function uploadPhieuThu(phieuThuRequest,chinhanhId,nguoiDungId,loaiThuId) {
    return ajaxPost(`v1/admin/phieu-thu/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-thu-id=${loaiThuId}`, phieuThuRequest);
}

function uploadPhieuThuHoaDon(hoaDonId,phieuThuId,tienDaTra) {
    return ajaxPost(`v1/admin/phieu-thu-hoa-don-tra-hang-nhap/hoa-don/upload?hoa-don-id=${hoaDonId}&phieu-thu-id=${phieuThuId}&tien-da-tra=${tienDaTra}`);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}
