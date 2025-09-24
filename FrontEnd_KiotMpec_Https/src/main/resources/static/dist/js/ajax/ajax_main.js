var url_api = "http://localhost:8081/api/";
 // var url_api = "https://www.kiotmpec.xyz/api/";
var ss_lg; //token login
var user,nguoiDungId,chiNhanhId,chucvuId;
var MAX = 99999;
$(function () {
    ss_lg = localStorage.getItem("token");
    user = JSON.parse(localStorage.getItem("user"));
    nguoiDungId = localStorage.getItem("id");
    checkLogin();
    buttonBackHistory();
    viewDateVn();
    runSelect2();
    voice();
})

function checkLogin() {
    let pathName;
    pathName = window.location.pathname;
    if(pathName.indexOf("/dang-nhap") == -1) {
        if (pathName==="/"==false) {
            if (ss_lg == null && pathName==="tim-tai-khoan") {
                window.location.replace("/dang-nhap");
            }
        }
    }
}

//call api get v1/admin/
async function ajaxGet(url, token = ss_lg) {
    console.log(url);
    let rs = null;
     await $.ajax({
        type: 'GET',
        headers: {
            "Authorization": token
        },
        dataType: "json",
        url: url_api + url,
        timeout: 30000,
        success: function (result) {
            rs = result;
        }
    });
    return rs;
}


function ajaxGetNoAsync(url) {
    let rs = null;
    $.ajax({
        type: 'GET',
        headers: {
            "Authorization": ss_lg
        },
        dataType: "json",
        async : false,
        url: url_api + url,
        timeout: 30000,
        success: function (result) {
            rs = result;
        }
    })
    return rs;
}

//url link get api
async function ajaxPost(url, data ,option = 1) {
    let rs = null;
    console.log(url);
    await $.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        headers: {
            "Authorization": ss_lg
        },
        url: url_api + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result
        }
    });
    return rs;
}

async function ajaxPostGHN(url, data ,option = 1) {
    let rs = null;
    await $.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        headers: {
            "Authorization": ss_lg,
            "token": "2c6fb2a4-dc4c-11ea-9203-666d21bb7226",
            "shopId":1278093
        },
        url: url_api + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result
        }
    });
    return rs;
}
 async function ajaxPostGTK(url, data ,option = 1) {
     let rs = null;
     await $.ajax({
         type: 'POST',
         data: JSON.stringify(data),
         headers: {
             "token": "8D0250860Fcd64aA4393C6EfcCC7eFce98D70a91"
         },
         url: url_api + url,
         timeout: 30000,
         contentType: "application/json",
         success: function (result) {
             rs = result
         }
     });
     return rs;
 }

async function ajaxPut(url, data ,option = 1) {
    let rs = null;
    await $.ajax({
        type: 'PUT',
        data: JSON.stringify(data),
        headers: {
            "Authorization": ss_lg
        },
        url: url_api + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result
        }
    })
    return rs;
}

async function ajaxDelete(url, data ,option = 1) {
    let rs = null;
    await $.ajax({
        type: 'DELETE',
        data: JSON.stringify(data),
        headers: {
            "Authorization": ss_lg
        },
        url: url_api + url,
        timeout: 30000,
        contentType: "application/json",
        success: function (result) {
            rs = result
        }
    })
    return rs;
}

function callGetSelectize(selector,valueField,labelField,url) {
    selector.selectize({
        valueField: valueField,
        value: valueField,
        labelField: labelField,
        searchField: labelField,
        preload: true,
        options: [],
        create: true,
        load: function (query, callback) {
            // if (!query.length) return callback();
            $.ajax({
                url: url_api + url,
                type: 'GET',
                dataType: 'json',
                headers: {
                    "Authorization": ss_lg
                },
                data: {
                    text: query
                },
                error: function () {
                    callback();
                },
                success: function (rs) {
                    console.log(rs);
                    if (rs.message === "found") {
                        rs = rs.data.currentElements;
                        console.log(rs);
                        // data=rs.data.currentElements;
                        callback(rs);
                    }
                    //not found , goi ham them moi
                }
            });
        },
        onFocus: function () {
            // control has gained focus
        },
    });
}

