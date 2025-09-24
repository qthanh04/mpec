//================ Declare variable===================//
var arr = [];

var inputDiaChiBenGiao, inputSoDienThoaiBenGiao, inputHoTenBenGiao, selectThanhPhoBenGiao, selectQuanHuyenBenGiao,
    selectXaPhuongBenGiao;
var inputDiaChiBenNhan, inputSoDienThoaiBenNhan, inputHoTenBenNhan, selectThanhPhoBenNhan, selectQuanHuyenBenNhan,
    selectXaPhuongBenNhan, selectDonViGiaoHang;
var inputDai, inputRong, inputCao;
var table, btnXacNhan, btnCheckGia;
var searchParams = new URLSearchParams(window.location.search);
var hoaDonId = searchParams.get('id-hoa-don');

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    table = $("#table");
    inputDiaChiBenGiao = $("#input-text-1");
    inputSoDienThoaiBenGiao = $("#input-text-2");
    inputHoTenBenGiao = $("#input-text-3");
    selectThanhPhoBenGiao = $("#select-thanh-pho-ben-giao");
    selectQuanHuyenBenGiao = $("#select-quan-huyen-ben-giao");
    selectXaPhuongBenGiao = $("#select-phuong-xa-ben-giao");
    inputDiaChiBenNhan = $("#input-text-4");
    inputSoDienThoaiBenNhan = $("#input-text-5");
    inputHoTenBenNhan = $("#input-text-6");
    selectThanhPhoBenNhan = $("#select-thanh-pho-ben-nhan");
    selectQuanHuyenBenNhan = $("#select-quan-huyen-ben-nhan");
    selectXaPhuongBenNhan = $("#select-phuong-xa-ben-nhan");
    inputDai = $("#input-text-11");
    inputRong = $("#input-text-12");
    inputCao = $("#input-text-13");
    // btnXacNhan = $("#btn-2");
    btnXacNhan = $("#confirm-btn");
    btnCheckGia = $("#btn-gia");
    selectDonViGiaoHang = $("#select-don-vi-giao-hang");

    //==========Function constructor========//
    viewSelectThanhPhoBenGiao(selectThanhPhoBenGiao);
    viewSelectDonViGiaoHang(selectDonViGiaoHang);
    clickThanhPhoBenGiao();
    clickQuanHuyenBenGiao();
    viewSelectThanhPhoBenNhan(selectThanhPhoBenNhan);
    clickThanhPhoBenNhan();
    clickQuanHuyenBenNhan();
    submitUploadHoaDon();
    checkGia();

});

//=================== Function Ben Giao===================//
async function clickThanhPhoBenGiao() {
    selectThanhPhoBenGiao.click(function () {
        let rs = selectThanhPhoBenGiao.val();
        viewSelectQuanHuyenBenGiao(selectQuanHuyenBenGiao, rs);
        //let tenThanhPho=$("#select-thanh-pho-ben-giao option:selected").text();
    })
}

