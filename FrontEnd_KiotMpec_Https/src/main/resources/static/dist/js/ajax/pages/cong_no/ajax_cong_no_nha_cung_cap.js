//================ Declare variable===================//
var arr = [];

//input
var  inputSearch, inputTuNgay, inputDenNgay,table;

//btn

var btnTimKiem,btnExcel;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-cong-no");
    inputSearch = $("#bimo1");
    inputTuNgay = $("#bimo3");
    inputDenNgay = $("#bimo4");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//
    viewCongNo();
    clickSearchCongNoNhaCungCap();
    checkThoiGianTHDN();
});

//=================== Function ===================//
function clickSearchCongNoNhaCungCap() {
    btnTimKiem.click(function () {
        console.log("vào đây");
        let text = inputSearch.val();
        console.log(text);
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            console.log(valTuNgay,valDenNgay);
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            CongNoNhaCungCap(valTuNgay,valDenNgay,text);
        }
    })
}

//=================== Function ===================//
function CongNoNhaCungCap(valTuNgay,valDenNgay,text) {
    $("#pageBody").LoadingOverlay("show");
    searchCongNoNhaCungCap(valTuNgay,valDenNgay,text,1,10).then(rs => {
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
                        setViewCongNo(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchCongNoNhaCungCap(valTuNgay,valDenNgay,text, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewCongNo(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewCongNo(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy còn nợ");
        setViewCongNo(1);
    });
}
//=================== Function ===================//
function viewCongNo(ngayDau,ngayCuoi) {
    $("#pageBody").LoadingOverlay("show");
    findCongNoNhaCungCap(ngayDau,ngayCuoi,1,10).then(rs => {
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
                        setViewCongNo(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    findCongNoNhaCungCap(ngayDau,ngayCuoi, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewCongNo(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewCongNo(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy còn nợ");
        setViewCongNo(1);
    });
}
//=================== Function ===================//
function setViewCongNo(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Tên khách hàng</th>
                <th>Tổng tiền</th>
                <th>Công nợ</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.ten)}</td>
                    <td>${viewField(item.tongTien)}</td>
                    <td>${viewField(item.conNo)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td>  <td></td><td></td><td></td><</tr>`
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