async function ajaxUploadFile(url, file) {
    let rs = null;
    let urlBack = url_api;
    await $.ajax({
        type: "POST",
        headers: {
            "Authorization": ss_lg
        },
        url: urlBack + url,
        data: file,
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (result) {
            rs = result;
        }
    });
    return rs;
}

async function ajaxCall(url) {
    let rs = null;
    await $.ajax({
        type: 'GET',
        dataType: "json",
        url: url,
        timeout: 30000,
        success: function (result) {
            rs = result;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    })
    return rs;
}


//view Field
function viewField(data) {
    return data !== null ? data : "";
}

//view error
function viewError(selector, message) {
    selector.addClass("has-error");
    selector.find(".help-block").text(`${message}. Mời nhập lại!`);
}

//hidden error
function hiddenError(selector) {
    selector.removeClass("has-error");
}

//regex field
function removeAscent (str) {
    if (str === null || str === undefined) return str;
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    return str;
}

function regexTen(name) {
    return /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/g.test(removeAscent(name));
}

function regexEmail(email) {
    return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)
}

function regexDienThoai(soDienThoai) {
    return /((09|03|07|08|05)+([0-9]{8})\b)/g.test(soDienThoai)
}

function checkSize(size, text) {
    return text.length > 0 && text.length <= size;
}

function regexUsername(text) {
    return /^[A-Za-z0-9]+/.test(text);
}

function regexPassword(text) {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$^+=!*()@%&]).{8,10}$/.test(text);
}

function regexDate(text) {
    return /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/.test(text);
}
//end regex field

//set disable button save
function setDisabledButton(selector, check) {
    selector.prop('disabled', check);
}
//end set disable button save

//back-history
function buttonBackHistory() {
    $("#back-history").click(function () {
        window.history.back();
    })
}
//end-back-history

//format date iso backend
function formatDate(date) {
    if (date !== null) {
        let format = date.trim().split("-");
        console.log(format[2]+"/"+format[1]+"/"+format[0]);
        return format[2]+"/"+format[1]+"/"+format[0];
    }
}

function isValidDate(dateString) {
    var regEx = /^\d{4}-\d{2}-\d{2}$/;
    if(!dateString.match(regEx)) return false;  // Invalid format
    var d = new Date(dateString);
    var dNum = d.getTime();
    if(!dNum && dNum !== 0) return false; // NaN value, Invalid date
    return d.toISOString().slice(0,10) === dateString;
}

function compareDate(date1, date2) {
    return convertDateISO(date1).getTime() < convertDateISO(date2).getTime();
}

function convertDateISO(date) {
    date = date.split("/");
    date = date.reverse().join("/");
    return new Date(date);
}
//end format date

//alter
function alterImage(text) {
    $.notify({
        icon: 'resources/dist/img/Logo.png',
        title: 'Tavi MRS',
        message: text
    }, {
        delay: 3000,
        offset: {x: 15, y:15},
        icon_type: 'image',
        type: 'minimalist',
        template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
            '<img data-notify="icon" class="img-circle pull-left">' +
            '<div class="text-mess">' +
            '<span data-notify="title">{1}</span>' +
            '<span data-notify="message">{2}</span>' +
            '</div>' +
            '</div>'
    });
}

function alterSuccess(text) {
    $.notify({
        icon: 'far fa-check-circle',
        message: text
    }, {
        delay: 3000,
        offset: {x: 15, y: 15},
        type: 'success',
    });
}

function alterInfo(text) {
    $.notify({
        icon: 'fas fa-info-circle',
        message: text
    }, {
        delay: 3000,
        offset: {x: 15, y: 15},
        type: 'info',
    });
}

function alterWarning(text ) {
    $.notify({
        icon: 'fas fa-exclamation',
        message: text
    }, {
        delay: 3000,
        offset: {x: 15, y: 15},
        type: 'warning',
    });
}

