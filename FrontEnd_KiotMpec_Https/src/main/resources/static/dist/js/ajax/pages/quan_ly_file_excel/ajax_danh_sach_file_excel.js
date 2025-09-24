var inputTuNgay, inputDenNgay, inputTenLoai, inputMaPhieu, btnTimkiem, tableFileExcel;
var arr = [];
$(function () {
    inputTuNgay = $("#bimo1");
    inputDenNgay = $("#bimo2");
    inputTenLoai = $("#bimo3");
    inputMaPhieu = $("#bimo4");
    btnTimkiem = $("#btn-search");
    tableFileExcel = $("#table-file-excel");

    callSearchFileExcel("", "", null, null, 1, 10);
    checkThoiGianTHDN();
    clickSearchFileExcel();
})

function clickSearchFileExcel() {
    btnTimkiem.click(function () {
        let tenLoai;
        if (inputTenLoai.val() == 'Tất cả') {
            tenLoai = '';
        } else {
            tenLoai = inputTenLoai.val();
        }
        console.log(tenLoai);
        let maPhieu = inputMaPhieu.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchFileExcel(tenLoai, maPhieu, valTuNgay, valDenNgay, 1, 10);
        }
    })
}

function callSearchFileExcel(tenLoai, maPhieu, valTuNgay, valDenNgay, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    searchFileExcel(tenLoai, maPhieu, valTuNgay, valDenNgay).then(rs => {
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
                        setViewFileExcel(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchFileExcel(tenLoai, maPhieu, valTuNgay, valDenNgay, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        console.log(arr);

                        setViewFileExcel(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        }
    })
}

function setViewFileExcel(pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Tên loại</th>
                <th>Mã phiếu</th>
                <th>Thời gian tạo</th>
                <th>Kiểu File</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-search">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenLoai)}</td>
                    <td>${viewField(item.maPhieu)}</td>
                    <td>${viewDateTime(item.thoiGianTao)}</td>
                    <td><a href=${item.url} target = '_blank'>${viewTypeFile(item.kieuFile)}</a></td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    tableFileExcel.html(view);
    $("#pageBody").LoadingOverlay("hide");
}

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

function viewURL(url) {
    if (url == null) {
        return "";
    }
    let rs = url.split("/");
    console.log(JSON.stringify(rs));
    return rs[rs.length - 1];
}

function viewTypeFile(type) {
    if (type == 1) {
        type = "Excel";
    } else {
        type = "PDF";
    }
    let rs = type.split("/");
    return rs;
}
