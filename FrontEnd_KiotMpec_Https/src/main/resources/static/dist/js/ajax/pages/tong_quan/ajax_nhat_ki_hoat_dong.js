//================ Declare variable===================//
var arr = [];

//input
var inputMaPhieu, inputTenHoatDong, inputTuNgay, inputDenNgay;

//btn
var btnTimKiem;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-nhat-ki-hoat-dong");
    inputMaPhieu = $("#bimo1");
    inputTuNgay = $("#bimo5");
    inputDenNgay = $("#bimo6");
    inputTenHoatDong = $("#bimo2");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//
    clickSearchPhieuHoatDong();
    checkThoiGianTHDN();
    findPhieuHoatDong();
});

//=================== Function ===================//
function findPhieuHoatDong() {
        let maPhieu = inputMaPhieu.val();
        let tenHoatDong = inputTenHoatDong.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            console.log(valTuNgay, valDenNgay);
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchPhieuHoatDong(maPhieu, tenHoatDong, valTuNgay, valDenNgay, 1, 10);
        }
}

//=================== Function ===================//
function clickSearchPhieuHoatDong() {
    btnTimKiem.click(function () {
        let maPhieu = inputMaPhieu.val();
        let tenHoatDong = inputTenHoatDong.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            console.log(valTuNgay, valDenNgay);
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            console.log("vao day 1");
            callSearchPhieuHoatDong(maPhieu, tenHoatDong, valTuNgay, valDenNgay, 1, 10);

        }
    });
}

//=================== Function ===================//
function callSearchPhieuHoatDong(maPhieu, tenHoatDong, valTuNgay, valDenNgay, page = 1, size = 10) {
    console.log("vao day 2");
    $("#pageBody").LoadingOverlay("show");
    hoatDongSearch(maPhieu, tenHoatDong, valTuNgay, valDenNgay).then(rs => {
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
                        setViewHoatDong(1);
                        return;
                    }
                    console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    hoatDongSearch(maPhieu, tenHoatDong, valTuNgay, valDenNgay, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewHoatDong(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewHoatDong(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy hoạt động");
        setViewHoatDong(1);
    });
}

//=================== Function ===================//
function setViewHoatDong(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Mã phiếu</th>
                <th>Thời gian</th>
                <th>Tên nhân viên</th>
                <th>Hoạt động</th>
                <th>Giá trị</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-hoat-dong">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.maPhieu)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.tenNguoiDung)}</td>
                    <td>${viewField(item.hoatDong)}</td>
                    <td>${viewField(item.giaTri)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td> <td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="6">Không có thông tin phù hợp</td></tr>`
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