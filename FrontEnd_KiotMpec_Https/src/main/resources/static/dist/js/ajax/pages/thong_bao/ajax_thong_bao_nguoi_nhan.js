var tieuDe, noiDung, urlTaiLieuDinhKem, btnGuiThongBao;
var arrNhanVien = [];
var arrNhanViens = [];
$(function () {

    tieuDe = $("#tieu-de");
    noiDung = $("#noi-dung");
    urlTaiLieuDinhKem = $("#file-dinh-kem");
    btnGuiThongBao = $("#btn-gui-thong-bao");
    nameNhanVien();
    sendEmail();
})

function nameNhanVien() {
    nhanVienFindAll().then(rs => {
        arrNhanViens = rs.data.currentElements;
        arrNhanVien = arrNhanViens.map((obj, index) => {
            let hVT = obj.hoVaTen;
            let object = {};
            object.key = obj.id;
            object.email = obj.email;
            object.value = false;
            let a = `<li><input type="checkbox" class="check" id="checkbox-${obj.id}" onclick="checking(${index})">${hVT}</li>`;
            $("#items").append(a);
            return object;
        });

    });
}

var idArr = [];

function checking(index) {
    let id = arrNhanVien[index].key;

    if (arrNhanVien[index].value === true) {
        arrNhanVien[index].value = false;
        idArr.pop(id);
    } else {
        arrNhanVien[index].value = true;
        idArr.push(id);
    }

}

function sendEmail() {
    btnGuiThongBao.click(function () {
            let {check: check_TieuDe, val: valTieuDe} = checkTieuDe(tieuDe);
            let {check: check_NoiDung, val: valNoiDung} = checkNoiDung(noiDung);
            let requestCheck = $("input[name='request']:checked").val();
            let request;
            if (requestCheck == 1) {
                request = true;
            } else {
                request = false;
            }

            let arrMail = null;
            if (idArr.length == 0) {
                alterWarning("Vui long chọn người nhận");
            } else {
                if (check_TieuDe && check_NoiDung) {
                    //send qua email
                    //upload file
                    let url = null;
                    $("#pageBody").LoadingOverlay("show");
                    if ($(urlTaiLieuDinhKem).val().length > 0) {
                        uploadFilesAWS("#file-dinh-kem").then(rs => {
                            if (rs !== null) {
                                url = rs;
                                let mailArr = [];
                                arrNhanVien.filter(nv => {
                                    if (nv.value) {
                                        let objSend = {};
                                        objSend.value = nv.email;
                                        mailArr.push(objSend.value);
                                        return objSend;
                                    }
                                });
                                arrMail = {
                                    content: valTieuDe,
                                    header: valNoiDung,
                                    mail: mailArr,
                                    urlAttachment: url
                                }

                                sendEmailToNguoiNhan(arrMail).then(rs => {
                                    if (rs.message === "success") {
                                        //alterSuccess("Gửi mail thành công");
                                    } else {
                                        alterSuccess("Gửi mail không thành công");
                                    }

                                }).catch(err => {
                                    console.log(err);
                                    $("#pageBody").LoadingOverlay("hide");
                                    alterDanger("Server Error - SendMail!");
                                });
                                //upload
                                let thongBao = {
                                    tieuDe: valTieuDe,
                                    noiDung: valNoiDung,
                                    mail: mailArr,
                                    taiLieuDinhKem: url,
                                    hinhThucGui: 1,
                                    yeuCauPhanHoi: request
                                }
                                uploadThongBao(thongBao).then(rs => {
                                    console.log(rs);
                                    if (rs.message === "uploaded") {
                                        let idThongBao = rs.data.id;
                                        let thongBaoNguoiNhan = {
                                            nguoiDungId: idArr,
                                            thongBaoId: idThongBao
                                        }
                                        //có id upload thong bao nguoi nhan
                                        uploadThongBaoNguoiNhan(thongBaoNguoiNhan).then(rs => {
                                            if (rs.message === "success") {
                                                $("#pageBody").LoadingOverlay("hide");
                                                alterSuccess("Gửi mail thành công");
                                            }
                                        })
                                    }
                                })
                            } else {
                                $("#pageBody").LoadingOverlay("hide");
                                alterDanger("Upload File không thành công");
                            }
                        }).catch(err => {
                            console.log(err);
                            $("#pageBody").LoadingOverlay("hide");
                            alterDanger("Server error - Upload file");
                        })
                    } else {
                        let mailArr = [];
                        arrNhanVien.filter(nv => {
                            if (nv.value) {
                                let objSend = {};
                                objSend.value = nv.email;
                                mailArr.push(objSend.value);
                                return objSend;
                            }
                        });
                        arrMail = {
                            content: valTieuDe,
                            header: valNoiDung,
                            mail: mailArr,
                            urlAttachment: url
                        }
                        sendEmailToNguoiNhan(arrMail).then(rs => {
                            if (rs.message === "success") {
                                //alterSuccess("Gửi mail thành công");
                            } else {
                                alterSuccess("Gửi mail không thành công");
                            }

                        }).catch(err => {
                            $("#pageBody").LoadingOverlay("hide");
                            alterDanger("Server Error - SendMail!");
                        });
                        //upload
                        let thongBao = {
                            tieuDe: valTieuDe,
                            noiDung: valNoiDung,
                            mail: mailArr,
                            taiLieuDinhKem: url,
                            hinhThucGui: 1,
                            yeuCauPhanHoi: request
                        }
                        uploadThongBao(thongBao).then(rs => {
                            console.log(rs);
                            if (rs.message === "uploaded") {
                                let idThongBao = rs.data.id;
                                let thongBaoNguoiNhan = {
                                    nguoiDungId: idArr,
                                    thongBaoId: idThongBao
                                }
                                //có id upload thong bao nguoi nhan
                                uploadThongBaoNguoiNhan(thongBaoNguoiNhan).then(rs => {
                                    if (rs.message === "success") {
                                        $("#pageBody").LoadingOverlay("hide");
                                        alterSuccess("Gửi mail thành công");
                                    }
                                })
                            }
                        })
                    }
                } else {
                    alterWarning("Vui lòng nhập đầy đủ tiêu đề và nội dung!");
                }
            }

        }
    )
}

function checkTieuDe(selector) {
    let rs = false;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (text === "") {
        viewError(selector, "Tiêu đề không được để trống!");
    } else {
        rs = true;
        hiddenError(selectorParent);
    }

    return {check: rs, val: text};
}

function checkNoiDung(selector) {
    let rs = false;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (text === "") {
        viewError(selector, "Nội dung không được để trống!");
    } else {
        rs = true;
        hiddenError(selectorParent);
    }
    return {check: rs, val: text};
}
