//================ Declare variable===================//

//input
var inputSearchNhaCungCap, inputMoiTenNhaCungCap, inputMoiDiaChi,
    inputMoiDienThoai, inputMoiEmail, inputMoiFacebook, inputMoiGhiChu;

//btn
var btnSearchNhaCungCap, btnThemNhaCungCap, btnSuaNhaCungCap, btnXoaNhaCungCap;

//table,arr
var arrNhaCungCap = [], elementNhaCungCap = [], tableNhaCungCap;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    inputSearchNhaCungCap = $("#bimo1");

    btnSearchNhaCungCap = $("#btn-search-1");
    tableNhaCungCap = $("#table-nha-cung-cap");
    inputMoiTenNhaCungCap = $("#text-ten-nha-cung-cap");
    inputMoiDiaChi = $("#text-dia-chi");
    inputMoiDienThoai = $("#text-dien-thoai");
    inputMoiEmail = $("#text-email");
    inputMoiFacebook = $("#text-facebook");
    inputMoiGhiChu = $("#text-ghi-chu");
    btnThemNhaCungCap = $("#btn-them-thuong-hieu");
    btnSuaNhaCungCap = $("#btn-sua-thuong-hieu");
    btnXoaNhaCungCap = $("#btn-xoa-thuong-hieu");

    //==========Function constructor========//
    searchNhaCungCap();
    clickAddNhaCungCap();
    clickSuaNhaCungCap();
    clickXoaNhaCungCap();
    clickSearchNhaCungCap();
    taiExcelTK();
})

//=============== Function detail ===================//

//=================== Function ===================//
function clickSearchNhaCungCap() {
    btnSearchNhaCungCap.click(function () {
        searchNhaCungCap();
    })
}

