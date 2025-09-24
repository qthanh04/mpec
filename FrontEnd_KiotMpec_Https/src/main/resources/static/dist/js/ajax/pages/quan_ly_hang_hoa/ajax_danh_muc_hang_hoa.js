//================ Declare variable===================//
//input
var inputSearchThuongHieu, inputSearchNhomHang, inputSearchDonVi,
    inputMoiThuongHieu, inputMoiNhomHang, inputMoiDonVi, inputMaNhomHang;

//button
var btnSearchThuongHieu, btnSearchNhomHang, btnSearchDonVi,
    btnThemThuongHieu, btnThemDonVi, btnThemNhomHang,
    btnSuaNhomHang, btnSuaDonVi, btnSuaThuongHieu,
    btnXoaNhomHang, btnXoaDonVi, btnXoaThuongHieu;

//table
var tableThuongHieu, tableNhomHang, tableDonVi;

//array
var arrThuongHieu = [], arrNhomHang = [], elementThuongHieu = [], arrDonVi = [], elementNhomHang = [],
    elementDonVi = [];

//=============== Funtion main ===================//
$(function () {
    //======mapping variabe and id=========//
    inputSearchThuongHieu = $("#bimo1");
    inputSearchNhomHang = $("#bimo2");
    inputSearchDonVi = $("#bimo3");
    inputMaNhomHang = $("#text-ma-nhom-hang");

    btnSearchThuongHieu = $("#btn-search-1");
    btnSearchNhomHang = $("#btn-search-2");
    btnSearchDonVi = $("#btn-search-3");

    tableThuongHieu = $("#table-thuong-hieu");
    tableNhomHang = $("#table-nhom-hang");
    tableDonVi = $("#table-don-vi");

    inputMoiThuongHieu = $("#text-thuong-hieu");
    inputMoiNhomHang = $("#text-nhom-hang");
    inputMoiDonVi = $("#text-don-vi");

    btnThemThuongHieu = $("#btn-them-thuong-hieu");
    btnSuaThuongHieu = $("#btn-sua-thuong-hieu");
    btnXoaThuongHieu = $("#btn-xoa-thuong-hieu");

    btnThemNhomHang = $("#btn-them-nhom-hang");
    btnSuaNhomHang = $("#btn-sua-nhom-hang");
    btnXoaNhomHang = $("#btn-xoa-nhom-hang");

    btnThemDonVi = $("#btn-them-don-vi");
    btnSuaDonVi = $("#btn-sua-don-vi");
    btnXoaDonVi = $("#btn-xoa-don-vi");


    //=======Ham khoi tao=========//
    searchThuongHieu();
    searchNhomHang();
    searchDonVi();

    clickAddThuongHieu();
    clickSuaThuongHieu();
    clickXoaThuongHieu();

    clickAddNhomHang();
    clickSuaNhomHang();
    clickXoaNhomHang();

    clickAddDonVi();
    clickSuaDonVi();
    clickXoaDonVi();

    clickSearchThuongHieu();
    clickSearchNhomHang();
    clickSearchDonVi();

    viewHiddenChucNangTH();
    viewHiddenChucNangNH();
    viewHiddenChucNangDV();
})

//=============== Funtion detail ===================//

//=============== Funtion view hidden chuc nang ===================//
function viewHiddenChucNangTH() {
    $(".danh-muc-thuong-hieu").click(function () {
        $("#modal-thuong-hieu").modal("toggle");
    })
}

function viewHiddenChucNangNH() {
    $(".danh-muc-nhom-hang").click(function () {
        $("#modal-nhom-hang").modal("toggle");
    })
}

function viewHiddenChucNangDV() {
    $(".danh-muc-don-vi").click(function () {
        $("#modal-don-vi").modal("toggle");
    })
}

//=============== Funtion click search ===================//
function clickSearchThuongHieu() {
    btnSearchThuongHieu.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchThuongHieu();
        $("#pageBody").LoadingOverlay("hide");
    })
}

function clickSearchNhomHang() {
    btnSearchNhomHang.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchNhomHang();
        $("#pageBody").LoadingOverlay("hide");
    })
}

function clickSearchDonVi() {
    btnSearchDonVi.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchDonVi();
        $("#pageBody").LoadingOverlay("hide");
    })
}

