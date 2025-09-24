//================ Declare variable===================//
var arr = [];
var inputTimKiem,btnTimKiem;
var table;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table");;
    inputTimKiem = $("#bimo3");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//
    callSearchHoaDonDangGiao();
    clickSearchPhieuNhapHang();
});

function callSearchHoaDonDangGiao(text) {
     $("#pageBody").LoadingOverlay("show");
    searchHoaDonDangGiao(text,1,10).then(rs => {
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
                        setViewHoaDonChuaGiao(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchHoaDonDangGiao( "",pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        console.log(arr);

                        setViewHoaDonChuaGiao(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        }
    })
}

//=================== Function ===================//
function clickSearchPhieuNhapHang() {
    btnTimKiem.click(function () {
        let text = inputTimKiem.val();
        console.log(text);
        callSearchHoaDonDangGiao(text);
    })
}

function setViewHoaDonChuaGiao(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Mã hóa đơn</th>
                <th>Tên đối tác</th>
                <th>Phí</th>
                <th>Thời gian lấy</th>
                <th>Thời gian giao</th>
                <th>Giá trị hóa đơn</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.hoaDon.ma)}</a></td>
                    <td>${viewField(item.doiTac.name)}</td>
                    <td>${formatMoney(item.fee)}</td>
                    <td>${viewField(item.pickTime)}</td>
                    <td>${viewField(item.deliverTime)}</td>
                    <td>${formatMoney(item.hoaDon.tongTien)}</td>
                    </td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td>  <td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
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


