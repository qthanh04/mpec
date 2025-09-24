async function traHangSearch(chiNhanhId = 0, text = "%",page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-tra-khach-chi-tiet/search?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function uploadPhieuTraKhachChiTiet(phieuTraKhachForm) {
    return ajaxPost(`v1/admin/phieu-tra-khach-chi-tiet/upload-by-form`,phieuTraKhachForm);
}

async function uploadPhieuTraKhach(nguoiDungId,phieuTraKhach) {
    return ajaxPost(`v1/admin/phieu-tra-khach/upload?nguoi-dung-id=${nguoiDungId}`, phieuTraKhach);
}

async function createPhieuKhachTraHangPDF(ma,nguoiDungId){
    return ajaxGet(`v1/admin/phieu-tra-khach/pdf/${ma}?nguoi-dung-id=${nguoiDungId}`);
}

async function uploadPhieuChi(phieuChiRequest,chinhanhId,nguoiDungId,loaiChiId) {
    return ajaxPost(`v1/admin/phieu-chi/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-chi-id=${loaiChiId}`, phieuChiRequest);
}

async function uploadPhieuChiNhapHangTraKhach(phieuTraKhachId,phieuChiId,tienDaTra) {
    return ajaxPost(`v1/admin/phieu-chi-nhap-hang-tra-khach/phieu-tra-khach/upload?phieu-tra-khach-id=${phieuTraKhachId}&phieu-chi-id=${phieuChiId}&tien-da-tra=${tienDaTra}`);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}
