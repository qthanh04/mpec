//================ Declare variable===================//
//input
var inputGiamGia, inputGhiChu;

//text
var textTongTien, textTienPhaiTra, textTienDaTra, textPhieuNhapTitle;

//btn
var btnThemHang, btnLuu, btnNhapHang, btnXoa, btnThanhToan;

//other
var selectNguoiNhap, selectNhaCungCap, selectHangHoa, tableData, table;
var idPhieuNhap = null;
var phieuNhap = null;
var arr = [];  // list các id hàng hóa đc chọn ( = 0 là bị xóa)
var count = 0; // số lượng hàng hóa trong bảng
var xoaCount = 0; // số lượng hàng hóa đã xóa (mục đích để hiện thị số thứ tự)

const PHIEU_NHAP_HANG_HD = 7 //id hoạt động của phiếu nhập hàng

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
    table = $("#table-hang-hoa");

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
    searchHangHoa2();
    chooseHH2();
    focusGiamGia();
    focusGiamGiaTong();
    searchNhaCungCap();
    addKhachHang();
    clickButtonThanhToan();
    chooseHH();
    chooseNhaCungCap();
    saveNhaCungCap();
    clickThayDoiSoLuong();
    enterThayDoiSoLuong();
    enterGiaNhap();
    traTien();
    deleteHH();
    deletePhieu();

})
//=============== Function detail ===================//

//=============== Function Chon hang hoa ===================//
function searchHangHoa2() {
    callGetSelectize($('#select-state'), 'id', 'tenHangHoa', "v1/admin/hang-hoa/search-text");
    $('#select-state')[0].selectize.focus();
}

//=============== Function Add giam gia cho 1 san pham===================//
function focusGiamGia() {
    $(document).on('click', '.giamGia', function (e) {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        console.log(hang);
        $('.giamGia').popover({
            // trigger: 'focus',
            html: 'true',
            content: `<input type="text" min="0" class=giamGiaSp" id="giamGiaSp" placeholder="Nhập khuyến mại">`,
            template: '<div class="popover sp"><div class="arrow"></div>' +
                '<h3 class="popover-title"></h3><div class="popover-content">' +
                '</div>' +
                ' <label for="donVi" style="margin-left:15px">Đơn vị:</label>' +
                '  <select name="donVi" id="donViGiamGia" style="height:25px ;margin-bottom:15px">' +
                '    <option value="1">%</option>' +
                '    <option value="2" selected>VND</option>' +
                '  </select>' +
                `<button type="button" class="btn btn-primary popover-submit">` +
                '<i class="fas fa-check icon-white"></i></button>' +
                '<button type="button" class="btn btn-danger popover-cancel">' +
                '<i class="far fa-times-circle"></i></button></div>'
        });

        $(`#giamGiaSp`).focus();

        //Escape button
        $(document).on("keyup", `#giamGiaSp`, function (e) {
            formatCurrency($(this));//chuyen tu so sang string format
            if (e.key === "Escape" || e.key === "Esc") {
                $('.giamGia').popover('hide');
            }
        });
        updateGiamGiaSp();


    });
}

function cancelPopup(){
    //cancel popover
    $(document).on('click', '.popover-cancel', function (e) {
        $('.giamGia').popover('hide');
    });
}

function enterGiamGia(){
    //enter button
    $(document).on("keypress", `#giamGiaSp`, function (e) {
        if (e.which === 13) {
            let currentRow = $(this).parent().parent().parent().closest("tr");
            let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount;
            console.log("Hang : "+ hang);
            let giamGiaSp = parseFloat(valTien($(`#giamGiaSp${hang}`).val()));
            console.log("giam gia san pham: "+giamGiaSp);

            let donVi = $("#donViGiamGia").val();
            let sl = $(`.soLuong${hang}`)[0].value;
            let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl;
            console.log("gia nhap:"+giaNhap);
            let giamGiaVal = 0, tongTien = 0;

            //1-%, 2-VND
            if (donVi == 1 && giamGiaSp != 0) {//xu ly giam theo %
                if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                    giamGiaSp = 0;
                }
                giamGiaVal = (giaNhap * giamGiaSp) / 100;
                $(`.giamGia${hang}`).val(formatMoney(giamGiaVal));
                tongTien = giaNhap - giamGiaVal;

            } else if (donVi == 2) { //xu ly giam theo tien;
                if (giamGiaSp == "") {
                    giamGiaSp = 0;
                }
                $(`.giamGia${hang}`).val(formatMoney(giamGiaSp));
                tongTien = giaNhap - giamGiaSp;
            }
            $(`.tong${hang}`).text(formatMoney(`${tongTien}`));
            $('.giamGia').popover('hide');

            let tong = 0;
            for (i = 1; i <= arr.length; i++) {
                if (arr[i - 1] != 0) {
                    let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                    if (i != hang) {
                        tong += thanhTien;
                    } else {
                        tong += tongTien;
                    }
                }
            }

            $("#tong-tien").val(formatMoney(tong));
            let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
            let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
            let tienPhaiTra = tongTien_ - giamGiaTong;
            console.log("tong tien:" + tongTien_);
            console.log("giam gia tong:" + giamGiaTong);
            console.log("tien phai tra:" + tienPhaiTra);
            $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
            return;
        }
    });
}

