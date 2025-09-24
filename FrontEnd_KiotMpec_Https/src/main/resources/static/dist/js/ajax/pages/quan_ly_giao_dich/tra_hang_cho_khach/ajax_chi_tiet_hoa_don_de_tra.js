//================ Declare variable===================//
var table;
var arr = [];
var searchParams = new URLSearchParams(window.location.search);
let tongTien, btnUpload;
var param = searchParams.get('hoa-don-id');
const PHIEU_TRA_KHACH_HD = 6;

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    btnUpload = $("#btn-tra-hang");
    table = $("#table-hang-hoa");

    //==========Function constructor========//
    callSearch(param);
    clickThayDoiSoLuong();
    enterThayDoiSoLuong();
    clickUpload();
})

//=============== Function detail ===================//

//=================== Function ===================//
function clickUpload() {
    btnUpload.click(function () {
        let tienTraKhach = parseFloat(valTien($("#canTra")[0].textContent));
        let lyDo= $("#ly-do").val();
        var phieuTraKhach = {
            tienTraKhach: tienTraKhach, // tong tien
            tienPhaiTra: tienTraKhach,
            tienDaTra: tienTraKhach,
            giamGia: 0,
            lyDo: lyDo,
            ghiChu: ""
        };
        console.log("Phieu Tra Hang" + JSON.stringify(phieuTraKhach));
        $("#pageBody").LoadingOverlay("show");
        uploadPhieuTraKhach(user.nguoiDungid, phieuTraKhach).then(rs => {
            if (rs.message === "uploaded") {
                phieuTraKhachId = rs.data.id;
                let hoaDonIdList = [];
                let phieuTraKhachChiTietList = [];
                arr.map((item, index) => {
                    hoaDonIdList[index] = item.id;
                    let sl = $("td input")[index].value;
                    let giaBan = item.lichSuGiaBan.giaBan;
                    let thanhTien = sl * giaBan;
                    phieuTraKhachChiTietList[index] = {
                        soLuong: sl,
                        tongTien: thanhTien
                    };
                })

                let phieuHoatDong = {
                    giaTri: tienTraKhach
                }
                //upload phieu hoat dong
                uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, phieuTraKhachId, 0, 0 , 0, 0, PHIEU_TRA_KHACH_HD).then(rs => {
                    console.log(rs);
                });

                uploadPhieuChi(phieuTraKhach, user.chinhanhid, user.nguoiDungid, 3).then(rs => {
                    console.log(rs);
                    console.log("upload phieu chi thanh cong");
                    idPhieuChi = rs.data.id;
                    uploadPhieuChiNhapHangTraKhach(phieuTraKhachId, idPhieuChi, tongTien).then(rs2 => {
                        console.log("upload phieuChiNhapHangTraKhach thanh cong");
                    })
                })

                var phieuTraKhachForm = {
                    phieuTraKhachId: phieuTraKhachId,
                    hoaDonChiTietId: hoaDonIdList,
                    phieuTraKhachChiTietList: phieuTraKhachChiTietList
                };
                uploadPhieuTraKhachChiTiet(phieuTraKhachForm).then(r => {
                    if (r.message === "success") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Thêm phiếu trả hàng cho khách thành công");
                        $("#btn-tra-hang").prop('disabled', true);
                        $("#btn-print-pdf").prop('disabled', false);
                        console.log(r.data);
                        inPDf(r.data.ma);
                    } else {
                        console.log("Error : " + r.message);
                        alterDanger("Thêm phiếu trả hàng cho khách không thành công");
                    }
                    $("#pageBody").LoadingOverlay("hide");
                });
            } else {
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Thêm phiếu trả hàng cho khách không thành công");
            }
        });
    });
}

//=================== Function ===================//
function clickThayDoiSoLuong() {
    $(document).on('click', '.soLuong ', function () {
        //table 1
        let currentRow = $(this).closest("tr");
        let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        let sl = $("td input")[hang - 1].value;
        let giaBan = parseFloat(valTien(currentRow.find("td:eq(3)").text()));
        let giamGia = parseFloat(valTien(currentRow.find("td:eq(5)").text()));
        tongTien = (sl * giaBan) * (1 - giamGia);
        currentRow.find("td:eq(6)").text(tongTien);
        let tenHang = currentRow.find("td:eq(1)").text();
        let totals = [0, 0, 0, 0];
        let tongCha = 0;
        let $dataRows = $("#sum_table tr:not('th')");
        $dataRows.each(function () {
            $(this).find('.tong').each(function (i) {
                totals[i] = parseFloat(valTien($(this).html(), 3));
                tongCha = tongCha + totals[i];
            });
        });
        $("#phaiTra").text(formatMoney(`${tongCha}`));

        let stt = parseInt(hang) - 1;
        $(`.tong${stt}`).each(function () {
            $(this).text(formatMoney(`${tongTien}`));
        });
        $("#canTra").text(formatMoney(`${tongCha}`));

    })
}

