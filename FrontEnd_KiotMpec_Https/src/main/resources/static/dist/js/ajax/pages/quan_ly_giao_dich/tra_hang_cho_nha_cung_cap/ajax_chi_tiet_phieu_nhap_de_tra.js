//================ Declare variable===================//
var table;
let tongTien, nhaCungCapId;
var arr = [];
const PHIEU_TRA_HANG_NHAP_HD = 8 //id hoạt hoạt động của phiếu trả hàng nhập
//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    btnUpload = $("#btn-tra-hang");
    table = $("#table-hang-hoa");
    let searchParams = new URLSearchParams(window.location.search);
    let param = searchParams.get('phieu-nhap-id');

    //==========Function constructor========//
    callSearch(param);
    clickThayDoiSoLuong();
    enterThayDoiSoLuong();
    clickUpload();

})

//=============== Function detail ===================//

//=================== Function ===================//
function clickThayDoiSoLuong() {
    $(document).on('click', '.soLuong ', function () {
        //table 1
        let currentRow = $(this).closest("tr");
        let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
        let sl = $("td input")[hang - 1].value;
        let stt = hang - 1;
        let giaNhap = parseFloat(valTien(currentRow.find("td:eq(4)").text()));
        let tongTien = (sl * giaNhap);
        currentRow.find("td:eq(6)").text(tongTien);
        console.log(stt);
        let tongCha = 0;
        let len = arr.length;
        for (let i = 0; i < len; i++) {
            let thanhTien = parseFloat(valTien($('td.tong' + i)[0].textContent));
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
    })
}

//=================== Function ===================//
function enterThayDoiSoLuong() {
    $(document).on('keypress', '.soLuong', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
            let stt = hang - 1;
            let sl = parseInt($("td input")[stt].value);
            let slMax = parseInt($('span.soLuongMax')[stt].value);
            if (sl <= slMax) {
                let giaNhap = parseFloat(valTien(currentRow.find("td:eq(4)").text()));
                let tongTien = (sl * giaNhap);
                currentRow.find("td:eq(6)").text(tongTien);
                let tongCha = 0;
                let len = arr.length;
                for (i = 0; i < len; i++) {
                    let thanhTien = parseFloat(valTien($('td.tong' + i)[1].value));
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
                let giaNhap = parseFloat(valTien(currentRow.find("td:eq(4)").text()));
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
function clickUpload() {
    btnUpload.click(function () {
        $("#pageBody").LoadingOverlay("show");
        var currentdate = new Date();
        var datetime = currentdate.getFullYear() + "-"
            + (currentdate.getMonth() < 10 ? "0" : "") + (currentdate.getMonth() + 1) + "-"
            + (currentdate.getDate() < 10 ? "0" : "") + currentdate.getDate() + "T"
            + (currentdate.getHours() < 10 ? "0" : "") + currentdate.getHours() + ":"
            + (currentdate.getMinutes() < 10 ? "0" : "") + currentdate.getMinutes() + ":"
            + (currentdate.getSeconds() < 10 ? "0" : "") + currentdate.getSeconds();
        console.log(datetime);
        let tong = parseFloat(valTien($("#canTra")[0].textContent));
        let lyDo= $("#ly-do").val();
        var phieuTraHang = {
            thoiGian: datetime,
            tongTien: tong,
            tienPhaiTra: tong,
            tienDaTra: tong,
            lyDo:lyDo
        };
        let phieuNhapHangId = 0;
        uploadPhieuTraHangNhap(nguoiDungId, nhaCungCapId, phieuTraHang).then(rs => {
            if (rs.message === "uploaded") {
                phieuNhapHangId = rs.data.id;
                var hangHoaIdList = [];
                var phieuTraHangNhapChiTietList = [];

                arr.map((item, index) => {
                    hangHoaIdList[index] = item.chiNhanhHangHoa.hangHoa.id;
                    let sl = $("td input")[index].value;
                    let giaNhap = item.giaNhap;
                    let tongTien = sl * giaNhap;
                    phieuTraHangNhapChiTietList[index] = {
                        soLuong: sl,
                        tongTien: tongTien
                    };
                })

                let phieuHoatDong = {
                    giaTri: tong
                }
                //upload phieu hoat dong
                uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, 0, phieuNhapHangId, 0, 0, PHIEU_TRA_HANG_NHAP_HD).then(rs => {
                    console.log(rs);
                });

                let phieuThuRequest = {
                    tienDaTra: tong,
                    ghiChu: ""
                }

                // loai thu = 2 (phieu tra hang nhap )
                uploadPhieuThu(phieuThuRequest, user.chinhanhid, user.nguoiDungid, 2).then(rs2 => {
                    if (rs2.message === "uploaded") {
                        console.log("Upload Phieu thu sucess");
                        console.log(rs2);
                        let idPhieuThu = rs2.data.id;
                        uploadPhieuThuPhieuTraHangNhap(phieuNhapHangId,idPhieuThu, tong).then(rs3 => {
                            console.log("uploadPhieuThuPhieuTraHangNhap sucess");
                        })
                    }
                });

                var phieuTraHangNhapForm = {
                    phieuTraHangNhapId: phieuNhapHangId,
                    chiNhanhId: 1,
                    hangHoaIdList: hangHoaIdList,
                    phieuTraHangNhapChiTietList: phieuTraHangNhapChiTietList
                };
                uploadPhieuTraHangNhapChiTiet(phieuTraHangNhapForm).then(r => {
                    if (r.message === "success") {
                        $("#pageBody").LoadingOverlay("hide");
                        alterSuccess("Thêm phiếu trả hàng thành công");
                        $("#btn-tra-hang").prop('disabled', true);
                        $("#btn-print-pdf").prop('disabled', false);
                        inPDf(r.data.maPhieu);
                    } else {
                        $("#pageBody").LoadingOverlay("hide");
                        console.log("Error : " + r.message);
                        alterDanger("Thêm phiếu trả hàng không thành công");
                    }
                })
            } else {
                alterDanger("Thêm phiếu trả hàng không thành công");
            }
        })
    })
}

//=================== Function ===================//
function callSearch(phieuNhapId) {
    $("#pageBody").LoadingOverlay("show");
    danhSachNhapHangChiTiet(phieuNhapId).then(rs => {
        if (rs.message === "found") {
            setPhieu(rs.data.currentElements);
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
                        arr = rs.data.currentElements;
                        setView(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    danhSachNhapHangChiTiet(phieuNhapId, pagination.pageNumber).then(rs => {
                        arr = rs.data.currentElements;
                        setView(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arr = [];
            setView(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Phieu Nhap");
        setView(1);
    })
}

//=================== Function ===================//
function setPhieu(item) {
    $("#tenChiNhanh").append(`${item[0].chiNhanhHangHoa.chiNhanh.diaChi}`);
    $("#nhaCungCap").append(`${item[0].phieuNhapHang.nhaCungCap.ten}`);
    let thoiGian = viewDateTime(item[0].phieuNhapHang.thoiGian);
    $("#ngayNhap").append(`${thoiGian}`);
    $("#diaChi").append(`${item[0].phieuNhapHang.nhaCungCap.diaChi}`);
    $("#tenNhanVien").append(`${item[0].phieuNhapHang.nguoiDung.hoVaTen}`);
    $("#dienThoaiNhaCungCap").append(`${item[0].phieuNhapHang.nhaCungCap.dienThoai}`);
    $("#maPhieuNhap").append(`${item[0].phieuNhapHang.maPhieu}`);
    $("#giaGoc").append(formatMoney(`${item[0].phieuNhapHang.tongTien}`));
    $("#phaiTra").text(formatMoney(`${item[0].phieuNhapHang.tongTien}`));
    $("#canTra").text(formatMoney(`${item[0].phieuNhapHang.tongTien}`));
    tongTien = item[0].phieuNhapHang.tongTien;
    chiNhanhId = item[0].chiNhanhHangHoa.chiNhanh.id;
    nhaCungCapId = item[0].phieuNhapHang.nhaCungCap.id;
}

//=================== Function ===================//
function setView() {
    let view = `<tr>
                <th>STT</th>
                <th>Mã hàng hóa</th>
                <th>Tên hàng hóa</th>
                <th>Số lượng</th>
                <th>Giá nhập </th>
                <th>Thành tiền </th>
                </tr>`;
    let len = arr.length;
    let pageNumber = 1;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.chiNhanhHangHoa.hangHoa.ma)}</td>
                    <td>${viewField(item.chiNhanhHangHoa.hangHoa.tenHangHoa)}</td>
                     <td  style="text-align: center; vertical-align: middle">
                     <input type="number" class="soLuong" data-id="${viewField(item.soLuong)}"  max="${viewField(item.soLuong)}" min="0" value="${viewField(item.soLuong)}" style="width: 40px">
                     <span>/</span>
                     <span class="soLuongMax soLuongMax${index}" value="${viewField(item.soLuong)}">${viewField(item.soLuong)}</span>
                     </td>
                    <td class="giaNhap"> ${formatMoney(item.giaNhap)} </td>   
                     <td class="tong tong${index}">${formatMoney(item.tongTien)} </td>    
                </tr>`);

        if (len < 10) {
            len++;
            for (let i = len; i <= 5; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td></tr>`
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
            $("#pageBody").LoadingOverlay("show");
            createPhieuTraHangNhapPDF(ma, nguoiDungId).then(rs => {
                if (rs.message == 'uploaded') {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess('In hóa đơn thành công');
                    window.open(rs.data.url, '_blank');
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("In hóa đơn thất bại");
                }
                $("#pageBody").LoadingOverlay("hide");
            });
        }
    });
}
