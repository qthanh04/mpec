//================ Declare variable===================//
//other
var selectChiNhanh, dataTable, validator;

//input
var inputSearch, inputMaTaiKhoan, inputHoVaTen, inputTaiKhoan, inputEmail, inputSDT,
    inputDiaChi, inputNgaySinh, inputTGKT, inputTGKH, inputTGKH,
    inputTGHH, gioiTinh, trangThai, radioGT,
    anhDaiDien, kichHoat, gTNam, avatarImg, ma, avatar;

//btn
var btnSua, btnXoa, btnThemMoiNhom, iconThemTaiKhoan, btnLamMoi, btnSearch;

const defaultAvatar = "https://mpec.s3.us-east-2.amazonaws.com/default_ava.png";

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    inputMaTaiKhoan = $("#label-1");
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
    radioGT = $("#radio-1");
    gTNam = $("#gTNam");
    kichHoat = $("#kichHoat");
    ma = $("#ma");

    btnLamMoi = $(".them-moi");
    btnSua = $("#btn-2");
    iconThemTaiKhoan = $(".them-moi-tk");
    btnXoa = $(".btn-xoa");
    btnThemMoiNhom = $("#btn-6");

    //==========Function constructor========//
    // validateForm();
    setViewThongTinTaiKhoan();
    submitUpdateTaiKhoan();
})

//=============== Function detail ===================//

//=================== Function ===================//
function setViewThongTinTaiKhoan() {
    findNguoiDungPhongBanByNhanVienId(nguoiDungId).then(rs => {
        if (rs.message === "found") {
            $("#ma").html(`<span class="page-title"> ${rs.data.nguoiDung.maTaiKhoan}</span>`);
            $("#label-2").val(viewField(rs.data.nguoiDung.taiKhoan));
            $("#input-text-2").val(viewField(rs.data.nguoiDung.hoVaTen));
            $("#input-text-3").val(viewField(rs.data.nguoiDung.email));
            $("#input-text-4").val(viewField(rs.data.nguoiDung.soDienThoai));
            $("#input-text-5").val(viewField(rs.data.nguoiDung.diaChi));
            $("#input-date-1").val(viewField(rs.data.nguoiDung.ngaySinh));
            $("#input-date-4").val(viewField(rs.data.nguoiDung.thoiGianKhoiTao));
            $("#input-date-2").val(viewField(rs.data.nguoiDung.thoiGianKichHoat));
            $("#input-date-3").val(viewField(rs.data.nguoiDung.thoiGianHetHan));

            let optionCN = new Option(rs.data.chiNhanh.diaChi, rs.data.chiNhanh.id, true, true);
            $(`#select-search-1`).append(optionCN).trigger('change');

            let optionCV = new Option(rs.data.chucVu.tenChucVu, rs.data.chucVu.id, true, true);
            $(`#select-search-2`).append(optionCV).trigger('change');

            let optionPB = new Option(rs.data.phongBan.tenPhongBan, rs.data.phongBan.id, true, true);
            $(`#select-search-3`).append(optionPB).trigger('change');

            let optionVT = new Option(rs.data.vaiTro.tenVaiTro, rs.data.vaiTro.id, true, true);
            $(`#select-search-4`).append(optionVT).trigger('change');


            if (rs.data.nguoiDung.gioiTinh == 1) {
                $('#gTNam').prop('checked', true);
                $('#gTNu').prop('checked', false);
            } else if (rs.data.nguoiDung.gioiTinh == 0) {
                $('#gTNam').prop('checked', false);
                $('#gTNu').prop('checked', true);
            } else {
                $('#gTNam').prop('checked', false);
                $('#gTNu').prop('checked', false);
            }
            if (rs.data.nguoiDung.trangThai == 0) {
                $('#kichHoat').prop('checked', true);
                $('#khoa').prop('checked', false);
            } else if (rs.data.nguoiDung.trangThai == 1) {
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

            showQRCode($("#qrcode"), `${url_api}v1/admin/tai-khoan/qrcode/${rs.data.nguoiDung.id}`, 180, 180);

            avatar = rs.data.nguoiDung.urlAnhDaiDien;
            if (avatar == null) {
                avatar = defaultAvatar;
            }
            $("#ava-img").empty();
            $("#ava-img").html(`<img src="${avatar}" width="70%">`);
        } else {
        }
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
            tag.html(`<img src='${defaultQRCode}'>`);
        }
    });

}

