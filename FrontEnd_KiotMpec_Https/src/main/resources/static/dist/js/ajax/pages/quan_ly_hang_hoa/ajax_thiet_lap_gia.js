//================ Declare variable===================//
var selectChiNhanh, inputSearch, btnSearch, table, box, uploadBtn;
var arr = [];
var mousedownHappened = false;
var tenHangHoa, hangHoaId, page, donViHangHoaId = 0;

//btn
var btnSaveDonVi,inputTenDonVi;

//=============== Function main ===================//
$(function () {

    //=========Mapping variabe and id=========//
    uploadBtn = $("#btn-upload");
    box = $("#box-thongtin");
    selectChiNhanh = $("#bimo2");
    inputSearch = $("#bimo1");
    btnSearch = $("#btn-1");
    table = $("#table-hang-hoa");
    btnSaveDonVi=$("#btn-save-don-vi");
    inputTenDonVi=$("#text-14");
    //==========Function constructor========//
    viewChiNhanh();
    clickSearch();
    searchHangHoa();
    chooseHHSearch();
    chooseDVSearch();
    updateGiaBan();
    uploadGiaBan();
    uploadDonViHangHoa();
    checkClick();
    addDV();
    clickBtnSaveDonVi();
})

//=============== Function detail ===================//

//=================== Function ===================//
function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    callSearch(chiNhanhId);
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
        selectChiNhanh.prepend(option).trigger('change');
        selectChiNhanh.prop("disabled", true);
    }
}

//=================== Function ===================//
function checkClick() {
    $(document).on('blur', ".giaBan", function () {
        let currentRow = $(this).closest("tr");
        let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        let stt = hang - (page - 1) * 10 - 1;
        let giaBan = parseFloat(valTien($("td input")[stt].value));
        if (!isNaN(giaBan)){
            let giaBanCu = arr[stt].lichSuGiaBan.giaBan;
            let giaNhap = parseFloat(valTien($(".giaNhap")[stt].textContent));
            if (giaBan != giaBanCu) {
                if (mousedownHappened) {
                    mousedownHappened = false;
                } else {
                    $("#confirmModal2").modal();
                    $("#confirm-btn2").click(function () {
                        if (giaBan < giaNhap) {
                            $("#confirmModal").modal();
                            $("#confirm-btn").click(function () {
                                var lichSuGiaBan = {
                                    id: arr[stt].lichSuGiaBan.id,
                                    giaBan: giaBan
                                };
                                let dvhhId = arr[stt].lichSuGiaBan.donViHangHoa.id;
                                $("#pageBody").LoadingOverlay("show");
                                saveGiaBan(dvhhId, lichSuGiaBan).then(rs => {
                                    if (rs.message === "uploaded") {
                                        $("#pageBody").LoadingOverlay("hide");
                                        alterSuccess("Thay đổi giá bán thành công");
                                        callSearch(chiNhanhId);
                                    } else {
                                        $("#pageBody").LoadingOverlay("hide");
                                        alterDanger("Lỗi khi thay đổi giá bán");
                                    }
                                })
                            })
                            $("#refuse-btn").click(function () {
                                giaBanCu = arr[stt].lichSuGiaBan.giaBan;
                                $("td input")[stt].val(formatMoney(giaBanCu));
                                $("#pageBody").LoadingOverlay("hide");
                            })
                        } else {
                            var lichSuGiaBan = {
                                id: arr[stt].lichSuGiaBan.id,
                                giaBan: giaBan
                            };
                            let dvhhId = arr[stt].lichSuGiaBan.donViHangHoa.id;
                            $("#pageBody").LoadingOverlay("show");
                            saveGiaBan(dvhhId, lichSuGiaBan).then(rs => {
                                if (rs.message === "uploaded") {
                                    $("#pageBody").LoadingOverlay("hide");
                                    alterSuccess("Thay đổi giá bán thành công");
                                } else {
                                    $("#pageBody").LoadingOverlay("hide");
                                    alterDanger("Lỗi khi thay đổi giá bán");
                                }
                            })
                        }
                    });
                    $("#refuse-btn2").click(function () {
                        $("td input")[stt].value(formatMoney(giaBanCu));
                    });
                    $("#pageBody").LoadingOverlay("hide");
                }
            }
        }else {
            alterDanger("Bạn chưa nhập giá bán");
        }
    });
}

