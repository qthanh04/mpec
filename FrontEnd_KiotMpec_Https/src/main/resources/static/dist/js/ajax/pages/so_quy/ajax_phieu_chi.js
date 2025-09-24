//================ Declare variable===================//
var arr = [];

//input
var inputMaPhieuChi, inputTenLoaiChi, inputTenNhanVien, inputTuNgay, inputDenNgay;

//btn
var btnTimKiem, selectTrangThai, selectChiNhanh, selectNhaCungCap;

var tienNo = [];
const TRANG_THAI_PHIEU_CHI = ["Đã thanh toán", "Chưa thanh toán", "Đang thanh toán"];
const PHIEU_CHI_HD = 5;

function viewTrangThaiPC(phieuChi) {
    return TRANG_THAI_PHIEU_CHI[phieuChi.trangThai];
}

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-phieu-chi");
    selectChiNhanh = $("#bimo0");
    inputMaPhieuChi = $("#bimo1");
    inputTenLoaiChi = $("#bimo2");
    inputTenNhanVien = $("#bimo3");
    selectTrangThai = $("#bimo4");
    inputTuNgay = $("#bimo5");
    inputDenNgay = $("#bimo6");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//
    viewChiNhanh();
    clickSearchPhieuChi();
    checkThoiGianTHDN();
    changeTrangThai();
    searchKhachHang();
    searchLoaiChi();
    changeDoiTuong();
    chooseDoiTac();
    clickLuuPhieu();
    nhapGiaTri();
    nhapTienNo();
    taiExcelTK();
});

//=============== Function detail ===================//