async function viewSelectQuanHuyenBenGiao(selector, rs) {
    let view = ``;
    findQuanHuyenByThanhPhoId(rs, MAX).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("");
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

async function clickQuanHuyenBenGiao() {
    selectQuanHuyenBenGiao.click(function () {
        let rs = selectQuanHuyenBenGiao.val();
        viewSelectPhuongXaBenGiao(selectXaPhuongBenGiao, rs);

    })
}

async function viewSelectPhuongXaBenGiao(selector, rs) {
    let view = ``;
    findPhuongXaByQuanHuyenId(rs, MAX).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("");
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

//=================== Function Ben Nhan===================//
async function clickThanhPhoBenNhan() {
    selectThanhPhoBenNhan.click(function () {
        let rs = selectThanhPhoBenNhan.val();
        viewSelectQuanHuyenBenNhan(selectQuanHuyenBenNhan, rs);
    })
}

async function viewSelectQuanHuyenBenNhan(selector, rs) {
    let view = ``;
    findQuanHuyenByThanhPhoId(rs, MAX).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("");
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

async function clickQuanHuyenBenNhan() {
    selectQuanHuyenBenNhan.click(function () {
        let rs = selectQuanHuyenBenNhan.val();
        viewSelectPhuongXaBenNhan(selectXaPhuongBenNhan, rs);
    })
}

async function viewSelectPhuongXaBenNhan(selector, rs) {
    let view = ``;
    findPhuongXaByQuanHuyenId(rs, MAX).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.locname}</option>`).join("");
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}

var hoaDon1 = null;
var hoaDon2 = null;
var products = [];
var ad1 = null;
var thoiGian = null;
var tongTien = null;

//=================== Function upload hoa don 1, ghn, 2, ghtk===================//
function submitUploadHoaDon() {
    btnXacNhan.click(function () {

        let {check: check_DCBenGiao, val: valDCBenGiao} = checkDiaChiHD(inputDiaChiBenGiao);
        let {check: check_SDTBenGiao, val: valSdtBenGiao} = checkSDTHD(inputSoDienThoaiBenGiao);
        let {check: check_HVTBenGiao, val: valHVTBenGiao} = checkHoTenHD(inputHoTenBenGiao);
        let thanhPhoBenGiao = $("#select-thanh-pho-ben-giao option:selected").text();
        let quanHuyenBenGiao = $("#select-quan-huyen-ben-giao option:selected").text();
        let phuongXaBenGiao = $("#select-phuong-xa-ben-giao option:selected").text();

        let {check: check_DCBenNhan, val: valDCBenNhan} = checkDiaChiHD(inputDiaChiBenNhan);
        let {check: check_SDTBenNhan, val: valSdtBenNhan} = checkSDTHD(inputSoDienThoaiBenNhan);
        let {check: check_HVTBenNhan, val: valHVTBenNhan} = checkHoTenHD(inputHoTenBenNhan);
        let thanhPhoBenNhan = $("#select-thanh-pho-ben-nhan option:selected").text();
        let quanHuyenBenNhan = $("#select-quan-huyen-ben-nhan option:selected").text();
        let phuongXaBenNhan = $("#select-phuong-xa-ben-nhan option:selected").text();
        let chieudai = inputDai.val();
        let chieurong = inputRong.val();
        let chieucao = inputCao.val();
        if (check_SDTBenGiao && check_HVTBenGiao && check_DCBenGiao && check_DCBenNhan && check_SDTBenNhan && check_HVTBenNhan) {
            //
            try {
                $("#pageBody").LoadingOverlay("show");
                let donViGiaoHangId = selectDonViGiaoHang.val();
                if (donViGiaoHangId == 1) {
                    findHoaDonById(hoaDonId).then(rs => {
                        console.log(rs);
                        tongTien = rs.data.tongTien;
                        hoaDon1 = {
                            "orderId": hoaDonId,
                            "payment_type_id": donViGiaoHangId,

                            "required_note": "KHONGCHOXEMHANG",
                            "return_phone": valSdtBenGiao,

                            "return_address": valDCBenGiao,
                            "return_province": thanhPhoBenGiao,
                            "return_district": quanHuyenBenGiao,
                            "return_ward": phuongXaBenGiao,


                            "to_address": valDCBenNhan,
                            "to_province": thanhPhoBenNhan,
                            "to_district": quanHuyenBenNhan,
                            "to_ward": phuongXaBenNhan,

                            "client_order_code": "HELLO",
                            "to_phone": valSdtBenNhan,
                            "to_name": valHVTBenNhan,

                            "cod_amount": tongTien,
                            "content": "ABCDEF",
                            "weight": 200,
                            "length": chieudai,
                            "width": chieurong,
                            "height": chieucao,
                            "pick_station_id": 0,
                            "insurance_value": 2000000,
                            "service_id": "0",
                            "service_type_id": "2",
                            "coupon": null
                        }
                        console.log(hoaDon1);
                    }).then(rs => {
                        giaoHangNhanh(hoaDon1, 1).then(rs => {
                            console.log(rs);
                            if (rs.success == true) {
                                setTrangThaiHoaDon(hoaDonId, 1).then(rs => {
                                    if (rs.message == "success") {
                                        $("#pageBody").LoadingOverlay("hide");
                                        alterSuccess("Giao hàng thành công");
                                    }
                                })
                            } else {
                                alterSuccess("Giao hàng thất bại");
                            }
                        });

                    })
                } else if (donViGiaoHangId == 2) {
                    $("#pageBody").LoadingOverlay("show");
                    findAllByIdHoaDon(hoaDonId).then(rs => {
                        for (i = 0; i < rs.data.currentElements.length; i++) {
                            ad1 = {
                                "name": rs.data.currentElements[i].lichSuGiaBan.donViHangHoa.hangHoa.tenHangHoa,
                                "weight": 0.1,
                                "quantity": rs.data.currentElements[i].soLuong
                            }
                            thoiGian = rs.data.currentElements[i].hoaDon.thoiGian;
                            products[i] = ad1;
                        }

                    }).then(rs => {
                        findHoaDonById(hoaDonId).then(rs => {
                            tongTien = rs.data.tongTien;
                            thoiGian = rs.data.thoiGian;
                            hoaDon2 = {
                                products,
                                order: {
                                    "id": hoaDonId,
                                    "pick_name": valHVTBenGiao,
                                    "pick_address": valDCBenGiao,
                                    "pick_province": thanhPhoBenGiao,
                                    "pick_district": quanHuyenBenGiao,
                                    "pick_ward": phuongXaBenGiao,
                                    "pick_tel": valSdtBenGiao,
                                    "tel": valSdtBenNhan,
                                    "name": valHVTBenNhan,
                                    "address": valDCBenGiao,
                                    "province": thanhPhoBenGiao,
                                    "district": quanHuyenBenGiao,
                                    "ward": phuongXaBenGiao,
                                    "hamlet": "Khác",
                                    "is_freeship": "1",
                                    "pick_date": thoiGian,
                                    "pick_money": tongTien,
                                    "note": "Khối lượng tính cước tối đa: 1.00 kg",
                                    "value": 3,
                                    "transport": "fly"
                                }
                            }
                            console.log(hoaDon2);
                        }).then(rs => {
                            console.log(rs);
                            giaoHangTietKiem(hoaDon2, 2).then(rs => {
                                console.log(rs);
                                if (rs.success == true) {
                                    setTrangThaiHoaDon(hoaDonId, TRANG_THAI_HOA_DON[1]).then(rs => {
                                        if (rs.message == "success") {
                                            $("#pageBody").LoadingOverlay("hide");
                                            alterSuccess("Giao hàng thành công");
                                        }

                                    })
                                }
                            })

                        })
                    })

                }
                $("#pageBody").LoadingOverlay("hide");
            } catch (e) {
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error - Giao Hang");
            }
        } else {
            alterWarning("Vui lòng nhập đầy đủ thông tin");
            $("#pageBody").LoadingOverlay("hide");
        }
    })
}

function checkGia() {
    btnCheckGia.click(function () {
        console.log("check gia");
        let {check: check_DCBenGiao, val: valDCBenGiao} = checkDiaChiHD(inputDiaChiBenGiao);
        let {check: check_SDTBenGiao, val: valSdtBenGiao} = checkSDTHD(inputSoDienThoaiBenGiao);
        let {check: check_HVTBenGiao, val: valHVTBenGiao} = checkHoTenHD(inputHoTenBenGiao);
        let thanhPhoBenGiao = $("#select-thanh-pho-ben-giao option:selected").text();
        let quanHuyenBenGiao = $("#select-quan-huyen-ben-giao option:selected").text();
        let phuongXaBenGiao = $("#select-phuong-xa-ben-giao option:selected").text();

        let {check: check_DCBenNhan, val: valDCBenNhan} = checkDiaChiHD(inputDiaChiBenNhan);
        let {check: check_SDTBenNhan, val: valSdtBenNhan} = checkSDTHD(inputSoDienThoaiBenNhan);
        let {check: check_HVTBenNhan, val: valHVTBenNhan} = checkHoTenHD(inputHoTenBenNhan);
        let thanhPhoBenNhan = $("#select-thanh-pho-ben-nhan option:selected").text();
        let quanHuyenBenNhan = $("#select-quan-huyen-ben-nhan option:selected").text();
        let phuongXaBenNhan = $("#select-phuong-xa-ben-nhan option:selected").text();
        let chieudai = inputDai.val();
        let chieurong = inputRong.val();
        let chieucao = inputCao.val();
        if (check_SDTBenGiao && check_HVTBenGiao && check_DCBenGiao && check_DCBenNhan && check_SDTBenNhan && check_HVTBenNhan) {
            $("#pageBody").LoadingOverlay("show");
            findHoaDonById(hoaDonId).then(rs => {
                $("#modal-don-vi-giao-hang").toggle();
                console.log(rs);
                tongTien = rs.data.tongTien;
                hoaDon1 = {
                    "orderId": hoaDonId,
                    "return_phone": valSdtBenGiao,
                    "return_address": valDCBenGiao,
                    "return_province": thanhPhoBenGiao,
                    "return_district": quanHuyenBenGiao,
                    "return_ward": phuongXaBenGiao,
                    "to_address": valDCBenNhan,
                    "to_province": thanhPhoBenNhan,
                    "to_district": quanHuyenBenNhan,
                    "to_ward": phuongXaBenNhan,
                    "to_phone": valSdtBenNhan,
                    "to_name": valHVTBenNhan,
                    "cod_amount": tongTien,
                    "weight": "200",
                    "height": "15",
                    "service_type_id": "2",
                    "payment_type_id": "1",
                    "order_time": 0,
                    "service_id": "SGN-BIKE"
                }
                // {   "orderId":hoaDonId,
                //
                //     "required_note": "KHONGCHOXEMHANG",
                //     "return_phone": "0332190458",
                //     "return_address": "725 Hẻm số 7 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Việt Nam",
                //     "return_province":"Hà Nội",
                //     "return_district": "Quận Thanh Xuân",
                //     "return_ward": "Phường Khương Trung",
                //     "to_address": "Miss Ao Dai Building, 21 Nguyễn Trung Ngạn, Bến Nghé, Quận 1, Hồ Chí Minh, Vietnam",
                //     "to_province":"Hà Nội",
                //     "to_ward": "Phường Kim Liên",
                //     "to_district": "Quận Đống Đa",
                //     "client_order_code": "dsa",
                //     "to_name": "TinTest124",
                //     "to_phone": "0987654321",
                //     "cod_amount": "200000",
                //     "to_street":"xa dan",
                //     "weight": "200",
                //     "height": "15",
                //     "service_type_id": "2",
                //     "payment_type_id": "1",
                //     "order_time":0,
                //     "service_id":"SGN-BIKE"
                // }
                console.log(hoaDon1);
                getFee(hoaDon1).then(rs => {
                    if (rs.success===true){
                        arr = rs.data;
                        console.log(rs);
                        console.log(arr);
                        setViewGia();
                    }else {
                        alterWarning("Kiểm tra giá thất bại");
                        $("#pageBody").LoadingOverlay("hide");
                    }

                })
            })
        }
    })
}

//=================== Function ===================//
function setViewGia() {

    let view = `<tr>
                <th>STT</th>
                <th>Mã hóa đơn</th>
                <th>Thời gian</th>
              
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(1 - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.partnerName)}</td>
                    <td>${viewField(item.totalFee)}</td>
                </tr>`);

    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    table.html(view);
    $("#pageBody").LoadingOverlay("hide");
}

//=======function-check========//
function checkDiaChiHD(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size, text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check: rs, val: text};
}

function checkHoTenHD(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size, text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check: rs, val: text};
}

function checkSDTHD(selector) {
    let rs = false;
    let size = 10;
    let ten = selector.val();
    let selectorParent = selector.parent();
    if (regexDienThoai(ten)) {
        if (checkSize(size, ten)) {
            rs = true;
            hiddenError(selectorParent);
        } else viewError(selectorParent, `Độ dài chưa phù hợp > 0 và < ${size}`);
    } else viewError(selectorParent, "Số điện thoại chưa đúng định dạng");
    return {check: rs, val: ten};
}

async function findAllDonViGiaoHang() {
    return ajaxGet('v1/public/partner/find-all');
}

async function viewSelectDonViGiaoHang(selector) {
    let view = ``;
    findAllDonViGiaoHang(1, MAX).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            view = rs.map(data => `<option value=${data.id}>${data.name}</option>`).join("");
            selector.html(view);
        }
    }).catch(err => {
        alterDanger("Server Error");
        console.log(err);
    })
}





