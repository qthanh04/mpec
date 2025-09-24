async function vaiTroFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/vai-tro/find-all-to-page?page=${page}&size=${size}`);
}

async function vaiTroSearch(tenVaiTro = '',page = 1, size = 10) {
    return ajaxGet(`v1/admin/vai-tro/search?ten-vai-tro=${tenVaiTro}&page=${page}&size=${size}`);
}

async function vaiTroUpload(vaiTro) {
    return ajaxPost(`v1/admin/vai-tro/upload`, vaiTro,1);
}

async function vaiTroUpdate(vaiTro) {
    return ajaxPut(`v1/admin/vai-tro/update`, vaiTro,1);
}

async function vaiTroDelete(vaiTroId) {
    return ajaxDelete(`v1/admin/vai-tro/delete?id=${vaiTroId}`);
}

async function viewSelectVaiTro(selector) {
    let view = ``;
    vaiTroFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenVaiTro}</option>`).join("") + `<option value=-1>+ Thêm vai trò</option>`;
            selector.html(view);
            setClickThemVaiTro(selector);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

async function setClickThemVaiTro(selector) {
    selector.find("option[value='0']").click(function () {
        console.log("Thêm vai trò");
    })
}