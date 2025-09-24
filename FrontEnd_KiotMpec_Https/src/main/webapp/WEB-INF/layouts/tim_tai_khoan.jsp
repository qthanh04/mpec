<%--
  Created by IntelliJ IDEA.
  User: QUY
  Date: 26/03/2020
  Time: 9:22 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="resources/dist/css/dang_nhap/tim_tai_khoan.css" type="text/css">
    <link rel="stylesheet" href="resources/dist/css/dang_nhap/dat_lai_mat_khau.css" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>
<div class="container " id="1">
    <div class="row justify-content-center" id="displayshow">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header"><h2>Tìm tài khoản</h2></div>
                <div class="card-body">
                    <span for="email_address" class="col-md-4 col-form-label text-md-right">Vui lòng nhập sô điện thoại hoặc email để tìm kiếm</span>
                    <form action="" method="">
                        <div class="form-group row">
                            <p for="email_address" class="col-md-12  text-md-right"></p>
                            <div class="col-md-8">
                                <input type="text" id="email_address" class="form-control" name="email-address"
                                       placeholder="Nhap email cua ban" required autofocus>
                            </div>

                            <p for="email_address" class="col-md-12 red_background" id="canhBao">
                                <strong>Không có kết quả tìm kiếm</strong>
                                <br>
                                <var> Tìm kiếm không trả về kết quả nào. Tài khoản của bạn có thể không tồn tại, vui
                                    lòng thử lại với thông tin khác.</var>
                            </p>
                        </div>
                        <hr>

                        <div class="col-bg-4 float-right">
                            <button type="button" class="btn btn-primary" id="btn-tim-tai-khoan">
                                Tìm kiếm
                            </button>
                            <button type="button" class="btn btn-secondary" id="btn-huy-1">
                                Hủy
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center" id="displaynone">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header"><h2>Đặt lại mật khẩu</h2></div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <div>
                                <span>Bạn muốn nhận mã để đặt lại mật khẩu bằng cách nào</span>
                                <div class="radio">
                                    <input type="radio" name="radio1" id="radio1" value="option1" checked="">
                                    <label for="radio1">
                                        Gửi mã qua email
                                    </label>
                                    <p class="email">

                                    </p>
                                </div>
                                <div class="radio">
                                    <input type="radio" name="radio1" id="radio2" value="option2">
                                    <label for="radio2">
                                        Gửi qua sms
                                    </label>
                                    <p>
                                        0894****064
                                    </p>
                                </div>
                            </div>

                        </div>
                        <div class="col-md-4 center-block v-divider" id="ava-img">

                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <button type="button" class="btn  ">
                                Gửi lại mã
                            </button>
                        </div>
                        <div class="col text-right">
                            <button type="button" class="btn btn-primary" id="guiEmail">
                                Tìm kiếm
                            </button>
                            <button type="button" class="btn btn-secondary" id="btn-huy-2">
                                Hủy
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div class="row justify-content-center" id="displaynone2">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header"><h2>Nhập mã bảo mật</h2></div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <div>
                                <p>Chúng tôi đã gửi cho bạn qua email hoặc số điện thoại!</p>
                                <p>Vui lòng kiểm tra mã trong email hoặc số điện thoại của bạn. Mã này gồm 6 số.</p>
                                <input placeholder="Nhập mã" id="maBaoMat"/>
                            </div>
                        </div>
                        <div class="border border-warning mt-3 p-3" id="canhBao2">
                            <span class="glyphicon glyphicon-warning-sign"><img src="./dist/img/warning.png"></span>
                            Cảnh báo lỗi về mã bảo mật... Vui lòng kiểm tra mã của bạn và thử lại
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col text-right">
                            <button type="button" class="btn btn-primary" id="btn-maBaoMat">
                                Tìm kiếm
                            </button>
                            <button type="button" class="btn btn-secondary" id="btn-huy-3">
                                Hủy
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center" id="displaynone3">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header"><h2>Chọn mật khẩu mới</h2></div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <div>
                                <p>Tạo mật khẩu mới có tối thiểu 6 ký tự. Mật khẩu mạnh là mật khẩu được kết hợp từ các
                                    ký tự, số và dấu câu.</p>

                                <input placeholder="Nhập mật khẩu mới" id="newPass"/>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col text-right">
                            <button type="button" class="btn btn-primary" id="matKhauMoi">
                                Tìm kiếm
                            </button>
                            <button type="button" class="btn btn-secondary" id="btn-huy-4">
                                Hủy
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center" id="displaynone4">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header"><h2>Chọn mật khẩu mới</h2></div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <div>
                                <p>Mật khẩu của bạn đã được thay đổi thành công. Nhấn nút đăng nhập để quay lại trang
                                    đăng nhập.</p>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col text-right">
                            <button type="button" class="btn btn-primary" id="btn-dang-nhap">
                                Dang nhap
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="resources/dist/js/dang_ky/tim_tai_khoan.js"></script>
<script src="resources/dist/js/ajax/ajax_main.js"></script>
<!-- Optional JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.8/js/select2.min.js" defer></script>
<script src="resources/plugins/bs-custom-file-input/bs-custom-file-input.js"></script>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

</body>
</html>