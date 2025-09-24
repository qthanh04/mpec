async function hoaDonSearch(chiNhanhId = 0, text = "%",page = 1, size = 10) {
    return ajaxGet(`v1/admin/hoa-don/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}
async function danhSachHoaDonChiTiet(hoaDonId = 0 ,page=1 ,size=99999){
    return ajaxGet(`v1/admin/hoa-don-chi-tiet/find-all-by-id-hoa-don?hoa-don-id=${hoaDonId}&page=${page}&size=${size}`);
}

async function createHoaDonPDF(maHoaDon,nguoiDungId){
    return ajaxGet(`v1/admin/hoa-don/pdf/${maHoaDon}?nguoi-dung-id=${nguoiDungId}`);
}
async function findAllDonViGiaoHang(){
    return ajaxGet('v1/public/partner/find-all');
}

async function PayMoMo(momo){
    return ajaxPost('v1/public/momo/pay',momo,1);
}

