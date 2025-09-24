//================ Declare variable===================//
var inputSearch, textSoLuongTaiKhoan, inputHoVaTen, inputTaiKhoan, inputEmail, inputSDT,
    inputDiaChi, inputNgaySinh, inputTGKT, inputTGKH, inputTGHH, gioiTinh, trangThai,
    anhDaiDien, iconThemTaiKhoan, kichHoat, gTNam, avatarImg, inputTGKH, radioGT, file;

//btn
var btnSearch, btnLamMoi, btnThemMoi, btnXoa, btnCapNhap

//select
var selectChiNhanh, dataTable, selectChiNhanhMoi;

//other
var totalElementsTaiKhoan = 0, arrElements = [], elementTaiKhoan = null, currentPageTaiKhoan = 0, validator;
const defaultAvatar = "https://mpec.s3.us-east-2.amazonaws.com/default_ava.png";
const defaultQRCode = "https://mpec.s3.us-east-2.amazonaws.com/qr_code_mpec_lab.png";

const TRANG_THAI_Tai_Khoan = ["kích hoạt", "khóa"];

function viewTrangThaiTaiKhoan(taiKhoan) {
    return TRANG_THAI_Tai_Khoan[taiKhoan.trangThai];
}

//=============== Function main ===================//
$(function () {

    //=========Mapping variabe and id=========//
    selectChiNhanh = $("#select-1");
    inputSearch = $("#input-text-1");
    btnSearch = $("#btn-1");

    textSoLuongTaiKhoan = $("#text-1");
    dataTable = $("#data-table");
    inputHoVaTen = $("#input-text-2");
    inputTaiKhoan = $("#label-2");
    inputEmail = $("#input-text-3");
    inputSDT = $("#input-text-4");
    inputDiaChi = $("#input-text-5");
    inputNgaySinh = $("#input-date-1");
    inputTGKT = $("#input-date-4");
    inputTGKH = $("#input-date-2");
    inputTGHH = $("#input-date-3");
    gioiTinh = $("input[name='gender']");
    trangThai = $("input[name='status']");

    anhDaiDien = $("#customFile-1");
    avatarImg = $("#ava-img");
    btnLamMoi = $(".them-moi");
    btnThemMoi = $("#btn-submit");
    btnCapNhap = $("#btn-cap-nhap");
    iconThemTaiKhoan = $(".them-moi-tk");
    btnXoa = $(".btn-xoa");
    selectChiNhanhMoi = $("#select-4");

    radioGT = $("#radio-1");
    gTNam = $("#gTNam");
    kichHoat = $("#kichHoat");
    file = $("#customFile-1");

    //==========Function constructor========//
    viewChiNhanh();
    validateForm();
    addValidatorMethod();
    initView();
    submitTK();
    clickRefeshInforTaiKhoan();
    deleteTaiKhoan();
    chooseAvatar();
    clickThemTaiKhoan();
    clickSearchTK();
    lockTaiKhoan();
    taiExcelTK();

})

//=============== Function detail ===================//
async function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    if (chucvuId === 1) {
        callSearchTaiKhoan(0);
        await viewSelectChiNhanhFindByTongCongTy().then(rs => {
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

function clickSearchTK() {
    btnSearch.click(function () {
        $("#pageBody").LoadingOverlay("show");
        console.log("search");
        let chiNhanhId = selectChiNhanh.val(), text = inputSearch.val();
        callSearchTaiKhoan(chiNhanhId, text);
        $("#pageBody").LoadingOverlay("hide");
    })
}

//=================== Function ===================//
async function callSearchTaiKhoan(chiNhanhId, text = "") {
    $("#pageBody").LoadingOverlay("show");
    await findTaiKhoanByChiNhanhAndText(chiNhanhId, text).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            console.log(rs);
            totalElementsTaiKhoan = rs.totalElements;
            arrElements = rs.content;
            currentPageTaiKhoan = 1;
            viewDataTaiKhoan(1);
            $("#click-load-data").unbind("click").click(function () {
                if (arrElements.length >= totalElementsTaiKhoan) {
                    loadEndData();
                } else {
                    findTaiKhoanByChiNhanhAndText(chiNhanhId, text, ++currentPageTaiKhoan).then(rs => {
                        if (rs.message == "found") {
                            console.log(rs.data.currentElements);
                            arrElements = arrElements.concat(rs.data.content);
                            viewDataTaiKhoan(currentPageTaiKhoan);
                        }
                    }).catch(err => {
                        console.log(err);
                    })
                }
            })
        } else {
            totalElementsTaiKhoan = 0;
            arrElements = [];
            viewDataTaiKhoan(1);
            loadEndData();
        }
        viewTongSoTaiKhoan();
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search TaiKhoan");
    });

}