//=================== Function ===================//
function viewGia(gia, tyLe, donVi) {

    let giaNhap =gia * tyLe;
    return gia != null ? formatMoney(giaNhap) + " VNĐ / " + donVi : "Chưa nhập hàng";
}

//=================== Function ===================//
function updateGiaBan() {
    $(document).on('keyup', '.giaBan', function () {
        formatCurrency($(this));
    })
    $(document).on('keypress', '.giaBan', function (e) {
        if (e.which === 13) {
            mousedownHappened = true;
            let currentRow = $(this).closest("tr");
            let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
            let stt = hang - (page - 1) * 10 - 1;
            let giaBan = parseFloat(valTien($("td input")[stt].value));
            console.log(giaBan);
            if (!isNaN(giaBan)){
                let giaNhap = parseFloat(valTien($(".giaNhap")[stt].textContent));
                if (giaBan < giaNhap) {
                    $("#confirmModal").modal();
                    $("#confirm-btn").click(function () {
                        var lichSuGiaBan = {
                            id: arr[stt].lichSuGiaBan.id,
                            giaBan: giaBan
                        };
                        let dvhhId = arr[stt].lichSuGiaBan.donViHangHoa.id;
                        $("#pageBody").LoadingOverlay("show");
                        saveGiaBan(dvhhId, lichSuGiaBan).then(rs => {
                            if (rs.message === "uploaded") {
                                alterSuccess("Thay đổi giá bán thành công");
                                callSearch(chiNhanhId);
                            } else {
                                alterDanger("Lỗi khi thay đổi giá bán");
                            }
                        });
                        $("#pageBody").LoadingOverlay("hide");
                    })
                    $("#refuse-btn").click(function () {
                        giaBanCu = arr[stt].lichSuGiaBan.giaBan;
                        $("td input")[stt].val(formatMoney(giaBanCu));
                    })
                } else {
                    var lichSuGiaBan = {
                        id: arr[stt].lichSuGiaBan.id,
                        giaBan: giaBan
                    };
                    let dvhhId = arr[stt].lichSuGiaBan.donViHangHoa.id;
                    saveGiaBan(dvhhId, lichSuGiaBan).then(rs => {
                        if (rs.message === "uploaded") {
                            alterSuccess("Thay đổi giá bán thành công");
                        } else {
                            alterDanger("Lỗi khi thay đổi giá bán");
                        }
                    })
                }
            }else {
                alterDanger("Bạn chưa nhập giá bán");
            }

        }
    })
}

//=================== Function ===================//
function searchHangHoa() {
    $("#search-hang-hoa").select2({
        ajax: {
            type: 'GET',
            headers: {
                "Authorization": ss_lg
            },
            dataType: "json",
            url: url_api + "v1/admin/hang-hoa/search-text",
            timeout: 30000,
            data: function (params) {
                var query = {
                    text: params.term != null ? params.term : "",
                    page: 1,
                    size: 5
                };
                return query;
                // Query parameters will be ?text=[term]&page=1&size=5
            },
            processResults: function (data) {
                let rs = [];
                if (data.message == "found") {
                    $.each(data.data.currentElements, function (idx, item) {
                        rs.push({
                            'id': item.id,
                            'text': item.tenHangHoa
                        });
                    });
                    return {results: rs};
                } else {
                    rs.push({
                        'text': "Không tìm thấy hàng hóa"
                    });
                }
                return {
                    results: rs
                };
            }
        }
    });
}

//=================== Function ===================//
function chooseHHSearch() {
    $("#search-hang-hoa").change(function () {
        var id = $("#search-hang-hoa").find(':selected')[0].value;
        let temp = $("#search-hang-hoa").find(':selected')[0].text;
        tenHangHoa = temp;
        hangHoaId = id;
        $('#search-don-vi').val(null).trigger('change');
        $("#search-don-vi").select2({
            ajax: {
                type: 'GET',
                headers: {
                    "Authorization": ss_lg
                },
                dataType: "json",
                url: url_api + "v1/admin/don-vi-hang-hoa/find-by-hang-hoa-id",
                timeout: 30000,
                data: function () {
                    var query = {
                        hangHoaId: id,
                        page: 1,
                        size: 5
                    };
                    return query;
                },
                processResults: function (data) {
                    let rs = [];
                    if (data.message != "not found") {
                        $.each(data.data.currentElements, function (idx, item) {
                            rs.push({
                                'id': item.id,
                                'text': item.donVi.tenDonVi
                            });
                        });
                    }
                    rs.push({
                        'id': -1,
                        'text': "+ Lựa chọn đơn vị "
                    })
                    return {results: rs};
                }
            }
        });
    })
}

