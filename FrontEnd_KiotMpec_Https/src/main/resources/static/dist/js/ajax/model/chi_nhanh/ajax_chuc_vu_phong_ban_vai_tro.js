async function findChucVuById(chucVuId){
    return ajaxGet(`v1/admin/chuc-vu/find-by-id?id=${chucVuId}`);
}

async function findPhongBanById(phongBanId){
    return ajaxGet(`v1/admin/phong-ban/find-by-id?id=${phongBanId}`);
}

async function findVaiTroById(vaiTroId){
    return ajaxGet(`v1/admin/vai-tro/find-by-id?id=${vaiTroId}`);
}