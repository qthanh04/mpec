//================ Declare variable===================//
var selectChiNhanh, inputSearch, btnSearch, table, table2;
var arr = [], arr2 = [];

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    box = $("#box-thongtin");
    btnThem = $("#btn-them");
    selectChiNhanh = $("#bimo2");
    inputSearch = $("#bimo1");
    btnSearch = $("#btn-1");
    table = $("#table-hang-hoa");
    table2 = $("#table-phieu-nhap");

    //==========Function constructor========//

    viewChiNhanh();
    callSearch();
    clickSearchHH();
    clickThemPhieu();
})

//=============== Function detail ===================//

function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    callSearchHoaDon(chiNhanhId);
    if (chucvuId == 1) {
        viewSelectChiNhanhFindByTongCongTy().then(rs => {
            selectChiNhanh.html(`<option value="0">Tất cả chi nhánh</option>` + rs);
            runSelect2();
            selectChiNhanh.change(function () {
                // inputSearch.val("");
                btnSearch.trigger("click");
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

//=================== Function ===================//
function clickThemPhieu() {
    btnThem.click(function () {
        callSearchHoaDon();
    });
}
const TRANG_THAI_HOA_DON = ["Lưu Tạm", "Đang Giao", "Đã Giao", "Đang Đóng Gói", "Khách Hủy", "Đơn Vị Giao Hủy"];


function viewTrangThaiHD(hoaDon) {
    return TRANG_THAI_HOA_DON[hoaDon.trangThai];
}
const TRANG_THAI_PHIEU_NHAP_DE_TRA = ["Đang Chờ", "Đã trả"];
function viewTrangThaiPN(phieuTraHang) {
    return TRANG_THAI_PHIEU_NHAP_DE_TRA[phieuTraHang.trangThai];
}


//=================== Function ===================//
function clickSearchHH() {
    btnSearch.click(function () {
        let chiNhanhId = selectChiNhanh.val(), text = inputSearch.val();
        callSearch(chiNhanhId, text);
    })
}

//=================== Function ===================//
// tim kiem theo chi nhanh id = 1
function callSearchHoaDon(chiNhanhId) {
    let text = "";
    hoaDonSearch(chiNhanhId, text).then(rs => {
        if (rs.message === "found") {
            $('#pagination2').pagination({
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
                        arr2 = rs.data.currentElements;
                        setViewHoaDon(1);
                        return;
                    }
                    console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    hoaDonSearch(chiNhanhId, text, pagination.pageNumber).then(rs => {
                        arr2 = rs.data.currentElements;
                        setViewHoaDon(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr2 = [];
            setView(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Hang Hoa");
        setViewHoaDon(1);
    })
}

//=================== Function ===================//
function setViewHoaDon(pageNumber) {
    let view2 = `<tr>
                <th>STT</th>
                <th>Mã hóa đơn</th>
                <th>Thời gian</th>
                <th>Nhân viên</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Tiền khách trả</th>
                <th>Tiền trả lại khách</th>
                <th>Trạng thái</th>
                <th></th>
                </tr>`;
    let len = arr2.length;
    if (len > 0) {
        view2 += arr2.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.ma)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.khachHang.tenKhachHang)}</td>
                    <td>${formatMoney(item.tongTien)}</td>
                    <td>${viewField(item.tienKhachTra)}</td>
                    <td>${viewField(item.tienTraLaiKhach)}</td>
                    <td>${viewTrangThaiHD(item)}</td>
                    <td><button><a href="/chi-tiet-hoa-don-de-tra?hoa-don-id=${item.id}" target="_blank">Chọn</a></button></td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view2 += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view2 += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table2.html(view2);
}

//=================== Function ===================//
function callSearch(chiNhanhId = 0, text = "%") {
    $("#pageBody").LoadingOverlay("show");
    traHangSearch(chiNhanhId, text).then(rs => {
        if (rs.message === "found") {
            $('#pagination1').pagination({
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
                        setView(1);
                        return;
                    }
                    console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    traHangSearch(chiNhanhId, text, pagination.pageNumber).then(rs => {
                        arr = rs.data.currentElements;
                        setView(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setView(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Không tìm thấy phiếu trả hàng nhập nào");
        setView(1);
    })
}

//=================== Function ===================//
function setView(pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Mã phiếu</th>
                <th>Thời gian</th>
                <th>Nhân viên</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Lý do</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.phieuTraKhach.ma)}</td>
                    <td>${viewDateTime(item.phieuTraKhach.thoiGian)}</td>
                    <td>${viewField(item.phieuTraKhach.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.hoaDonChiTiet.hoaDon.khachHang.tenKhachHang)}</td>
                    <td>${formatMoney(item.phieuTraKhach.tienTraKhach)}</td>
                    <td>${viewTrangThaiPN(item.phieuTraKhach)}</td>
                    <td>${viewField(item.phieuTraKhach.lyDo)}</td>
                </tr>`
        );
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}
