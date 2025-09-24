//================ Declare variable===================//
var selectChiNhanh, inputSearch, btnSearch, tableHangHoa;
var arrHangHoa = [];

//=============== Function main ===================//
$(function () {

    //=========Mapping variabe and id=========//
    selectChiNhanh = $("#bimo2");
    inputSearch = $("#bimo1");
    btnSearch = $("#btn-1");
    tableHangHoa = $("#table-hang-hoa");

    //==========Function constructor========//
    viewChiNhanh();
    clickSearchHH();
    btnImportFileExcel();
    taiExcelTK();
})

//=============== Function detail ===================//

//=================== Function ===================//
function viewChiNhanh() {
    chucvuId = user.chucvuid;
    chiNhanhId = user.chinhanhid;
    callSearchHangHoa(chiNhanhId);
    if (chucvuId == 1) {
        viewSelectChiNhanhFindByTongCongTy().then(rs => {
            selectChiNhanh.html(`<option value="0">Tất cả chi nhánh</option>` + rs);
            runSelect2();
            selectChiNhanh.change(function () {
                inputSearch.val("");
                btnSearch.trigger("click");
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

//=================== Function ===================//
function clickSearchHH() {
    btnSearch.click(function () {
        let chiNhanhId = selectChiNhanh.val(), text = inputSearch.val();
        callSearchHangHoa(chiNhanhId, text);
    })
}

//=================== Function ===================//
function callSearchHangHoa(chiNhanhId, text = "%") {
    $("#pageBody").LoadingOverlay("show");
    hangHoaSearch(chiNhanhId, text).then(rs => {
        if (rs.message === "found") {
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
                        arrHangHoa = rs.data.currentElements;
                        setViewHangHoa(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    hangHoaSearch(chiNhanhId, text, pagination.pageNumber).then(rs => {
                        arrHangHoa = rs.data.currentElements;
                        setViewHangHoa(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        } else {
            arrHangHoa = [];
            setViewHangHoa(1);
            paginationReset();
        }
    }).catch(err => {
        console.log(err);
        alterDanger("Server Error - Search Hang Hoa");
        setViewHangHoa(1);
    });


}

//=================== Function ===================//
function setViewHangHoa(pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Mã hàng</th>
                <th>Tên hàng</th>
                <th>Tồn kho </th>
                <th>Thương Hiệu</th>
                <th>Nhóm hàng </th>
                <th>Mã Vạch</th>
                </tr>`;
    let len = arrHangHoa.length;
    if (len > 0) {
        view += arrHangHoa.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td>${(pageNumber - 1) * 10 + index + 1}</td>
                    <td><a href="hang-hoa?id=${item.hangHoa.id}" target="_blank">${viewField(item.hangHoa.ma)}</a></td>
                    <td>${viewField(item.hangHoa.tenHangHoa)}</td>
                    <td>${viewField(item.tonKho)}</td>
                    <td>${viewField(item.hangHoa.thuongHieu.tenThuongHieu)}</td>
                    <td>${viewField(item.hangHoa.nhomHang.tenNhomHang)}</td>
                    <td>${viewField(item.hangHoa.maVach)}</td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    tableHangHoa.html(view);
    $("#pageBody").LoadingOverlay("hide");
}

function btnImportFileExcel() {
    $("#btn-import-excel").click(function () {
        console.log("click import file excel");
        let {check: check, val: valFile} = checkFile($("#file-4"));
        if (check){
            $("#pageBody").LoadingOverlay("show");
            let files = $("#file-4").get(0).files;
            console.log(files);
            let formData  = new FormData();
            formData.append('file',files[0]);
            console.log(formData);
            ajaxUploadFile("v1/admin/hang-hoa/import",formData).then(rs=>{
                console.log(rs);
                if (rs.message==="success"){
                    $("#pageBody").LoadingOverlay("hide");
                    callSearchHangHoa(chiNhanhId);
                    $("#modal-chon-file").modal("toggle");
                    alterSuccess(rs.data);
                }
            })
        }
    })
}

//=================== Function ===================//
//tai excel va in thong tin Hàng Hóa
function taiExcelTK() {
    let arrExcel = [];
    $('#btn-3').on('click', function () {
        $("#pageBody").LoadingOverlay("show");
        let chiNhanhId = selectChiNhanh.val(), text = inputSearch.val();
        hangHoaSearch(chiNhanhId, text).then(rs => {
            arrExcel = rs.data.currentElements;
            console.log(arrExcel);
            let nguoiDungId = sessionStorage.getItem("id");
            ajaxGet(`v1/admin/chi-nhanh-hang-hoa/excel?nguoi-dung-id=${nguoiDungId}&list-hang-hoa=` + arrExcel.map(chiNhanhHangHoa => chiNhanhHangHoa.id))
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
        console.log(arrExcel);
    });
    clickPrintElement(".ttcttk");
}