//=================== Function ===================//
function viewTongSoTaiKhoan() {
    let text = (totalElementsTaiKhoan + "").length == 1 && totalElementsTaiKhoan != 0 ? "0" + totalElementsTaiKhoan : totalElementsTaiKhoan;
    textSoLuongTaiKhoan.text(text);
}

//=================== Function ===================//
function viewDataTaiKhoan(page) {
    let view = "";
    if (arrElements !== null && arrElements.length > 0) {
        view += arrElements.map((data, index) => `<tr data-index="${index}">
                                <td>${index + 1}</td>
                                <td>${viewField(data.nguoiDung.maTaiKhoan)}</td>
                                <td>${viewField(data.nguoiDung.taiKhoan)}</td>
                                <td>${viewTrangThaiTaiKhoan(data.nguoiDung)}</td>
                                <td>
                                  <div class="chucnang">
                                    <i class="fas fa-pen sua"></i>
                                    <i class="fas fa-lock btn-khoa" data-toggle="modal" data-target="#modal-change" ></i>
                                    <i class="fas fa-trash-alt btn-xoa" data-toggle="modal" data-target="#modal-remove"></i>
                                  
                                   </div>
                                  </td>
                          
                            </tr>`)
    } else {
        view = '<tr><td colspan="9"><strong>Không có thông tin thích hợp!</strong></td></tr>';
    }
    if (page == 1) resetIconLoadData();
    $(dataTable).find("tr:not(#click-load-data)").remove();
    $(view).insertBefore("#click-load-data");
    clickElementTableTaiKhoan();
    btnThemMoi.prop('disabled', true);
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function clickRefeshInforTaiKhoan() {
    elementTaiKhoan = null;
    inputTaiKhoan.val("");
    inputHoVaTen.val("");
    inputEmail.val("");
    inputSDT.val("");
    inputDiaChi.val("");
    inputNgaySinh.val("");
    inputTGKT.val("");
    inputTGHH.val("");
    $("#btn-submit").val("Thêm mới");
    $("#qrcode").empty();
    $("#ava-img").empty();
    elementTaiKhoan = null;
    $(anhDaiDien).val('');
    initView();
    validator.resetForm();
    btnThemMoi.prop('disabled', false);
    btnCapNhap.prop('disabled', true);
}

//=================== Function ===================//
function clickThemTaiKhoan() {
    iconThemTaiKhoan.click(function () {
        clickRefeshInforTaiKhoan();

    })
}

//=================== Function ===================//
function chooseAvatar() {
    $("#customFile-1").change(function () {
        let file = this.files[0];
        const fileType = file['type'];
        const validImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];
        if ($.inArray(fileType, validImageTypes) < 0) {
            $("#validateFormError").text('Chỉ được sử dụng file định dạng jpg,jpeg,png');
        } else {
            $("#validateFormError").text('');
            showPreviewAvatar(this);
        }
    });
}

