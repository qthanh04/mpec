async function searchGiaBan(chiNhanhId = 0,text = "",page = 1, size = 10) {
    console.log(`v1/admin/lich-su-gia-ban/search?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
    return ajaxGet(`v1/admin/lich-su-gia-ban/search?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function saveGiaBan(donViHangHoaId = 0, lichSuGiaBan){
    return ajaxPost(`v1/admin/lich-su-gia-ban/upload?don-vi-hang-hoa-id=${donViHangHoaId}`, lichSuGiaBan);
}

async function saveDonViHangHoa(donViHangHoa,hangHoaId,donViId) {
    return ajaxPost(`v1/admin/don-vi-hang-hoa/upload?hang-hoa-id=${hangHoaId}&don-vi-id=${donViId}`,donViHangHoa);
}