function alterDanger(text) {
    $.notify({
        icon: 'fas fa-exclamation-triangle',
        message: text
    }, {
        delay: 3000,
        offset: {x: 15, y: 15},
        type: 'danger',
    });
}

function formatNumber(nStr, decSeperate, groupSeperate) {
    nStr += '';
    x = nStr.split(decSeperate);
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + groupSeperate + '$2');
    }
    return x1 + x2;
}

//end alter
function viewDateVn() {
    if ($(".date-vn").length > 0) {
        $(".date-vn").datepicker({
            language: "vi"
        });
    }
}

function viewDateTime(date) {
    // if(date !== null) date = new Date(date).toLocaleString();
    if(date !== null) date = moment(new Date(date)).format("DD/MM/YYYY HH:mm:ss");
    return date;
}

function viewSrc(src, check) {
    return src.indexOf("http") == 0 ? src : url_img + src;
}

function runOwlCarousel() {
    let selector = $(".owl-carousel");
    selector.owlCarousel({
        loop:true,
        dots:true,
        nav:true,
        margin:10,
        autoHeight:true,
        responsive:{
            0:{
                items:1
            }
        }
    });
    selector.on('mousewheel', '.owl-stage', function (e) {
        if (e.deltaY>0) {
            selector.trigger('next.owl');
        } else {
            selector.trigger('prev.owl');
        }
        e.preventDefault();
    });
}

function replaceOwlCarousel(html) {
    $('.owl-carousel').trigger('replace.owl.carousel', html).trigger('refresh.owl.carousel');
}

function viewNgayBaoCao(valTuNgay, valDenNgay) {
    let text = "";
    if (valTuNgay !== "")  text += `Từ ngày ${valTuNgay}`;
    if (valDenNgay !== "") text += ` đến ${valDenNgay}`;
    return text !== "" ? `(${text})` : "Từ trước đến nay";
}

function textToIconFile(nameFile) {
    if((/(.doc|.docx)$/ig).test(nameFile)) return '<i class="fas fa-file-word text-primary"></i>';
    if((/(xls|xlsx)$/ig).test(nameFile)) return '<i class="fas fa-file-excel text-success"></i>';
    if((/(ppt|pptx)$/ig).test(nameFile)) return '<i class="fas fa-file-powerpoint text-danger"></i>';
    if((/(zip|rar|tar|gzip|gz|7z)$/ig).test(nameFile)) return '<i class="fas fa-file-archive text-muted"></i>';
    if((/(htm|html)$/ig).test(nameFile)) return '<i class="fas fa-file-code text-info"></i>';
    if((/(txt|ini|csv|java|php|js|css)$/ig).test(nameFile)) return '<i class="fas fa-file-text text-info"></i>';
    if((/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/ig).test(nameFile)) return '<i class="fas fa-file-movie-o text-warning"></i>';
    if((/(mp3|wav)$/ig).test(nameFile)) return '<i class="fas fa-file-audio text-warning"></i>';
    if((/(.jpg|.png|.gif)$/ig).test(nameFile)) return '<i class="fas fa-file-image text-primary"></i>';
    if((/(.pdf)$/ig).test(nameFile)) return '<i class="fas fa-file-pdf text-danger"></i>';
    return '<i class="fas fa-file"></i>'
}

function clickPrintElement(selector) {
    $('#btn-print').on("click", function () {
        $(selector).printThis({
            importCSS: true,
            printDelay: 333,
        });
    });
}

function getMonthAndYear() {
    let date = new Date();
    return {
        month: date.getMonth() + 1,
        year: date.getFullYear()
    }
}

function drawChart(selector, options) {
    $(selector).CanvasJSChart(options);
}

function paginationReset() {
    $('#pagination').pagination({
        dataSource: [0],
        locator: 'items',
        totalNumber: 0,
        pageSize: 1,
        showPageNumbers: true,
        showPrevious: true,
        showNext: true,
        // showNavigator: true,
        showFirstOnEllipsisShow: true,
        showLastOnEllipsisShow: true
    })
}

