<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="resources/pages/thong_tin_header/ajax_header.js"></script>
<script src="resources/model/nhan_vien/ajax_nhan_vien.js"></script>
<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo"><b>KIOTMPEC</b></a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-fixed-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
    </nav>
</header>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel" style="margin-top:-20px;">
            <div class="pull-left image">
                <%--                <img id="avatar" src="./dist/img/imgquy.png" class="img-circle" alt="User Image" />--%>
                <img id="avatar" class="img-circle" alt="User Image"/>
            </div>
            <div class="pull-left info">
                <p id="name-emp" style="font-size: 14px;">Nguyễn Đức Quý</p>
                <a href="#"><i class="fa fa-circle text-success"></i> <span id="role"
                                                                            style="font-size: 14px;">Nhân viên</span></a>
            </div>
        </div>

        <hr>
<%--         <form action="#" method="get" class="sidebar-form">--%>
<%--            <div class="input-group">--%>
<%--                <input type="text" name="q" class="form-control" placeholder="Search..." />--%>
<%--                <span class="input-group-btn">--%>
<%--                    <button type='submit' name='search' id='search-btn' class="btn btn-flat"><i--%>
<%--                            class="fa fa-search"></i></button>--%>
<%--                </span>--%>
<%--            </div>--%>
<%--        </form>--%>

        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-dashboard"></i>
                    <span>Tổng quan</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="tong-quan"><i class="fa fa-circle-o"></i> Báo cáo</a></li>
                    <li><a href="nhat-ki-hoat-dong"><i class="fa fa-circle-o"></i>Nhật kí hoạt động</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-product-hunt"></i>
                    <span>Quản lý hàng hóa</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="danh-sach-hang-hoa"><i class="fa fa-circle-o"></i> Danh sách hàng hóa</a></li>
                    <li><a href="danh-muc-hang-hoa"><i class="fa fa-circle-o"></i> Danh mục</a></li>
                    <li><a href="thiet-lap-gia"><i class="fa fa-circle-o"></i>Thiết lập giá</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-send"></i>
                    <span>Bán hàng</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="chi-tiet-hoa-don?id=0" target="_blank"><i class="fa fa-circle-o"></i> Tạo hóa đơn bán hàng</a></li>
                    <li><a href="danh-sach-hoa-don"><i class="fa fa-circle-o"></i> Danh sách hóa đơn</a></li>
                    <li><a href="quan-ly-giao-hang"><i class="fa fa-circle-o"></i> Quản lý giao hàng</a></li>
                    <li><a href="chi-tiet-van-chuyen"><i class="fa fa-circle-o"></i> Chi tiết vận chuyển</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-file-import"></i>
                    <span>Nhập hàng</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="chi-tiet-nhap-hang?id=0" target="_blank"><i class="fa fa-circle-o"></i> Tạo hóa đơn nhập hàng</a></li>
                    <li><a href="danh-sach-nhap-hang"><i class="fa fa-circle-o"></i> Danh sách nhập hàng</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-refresh"></i>
                    <span>Trả hàng</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="danh-sach-hoa-don-de-tra"><i class="fa fa-circle-o"></i> Trả hàng cho khách</a></li>
                    <li><a href="danh-sach-phieu-nhap-de-tra"><i class="fa fa-circle-o"></i> Trả hàng cho nhà cung
                        cấp</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fas fa-dollar-sign" style="width: 20px;"></i>
                    <span>Sổ qũy</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="phieu-thu"><i class="fa fa-circle-o"></i> Phiếu thu</a></li>
                    <li><a href="phieu-chi"><i class="fa fa-circle-o"></i> Phiếu chi</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-handshake-o"></i>
                    <span>Quản lý đối tác</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="danh-sach-khach-hang"><i class="fa fa-circle-o"></i>Khách hàng</a></li>
                    <li><a href="danh-sach-nha-cung-cap"><i class="fa fa-circle-o"></i>Nhà cung cấp</a></li>

                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-user"></i>
                    <span>Quản lý nhân viên</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="thong-tin-nhan-vien"><i class="fa fa-circle-o"></i>Nhân viên</a></li>
                    <li><a href="danh-muc-nhan-vien"><i class="fa fa-circle-o"></i>Danh mục nhân viên</a></li>
                    <li><a href="ca-lam-viec"><i class="fa fa-circle-o"></i>Ca làm việc</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-home"></i>
                    <span>Quản lý doanh nghiệp</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="thong-tin-cong-ty"><i class="fa fa-circle-o"></i> Thông tin công ty</a></li>
                    <li><a href="quan-ly-chi-nhanh"><i class="fa fa-circle-o"></i> Danh sách chi nhánh</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-bell"></i>
                    <span>Thông báo - Báo cáo</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="thong-bao"><i class="fa fa-circle-o"></i>Gửi thông báo</a></li>
                    <li><a href="quan-ly-thong-bao"><i class="fa fa-circle-o"></i>Quản lý thông báo</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="quan-ly-file">
                    <i class="fa fa-files-o"></i>
                    <span>Quản lý file </span>
                </a>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fa fa-files-o"></i>
                    <span>Công nợ</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/cong-no"><i class="fa fa-circle-o"></i>Khách hàng</a></li>
                    <li><a href="/cong-no-nha-cung-cap"><i class="fa fa-circle-o"></i>Nhà cung cấp</a></li>
                </ul>
            </li>


            <li class="treeview">
                <a href="#">
                    <i class="fa fa-video"></i>
                    <span>Chat - Video</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="chat"><i class="fa fa-circle-o"></i>Phòng chat</a></li>
                    <li><a href="video-call"><i class="fa fa-circle-o"></i>Video call</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#">
                    <i class="fas fa-user-circle"></i> <span>Tài khoản</span>
                    <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li><a href="chi-tiet-tai-khoan"><i class="fa fa-circle-o"></i>Tài khoản</a></li>
                    <li><a href="javascript:void(0)" id="dang-xuat-btn"><i class="fa fa-circle-o"></i>Đăng xuất</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="huong-dan-su-dung">
                    <i class="fa fa-hand-sparkles"></i> <span>Hướng dẫn sử dụng</span>
                </a>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <%--    <section class="noti">--%>
    <%--        <div class="notileft">--%>
    <%--            <span>Thông báo</span>--%>
    <%--        </div>--%>
    <%--                <marquee id="marq" scrollamount="10" direction="left" loop="infinite" scrolldelay="0" behavior="scroll"--%>
    <%--                         onmouseover="this.stop()" onmouseout="this.start()" class="notiright">--%>
    <%--                                <span>- Whether Object Identity information is to be used for determining how to serialize/deserialize property value to/from JSON (and other data formats) will be based on existence (or lack thereof) of @JsonIdentityInfo annotation. It can be used on classes (to indicate that properties of that type should have feature enabled) as well as on individual properties (to support cases where type itself can not be annotated; or to use different id generation sequence).</span>--%>

    <%--                </marquee>--%>
    <%--    </section>--%>

    <!-- Right side column. Contains the navbar and content of the page -->