//================ Declare variable===================//
var inputSearch, btnSearch, table,selectDonViGiaoHang, btnXacNhan ;
var arr = [];
var searchParams = new URLSearchParams(window.location.search);
var param = searchParams.get('id');

//=============== Function main ===================//
$(function () {
    table = $("#table-hang-hoa");
    btnXacNhan = $("#confirm-btn")
    callSearch(param);
    taiExcelTK();
    inHoaDon();
    clickDVGH();
})

//=============== Function detail ===================//

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
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Hoa Don");
        setView(1);
    });
}

//=================== Function ===================//
function setPhieu(item) {
    $("#tenChiNhanh").append(`${item[0].hoaDon.chiNhanh.diaChi}`);
    $("#khachHang").append(`${item[0].hoaDon.khachHang.tenKhachHang}`);
    let thoiGian = viewDateTime(item[0].hoaDon.thoiGian);
    $("#ngayBan").append(`${thoiGian}`);
    $("#diaChi").append(`${item[0].hoaDon.khachHang.diaChi}`);
    $("#tenNhanVien").append(`${item[0].hoaDon.nguoiDung.hoVaTen}`);
    $("#dienThoaiKhach").append(`${item[0].hoaDon.nguoiDung.soDienThoai}`);
    $("#maHoaDon").append(`${item[0].hoaDon.ma}`);
    $("#tong-tien").append(formatMoney(`${item[0].hoaDon.tongTien}`));
    $("#tien-khach-tra").text(formatMoney(`${item[0].hoaDon.tienKhachTra}`));
    $("#tien-tra-khach").text(formatMoney(`${item[0].hoaDon.tienTraLaiKhach}`));
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
                    <span >${viewField(item.soLuong)}</span></td>
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
function taiExcelTK() {
    $('#btn-excel').on('click', function () {
        ajaxGet('v1/admin/hoa-don-chi-tiet/excel?list-hoa-don-chi-tiet=' + arr.map(hoaDonChiTiet => hoaDonChiTiet.id))
            .then(rs => {
                window.open(rs.data, '_blank');
            }).catch(ex => {
            console.log(ex);
            alterDanger("Tạo file excel thất bại");
        })
    });
    clickPrintElement(".ttcttk");
}


//=================== Function ===================//

function inHoaDon() {
    $("#btn-print-pdf").click(function () {
        $("#pageBody").LoadingOverlay("show");
        createHoaDonPDF(arr[0].hoaDon.ma, nguoiDungId).then(rs => {
            console.log(rs.message);
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
async function viewSelectDonViGiaoHang(selector) {
    let view = ``;
    findAllDonViGiaoHang(1, MAX).then(rs => {
        if(rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.name}</option>`).join("") ;
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}
async function clickDVGH(){
    $("#btn-DVGH").click(function (){
        window.location=`/don-vi-giao-hang?id-hoa-don=${param}`;
    })
}


