async function chucVuFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/chuc-vu/find-all-to-page?page=${page}&size=${size}`);
}

async function chucVuSearch(tenChucVu = '',page = 1, size = 10) {
    return ajaxGet(`v1/admin/chuc-vu/search?ten-chuc-vu=${tenChucVu}&page=${page}&size=${size}`);
}

async function chucVuUpload(chucVu) {
    return ajaxPost(`v1/admin/chuc-vu/upload`, chucVu,1);
}

async function chucVuUpdate(chucVu) {
    return ajaxPut(`v1/admin/chuc-vu/update`, chucVu,1);
}

async function chucVuDelete(chucVuId) {
    return ajaxDelete(`v1/admin/chuc-vu/delete?id=${chucVuId}`);
}

async function viewSelectChucVu(selector) {
    let view = ``;
    chucVuFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenChucVu}</option>`).join("") + `<option value=-1>+ Thêm chức vụ</option>`;
            selector.html(view);
            setClickThemChucVu(selector);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

async function setClickThemChucVu(selector) {
    selector.find("option[value='0']").click(function () {
        console.log("Thêm chức vụ");
    })
}