//=================== Function ===================//
function showPreviewAvatar(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            $("#ava-img").empty();
            $("#ava-img").html(`<img src="${e.target.result}" width="70%">`);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

//=================== Function ===================//
function clickElementTableTaiKhoan() {
    $(dataTable).find("tr:not(#click-load-data)").unbind("click").click(function () {
        inputHoVaTen.focus();
        let valIndex = $(this).attr("data-index");
        elementTaiKhoan = {
            nguoiDung: arrElements[valIndex].nguoiDung,
            chiNhanhId: arrElements[valIndex].chiNhanh.id,
            chucVuId: arrElements[valIndex].chucVu.id,
            phongBanId: arrElements[valIndex].phongBan.id,
            vaiTroId: arrElements[valIndex].vaiTro.id
        }
        viewInforelementTaiKhoan_Click();
        btnCapNhap.prop('disabled', false);
        btnThemMoi.prop('disabled', true);
    })
}

//=================== Function ===================//
function showQRCode(tag, url, width = 100, height = 100) {
    $.ajax({
        type: 'GET',
        headers: {
            "Authorization": ss_lg
        },
        responseType: 'image/png',
        url: url,
        timeout: 30000,
        success: function (data) {
            tag.append(`<img src="data:image/png;base64,${data.data}" width="${width}" height="${height}">`);
        },
        error: function () {
            console.log("error");
            tag.html(`<img src=''>`);
        }
    });
}

//=================== Function ===================//
function submitTaiKhoan() {
    if (elementTaiKhoan == null) {
        let valHVT = $("#input-text-2").val();
        let valEmail = $("#input-text-3").val();
        let valSdt = $("#input-text-4").val();
        let valDC = $("#input-text-5").val();
        let valns = $("#input-date-1").val();
        let valgt = $("input[name='gender']:checked").val();
        let valtgkh = $("#input-date-2").val();
        let valttghh = $("#input-date-3").val();
        let valtt = $("input[name='status']:checked").val();
        uploadFilesAWS(file).then(rs => {
            if (rs !== null && rs.length > 0) {
                console.log(rs);
                let taiKhoanNew = {
                    email: valEmail,
                    soDienThoai: valSdt,
                    hoVaTen: valHVT,
                    diaChi: valDC,
                    ngaySinh: valns,
                    gioiTinh: valgt,
                    thoiGianKichHoat: valtgkh,
                    thoiGianHetHan: valttghh,
                    trangThai: valtt,
                    urlAnhDaiDien: rs
                }
                console.log(taiKhoanNew);
                let chiNhanhId = $('#select-search-1').val();
                let chucVuId = $('#select-search-2').val();
                let phongBanId = $('#select-search-3').val();
                let vaiTroId = $('#select-search-4').val();
                uploadTaiKhoan(taiKhoanNew, chiNhanhId, phongBanId, chucVuId, vaiTroId).then(rs => {
                    if (rs.message === "uploaded") {
                        btnSearch.trigger("click");
                        elementTaiKhoan = rs.data;
                        viewInforelementTaiKhoan_Search();
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Thêm tài khoản thành công");

                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterWarning("Server error - Upload TaiKhoan");
                })
            } else {
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server error - Upload file");
            }
        }).catch(err => {
            console.log(err);
            $("#pageBody").LoadingOverlay("hide");
            alterDanger("Server error - Upload file");
        });
    }
}

//=================== Function ===================//
async function submitUpdateTaiKhoan() {
    let valHVT = $("#input-text-2").val();
    let valEmail = $("#input-text-3").val();
    let valSdt = $("#input-text-4").val();
    let valDC = $("#input-text-5").val();
    let valns = $("#input-date-1").val();
    let valgt = $("input[name='gender']:checked").val();
    let valtgkh = $("#input-date-2").val();
    let valtt = $("input[name='status']:checked").val();
    let valttghh = $("#input-date-3").val();
    let chiNhanhId = $('#select-search-1').val();
    let chucVuId = $('#select-search-2').val();
    let phongBanId = $('#select-search-3').val();
    let vaiTroId = $('#select-search-4').val();
    if ($(anhDaiDien).val().length > 0) {
        await uploadFilesAWS(file).then(rs => {
            if (rs !== null) {
                let taiKhoan = {
                    id: elementTaiKhoan.nguoiDung.id,
                    email: valEmail,
                    soDienThoai: valSdt,
                    hoVaTen: valHVT,
                    diaChi: valDC,
                    ngaySinh: valns,
                    gioiTinh: valgt,
                    thoiGianKichHoat: valtgkh,
                    thoiGianHetHan: valttghh,
                    trangThai: valtt,
                    urlAnhDaiDien: rs
                }

                updateTaiKhoan(taiKhoan, chiNhanhId, phongBanId, chucVuId, vaiTroId).then(rs => {
                    console.log(rs);
                    if (rs.message === "updated") {
                        btnSearch.trigger("click");
                        elementTaiKhoan = rs.data;
                        viewInforelementTaiKhoan_Search();
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Sửa thông tin tài khoản thành công");
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Update tài khoản");
                });
            }
        }).catch(err => {
            console.log(err);
            $("#pageBody").LoadingOverlay("hide");
            alterDanger("Server error - Upload file");
        });
    } else {
        let taiKhoan = {
            id: elementTaiKhoan.nguoiDung.id,
            email: valEmail,
            soDienThoai: valSdt,
            hoVaTen: valHVT,
            diaChi: valDC,
            ngaySinh: valns,
            gioiTinh: valgt,
            thoiGianHetHan: valttghh,
            thoiGianKichHoat: valtgkh,
            trangThai: valtt,
            urlAnhDaiDien: elementTaiKhoan.nguoiDung.urlAnhDaiDien
        }

        updateTaiKhoan(taiKhoan, chiNhanhId, phongBanId, chucVuId, vaiTroId).then(rs => {
            console.log(rs);
            if (rs.message === "updated") {
                btnSearch.trigger("click");
                elementTaiKhoan = rs.data;
                viewInforelementTaiKhoan_Search();
                $("#pageBody").LoadingOverlay("hide");
                alterSuccess("Sửa thông tin tài khoản thành công");
            }
        }).catch(err => {
            console.log(err);
            $("#pageBody").LoadingOverlay("hide");
            alterDanger("Server error - Update tài khoản");
        })
    }
}

//=================== Function ===================//
function deleteTaiKhoan() {
    // to do check element remove
    $("#confirm-yes").unbind("click").click(function () {
        xoaTaiKhoan(elementTaiKhoan.nguoiDung.id).then(rs => {
            if (rs.message = "deleted") {
                alterSuccess(`Đã xóa tài khoản ${elementTaiKhoan.nguoiDung.maTaiKhoan}`);
                btnLamMoi.trigger("click");
                btnSearch.trigger("click");
            }
        }).catch(err => {
            console.log(err);
            alterDanger("Server error - Delete Account")
        })
        // to do remove TaiKhoan
    })

}

//=================== Function ===================//
function lockTaiKhoan() {
    // to do check element remove
    $("#change-yes").unbind("click").click(function () {
        if (elementTaiKhoan.nguoiDung.trangThai == 1) {
            alterDanger("Tài khoản đã ở trạng thái khóa")
        } else {
            khoaTaiKhoan(elementTaiKhoan.nguoiDung.id).then(rs => {
                if (rs.message = "success") {
                    alterSuccess(`Đã khóa tài khoản ${elementTaiKhoan.nguoiDung.maTaiKhoan}`);
                    btnLamMoi.trigger("click");
                    btnSearch.trigger("click");
                }
            }).catch(err => {
                console.log(err);
                alterDanger("Server error - Lock Account")
            })
        }
        // to do remove TaiKhoan
    })
}

//=================== Function ===================//
function viewInforelementTaiKhoan_Click() {
    validator.resetForm();
    inputHoVaTen.val(viewField(elementTaiKhoan.nguoiDung.hoVaTen));
    inputTaiKhoan.val(viewField(elementTaiKhoan.nguoiDung.taiKhoan));
    inputEmail.val(viewField(elementTaiKhoan.nguoiDung.email));
    inputSDT.val(viewField(elementTaiKhoan.nguoiDung.soDienThoai));
    inputDiaChi.val(viewField(elementTaiKhoan.nguoiDung.diaChi));
    inputNgaySinh.val(viewField(elementTaiKhoan.nguoiDung.ngaySinh));
    inputTGKH.val(viewField(elementTaiKhoan.nguoiDung.thoiGianKichHoat));
    inputTGKT.val(viewField(elementTaiKhoan.nguoiDung.thoiGianKhoiTao));
    inputTGHH.val(viewField(elementTaiKhoan.nguoiDung.thoiGianHetHan));
    console.log(elementTaiKhoan);

    findChiNhanhById(elementTaiKhoan.chiNhanhId).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.diaChi, rs.data.id, true, true);
            $(`#select-search-1`).append(option).trigger('change');

        }
    });
    findChucVuById(elementTaiKhoan.chucVuId).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenChucVu, rs.data.id, true, true);
            $(`#select-search-2`).append(option).trigger('change');

        }
    });
    findPhongBanById(elementTaiKhoan.phongBanId).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenPhongBan, rs.data.id, true, true);
            $(`#select-search-3`).append(option).trigger('change');

        }
    });
    findVaiTroById(elementTaiKhoan.vaiTroId).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenVaiTro, rs.data.id, true, true);
            $(`#select-search-4`).append(option).trigger('change');

        }
    });
    if (elementTaiKhoan.nguoiDung.gioiTinh == 1) {
        $('#gTNam').prop('checked', true);
        $('#gTNu').prop('checked', false);
    } else if (elementTaiKhoan.nguoiDung.gioiTinh == 0) {
        $('#gTNam').prop('checked', false);
        $('#gTNu').prop('checked', true);
    } else {
        $('#gTNam').prop('checked', false);
        $('#gTNu').prop('checked', false);
    }

    if (elementTaiKhoan.nguoiDung.trangThai == 0) {
        $('#kichHoat').prop('checked', true);
        $('#khoa').prop('checked', false);
    } else if (elementTaiKhoan.nguoiDung.trangThai == 1) {
        $('#kichHoat').prop('checked', false);
        $('#khoa').prop('checked', true);
    } else {
        $('#kichHoat').prop('checked', false);
        $('#khoa').prop('checked', false);
    }
    $('#group-1').show();
    $('#group-2').show();
    $('#group-3').show();
    $('#group-4').show();
    $("#qrcode").empty();
    showQRCode($("#qrcode"), `${url_api}v1/admin/tai-khoan/qrcode/${elementTaiKhoan.nguoiDung.id}`, 200, 200);

    let avatar = elementTaiKhoan.nguoiDung.urlAnhDaiDien;
    if (avatar == null) {
        elementTaiKhoan.nguoiDung.urlAnhDaiDien = defaultAvatar;
        avatar = defaultAvatar;
    } else {
        avatarImg.empty();
        avatarImg.html(`<img src="${avatar}" width="70%">`);
    }
    $("#btn-submit").val("Cập nhật");
}

