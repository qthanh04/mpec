async function phongBanFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/phong-ban/find-all-to-page?page=${page}&size=${size}`);
}

async function phongBanSearch(tenPhongBan = '', maPhongBan = "" , page = 1, size = 10) {
    return ajaxGet(`v1/admin/phong-ban/search?ten-phong-ban=${tenPhongBan}&ma-phong-ban=${maPhongBan}&page=${page}&size=${size}`);
}

async function phongBanUpload(phongBan) {
    return ajaxPost(`v1/admin/phong-ban/upload`, phongBan, 1);
}

async function phongBanUpdate(phongBan) {
    return ajaxPut(`v1/admin/phong-ban/update`, phongBan,1);
}

async function phongBanDelete(phongBanId) {
    return ajaxDelete(`v1/admin/phong-ban/delete?id=${phongBanId}`);
}

async function viewSelectPhongBan(selector) {
    let view = ``;
    await phongBanFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenPhongBan}</option>`).join("") + `<option value=-1>+ Thêm phòng ban</option>`;
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}