function   updateGiamGiaSp(){
    $(document).on('click', `.popover-submit`, function (e) {

        let currentRow = $(this).parent().parent().parent().closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount;
        console.log("Hang : "+ hang);
        let giamGiaSp = parseFloat(valTien($(`#giamGiaSp`).val()));

        let donVi = $("#donViGiamGia").val();
        let sl = $(`.soLuong${hang}`)[0].value;
        let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl;
        let giamGiaVal = 0, tongTien = 0;

        //1-%, 2-VND
        if (donVi == 1 && giamGiaSp != 0) {//xu ly giam theo %
            if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                giamGiaSp = 0;
            }
            giamGiaVal = (giaNhap * giamGiaSp) / 100;
            $(`.giamGia${hang}`).val(formatMoney(giamGiaVal));
            tongTien = giaNhap - giamGiaVal;

        } else if (donVi == 2) { //xu ly giam theo tien
            if (giamGiaSp == "") {
                giamGiaSp = 0;
            }
            $(`.giamGia${hang}`).val(formatMoney(giamGiaSp));
            tongTien = giaNhap - giamGiaSp;
        }
        $(`.tong${hang}`).text(formatMoney(`${tongTien}`));
        $('.giamGia').popover('hide');

        let tong = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tong += thanhTien;
                } else {
                    tong += tongTien;
                }
            }
        }

        $("#tong-tien").val(formatMoney(tong));
        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        return;
    });
}

//=================== Function ===================//
function enterGiaNhap() {
    $(document).on('keyup', '.giaNhap', function () {
        console.log("vao keyup");
        formatCurrency($(this));//chuyen tu so sang string format
    })
    $(document).on('keypress', '.giaNhap', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
            let sl = $(`.soLuong${hang}`)[0].value;
            let giamGia = parseFloat(valTien($(`.giamGia${hang}`)[0].value));
            let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl - giamGia;
            $(`.tong${hang}`).text(formatMoney(`${giaNhap}`));
            let tongTien = 0;
            for (i = 1; i <= arr.length; i++) {
                if (arr[i - 1] != 0) {
                    let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                    if (i != hang) {
                        tongTien = tongTien + thanhTien;
                    } else {
                        tongTien = tongTien + giaNhap;
                    }
                }
            }
            $("#tong-tien").val(formatMoney(tongTien));
            console.log("tong tien 1:"+ tongTien);
            if (valTien($("#tien-khach-tra")[0].value) != 0) {
                let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value) - tongTien;
                $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
            }

            let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
            let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
            let tienPhaiTra = tongTien_ - giamGiaTong;
            $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        }
    });
    $(document).on('blur', '.giaNhap', function (e) {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let stt = hang - 1;
        let sl = $(`.soLuong${hang}`)[0].value;
        let giamGia = parseFloat(valTien($(`.giamGia${hang}`)[0].value));
        let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl - giamGia;
        $(`.tong${hang}`).text(formatMoney(`${giaNhap}`));
        let tongTien = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tongTien = tongTien + thanhTien;
                } else {
                    tongTien = tongTien + giaNhap;
                }
            }
        }
        $("#tong-tien").val(formatMoney(tongTien));
        console.log("tong tien 2:"+tongTien);
        if (valTien($("#tien-khach-tra")[0].value) != 0) {
            let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value)- tongTien ;
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }

        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));

    })
}


//=============== Function focus hide giam gia cho ca hoa don===================//
var donViTong = 0;