function runSelect2() {
    $('.select2bs4').select2({ width: 'resolve' });
}

function loadEndData() {
    $("#click-load-data td").html(`<i class="fas fa-arrow-circle-up"></i>`);
    $("#click-load-data").unbind("click");
}

function resetIconLoadData() {
    $("#click-load-data td").html(`<i class="fas fa-arrow-circle-down"></i>`);
}

function paginationReset() {
    $('#pagination').pagination({
        dataSource: [0],
        locator: 'items',
        totalNumber: 0,
        pageSize: 1,
        showPageNumbers: true,
        showPrevious: true,
        showNext: true,
        // showNavigator: true,
        showFirstOnEllipsisShow: true,
        showLastOnEllipsisShow: true
    })
}

//=================== Function Validate ===================//
function checkText(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size,text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check : rs, val: text};
}

function checkTen(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size,text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check : rs, val: text};
}

//=================== Function ===================//
function checkSoDT(selector) {
    let rs = false;
    let size = 10;
    let ten = selector.val();
    let selectorParent = selector.parent();
    if (regexDienThoai(ten)) {
        if (checkSize(size,ten)) {
            rs = true;
            hiddenError(selectorParent);
        } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    } else viewError(selectorParent,"Số điện thoại chưa đúng định dạng");
    return {check : rs, val: ten};
}

//=================== Function ===================//
function checkSoDienthoai() {
    let rs = false;
    let size = 10;
    let ten = inputMoiDienThoai.val();
    let selector = inputMoiDienThoai.parent();
    if (regexDienThoai(ten)) {
        if (checkSize(size,ten)) {
            rs = true;
            hiddenError(selector);
        } else viewError(selector,`Độ dài chưa phù hợp > 0 và < ${size}`);
    } else viewError(selector,"Số điện thoại chưa đúng định dạng");
    return {check : rs, val: ten};
}

//=================== Function ===================//
function checkEmail() {
    let rs = false;
    let size = 100;
    let ten = inputMoiEmail.val();
    let selector = inputMoiEmail.parent();
    if (regexEmail(ten)) {
        if (checkSize(size,ten)) {
            rs = true;
            hiddenError(selector);
        } else viewError(selector,`Độ dài chưa phù hợp > 0 và < ${size}`);
    } else viewError(selector,"Email chưa đúng định dạng");
    return {check : rs, val: ten};
}

//=================== Function ===================//
function checkHoTen(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size,text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check : rs, val: text};
}


//=================== Function ===================//
function checkTaiKhoan(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size,text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check : rs, val: text};
}


//=================== Function ===================//
function checkSDT() {
    let rs = false;
    let size = 10;
    let ten = inputSDT.val();
    let selector = inputSDT.parent();
    if (regexDienThoai(ten)) {
        if (checkSize(size,ten)) {
            rs = true;
            hiddenError(selector);
        } else viewError(selector,`Độ dài chưa phù hợp > 0 và < ${size}`);
    } else viewError(selector,"Số điện thoại chưa đúng định dạng");
    return {check : rs, val: ten};
}


//=================== Function ===================//
function checkDiaChi(selector) {
    let rs = false;
    let size = 255;
    let text = selector.val();
    let selectorParent = selector.parent();
    if (checkSize(size,text)) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent,`Độ dài chưa phù hợp > 0 và < ${size}`);
    return {check : rs, val: text};
}

//=================== Function ===================//
function checkGT() {
    let rs = false;
    let size = 255;
    let val = $("input[name='gender']:checked").val();

    if (val==0 || val ==1) {
        rs = true;
        hiddenError(gTNam);
    } else if (val==undefined) {
        viewError(gTNam,`Chưa chọn giới tính`);}
    return {check : rs, val: val};
}

//=================== Function ===================//
function checkNS() {
    let rs = false;
    let ngaySinh = inputNgaySinh.val();
    console.log(isValidDate(ngaySinh));
    if (isValidDate(ngaySinh)) {
        rs = true;
        hiddenError(inputNgaySinh);
    } else viewError(inputNgaySinh,`Ngày không được để trống`);
    return {check : rs, val: ngaySinh};
}

