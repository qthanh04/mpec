//================ Declare variable===================//
var inputSearchkhachHang, tablekhachHang, inputMoiTenkhachHang, inputMoiDiaChi, inputMoiDienThoai,
    inputMoiEmail, inputMoiFacebook, inputMoiGhiChu;
//btn
var btnThemkhachHang, btnSuakhachHang, btnXoakhachHang, btnSearchkhachHang;
var arrkhachHang = [];
var elementkhachHang = [];

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    inputSearchkhachHang = $("#bimo1");
    btnSearchkhachHang = $("#btn-search-1");
    tablekhachHang = $("#table-khach-hang");

    inputMoiTenkhachHang = $("#text-ten");
    inputMoiDiaChi = $("#text-dia-chi");
    inputMoiDienThoai = $("#text-dien-thoai");
    inputMoiEmail = $("#text-email");
    inputMoiFacebook = $("#text-facebook");
    inputMoiGhiChu = $("#text-ghi-chu");

    btnThemkhachHang = $("#btn-them");
    btnSuakhachHang = $("#btn-sua");
    btnXoakhachHang = $("#btn-xoa");

    //==========Function constructor========//
    searchkhachHang();
    clickAddkhachHang();
    clickSuakhachHang();
    clickXoakhachHang();
    clickSearchkhachHang();
    taiExcelTK();
})

//=================== Function ===================//
function clickSearchkhachHang() {
    btnSearchkhachHang.click(function () {
        searchkhachHang();
    })
}

