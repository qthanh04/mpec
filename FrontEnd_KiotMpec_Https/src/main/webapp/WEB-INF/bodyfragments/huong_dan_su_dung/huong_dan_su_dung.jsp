<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<link rel="stylesheet" href="resources/plugins/assets/vendor/bootstrap/css/bootstrap.min.css">--%>
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="resources/plugins/assets/vendor/fonts/circular-std/style.css" rel="stylesheet">
<link rel="stylesheet" href="resources/plugins/assets/libs/css/style.css">
<link rel="stylesheet" href="resources/plugins/assets/vendor/fonts/fontawesome/css/fontawesome-all.css">

<div class="cd-timeline__container">
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--picture js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-picture.svg" alt="Picture">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý hàng hóa</h3>
            <p>Để bán hàng bạn đầu tiên sẽ phải thiết lập hàng hóa với các thông tin cơ bản như tên hàng, nhóm hàng,
                thương hiệu, đơn vị. Bạn có thể upload ảnh của sản phẩm </p>
            <a href="danh-sach-hang-hoa" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--movie js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-movie.svg" alt="Movie">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Nhập hàng</h3>
            <p>Sau khi có thông tin hàng hóa, bạn sẽ nhập hàng vào kho để bán. Ở đây bạn có thể thêm mới nhà cung cấp
                sản phẩm hoặc chọn nhà cung cấp có sẵn. Bạn sẽ chọn các sản phẩm đã thêm mới ở trên. Bạn có thể thanh
                toán và in hóa đơn</p>
            <a href="danh-sach-nhap-hang" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--picture js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-picture.svg" alt="Picture">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Thiết lập giá</h3>
            <p>Sau khi nhập hàng, bạn có thể thiết lập giá cho từng đơn vị. Vì 1 hàng hóa của bạn có thể bán với nhiều
                đơn vị khác nhau với giá khác nhau</p>
            <a href="thiet-lap-gia" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Bán hàng</h3>
            <p>Bạn có thể tạo hóa đơn bán hàng, bạn sẽ chọn hàng từ kho đã được thiết lập giá để bán, bạn sẽ chọn đơn vị
                và số lượng. Ở đây bạn có thể thêm mới khách hàng hoặc chọn khách hàng có sẵn mua ở lần trước. Sau khi
                nhấn nút thanh toán bạn có thể in hóa đơn, hệ thống sẽ tự động tính tổng số tiền , gửi mail hóa đơn cho
                khách hàng.Bạn cũng có thể quản lý thông tin của các hóa đơn cũ </p>
            <a href="danh-sach-hoa-don" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->

    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Trả hàng cho khách</h3>
            <p>Bạn có thể tạo hóa đơn trả hàng cho khách do khách yêu cầu, bạn sẽ chọn hóa đơn mà khách hàng yêu cầu
                trả, sau đó chỉnh sửa số lượng cần trả. Sau khi nhấn nút thanh toán bạn có thể in hóa đơn, hệ thống sẽ
                tự động tính tổng số tiền , gửi mail hóa đơn cho khách hàng . Bạn cũng có thể quản lý thông tin của các
                hóa đơn trả hàng cho khách cũ </p>
            <a href="danh-sach-hoa-don-de-tra" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->

    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Trả hàng cho nhà cung cấp</h3>
            <p>Bạn có thể tạo hóa đơn trả hàng cho nhà cung cấp, bạn sẽ chọn hóa nhập hàng, sau đó chỉnh sửa số lượng
                cần trả. Sau khi nhấn nút thanh toán bạn có thể in hóa đơn, hệ thống sẽ tự động tính tổng số tiền , gửi
                mail hóa đơn cho khách hàng . Bạn cũng có thể quản lý thông tin của các hóa đơn trả hàng cho nhà cung
                cấp cũ </p>
            <a href="danh-sach-hoa-don-de-tra" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->

    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý chi nhánh</h3>
            <p> Khi bạn kinh doanh bạn sẽ có nhiều chi nhánh ở mọi nơi, bạn sẽ thêm thông tin các chi nhánh con của bạn,
                khi ở các chi nhánh con bạn sẽ đăng nhập với tài khoản của user của chi nhánh đó</p>
            <a href="quan-ly-chi-nhanh" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>

    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý nhân viên</h3>
            <p>Bạn có thể quản lý được tất cả thông tin nhân viên của bạn ở chi nhánh nào, chức năng, vai trò là gì,
                phân quyền cho nhân viên </p>
            <a href="thong-tin-nhan-vien" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý file</h3>
            <p> Tát cả các file phiếu hóa đơn, nhập hang, trả hàng, bán hàng sẽ được lưu trữ ở đây. Bạn có thể tìm kiếm
                lại các file đó </p>
            <a href="quan-ly-file" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--location js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-location.svg" alt="Location">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý khách hàng</h3>
            <p> Bạn có thể thêm, sửa, xóa, tìm kiếm thông tin của khách hàng đã mua hàng tại cửa hàng </p>
            <a href="danh-sach-khach-hang" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->

    <div class="cd-timeline__block js-cd-block">
        <div class="cd-timeline__img cd-timeline__img--movie js-cd-img">
            <img src="resources/plugins/assets/vendor/timeline/img/cd-icon-movie.svg" alt="Movie">
        </div>
        <!-- cd-timeline__img -->
        <div class="cd-timeline__content js-cd-content">
            <h3>Quản lý nhà cung cấp</h3>
            <p> Bạn có thể thêm, sửa, xóa, tìm kiếm thông tin của nhà cung cấp đã hợp tác với bạn </p>
            <a href="danh-sach-nha-cung-cap" class="btn btn-primary btn-lg">Read More</a>
        </div>
        <!-- cd-timeline__content -->
    </div>
    <!-- cd-timeline__block -->
</div>

<script src="resources/plugins/assets/vendor/bootstrap/js/bootstrap.bundle.js"></script>
<script src="resources/plugins/assets/vendor/slimscroll/jquery.slimscroll.js"></script>
<script src="resources/plugins/assets/libs/js/main-js.js"></script>
<script src="resources/plugins/assets/vendor/timeline/js/main.js"></script>
