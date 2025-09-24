async function thanhPhoFindAll(level=1) {
    return ajaxGet(`v1/public/location/find-by-level?level=${level}`);
}

async function findQuanHuyenByThanhPhoId(thanhPhoId=rs) {
    return ajaxGet(`v1/public/location/find-quan-huyen-by-thanh-pho-id?thanh-pho-id=${thanhPhoId}`);
}

async function findPhuongXaByQuanHuyenId(quanHuyenId=rs) {
    return ajaxGet(`v1/public/location/find-phuong-xa-by-quan-huyen-id?quan-huyen-id=${quanHuyenId}`);
}

async function viewSelectThanhPhoBenGiao(selector) {
    let view = ``;
    thanhPhoFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("") ;
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}
async function viewSelectThanhPhoBenNhan(selector) {
    let view = ``;
    thanhPhoFindAll(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("") ;
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}
async function findHoaDonById(id= 0) {
    return ajaxGet(`v1/admin/hoa-don/find-by-id?id=${id}`);
}
async function giaoHangNhanh(data,partnerId=1) {
    return ajaxPostGHN(`v1/public/transport-ghn/order?partner-id=${partnerId}`,data,1);
}
async function giaoHangTietKiem(data,partnerId) {
    return ajaxPostGTK(`v1/public/transport-ghtk/order?partner-id=${partnerId}`,data,1);
}
async function findAllByIdHoaDon(idHoaDon= 0) {
    return ajaxGet(`v1/admin/hoa-don-chi-tiet/find-all-by-id-hoa-don?hoa-don-id=${idHoaDon}`);
}
async function setTrangThaiHoaDon(hoaDonId,trangThai) {
    return ajaxPut(`v1/admin/hoa-don/trang-thai?id=${hoaDonId}&trang-thai=${trangThai}`);
}

async function getFee(hoaDon) {
    return ajaxGetFee(`v1/admin/transport/fee`,hoaDon);
}

//call api get v1/admin/
async function ajaxGetFee(url, data,token = ss_lg) {
    console.log(url);
    let rs = null;
    await $.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        headers: {
            "Authorization": token,
            "ShopId": "1278093",
            "token_ghtk": "8D0250860Fcd64aA4393C6EfcCC7eFce98D70a91",
            "token_ghn": "2c6fb2a4-dc4c-11ea-9203-666d21bb7226",
            "token_aha" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhaGEiLCJ0eXAiOiJ1c2VyIiwiY2lkIjoiODQ1ODkwMDYzNjgiLCJzdGF0dXMiOiJPTkxJTkUiLCJlb2MiOiJuZ3V5ZW5kdWNxdXkuaHVzdEBnbWFpbC5jb20iLCJub2MiOiJxdXkiLCJjdHkiOiJTR04iLCJhY2NvdW50X3N0YXR1cyI6IkFDVElWQVRFRCIsImV4cCI6MTU5ODkyNzQyOSwicGFydG5lciI6InRlc3Rfa2V5In0.5O46jQslDF4Epnz82ngpkueslOScdDZ5A-5AdtYEVHM"
        },

        url: url_api + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result,
            console.log(rs)
        }
    });
    return rs;
}