function focusGiamGiaTong() {

    $(document).on('click', '.giamGiaTong', function (e) {
        $('.giamGiaTong').popover({
            // trigger: 'focus',
            html: 'true',
            content: '<input id="giamGiaTongPop" placeholder="Nhập khuyến mại">',
            template: '<div class="popover tong"><div class="arrow"></div>' +
                '<h3 class="popover-title"></h3><div class="popover-content">' +
                '</div>' +
                ' <label for="donVi" style="margin-left:10px">Đơn vị:</label>' +
                '  <select name="donVi" id="donViGiamGiaTong" style="height:25px ;margin-bottom:15px">' +
                '    <option value="1">%</option>' +
                '    <option value="2" selected>VND</option>' +
                '  </select>' +
                '<button type="button" class="btn btn-primary popover-submit-2">' +
                '<i class="fas fa-check icon-white"></i></button>' +
                '<button type="button" class="btn btn-danger popover-cancel-2">' +
                '<i class="far fa-times-circle"></i></button></div>',
            placement: "bottom"
        });
        //cancel popover
        $('#giamGiaTongPop').focus();
        $(document).on('click', '.popover-cancel-2', function (e) {
            $('.giamGiaTong').popover('hide');
        });

        $(document).on('blur', '.sp', function (e) {
            $('.giamGiaTong').popover('hide');
        });
        // $(document).on('blur', '.tong', function (e) {
        //     $('.giamGiaTong').popover('hide');
        // });
        //Escape button
        $(document).on("keyup", `#giamGiaTongPop`, function (e) {
            formatCurrency($(this));//chuyen tu so sang string format
            if (e.key === "Escape" || e.key === "Esc") {
                $('.giamGiaTong').popover('hide');
            }
        });


        //update on submit
        $(document).on('click', '.popover-submit-2', function (e) {
            $('.giamGiaTong').popover('hide');
            //xu ly giam gia tong
            let tongTien = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
            let giamGiaTong = parseFloat(valTien($("#giamGiaTongPop")[0].value));//value giam gia tong mac dinh bang 0 neu k nhap gi
            if (giamGiaTong == "" || isNaN(giamGiaTong)) {
                giamGiaTong = 0;
                console.log("aaabb");
            }
            let giamGiaTongVal = 0, tienPhaiTra = 0;
            donViTong = $("#donViGiamGiaTong").val();

            //1-%, 2-VND
            if (donViTong == 1) {    //xu ly giam theo %
                giamGiaTongVal = (tongTien * giamGiaTong) / 100;
                tienPhaiTra = tongTien - giamGiaTongVal;
                $('.giamGiaTong').val(formatMoney(giamGiaTongVal));
            } else if (donViTong == 2) {   //xu ly giam theo tien
                tienPhaiTra = tongTien - giamGiaTong;
                $('.giamGiaTong').val(formatMoney(giamGiaTong));
            }

            $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        });

        $(document).on("keypress", `#giamGiaTongPop`, function (e) {
            if (e.which === 13) {
                $('.giamGiaTong').popover('hide');
                //xu ly giam gia tong
                let tongTien = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
                let giamGiaTong = parseFloat(valTien($("#giamGiaTongPop")[0].value));//value giam gia tong mac dinh bang 0 neu k nhap gi
                if (giamGiaTong == "" || isNaN(giamGiaTong)) {
                    giamGiaTong = 0;
                    console.log("aaabb");
                }
                let giamGiaTongVal = 0, tienPhaiTra = 0;
                donViTong = $("#donViGiamGiaTong").val();

                //1-%, 2-VND
                if (donViTong == 1) {    //xu ly giam theo %
                    giamGiaTongVal = (tongTien * giamGiaTong) / 100;
                    tienPhaiTra = tongTien - giamGiaTongVal;
                    $('.giamGiaTong').val(formatMoney(giamGiaTongVal));
                } else if (donViTong == 2) {   //xu ly giam theo tien
                    tienPhaiTra = tongTien - giamGiaTong;
                    $('.giamGiaTong').val(formatMoney(giamGiaTong));
                }

                $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
            }
        })


    })
}


