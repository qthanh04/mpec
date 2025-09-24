//================ Declare variable===================//

//input
var inputSearchPhongBan, inputSearchChucVu, inputSearchVaiTro,
    btnSearchPhongBan, btnSearchChucVu, btnSearchVaiTro,
    tablePhongBan, tableChucVu, tableVaiTro, inputMoiPhongBan, inputMaPhongBan, inputMoiChucVu, inputMoiVaiTro;

//btn
var btnThemPhongBan, btnThemChucVu, btnThemVaiTro, btnSuaPhongBan, btnSuaChuVu, btnSuaVaiTro, btnXoaPhongBan,
    btnXoaChucVu, btnXoaVaiTro;

//array
var arrPhongBan = [],arrChucVu = [],arrVaiTro = [],elementPhongBan = [],elementChucVu = [],elementVaiTro = [];


//=============== Function main ===================//
$(function () {

    //=========Mapping variabe and id=========//
    inputSearchPhongBan = $("#bimo1");
    inputSearchChucVu = $("#bimo2");
    inputSearchVaiTro = $("#bimo3");
    btnSearchPhongBan = $("#btn-search-1");
    btnSearchChucVu = $("#btn-search-2");
    btnSearchVaiTro = $("#btn-search-3");

    tablePhongBan = $("#table-phong-ban");
    tableChucVu = $("#table-chuc-vu");
    tableVaiTro = $("#table-vai-tro");

    inputMoiPhongBan = $("#text-phong-ban");
    inputMoiChucVu = $("#text-chuc-vu");
    inputMoiVaiTro = $("#text-vai-tro");
    inputMaPhongBan = $("#text-ma-phong-ban");

    btnThemPhongBan = $("#btn-them-phong-ban");
    btnThemChucVu = $("#btn-them-chuc-vu");
    btnThemVaiTro = $("#btn-them-vai-tro");

    btnSuaPhongBan = $("#btn-sua-phong-ban");
    btnSuaChuVu = $("#btn-sua-chuc-vu");
    btnSuaVaiTro = $("#btn-sua-vai-tro");

    btnXoaPhongBan = $("#btn-xoa-phong-ban");
    btnXoaChucVu = $("#btn-xoa-chuc-vu");
    btnXoaVaiTro = $("#btn-xoa-vai-tro");


    //==========Function constructor========//
    searchPhongBan();
    searchChucVu();
    searchVaiTro();

    clickAddPhongBan();
    clickSuaPhongBan();
    clickXoaPhongBan();

    clickAddChucVu();
    clickSuaChucVu();
    clickXoaChucVu();

    clickAddVaiTro();
    clickSuaVaiTro();
    clickXoaVaiTro();

    clickSearchPhongBan();
    clickSearchChucVu();
    clickSearchVaiTro();

    viewHiddenChucNangPB();
    viewHiddenChucNangCV();
    viewHiddenChucNangVT();
})

//=============== Function detail ===================//

//=================== Function view hidden ===================//
function viewHiddenChucNangPB() {
    $(".danh-muc-phong-ban").click(function () {
        $("#modal-phong-ban").modal("toggle");
    })
}

function viewHiddenChucNangCV() {
    $(".danh-muc-chuc-vu").click(function () {
        $("#modal-chuc-vu").modal("toggle");
    })
}

function viewHiddenChucNangVT() {
    $(".danh-muc-vai-tro").click(function () {
        $("#modal-vai-tro").modal("toggle");
    })
}

//=================== Function click search===================//
function clickSearchPhongBan() {
    btnSearchPhongBan.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchPhongBan();
    })
}

function clickSearchChucVu() {
    btnSearchChucVu.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchChucVu();
    })
}

function clickSearchVaiTro() {
    btnSearchVaiTro.click(function () {
        $("#pageBody").LoadingOverlay("show");
        searchVaiTro();

    })
}