//=================== Function ===================//
function submitUpdateTaiKhoan() {
    btnSua.click(function () {
        let {check: check_HVT, val: valHVT} = checkHoTen(inputHoVaTen);
        let {check: check_Email, val: valEmail} = checkEmail(inputEmail);
        let {check: check_SDT, val: valSdt} = checkSDT(inputSDT);
        let {check: check_DC, val: valDC} = checkDiaChi(inputDiaChi);
        let {check: check_NS, val: valns} = checkNS(inputNgaySinh);
        let {check: check_GT, val: valgt} = checkGT(gioiTinh);
        let {check: check_TGKH, val: valtgkh} = checkTGKH(inputTGKH);
        let {check: check_TT, val: valtt} = checkTT(trangThai);
        let {check: check_TGHH, val: valtghh} = checkTGHH(inputTGHH);
        let chiNhanhId = $('#select-search-1').val();
        console.log(chiNhanhId);
        let chucVuId = $('#select-search-2').val();
        let phongBanId = $('#select-search-3').val();
        let vaiTroId = $('#select-search-4').val();
        if (check_HVT && check_Email && check_SDT && check_DC && check_NS && check_GT && check_TGKH && check_TT && check_TGHH) {
            $("#pageBody").LoadingOverlay("show");
            if ($(anhDaiDien).val().length > 0) {
                uploadFilesAWS("#customFile-1").then(rs => {
                    if (rs !== null) {
                        let taiKhoan = {
                            id: idNguoiDung,
                            email: valEmail,
                            soDienThoai: valSdt,
                            hoVaTen: valHVT,
                            diaChi: valDC,
                            ngaySinh: valns,
                            gioiTinh: valgt,
                            thoiGianKichHoat: valtgkh,
                            thoiGianHetHan: valtghh,
                            trangThai: valtt,
                            urlAnhDaiDien: rs
                        }
                        console.log(taiKhoan);
                        updateTaiKhoan(taiKhoan, chiNhanhId, phongBanId, chucVuId, vaiTroId).then(rs => {
                            console.log(rs);
                            if (rs.message === "updated") {
                                $("#pageBody").LoadingOverlay("hide");
                                alterSuccess("Sửa thông tin tài khoản thành công");
                                setViewThongTinTaiKhoan();
                            }
                        }).catch(err => {
                            console.log(err);
                            $("#pageBody").LoadingOverlay("hide");
                            alterDanger("Server error - Update tài khoản");
                        });
                    } else {
                        $("#pageBody").LoadingOverlay("hide");
                        alterDanger("Server error - Upload file");
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Upload file");
                });

            } else {
                let taiKhoan = {
                    id: idNguoiDung,
                    email: valEmail,
                    soDienThoai: valSdt,
                    hoVaTen: valHVT,
                    diaChi: valDC,
                    ngaySinh: valns,
                    gioiTinh: valgt,
                    thoiGianHetHan: valtghh,
                    trangThai: valtt,
                    urlAnhDaiDien: avatar
                }
                updateTaiKhoan(taiKhoan, chiNhanhId, phongBanId, chucVuId, vaiTroId).then(rs => {
                    console.log(rs);
                    if (rs.message === "updated") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Sửa thông tin tài khoản thành công");
                        setViewThongTinTaiKhoan();
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Update tài khoản");
                })
            }
        } else {
            alterWarning("Vui lòng nhập đầy đủ thông tin");
        }

    })
}

