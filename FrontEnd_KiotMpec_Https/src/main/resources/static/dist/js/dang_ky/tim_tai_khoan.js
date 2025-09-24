var local_api= "http://localhost:8282/";

//-------------Cac ham ajax--------//

async function findByEmail(email_address) {
    return ajaxGet(`v1/public/user/find-nguoi-dung-by-email?email=${email_address}`);
}
async function sendMail(email_address) {
    return ajaxPut(`v1/public/user/tim-tai-khoan?email=${email_address}`);
}

async function checkMaBaoMat(maBaoMat , idNguoiDung) {
    return ajaxGet(`v1/public/user/tim-tai-khoan?ma-bao-mat=${maBaoMat}&nguoi-dung-id=${idNguoiDung}`);
}

async function creatNewPass(doiMK) {
    return ajaxPost(`v1/public/user/doi-mat-khau`,doiMK);
}


//----------het cac ham ajax----------//

var email_address;
$(function () {
    $( "#btn-tim-tai-khoan" ).click(function() {
        email_address = $("#email_address").val();
        console.log("he" +email_address);
            if(email_address===""){
            console.log("ha" +email_address);
            alert("Vui lòng nhập email")
        }
        else {
            setDataUser(email_address);
            console.log("hi" +email_address);
            // findByEmail(email_adress);
        }
    });

    $( "#btn-huy-1" ).click(function() {

        console.log("huy");
        window.location.href= local_api + "dang-nhap";
    });

    $( "#btn-huy-2" ).click(function() {
        $( "#displayshow" ).toggle();
        $( "#displaynone" ).toggle();
    });

    $( "#btn-huy-3" ).click(function() {
        $( "#displaynone" ).toggle();
        $( "#displaynone2" ).toggle();
    });
    $( "#btn-huy-4" ).click(function() {
        $( "#displaynone2" ).toggle();
        $( "#displaynone3" ).toggle();
    });

    $( "#btn-dang-nhap" ).click(function() {
        window.location.href=local_api + "dang-nhap";
    });

})

var idNguoiDung;
function setDataUser(email_address) {
    findByEmail(email_address).then(result => {
        console.log(result.data);
        let emailViews =``;
        let nameViews =``;

        if(result.message=="found"){
            console.log(result.message + "vaoday");
            idNguoiDung = result.data.id;
            let email= result.data.email;
            let emailSplit=email.substr(0,4) + "******"+ email.substr(email.indexOf('@'),email.length)
            emailViews =`<p class="email">
                   ${emailSplit}
                </p>`;
            nameViews=`<p class="text-center name">${result.data.hoVaTen}</p>`
            $( "#displayshow" ).toggle();
            $( "#displaynone" ).toggle();
            $(".email").html(emailViews);
            $(".name").html(nameViews);
            $("#ava-img").html(`<img src="${result.data.urlAnhDaiDien}" width="70%">`);
        }
    }).catch(err => {
        console.log("k thay mail");
        alert("Không tìm thấy email");
        $( "#canhBao" ).toggle();
        console.log(err);
    })
}

$(function () {
    $( "#guiEmail" ).click(function() {
        sendMail(email_address);
        $( "#displaynone" ).toggle();
        $( "#displaynone2" ).toggle();
    });
})

var maBaoMat;
$(function () {
    $( "#btn-maBaoMat" ).click(function() {
        maBaoMat = $("#maBaoMat").val();
        console.log(idNguoiDung);
        checkMaBaoMat(maBaoMat, idNguoiDung).then(result =>{
            if (result.message ==="success"){
                $( "#displaynone2" ).toggle();
                $( "#displaynone3" ).toggle();
            }
        }).catch(err => {
            $( "#canhBao2" ).toggle();
            console.log("loi"+ err);
        })
    });
})


var newPass;
$( "#matKhauMoi" ).click(function() {
    newPass = $("#newPass").val();
    console.log(regexPassword(newPass));
    if(regexPassword(newPass)){

        let doiMK = {
            matKhau: newPass,
            email:email_address
        }
        console.log(doiMK)
        creatNewPass(doiMK).then(result =>{
            if (result.message ==="updated"){
                $( "#displaynone3" ).toggle();
                $( "#displaynone4" ).toggle();
            }
        });
    }
    else {
        alert("Nhập mật khẩu chưa đúng định dạng  ");
    }
});




