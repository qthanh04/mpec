async function uploadHangHoa( hangHoa,chiNhanhId, thuongHieuId, nhomHangId,donViId) {
    console.log(`v1/admin/hang-hoa/upload?chi-nhanh-id=${chiNhanhId}&thuong-hieu-id=${thuongHieuId}&nhom-hang-id=${nhomHangId}&don-vi-id=${donViId}`);
    return ajaxPost(`v1/admin/hang-hoa/upload?chi-nhanh-id=${chiNhanhId}&thuong-hieu-id=${thuongHieuId}&nhom-hang-id=${nhomHangId}&don-vi-id=${donViId}`, hangHoa);
}

async function updateHangHoa( hangHoa, thuongHieuId, nhomHangId,donViId,idDVHH) {
    return ajaxPut(`v1/admin/hang-hoa/update?thuong-hieu-id=${thuongHieuId}&nhom-hang-id=${nhomHangId}&don-vi-id=${donViId}&don-vi-hang-hoa-id=${idDVHH}`, hangHoa,1);
}

async function hangHoaFindAll() {
    return ajaxGet(`v1/admin/hang-hoa/find-all?size=9999`);
}

async function deleteImageByUrl(url){
    return ajaxDelete(`s3/deleteFile?url=${url}`);
}

async function viewSelectHangHoa(selector, all = true) {
    let view = all ? `<option value="0">Tất cả</option>` : "";
    var result = [];
    await hangHoaFindAll().then(rs => {
        if(rs.message == "found") {
            rs = rs.data.currentElements;
            result = rs;
            view += rs.map(data => `<option value="${data.id}">${viewField(data.ma)} - ${viewField(data.tenHangHoa)}</option>`).join("");
        }
    }).catch(err => {
        alterDanger("Server error find all hang hoa");
        console.log(err);
    })
    selector.html(view);
    return result;
}

async function hangHoaChiNhanhFindAll(id) {
    return ajaxGet(`v1/admin/chi-nhanh-hang-hoa/find-by-chi-nhanh?chi-nhanh-id=${id}&size=9999`);
}

async function findHangHoaDonViCoBanById(idHangHoa) {
    return ajaxGet(`v1/admin/don-vi-hang-hoa/find-don-vi-co-ban-by-hang-hoa-id?hangHoaId=${idHangHoa}`);
}

function showBarCode(tag,url,width=200,height=50){
    $.ajax({
        type: 'GET',
        headers: {
            "Authorization": ss_lg
        },
        responseType: 'image/png',
        url: url,
        timeout: 30000,
        success: function (data) {
            tag.append(`<img src="data:image/png;base64,${data.data}" width="${width}" height="${height}">`);
        },
        error: function (){
            tag.html(`<img src=''>`);
        }
    });
}