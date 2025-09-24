//================ Declare variable===================//
var arr = [];

//input
var inputMaPhieuThu, inputTenLoaiThu, inputTenNhanVien, inputTuNgay, inputDenNgay;

//btn
var btnTimKiem, selectTrangThai, selectChiNhanh;

var tienNo = [];
const TRANG_THAI_PHIEU_THU = ["Đã thanh toán", "Chưa thanh toán", "Đang thanh toán"];
const PHIEU_THU_HD = 4;

function viewTrangThaiPT(phieuThu) {
    return TRANG_THAI_PHIEU_THU[phieuThu.trangThai];
}

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-phieu-thu");
    selectChiNhanh = $("#bimo0");
    inputMaPhieuThu = $("#bimo1");
    inputTenLoaiThu = $("#bimo2");
    inputTenNhanVien = $("#bimo3");
    selectTrangThai = $("#bimo4");
    inputTuNgay = $("#bimo5");
    inputDenNgay = $("#bimo6");
    btnTimKiem = $("#btn-search");

    //==========Function constructor========//
    viewChiNhanh();
    clickSearchPhieuThu();
    checkThoiGianTHDN();
    changeTrangThai();
    searchKhachHang();
    searchLoaiThu();
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
    callSearchPhieuThu(chiNhanhId);
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


//=============== Function Tim kiem khach hang ===================//
function searchKhachHang() {
    callGetSelectize($('#select-khach-hang'), "id", "tenKhachHang", "v1/admin/khach-hang/search-text");
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
            findHoaDonByIdNhaCungCap(id).then(rs => {
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
             <th>Còn cần thu</th>
             <th>Tiền thu</th>                
                </tr>`;
    let len = nhaCungCap.length;
    if (len > 0) {
        nhaCungCap.map((item, index) => {
            let no = item.tongTien - item.tienDaTra;
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
                    <td>${formatMoney(item.tongTien)}</td>
                    <td>${formatMoney(item.tienDaTra)}</td>
                    <td>${formatMoney(item.tongTien - item.tienDaTra)}</td>
                 <td><input type="text" class="none-arrow tienNo" id="tien-thu-${index}" style="width: 90px" value="${formatMoney(no)}" max="${formatMoney(item.tongTien - item.tienKhachTra)}"></td>
                </tr>`
            view += view2;
        });

    } else {
        view += `<tr><td colspan="6">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-doi-tac").html(view);
    $("#pageBody").LoadingOverlay("hide");
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
             <th>Tiền đã trả</th>
             <th>Còn cần thu</th>
             <th>Tiền thu</th>                
                </tr>`;
    let len = khachHang.length;
    if (len > 0) {

        khachHang.map((item, index) => {

            let no = item.tongTien - item.tienKhachTra;
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
                    <td>${formatMoney(item.tienKhachTra)}</td>
                    <td>${formatMoney(item.tongTien - item.tienKhachTra)}</td>
                    <td><input type="text" class="none-arrow tienNo tienNo${index}" id="tien-thu-${index}" style="width: 90px" value="${formatMoney(no)}" max="${formatMoney(item.tongTien - item.tienKhachTra)}"></td>
                </tr>`
            view += view2;
        });
    } else {
        view += `<tr><td colspan="6">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-doi-tac").html(view);
    $("#pageBody").LoadingOverlay("hide");
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
            $(`#tien-thu-${stt}`).val(max);
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
                $(`#tien-thu-${stt}`).val(max);
            }
        }
    })
}


function nhapGiaTri() {
    $(document).on('keyup', '#gia-tri', function () {
        console.log("vao keyup");
        formatCurrency($(this));//chuyen tu so sang string format
    })

    $(document).on('blur', '#gia-tri', function () {
        console.log("blur gia tri");
        // let giaTri = $("#gia-tri").val();//
        let giaTri = valTien($("#gia-tri").val());
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
            $(`#tien-thu-${i}`).val(formatMoney(no));
        }
    })
    $(document).on('keypress', '#gia-tri', function (e) {
        if (e.which === 13) {
            console.log("enter gia tri");
            // let giaTri = $("#gia-tri").val();//
            let giaTri = valTien($("#gia-tri").val());
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
                $(`#tien-thu-${i}`).val(formatMoney(no));
            }
        }
    })

}

