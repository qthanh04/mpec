async function nhanVienFindAll() {
    return ajaxGet(`v1/admin/nhan-vien/find-all?size=9999`);
}
async function sendEmailToNguoiNhan(arrEmail) {
    return ajaxPost(`v1/admin/send-mail/list-mail`, arrEmail);
}
async function uploadThongBao(thongBao) {
    return ajaxPost(`v1/admin/thong-bao/upload`, thongBao);
}
async function uploadThongBaoNguoiNhan(thongBaoNguoiNhan) {
    return ajaxPost(`v1/admin/thong-bao-nguoi-nhan/upload`, thongBaoNguoiNhan);
}
