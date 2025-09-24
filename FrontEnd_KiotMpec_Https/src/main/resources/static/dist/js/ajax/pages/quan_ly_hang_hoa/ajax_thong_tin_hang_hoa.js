//================ Declare variable===================//

//input
var inputMaHang, inputTenHangHoa, inputTichDiem, inputMaGiamGia, inputMaVach, inputGiamGia, textAreaMoTa,
    inputTenThuongHieu, inputTenDonVi, inputTenNhomHang, inputMaNhomHang;
var arrHangHoa = [];

//form file
var formFile1, formFile2, formFile3;

//btn
var btnLuuHangHoa, btnThemMoiNhomHang, btnThemMoiThuongHieu, btnThemDonVi, btnSaveThuongHieu, btnSaveNhomHang,
    btnSaveDonVi;

//select
var selectNhomHang, selectThuongHieu, selectDonViCoBan;

//id
var idHangHoa = null, idDVHH, hangHoa = null;

//img
var img1, img2, img3, anh1, anh2, anh3, imgHH1, imgHH2, imgHH3;


//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    selectNhomHang = $("#select-1");
    selectThuongHieu = $("#select-2");
    selectDonViCoBan = $("#select-3");

    inputMaHang = $("#text-1");
    inputTenHangHoa = $("#text-2");
    inputTichDiem = $("#text-5");
    inputGiamGia = $("#text-7");
    inputMaGiamGia = $("#text-8");

    inputTenNhomHang = $("#text-9");
    inputTenThuongHieu = $("#text-10");
    inputTenDonVi = $("#text-14");

    inputMaNhomHang = $("#text-11");
    inputMaVach = $("#text-13");

    img1 = "#file-1";
    img2 = "#file-2";
    img3 = "#file-3";

    formFile1 = "#form-file";
    formFile2 = "#form-file1";
    formFile3 = "#form-file2";

    btnLuuHangHoa = $("#luu-hang-hoa");
    btnThemMoiNhomHang = $("#btn-them-nhom-hang");
    btnThemMoiThuongHieu = $("#btn-them-thuong-hieu");
    btnThemDonVi = $("#btn-them-don-vi");

    btnSaveThuongHieu = $("#btn-save-thuong-hieu");
    btnSaveNhomHang = $("#btn-save-nhom-hang");
    btnSaveDonVi = $("#btn-save-don-vi");

    anh1 = $("#imgHH-1")
    anh2 = $("#imgHH-2")
    anh3 = $("#imgHH-3")

    let href = new URL(window.location.href);
    idHangHoa = href.searchParams.get("id");
    idHangHoa = idHangHoa === null ? 0 : idHangHoa;
    buttonBackHistory();
    if (idHangHoa != 0) {
        viewHangHoa(idHangHoa);
    }
    // // bkLib.onDomLoaded(function () {
    // //     new nicEditor({fullPanel: true, maxHeight: 300,width:100}).panelInstance('mo-ta');
    // // });
    // textAreaMoTa = "#mo-ta-hang-hoa .nicEdit-main";
    textAreaMoTa=$("#mo-ta");
    //==========Function constructor========//
    viewSelectNhomHang(selectNhomHang)
    viewSelectThuongHieu(selectThuongHieu);
    viewSelectDonVi(selectDonViCoBan);

    clickLuuHangHoa();
    clickAddNhomHang();
    clickAddThuongHieu();
    clickAddDonVi();

    clickBtnSaveNhomHang();
    clickBtnSaveThuongHieu();
    clickBtnSaveDonVi();

    hiddenErrorWhenChange();

    chooseImg1();
    chooseImg2();
    chooseImg3();
})

//=============== Function detail ===================//

//=================== Function ===================//

function hiddenErrorWhenChange() {
    inputMaNhomHang.change(function () {
        hiddenError(inputMaNhomHang.parent());
    })

}