function openInNewTab(url) {
    var win = window.open(url, '_blank');
    // win.focus();
}


var listTienKhachTras = [];
var tienNhaCungCapTras = [];

function clickLuuPhieu() {
    $("#btn-save").click(function () {
        let loaiThuId = $("#select-loai-thu option:selected").val();
        let giaTri = parseFloat(valTien($("#gia-tri").val()));

        if (giaTri == 0) {
            alterWarning("Bạn cần nhập tiền đã trả");
        } else {
            if (loaiThuId == null) {
                alterWarning("Bạn cần chọn loại thu");
            } else if (loaiThuId == 1) {
                // 1 la thu tien khach tra

                let ghiChu = $("#ghi-chu")[0].value;
                let giaTri = parseFloat(valTien($("#gia-tri")[0].value));
                let phieuThuRequest = {
                    tienDaTra: giaTri,
                    ghiChu: ghiChu
                }
                let hoaDonId = 0;

                $("#pageBody").LoadingOverlay("show");
                $('#table-doi-tac .data').each(function () {
                    hoaDonId = $(this).find("td:nth-child(1)").attr("data-id");
                    let tienKhachTra = parseFloat(valTien($(this).find("td:nth-child(7) input").val()));
                    if (tienKhachTra != 0) {
                        listTienKhachTras.push(
                            {
                                hoaDonId: hoaDonId,
                                tienKhachTra: tienKhachTra
                            }
                        );
                    }
                })
                // upload phieu thu, so quy
                uploadPhieuThu(phieuThuRequest, user.chinhanhid, user.nguoiDungid, loaiThuId).then(rs2 => {
                    if (rs2.message === "uploaded") {
                        console.log("Upload Phieu thu sucess");
                        console.log(rs2);
                        let idPhieuThu = rs2.data.id;

                        let phieuHoatDong = {
                            giaTri: giaTri
                        }
                        //upload phieu hoat dong
                        uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, 0, 0, idPhieuThu, 0, PHIEU_THU_HD).then(rs => {
                            console.log(rs);
                        });

                        for (let i = 0; i < listTienKhachTras.length; i++) {
                            let tienKhachTras = [];
                            tienKhachTras.push(listTienKhachTras[i]);
                            let tienKhachTraObj = {tienKhachTras};
                            console.log(tienKhachTraObj);
                            // upload phieu thu - hoa don
                            uploadPhieuThuSoQuy(idPhieuThu, giaTri, tienKhachTraObj).then(rs3 => {
                                console.log("uploadPhieuThuHoaDon sucess");
                                console.log(rs3);
                            });

                            //load bang view phieu thu
                            callBackSearchPhieuThu();
                        }

                        // in phieu thu PDF
                        creatPhieuThuPDF(idPhieuThu, hoaDonId, user.nguoiDungid).then(rs2 => {
                            console.log(rs2);
                            if (rs2.message == 'uploaded') {
                                $("#pageBody").LoadingOverlay("hide");
                                alterSuccess("upload phiếu thu thành công");
                                alterSuccess('In phiếu thu thành công');
                                openInNewTab(rs2.data.url);
                            } else {
                                alterDanger("In phiếu thu thất bại");
                            }
                        });
                        // $('#select-khach-hang')[0].selectize.destroy();
                        // $('#select-loai-thu')[0].selectize.destroy();
                        $('#gia-tri').val(0);
                        $('#ghi-chu').val("");
                        $("#table-doi-tac").find('tbody').remove();
                        $("#modal-phieu-thu").toggle();

                        // tienKhachTras = [];
                        // tienNhaCungCapTras = [];

                        //lap 1 hoat dong id=4
                    } else {
                        $("#pageBody").LoadingOverlay("hide");
                        alterDanger("upload phiếu thu thất bại");
                    }
                });
            } else if (loaiThuId == 2) {
                // 2 la thu tien tra hang cho nha cung cap

                let phieuTraHangNhapId = 0;

                $('#table-doi-tac .data').each(function () {
                    $("#pageBody").LoadingOverlay("show");
                    phieuTraHangNhapId = $(this).find("td:nth-child(1)").attr("data-id");
                    let tienDaTra = parseFloat(valTien($(this).find("td:nth-child(7) input").val()));
                    if (tienDaTra != 0) {
                        tienNhaCungCapTras.push(
                            {
                                phieuTraHangNhapId: phieuTraHangNhapId,
                                tienDaTra: tienDaTra
                            }
                        );
                    }
                });
                console.log(tienNhaCungCapTras);
                let tienNhaCungCapTraList = {tienNhaCungCapTras};
                console.log(tienNhaCungCapTraList);
                let ghiChu = $("#ghi-chu")[0].value;
                let giaTri = parseFloat(valTien($("#gia-tri")[0].value));
                let phieuThuRequest = {
                    tienDaTra: giaTri,
                    ghiChu: ghiChu
                }
                uploadPhieuThu(phieuThuRequest, user.chinhanhid, user.nguoiDungid, loaiThuId).then(rs2 => {
                    if (rs2.message === "uploaded") {
                        console.log("Upload Phieu thu sucess");
                        console.log(rs2);
                        let idPhieuThu = rs2.data.id;

                        let phieuHoatDong = {
                            giaTri: giaTri
                        }
                        //upload phieu hoat dong
                        uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, 0, 0, idPhieuThu, 0, PHIEU_THU_HD).then(rs => {
                            console.log(rs);
                        });

                        // upload so quy
                        uploadPhieuThuTraHangSoQuy(idPhieuThu, giaTri, tienNhaCungCapTraList).then(rs3 => {
                            console.log("uploadPhieuThuHoaDon sucess");
                            console.log(rs3);
                            // $('#select-khach-hang')[0].selectize.destroy();
                            // $('#select-loai-thu')[0].selectize.destroy();

                            //load bang view phieu thu
                            callBackSearchPhieuThu();

                        });

                        // tao phieu thu PDF
                        creatPhieuThuPhieuTraHangNhapPDF(idPhieuThu, phieuTraHangNhapId, user.nguoiDungid).then(rs2 => {
                            console.log("tao phieu thu pdf thanh cong");
                            console.log(rs2);
                            if (rs2.message == "uploaded") {
                                $("#pageBody").LoadingOverlay("hide");
                                alterSuccess("upload phiếu thu thành công");
                                alterSuccess('In phiếu thu thành công');
                                openInNewTab(rs2.data.url);
                            } else {
                                alterDanger("In phiếu thu thất bại");
                            }
                        })

                        $('#gia-tri').val(0);
                        $('#ghi-chu').val("");
                        $("#table-doi-tac").find('tbody').remove();
                        $("#modal-phieu-thu").toggle();
                        listTienKhachTras = [];
                        tienNhaCungCapTras = [];

                    } else {
                        $("#pageBody").LoadingOverlay("hide");
                        alterDanger("upload phiếu thu thất bại");
                    }
                });
            }
        }

    })

}