//=================== Function ===================//
function viewInforelementTaiKhoan_Search() {
    validator.resetForm();
    inputHoVaTen.val(viewField(elementTaiKhoan.nguoiDung.hoVaTen));
    inputTaiKhoan.val(viewField(elementTaiKhoan.nguoiDung.taiKhoan));
    inputEmail.val(viewField(elementTaiKhoan.nguoiDung.email));
    inputSDT.val(viewField(elementTaiKhoan.nguoiDung.soDienThoai));
    inputDiaChi.val(viewField(elementTaiKhoan.nguoiDung.diaChi));
    inputNgaySinh.val(viewField(elementTaiKhoan.nguoiDung.ngaySinh));
    inputTGKH.val(viewField(elementTaiKhoan.nguoiDung.thoiGianKichHoat));
    inputTGKT.val(viewField(elementTaiKhoan.nguoiDung.thoiGianKhoiTao));
    inputTGHH.val(viewField(elementTaiKhoan.nguoiDung.thoiGianHetHan));
    console.log(elementTaiKhoan);

    findChiNhanhById(elementTaiKhoan.chiNhanh.id).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.diaChi, rs.data.id, true, true);
            $(`#select-search-1`).append(option).trigger('change');

        }
    });
    findChucVuById(elementTaiKhoan.chucVu.id).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenChucVu, rs.data.id, true, true);
            $(`#select-search-2`).append(option).trigger('change');

        }
    });
    findPhongBanById(elementTaiKhoan.phongBan.id).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenPhongBan, rs.data.id, true, true);
            $(`#select-search-3`).append(option).trigger('change');

        }
    });
    findVaiTroById(elementTaiKhoan.vaiTro.id).then(rs => {
        if (rs.message == 'found') {
            let option = new Option(rs.data.tenVaiTro, rs.data.id, true, true);
            $(`#select-search-4`).append(option).trigger('change');

        }
    });
    if (elementTaiKhoan.nguoiDung.gioiTinh == 1) {
        $('#gTNam').prop('checked', true);
        $('#gTNu').prop('checked', false);
    } else if (elementTaiKhoan.nguoiDung.gioiTinh == 0) {
        $('#gTNam').prop('checked', false);
        $('#gTNu').prop('checked', true);
    } else {
        $('#gTNam').prop('checked', false);
        $('#gTNu').prop('checked', false);
    }

    if (elementTaiKhoan.nguoiDung.trangThai == 0) {
        $('#kichHoat').prop('checked', true);
        $('#khoa').prop('checked', false);
    } else if (elementTaiKhoan.nguoiDung.trangThai == 1) {
        $('#kichHoat').prop('checked', false);
        $('#khoa').prop('checked', true);
    } else {
        $('#kichHoat').prop('checked', false);
        $('#khoa').prop('checked', false);
    }
    $('#group-1').show();
    $('#group-2').show();
    $('#group-3').show();
    $('#group-4').show();
    $("#qrcode").empty();
    showQRCode($("#qrcode"), `${url_api}v1/admin/tai-khoan/qrcode/${elementTaiKhoan.nguoiDung.id}`, 200, 200);

    let avatar = elementTaiKhoan.nguoiDung.urlAnhDaiDien;
    if (avatar == null) {
        elementTaiKhoan.nguoiDung.urlAnhDaiDien = defaultAvatar;
        avatar = defaultAvatar;
    } else {
        avatarImg.empty();
        avatarImg.html(`<img src="${avatar}" width="70%">`);
    }
    $("#btn-submit").val("Cập nhật");
}

