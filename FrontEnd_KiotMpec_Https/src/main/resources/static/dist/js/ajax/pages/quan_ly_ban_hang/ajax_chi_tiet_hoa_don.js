//================ Declare variable===================//
var arr = [], table, btnSaveKhachHang;
var count = 0, xoaCount = 0;

const HOA_DON_HD =1;

//=============== Function main ===================//
$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table-hang-hoa");
    btnThanhToan = $("#btn-thanh-toan");
    btnSaveKhachHang = $("#btn-save-khach-hang");

    //==========Function contructor========//
    searchHangHoa();
    chooseHH2();
    focusGiamGia();
    focusGiamGiaTong();
    searchKhachHang();
    addKhachHang();
    chooseKhachHang();
    chooseDv();
    clickThayDoiSoLuong();
    enterThayDoiSoLuong();
    saveKhachHang();
    deleteHH();
    khachTraTien();
    thanhToan();
    deleteHD();
    clickBtnMomo();
})

//=============== Function detail ===================//

//=============== Function Tim kiem hang hoa ===================//
function searchHangHoa() {

    callGetSelectize($('#select-state'), "id", "tenHangHoa", "v1/admin/hang-hoa/search-text");
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
            content: `<input type="text" min="0" class=giamGiaSp${hang}" id="giamGiaSp" placeholder="Nhập khuyến mại">`,
            template: '<div class="popover sp"><div class="arrow"></div>' +
                '<h3 class="popover-title"></h3><div class="popover-content">' +
                '</div>' +
                ' <label for="donVi" style="margin-left:15px">Đơn vị:</label>' +
                '  <select name="donVi" id="donViGiamGia" style="height:25px ;margin-bottom:15px">' +
                '    <option value="1">%</option>' +
                '    <option value="2" selected>VND</option>' +
                '  </select>' +
                `<button type="button" class="btn btn-primary popover-submit${hang}">` +
                '<i class="fas fa-check icon-white"></i></button>' +
                '<button type="button" class="btn btn-danger popover-cancel">' +
                '<i class="far fa-times-circle"></i></button></div>'
        });

        $(`#giamGiaSp`).focus();
        // focusPopover(hang);
        cancelPopover(hang);
        escapeBtn(hang);
        updateSubmit(hang);
        enterButton(hang);

    });
}

function focusPopover(hang){
    //cancel popover

}

function cancelPopover() {
    $(document).on('click', '.popover-cancel', function (e) {
        $('.giamGia').popover('hide');
    });
}

function escapeBtn(hang){
    //Escape button
    $(document).on("keyup", `#giamGiaSp`, function (e) {
        formatCurrency($(this));
        if (e.key === "Escape" || e.key === "Esc") {
            $('.giamGia').popover('hide');
        }
    });
}

function updateSubmit(hang){
    //update on submit
    $(document).on('click', `.popover-submit${hang}`, function (e) {
        let currentRow = $(this).parent().parent().parent().closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount;
        console.log("Hang : "+ hang);
        let giamGiaSp = valTien($(`#giamGiaSp`).val());
        console.log("giamGiaSp : " + giamGiaSp);
        let donVi = $("#donViGiamGia").val();
        let sl = $(`.soLuong${hang}`)[0].value;
        let giaBan = parseFloat(valTien(currentRow.find("td:eq(4)").text())) * sl;
        let giamGiaVal = 0, tongTien = 0;

        //1-%, 2-VND
        if (donVi == 1) {//xu ly giam theo %
            if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                giamGiaSp = 0;
            }
            console.log("giamGiaSp2" + giamGiaSp);
            giamGiaVal = (giaBan * giamGiaSp )/ 100;
            $(`.giamGia${hang}`).val(formatMoney(giamGiaVal));
            tongTien = giaBan - giamGiaVal;
            console.log(tongTien)

        } else if (donVi == 2) { //xu ly giam theo tien
            if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                giamGiaSp = 0;
            }
            console.log("giamGiaSp2" + giamGiaSp);
            $(`.giamGia${hang}`).val(formatMoney(giamGiaSp));
            tongTien = giaBan - giamGiaSp;
            console.log(tongTien);
        }
        $(`.tong${hang}`).text(formatMoney(`${tongTien}`));
        $('.giamGia').popover('hide');

        let tong = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                console.log("thanhTien:" + thanhTien);
                if (i != hang) {
                    tong += thanhTien;
                } else {
                    tong += tongTien;
                }
            }
        }
        console.log("tong:" + tong);

        $("#tong-tien").val(formatMoney(tong));
        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        return;
    });
}