//=================== Function ===================//
async function viewHangHoa(idHangHoa) {
    $("#pageBody").LoadingOverlay("show");
    await findHangHoaDonViCoBanById(idHangHoa).then(rs1 => {
        let rs = rs1.data.hangHoa;
        idDVHH = rs1.data.id;
        if (rs1.message == 'found') {

            inputMaHang.val(viewField(rs.ma));
            inputTenHangHoa.val(viewField(rs.tenHangHoa));
            inputTichDiem.val(viewField(rs.tichDiem));
            inputGiamGia.val(viewField(rs.phanTramGiamGia));
            inputMaGiamGia.val(viewField(rs.maGiamGia));
            inputMaVach.val(viewField(rs.maVach));

            let option1 = new Option(rs.nhomHang.tenNhomHang, rs.nhomHang.id, true, true);
            selectNhomHang.append(option1).trigger('change');

            let option2 = new Option(rs.thuongHieu.tenThuongHieu, rs.thuongHieu.id, true, true);
            selectThuongHieu.append(option2).trigger('change');

            let option3 = new Option(rs1.data.donVi.tenDonVi, rs1.data.donVi.id, true, true);
            selectDonViCoBan.append(option3).trigger('change');

            if (rs.urlHinhAnh1) {
                imgHH1 = rs.urlHinhAnh1;
                anh1.html(`<img src="${rs.urlHinhAnh1}" width="70%">`);
            }
            if (rs.urlHinhAnh2) {
                imgHH2 = rs.urlHinhAnh2;
                anh2.html(`<img src="${rs.urlHinhAnh2}" width="70%">`);
            }
            if (rs.urlHinhAnh3) {
                imgHH3 = rs.urlHinhAnh3;
                anh3.html(`<img src="${rs.urlHinhAnh3}" width="70%">`);
            }
            let maVach = rs.maVach;
            if (maVach != " ") {
                showBarCode($("#bar-img"), `${url_api}v1/admin/hang-hoa/barcode/${maVach}`);
            }

        }
    });
    $("#pageBody").LoadingOverlay("hide");
}


//=================== Function check img ===================//
async function checkUpdateImg() {
    let rs1 = null;
    let rs2 = null;
    let rs3 = null;
    if ($('#file-1').val().length != 0) {
        await uploadMultiFileAWS(img1).then(rs => {
            rs1 = rs;
        });
    }
    if ($('#file-2').val().length != 0) {
        await uploadMultiFileAWS(img2).then(rs => {
            rs2 = rs;
        });
    }
    if ($('#file-3').val().length != 0) {
        await uploadMultiFileAWS(img3).then(rs => {
            rs3 = rs;
        });
    }
    return {
        img1: rs1,
        img2: rs2,
        img3: rs3
    }
}

//=================== Function upload file===================//
async function uploadFileHangHoa() {
    let arrUpload = [uploadMultiFileAWS(img1), uploadMultiFileAWS(img2), uploadMultiFileAWS(img3)];
    let val = [];
    await Promise.all(arrUpload).then(rs => {
        val = rs;
    }).catch(err => {
        console.log(err);
    })
    return val;
}