//=================== Function tra tien, nhap tien khach da tra ===================//
function traTien() {
    $(document).on('keyup', '#tien-khach-tra', function () {
        console.log("vao keyup");
        formatCurrency($(this));//chuyen tu so sang string format
    })
    $(document).on("keypress", "#tien-khach-tra", function (e) {
        if (e.which === 13) {
            let tienKhachTra = valTien($("#tien-khach-tra")[0].value);
            let tienPhaiTra = parseFloat(valTien($("#tien-phai-tra")[0].value));
            let tienTraLaiKhach =  tienKhachTra-tienPhaiTra;
            if (tienTraLaiKhach < 0) {
                alterWarning("Số tiền bạn trả ít hơn tổng số tiền hóa đơn");
                $("#tien-tra-lai").val(0);
            }else {
                $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
            }
        }
    });
    $(document).on("blur", "#tien-khach-tra", function (e) {
        let tienKhachTra = parseFloat(valTien($("#tien-khach-tra")[0].value));
        let tienPhaiTra = parseFloat(valTien($("#tien-phai-tra")[0].value));
        let tienTraLaiKhach = tienKhachTra-tienPhaiTra;
        if (tienTraLaiKhach < 0) {
            alterWarning("Số tiền bạn trả ít hơn tổng số tiền hóa đơn");
            $("#tien-tra-lai").val(0);
        }else {
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }
    })
}

//=================== Function ===================//
function clickThayDoiSoLuong() {
    $(document).on('click', '.soLuong', function () {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let sl = $(`.soLuong${hang}`)[0].value;
        let giamGia = valTien($(`.giamGia${hang}`)[0].value);
        let giaNhap = valTien($(`.giaNhap${hang}`)[0].value) * sl - giamGia;
        $(`.tong${hang}`).text(formatMoney(`${giaNhap}`));
        let tongTien = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tongTien = tongTien + thanhTien;
                } else {
                    tongTien = tongTien + giaNhap;
                }
            }
        }
        $("#tong-tien").val(formatMoney(tongTien));
        if (valTien($("#tien-khach-tra")[0].value) != 0) {
            let tienTraLaiKhach = val($("#tien-khach-tra")[0].value) - tongTien;
            $("#tien-tra-lai").val(tienTraLaiKhach);
        }

        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
    });
}

//=================== Function ===================//
function enterThayDoiSoLuong() {
    $(document).on('keypress', '.soLuong', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
            let sl = $(`.soLuong${hang}`)[0].value;
            let giamGia = parseFloat(valTien($(`.giamGia${hang}`)[0].value));
            let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl - giamGia;
            $(`.tong${hang}`).text(formatMoney(`${giaNhap}`));
            let tongTien = 0;
            for (i = 1; i <= arr.length; i++) {
                if (arr[i - 1] != 0) {
                    let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                    if (i != hang) {
                        tongTien = tongTien + thanhTien;
                    } else {
                        tongTien = tongTien + giaNhap;
                    }
                }
            }
            $("#tong-tien").val(formatMoney(tongTien));
            if (valTien($("#tien-khach-tra")[0].value) != 0) {
                let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value) - tongTien;
                $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
            }
        }

        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
    });

    $(document).on('blur', '.soLuong', function (e) {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let sl = $(`.soLuong${hang}`)[0].value;
        let giamGia = parseFloat(valTien($(`.giamGia${hang}`)[0].value));
        let giaNhap = parseFloat(valTien($(`.giaNhap${hang}`)[0].value)) * sl - giamGia;
        $(`.tong${hang}`).text(formatMoney(`${giaNhap}`));
        let tongTien = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tongTien = tongTien + thanhTien;
                } else {
                    tongTien = tongTien + giaNhap;
                }
            }
        }
        $("#tong-tien").val(formatMoney(tongTien));
        if (valTien($("#tien-khach-tra")[0].value) != 0) {
            let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value) - tongTien;
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }

        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));

    })
}


//=================== Function ===================//
function deleteHH() {
    $(document).on('click', '.xoa-btn', function () {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let stt = currentRow[0].id.substring(4, 5);
        $(currentRow[0]).hide();
        arr[stt - 1] = 0;
        let tongTienCu = parseFloat(valTien($("#tong-tien")[0].value));
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));

        let tongTienMoi = tongTienCu - parseFloat(valTien($(`.tong${stt}`)[0].textContent));
        let tienPhaiTra = tongTienMoi - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        $("#tong-tien").val(formatMoney(tongTienMoi));
        let temp = 1;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                $(`#stt-${i}`).text(temp);
                temp++;
            }
        }
        count--;
        xoaCount++;
    })
}


function searchNhaCungCap() {
    callGetSelectize($('#select-khach-hang'), "id", "ten", "v1/admin/nha-cung-cap/search-text");
}