//=================== Function ===================//
function taiExcelTK() {
    $('#btn-excel').on('click', function () {
        console.log("dstk");
        ajaxGet('v1/admin/tai-khoan/excel?list-tai-khoan=' + arrElements.map(taiKhoan => taiKhoan.id))
            .then(rs => {
                window.open(rs.data, '_blank');
            }).catch(ex => {
            console.log(ex);
            alterDanger("Tạo file excel thất bại");
        })
    });
    clickPrintElement(".ttcttk");
}

//=================== Function ===================//
function initView() {
    $('#group-1').hide();
    $('#group-2').hide();
    $('#group-3').hide();
    $('#group-4').hide();
    // $(".dateFormat").datepicker('option', 'dateFormat', 'dd-mm-yy');
    gTNam.prop('checked', true);
    kichHoat.prop('checked', true);
    inputTGKH.val(getCurrentDay());
    $(anhDaiDien).val('');

    createSelect2Ajax($("#select-search-1"),
        url_api + "v1/admin/chi-nhanh/select2-search",
        'Chọn chi nhánh', "chi nhánh", false,);

    createSelect2Ajax($("#select-search-2"),
        url_api + "v1/admin/chuc-vu/select-search",
        'Chọn chức vụ', "chức vụ", false,);

    createSelect2Ajax($("#select-search-3"),
        url_api + "v1/admin/phong-ban/select-search",
        'Chọn phòng ban', "phòng ban", false,);

    createSelect2Ajax($("#select-search-4"),
        url_api + "v1/admin/vai-tro/select-search",
        'Chọn vai trò', "vai trò", false,);
}

