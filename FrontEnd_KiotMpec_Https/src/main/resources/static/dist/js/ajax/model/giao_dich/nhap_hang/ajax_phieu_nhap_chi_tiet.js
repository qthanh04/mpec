async function danhSachNhapHangChiTiet(phieuNhapId,page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-nhap-hang-chi-tiet/find-by-phieu-nhap?phieu-nhap-id=${phieuNhapId}&page=${page}&size=${size}`);
}

async function createPhieuNhapPDF(maPN,nguoiDungId){
    return ajaxGet(`v1/admin/phieu-nhap-hang/pdf/${maPN}?nguoi-dung-id=${nguoiDungId}`);
}