//=================== Function ===================//
function searchNhaCungCap() {
    let valSearch = inputSearchNhaCungCap.val();
    $("#pageBody").LoadingOverlay("show");
    nhaCungCapSearchText(valSearch).then(rs => {
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
                        arrNhaCungCap = rs.data.currentElements;
                        setViewNhaCungCap(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    nhaCungCapSearchText(valSearch, pagination.pageNumber).then(rs => {
                        arrNhaCungCap = rs.data.currentElements;
                        setViewNhaCungCap(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrNhaCungCap = [];
            setViewNhaCungCap(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Nha Cung Cap");
        setViewNhaCungCap(1);
    })
}

//=================== Function ===================//
function setViewNhaCungCap(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên nhà cung cấp</th>
                    <th>Địa chỉ</th>
                    <th>Điện thoại</th>
                    <th>Email</th>
                    <th>Facebook</th>
                    <th>Ghi chú</th>
                </tr>`;
    let len = arrNhaCungCap.length;
    if (len > 0) {
        view += arrNhaCungCap.map((item, index) => `<tr data-index="${index}" class="click-nha-cung-cap">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.ten)}</td>
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
    tableNhaCungCap.html(view);
    clickNhaCungCap();
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function clickNhaCungCap() {
    tableNhaCungCap.find("tr:not(.click-nha-cung-cap)").unbind("click").click(function () {
        elementNhaCungCap = {};
        resetViewInputMoi();
    })
    $("tr.click-nha-cung-cap").unbind("click").click(function () {
        elementNhaCungCap = arrNhaCungCap[$(this).attr("data-index")];
        setViewInputMoi();
    })
}

//=================== Function ===================//
function setViewInputMoi() {
    inputMoiTenNhaCungCap.val(viewField(elementNhaCungCap.ten));
    inputMoiDiaChi.val(viewField(elementNhaCungCap.diaChi));
    inputMoiDienThoai.val(viewField(elementNhaCungCap.dienThoai));
    inputMoiEmail.val(viewField(elementNhaCungCap.email));
    inputMoiFacebook.val(viewField(elementNhaCungCap.facebook));
    inputMoiGhiChu.val(viewField(elementNhaCungCap.ghiChu));
}

//=================== Function ===================//
function resetViewInputMoi() {
    inputMoiTenNhaCungCap.val("");
    inputMoiDiaChi.val("");
    inputMoiDienThoai.val("");
    inputMoiEmail.val("");
    inputMoiFacebook.val("");
    inputMoiGhiChu.val("");
}

//=================== Function ===================//
function clickAddNhaCungCap() {
    btnThemNhaCungCap.click(function () {
        $("#pageBody").LoadingOverlay("show");
        let {check: checkTen, val: valTen} = checkText(inputMoiTenNhaCungCap);
        let {check: checkDC, val: valDC} = checkText(inputMoiDiaChi);
        let {check: checkDT, val: valDT} = checkSoDienthoai();
        let {check: checkMail, val: valMail} = checkEmail();
        let valFacebook = inputMoiFacebook.val();
        let valGhiChu = inputMoiGhiChu.val();
        if (checkTen & checkDC & checkDT & checkMail) {
            let nhaCungCap = {
                ten: valTen,
                diaChi: valDC,
                dienThoai: valDT,
                email: valMail,
                facebook: valFacebook,
                ghiChu: valGhiChu
            }
            nhaCungCapUpload(nhaCungCap).then(rs => {
                if (rs.message === "uploaded") {
                    elementNhaCungCap = rs.data;
                    setViewInputMoi();
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm nhà cung cấp thành công");
                    searchNhaCungCap();
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Nha Cung Cap");
            })
        }
    })
}

//=================== Function ===================//
function clickSuaNhaCungCap() {
    btnSuaNhaCungCap.click(function () {
        $("#pageBody").LoadingOverlay("show");
        let {check: checkTen, val: valTen} = checkText(inputMoiTenNhaCungCap);
        let {check: checkDC, val: valDC} = checkText(inputMoiDiaChi);
        let {check: checkDT, val: valDT} = checkSoDienthoai();
        let {check: checkMail, val: valMail} = checkEmail();
        let valFacebook = inputMoiFacebook.val();
        let valGhiChu = inputMoiGhiChu.val();
        if (checkTen & checkDC & checkDT & checkMail) {
            elementNhaCungCap.ten = valTen;
            elementNhaCungCap.diaChi = valDC;
            elementNhaCungCap.dienThoai = valDT;
            elementNhaCungCap.email = valMail;
            elementNhaCungCap.facebook = valFacebook;
            elementNhaCungCap.ghiChu = valGhiChu;
            nhaCungCapUpdate(elementNhaCungCap).then(rs => {
                if (rs.message === "updated") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa nhà cung cấp thành công");
                    searchNhaCungCap();
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Nha Cung Cap");
            })
        }
    })
}

//=================== Function ===================//
function clickXoaNhaCungCap() {
    btnXoaNhaCungCap.click(function () {
        if (Object.keys(elementNhaCungCap).length !== 0) {
            $('#modal-remove').modal('show');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                nhaCungCapDelete(elementNhaCungCap.id).then(rs => {
                    if (rs.message = "deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa nhà cung cấp ${elementNhaCungCap.ten}`);
                        searchNhaCungCap();
                        elementNhaCungCap = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Nha Cung Cap")
                })
                // to do remove camera
            })
        } else {
            alterInfo("Vui lòng chọn một nhà cung cấp để thực hiện thao tác");
        }
    })
}

//=================== Function ===================//
function taiExcelTK() {
    $('#btn-excel').on('click', function () {
        $("#pageBody").LoadingOverlay("show");
        let valSearch = inputSearchNhaCungCap.val();
        nhaCungCapSearchText(valSearch, 1, 1000).then(rs => {
            let nguoiDungId = sessionStorage.getItem("id");
            let arrExcel = rs.data.currentElements;
            ajaxGet(`v1/admin/nha-cung-cap/excel?nguoi-dung-id=${nguoiDungId}&list-nha-cung-cap=` + arrExcel.map(ncc => ncc.id))
                .then(rs => {
                    $("#pageBody").LoadingOverlay("hide");
                    window.open(rs.data.url, '_blank');
                }).catch(ex => {
                console.log(ex);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Tạo file excel thất bại");
            })
        });
    });
    clickPrintElement(".ttcttk");
}