//=================== Function ===================//
function chooseNhaCungCap() {
    $('#select-khach-hang').change(function () {
        let val = $("#select-khach-hang option:selected").text();
        let id = $("#select-khach-hang option:selected").val();
        console.log(val);
        console.log(id);
        if (id > 0) {
            findNhaCungCapById(id).then(rs => {
                if (rs.message === "found") {
                    $("#bimo5").val(rs.data.dienThoai);
                } else {
                    alterDanger("Xảy ra lỗi hệ thống");
                }
            })
        } else {
            $("#modal-khach-hang").modal();
        }
        $("#btn-thanh-toan").prop('disabled', false);
        $("#btn-print-pdf").prop('disabled', true);
        $("#search-hang-hoa").removeAttr("disabled");
    });
}

function addKhachHang() {
    $(".add-nha-cung-cap").click(function () {
        $("#modal-khach-hang").modal();
    })
}

//==============Function chon duoc 1 hang hoa===============//
var dem = 0;

function chooseHH2() {
    $('#select-state').change(function () {
        if (dem === 0) {
            table.find('tbody').remove();
        }
        dem++;
        let val = $("#select-state option:selected").text();
        let id = $("#select-state option:selected").val();
        console.log(val);
        console.log(id);
        if (id > 0) {
            findDonViCoBan(id).then(rs => {
                console.log(rs);
                arr.push(id);
                let len = arr.length;
                count++;
                let row = `<tr class="row-${len}" id="row-${len}">
                <td id="stt-${len}" style="padding-top: 20px;">${viewField(count)}</td>
                <td style="padding-top: 20px;">                                
                    <div class="ten-hang-hoa">${viewField(rs.data.hangHoa.tenHangHoa)}</div>              
                </td>
                <td style="padding-top: 20px">
                    ${viewField(rs.data.donVi.tenDonVi)}
                </td>
                 <td style="text-align: center; padding-top: 20px">
                    <input class="soLuong soLuong${len}" type="number" id="quantity" min="1" value="1" style="width: 60px">
                </td>
                <td  style="padding-top: 20px;">
                    <input class="giaNhap giaNhap${len} none-arrow" type="text" id="quantity" min="0" value="0" style="width: 40px">
                </td>
                
                <td style="text-align: center;padding-top: 20px">
                     <input class="giamGia giamGia${len}" type="text" id="giamGia" min="0"  value="${formatMoney(rs.data.hangHoa.phanTramGiamGia)}" style="width: 50px" data-toggle="popover" data-placement="bottom">                 
                     <span>VNĐ</span>
                 </td>
                    
                <td class="tong tong${len}" style="text-align: center;padding-top: 20px" > 0
                </td>
                <td>
                    <button class="btn btn-light xoa-btn" id="xoa-btn-${len}">
                        <i class="fas fa-trash-alt" style="font-size: 15px;"></i>
                    </button>
                </td>
            </tr>`;
                table.append(row);
                $('#select-state')[0].selectize.clear();
                $(`.soLuong${len}`).focus();
            });
        } else {
            // // open modal to save new product
            // window.open("/hang-hoa?id=0", '_blank');
        }
    })
}