function enterButton(hang){
    //enter button
    $(document).on("keypress", `#giamGiaSp`, function (e) {
        if (e.which === 13) {
            let giamGiaSp = valTien($(`#giamGiaSp`).val());
            console.log("giamGiaSp23214" + giamGiaSp);
            let donVi = $("#donViGiamGia").val();
            let sl = $(`.soLuong${hang}`)[0].value;
            let currentRow = $(this).closest("tr");
            let giaBan = parseFloat(valTien(currentRow.find("td:eq(4)")).text()) * sl;
            let giamGiaVal = 0, tongTien = 0;

            //1-%, 2-VND
            if (donVi == 1) {//xu ly giam theo %
                let giamGiaSp = $(`#giamGiaSp`).val();
                if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                    giamGiaSp = 0;
                }
                console.log("giamGiaSp2" + giamGiaSp);
                giamGiaVal = (giaBan * giamGiaSp) / 100;
                $(`.giamGia${hang}`).val(formatMoney(giamGiaVal));
                tongTien = giaBan - giamGiaVal;

            } else if (donVi == 2) { //xu ly giam theo tien
                if (giamGiaSp == ""||isNaN(giamGiaSp)) {
                    giamGiaSp = 0;
                }
                console.log("giamGiaSp2" + giamGiaSp);
                $(`.giamGia${hang}`).val(giamGiaSp);
                tongTien = giaBan - giamGiaSp;
                console.log(tongTien);
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
            let giamGiaTong = parseFloat($(valTien("#giamGiaTong")[0].value));
            let tienPhaiTra = tongTien_ - giamGiaTong;
            $("#tien-phai-tra").val(tienPhaiTra);
            return;
        }
    })
}



//=============== Function focus hide giam gia cho ca hoa don===================//
var donViTong = 0;
function focusGiamGiaTong() {
    $(document).on('click', '.giamGiaTong', function (e) {
        $('.giamGiaTong').popover({
            html: 'true',
            content: '<input type="text" id="giamGiaTongPop" placeholder="Nhập khuyến mại">',
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
        $(`#giamGiaTongPop`).focus();
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
            formatCurrency($(this));
            if (e.key === "Escape" || e.key === "Esc") {
                $('.giamGiaTong').popover('hide');
            }
        });

        //update on submit
        $(document).on('click', '.popover-submit-2', function (e) {
            $('.giamGiaTong').popover('hide');
            //xu ly giam gia tong
            console.log("tongTien1 : " + $("#tong-tien")[0].value);
            console.log("giamGiaTong1 : " + $("#giamGiaTongPop")[0].value);
            let tongTien = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
            let giamGiaTong = parseFloat(valTien($("#giamGiaTongPop")[0].value));//value giam gia tong mac dinh bang 0 neu k nhap gia
            console.log("tongTien : " + tongTien);
            console.log("giamGiaTong : " + giamGiaTong);
            if (giamGiaTong == ""||isNaN(giamGiaTong)) {
                giamGiaTong = 0;
            }
            let giamGiaTongVal = 0, tienPhaiTra = 0;
            donViTong = $("#donViGiamGiaTong").val();

            //1-%, 2-VND
            if (donViTong == 1) {    //xu ly giam theo %
                giamGiaTongVal = (tongTien * giamGiaTong) / 100;
                tienPhaiTra = tongTien - giamGiaTongVal;
                console.log("tienb phai tra"+tienPhaiTra);
                $('.giamGiaTong').val(formatMoney(giamGiaTongVal));
            } else if (donViTong == 2) {   //xu ly giam theo tien
                tienPhaiTra = tongTien - giamGiaTong;
                console.log("hello"+giamGiaTong);
                $("#giamGiaTong").val(formatMoney(giamGiaTong));
            }
            $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        });

        //enter button
        $(document).on("keypress", `#giamGiaTongPop`, function (e) {
            if (e.which === 13) {
                $('.giamGiaTong').popover('hide');
                //xu ly giam gia tong
                let tongTien = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
                let giamGiaTong = parseFloat(valTien($("#giamGiaTongPop")[0].value));//value giam gia tong mac dinh bang 0 neu k nhap gia
                if (giamGiaTong === "" || isNaN(giamGiaTong)) {
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
                    console.log("hello"+giamGiaTong);
                    $('.giamGiaTong').val(formatMoney(giamGiaTong));
                }
                $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
            }
        });

    })
}

