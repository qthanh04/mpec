//================ Declare variable===================//
//select
var selectNguoiNhap, selectNhaCungCap, selectHangHoa;

//input
var inputGiamGia, inputGhiChu, textTongTien, textTienPhaiTra, textTienDaTra, textPhieuNhapTitle;

//btn
var btnThemHang, btnThanhToan, btnNhapHang, btnXoa, btnLuu;

//other
var idPhieuNhap = null, phieuNhap = null, tableData;


//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    selectNguoiNhap = $("#bimo1");
    selectNhaCungCap = $("#bimo2");
    inputGiamGia = $("#bimo3");
    inputGhiChu = $("#bimo4");
    selectHangHoa = $("#bimo5");
    btnThemHang = $("#btn-them-hang");
    tableData = $("tbody");
    btnThanhToan = $("#btn-thanh-toan");
    btnNhapHang = $("#btn-nhap-hang");
    btnXoa = $("#btn-xoa-nhap-hang");
    textTongTien = $("#so-tong-tien");
    textTienPhaiTra = $("#tien-phai-tra");
    textTienDaTra = $("#tien-da-tra");
    textPhieuNhapTitle = $("#phieu-nhap-title");
    btnLuu = $("#btn-luu");

    let href = new URL(window.location.href);
    idPhieuNhap = href.searchParams.get("id");
    idPhieuNhap = idPhieuNhap === null ? 0 : idPhieuNhap;

    let arrAjax = [viewSelectNhanVien(selectNguoiNhap, false), viewSelectNhaCungCap(selectNhaCungCap, false), viewSelectHangHoa(selectHangHoa, false)];

    Promise.all(arrAjax).then(rs => {
        if (idPhieuNhap != 0) {
            viewPhieuNhap();
        }
    }).catch(err => {
        console.log(err);
    })

    //==========Function constructor========//
    clickButtonXoaNhapHang();
    clickButtonThanhToan();
})

//=============== Function detail ===================//

//=================== Function ===================//
function viewPhieuNhap() {
    phieuNhapHangFindById(idPhieuNhap).then(rs => {
        if (rs.message == "found") {
            rs = rs.data;
            phieuNhap = rs;
            textPhieuNhapTitle.html(`${rs.maPhieu} - ${viewDateTime(rs.thoiGian)}`);
            rs.nguoiDung != null ? selectNguoiNhap.val(viewField(rs.nguoiDung.id)).trigger("change") : 0;
            rs.nhaCungCap != null ? selectNhaCungCap.val(viewField(rs.nhaCungCap.id)).trigger("change") : 0;
            inputGiamGia.val(viewField(rs.giamGia));
            inputGhiChu.val(viewField(rs.ghiChu));
            textTongTien.html(formatNumber(viewField(rs.tongTien), ",", ".") + " VNĐ");
            textTienPhaiTra.html(formatNumber(viewField(rs.tienDaTra), ",", ".") + " VNĐ");
            textTienDaTra.html(formatNumber(viewField(rs.tienPhaiTra), ",", ".") + " VNĐ");
            if (rs.trangThai > 0) {
                $(selectNguoiNhap).prop('disabled', true);
                $(selectNhaCungCap).prop('disabled', true);
                $(inputGiamGia).prop('disabled', true);
                $(btnNhapHang).prop('disabled', true);
                $(btnThemHang).prop('disabled', true);
                $(btnXoa).prop('disabled', true);
                if (rs.trangThai == 2) $(btnThanhToan).prop('disabled', true);
            }
            phieuNhapChiTietFindByPhieuNhap(idPhieuNhap).then(rs => {
                if (rs.message == "found") {
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
                                setViewPhieuChiTiet(rs.data.currentElements, 1);
                                return;
                            }
                            // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                            phieuNhapChiTietFindByPhieuNhap(idPhieuNhap, pagination.pageNumber).then(rs => {
                                setViewPhieuChiTiet(rs.data.currentElements, pagination.pageNumber);
                            }).catch(err => console.log(err))
                        }
                    })
                }
            }).catch(err => {
                console.log(err);
            })
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Phiếu nhập không tồn tại");
        // setTimeout(function () {
        //     window.location.href = "danh-sach-nhap-hang";
        // }, 4000);
    })
}

//=================== Function ===================//
function disableQuanlity() {
    if (phieuNhap.trangThai == 2) {
        console.log("quantity");
        $(".quantity").prop("disabled", true);
    }
}

//=================== Function ===================//
function setViewPhieuChiTiet(list, pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Hàng hóa</th>
                <th>Số lượng</th>
                <th>Tổng tiền</th>
            </tr>`;
    let len = list.length;
    if (len > 0) {
        view += list.map((data, index) => `<tr>
                <td>${(pageNumber - 1) * 5 + index + 1}</td>
                <td>
                    <div class="hang-hoa-phieu-nhap">
                        <img src="${viewField(data.chiNhanhHangHoa.hangHoa.urlHinhAnh1)}" class="img-hang-hoa-phieu-nhap">
                        <div class="ten-hang-hoa">${viewField(data.chiNhanhHangHoa.hangHoa.ma)} - ${viewField(data.chiNhanhHangHoa.hangHoa.tenHangHoa)}</div>
                    </div>
                </td>
                <td style="text-align: center; vertical-align: middle">
                    <input type="number" class="quantity" min="1" value="${viewField(data.soLuong)}" style="width: 40px">
                </td>
                <td style="text-align: center; vertical-align: middle">
                    ${formatNumber(viewField(data.tongTien), ",", ".")}
                </td>
            </tr>`).join("");
    } else {
        view += `<tr><td colspan="4">Không có thông tin phù hợp</td></tr>`
    }
    tableData.html(view);
    disableQuanlity();
}

//=================== Function ===================//

//=================== Function ===================//
function clickButtonXoaNhapHang() {
    btnXoa.click(function () {
        $("#modal-remove").modal("show");
        phieuNhapHangDelete(idPhieuNhap).then(rs => {
            if (rs.message == "deleted") {
                alterSuccess(`Xóa phiếu nhập hàng ${phieuNhap.maPhieu} thành công`);
                setTimeout(function () {
                    window.location.href = "danh-sach-nhap-hang";
                }, 4000);
            }
        }).catch(err => {
            console.log(err);
            alterDanger("Server error xoa nhap hang");
        })
    })
}

//=================== Function ===================//
function clickButtonThanhToan() {
    btnThanhToan.click(function () {
        $("#modal-thanh-toan").modal("show");
    })
}