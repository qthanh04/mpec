//================ Declare variable===================//
var btnEditBranch, btnRemoveBranch, btnSyncBranch, btnAddBranch, btnConfirmYes, dataTableTemplate;
var selectedChiNhanh = null, selectChiNhanh;
var Trang_Thai_Chi_Nhanh = ["Không hoạt động", "Đang hoạt động"];

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    selectChiNhanh = $("#select-1");
    btnEditBranch = $("#btnEditBranch");
    btnSyncBranch = $("#btnSyncBranch");
    btnRemoveBranch = $("#btnRemoveBranch");
    btnConfirmYes = $("#confirm-yes");
    btnAddBranch = $("#btn-addBranch");
    btnSearch = $("#btn-1");
    inputSearch = $("#bimo1");
    dataTableTemplate = $("#data-table");

    //==========Function constructor========//


    loadBranchesData(1, 1, 10);
    confirmUploadBranch();
    confirmRemoveBranch();
    confirmUpdateBranch();
    clickEditEvent();
    clickRemoveEvent();
    clickSearch();
});

//=============== Function detail ===================//
function viewTrangThaiChiNhanh(chiNhanh) {
    return Trang_Thai_Chi_Nhanh[chiNhanh.trangThaiHoatDong];
}

//=================== Function ===================//
function clickEditEvent(){
        $(document).on("click", ".editBranchInfoBtn", function (e) {
        let idChiNhanh = $(this).attr('id-branch');
        selectedChiNhanh = idChiNhanh;
        console.log("vaoo day"+idChiNhanh);
        console.log(selectedChiNhanh);
        loadExistBranchDataFromTableToModal(selectedChiNhanh);
    })
}

//=================== Function ===================//
function clickRemoveEvent() {
    $(document).on("click", ".deleteBranchInfoBtn", function (e) {
        let idChiNhanh = $(this).attr('id-branch');
        selectedChiNhanh = idChiNhanh;
    })
}

//=================== Function ===================//
function confirmUploadBranch() {
    $("#btn-confirmedBranch").click(function () {
        let {check: checkDC, val: valDC} = checkTen($("#input-text-addressBranch"));
        let {check: checkIP, val: valIP} = checkTen($("#input-text-ipAddressBranch"));
        if (checkIP && checkDC) {
            $("#pageBody").LoadingOverlay("show");
            let address = $("#input-text-addressBranch").val();
            let connectiveStt = $("#connectiveStatusSelect").children("option:selected").val();
            let activeStt = $("#activeStatusSelect").children("option:selected").val();
            let ipAddress = $("#input-text-ipAddressBranch").val();
            let chiNhanh = {};
            chiNhanh.diaChi = address;
            chiNhanh.trangThaiKetNoi = parseInt(connectiveStt);
            chiNhanh.trangThaiHoatDong = parseInt(activeStt);
            chiNhanh.diaChiIp = ipAddress;
            uploadBranch(chiNhanh, 1).then(r => {
                $("#pageBody").LoadingOverlay("hide");
                alterSuccess("Thêm chi nhánh thành công")
                $("#modal-1").modal('toggle');
                loadBranchesData(1)
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server Error!");
            });

        }
    });
}

//=================== Function ===================//
function confirmRemoveBranch() {
    btnConfirmYes.click(function () {
        if (selectedChiNhanh !== 0) {
            $("#pageBody").LoadingOverlay("show");
            deleteBranch(selectedChiNhanh, 1).then(r => {
                $("#pageBody").LoadingOverlay("hide");
                alterSuccess("Xoá chi nhánh thành công");
                loadBranchesData(1);
            }).catch(err => {
                console.log(err);
                alterDanger("Server Error!");
            });
        }
    });
}