//=============== Function Tien khach hang tra ===================//
function khachTraTien() {
    $(document).on('keyup', '#tien-khach-tra', function () {
        console.log("vao keyup");
        formatCurrency($(this));//chuyen tu so sang string format
    })
    $(document).on("keypress", "#tien-khach-tra", function (e) {
        if (e.which === 13) {
            let tienKhachTra = parseFloat(valTien($("#tien-khach-tra")[0].value));
            let tienPhaiTra = parseFloat(valTien($("#tien-phai-tra")[0].value));
            let tienTraLaiKhach = tienKhachTra - tienPhaiTra;
            if (tienTraLaiKhach < 0) {
                alterWarning("Số tiền khách đưa ít hơn tổng tiền của hóa đơn");
                $("#tien-tra-lai").val(0);
            }else {
                $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
            }
        }
    });
    $(document).on("blur", "#tien-khach-tra", function (e) {
        let tienKhachTra = parseFloat(valTien($("#tien-khach-tra")[0].value));
        let tienPhaiTra = parseFloat(valTien($("#tien-phai-tra")[0].value));
        let tienTraLaiKhach = tienKhachTra - tienPhaiTra;
        if (tienTraLaiKhach < 0) {
            alterWarning("Số tiền khách đưa ít hơn tổng tiền của hóa đơn");
            $("#tien-tra-lai").val(0);
        }else {
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }
    })
}


//=============== Function Tim kiem khach hang ===================//

function searchKhachHang() {
    callGetSelectize($('#select-khach-hang'), "id", "tenKhachHang", "v1/admin/khach-hang/search-text");
}

function addKhachHang() {
    $(".add-khach-hang").click(function () {
        $("#modal-khach-hang").modal();
    })
}

