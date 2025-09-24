async function thuongHieuFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/thuong-hieu/find-all?page=${page}&size=${size}`);
}

async function thuongHieuSearch(tenThuongHieu = '',page = 1, size = 10) {
    return ajaxGet(`v1/admin/thuong-hieu/search?ten-thuong-hieu=${tenThuongHieu}&page=${page}&size=${size}`);
}

async function thuongHieuUpload(thuongHieu) {
    return ajaxPost(`v1/admin/thuong-hieu/upload`, thuongHieu,1);
}

async function thuongHieuUpdate(thuongHieu) {
    return ajaxPut(`v1/admin/thuong-hieu/update`, thuongHieu,1);
}

async function thuongHieuDelete(thuongHieuId) {
    return ajaxDelete(`v1/admin/thuong-hieu/delete?id=${thuongHieuId}`);
}

async function viewSelectThuongHieu(selector) {
    let view = ``;
    thuongHieuFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenThuongHieu}</option>`).join("") + `<option value=-1>+ Thêm thương hiệu</option>`;
            selector.html(view);
            setClickThemThuongHieu(selector);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

function setClickThemThuongHieu(selector) {
    selector.find("option[value='0']").click(function () {
        console.log("Thêm thương hiệu");
    })
}