//=================== Function ===================//
function loadBranchesData(idCompany, page = 1, size = 10) {
    $("#pageBody").LoadingOverlay("show");
    findAllBranchesByIdCompany(idCompany, 1, 10).then(rs => {
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
                        console.log(arr);
                        setViewChiNhanh(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    findAllBranchesByIdCompany(1, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        console.log(arr);
                        setViewChiNhanh(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        }
    })
}

function setViewChiNhanh(pageNumber) {
    let view = `<tr>
                <th>STT</th>
                <th>Mã chi nhánh</th>
                <th>Vị trí</th>
                <th>Địa chỉ IP</th>
                <th>Trạng thái</th>
                <th>Chức năng</th>
                </tr>`;
    let len = arr.length;
    if (len > 0) {
        view += arr.map((item, index) => `<tr data-index="${index}" class="click-search">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>  
                    <td>${viewField(item.maChiNhanh)}</td>
                    <td>${viewField(item.diaChi)}</td>
                    <td>${viewField(item.diaChiIp)}</td>
                    <td>${viewTrangThaiChiNhanh(item)}</td>
                    <td id="${item.id}">
                                        <button class="editBranchInfoBtn" style="background: transparent;border: none; margin-right: 12px;" id-branch="${item.id}" id="btnEditBranch-${item.id}" data-toggle="modal" data-target = "#modal-editBranch"><i class="fas fa-pen"></i></button>                               
                                        <button class="deleteBranchInfoBtn" style="background: transparent;border: none;" id-branch="${item.id}" id="btnRemoveBranch-${item.id}" data-toggle="modal" data-target="#modal-remove"><i class="fas fa-trash"></i></button>
                                    </td>
                </tr>`);
        if (len < 10) {
            len++;
            for (let i = len; i <= 10; i++) {
                view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td>><td></td><td></td><td></td><td></td></tr>`
            }
        }
    } else {
        view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
    }
    $("#table").html(view);
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function confirmUpdateBranch() {
    $("#btn-confirmedEditedBranch").click(function () {
        let {check: checkDC, val: valDC} = checkTen($("#input-text-addressEditedBranch"));
        let {check: checkIP, val: valIP} = checkTen($("#input-text-ipAddressEditedBranch"));
        if (checkIP && checkDC) {
            $("#pageBody").LoadingOverlay("show");
            let address = $("#input-text-addressEditedBranch").val();
            let ipAddress = $("#input-text-ipAddressEditedBranch").val();
            let connectiveStt = $("#connectiveStatusEditedBranchSelect").children("option:selected").val();
            let activeStt = $("#activeStatusEditedBranchSelect").children("option:selected").val();
            let chiNhanh = {};
            chiNhanh.id = selectedChiNhanh;
            chiNhanh.diaChi = address;
            chiNhanh.trangThaiKetNoi = parseInt(connectiveStt);
            chiNhanh.trangThaiHoatDong = parseInt(activeStt);
            chiNhanh.diaChiIp = ipAddress;
            console.log(chiNhanh);
            updateBranch(chiNhanh, 1).then(r => {
                $("#pageBody").LoadingOverlay("hide");
                alterSuccess("Sửa chi nhánh thành công");
                $("#modal-editBranch").modal('toggle');
                loadBranchesData(1);
            }).catch(err => {
                console.log(err);
                $("#pageBody").LoadingOverlay("hide");
                alterDanger("Server error!!");
            });
        }
    });
}

function checkTen(selector) {
    let rs = false;
    let size = 255;
    let ten = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size, ten)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check: rs, val: ten};
}

//=================== Function ===================//
function loadExistBranchDataFromTableToModal(idBranch) {
    findBranchById(idBranch).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            $("#input-text-addressEditedBranch").val(rs.diaChi);
            $("#input-text-ipAddressEditedBranch").val(rs.diaChiIp);
        }
    }).catch(er => console.log(er));
}

//=================== Function ===================//
function clickSearch() {
    btnSearch.click(function () {
        let text = inputSearch.val();
        callSearch(text);
    })
}

//=================== Function ===================//
function callSearch(text) {
    $("#pageBody").LoadingOverlay("show");
    chiNhanhSelectSearch(text, 1, 10).then(rs => {
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
                        console.log(arr);
                        setViewChiNhanh(1);
                        return;
                    }
                    // console.log(pagination.pageNumber); // khi click sẽ đọc ra số trang click
                    chiNhanhSelectSearch(text, pagination.pageNumber, 10).then(rs => {
                        arr = rs.data.currentElements;
                        console.log(arr);
                        setViewChiNhanh(pagination.pageNumber);
                    }).catch(err => console.log(err))
                }
            })
        }
    })
}

//==============voice==================
var SpeechRecognition = window.webkitSpeechRecognition;
var recognition = new SpeechRecognition();
var Textbox = $('#bimo1');
recognition.continuous = true;
var check = 0;
recognition.onresult = function (event) {
    var current = event.resultIndex;
    var trancript = event.results[current][0].transcript;
    Textbox.val(trancript);
};

// recognition.onstart = function() {
//     alert("Mic đã bật ");
// }
// recognition.onspeechend = function() {
//     instructions.text('No activity.');
// }

recognition.onerror = function (event) {
    if (event.error == 'no-speech') {
        alterWarning("Try again.");
    }
}

$('#start-btn').on('click', function (e) {
    // if (recognition.onstart){
    if (check == 0) {
        alterWarning("Mic đã bật ");
        recognition.start();
        check = 1;
    } else if (check == 1) {
        alterWarning("Mic đã tắt ");
        recognition.stop();
        check = 0;
    }

    // }
    // if (Content.length) {
    //     Content += ' ';
    //

});