//=================== Function ===================//
function clickBtnSaveThuongHieu() {
    btnSaveThuongHieu.click(function () {
        let {check, val} = checkTen(inputTenThuongHieu);
        if (check) {
            let thuongHieu = {
                tenThuongHieu: val
            }
            $("#pageBody").LoadingOverlay("show");
            thuongHieuUpload(thuongHieu).then(rs => {
                if (rs.message === "uploaded") {
                    elementThuongHieu = rs.data;
                    $("#modal-thuong-hieu").modal('toggle');
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm thương hiệu thành công");
                    let option = new Option(rs.data.tenThuongHieu, rs.data.id, true, true);
                    selectThuongHieu.prepend(option).trigger('change');
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Thuong Hieu");
            });
        }

    })
}

//=================== Function ===================//
function clickBtnSaveNhomHang() {
    btnSaveNhomHang.click(function () {
        $("#pageBody").LoadingOverlay("show");
        let {check, val} = checkTen(inputTenNhomHang);
        let {check: checkMa, val: valMa} = checkTen(inputMaNhomHang);
        if (check && checkMa) {
            let nhomHang = {
                tenNhomHang: val,
                maNhomHang: valMa
            }
            nhomHangUpload(nhomHang).then(rs => {
                if (rs.message === "uploaded") {
                    elementNhomHang = rs.data;
                    $("#modal-nhom-hang").modal('toggle');
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm nhóm hàng thành công");
                    let option = new Option(rs.data.tenNhomHang, rs.data.id, true, true);
                    selectNhomHang.prepend(option).trigger('change');
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Nhom Hang ");
            });
        }
    })
}

//=================== Function ===================//
function clickBtnSaveDonVi() {
    btnSaveDonVi.click(function () {
        $("#pageBody").LoadingOverlay("show");
        let {check, val} = checkTen(inputTenDonVi);
        if (check) {
            let donVi = {
                tenDonVi: val
            }
            donViUpload(donVi).then(rs => {
                if (rs.message === "uploaded") {
                    elementDonVi = rs.data;
                    $("#modal-don-vi").modal('toggle');
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm đơn vị thành công");
                    let option = new Option(rs.data.tenDonVi, rs.data.id, true, true);
                    selectDonViCoBan.prepend(option).trigger('change');
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Don Vi ");
            });
        }
    })
}

//=================== Function ===================//
function clickAddNhomHang() {
    $(selectNhomHang).change(function () {
        let val = $(this).val();
        if (val == -1) {
            btnThemMoiNhomHang.trigger("click");
            $(selectNhomHang).val(selectNhomHang.find("option:nth-child(1)").val()).trigger("change");
        }
    })
}

//=================== Function ===================//
function clickAddThuongHieu() {
    $(selectThuongHieu).change(function () {
        let val = $(this).val();
        if (val == -1) {
            btnThemMoiThuongHieu.trigger("click");
            $(selectThuongHieu).val(selectThuongHieu.find("option:nth-child(1)").val()).trigger("change");
        }
    })
}

//=================== Function ===================//
function clickAddDonVi() {
    $(selectDonViCoBan).change(function () {
        let val = $(this).val();
        if (val == -1) {
            btnThemDonVi.trigger("click");
            $(selectDonViCoBan).val(selectDonViCoBan.find("option:nth-child(1)").val()).trigger("change");
        }
    })
}

//=================== Function ===================//
function checkTen(selector) {
    let rs = false;
    let size = 255;
    let ten = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size, ten)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check: rs, val: ten};
}

//=================== Function ===================//
async function clickLuuHangHoa() {
    btnLuuHangHoa.click(function () {
        let {check: checkTHH, val: valTHH} = checkTen(inputTenHangHoa);
        let {check: checkTD, val: valTD} = checkNumber(inputTichDiem, 100);
        let {check: checkGG, val: valGG} = checkNumber(inputGiamGia, 100);
        let {check: checkMV, val: valMV} = checkMaVach(inputMaVach);
        let {check: checkMGG, val: valMGG} = checkMaGiamGia();
        if (checkTHH && checkTD && checkGG && checkMGG) {
            if (idHangHoa == 0) {
                let {check: checkAnh, val: valAnh} = checkImg();
                if (checkAnh) {
                    $("#pageBody").LoadingOverlay("show");
                    uploadFileHangHoa().then(rs => {
                        hangHoa = {
                            maGiamGia: valMGG,
                            moTa: "",
                            phanTramGiamGia: valGG,
                            tenHangHoa: valTHH,
                            tichDiem: valTD,
                            urlHinhAnh1: rs[0].length != 0 ? rs[0] : "",
                            urlHinhAnh2: rs[1].length != 0 ? rs[1] : "",
                            urlHinhAnh3: rs[2].length != 0 ? rs[2] : "",
                            maVach: valMV
                        };
                        uploadHangHoa(hangHoa,selectChiNhanh.val(), selectThuongHieu.val(), selectNhomHang.val(), selectDonViCoBan.val()).then(rs2 => {
                            if (rs2.message = "uploaded") {
                                $("#pageBody").LoadingOverlay("hide");
                                alterSuccess(`Thêm hàng hóa ${valTHH} thành công`);
                                console.log(rs2);
                                viewHangHoa(rs2.data.hangHoa.id);
                            }
                        }).catch(err => {
                            console.log(err);
                            for (i = 0; i < rs.length; i++) {
                                if (rs[i].length != 0) {
                                    deleteImageByUrl(rs[i]).then(rs3 => {
                                        if (rs3.message = "deleted") {
                                        } else {
                                            console.log("Server Error");
                                        }
                                    })
                                }
                            }
                            $("#pageBody").LoadingOverlay("hide");
                            alterDanger("Upload Hang Hoa error, Server Error");

                        });
                    }).catch(err => {
                        console.log(err);
                        $("#pageBody").LoadingOverlay("hide");
                    });
                }
            } else {
                let imgArr = [];
                $("#pageBody").LoadingOverlay("show");
                checkUpdateImg().then(rs => {
                    imgArr = rs;
                    hangHoa = {
                        id: idHangHoa,
                        maGiamGia: valMGG,
                        moTa: "",
                        phanTramGiamGia: valGG,
                        tenHangHoa: valTHH,
                        tichDiem: valTD,
                        maVach: valMV,
                        urlHinhAnh1: imgArr.img1 != null ? imgArr.img1 : imgHH1,
                        urlHinhAnh2: imgArr.img2 != null ? imgArr.img2 : imgHH2,
                        urlHinhAnh3: imgArr.img3 != null ? imgArr.img3 : imgHH3
                    };
                    updateHangHoa(hangHoa, selectThuongHieu.val(), selectNhomHang.val(), selectDonViCoBan.val(), idDVHH).then(rs2 => {
                        if (rs2.message = "updated") {
                            $("#pageBody").LoadingOverlay("hide");
                            alterSuccess(`Update hàng hóa ${valTHH} thành công`);
                            viewHangHoa(idHangHoa);
                        }
                    });
                    $("#pageBody").LoadingOverlay("hide");
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                });
            }
        }
    });
}


//=================== Function ===================//
function checkNumber(selector, max) {
    let rs = false;
    let number = selector.val();
    let selectorParent = selector.parent();
    if (number >= 0 && number < max) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Vui lòng nhập số lượng lớn hơn 0 và bé hơn ${max}`);
    return {check: rs, val: number};
}

//=================== Function ===================//
function checkMaVach(selector) {
    let rs = false;
    let number = selector.val();
    let selectorParent = selector.parent();
    if (number == null) {
        return {check: rs, val: 0};
    }
    return {check: rs, val: number};
}

//=================== Function ===================//
function checkImg() {
    let rs = false;
    let selectorParent = $(img1).parents(".form-group");
    let val = $(img1);
    let lenFile = val[0].files.length;
    if (lenFile > 0) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Vui lòng chọn ít nhất 1 ảnh`);
    return {check: rs, val: val};
}

