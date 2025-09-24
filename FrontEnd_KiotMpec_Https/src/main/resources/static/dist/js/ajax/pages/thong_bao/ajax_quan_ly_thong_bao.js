//================ Declare variable===================//
var arr = [];

//input
var inputTieuDe, inputNguoiNhan, inputTuNgay, inputDenNgay;

//btn
var btnTimKiem;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-bao-cao");
    inputTieuDe = $("#bimo1");
    inputNguoiNhan = $("#bimo2");
    inputTuNgay = $("#bimo5");
    inputDenNgay = $("#bimo6");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//

    callSearchThongBao();
    checkThoiGianTHDN();
    clickSearchThongBao();

});

//=============== Function detail ===================//

//=================== Function ===================//
function clickSearchThongBao() {
    btnTimKiem.click(function () {
        let tieuDe = inputTieuDe.val();
        let nguoiNhan = inputNguoiNhan.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchThongBao(tieuDe, nguoiNhan, valTuNgay, valDenNgay, page = 1, size = 10);
        }
    });
}


//=================== Function ===================//
function callSearchThongBao(tieuDe, nguoiNhan, valTuNgay, valDenNgay, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    searchThongBao(tieuDe, nguoiNhan, valTuNgay, valDenNgay).then(rs => {
        if (rs.message === "found") {
            $('#pagination').pagination({
                dataSource: function (done) {
                    let result = [];
                    for (let i = 1; i <= rs.data.totalPages; i++) result.push(1);
                    done(result);
                },
                locator: 'items',
                totalNumber: rs.data.totalPages,
                pageSize: 1,
                showPageNumbers: true,
                showPrevious: true,
                showNext: true,
                // showNavigator: true,
                showFirstOnEllipsisShow: true,
                showLastOnEllipsisShow: true,
                callback: function (response, pagination) {
                    if (pagination.pageNumber == 1) {
                        arr = rs.data.currentElements;
                        setViewThongBao(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchThongBao(tieuDe, nguoiNhan, valTuNgay, valDenNgay, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewThongBao(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewThongBao(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy thông báo");
        setViewThongBao(1);
    });
}


//=================== Function ===================//
function setViewThongBao(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Thời gian</th>
                <th>Tiêu đề</th>
                <th>Nội dung</th>
                <th>Người nhận</th>
                <th>Yêu cầu phản hồi</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thong-bao">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewDateTime(item.thongBao.thoiGianGui)}</td>
                    <td>${viewField(item.thongBao.tieuDe)}</td>
                    <td>${viewField(item.thongBao.noiDung)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.thongBao.yeuCauPhanHoi)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td>  <td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}


//=================== Function ===================//
function checkThoiGianTHDN() {
    let rs = true;
    let {check: checkTuNgay, val: valTuNgay} = checkNgayTKDSDN(inputTuNgay);
    let {check: checkDenNgay, val: valDenNgay} = checkNgayTKDSDN(inputDenNgay);
    let selectorDenNgay = inputDenNgay.parents(".form-group");
    if (valTuNgay !== "" && valDenNgay !== "" && !compareDate(valTuNgay, valDenNgay)) {
        rs = false;
        viewError(selectorDenNgay, "Thời gian giới hạn phải lớn hơn");
    } else hiddenError(selectorDenNgay);
    return {check: rs, valTuNgay: valTuNgay, valDenNgay: valDenNgay};
}

//=================== Function ===================//
function checkNgayTKDSDN(selectorNgay) {
    let rs = false;
    let val = selectorNgay.val();
    let selector = selectorNgay.parents(".form-group");
    if (val === '' || regexDate(val)) {
        rs = true;
        hiddenError(selector);
    } else viewError(selector, "Chưa đúng định dạng ngày");
    return {check: rs, val: val};
}