//=================== Function chon khach hang===================//
function chooseKhachHang() {
    $('#select-khach-hang').change(function () {
        let val = $("#select-khach-hang option:selected").text();
        let id = $("#select-khach-hang option:selected").val();
        console.log(val);
        console.log(id);
        if (id > 0) {
            findKhachHangById(id).then(rs => {
                if (rs.message === "found") {
                    console.log(rs);
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

//=============== Function Chon hang hoa ===================//
var dem = 0;

function chooseHH2() {
    $('#select-state').change(function () {
        if (dem === 0) {
            table.find('tbody').remove();
        }
        dem++;
        let val = $("#select-state option:selected").text();
        let id = $("#select-state option:selected").val();
        if (id > 0) {
            findFirstHH(id).then(sample => {
                if (sample.message == "found") {
                    arr.push(sample.data.id);
                    // let thanhTien = sample.data.giaBan;
                    // let tongTienCurr = parseFloat(valTien($("#tong-tien")[0].value));
                    // let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
                    // let tongTien = tongTienCurr + thanhTien - giamGiaTong;
                    // console.log("GiamGiaTong : " + giamGiaTong);
                    // $("#tong-tien").val(formatMoney(tongTien));

                    // gọi sự kiện thay đổi đơn vị -> gọi hàm change đơn vị ở dưới
                    // -> thay đổi value tổng tiền -> bị x2 tổng tiền
                    findHangHoa(id).then(rs => {
                        let len = arr.length;
                        count++; // stt san pham
                        let row = `<tr id="row-${len}">
                    <td data-id="${rs.id}" id="stt-${len}" style="padding-top: 20px;">${viewField(count)}</td>
                    <td style="padding-top: 20px;">                                                          
                           ${viewField(rs.data.tenHangHoa)}          
                    </td>
                    <td>
                        <select class = "select2 choose-don-vi " id="select-don-vi-${len}" placeholder="Chọn đơn vị" style="width: 55%"> 
                        <option></option>
                        </select>
                    </td>
                     <td style="text-align: center;padding-top: 20px">
                        <input class="soLuong soLuong${len}" type="number" id="quantity" min="1" value="1" style="width: 40px" /> 
                    </td>
                    
                    <td data-id="${sample.data.id}" class="giaBan${len}" style="padding-top: 20px;">
                        ${formatMoney(sample.data.giaBan)}
                    </td>
                    
                    <td style="text-align: center;padding-top: 20px">
                     <input class="giamGia giamGia${len}" type="text" id="giamGia" min="1"  value="${rs.data.phanTramGiamGia}" style="width: 70px" data-toggle="popover" data-placement="bottom"  />                 
                         <span>VNĐ</span>
                    </td>
                    
                    <td class="tong tong${len}" style="text-align: center;padding-top: 20px" data-id = "${sample.data.id}">
                             ${0}
                    </td>
                    
                    <td>
                        <button class="btn btn-light xoa-btn" id="xoa-btn-${len}">
                            <i class="fas fa-trash-alt" style="font-size: 15px;"></i>
                        </button>
                    </td>
                </tr>`;
                        table.append(row);
                        $(`.soLuong${len}`).focus();
                        $(`#select-don-vi-${len}`).select2({
                            placeholder: 'Chọn đơn vị',
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
                                    if (data.message == "found") {
                                        let rs = [];
                                        $.each(data.data.currentElements, function (idx, item) {
                                            rs.push({
                                                'id': item.id,
                                                'text': item.donVi.tenDonVi
                                            });
                                        });
                                        return {results: rs};
                                    } else {
                                        let rs = {
                                            'text': "Không tìm đơn vị phù hợp"
                                        }
                                        return {
                                            results: rs
                                        };
                                    }
                                }
                            }
                        });
                        $.ajax({
                            type: 'GET',
                            headers: {
                                "Authorization": ss_lg
                            },
                            dataType: "json",
                            url: url_api + 'v1/admin/don-vi-hang-hoa/find-by-id?id=' + sample.data.donViHangHoa.id
                        }).then(function (data) {
                            // create the option and append to Select2
                            var option = new Option(data.data.donVi.tenDonVi, data.data.id, true, true);

                            $(`#select-don-vi-${len}`).append(option).trigger('change');

                            // manually trigger the `select2:select` event
                            $(`#select-don-vi-${len}`).trigger({
                                type: 'select2:select',
                                params: {
                                    data: data
                                }
                            });
                        });
                    });
                    $('#search-don-vi-' + arr.length).val(sample.data.donViHangHoa.donVi.id).trigger("change");
                    $('#select-state')[0].selectize.clear();
                } else {
                    alterDanger("Sản phầm chưa được thiết lập giá");
                }
            }).catch(err => {
                alterDanger("Sản phầm chưa được thiết lập giá");
            })
        } else if (id == -1) {
            // open modal to save new product
            window.open("/hang-hoa?id=0", '_blank');
        }
    });
}


//=============== Function Chon don vi hang hoa ===================//
function chooseDv() {
    $(document).on("change", ".choose-don-vi", function () {
        let currentRow = $(this).closest("tr");
        let hang = currentRow.find("td:eq(0)").text();  // get current row 1st TD value
        let giamGiaSp = valTien($(`.giamGia${hang}`).val());
        let stt = currentRow[0].id.substring(4, 5);  // lay stt trong arr
        let id = $(this).find(':selected')[0].value;
        findGiaBanByHangHoaId(id).then(rs => {
            if (rs.message === "found") {
                let giaBan = rs.data.giaBan;
                let thanhTienCu = valTien($(`.tong${stt}`)[0].textContent);
                let sl = $(`.soLuong${hang}`)[0].value;
                $(`.giaBan${stt}`).text(formatMoney(giaBan));
                $(`.tong${stt}`).text(formatMoney(giaBan * sl - giamGiaSp));
                $(`.giaBan${stt}`).data('id', rs.data.id);
                let tongTienCurr = parseFloat(valTien($("#tong-tien")[0].value));
                let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
                console.log("giamGiaTong : " + giamGiaTong);
                console.log("tongTienCurr : " + tongTienCurr);
                console.log("thanhTien : " + rs.data.giaBan * sl);
                console.log("giamGiaSp : " + giamGiaSp);
                console.log("thanhTienCu : " + thanhTienCu.trim());

                let tongTien = tongTienCurr + rs.data.giaBan * sl - giamGiaSp - thanhTienCu;
                let tienPhaiTra = tongTien - giamGiaTong;
                $("#tong-tien").val(formatMoney(tongTien));
                $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
                arr[stt - 1] = rs.data.id;
            } else {
                alterDanger("Sản phầm chưa được thiết lập giá");
            }
        }).catch(err => {
            alterDanger("Sản phầm chưa được thiết lập giá");
        })
    })
}

// //=============== Function Tim kiem khach hang ===================//


//=============== Function Thay doi so luong ===================//
function clickThayDoiSoLuong() {
    $(document).on('click', '.soLuong', function () {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let sl = $(`.soLuong${hang}`)[0].value;
        let giamGiaSp = parseFloat(valTien($(`.giamGia${hang}`).val()));
        let giaBan = parseFloat(valTien(currentRow.find("td:eq(4)").text()))  * sl - giamGiaSp;

        $(`.tong${hang}`).text(formatMoney(`${giaBan}`));
        //xu ly giam giaz
        let tongTien = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tongTien = tongTien + thanhTien;
                } else {
                    tongTien = tongTien + giaBan;
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
        console.log("tienPhaiTra : " + tienPhaiTra);
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
    });
}


//=============== Function Click thay doi so luong ===================//
function enterThayDoiSoLuong() {
    $(document).on('keypress', '.soLuong', function (e) {
        if (e.which === 13) {
            let currentRow = $(this).closest("tr");
            let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
            let sl = $(`.soLuong${hang}`)[0].value;
            let giamGiaSp = parseFloat(valTien($(`.giamGia${hang}`).val()));
            let giaBan = parseFloat(valTien(currentRow.find("td:eq(4)").text()))  * sl - giamGiaSp;

            $(`.tong${hang}`).text(formatMoney(`${giaBan}`));
            //xu ly giam giaz
            let tongTien = 0;
            for (i = 1; i <= arr.length; i++) {
                if (arr[i - 1] != 0) {
                    let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                    if (i != hang) {
                        tongTien = tongTien + thanhTien;
                    } else {
                        tongTien = tongTien + giaBan;
                    }
                }
            }
            $("#tong-tien").val(formatMoney(tongTien));
            if (valTien($("#tien-khach-tra")[0].value) != 0) {
                let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value) - tongTien;
                $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
            }
            let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
            let giamGiaTong = parseFloat($("#giamGiaTong")[0].value);
            let tienPhaiTra = tongTien_ - giamGiaTong;
            $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        }
    });
    $(document).on('blur', '.soLuong', function (e) {
        let currentRow = $(this).closest("tr");
        let hang = parseInt(currentRow.find("td:eq(0)").text()) + xoaCount; // get current row 1st TD value
        let sl = $(`.soLuong${hang}`)[0].value;

        let giamGiaSp = parseFloat(valTien($(`.giamGia${hang}`).val()));
        let giaBan = parseFloat(valTien(currentRow.find("td:eq(4)").text()))  * sl - giamGiaSp;

        $(`.tong${hang}`).text(formatMoney(`${giaBan}`));
        //xu ly giam giaz
        let tongTien = 0;
        for (i = 1; i <= arr.length; i++) {
            if (arr[i - 1] != 0) {
                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                if (i != hang) {
                    tongTien = tongTien + thanhTien;
                } else {
                    tongTien = tongTien + giaBan;
                }
            }
        }
        $("#tong-tien").val(formatMoney(tongTien));
        if (valTien($("#tien-khach-tra")[0].value) != 0) {
            let tienTraLaiKhach = valTien($("#tien-khach-tra")[0].value) - tongTien;
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }
        let tongTien_ = parseFloat(valTien($("#tong-tien")[0].value));  //tong tien chua giam gia tong
        let giamGiaTong = parseFloat($("#giamGiaTong")[0].value);
        let tienPhaiTra = tongTien_ - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));


    })
}

//=============== Function Xoa hang hoa ===================//
function deleteHH() {
    $(document).on('click', '.xoa-btn', function () {
        let currentRow = $(this).closest("tr");
        let stt = currentRow[0].id.substring(4, 5);
        $(currentRow[0]).hide();
        arr[stt - 1] = 0;
        let tongTienCu = parseFloat(valTien($("#tong-tien")[0].value));
        let giamGiaTong = parseFloat(valTien($("#giamGiaTong")[0].value));
        let tongTienMoi = tongTienCu - parseFloat(valTien($(`.tong${stt}`)[0].textContent));
        console.log("tongtiencu : " + tongTienCu);
        console.log("giamGiaTong : " + giamGiaTong);
        console.log("tongTienMoi : " + tongTienMoi);
        let tienPhaiTra = tongTienMoi - giamGiaTong;
        $("#tien-phai-tra").val(formatMoney(tienPhaiTra));
        $("#tong-tien").val(formatMoney(tongTienMoi));
        let tienKhachTra = valTien($("#tien-khach-tra")[0].value);
        if (tienKhachTra != 0) {
            let tienTraLaiKhach = tienKhachTra - tongTienMoi;
            $("#tien-tra-lai").val(formatMoney(tienTraLaiKhach));
        }
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


//=============== Function Thanh toan ===================//
function thanhToan() {
    btnThanhToan.on("click", function () {
            let khachHangId = $("#select-khach-hang option:selected").val();
            let {check: checkDT, val: valDT} = checkSoDT($("#bimo5"));
            let tongTien = parseFloat(valTien($("#tien-phai-tra")[0].value));
            let tienKhachTra = valTien($("#tien-khach-tra")[0].value);
            //kiem tra dieu kien
            if (khachHangId <= 0) {
                alterWarning("Bạn chưa chọn khách hàng");
            } else if (!checkDT) {
                alterWarning("Nhập sai định dạng số điện thoại");
            } else if (tongTien == 0) {
                alterWarning("Bạn không thể tạo hóa đơn với tổng tiền bằng 0");
            } else if (tienKhachTra === "") {
                alterWarning("Vui lòng điền số tiền khách trả");
            } else {
                let tienTraKhach = valTien($("#tien-tra-lai")[0].value);
                let tienNo = valTien(tienTraKhach.substr(1, tienTraKhach.length));
                let giamGia = valTien($("#giamGiaTong").val());
                if (tienTraKhach < 0) {
                    let ghiChu = $("#ghi-chu")[0].value;
                    $("#modal-cho-no .modal-body").text(`Số tiền khách trả ít hơn tổng tiền hóa đơn. Bạn có chắc chắn cho khách hàng nợ số tiền ${tienNo} không?`);
                    $("#modal-cho-no").modal('toggle');
                    $("#confirm-btn").click(function () {
                        let hoaDon = {
                            tongTien: tongTien,
                            tienKhachTra: tienKhachTra,
                            tienTraLaiKhach: 0,
                            ghiChu: ghiChu,
                            giamGia: giamGia,
                            trangThai: 1
                        };
                        let giaBanList = [];
                        for (i = 0; i < arr.length; i++) {
                            if (arr[i] != 0) {
                                giaBanList.push(arr[i]);
                            }
                        }
                        let hoaDonChiTietList = [];
                        let count = 0;
                        for (i = 1; i <= arr.length; i++) {
                            if (arr[i - 1] != 0) {
                                let sl = $(`.soLuong${i}`)[0].value;
                                let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                                hoaDonChiTietList[i - 1 - count] = {
                                    soLuong: sl,
                                    tongGia: thanhTien,
                                    xoa: false
                                }
                            } else {
                                count++;
                            }
                        }
                        let hoaDonDto = {
                            hoaDon: hoaDon,
                            lichSuGiaBanIdList: giaBanList,
                            hoaDonChiTietList: hoaDonChiTietList
                        };
                        uploadData(hoaDonDto, false);
                        let phieuNo = {
                            tongNo: parseFloat(tienNo)
                        }
                    })
                } else {
                    let ghiChu = $("#ghi-chu")[0].value;
                    let hoaDon = {
                        tongTien: tongTien,
                        tienKhachTra: tienKhachTra,
                        tienTraLaiKhach: tienTraKhach,
                        ghiChu: ghiChu,
                        trangThai: 1
                    };
                    let giaBanList = [];
                    for (i = 0; i < arr.length; i++) {
                        if (arr[i] != 0) {
                            giaBanList.push(arr[i]);
                        }
                    }
                    let hoaDonChiTietList = [];
                    let count = 0;
                    for (i = 1; i <= arr.length; i++) {
                        if (arr[i - 1] != 0) {
                            let sl = $(`.soLuong${i}`)[0].value;
                            let thanhTien = parseFloat(valTien($(`.tong${i}`)[0].textContent));
                            hoaDonChiTietList[i - 1 - count] = {
                                soLuong: sl,
                                tongGia: thanhTien,
                                giamGia: 0
                            }
                        } else {
                            count++;
                        }
                    }
                    let hoaDonDto = {
                        hoaDon: hoaDon,
                        lichSuGiaBanIdList: giaBanList,
                        hoaDonChiTietList: hoaDonChiTietList
                    };
                    uploadData(hoaDonDto, false);
                }
            }

        }
    )
}

//=============== Function Upload hoa don ===================//
var maHD = null;

function uploadData(hoaDonDto = {}, check) {
    $("#pageBody").LoadingOverlay("show");
    let khachHangId = $("#select-khach-hang").find(':selected')[0].value;
    let chiNhanhId = user.chinhanhid;
    uploadHoaDon(nguoiDungId, khachHangId, chiNhanhId, hoaDonDto).then(rs => {
        if (rs.message === "uploaded") {
            console.log(rs.data);
            maHD = rs.data.ma;
            let idHD = rs.data.id;

            let tongTien = parseFloat(valTien($("#tien-phai-tra")[0].value));
            let tienDaTra = parseFloat(valTien($("#tien-khach-tra")[0].value));
            let ghiChu = $("#ghi-chu")[0].value;
            let phieuThuRequest = {
                tienDaTra: tienDaTra,
                ghiChu: ghiChu
            }
            let phieuHoatDong = {
                giaTri: tongTien
            }

            // upload phieu hoat dong
            uploadPhieuHoatDong(phieuHoatDong,user.nguoiDungid, idHD,0, 0, 0, 0 , 0, HOA_DON_HD).then(rs => {
                console.log(rs);
            });

            // upload phieu thu, phieu thu hoa don
            uploadPhieuThu(phieuThuRequest, user.chinhanhid, user.nguoiDungid, 1).then(rs2 => {
                if (rs2.message === "uploaded") {
                    console.log("Upload Phieu thu sucess");
                    console.log(rs2);
                    let idPhieuThu = rs2.data.id;
                    uploadPhieuThuHoaDon(idHD,idPhieuThu, tienDaTra).then(rs3 => {
                        console.log("uploadPhieuThuHoaDon sucess");
                    })
                }
            });

            //disable button
            $("#btn-thanh-toan").prop('disabled', true);
            $("#btn-print-pdf").prop('disabled', false);
            $("#search-hang-hoa").attr("disabled", "disabled");
            inHoaDon(maHD);
            $("#pageBody").LoadingOverlay("hide");
            alterSuccess("Thêm hóa đơn mới thành công");
        } else {
            console.log(rs.message);
            $("#pageBody").LoadingOverlay("hide");
            alterDanger("Thêm hóa đơn mới không thành công");

        }
    });
}

//=============== Function Luu khach hang ===================//
function saveKhachHang() {
    btnSaveKhachHang.click(function () {
        let {check: checkTen, val: tenKhachHang} = checkText($("#ten-khach-hang"));
        let {check: checkSDT, val: sdtKhachHang} = checkSoDT($("#sdt-khach-hang"));
        let {check: checkEmail, val: emailKhachHang} = checkText($("#email-khach-hang"));
        let {check: checkDC, val: diaChiKhachHang} = checkText($("#dia-chi-khach-hang"));
        let {check: checkNS, val: ngaySinh} = checkNS1($("#birthday"));
        if(checkTen && checkSDT && checkEmail && checkDC ){
            let khachHang = {
                taiKhoan: "",
                tenKhachHang: tenKhachHang,
                dienThoai: sdtKhachHang,
                loaiKhach: "Bình thường",
                ngaySinh: ngaySinh,
                gioiTinh: null,
                email: emailKhachHang,
                facebook: "",
                ghiChu: "",
                trangThai: 1,
                diaChi: diaChiKhachHang
            }
            $("#pageBody").LoadingOverlay("show");
            uploadKhachHang(khachHang).then(rs => {
                console.log(JSON.stringify(rs));
                if (rs.message === "uploaded") {
                    $("#bimo6").val(sdtKhachHang);
                    $("#bimo2").val(emailKhachHang);
                    let option = new Option(tenKhachHang, rs.data.id, true, true);
                    $('#select-khach-hang').append(option).trigger('change');
                    // $('#select-khach-hang').prop("disabled", true);
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm khách hàng mới thành công");
                    $("#modal-khach-hang").modal('hide');
                    $("#btn-thanh-toan").prop('disabled', false);
                    $("#btn-print-pdf").prop('disabled', true);
                    $("#search-hang-hoa").removeAttr("disabled");
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Lỗi hệ thống");
                }
            });
        }else {
            alterDanger("Chưa nhập đủ hoặc nhập sai định dạng thông tin");
        }
    })
}

//=============== Function In hoa don ===================//
function inHoaDon(maHD) {
    $("#btn-print-pdf").click(function () {
        if (typeof maHD !== "undefined") {
            console.log("print " + maHD);
            $("#pageBody").LoadingOverlay("show");
            createHoaDonPDF(maHD, nguoiDungId).then(rs => {
                if (rs.message == 'uploaded') {
                    alterSuccess('In hóa đơn thành công');
                    openInNewTab(rs.data.url);
                } else {
                    alterDanger("In hóa đơn thất bại");
                }
                $("#pageBody").LoadingOverlay("hide");
            });
        }
    });
}

function openInNewTab(url) {
    var win = window.open(url, '_blank');
    win.focus();
}

//=============== Function Xoa  ===================//
function deleteHD() {
    $("#btn-xoa-hoa-don").on("click", function () {
        for (i = 0; i < arr.length; i++) {
            $(`#row-${i + 1}`).remove();
        }
        arr = [];
        count = 0;
        xoaCount = 0;
        $("select").val('').trigger('change');
        $("#bimo5").val("");
        $("#bimo6").val("");
        $("#bimo2").val("");
        $("#bimo3").val("");
        $("#bimo4").val("");
        $("#bimo5").prop('disabled', false);
        $("#bimo2").prop('disabled', false);
        $("#bimo6").prop('disabled', false);
        $("#tong-tien").val(0);
        $("#tien-khach-tra").val("");
        $("#tien-tra-lai").val("");
    });
}



// //=============== Function momo ===================//
function clickBtnMomo() {
    $("#momo").click(function () {
        var ma = Date.now() + Math.random();
        let tienMoMo = parseFloat($("#tien-phai-tra")[0].value);
        console.log("momo");
        let momo = {
            "notifyURL": "https://momo.vn",
            "returnURL": "https://momo.vn",
            "orderId": ma.toString(),
            "amount": tienMoMo.toString(),
            "orderInfo": "Thanh toán hóa đơn",
            "requestId": "1540456475"
        }
        console.log(momo);
        PayMoMo(momo).then(rs => {
            console.log(rs);
            if (rs.message == "success") {
                openInNewTab(rs.data.payUrl);
            }
        });
    })
}