//=================== Function ===================//
function searchkhachHang() {
    let valSearch = inputSearchkhachHang.val();
    $("#pageBody").LoadingOverlay("show");
    khachHangSearchTex(valSearch).then(rs => {
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
                        arrkhachHang = rs.data.currentElements;
                        setViewkhachHang(1);
                        $("#pageBody").LoadingOverlay("hide");
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    khachHangSearchTex(valSearch, pagination.pageNumber).then(rs => {
                        arrkhachHang = rs.data.currentElements;
                        setViewkhachHang(pagination.pageNumber);
                        $("#pageBody").LoadingOverlay("hide");
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrkhachHang = [];
            setViewkhachHang(1);
            paginationReset();
            $("#pageBody").LoadingOverlay("hide");
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Nha Cung Cap");
        setViewkhachHang(1);
        $("#pageBody").LoadingOverlay("hide");
    })
}

//=================== Function ===================//
function setViewkhachHang(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên khách hàng</th>
                    <th>Địa chỉ</th>
                    <th>Điện thoại</th>
                    <th>Email</th>
                    <th>Facebook</th>
                    <th>Ghi chú</th>
                </tr>`;
    let len = arrkhachHang.length;
    if (len > 0) {
        view += arrkhachHang.map((item, index) => `<tr data-index="${index}" class="click-nha-cung-cap">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenKhachHang)}</td>
                    <td>${viewField(item.diaChi)}</td>
                    <td>${viewField(item.dienThoai)}</td>
                    <td>${viewField(item.email)}</td>
                    <td>${viewField(item.facebook)}</td>
                    <td>${viewField(item.ghiChu)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="7">Không có thông tin phù hợp</td></tr>`
    }
    tablekhachHang.html(view);
    clickkhachHang();
}

//=================== Function ===================//
function clickkhachHang() {
    tablekhachHang.find("tr:not(.click-nha-cung-cap)").unbind("click").click(function () {
        elementkhachHang = {};
        resetViewInputMoi();
    })
    $("tr.click-nha-cung-cap").unbind("click").click(function () {
        elementkhachHang = arrkhachHang[$(this).attr("data-index")];
        setViewInputMoi();
    })
}

//=================== Function ===================//
function setViewInputMoi() {
    inputMoiTenkhachHang.val(viewField(elementkhachHang.tenKhachHang));
    inputMoiDiaChi.val(viewField(elementkhachHang.diaChi));
    inputMoiDienThoai.val(viewField(elementkhachHang.dienThoai));
    inputMoiEmail.val(viewField(elementkhachHang.email));
    inputMoiFacebook.val(viewField(elementkhachHang.facebook));
    inputMoiGhiChu.val(viewField(elementkhachHang.ghiChu));
}

//=================== Function ===================//
function resetViewInputMoi() {
    inputMoiTenkhachHang.val("");
    inputMoiDiaChi.val("");
    inputMoiDienThoai.val("");
    inputMoiEmail.val("");
    inputMoiFacebook.val("");
    inputMoiGhiChu.val("");
}


//=================== Function ===================//
function clickAddkhachHang() {
    btnThemkhachHang.click(function () {
        let {check: checkTen, val: valTen} = checkText(inputMoiTenkhachHang);
        let {check: checkDC, val: valDC} = checkText(inputMoiDiaChi);
        let {check: checkDT, val: valDT} = checkSoDienthoai();
        let {check: checkMail, val: valMail} = checkEmail();
        let valFacebook = inputMoiFacebook.val();
        let valGhiChu = inputMoiGhiChu.val();
        if (checkTen & checkDC & checkDT & checkMail) {
            let khachHang = {
                tenKhachHang: valTen,
                diaChi: valDC,
                dienThoai: valDT,
                email: valMail,
                facebook: valFacebook,
                ghiChu: valGhiChu
            }
            $("#pageBody").LoadingOverlay("show");
            khachHangUpload(khachHang).then(rs => {
                if (rs.message === "uploaded") {
                    elementkhachHang = rs.data;
                    setViewInputMoi();
                    alterSuccess("Thêm khách hàng thành công");
                    $("#pageBody").LoadingOverlay("hide");
                    searchkhachHang();
                }
            }).catch(err => {
                console.log(err);
                alterDanger("Server Error - Upload Khach Hang");
                $("#pageBody").LoadingOverlay("hide");
            })
        }
    })
}

//=================== Function ===================//
function clickSuakhachHang() {
    btnSuakhachHang.click(function () {
        let {check: checkTen, val: valTen} = checkText(inputMoiTenkhachHang);
        let {check: checkDC, val: valDC} = checkText(inputMoiDiaChi);
        let {check: checkDT, val: valDT} = checkSoDienthoai();
        let {check: checkMail, val: valMail} = checkEmail();
        let valFacebook = inputMoiFacebook.val();
        let valGhiChu = inputMoiGhiChu.val();
        if (checkTen & checkDC & checkDT & checkMail) {
            elementkhachHang.tenKhachHang = valTen;
            elementkhachHang.diaChi = valDC;
            elementkhachHang.dienThoai = valDT;
            elementkhachHang.email = valMail;
            elementkhachHang.facebook = valFacebook;
            elementkhachHang.ghiChu = valGhiChu;
            $("#pageBody").LoadingOverlay("show");
            khachHangUpdate(elementkhachHang).then(rs => {
                if (rs.message === "updated") {
                    alterSuccess("Sửa khách hàng thành công");
                    searchkhachHang();
                    $("#pageBody").LoadingOverlay("hide");
                }
            }).catch(err => {
                console.log(err);
                alterDanger("Server Error - Update Nha Cung Cap");
                $("#pageBody").LoadingOverlay("hide");
            })
        }
    })
}

//=================== Function ===================//
function clickXoakhachHang() {
    btnXoakhachHang.click(function () {
        if (Object.keys(elementkhachHang).length !== 0) {
            $('#modal-remove').modal('show');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                khachHangDelete(elementkhachHang.id).then(rs => {
                    if (rs.message = "deleted") {
                        alterSuccess(`Đã xóa khách hàng ${elementkhachHang.tenKhachHang}`);
                        searchkhachHang();
                        elementkhachHang = {};
                        $("#pageBody").LoadingOverlay("hide");
                    }
                }).catch(err => {
                    console.log(err);
                    alterDanger("Server error - Delete Nha Cung Cap");
                    $("#pageBody").LoadingOverlay("hide");
                })
                // to do remove camera
            })
        } else {
            alterInfo("Vui lòng chọn một khách hàng để thực hiện thao tác");
        }
    })
}

//=================== Function ===================//
function taiExcelTK() {
    $('#btn-excel').on('click', function () {
        let valSearch = inputSearchkhachHang.val();
        $("#pageBody").LoadingOverlay("show");
        khachHangSearchTex(valSearch, 1, 1000).then(rs => {
            let arrExcel = rs.data.currentElements;
            let nguoiDungId = sessionStorage.getItem("id");
            ajaxGet(`v1/admin/khach-hang/excel?nguoi-dung-id=${nguoiDungId}&list-khach-hang=` + arrExcel.map(khachHang => khachHang.id))
                .then(rs => {
                    $("#pageBody").LoadingOverlay("hide");
                    window.open(rs.data.url, '_blank');
                }).catch(ex => {
                console.log(ex);
                alterDanger("Tạo file excel thất bại");
                $("#pageBody").LoadingOverlay("hide");
            })
        });
    });
    clickPrintElement(".ttcttk");
}