//=============== Funtion search thuong hieu ===================//
function searchThuongHieu() {
    $("#pageBody").LoadingOverlay("show");
    let valSearchThuongHieu = inputSearchThuongHieu.val();
    thuongHieuSearch(valSearchThuongHieu).then(rs => {
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
                        arrThuongHieu = rs.data.currentElements;
                        setViewThuongHieu(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    thuongHieuSearch(valSearchThuongHieu, pagination.pageNumber).then(rs => {
                        arrThuongHieu = rs.data.currentElements;
                        setViewThuongHieu(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrThuongHieu = [];
            setViewThuongHieu(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Thuong Hieu");
        setViewThuongHieu(1);
    })
}

//=============== Funtion set view thuong hieu ===================//
function setViewThuongHieu(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên thương hiệu</th>
                </tr>`;
    let len = arrThuongHieu.length;
    if (len > 0) {
        view += arrThuongHieu.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenThuongHieu)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="2">Không có thông tin phù hợp</td></tr>`
    }
    tableThuongHieu.html(view);
    clickThuongHieu();
    $("#pageBody").LoadingOverlay("hide");

}

//=============== Funtion search nhom hang ===================//
function searchNhomHang() {
    let valSearchNhomHang = inputSearchNhomHang.val();
    nhomHangSearch(valSearchNhomHang, "").then(rs => {
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
                        arrNhomHang = rs.data.currentElements;
                        setViewNhomHang(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    nhomHangSearch(valSearchNhomHang, "", pagination.pageNumber).then(rs => {
                        arrNhomHang = rs.data.currentElements;
                        setViewNhomHang(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrNhomHang = [];
            setViewNhomHang(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Nhom Hang");
        setViewNhomHang(1);
    })
}

//=============== Funtion set view nhom hang ===================//
function setViewNhomHang(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên nhóm hàng</th>
                    <th>Mã nhóm hàng</th>
                </tr>`;
    let len = arrNhomHang.length;
    if (len > 0) {
        view += arrNhomHang.map((item, index) => `<tr data-index="${index}" class="click-nhom-hang">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenNhomHang)}</td>
                    <td>${viewField(item.maNhomHang)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="3">Không có thông tin phù hợp</td></tr>`
    }
    tableNhomHang.html(view);
    clickNhomHang();
}

//=============== Funtion search don vi ===================//
function searchDonVi() {
    let valSearchDonVi = inputSearchDonVi.val();
    donViSearch(valSearchDonVi).then(rs => {
        if (rs.message === "found") {
            $('#pagination3').pagination({
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
                        arrDonVi = rs.data.currentElements;
                        setViewDonVi(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    donViSearch(valSearchDonVi, pagination.pageNumber).then(rs => {
                        arrDonVi = rs.data.currentElements;
                        setViewDonVi(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrDonVi = [];
            setViewDonVi(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Don Vi");
        setViewDonVi(1);
    })

}

//=============== Funtion set view don vi ===================//
function setViewDonVi(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên đơn vị</th>
                </tr>`;
    let len = arrDonVi.length;
    if (len > 0) {
        view += arrDonVi.map((item, index) => `<tr data-index="${index}" class="click-don-vi">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenDonVi)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="2">Không có thông tin phù hợp</td></tr>`
    }
    tableDonVi.html(view);
    clickDonVi();
    $("#pageBody").LoadingOverlay("hide");
}

function clickThuongHieu() {
    tableThuongHieu.find("tr:not(.click-thuong-hieu)").unbind("click").click(function () {
        elementThuongHieu = {};
        inputMoiThuongHieu.val("");
    })
    $("tr.click-thuong-hieu").unbind("click").click(function () {
        elementThuongHieu = arrThuongHieu[$(this).attr("data-index")];
        inputMoiThuongHieu.val(viewField(elementThuongHieu.tenThuongHieu));
    })
}

function clickNhomHang() {
    tableNhomHang.find("tr:not(.click-nhom-hang)").unbind("click").click(function () {
        elementNhomHang = {};
        inputMoiNhomHang.val("");
        inputMaNhomHang.val("");
    })
    $("tr.click-nhom-hang").unbind("click").click(function () {
        elementNhomHang = arrNhomHang[$(this).attr("data-index")];
        inputMoiNhomHang.val(viewField(elementNhomHang.tenNhomHang));
        inputMaNhomHang.val(viewField(elementNhomHang.maNhomHang));
    })
}

function clickDonVi() {
    tableDonVi.find("tr:not(.click-don-vi)").unbind("click").click(function () {
        elementDonVi = {};
        inputMoiDonVi.val("");
    })
    $("tr.click-don-vi").unbind("click").click(function () {
        elementDonVi = arrDonVi[$(this).attr("data-index")];
        inputMoiDonVi.val(viewField(elementDonVi.tenDonVi));
    })
}

function clickAddThuongHieu() {
    btnThemThuongHieu.click(function () {
        let {check: check, val: val} = checkTen(inputMoiThuongHieu);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            let thuongHieu = {
                tenThuongHieu: val
            }
            thuongHieuUpload(thuongHieu).then(rs => {
                console.log(rs.message);
                if (rs.message === "uploaded") {
                    elementThuongHieu = rs.data;
                    inputMoiThuongHieu.val(viewField(elementThuongHieu.tenThuongHieu));
                    $("#modal-thuong-hieu").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm thương hiệu thành công");
                    searchThuongHieu();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Thuong Hieu ");
            })
        }
    });
}

function clickSuaThuongHieu() {
    btnSuaThuongHieu.click(function () {
        let {check: check, val: val} = checkTen(inputMoiThuongHieu);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            elementThuongHieu.tenThuongHieu = val;
            thuongHieuUpdate(elementThuongHieu).then(rs => {
                if (rs.message === "updated") {
                    $("#modal-thuong-hieu").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa thương hiệu thành công");
                    searchThuongHieu();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Thuong Hieu");
            });
        }
    });
}

function clickXoaThuongHieu() {
    btnXoaThuongHieu.click(function () {
        if (Object.keys(elementThuongHieu).length !== 0) {
            $("#modal-thuong-hieu").modal("toggle");
            $('#modal-remove').modal('toggle');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                thuongHieuDelete(elementThuongHieu.id).then(rs => {
                    if (rs.message = "deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa thương hiệu ${elementThuongHieu.tenThuongHieu}`);
                        searchThuongHieu();
                        elementThuongHieu = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Thuong Hieu")
                });
                // to do remove camera
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterInfo("Vui lòng chọn một thương hiệu để thực hiện thao tác");
        }
    })
}

function clickAddNhomHang() {
    btnThemNhomHang.click(function () {
        let {check: check, val: val} = checkTen(inputMoiNhomHang);
        let {check: checkMa, val: valMa} = checkTen(inputMaNhomHang);
        if (check && checkMa) {
            let nhomHang = {
                tenNhomHang: val,
                maNhomHang: valMa
            }
            $("#pageBody").LoadingOverlay("show");
            nhomHangUpload(nhomHang).then(rs => {
                if (rs.message === "uploaded") {
                    elementNhomHang = rs.data;
                    inputMoiNhomHang.val(viewField(elementNhomHang.tenNhomHang));
                    inputMaNhomHang.val(viewField(elementNhomHang.maNhomHang));
                    $("#modal-nhom-hang").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm nhóm hàng thành công");
                    searchNhomHang();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Nhom Hang");
            });
        }
    })
}

function clickSuaNhomHang() {
    btnSuaNhomHang.click(function () {
        let {check: check, val: val} = checkTen(inputMoiNhomHang);
        let {check: checkMa, val: valMa} = checkTen(inputMaNhomHang);
        if (check && checkMa) {
            elementNhomHang.tenNhomHang = val;
            elementNhomHang.maNhomHang = valMa;
            $("#pageBody").LoadingOverlay("show");
            nhomHangUpdate(elementNhomHang).then(rs => {
                if (rs.message === "updated") {
                    $("#modal-nhom-hang").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa nhóm hàng thành công");
                    searchNhomHang();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Nhom Hang");
            });
        }
    })
}

function clickXoaNhomHang() {
    btnXoaNhomHang.click(function () {
        if (Object.keys(elementNhomHang).length !== 0) {
            $("#modal-nhom-hang").modal("toggle");
            $('#modal-remove').modal('toggle');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                nhomHangDelete(elementNhomHang.id).then(rs => {
                    if (rs.message = "deleted") {
                        $("#modal-nhom-hang").modal("toggle");
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa nhóm hàng ${elementNhomHang.tenNhomHang}`);
                        searchNhomHang();
                        elementNhomHang = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Nhom Hang")
                });
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterInfo("Vui lòng chọn một nhóm hàng để thực hiện thao tác");
        }
    })
}

function clickAddDonVi() {
    btnThemDonVi.click(function () {
        let {check: check, val: val} = checkTen(inputMoiDonVi);
        if (check) {
            let donVi = {
                tenDonVi: val
            };
            $("#pageBody").LoadingOverlay("show");
            donViUpload(donVi).then(rs => {
                if (rs.message === "uploaded") {
                    elementDonVi = rs.data;
                    inputMoiDonVi.val(viewField(elementDonVi.tenDonVi));
                    $("#modal-don-vi").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm đơn vị thành công");
                    searchDonVi();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Don Vi");
            });
        }
    })
}

function clickSuaDonVi() {
    btnSuaDonVi.click(function () {
        let {check: check, val: val} = checkTen(inputMoiDonVi);
        if (check) {
            elementDonVi.tenDonVi = val;
            $("#pageBody").LoadingOverlay("show");
            donViUpdate(elementDonVi).then(rs => {
                if (rs.message === "updated") {
                    $("#modal-don-vi").modal("toggle");
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa đơn vị thành công");
                    searchDonVi();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Don Vi");
            });
        }
    })
}

function clickXoaDonVi() {
    btnXoaDonVi.click(function () {
        if (Object.keys(elementDonVi).length !== 0) {
            $("#modal-don-vi").modal("toggle");
            $('#modal-remove').modal('toggle');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                donViDelete(elementDonVi.id).then(rs => {
                    if (rs.message === "deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa đơn vị ${elementDonVi.tenDonVi}`);
                        searchDonVi();
                        elementDonVi = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Don Vi")
                });
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterInfo("Vui lòng chọn một đơn vị để thực hiện thao tác");
        }
    })
}


