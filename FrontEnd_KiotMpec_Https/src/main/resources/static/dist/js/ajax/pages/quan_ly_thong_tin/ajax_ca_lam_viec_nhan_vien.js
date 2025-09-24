var inputTimKiem, btnTimKiem, btnPrint;
$(function () {
    "use strict";

    $(document).ready(function () {
        var today = moment().day();
        $('#calendar1').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,listWeek'
            },
            firstDay: today,
            navLinks: true, // can click day/week names to navigate views
            editable: true,
            eventLimit: true, // allow "more" link when too many events

            events: function (start, end, timezone, callback) {
                console.log("vao day");
                jQuery.ajax({
                    url: url_api + 'v1/admin/nguoi-dung-ca-lam-viec/find-all?page=1&size=99999',
                    type: 'GET',
                    dataType: 'json',
                    headers: {
                        "Authorization": ss_lg
                    },
                    data: {
                        start: start.format(),
                        end: end.format()
                    },
                    success: function (doc) {
                        var events = [];
                        console.log(doc);
                        if (doc.message === "found") {

                            $.map(doc.data.currentElements, function (r) {
                                let color = null;
                                if (r.statusCheckin == 1) {
                                    color = "#25d5f2";
                                } else if (r.statusCheckin == 2) {
                                    color = "red";
                                } else if (r.statusCheckin == 3) {
                                    color = "#ff407b";
                                }
                                events.push({
                                    id: r.id,
                                    title: r.nguoiDung.hoVaTen,
                                    start: r.checkin,
                                    end: r.checkout,
                                    backgroundColor: color,
                                    borderColor: color
                                });

                            });
                        }
                        callback(events);
                    }
                });
            }
        });

    });

    $(document).ready(function () {

        /* initialize the external events
        -----------------------------------------------------------------*/

        $('#external-events .fc-event').each(function () {

            // store data so the calendar knows to render an event upon drop
            $(this).data('event', {
                title: $.trim($(this).text()), // use the element's text as the event title
                stick: true // maintain when user navigates (see docs on the renderEvent method)
            });

            // make the event draggable using jQuery UI
            $(this).draggable({
                zIndex: 999,
                revert: true, // will cause the event to go back to its
                revertDuration: 0 //  original position after the drag
            });

        });


        /* initialize the calendar
        -----------------------------------------------------------------*/

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            editable: true,
            droppable: true, // this allows things to be dropped onto the calendar
            drop: function () {
                // is the "remove after drop" checkbox checked?
                if ($('#drop-remove').is(':checked')) {
                    // if so, remove the element from the "Draggable Events" list
                    $(this).remove();
                }
            }
        });
    });
    inputTimKiem = $("#bimo");
    btnTimKiem = $("#btn-search");
    btnPrint = $("#btn-print");

    searchCa();
    searchNhanVien();

    chooseNhanVien();
    btnThemMoi();
    btnThemMoiCaLamViec();
    callFindAllNguoiDungCaLamViec();
    clickTimKiemNguoiDungCalamViec();
    taiExcelTK();
    //sort();
});

// function sort() {
//     const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;
//
// const comparer = (idx, asc) => (a, b) => ((v1, v2) =>
//         v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)
// )(getCellValue(asc ? a : b, idx), getCellValue(asc ? b : a, idx));
//
// // do the work...
// document.querySelectorAll('th').forEach(th => th.addEventListener('click', (() => {
//     console.log("tgsdf");
//     const table = th.closest('table');
//     Array.from(table.querySelectorAll('tr:nth-child(n+2)'))
//         .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))
//         .forEach(tr => table.appendChild(tr) );
// })));
// }

//=============== Function Tim kiem ca lam viec ===================//
function searchCa() {
    callGetSelectize($('#search-ca'), 'id', 'ten', "v1/admin/ca-lam-viec/search");
}

//=============== Function Tim kiem nhan vien ===================//
function searchNhanVien() {
    callGetSelectize($('#search-nhan-vien'), 'id', 'hoVaTen', "v1/admin/tai-khoan/find-all");
}