//=================== Function ===================//
function viewPhieuNhap() {
    phieuNhapHangFindById(idPhieuNhap).then(rs => {
        if (rs.message == "found") {
            rs = rs.data;
            phieuNhap = rs;
            textPhieuNhapTitle.html(`${rs.maPhieu} - ${viewDateTime(rs.thoiGian)}`);
            rs.nguoiDung != null ? selectNguoiNhap.val(viewField(rs.nguoiDung.id)).trigger("change") : 0;
            rs.nhaCungCap != null ? selectNhaCungCap.val(viewField(rs.nhaCungCap.id)).trigger("change") : 0;
            inputGiamGia.val(formatMoney(rs.giamGia));
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
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function clickButtonThanhToan() {
    btnThanhToan.click(function () {
        let nhaCungCapId = $("#select-khach-hang option:selected").val();
        let tienKhachTra = valTien($("#tien-khach-tra")[0].value);
        let tongTien = parseFloat(valTien($("#tong-tien")[0].value));

        let {check: checkDT, val: valDT} = checkSoDT($("#bimo5"));
        //kiem tra dieu kien
        if (nhaCungCapId <= 0) {
            alterWarning("Bạn chưa chọn nhà cung cấp");
        } else if (!checkDT) {
            alterWarning("Nhập sai định dạng số điện thoại");
        } else if (tongTien == 0) {
            alterWarning("Bạn không thể tạo hóa đơn với tổng tiền bằng 0");
        } else if (tienKhachTra == "") {
            alterWarning("Bạn chưa điền số tiền phải trả");
        } else {

            let tienTraLai = parseFloat(valTien($("#tien-tra-lai")[0].value));
            if (tienTraLai < 0) {
                $("#modal-cho-no .modal-body").text(`Số tiền bạn trả ít hơn tổng tiền hóa đơn. Bạn có chắc chắn nợ nhà cung cấp số tiền ${-tienTraLai} không?`);
                $("#modal-cho-no").modal('toggle');
                $("#confirm-btn").click(function () {
                    let tienDaTra = valTien($("#tien-khach-tra")[0].value);
                    let maPN = Math.floor(Math.random() * 10000);
                    let ghiChu = $("#ghi-chu")[0].value;
                    let giamGia = valTien($("#giamGiaTong").val());
                    let tienPhaiTra = valTien($("#tien-phai-tra").val());
                    $("#pageBody").LoadingOverlay("show");
                    findNguoiDungPhongBanByNhanVienId(nguoiDungId).then(rs => {
                        let chiNhanhId = rs.data.chiNhanh.id;
                        let phieuNhap = {
                            maPhieu: "PN-000" + maPN,
                            tongTien: tongTien,//tong tien
                            tienDaTra: tienDaTra,//tien da tra
                            tienPhaiTra: tienPhaiTra,//tong tien - giam gia
                            tienTraLai:0,
                            ghiChu: ghiChu,
                            giamGia:giamGia,
                            trangThai: 2
                        }
                        console.log(phieuNhap);
                        uploadPhieuNhap(phieuNhap, nguoiDungId, nhaCungCapId).then(rs2 => {
                            if (rs2.message === "uploaded") {
                                let id = rs2.data.id;
                                let count = 0;
                                let phieuNhapChiTietList = [];
                                for (i = 1; i <= arr.length; i++) {
                                    if (arr[i - 1] != 0) {
                                        let sl = parseFloat($(`.soLuong${i}`)[0].value);
                                        let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                                        let giaNhap = parseFloat(valTien($(`.giaNhap${i}`)[0].value));
                                        phieuNhapChiTietList[i - 1 - count] = {
                                            tongTien: thanhTien,
                                            soLuong: sl,
                                            giaNhap: giaNhap,
                                            xoa: false
                                        }
                                    } else {
                                        count++;
                                    }
                                }

                                let phieuHoatDong = {
                                    giaTri: tienPhaiTra
                                }
                                //upload phieu hoat dong
                                uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, id, 0 , 0, 0, PHIEU_NHAP_HANG_HD).then(rs => {
                                    console.log(rs);
                                });

                                let phieuChiRequest = {
                                    tienDaTra: tienDaTra,
                                    ghiChu: ghiChu
                                }
                                uploadPhieuChi(phieuChiRequest, chiNhanhId, nguoiDungId, 1).then(rs3 => {
                                    if (rs3.message === "uploaded") {
                                        console.log("Upload Phieu Chi Sucess");
                                        console.log(rs3);
                                        let idPhieuChi = rs3.data.id;
                                        uploadPhieuChiNhapHangTraKhach(id,idPhieuChi, tienDaTra).then(rs4 => {
                                            console.log("uploadPhieuChiNhapHangTraKhach sucess");
                                        })
                                    }
                                })

                                let hangHoaIdList = [];
                                for (i = 0; i < arr.length; i++) {
                                    if (arr[i] != 0) {
                                        hangHoaIdList.push(parseInt(arr[i]));
                                    }
                                }
                                let phieuNhapForm = {
                                    phieuNhapHangId: id,
                                    chiNhanhId: chiNhanhId,
                                    hangHoaIdList: hangHoaIdList,
                                    phieuNhapHangChiTietList: phieuNhapChiTietList
                                }
                                uploadPhieuNhapChiTiet(phieuNhapForm).then(rs => {
                                    if (rs.message === "success") {
                                        alterSuccess("Nhập hàng thành công");
                                        $("#btn-thanh-toan").prop('disabled', true);
                                        $("#btn-print-pdf").prop('disabled', false);
                                        $("#search-hang-hoa").attr("disabled", "disabled");
                                        $("#pageBody").LoadingOverlay("hide");
                                        inPhieuNhap(rs.data.maPhieu);
                                    } else {
                                        alterDanger("Nhập hàng không thành công");
                                        $("#pageBody").LoadingOverlay("hide");
                                    }
                                });
                            } else {
                                alterDanger("Nhập hàng không thành công");
                                $("#pageBody").LoadingOverlay("hide");
                            }
                        });
                    })
                })
            } else {
                let tienDaTra = parseFloat(valTien($("#tien-khach-tra")[0].value));
                // let tienPhaiTra = $("#tien-phai-tra").val();
                let maPN = Math.floor(Math.random() * 10000);
                let ghiChu = $("#ghi-chu")[0].value;
                let tienPhaiTra = parseFloat(valTien($("#tien-phai-tra").val()));
                $("#pageBody").LoadingOverlay("show");
                findNguoiDungPhongBanByNhanVienId(nguoiDungId).then(rs => {
                    let chiNhanhId = rs.data.chiNhanh.id;
                    let phieuNhap = {
                        maPhieu: "PN-000" + maPN,
                        tongTien: tongTien,
                        tienDaTra: tienDaTra,
                        tienPhaiTra: tienPhaiTra,
                        ghiChu: ghiChu,
                        trangThai: 2
                    }
                    uploadPhieuNhap(phieuNhap, nguoiDungId, nhaCungCapId).then(rs2 => {
                        if (rs2.message === "uploaded") {
                            let id = rs2.data.id;
                            let count = 0;
                            let phieuNhapChiTietList = [];
                            for (i = 1; i <= arr.length; i++) {
                                if (arr[i - 1] != 0) {
                                    let sl = parseFloat($(`.soLuong${i}`)[0].value);
                                    let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                                    let giaNhap = parseFloat(valTien($(`.giaNhap${i}`)[0].value));
                                    phieuNhapChiTietList[i - 1 - count] = {
                                        tongTien: thanhTien,
                                        soLuong: sl,
                                        giaNhap: giaNhap,
                                        xoa: false
                                    }
                                } else {
                                    count++;
                                }
                            }

                            let phieuHoatDong = {
                                giaTri: tienPhaiTra
                            }
                            //upload phieu hoat dong
                            uploadPhieuHoatDong(phieuHoatDong, user.nguoiDungid, 0, 0, id, 0 , 0, 0, PHIEU_NHAP_HANG_HD).then(rs => {
                                console.log(rs);
                            });

                            let phieuChiRequest = {
                                tienDaTra: tienDaTra,
                                ghiChu: ghiChu
                            }
                            uploadPhieuChi(phieuChiRequest, chiNhanhId, nguoiDungId, 1).then(rs3 => {
                                if (rs3.message === "uploaded") {
                                    console.log("Upload Phieu Chi Sucess");
                                    console.log(rs3);
                                    let idPhieuChi = rs3.data.id;
                                    uploadPhieuChiNhapHangTraKhach(id,idPhieuChi, tienDaTra).then(rs4 => {
                                        console.log("uploadPhieuChiNhapHangTraKhach sucess");
                                    })
                                }
                            })

                            let hangHoaIdList = [];
                            for (i = 0; i < arr.length; i++) {
                                if (arr[i] != 0) {
                                    hangHoaIdList.push(parseInt(arr[i]));
                                }
                            }
                            let phieuNhapForm = {
                                phieuNhapHangId: id,
                                chiNhanhId: chiNhanhId,
                                hangHoaIdList: hangHoaIdList,
                                phieuNhapHangChiTietList: phieuNhapChiTietList
                            }
                            uploadPhieuNhapChiTiet(phieuNhapForm).then(rs => {
                                if (rs.message === "success") {
                                    alterSuccess("Nhập hàng thành công");
                                    $("#btn-thanh-toan").prop('disabled', true);
                                    $("#btn-print-pdf").prop('disabled', false);
                                    $("#search-hang-hoa").attr("disabled", "disabled");
                                    $("#pageBody").LoadingOverlay("hide");
                                    inPhieuNhap(rs.data.maPhieu);
                                } else {
                                    alterDanger("Nhập hàng không thành công");
                                    $("#pageBody").LoadingOverlay("hide");
                                }
                            });
                        } else {
                            alterDanger("Nhập hàng không thành công");
                            $("#pageBody").LoadingOverlay("hide");
                        }
                    });
                })
            }
        }
    })
}


//=================== Function ===================//
function chooseHH() {
    $("#search-hang-hoa").change(function () {
        var id = $(this).find(':selected')[0].value;
        if (id > 0) {
            findDonViCoBan(id).then(rs => {
                arr.push(id);
                let len = arr.length;
                count++;
                let row = `<tr class="row-${len}" id="row-${len}">
                <td id="stt-${len}" style="padding-top: 40px;">${viewField(count)}</td>
                <td>
                    <div class="hang-hoa-phieu-nhap">
                        <img src="${imgUrl}" class="img-hang-hoa-phieu-nhap">
                        <div class="ten-hang-hoa">${viewField(rs.data.hangHoa.tenHangHoa)}</div>
                    </div>
                </td>
                <td style="padding-top: 45px">
                    ${viewField(rs.data.donVi.tenDonVi)}
                </td>
                 <td style="text-align: center; vertical-align: middle">
                    <input class="soLuong soLuong${len}" type="number" id="quantity" min="1" value="1" style="width: 60px">
                </td>
                <td  style="text-align: center; vertical-align: middle">
                    <input class="giaNhap giaNhap${len} none-arrow" type="text" id="quantity" min="0" value="0" style="width: 60px">
                </td>
                <td class="tong tong${len}" style="text-align: center; vertical-align: middle" > 0
                </td>
                <td>
                    <button class="btn btn-light xoa-btn" id="xoa-btn-${len}"  style="padding-top: 20px;">
                        <i class="fas fa-trash-alt" style="font-size: 24px;"></i>
                    </button>
                </td>
            </tr>`;
                table.append(row);
                $('#select-state')[0].selectize.clear();
                $(`.soLuong${len}`).focus();
            });
        } else {
            // open modal to save new product
            window.open("/hang-hoa?id=0", '_blank');
        }
    })
}


//=================== Function ===================//
function saveNhaCungCap() {
    $("#btn-save-khach-hang").click(function () {
        let {check: checkTen, val: tenKhachHang} = checkText($("#ten-khach-hang"));
        let {check: checkSDT, val: sdtKhachHang} = checkSoDT($("#sdt-khach-hang"));
        let {check: checkEmail, val: emailKhachHang} = checkText($("#email-khach-hang"));
        let {check: checkDC, val: diaChiKhachHang} = checkText($("#dia-chi-khach-hang"));
        if(checkTen && checkSDT && checkEmail && checkDC) {
            let nhaCungCap = {
                ten: tenKhachHang,
                dienThoai: sdtKhachHang,
                loaiKhach: "Bình thường",
                email: emailKhachHang,
                facebook: "",
                ghiChu: "",
                trangThai: 1,
                tongMua: 0,
                tongNo: 0,
                diaChi: diaChiKhachHang
            }
            $("#pageBody").LoadingOverlay("show");
            uploadNhaCungCap(nhaCungCap).then(rs => {
                if (rs.message === "uploaded") {
                    $("#bimo5").val(diaChiKhachHang);
                    $("#bimo6").val(sdtKhachHang);
                    $("#bimo2").val(emailKhachHang);
                    let id = rs.data.id;
                    $.ajax({
                        type: 'GET',
                        headers: {
                            "Authorization": ss_lg
                        },
                        dataType: "json",
                        url: url_api + 'v1/admin/nha-cung-cap/find-by-id?id=' + id
                    }).then(function (data) {
                        var option = new Option(data.data.ten, data.data.id, true, true);
                        $('#select-khach-hang').append(option).trigger('change');

                        // manually trigger the `select2:select` event
                        $('#select-khach-hang').trigger({
                            type: 'select2:select',
                            params: {
                                data: data
                            }
                        });
                    });
                    $('#select-khach-hang').prop("disabled", true);
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm nhà cung cấp mới thành công");
                    $("#modal-khach-hang").modal('toggle');
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Lỗi hệ thống");
                }
            })
        }else{
            alterDanger("Chưa nhập đủ hoặc nhập sai định dạng thông tin");
        }
    })
}


//=================== Function ===================//
function deletePhieu() {
    $(document).on('click', '#btn-xoa-hoa-don', function () {
        for (i = 0; i < arr.length; i++) {
            arr[i] = 0;
            xoaCount++;
        }
        count = 0;
        $("#table-hang-hoa").find("tr:gt(0)").remove();
        $("#tong-tien").val(0);
    });

}


//=================== Function ===================//
function inPhieuNhap(ma) {
    $("#btn-print-pdf").click(function () {
        if (typeof ma !== "undefined") {
            console.log("print " + ma);
            let nguoiDundId = sessionStorage.getItem("id");
            createPhieuNhapPDF(ma, nguoiDundId).then(rs => {
                if (rs.message == 'uploaded') {
                    alterSuccess('In hóa đơn thành công');
                    window.open(rs.data.url, '_blank');
                } else {
                    alterDanger("In hóa đơn thất bại");
                }
            });
        }
    });
}