//=================== Function ===================//
function checkMaGiamGia() {
    let rs = false;
    let selectorParent = inputMaGiamGia.parents(".form-group");
    let val = inputMaGiamGia.val();
    let len = val.length;
    if (len < 255) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Độ dài chưa phù hợp < 255`);
    return {check: rs, val: val};
}

//=================== Function ===================//
function chooseImg1() {
    $("#file-1").change(function () {
        let file = this.files[0];
        const fileType = file['type'];
        const validImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];
        if ($.inArray(fileType, validImageTypes) < 0) {
            $("#validateFormError").text('Chỉ được sử dụng file định dạng jpg,jpeg,png');
        } else {
            $("#validateFormError").text('');
            showPreviewImg1(this);
        }
    });
}

function chooseImg2() {
    $("#file-2").change(function () {
        let file = this.files[0];
        const fileType = file['type'];
        const validImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];
        if ($.inArray(fileType, validImageTypes) < 0) {
            $("#validateFormError").text('Chỉ được sử dụng file định dạng jpg,jpeg,png');
        } else {
            $("#validateFormError").text('');
            showPreviewImg2(this);
        }
    });
}

function chooseImg3() {
    $("#file-3").change(function () {
        let file = this.files[0];
        const fileType = file['type'];
        const validImageTypes = ['image/jpg', 'image/jpeg', 'image/png'];
        if ($.inArray(fileType, validImageTypes) < 0) {
            $("#validateFormError").text('Chỉ được sử dụng file định dạng jpg,jpeg,png');
        } else {
            $("#validateFormError").text('');
            showPreviewImg3(this);
        }
    });
}

//=================== Function ===================//
function showPreviewImg1(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            $("#imgHH-1").empty();
            $("#imgHH-1").html(`<img src="${e.target.result}" width="70%">`);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function showPreviewImg2(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            $("#imgHH-2").empty();
            $("#imgHH-2").html(`<img src="${e.target.result}" width="70%">`);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function showPreviewImg3(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function (e) {
            $("#imgHH-3").empty();
            $("#imgHH-3").html(`<img src="${e.target.result}" width="70%">`);
        }
        reader.readAsDataURL(input.files[0]);
    }
}