//=================== Function chon khach hang===================//
var nhanVien = null;

function chooseNhanVien() {
    $('#search-nhan-vien').change(function () {
        let val = $("#search-nhan-vien option:selected").text();
        let id = $("#search-nhan-vien option:selected").val();
        if (id > 0) {
            findNhanVienById(id).then(rs => {
                if (rs.message === "found") {
                    nhanVien = rs.data;
                    setViewNhanVien(nhanVien);

                } else {
                    alterDanger("Xảy ra lỗi hệ thống");
                }
            })
        } else {
            $("#modal-khach-hang").modal();
        }

    });
}

const TRANG_THAI_Tai_Khoan = ["kích hoạt", "khóa"];

function viewTrangThaiTaiKhoan(taiKhoan) {
    return TRANG_THAI_Tai_Khoan[taiKhoan.trangThai];
}

function setViewNhanVien() {
    let view = `<tr>
                            <th>Mã nhân viên</th>
                            <th>Tên nhân viên</th>
                            <th>Số điện thoại</th>
                            <th>Trạng thái</th>
                </tr>`;
    if (nhanVien != null) {
        view += `  <td>${viewField(nhanVien.maTaiKhoan)}</td>
                    <td>${viewField(nhanVien.hoVaTen)}</td>
                    <td>${viewField(nhanVien.soDienThoai)}</td>
                    <td>${viewTrangThaiTaiKhoan(nhanVien)}</td>
                </tr>`;
    } else {
        view += `<tr><td colspan="3">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-nhan-vien").html(view);
}

//=================== Function ===================//
function btnThemMoi() {

    $("#btn-them-moi").click(function () {
        $("#pageBody").LoadingOverlay("show");
        let ghiChu = $("#ghiChu").val();
        let idNhanVien = $("#search-nhan-vien option:selected").val();
        let idCa = $("#search-ca option:selected").val();
        let ngayThang = $("#chonNgay").val();

        if (idNhanVien == "" || idCa == "" || ngayThang == "") {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Vui lòng điền đầy đủ thông tin");
        } else {
            let nguoiDungCaLamViecUpload = {
                caLamViecId: idCa,
                nguoiDungId: idNhanVien,
                ngayThang: ngayThang,
                ghiChu: ghiChu
            }
            $("#modal-dat-lich").toggle();
            uploadNguoiDungCaLamViec(nguoiDungCaLamViecUpload).then(rs => {
                if (rs.message === "uploaded") {
                    $("#pageBody").LoadingOverlay("hide");
                    alterSuccess("Thêm mới ca làm việc cho người dùng thành công");
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Thêm mới ca làm việc cho người dùng thất bại");
                }
            });
        }
    });
}

//=================== Function ===================//
async function uploadNguoiDungCaLamViec(nguoiDungCaLamViecUpload) {
    return ajaxPost(`v1/admin/nguoi-dung-ca-lam-viec/upload`, nguoiDungCaLamViecUpload);
}

//=================== Function them moi ca lam viec===================//
function btnThemMoiCaLamViec() {

    $("#btn-add-ca-lam-viec").click(function () {
        $("#pageBody").LoadingOverlay("show");
        let tenCa = $("#tenCa").val();
        let soNhanVien = $("#soNhanVien").val();
        let timeCheckIn = $("#timeCheckIn").val();
        let timeCheckOut = $("#timeCheckOut").val();

        let checkInStart = $("#checkInStart").val();
        let checkInEnd = $("#checkInEnd").val();

        let checkOutStart = $("#checkOutStart").val();
        let checkOutEnd = $("#checkOutEnd").val();

        if (tenCa == "" || timeCheckIn == "" || timeCheckOut == "" || checkInStart == "" || checkInEnd == "" || checkOutStart == "" || checkOutEnd == "" || soNhanVien == "") {
            $("#pageBody").LoadingOverlay("hide");
            alterWarning("Vui lòng điền đầy đủ thông tin");
        } else {
            let caLamViec = {
                ten: tenCa,
                checkIn: timeCheckIn,
                checkOut: timeCheckOut,
                batDauChoPhepCheckIn: checkInStart,
                ketThucChoPhepCheckIn: checkInEnd,
                batDauChoPhepCheckOut: checkOutStart,
                ketThucChoPhepCheckOut: checkOutEnd,
                soLuongNhanVienToiDa: soNhanVien,
                status: "1"
            }
            $("#modal-them-ca").toggle();
            uploadCaLamViec(caLamViec).then(rs => {
                console.log(rs);
                if (rs.message === "uploaded") {
                    $("#pageBody").LoadingOverlay("hide");

                    alterSuccess("Thêm mới ca làm việc thành công");
                } else {
                    $("#pageBody").LoadingOverlay("hide");
                    alterDanger("Thêm mới ca làm việc thất bại");
                }
            });
        }
    });
}

//=================== Function ===================//
async function uploadCaLamViec(caLamViec) {
    return ajaxPost(`v1/admin/ca-lam-viec/upload`, caLamViec);
}

async function findAllNguoiDungCaLamViec(text = "", page, size) {
    return ajaxGet(`v1/admin/nguoi-dung-ca-lam-viec/search?text=${text}&page=${page}&size=${size}`);
}

function clickTimKiemNguoiDungCalamViec() {
    btnTimKiem.click(function () {
        let text = inputTimKiem.val();
        callFindAllNguoiDungCaLamViec(text);
    })
}

function callFindAllNguoiDungCaLamViec(text) {
    //$("#pageBody").LoadingOverlay("show");
    findAllNguoiDungCaLamViec(text, 1, 10).then(rs => {
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
                        setViewNguoiDungCaLamViec(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    findAllNguoiDungCaLamViec(text, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        console.log(arr);

                        setViewNguoiDungCaLamViec(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        }
    })
}

function setViewNguoiDungCaLamViec(pageNumber) {

    let view = `<tr>
                <th>STT</th>
                <th>Người dùng</th>
                <th>Ca làm việc</th>
                <th>Ngày tháng</th>
                <th>Check in</th>
                <th>Check out</th>
                <th>Status check in</th>
                <th>Status check out</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.caLamViec.ten)}</td>
                    <td>${viewField(item.ngayThang)}</td>
                    <td>${viewField(item.checkin)}</td>
                    <td>${viewField(item.checkout)}</td>
                    <td>${viewStatus(item.statusCheckin)}</td>
                    <td>${viewStatus(item.statusCheckout)}</td>
                    </td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td>  <td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    $("#table-ca-lam-viec").html(view);
}