//=================== Function ===================//
function chooseDVSearch() {
    $("#search-don-vi").change(function () {
        let id = $("#search-don-vi").find(':selected')[0].value;
        if (id > 0) {
            donViHangHoaId = id;
        } else if (id == -1) {
            $("#subModal").modal("toggle");
            $("#ten-hang-hoa-moi").text(tenHangHoa);
            $("#search-don-vi-co-ban").select2({
                ajax: {
                    type: 'GET',
                    headers: {
                        "Authorization": ss_lg
                    },
                    dataType: "json",
                    url: url_api + "v1/admin/don-vi/search-text",
                    timeout: 30000,
                    data: function (params) {
                        var query = {
                            text: params.term != null ? params.term : "",
                            page: 1,
                            size: 5
                        };
                        return query;
                    },
                    processResults: function (data) {
                        if (data.message == "not found") {
                            let rs = {
                                'text': "Không tìm thấy đơn vị"
                            }
                            return {
                                results: rs
                            }
                        } else {
                            let rs = [];
                            $.each(data.data.currentElements, function (idx, item) {
                                rs.push({
                                    'id': item.id,
                                    'text': item.tenDonVi
                                });
                            });
                            rs.push({
                                'id': -1,
                                'text': "+ Thêm đơn vị "
                            })
                            return {results: rs};
                        }

                    }
                }
            });
        }
    });
}

//=================== Function ===================//
function addDV() {
    $("#search-don-vi-co-ban").change(function () {
        let id = $("#search-don-vi-co-ban").find(':selected')[0].value;
         if (id == -1) {
            console.log("aaaa");
             $("#modal-don-vi").modal("toggle");
         }
    });
}

