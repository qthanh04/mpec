//================ Declare variable===================//
var btnSearch, table;
let tongTien;
var arr = [];

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-hang-hoa");
    selectChiNhanh = $("#bimo2");
    let searchParams = new URLSearchParams(window.location.search);
    let param = searchParams.get('id');

    //==========Function constructor========//
    callSearch(param);
    taiExcelTK();
    inPhieuNhap();

})

//=============== Function detail ===================//

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
    $("#tenCongTy").append(`${item[0].chiNhanhHangHoa.chiNhanh.tongCongTy.tenDoanhNghiep}`);
    $("#tenChiNhanh").append(`${item[0].chiNhanhHangHoa.chiNhanh.diaChi}`);
    $("#nhaCungCap").append(`${item[0].phieuNhapHang.nhaCungCap.ten}`);
    let thoiGian = viewDateTime(item[0].phieuNhapHang.thoiGian);
    $("#ngayNhap").append(`${thoiGian}`);
    $("#diaChi").append(`${item[0].phieuNhapHang.nhaCungCap.diaChi}`);
    $("#tenNhanVien").append(`${item[0].phieuNhapHang.nguoiDung.hoVaTen}`);
    $("#dienThoaiNhaCungCap").append(`${item[0].phieuNhapHang.nhaCungCap.dienThoai}`);
    $("#maPhieuNhap").append(`${item[0].phieuNhapHang.maPhieu}`);
    $("#giaGoc").append(formatMoney(`${item[0].phieuNhapHang.tongTien}`));
    $("#phaiTra").text(formatMoney(`${item[0].phieuNhapHang.tienDaTra}`));
    $("#canTra").text(formatMoney(`${item[0].phieuNhapHang.tienPhaiTra}`));
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
                     <span class="soLuongMax soLuongMax${index}" value="${viewField(item.soLuong)}">${viewField(item.soLuong)}</span>
                     </td>
                    <td class="giaNhap"> ${formatMoney(item.giaNhap)} </td>   
                     <td class="tong tong${index}">${formatMoney(item.tongTien)} </td>    
                </tr>`);

        if (len < 5) {
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
function taiExcelTK() {
    $('#btn-excel').on('click', function () {
        console.log("inds");
        $("#pageBody").LoadingOverlay("show");
        ajaxGet('v1/admin/phieu-nhap-hang-chi-tiet/excel?list-phieu-nhap-hang-chi-tiet=' + arr.map(hoaDonChiTiet => hoaDonChiTiet.id))
            .then(rs => {
                window.open(rs.data, '_blank');
                $("#pageBody").LoadingOverlay("hide");
            }).catch(ex => {
            console.log(ex);
            alterDanger("Tạo file excel thất bại");
            $("#pageBody").LoadingOverlay("hide");
        })
    });
    clickPrintElement(".ttcttk");
}

//=================== Function ===================//
function inPhieuNhap() {
    $("#btn-print-pdf").click(function () {
        $("#pageBody").LoadingOverlay("show");
        let maPhieuNhap = arr[0].phieuNhapHang.maPhieu;
        let nguoiDundId = sessionStorage.getItem("id");
        createPhieuNhapPDF(maPhieuNhap, nguoiDundId).then(rs => {
            if (rs.message == 'uploaded') {
                $("#pageBody").LoadingOverlay("hide");
                alterSuccess('In hóa đơn thành công');
                window.open(rs.data.url, '_blank');
            } else {
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("In hóa đơn thất bại");
            }
        })
    });
}
