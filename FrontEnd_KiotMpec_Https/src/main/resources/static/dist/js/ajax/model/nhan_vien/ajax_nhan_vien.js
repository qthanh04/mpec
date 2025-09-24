async function nhanVienFindAll() {
    return ajaxGet(`v1/admin/nhan-vien/find-all?size=9999`);
}

async function findNhanVienById(id=0){
    return ajaxGet(`v1/admin/nhan-vien/find-by-id?id=${id}`);
}

async function findNguoiDungPhongBanByNhanVienId(id=0){
    return ajaxGet(`v1/admin/nguoi-dung-phong-ban-chuc-vu-vai-tro/find-by-nguoi-dung-id?nguoi-dung-id=${id}`)
}

async function deleteImageByUrl(url){
    return ajaxDelete(`s3/deleteFile?url=${url}`);
}

async function viewSelectNhanVien(selector, all = true) {
    let view = all ? `<option value="0">Tất cả</option>` : "";
    await nhanVienFindAll().then(rs => {
        if(rs.message == "found") {
            rs = rs.data.currentElements;
            view += rs.map(data => `<option value="${data.id}">${viewField(data.maTaiKhoan)} - ${viewField(data.hoVaTen)}</option>`).join("");
        }
    }).catch(err => {
        alterDanger("Server error find all nhan vien");
        console.log(err);
    })
    selector.html(view);
}