//=================== Function search phong ban ===================//
function searchPhongBan() {
    let valSearchPhongBan = inputSearchPhongBan.val();
    $("#pageBody").LoadingOverlay("show");
    phongBanSearch(valSearchPhongBan, "").then(rs => {
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
                        arrPhongBan = rs.data.currentElements;
                        setViewPhongBan(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    phongBanSearch(valSearchPhongBan, "", pagination.pageNumber).then(rs => {
                        arrPhongBan = rs.data.currentElements;
                        setViewPhongBan(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrPhongBan = [];
            setViewPhongBan(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Phong Ban");
        setViewPhongBan(1);
    })
}

//=================== Function set view phong ban ===================//
function setViewPhongBan(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên phòng ban</th>
                    <th>Mã phòng ban</th>
                </tr>`;
    let len = arrPhongBan.length;
    if (len > 0) {
        view += arrPhongBan.map((item, index) => `<tr data-index="${index}" class="click-phong-ban">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenPhongBan)}</td>
                    <td>${viewField(item.maPhongBan)}</td>
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
    tablePhongBan.html(view);
    clickPhongBan();
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function search chuc vu===================//
function searchChucVu() {
    let valSearchChucVu = inputSearchChucVu.val();
    chucVuSearch(valSearchChucVu).then(rs => {
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
                        arrChucVu = rs.data.currentElements;
                        setViewChucVu(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    chucVuSearch(valSearchChucVu, pagination.pageNumber).then(rs => {
                        arrChucVu = rs.data.currentElements;
                        setViewChucVu(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrChucVu = [];
            setViewChucVu(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Chuc Vu");
        setViewChucVu(1);
    })
}

//=================== Function set view chuc vu ===================//
function setViewChucVu(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên chức vụ</th>
                </tr>`;
    let len = arrChucVu.length;
    if (len > 0) {
        view += arrChucVu.map((item, index) => `<tr data-index="${index}" class="click-chuc-vu">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenChucVu)}</td>
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
    tableChucVu.html(view);
    clickChucVu();
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function search vai tro===================//
function searchVaiTro() {
    let valSearchVaiTro = inputSearchVaiTro.val();
    vaiTroSearch(valSearchVaiTro).then(rs => {
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
                        arrVaiTro = rs.data.currentElements;
                        setViewVaiTro(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    vaiTroSearch(valSearchVaiTro, pagination.pageNumber).then(rs => {
                        arrVaiTro = rs.data.currentElements;
                        setViewVaiTro(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrVaiTro = [];
            setViewVaiTro(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Vai Tro");
        setViewVaiTro(1);
    })
}

//=================== Function st view vai tro ===================//
function setViewVaiTro(pageNumber) {
    let view = `<tr>
                    <th>STT</th>
                    <th>Tên vai trò</th>
                </tr>`;
    let len = arrVaiTro.length;
    if (len > 0) {
        view += arrVaiTro.map((item, index) => `<tr data-index="${index}" class="click-vai-tro">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.tenVaiTro)}</td>
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
    tableVaiTro.html(view);
    clickVaiTro();
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function click in table ===================//
function clickPhongBan() {
    tablePhongBan.find("tr:not(.click-phong-ban)").unbind("click").click(function () {
        elementPhongBan = {};
        inputMoiPhongBan.val("");
        inputMaPhongBan.val("");
    })
    $("tr.click-phong-ban").unbind("click").click(function () {
        elementPhongBan = arrPhongBan[$(this).attr("data-index")];
        inputMoiPhongBan.val(viewField(elementPhongBan.tenPhongBan));
        inputMaPhongBan.val(viewField(elementPhongBan.maPhongBan));
    })
}

function clickChucVu() {
    tableChucVu.find("tr:not(.click-chuc-vu)").unbind("click").click(function () {
        elementChucVu = {};
        inputMoiChucVu.val("");
    })
    $("tr.click-chuc-vu").unbind("click").click(function () {
        elementChucVu = arrChucVu[$(this).attr("data-index")];
        inputMoiChucVu.val(viewField(elementChucVu.tenChucVu));
    })
}

function clickVaiTro() {
    tableVaiTro.find("tr:not(.click-vai-tro)").unbind("click").click(function () {
        elementVaiTro = {};
        inputMoiVaiTro.val("");
    })
    $("tr.click-vai-tro").unbind("click").click(function () {
        elementVaiTro = arrVaiTro[$(this).attr("data-index")];
        inputMoiVaiTro.val(viewField(elementVaiTro.tenVaiTro));
    })
}

//=================== Function them phong ban ===================//
function clickAddPhongBan() {
    btnThemPhongBan.click(function () {
        let {check: check, val: val} = checkTen(inputMoiPhongBan);
        let {check: checkMa, val: valMa} = checkTen(inputMaPhongBan);
        if (check && checkMa) {
            let phongBan = {
                tenPhongBan: val,
                maPhongBan: valMa
            };
            $("#pageBody").LoadingOverlay("show");
            phongBanUpload(phongBan).then(rs => {
                if (rs.message === "uploaded") {
                    elementPhongBan = rs.data;
                    inputMoiPhongBan.val(viewField(elementPhongBan.tenPhongBan));
                    inputMaPhongBan.val(viewField(elementPhongBan.maPhongBan));
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm phòng ban thành công");
                    $("#modal-phong-ban").modal("toggle");
                    searchPhongBan();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Phong Ban");
            });
        }
    })
}

//=================== Function sua phong ban ===================//
function clickSuaPhongBan() {
    btnSuaPhongBan.click(function () {
        let {check: check, val: val} = checkTen(inputMoiPhongBan);
        let {check: checkMa, val: valMa} = checkTen(inputMaPhongBan);
        if (check && checkMa) {
            elementPhongBan.tenPhongBan = val;
            elementPhongBan.maPhongBan = valMa;
            $("#pageBody").LoadingOverlay("show");
            phongBanUpdate(elementPhongBan).then(rs => {
                if (rs.message === "updated") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa phòng ban thành công");
                    $("#modal-phong-ban").modal("toggle");
                    searchPhongBan();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Phong Ban");
            });
        }
    });
}

//=================== Function xoa phong ban===================//
function clickXoaPhongBan() {
    btnXoaPhongBan.click(function () {
        if (Object.keys(elementPhongBan).length !== 0) {
            $("#modal-phong-ban").modal("toggle");
            $('#modal-remove').modal('show');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                phongBanDelete(elementPhongBan.id).then(rs => {
                    if (rs.message = "deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa phòng ban ${elementPhongBan.tenPhongBan}`);
                        searchPhongBan();
                        elementPhongBan = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Phòng Ban")
                });
                // to do remove camera
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Vui lòng chọn một phòng ban để thực hiện thao tác");
        }
    })
}

//=================== Function them chuc vu ===================//
function clickAddChucVu() {
    btnThemChucVu.click(function () {
        let {check: check, val: val} = checkTen(inputMoiChucVu);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            let chucVu = {
                tenChucVu: val
            };
            chucVuUpload(chucVu).then(rs => {
                if (rs.message === "uploaded") {
                    elementChucVu = rs.data;
                    inputMoiChucVu.val(viewField(elementChucVu.tenChucVu));
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm chức vụ thành công");
                    $("#modal-chuc-vu").modal("toggle");
                    searchChucVu();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Upload Chuc Vu");
            })
        }
    });
}

//=================== Function sua chuc vu ===================//
function clickSuaChucVu() {
    btnSuaChuVu.click(function () {
        let {check: check, val: val} = checkTen(inputMoiChucVu);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            elementChucVu.tenChucVu = val;
            chucVuUpdate(elementChucVu).then(rs => {
                if (rs.message === "updated") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa chức vụ thành công");
                    $("#modal-chuc-vu").modal("toggle");
                    searchChucVu();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Chuc Vu");
            });
        }
    });
}

//=================== Function xoa chuc vu ===================//
function clickXoaChucVu() {
    btnXoaChucVu.click(function () {
        if (Object.keys(elementChucVu).length !== 0) {
            $("#modal-chuc-vu").modal("toggle");
            $('#modal-remove').modal('show');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                chucVuDelete(elementChucVu.id).then(rs => {
                    if (rs.message = "deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa chức vụ ${elementChucVu.tenChucVu}`);
                        searchChucVu();
                        elementChucVu = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Chuc Vu")
                });
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Vui lòng chọn một chức vụ để thực hiện thao tác");
        }
    })
}

//=================== Function them vai tro ===================//
function clickAddVaiTro() {
    btnThemVaiTro.click(function () {
        let {check: check, val: val} = checkTen(inputMoiVaiTro);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            let vaiTro = {
                tenVaiTro: val
            };
            vaiTroUpload(vaiTro).then(rs => {
                if (rs.message === "uploaded") {
                    elementVaiTro = rs.data;
                    inputMoiVaiTro.val(viewField(elementVaiTro.tenVaiTro));
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm vai trò thành công");
                    $("#modal-vai-tro").modal("toggle");
                    searchVaiTro();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                alterDanger("Server Error - Upload Vai Tro");
            });
        }
    });
}

//=================== Function sua vai tro ===================//
function clickSuaVaiTro() {
    btnSuaVaiTro.click(function () {
        let {check: check, val: val} = checkTen(inputMoiVaiTro);
        if (check) {
            $("#pageBody").LoadingOverlay("show");
            elementVaiTro.tenVaiTro = val;
            vaiTroUpdate(elementVaiTro).then(rs => {
                if (rs.message === "updated") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Sửa vai trò thành công");
                    $("#modal-vai-tro").modal("toggle");
                    searchVaiTro();
                } else if (rs.message === "existed") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Update Vai Tro");
            });
        }
    });
}

//=================== Function xoa vai tro ===================//
function clickXoaVaiTro() {
    btnXoaVaiTro.click(function () {
        if (Object.keys(elementVaiTro).length !== 0) {
            $("#modal-vai-tro").modal("toggle");
            $('#modal-remove').modal('show');
            $("#confirm-yes").unbind("click").click(function () {
                $("#pageBody").LoadingOverlay("show");
                vaiTroDelete(elementVaiTro.id).then(rs => {
                    if (rs.message ==="deleted") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess(`Đã xóa vai trò ${elementVaiTro.tenVaiTro}`);
                        searchVaiTro();
                        elementVaiTro = {};
                    }
                }).catch(err => {
                    console.log(err);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Server error - Delete Vai Tro")
                });
            })
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Vui lòng chọn một chức vụ để thực hiện thao tác");
        }
    })
}
