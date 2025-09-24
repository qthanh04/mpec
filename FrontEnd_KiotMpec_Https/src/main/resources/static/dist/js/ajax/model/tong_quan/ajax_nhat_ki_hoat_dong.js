async function findAll(){
    return ajaxGet('v1/admin/phieu-hoat-dong/find-all');
}

async function hoatDongSearch( maPhieu = "", tenHoatDong = "", ngayDau = null, ngayCuoi = null, page = 1 , size = 10 ) {
    return ajaxGet(`v1/admin/phieu-hoat-dong/search?ma-phieu=${maPhieu}&ten-hoat-dong=${tenHoatDong}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&page=${page}&size=${size}`);
}

function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}