async function nhomHangFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/nhom-hang/find-all?page=${page}&size=${size}`);
}

async function nhomHangSearch(tenNhomHang = "", maNhomHang = "" ,page = 1, size = 10) {
    return ajaxGet(`v1/admin/nhom-hang/search?ten-nhom-hang=${tenNhomHang}&ma-nhom-hang=${maNhomHang}&page=${page}&size=${size}`);
}

async function nhomHangUpload(nhomHang) {
    return ajaxPost(`v1/admin/nhom-hang/upload`, nhomHang,1);
}

async function nhomHangUpdate(nhomHang) {
    return ajaxPut(`v1/admin/nhom-hang/update`, nhomHang,1);
}

async function nhomHangDelete(nhomHangId) {
    return ajaxDelete(`v1/admin/nhom-hang/delete?id=${nhomHangId}`);
}

async function viewSelectNhomHang(selector) {
    let view = ``;
    await nhomHangFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenNhomHang}</option>`).join("") + `<option value=-1>+ Thêm nhóm hàng</option>`;
            selector.html(view);
            setClickThemNhomHang(selector);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

function setClickThemNhomHang(selector) {
    selector.find("option[value='0']").click(function () {
        console.log("Thêm đơn vị");
    })
}