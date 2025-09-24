async function phieuTraHangSearch(chiNhanhId = 0, text = "%",page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-nhap-hang-chi-tiet/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function uploadPhieuTraHangNhap(nguoiDungId,nhaCungCapId, phieuTraHangNhap){
    return ajaxPost(`v1/admin/phieu-tra-hang-nhap/upload?nguoi-dung-id=${nguoiDungId}&nha-cung-cap-id=${nhaCungCapId}`, phieuTraHangNhap,1);
}

async function uploadPhieuThuPhieuTraHangNhap(phieuTraHangNhapId,phieuThuId, tienDaTra){
    return ajaxPost(`v1/admin/phieu-thu-hoa-don-tra-hang-nhap/phieu-tra-hang-nhap/upload?phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&tien-da-tra=${tienDaTra}`);
}

async function uploadPhieuThu(phieuThuRequest,chinhanhId,nguoiDungId,loaiThuId) {
    return ajaxPost(`v1/admin/phieu-thu/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-thu-id=${loaiThuId}`, phieuThuRequest);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}
