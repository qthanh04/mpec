<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="resources/dist/img/LogoBK.png"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/login/css/util.css">
    <link rel="stylesheet" type="text/css" href="resources/login/css/main.css">
    <%@include file="../library/library_css.jsp" %>
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <div class="login100-pic js-tilt" data-tilt>
                <img src="resources/dist/img/LogoBK.png" alt="IMG" width="60%">
            </div>

            <div class="login100-form validate-form">
					<span class="login100-form-title">
						Member Login
					</span>

                <div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
                    <input class="input100" type="text" name="username" placeholder="Username" id="username">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Password is required">
                    <input class="input100" type="password" name="pass" placeholder="Password" id="password">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
                </div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" id="btn-login">
                        Login
                    </button>
                </div>
                <a href="/tim-tai-khoan">
                    <div class="text-center p-t-12" id="btn-quen-mat-khau">
						<span class="txt1">
							Forgot Password?
						</span>

                    </div>
                </a>
                <div class="text-center p-t-136">
                    <a class="txt2" href="#">
                        Create your Account
                        <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!--===============================================================================================-->
<!--===============================================================================================-->
<script src="resources/login/vendor/bootstrap/js/popper.js"></script>
<script src="resources/login/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="resources/login/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="resources/login/vendor/tilt/tilt.jquery.min.js"></script>
<script>
    $('.js-tilt').tilt({
        scale: 1.1
    })
</script>
<!--===============================================================================================-->
<script src="resources/dist/js/ajax/ajax_main.js"></script>
<script src="resources/dist/js/dang_ky/login.js"></script>
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap-notify.min.css"/>
<script src="resources/plugins/bootstrap-notify.min.js"></script>
<script src="resources/bootstrap/js/bootstrap-notify.min.js"></script>
</body>
</html>