//=================== Function ===================//
function enterThayDoiSoLuong() {
    $(document).on('keypress', '.soLuong ', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
            let stt = hang - 1;
            let sl = parseInt($("td input")[stt].value);
            let slMax = parseInt($('span.soLuongMax')[stt].textContent);
            if (sl <= slMax) {
                let giaBan = parseFloat(valTien(currentRow.find("td:eq(3)").text()));
                console.log(giaBan);
                tongTien = (sl * giaBan);
                currentRow.find("td:eq(6)").text(tongTien);
                console.log(stt);
                let tongCha = 0;
                let len = arr.length;
                for ( let i = 0; i < len; i++) {
                    let thanhTien = parseFloat(valTien($('td.tong' + i)[1].textContent));
                    if (i != stt) {
                        tongCha = tongCha + thanhTien;
                    } else {
                        tongCha = tongCha + tongTien;
                    }
                    console.log("Lan " + i + " : " + thanhTien);
                }
                console.log(tongCha);
                $("#phaiTra").text(formatMoney(`${tongCha}`));

                $(`.tong${stt}`).each(function () {
                    $(this).text(formatMoney(`${tongTien}`));
                });
                $("#canTra").text(formatMoney(`${tongCha}`));
            } else {
                alert("Số lượng trả quá số lượng cho phép!");
                $("td input").eq(stt).val(slMax);
                let giaNhap = parseFloat(valTien(currentRow.find("td:eq(3)").text()));
                let tongTien = (slMax * giaNhap);
                currentRow.find("td:eq(6)").text(tongTien);
                let totals = [0, 0, 0, 0];
                let tongCha = 0;
                let $dataRows = $("#sum_table tr:not('th')");
                $dataRows.each(function () {
                    $(this).find('.tong').each(function (i) {
                        totals[i] = parseFloat(valTien($(this).html(), 3));
                        tongCha = tongCha + totals[i];
                    });
                });
                $("#phaiTra").text(formatMoney(`${tongCha}`));

                $(`.tong${stt}`).each(function () {
                    $(this).text(formatMoney(`${tongTien}`));
                });
                $("#canTra").text(formatMoney(`${tongCha}`));
            }
        }
    })
}

//=================== Function ===================//
function callSearch(hoaDonId) {
    $("#pageBody").LoadingOverlay("show");
    danhSachHoaDonChiTiet(hoaDonId).then(rs => {
        if (rs.message === "found") {
            setPhieu(rs.data.currentElements);
            arr = rs.data.currentElements;
            setView();
            danhSachHoaDonChiTiet(hoaDonId).then(rs => {
                arr = rs.data.currentElements;
                setView();
            }).catch(err => console.log(err))
        }
    })
        .catch(err => {
            console.log(err);
            alterDanger("Server Error - Search Hoa Don");
            setView(1);
        })
}

//=================== Function ===================//
function setPhieu(item) {

    $("#maHoaDon").append(`${item[0].hoaDon.ma}`);
    $("#khachHang").append(`${item[0].hoaDon.khachHang.tenKhachHang}`);
    let thoiGian = viewDateTime(item[0].hoaDon.thoiGian);
    $("#ngayBan").append(`${thoiGian}`);
    $("#diaChi").append(`${item[0].hoaDon.khachHang.diaChi}`);
    $("#tenNhanVien").append(`${item[0].hoaDon.nguoiDung.hoVaTen}`);
    $("#dienThoaiKhach").append(`${item[0].hoaDon.nguoiDung.soDienThoai}`);
    $("#giaGoc").append(formatMoney(`${item[0].hoaDon.tongTien}`));
    $("#phaiTra").text(formatMoney(`${item[0].hoaDon.tongTien}`));
    $("#canTra").text(formatMoney(`${item[0].hoaDon.tongTien}`));
    tongTien = item[0].hoaDon.tongTien;
    console.log("Tong Tien : " + tongTien);
}

//=================== Function ===================//
function setView() {
    let view = `<tr>
                <th>STT</th>
               <th>Tên hàng</th>
               <th>Số lượng</th>
               <th>Giá bán </th>
               <th>Đơn vị</th>
               <th>Giảm giá</th>
               <th>Tổng tiền</th>
                </tr>`;

    let len = arr.length;


    let pageNumber = 1;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" id="giaTri" class="click-san-pham">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.lichSuGiaBan.donViHangHoa.hangHoa.tenHangHoa)}</td>
                    <td  style="text-align: center; vertical-align: middle">
                    <input type="number" class="soLuong" data-id="${viewField(item.soLuong)}"  max="${viewField(item.soLuong)}" min="0" value="${viewField(item.soLuong)}" style="width: 40px">
                    <span>/</span>
                    <span class="soLuongMax" >${viewField(item.soLuong)}</span></td>
                    <td class="giaBan">${formatMoney(item.lichSuGiaBan.giaBan)}</td>
                    <td>${viewField(item.lichSuGiaBan.donViHangHoa.donVi.tenDonVi)}</td>    
                    <td>${formatMoney(item.giamGia)}</td>    
                     <td class="tong tong${index}">${formatMoney(item.tongGia)}</td>  
                    
                </tr>`);

        if (len < 10) {
            len++;
            for (let i = len; i <= 5; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function inPDf(ma) {
    $("#btn-print-pdf").click(function () {
        $("#pageBody").LoadingOverlay("show");
        if (typeof ma !== "undefined") {
            console.log("print " + ma);
            createPhieuKhachTraHangPDF(ma, nguoiDungId).then(rs => {
                if (rs.message == 'uploaded') {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess('In hóa đơn thành công');
                    window.open(rs.data.url, '_blank');
                    console.log(rs.data.url);
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("In hóa đơn thất bại");
                }
            });
        }
    });
}