//=================== Function ===================//
function getCurrentDay() {
    let date = new Date();
    let day = date.getDate();
    if (day < 10) {
        day = '0' + day;
    }
    let month = date.getMonth() + 1;
    if (month < 10) {
        month = '0' + month;
    }
    let year = date.getFullYear();
    return year + '-' + month + '-' + day;
}

//=================== Function ===================//
function validateForm() {
    validator = $("#myForm").validate({
        rules: {
            name: {
                required: true,
                maxlength: 255
            },
            email: {
                required: true,
                validationEmail: true,
                uniqueEmail: true,
            },
            phone: {
                required: true,
                validationSDT: true,
                uniqueSDT: true,
            },
            address: {
                required: true,
                maxlength: 255
            },
            birthday: {
                required: true
            },
            gender: {
                required: true
            },
            chiNhanh: {
                required: true,
                validationSelect: true
            },
            chucVu: {
                required: true,
                validationSelect: true,
            },
            vaiTro: {
                required: true,
                validationSelect: true,
            },
            phongBan: {
                required: true,
                validationSelect: true
            }
        },
        messages: {
            name: {
                required: "Thông tin bắt buộc",
                maxlength: "Độ dài tối đa 255"
            },
            email: {
                required: "Thông tin bắt buộc",
                validationEmail: "Email sai định dạng",
                uniqueEmail: "Email đã tồn tại",
                uniqueEmailEdit: "Email đã tồn tại",
            },
            phone: {
                required: "Thông tin bắt buộc",
                validationSDT: "Số điện thoại sai định dạng",
                uniqueSDT: "Số điện thoại đã tồn tại",
                uniqueSDTEdit: "Số điện thoại đã tồn tại",
            },
            address: {
                required: "Thông tin bắt buộc",
                maxlength: "Độ dài tối đa 255"
            },
            birthday: {
                required: "Thông tin bắt buộc"
            },
            gender: {
                required: "Thông tin bắt buộc"
            },
            chiNhanh: {
                required: "Thông tin bắt buộc",
                validationSelect: "Lựa chọn không phù hợp"
            },
            chucVu: {
                required: "Thông tin bắt buộc",
                validationSelect: "Lựa chọn không phù hợp",
            },
            vaiTro: {
                required: "Thông tin bắt buộc",
                validationSelect: "Lựa chọn không phù hợp",
            },
            phongBan: {
                required: "Thông tin bắt buộc",
                validationSelect: "Lựa chọn không phù hợp"
            }
        },
        highlight: function (element) {
            $(element).parent().addClass('error')
        },
        unhighlight: function (element) {
            $(element).parent().removeClass('error')
        }
    });
}