async function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    callSearchPhieuChiByChiNhanh(chiNhanhId);
    if (chucvuId == 1) {
        await viewSelectChiNhanhFindByTongCongTy().then(rs => {
            selectChiNhanh.html(`<option value="0">Tất cả chi nhánh</option>` + rs);
            runSelect2();
            selectChiNhanh.change(function () {
                // inputSearch.val("");
                btnTimKiem.trigger("click");
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
function clickSearchPhieuChi() {
    btnTimKiem.click(function () {
        let maPhieuChi = inputMaPhieuChi.val();
        let tenNhanVien = inputTenNhanVien.val();
        let tenLoaiChi = inputTenLoaiChi.val();
        let trangThai = selectTrangThai.val();
        let tenNCC = $("#bimo7 :selected").text();
        // let tenNCC = selectNhaCungCap.text();
        console.log(tenNCC);
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchPhieuChi(maPhieuChi, tenLoaiChi, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 10);
        }
    });
}

//=================== Function ===================//
function callSearchPhieuChi(maPhieuChi, tenLoaiChi, tenNhanVien, valTuNgay, valDenNgay, trangThai, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    searchPhieuChi(chiNhanhId, maPhieuChi, tenLoaiChi, tenNhanVien, valTuNgay, valDenNgay, trangThai).then(rs => {
        if (rs.message === "found") {
            $('#pagination').pagination({
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
                        setViewPhieuChi(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchPhieuChi(chiNhanhId, maPhieuChi, tenLoaiChi, tenNhanVien, valTuNgay, valDenNgay, trangThai, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewPhieuChi(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewPhieuChi(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy phiếu chi");
        setViewPhieuChi(1);
    });
}

//=================== Function ===================//
function callSearchPhieuChiByChiNhanh(chiNhanhId) {
    $("#pageBody").LoadingOverlay("show");
    searchPhieuChiByChiNhanh(chiNhanhId).then(rs => {
        if (rs.message === "found") {
            $('#pagination').pagination({
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
                        setViewPhieuChi(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchPhieuChiByChiNhanh(chiNhanhId, "", pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewPhieuChi(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewPhieuChi(1);
            paginationReset();
        }
    })

}

//=================== Function ===================//
function setViewPhieuChi(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Mã phiếu chi</th>
                <th>Thời gian</th>
                <th>Loại chi</th>
                <th>Nhân viên</th>
                <th>Giá trị</th>
                <th>Trạng thái</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-phieu-chi">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.loaiChi.maLoaiChi)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.loaiChi.tenLoaiChi)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${formatMoney(item.daTra)}</td>
                    <td><button type="button" class="trang-thai-btn trang-thai-btn-${index} btn btn-default" style="width: 120px">
                            ${viewTrangThaiPC(item)}
                        </button>
                    </td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td> <td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function changeTrangThai() {
    $(document).on('click', '.trang-thai-btn', function () {
        let trangThaiCu = $.trim($(this).text());
        let currentRow = $(this).closest("tr");
        let phieuChiId = currentRow.find("td:eq(0)").attr("data-id");
        let valueCu = TRANG_THAI_PHIEU_CHI.indexOf(trangThaiCu);
        $("#trang-thai-cu").text(`Trạng thái cũ : ${trangThaiCu}`);
        $("#select-trang-thai").val(valueCu);
        $('#select-trang-thai').trigger('change');
        $("#modal-trang-thai").modal('toggle');
        $("#confirm-btn").click(function () {
            let valueMoi = $("#select-trang-thai").find(':selected')[0].value;
            if (valueCu != valueMoi) {
                $("#pageBody").LoadingOverlay("show");
                setTrangThaiPhieuChi(phieuChiId, valueMoi).then(rs => {
                    if (rs.message === "success") {
                        let hang = parseInt(currentRow.find("td:eq(0)").text());
                        $(`.trang-thai-btn-${hang - 1}`).text($("#select-trang-thai").find(':selected')[0].text);
                        $("#modal-trang-thai").modal('hide');
                        alterSuccess("Thay đổi trạng thái phiếu chi thành công");
                    } else {
                        alterDanger("Thay đổi trạng thái phiếu chi không thành công")
                    }
                });
                $("#pageBody").LoadingOverlay("hide");
            } else {
                alterSuccess("Bạn không có thay đổi gì");
                $("#modal-trang-thai").modal('hide');
            }
        });
    });
}

//=============== Function Tim kiem khach hang ===================//
function searchKhachHang() {
    callGetSelectize($('#select-khach-hang'), "id", "tenKhachHang", "v1/admin/khach-hang/search-text");
}

function setView(khachHang) {
    var giaTri = $("#gia-tri").val();
    $("#table-doi-tac").find('tbody').remove();
    tienNo = [];
    let view = `<tr>
            <th>STT</th>
            <th>Mã hóa đơn</th>
            <th>Thời gian</th>
            <th>Tổng tiền</th>
             <th>Tiền trả</th>                
                </tr>`;
    let len = khachHang.length;
    if (len > 0) {

        khachHang.map((item, index) => {

            let no = item.tongTien;
            console.log("nợ :" + no);
            tienNo.push(no);
            if (giaTri >= no) {
                giaTri = giaTri - no;
            } else {
                no = giaTri;
                giaTri = 0;
            }
            let view2 = `<tr class="data" data-index="${index}">
                    <td class="idHoaDon" data-id="${viewField(item.id)}">${index + 1}</td>
                    <td>${viewField(item.ma)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${formatMoney(item.tongTien)}</td>
                    <td><input type="text" class="none-arrow tienNo tienNo${index}" id="tien-chi-${index}" style="width: 90px" value="${formatMoney(no)}" max="${formatMoney(item.tongTien)}"></td>
                </tr>`
            view += view2;
        });
    } else {
        view += `<tr><td colspan="6">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-doi-tac").html(view);
    $("#pageBody").LoadingOverlay("hide");
}


function setViewNCC(nhaCungCap) {
    var giaTri = $("#gia-tri").val();
    $("#table-doi-tac").find('tbody').remove();
    tienNo = [];
    let view = `<tr>
            <th>STT</th>
            <th>Mã hóa đơn</th>
            <th>Thời gian</th>
            <th>Tổng tiền</th>
             <th>Tiền đã trả</th>
             <th>Cần trả khách</th>
             <th>Tiền trả</th>                
                </tr>`;
    let len = nhaCungCap.length;
    if (len > 0) {
        nhaCungCap.map((item, index) => {
            let no = item.tienPhaiTra - item.tienDaTra;
            tienNo.push(no);
            if (giaTri >= no) {
                giaTri = giaTri - no;
            } else {
                no = giaTri;
                giaTri = 0;
            }
            let view2 = `<tr class="data" data-index="${index}">
                    <td data-id="${viewField(item.id)}">${index + 1}</td>
                    <td>${viewField(item.maPhieu)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${formatMoney(item.tienPhaiTra)}</td>
                    <td>${formatMoney(item.tienDaTra)}</td>
                    <td>${formatMoney(item.tienPhaiTra - item.tienDaTra)}</td>
                 <td><input type="text" class="none-arrow tienNo tienNo${index}" id="tien-chi-${index}" style="width: 90px" value="${formatMoney(no)}" max="${formatMoney(item.tienPhaiTra - item.tienDaTra)}"></td>
                </tr>`
            view += view2;
        });

    } else {
        view += `<tr><td colspan="6">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-doi-tac").html(view);
    $("#pageBody").LoadingOverlay("hide");
}


function nhapGiaTri() {
    $(document).on('keyup', '#gia-tri', function () {
        formatCurrency($(this));//chuyen tu so sang string format
    })
    $(document).on('blur', '#gia-tri', function () {
        console.log("blur gia tri");
        let giaTri = valTien($("#gia-tri").val());
        // let giaTri = $("#gia-tri").val();
        for (let i = 0; i < tienNo.length; i++) {
            let no = tienNo[i];
            console.log(i);
            if (giaTri >= no) {
                giaTri = giaTri - no;
                console.log(giaTri);
                console.log(no);
            } else {
                no = giaTri;
                giaTri = 0;
                console.log(giaTri);
                console.log(no);
            }
            $(`#tien-chi-${i}`).val(formatMoney(no));
        }
    })
}

function nhapTienNo() {
    $(document).on('blur', '.tienNo', function () {
        let currentRow = $(this).closest("tr");
        let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        let stt = hang - 1;

        let tien = valTien($(`.tienNo${stt}`).val());

        console.log("hang :" + hang);
        console.log("tien :" + tien);

        let max = valTien($(`.tienNo${stt}`).attr("max"));
        console.log(max);
        console.log("stt :" + stt);
        if (tien > max) {
            alterWarning("Bạn không thể nhập quá số tiền nợ");
            $(`#tien-chi-${stt}`).val(max);
        }
    })
    $(document).on('keypress', '.tienNo', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
            let stt = hang - 1;

            let tien = valTien($(`.tienNo${stt}`).val());

            console.log("hang :" + hang);
            console.log("tien :" + tien);

            let max = valTien($(`.tienNo${stt}`).attr("max"));
            console.log(max);
            console.log("stt :" + stt);
            if (tien > max) {
                alterWarning("Bạn không thể nhập quá số tiền nợ");
                $(`#tien-chi-${stt}`).val(max);
            }
        }
    })
}

function openInNewTab(url) {
    var win = window.open(url, '_blank');
    // win.focus();
}


var tienTraKhachList = [];
var listTienTraNhaCungCaps = [];
function clickLuuPhieu() {
    $("#btn-save").click(function () {
        let loaiChiId = $("#select-loai-chi option:selected").val();
        let giaTri = parseFloat(valTien($("#gia-tri").val()));
        if (giaTri == 0) {
            alterWarning("Bạn cần nhập tiền đã trả");
        } else {
            if (loaiChiId == null) {
                alterWarning("Bạn cần chọn loại chi");
            } else if (loaiChiId == 3) {
                // 1 la chi tien tra khach
                $("#pageBody").LoadingOverlay("show");
                let hoaDonId = 0;
                $('#table-doi-tac .data').each(function () {
                    hoaDonId = $(this).find("td:nth-child(1)").attr("data-id");
                    let tienTraKhach = parseFloat(valTien($(this).find("td:nth-child(5) input").val()));
                    if (tienTraKhach != 0) {
                        let ghiChu = $("#ghi-chu")[0].value;
                        let giaTri = parseFloat(valTien($("#gia-tri")[0].value));

                        var phieuTraKhach = {
                            tienTraKhach: tienTraKhach, // tong tien
                            tienPhaiTra: tienTraKhach,
                            tienDaTra: tienTraKhach,
                            giamGia: 0,
                            lyDo: "",
                            ghiChu: ghiChu
                        };

                        uploadPhieuTraKhach(user.nguoiDungid, phieuTraKhach).then(rs => {
                            if (rs.message === "uploaded") {
                                console.log(rs);
                                phieuTraKhachId = rs.data.id;
                                tienTraKhachList.push(
                                    {
                                        phieuTraKhachId: phieuTraKhachId,
                                        tienDaTra: tienTraKhach
                                    }
                                );

                                console.log("tienTraKhachList" + tienTraKhachList);
                                let tienTraKhachObj = {tienTraKhachList};
                                console.log(tienTraKhachObj);
                                console.log("gia tri" + giaTri);

                                let phieuChiRequest = {
                                    tienDaTra: giaTri,
                                    ghiChu: ghiChu

                                }
                                uploadPhieuChi(phieuChiRequest, user.chinhanhid, user.nguoiDungid, loaiChiId).then(rs2 => {
                                    if (rs2.message === "uploaded") {
                                        console.log("Upload Phieu chi success");
                                        console.log(rs2);
                                        let phieuChiId = rs2.data.id;

                                        let phieuHoatDong = {
                                            giaTri: giaTri
                                        }
                                        //upload phieu hoat dong
                                        uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, 0, 0 , 0, phieuChiId, PHIEU_CHI_HD).then(rs => {
                                            console.log(rs);
                                        });

                                        uploadPhieuChiSoQuy(phieuChiId, giaTri, tienTraKhachObj).then(rs3 => {
                                            callSearchPhieuChiByChiNhanh(user.chinhanhid);
                                            console.log("uploadPhieuChiHoaDon success");
                                            console.log(rs3);
                                            // $("#pageBody").LoadingOverlay("hide");
                                            // $('#select-khach-hang')[0].selectize.destroy();
                                            // $('#select-loai-thu')[0].selectize.destroy();
                                            // $('#gia-tri').val(0);
                                            // $('#ghi-chu').val("");
                                            // $("#table-doi-tac").find('tbody').remove();
                                            // $("#modal-phieu-chi").toggle();
                                            // alterSuccess("upload phiếu chi thành công");
                                            tienTraKhachList = [];
                                            tienNhaCungCapTras = [];
                                        });

                                        creatPhieuChiPhieuTraKhachPDF(phieuChiId, hoaDonId, user.nguoiDungid).then(rs => {
                                            console.log(rs);
                                            if (rs.message == 'uploaded') {

                                                $("#pageBody").LoadingOverlay("hide");
                                                $('#gia-tri').val(0);
                                                $('#ghi-chu').val("");
                                                $("#table-doi-tac").find('tbody').remove();
                                                $("#modal-phieu-chi").toggle();

                                                alterSuccess("upload phiếu chi thành công");
                                                alterSuccess('In phiếu chi thành công');
                                                openInNewTab(rs.data.url);

                                                tienTraKhachList = [];
                                                listTienTraNhaCungCaps = [];

                                                $("#pageBody").LoadingOverlay("hide");
                                            }
                                        })

                                    } else {
                                        $("#pageBody").LoadingOverlay("hide");
                                        alterDanger("upload phiếu chi thất bại");
                                    }
                                });
                            }
                        })
                    }
                })
            } else if (loaiChiId == 1) {
                // 2 la thu tien tra hang cho nha cung cap\
                let phieuNhapHangId = 0;
                $('#table-doi-tac .data').each(function () {
                    $("#pageBody").LoadingOverlay("show");
                    phieuNhapHangId = $(this).find("td:nth-child(1)").attr("data-id");
                    let tienDaTra = parseFloat(valTien($(this).find("td:nth-child(7) input").val()));
                    if (tienDaTra != 0) {
                        listTienTraNhaCungCaps.push(
                            {
                                phieuNhapHangId: phieuNhapHangId,
                                tienDaTra: tienDaTra
                            }
                        );
                    }
                });
                // console.log(tienTraNhaCungCaps);
                // let tienTraNhaCungCapList = {tienTraNhaCungCaps};
                // console.log(tienTraNhaCungCapList);
                let ghiChu = $("#ghi-chu")[0].value;
                let giaTri = parseFloat(valTien($("#gia-tri")[0].value));
                let phieuChiRequest = {
                    tienDaTra: giaTri,
                    ghiChu: ghiChu
                }
                uploadPhieuChi(phieuChiRequest, user.chinhanhid, user.nguoiDungid, loaiChiId).then(rs2 => {
                    if (rs2.message === "uploaded") {
                        console.log("Upload Phieu chi sucess");
                        console.log(rs2);
                        let idPhieuChi = rs2.data.id;

                        let phieuHoatDong = {
                            giaTri: giaTri
                        }
                        //upload phieu hoat dong
                        uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, 0, 0, 0, idPhieuChi, PHIEU_CHI_HD).then(rs => {
                            console.log(rs);
                        });

                        for (let i = 0; i < listTienTraNhaCungCaps.length; i++) {
                            let tienTraNhaCungCaps = [];
                            tienTraNhaCungCaps.push(listTienTraNhaCungCaps[i]);
                            let tienTraNhaCungCapObj = {tienTraNhaCungCaps};
                            uploadPhieuChiNhapHangSoQuy(idPhieuChi, giaTri, tienTraNhaCungCapObj).then(rs3 => {
                                callSearchPhieuChiByChiNhanh(user.chinhanhid);
                                // $("#pageBody").LoadingOverlay("hide");
                                console.log("uploadPhieuChiNhapHangSoQuy sucess");
                                console.log(rs3);
                                // $('#select-khach-hang')[0].selectize.destroy();
                                // $('#select-loai-thu')[0].selectize.destroy();

                            });
                        }

                        creatPhieuChiPhieuNhapHangPDF(idPhieuChi, phieuNhapHangId, user.nguoiDungid).then(rs => {
                            console.log(rs);
                            if (rs.message == 'uploaded') {

                                $("#pageBody").LoadingOverlay("hide");
                                $('#gia-tri').val(0);
                                $('#ghi-chu').val("");
                                $("#table-doi-tac").find('tbody').remove();
                                $("#modal-phieu-chi").toggle();

                                alterSuccess("upload phiếu chi thành công");
                                alterSuccess('In phiếu chi thành công');
                                openInNewTab(rs.data.url);

                                tienTraKhachList = [];
                                listTienTraNhaCungCaps = [];

                                $("#pageBody").LoadingOverlay("hide");

                            } else {
                                alterDanger("In phiếu chi thất bại");
                            }
                        })

                    } else {
                        $("#pageBody").LoadingOverlay("hide");
                        alterDanger("upload phiếu chi thất bại");
                    }
                });
            }
        }

    })

}


//=============== Function Tim kiem loai chi ===================//
function searchLoaiChi() {
    callGetSelectize($('#select-loai-chi'), "id", "tenLoaiChi", "v1/admin/loai-chi/search");
}

//=============== Function Change doi tuong ===================//
function changeDoiTuong() {
    $("#select-loai-chi").change(function () {
        console.log("vao day 2");

        let loaiChi = $("#select-loai-chi option:selected").val();

        console.log("idloaiChi : " + loaiChi);

        if (loaiChi == 3) { // thu tiền khách trả
            $("#select-doi-tuong").val('1');
        } else if (loaiChi == 1) { // NCC
            $("#select-doi-tuong").val('2');
        }

        let doiTuong = $("#select-doi-tuong").val();

        if (doiTuong == 1) { //khach hang
            $('#select-khach-hang')[0].selectize.destroy();
            callGetSelectize($('#select-khach-hang'), "id", "tenKhachHang", "v1/admin/khach-hang/search-text");
        } else if (doiTuong == 2) { // nha cung cap
            console.log("vao day 3");
            $('#select-khach-hang')[0].selectize.destroy();
            callGetSelectize($('#select-khach-hang'), "id", "ten", "v1/admin/nha-cung-cap/search-text");
        }
    })
}

function chooseDoiTac() {
    $('#select-khach-hang').change(function () {
        let id = $("#select-khach-hang option:selected").val();
        let doiTuong = $("#select-doi-tuong").val();
        if (doiTuong == 1) {
            findHoaDonByIdKhachHang(id).then(rs => {
                if (rs.message === "found") {
                    let khachHang = [];
                    khachHang = rs.data.currentElements;
                    console.log(khachHang);
                    setView(khachHang);
                } else {
                    alterWarning("Khách hàng không có hóa đơn nào nợ!");
                }

            })
        } else if (doiTuong == 2) {
            findPhieuNhapHangByIdNhaCungCap(id).then(rs => {
                if (rs.message === "found") {
                    let nhaCungCap = [];
                    nhaCungCap = rs.data.currentElements;
                    console.log(nhaCungCap);
                    setViewNCC(nhaCungCap);
                } else {
                    alterWarning("Khách hàng không có hóa đơn nào nợ!");
                }
            })
        }

    })
}

//=================== Function ===================//
function checkThoiGianTHDN() {
    let rs = true;
    let {check: checkTuNgay, val: valTuNgay} = checkNgayTKDSDN(inputTuNgay);
    let {check: checkDenNgay, val: valDenNgay} = checkNgayTKDSDN(inputDenNgay);
    let selectorDenNgay = inputDenNgay.parents(".form-group");
    if (valTuNgay !== "" && valDenNgay !== "" && !compareDate(valTuNgay, valDenNgay)) {
        rs = false;
        viewError(selectorDenNgay, "Thời gian giới hạn phải lớn hơn");
    } else hiddenError(selectorDenNgay);
    return {check: rs, valTuNgay: valTuNgay, valDenNgay: valDenNgay};
}

//=================== Function ===================//
function checkNgayTKDSDN(selectorNgay) {
    let rs = false;
    let val = selectorNgay.val();
    let selector = selectorNgay.parents(".form-group");
    if (val === '' || regexDate(val)) {
        rs = true;
        hiddenError(selector);
    } else viewError(selector, "Chưa đúng định dạng ngày");
    return {check: rs, val: val};
}

//=================== Function ===================//
//tai excel va in thong tin phieu thu
function taiExcelTK() {
    let arrExcel = [];
    $('#btn-excel').on('click', function () {
        $("#pageBody").LoadingOverlay("show");
        let maPhieuChi = inputMaPhieuChi.val();
        let tenNhanVien = inputTenNhanVien.val();
        let tenLoaiChi = inputTenLoaiChi.val();
        let trangThai = selectTrangThai.val();
        let tenNCC = $("#bimo7 :selected").text();
        // let tenNCC = selectNhaCungCap.text();
        console.log(tenNCC);
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            searchPhieuChi(chiNhanhId, maPhieuChi, tenLoaiChi, tenNhanVien, valTuNgay, valDenNgay, trangThai).then(rs => {
                arrExcel = rs.data.currentElements;
                console.log(arrExcel);
                let nguoiDungId = sessionStorage.getItem("id");
                ajaxGet(`v1/admin/phieu-chi/excel?nguoi-dung-id=${nguoiDungId}&list-phieu-chi=` + arrExcel.map(phieuChi => phieuChi.id))
                    .then(rs => {
                        alterSuccess("Tạo file excel thành công");
                        $("#pageBody").LoadingOverlay("hide");
                        window.open(rs.data.url, '_blank');
                    }).catch(ex => {
                    console.log(ex);
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Tạo file excel thất bại");
                })
            });
        }
    });
    clickPrintElement(".ttcttk");
}
