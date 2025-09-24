//================ Declare variable===================//
var arr = [];
var inputMaHoaDon, inputTenKhachHang, inputTenNhanVien, selectChiNhanh,
    inputTuNgay, inputDenNgay, btnTimKiem, btnXacNhan, table;
var trangThai = 3;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table");
    inputMaHoaDon = $("#bimo1");
    inputTenKhachHang = $("#bimo2");
    inputTenNhanVien = $("#bimo3");
    selectChiNhanh = $("#bimo4");
    inputTuNgay = $("#bimo5");
    inputDenNgay = $("#bimo6");
    btnTimKiem = $("#btn-search");
    selectDonViGiaoHang = $("#select-don-vi-giao-hang");
    btnXacNhan = $("#confirm-btn");

    //==========Function constructor========//
    viewChiNhanh();
    clickSearchHoaDonChuaGiao();
    checkThoiGianTHDN();
    taiExcelTK();
});

//=============== Function detail ===================//

function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    callSearchHoaDonByChiNhanh(chiNhanhId);
    if (chucvuId == 1) {
        viewSelectChiNhanhFindByTongCongTy().then(rs => {
            selectChiNhanh.html(`<option value="0">Tất cả chi nhánh</option>` + rs);
            runSelect2();
            selectChiNhanh.change(function () {
                // inputSearch.val("");
                btnTimKiem.trigger("click");
            })
        }).catch(err => {
            console.log(err);
            alterDanger("Server Error - Select Chi Nhanh");
        })
    } else {
        let option = new Option(user.diachicn, chiNhanhId, true, true);
        selectChiNhanh.append(option).trigger('change');
        selectChiNhanh.prop("disabled", true);
    }
}

function callSearchHoaDonByChiNhanh(chiNhanhId) {
    $("#pageBody").LoadingOverlay("show");
    findAllHoaDonChuaGiao(chiNhanhId, trangThai, 1, 10).then(rs => {
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
                    findAllHoaDonChuaGiao(chiNhanhId, trangThai, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewHoaDonChuaGiao(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewHoaDonChuaGiao(1);
            paginationReset();
        }
    })
}

function clickSearchHoaDonChuaGiao() {
    btnTimKiem.click(function () {
        let maHoaDon = inputMaHoaDon.val();
        let tenKhachHang = inputTenKhachHang.val();
        let tenNhanVien = inputTenNhanVien.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchHoaDonChuaGiao(maHoaDon, tenKhachHang, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 10);
        }
    });
}

//=================== Function ===================//
function callSearchHoaDonChuaGiao(maHoaDon, tenKhachHang, tenNhanVien, valTuNgay, valDenNgay, trangThai, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    searchHoaDonHangChuaGiao(chiNhanhId, maHoaDon, tenKhachHang, tenNhanVien, valTuNgay, valDenNgay, trangThai).then(rs => {
        if (rs.message === "found") {
            console.log(rs);
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
                    searchHoaDonHangChuaGiao(chiNhanhId, maHoaDon, tenKhachHang, tenNhanVien, valTuNgay, valDenNgay, trangThai, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewHoaDonChuaGiao(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewHoaDonChuaGiao(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy hóa đơn");
        setViewHoaDonChuaGiao(1);
    });

}

function setViewHoaDonChuaGiao(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Mã hóa đơn</th>
                <th>Thời gian</th>
                <th>Nhân viên</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Tiền khách trả</th>
                <th>Tiền trả lại khách</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td><a href="hoa-don-chi-tiet?id=${item.id}" target="_blank">${viewField(item.ma)}</a></td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.khachHang.tenKhachHang)}</td>
                    <td>${formatMoney(item.tongTien)}</td>
                    <td>${formatMoney(item.tienKhachTra)}</td>
                    <td>${formatMoney(item.tienTraLaiKhach)}</td>
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

//=================== Function ===================//
//tai excel va in thong tin hoa don
function taiExcelTK() {
    let arrExcel = [];
    $('#btn-excel').on('click', function () {
        let maHoaDon = inputMaHoaDon.val();
        let tenKhachHang = inputTenKhachHang.val();
        let tenNhanVien = inputTenNhanVien.val();
        let trangThai = selectTrangThai.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            searchHoaDonHangChuaGiao(chiNhanhId, maHoaDon, tenKhachHang, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 1000).then(rs => {
                arrExcel = rs.data.currentElements;
                let nguoiDungId = sessionStorage.getItem("id");
                console.log(arrExcel);
                ajaxGet(`v1/admin/hoa-don/excel?nguoi-dung-id=${nguoiDungId}&list-hoa-don=` + arrExcel.map(hoaDon => hoaDon.id))
                    .then(rs => {
                        alterSuccess("Tạo file excel thành công");
                        window.open(rs.data.url, '_blank');
                    }).catch(ex => {
                    console.log(ex);
                    alterDanger("Tạo file excel thất bại");
                })
                alterSuccess("Created Excel Success");
            });
            console.log(arrExcel);
        }
    });
    clickPrintElement(".ttcttk");
}