//=================== Function ===================//
function submitTK() {
    $("#myForm").on('submit', function (e) {
        e.preventDefault();
        if (elementTaiKhoan == null) {
            console.log("upload");
            $("#pageBody").LoadingOverlay("show");
            submitTaiKhoan();

        } else {
            console.log("update");
            $("#pageBody").LoadingOverlay("show");
            submitUpdateTaiKhoan(elementTaiKhoan);

        }
    });
}

//=================== Function ===================//
function addValidatorMethod() {
    $.validator.addMethod("uniqueEmail", function (value) {
        if (elementTaiKhoan != null) {
            if (value != elementTaiKhoan.nguoiDung.email) {
                let rs = taiKhoanFindByEmail(value);
                if (rs.message == 'found') {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        } else {
            let rs = taiKhoanFindByEmail(value);
            if (rs.message == 'found') {
                return false;
            } else {
                return true;
            }
        }
    });

    $.validator.addMethod("validationEmail", function (value) {
        return regexEmail(value);
    });


    $.validator.addMethod("uniqueSDT", function (value) {
        if (elementTaiKhoan != null) {
            if (value != elementTaiKhoan.nguoiDung.soDienThoai) {
                let rs = taiKhoanFindBySDT(value);
                if (rs.message == 'found') {
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        } else {
            let rs = taiKhoanFindBySDT(value);
            if (rs.message == 'found') {
                return false;
            } else {
                return true;
            }
        }
    });

    $.validator.addMethod("validationSDT", function (value) {
        return regexDienThoai(value);
    });

    $.validator.addMethod("validationSelect", function (value) {
        return value > 0;
    });

}