//=================== Function ===================//
function clickBtnSaveDonVi() {
    btnSaveDonVi.click(function () {
        let {check, val} = checkTen(inputTenDonVi);
        if (check) {
            let donVi = {
                tenDonVi: val
            }
            $("#pageBody").LoadingOverlay("show");
            donViUpload(donVi).then(rs => {
                if (rs.message === "uploaded") {
                    elementDonVi = rs.data;
                    $("#modal-don-vi").modal('toggle');
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm đơn vị thành công");
                    //viewSelectNhomHang(selectNhomHang);
                    let option = new Option(rs.data.tenDonVi, rs.data.id, true, true);
                    $("#search-don-vi-co-ban").prepend(option).trigger('change');
                } else if (rs.message === "existed") {
                    alterDanger(rs.data);
                }
            }).catch(err => {
                console.log(err);
                alterDanger("Server Error - Upload Don Vi ");
            });
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
function uploadDonViHangHoa() {
    $("#btn-upload-don-vi").click(function () {
        let donViId = $("#search-don-vi-co-ban").find(':selected')[0].value;
        let tyLe = parseFloat($("#tyLe")[0].value);
        if (donViId>0 && tyLe>0){
            let donViHangHoa = {
                tyLe: tyLe,
                xoa: false
            }
            console.log(donViHangHoa);
            $("#pageBody").LoadingOverlay("show");
            saveDonViHangHoa(donViHangHoa, hangHoaId, donViId).then(rs => {
                if (rs.message == "uploaded") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm đơn vị hàng hóa mới thành công");
                    let id = rs.data.id;
                    $.ajax({
                        type: 'GET',
                        headers: {
                            "Authorization": ss_lg
                        },
                        dataType: "json",
                        url: url_api + 'v1/admin/don-vi-hang-hoa/find-by-id?id=' + id
                    }).then(function (data) {
                        // create the option and append to Select2
                        console.log(data);
                        var option = new Option(data.data.donVi.tenDonVi, data.data.id, true, true);
                        $('#search-don-vi').prepend(option).trigger('change');

                        // manually trigger the `select2:select` event
                        $('#search-don-vi').trigger({
                            type: 'select2:select',
                            params: {
                                data: data
                            }
                        });
                    });
                    donViHangHoaId = id;
                    $("#subModal").modal("toggle");
                } else if (rs.message=="existed"){
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Bạn không thể chọn tỷ lệ bằng 1 vì hàng hóa đã có đơn vị cơ bản");
                }else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Thêm đơn vị hàng hóa mới không thành công");
                }
            });
        }else {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Bạn chưa nhập đúng và đủ thông tin");
        }

    })
}

//=================== Function ===================//
function uploadGiaBan() {
    $(document).on('keyup', '#giaBanInput', function () {
        formatCurrency($(this));//chuyen tu so sang string format
    })
    uploadBtn.click(function () {
        if (donViHangHoaId > 0) {
            var giaBan = parseFloat(valTien($("#giaBanInput").val()));
            if (isNaN(giaBan)) {
                alterDanger("Bạn chưa nhập giá bán");
            } else if (giaBan <= 0) {
                alterDanger("Giá bán không hợp lệ");
            } else {
                let lichSuGiaBan = {
                    giaBan: giaBan
                };
                $("#pageBody").LoadingOverlay("show");
                saveGiaBan(donViHangHoaId, lichSuGiaBan).then(rs => {
                    if (rs.message === "uploaded") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Thêm giá bán mới thành công");
                        $('#myModal').modal('toggle');
                        console.log(rs.data);
                        callSearch(chiNhanhId);
                    } else {
                        console.log("Error : " + rs.message);
                        $("#pageBody").LoadingOverlay("hide");
                        alterDanger("Thêm giá bán mới không thành công");
                    }
                });
            }
        } else {
            $("#pageBody").LoadingOverlay("hide");
            alterDanger("Bạn chưa chọn đủ thông tin");
        }
    })
}

//=================== Function ===================//
function clickSearch() {
    btnSearch.click(function () {
        let text = inputSearch.val();
        callSearch(chiNhanhId,text);
    })
}

//=================== Function ===================//
function callSearch(chiNhanhId, text = "") {
    $("#pageBody").LoadingOverlay("show");
    searchGiaBan(chiNhanhId, text).then(rs => {
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
                    page = pagination.pageNumber;
                    if (pagination.pageNumber == 1) {
                        arr = rs.data.currentElements;
                        setView(1);
                        return;
                    }
                    console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchGiaBan(chiNhanhId, text, pagination.pageNumber).then(rs => {
                        console.log(rs);
                        arr = rs.data.currentElements;
                        setView(pagination.pageNumber);
                    }).catch(err => {
                        console.log(err);
                        $("#pageBody").LoadingOverlay("hide");
                    })
                }
            })
        } else {
            arr = [];
            setView(1);
            paginationReset();
        }
    }).catch(err => {
        $("#pageBody").LoadingOverlay("hide");
        alterDanger("Không tìm thấy danh sách thiết lập giá");
        setView(1);
    });
}

//=================== Function ===================//
function setView(pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Mã hàng</th>
                <th>Tên hàng</th>
                <th>Đơn vị</th>
                <th>Giá nhập gần nhất</th>
                <th>Giá bán</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.lichSuGiaBan.donViHangHoa.hangHoa.ma)}</td>
                    <td>${viewField(item.lichSuGiaBan.donViHangHoa.hangHoa.tenHangHoa)}</td>
                    <td>${viewField(item.lichSuGiaBan.donViHangHoa.donVi.tenDonVi)}</td>
                    <td class="giaNhap">${viewGia(item.giaNhapGanNhat, item.lichSuGiaBan.donViHangHoa.tyLe, item.lichSuGiaBan.donViHangHoa.donVi.tenDonVi)}
                    <br>${viewDateTime(item.ngayNhapGanNhat)}
                    </td>
                    <td><input type="text" class="giaBan" pattern="[0-9]+([\\.,][0-9]+)?" step="0.01" value="${formatMoney(item.lichSuGiaBan.giaBan)}" style="width:100px"></td>
                </tr>`
        );
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}
