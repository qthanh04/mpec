var front_end_api = "api/v1/session/";
async function ajaxLogin(data){
    return ajaxPost(  "v1/public/user/login",data);
}

function findByTaiKhoan(taiKhoan) {
    return ajaxGet(`v1/public/user/find-nguoi-dung-by-tai-khoan?tai-khoan=${taiKhoan}`,1);
}

async function findNguoiDungPhongBanByNhanVienId(id=0){
    return ajaxGet(`v1/admin/nguoi-dung-phong-ban-chuc-vu-vai-tro/find-by-nguoi-dung-id?nguoi-dung-id=${id}`)
}

async function findNguoiDungPhongBanByTaiKhoan(taiKhoan,token){
    return ajaxGet(`v1/admin/nguoi-dung-phong-ban-chuc-vu-vai-tro/find-by-nguoi-dung-tai-khoan?tai-khoan=${taiKhoan}`,token)
}

async function ajaxGetUrl(option) {
    let rs = null;
    let url = option == 1 ? "get-url" : "clear-url"
    await $.ajax({
        type: 'GET',
        url: front_end_api +  url,
        timeout: 60000,
        success: function (result) {
            rs = result;
        }
    })
    return rs;
}

function checkToken(){
    let token = sessionStorage.getItem("token");
    let pathName = window.location.pathname;
    if(token == null){
        if(pathName.indexOf("/dang-nhap") == -1) {
            console.log("redirect");
            window.location.replace("/dang-nhap");
        }
    }
}

$(document).ready(function () {
    checkToken();
    submitLogin();
    //timTaiKhoan();
});

function submitLogin(){
    $('#btn-login').click(function (){
        let username=$('#username').val();
        let password=$('#password').val();
        if(username===null || password===null){
            alterWarning("vui lòng nhập đầy đủ username và password!",3000);
            return;
        }
        else if(password.length<8){
            alterDanger("password nhập thiếu kí tự",3000);
            return;
        }
        let LoginForm={
            username:username,
            password:password
        }
         ajaxLogin(LoginForm).then(rs =>{
            if(rs.message == "login success"){
                 ajaxGetUrl(1).then(rs1 => {
                    ajaxGetUrl(2).then(() => {
                        findNguoiDungPhongBanByTaiKhoan(username,rs.data).then(rs2 =>{
                            console.log(rs2);
                            let user={
                                nguoiDungid : rs2.data.nguoiDung.id,
                                hoVaTen : rs2.data.nguoiDung.hoVaTen,
                                urlAnhDaiDien : rs2.data.nguoiDung.urlAnhDaiDien,
                                chinhanhid: rs2.data.chiNhanh.id,
                                diachicn: rs2.data.chiNhanh.diaChi,
                                vaitro: rs2.data.vaiTro.tenVaiTro,
                                chucvu:rs2.data.chucVu.tenChucVu,
                                chucvuid:rs2.data.chucVu.id
                            }
                            sessionStorage.setItem("token",rs.data);
                            sessionStorage.setItem("id",rs2.data.nguoiDung.id);
                            sessionStorage.setItem("user",JSON.stringify(user));

                            localStorage.setItem("token",rs.data);
                            localStorage.setItem("id",rs2.data.nguoiDung.id);
                            localStorage.setItem("user",JSON.stringify(user));

                            document.cookie = `token=${rs.data}`;
                            location.href = rs1;


                        });
                    });
                });
            }else if (rs.message=="login fail") {
                alterDanger("Username hoặc Password không đúng, vui lòng kiểm tra lại",3000);
            }
        }).catch(er=>{
            console.log(er);
            $('.login-box-msg').html('Username hoặc Password không đúng, vui lòng kiểm tra lại');
            $('.login-box-msg').css('color','red');
        });
    });
}




