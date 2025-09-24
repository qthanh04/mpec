async function donViFindAll(page = 1, size = 10) {
    return ajaxGet(`v1/admin/don-vi/find-all?page=${page}&size=${size}`);
}

async function donViHangHoaFindAll() {
    return ajaxGet(`v1/admin/don-vi-hang-hoa/find-all`);
}

async function donViSearch(tenDonVi, page = 1, size = 10) {
    return ajaxGet(`v1/admin/don-vi/search-text?text=${tenDonVi}&page=${page}&size=${size}`);
}

async function donViUpload(donVi) {
    return ajaxPost(`v1/admin/don-vi/upload`, donVi,1);
}

async function donViUpdate(donVi) {
    return ajaxPut(`v1/admin/don-vi/update`, donVi,1);
}

async function donViDelete(donViId) {
    return ajaxDelete(`v1/admin/don-vi/delete?id=${donViId}`);
}



async function viewSelectDonVi(selector) {
    let view = ``;
    donViFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data.currentElements;
            view = rs.map(data => `<option value=${data.id}>${data.tenDonVi}</option>`).join("") + `<option value=-1>+ Thêm đơn vị</option>`;
            selector.html(view);
            setClickThemDonVi(selector)
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}


function setClickThemDonVi(selector) {
    selector.find("option[value='0']").click(function () {
        console.log("Thêm đơn vị");
    })
}