function checkNS1(select) {
    let rs = false;
    let ngaySinh = select.val();
    console.log(isValidDate(ngaySinh));
    if (isValidDate(ngaySinh)) {
        rs = true;
        hiddenError(select);
    } else viewError(select,`Ngày không được để trống`);
    return {check : rs, val: ngaySinh};
}

//=================== Function ===================//
function checkTGT() {
    let rs = false;
    let tgt = inputTGKT.val();
    if (tgt == "" || isValidDate(tgt)) {
        rs = true;
        hiddenError(inputTGKT);
    } else viewError(inputTGKT,`Ngày không được để trống`);
    return {check : rs, val: tgt};
}

//=================== Function ===================//
function checkTGKH() {
    let rs = false;
    let tgkh = inputTGKH.val();
    if (tgkh == "" || isValidDate(tgkh)) {
        rs = true;
        hiddenError(inputTGKH);
    } else viewError(inputTGKH,`Ngày không được để trống`);
    return {check : rs, val: tgkh};
}

//=================== Function ===================//
function checkTGHH() {
    let rs = false;
    let tghh = inputTGHH.val();
    if (tghh == "" || isValidDate(tghh)) {
        rs = true;
        hiddenError(inputTGHH);
    } else viewError(inputTGHH,`Ngày không được để trống`);
    return {check : rs, val: tghh};
}

//=================== Function ===================//
function checkTT() {
    let rs = false;
    let val = $("input[name='status']:checked").val();
    if (val==0 || val ==1) {
        rs = true;
        hiddenError(kichHoat);
    } else if (val==undefined) {
        viewError(kichHoat,`Chưa chọn trạng thái`);}
    return {check : rs, val: val};
}

//=================== Function ===================//
function checkAnhDaiDien() {
    let rs = false;
    let val = $(anhDaiDien);
    let  len = val[0].files.length;
    if (len > 0) {
        rs = true;
        hiddenError(anhDaiDien);
    } else alterWarning(`Vui lòng chọn ảnh đại diện`);
    return {check : rs, val: val};
}


function checkFile(selector) {
    let rs = false;
    let selectorParent = selector.parents(".form-group");
    let val = selector;
    let lenFile = val[0].files.length;
    if (lenFile > 0) {
        rs = true;
        hiddenError(selectorParent);
    } else viewError(selectorParent, `Vui lòng chọn ít nhất 1 file`);
    return {check: rs, val: val};
}

//end validate
//==============voice==================
function voice() {
    var SpeechRecognition = window.webkitSpeechRecognition;
    var recognition = new SpeechRecognition();
    var Textbox = $('#bimo1');
    recognition.continuous = true;
    var check=0;
    recognition.onresult = function(event) {
        var current = event.resultIndex;
        var trancript = event.results[current][0].transcript;
        Textbox.val(trancript);
    };
    recognition.onerror = function(event) {
        if(event.error == 'no-speech') {
            alterWarning("Try again.");
        }
    }

    $('#start-btn').on('click', function(e) {
        if (check==0){
            recognition.start();
            check=1;
            $('#start-btn').removeClass("fa-microphone-slash");
            $('#start-btn').addClass("fa-microphone");
        }else if (check==1){
            recognition.stop();
            check=0;
            $('#start-btn').removeClass("fa-microphone");
            $('#start-btn').addClass("fa-microphone-slash");
        }
    });
}

function formatMoney(n) {
    let n2= n.toString();
    return n2.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");  // format number 1 000 000 to 1,234,567
}

function valTien(n){
    // let n2=n.toString();
    if(n !== undefined){
        return n.replaceAll(',','');  //format 1,234,567 = > 1 000000
    }else {
        return 0;
    }
}

//keyup
function formatCurrency(input) {
    var input_val = input.val();
    if (input_val === "") { return; }
    input_val = formatMoney(input_val);
    input.val(input_val);
}