function viewStatus(type) {
    if (type == 1) {
        type = "Đúng giờ";
    } else {
        type = "Sai giờ";
    }
    return type;
}

//=================== Function ===================//
//tai excel va in thong tin hoa don
function taiExcelTK() {
    let arrExcel = [];
    $('#btn-excel').on('click', function () {
        $("#pageBody").LoadingOverlay("show");
        let text = inputTimKiem.val();
        findAllNguoiDungCaLamViec(text, 1, 10).then(rs => {
            arrExcel = rs.data.currentElements;
            console.log(arrExcel);
            let nguoiDungId = sessionStorage.getItem("id");
            ajaxGet(`v1/admin/nguoi-dung-ca-lam-viec/excel?nguoi-dung-id=${nguoiDungId}&list-nguoi-dung-ca-lam-viec=` + arrExcel.map(nguoiDungCaLamViec => nguoiDungCaLamViec.id))
                .then(rs => {
                    console.log(rs);
                    alterSuccess("Tạo file excel thành công");
                    $("#pageBody").LoadingOverlay("hide");
                    window.open(rs.data.url, '_blank');
                }).catch(ex => {
                console.log(ex);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Tạo file excel thất bại");
            })
        });
        console.log(arrExcel);
    });
    clickPrintElement(".ttcttk");
}
