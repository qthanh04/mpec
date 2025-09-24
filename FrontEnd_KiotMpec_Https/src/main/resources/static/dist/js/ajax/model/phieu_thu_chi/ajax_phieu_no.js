async function searchPhieuNo(tenKhachHang,trangThai,page=1,size=10){
    return ajaxGet(`v1/admin/phieu-no/search?ten-khach-hang=${tenKhachHang}&trang-thai=${trangThai}&page=${page}&size=${size}`);
}

async function uploadPhieuNo(khachHangId,hoaDonId,nguoiDungId,phieuNo) {
    return ajaxPost(`v1/admin/phieu-no/upload?khach-hang-id=${khachHangId}&hoa-don-id=${hoaDonId}&nguoi-dung-id=${nguoiDungId}`,phieuNo);
}

async function sumPhieuNo(khachHangId) {
    return ajaxGet(`v1/admin/phieu-no/sum?khach-hang-id=${khachHangId}`);
}

async function updatePhieuNo(phieuNo){
    return ajaxPut(`v1/admin/phieu-no/update`,phieuNo);
}

async function setTrangThaiPhieuNo(id,trangThai) {
    return ajaxPut(`v1/admin/phieu-no/set-trang-thai?id=${id}&trang-thai=${trangThai}`);
}