function changeDoiTuong() {
    $("#select-loai-thu").change(function () {
        console.log("vao day 2");

        let loaiThu = $("#select-loai-thu option:selected").val();

        console.log("idloaithu : " + loaiThu);

        if (loaiThu == 1) { // thu tiền khách trả
            $("#select-doi-tuong").val('1');
        } else if (loaiThu == 2) { // NCC
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

function searchLoaiThu() {
    callGetSelectize($('#select-loai-thu'), "id", "tenLoaiThu", "v1/admin/loai-thu/search");
}

//=================== Function ===================//
function callBackSearchPhieuThu() {
    let maPhieuThu = inputMaPhieuThu.val();
    let tenNhanVien = inputTenNhanVien.val();
    let tenLoaiThu = inputTenLoaiThu.val();
    let trangThai = selectTrangThai.val();
    let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
    if (check) {
        valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
        valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
        callSearchPhieuThu(maPhieuThu, "", tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 10);
    }
}

//=================== Function ===================//
function clickSearchPhieuThu() {
    btnTimKiem.click(function () {
        let maPhieuThu = inputMaPhieuThu.val();
        let tenNhanVien = inputTenNhanVien.val();
        let tenLoaiThu = inputTenLoaiThu.val();
        let trangThai = selectTrangThai.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            callSearchPhieuThu(maPhieuThu, "", tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 10);
        }
    });
}

//=================== Function ===================//
function callSearchPhieuThu(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    searchPhieuThu(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai).then(rs => {
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
                        setViewPhieuThu(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    searchPhieuThu(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        setViewPhieuThu(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setViewPhieuThu(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Không tìm thấy phiếu thu");
        setViewPhieuThu(1);
    });
}


//=================== Function ===================//
function setViewPhieuThu(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Mã phiếu thu</th>
                <th>Thời gian</th>
                <th>Loại thu</th>
                <th>Nhân viên</th>
                <th>Giá trị</th>
                <th>Trạng thái</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-phieu-thu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.maPhieu)}</td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.loaiThu.tenLoaiThu)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${formatMoney(item.tienDaTra)}</td>
                    <td><button type="button" class="trang-thai-btn trang-thai-btn-${index} btn btn-default" style="width: 120px">
                            ${viewTrangThaiPT(item)}
                        </button>
                    </td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td>  <td></td><td></td><td></td><td></td><td></td><td></td></tr>`
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
        let phieuThuId = currentRow.find("td:eq(0)").attr("data-id");
        let valueCu = TRANG_THAI_PHIEU_THU.indexOf(trangThaiCu);
        $("#trang-thai-cu").text(`Trạng thái cũ : ${trangThaiCu}`);
        $("#select-trang-thai").val(valueCu);
        $('#select-trang-thai').trigger('change');
        $("#modal-trang-thai").modal('toggle');
        $("#confirm-btn").click(function () {
            let valueMoi = $("#select-trang-thai").find(':selected')[0].value;
            if (valueCu != valueMoi) {
                $("#pageBody").LoadingOverlay("show");
                setTrangThaiPhieuThu(phieuThuId, valueMoi).then(rs => {
                    if (rs.message === "success") {
                        let hang = parseInt(currentRow.find("td:eq(0)").text());
                        $(`.trang-thai-btn-${hang - 1}`).text($("#select-trang-thai").find(':selected')[0].text);
                        $("#modal-trang-thai").modal('hide');
                        alterSuccess("Thay đổi trạng thái phiếu thu thành công");
                    } else {
                        alterDanger("Thay đổi trạng thái phiếu thu không thành công")
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
        let maPhieuThu = inputMaPhieuThu.val();
        let tenNhanVien = inputTenNhanVien.val();
        let tenLoaiThu = inputTenLoaiThu.val();
        let trangThai = selectTrangThai.val();
        let {check, valTuNgay, valDenNgay} = checkThoiGianTHDN();
        if (check) {
            valTuNgay = valTuNgay === '' ? null : convertDateISO(valTuNgay).toISOString();
            valDenNgay = valDenNgay === '' ? null : convertDateISO(valDenNgay).toISOString();
            searchPhieuThu(chiNhanhId, maPhieuThu, tenLoaiThu, tenNhanVien, valTuNgay, valDenNgay, trangThai, 1, 10).then(rs => {
                arrExcel = rs.data.currentElements;
                console.log(arrExcel);
                let nguoiDungId = sessionStorage.getItem("id");
                ajaxGet(`v1/admin/phieu-thu/excel?nguoi-dung-id=${nguoiDungId}&list-phieu-thu=` + arrExcel.map(phieuThu => phieuThu.id))
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




