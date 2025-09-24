<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<style>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    label.label-form {
        font-size: 17px;
        margin-top: 5px;
    }

    #myForm .error{
        font-size: 13px;
        color: red;
    }
</style>
    <style>
        .form-group {
            margin-bottom: 10px !important;
            padding-bottom: 2px !important;
        }
    </style>

<section class="content" id="pageBody">
    <div class="row" >
        <%--        bắt đầu--%>
        <div class="col-lg-4 danh-muc">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Thông tin nhân viên </span><span id="ma"></span>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <hr/>
        </div>
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong>Họ và tên:</strong></label>
                <input type="text" class="form-control" id="input-text-2" name="name" placeholder="Nhập họ và tên">
                <span class="help-block">Help block with error</span>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong>Email:</strong></label>
                <input type="text" class="form-control" id="input-text-3" placeholder="Nhập email" name="email">
                <span class="help-block">Help block with error</span>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong> Số điện thoại:</strong></label>
                <input type="text" class="form-control" id="input-text-4" placeholder="Nhập số điện thoại" name="phone">
                <span class="help-block">Help block with error</span>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong> Địa chỉ:</strong></label>
                <input type="text" class="form-control" id="input-text-5" placeholder="Nhập địa chỉ" name="address">
                <span class="help-block">Help block with error</span>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong> Tài khoản:</strong></label>
                <input type="text" class="form-control" id="label-2" disabled>
                <span class="help-block">Help block with error</span>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="caifop1li form-group">
                <label><strong> Ngày sinh:</strong></label>
                <input type="date" class="form-control dateFormat" id="input-date-1"  placeholder="Nhập ngày sinh" name="birthday">
                <span class="help-block">Help block with error</span>
            </div>
        </div>
    </div>

    <div class="row " style="margin-top: 10px">
        <div class="col-lg-4">
            <label><strong>Thời gian kích hoạt:</strong></label>
            <input type="date" class="form-control dateFormat"  id="input-date-2" >
            <span class="help-block">Help block with error</span>
        </div>
        <div class="col-lg-4">
            <label><strong>Thời gian hết hạn : </strong></label>
            <input type="date" class="form-control dateFormat"  id="input-date-3">
            <span class="help-block">Help block with error</span>
        </div>
        <div class="col-lg-4">
            <label><strong> Thời gian khởi tạo:</strong></label>
            <input type="date" class="form-control dateFormat"  id="input-date-4" disabled>
            <span class="help-block">Help block with error</span>
        </div>
    </div>

    <div class="row" style="margin-top: 10px">
        <div class="col-lg-4">
            <label><strong> Chi nhánh:</strong></label>
            <select class="form-control select2" id="select-search-1" name="chiNhanh">
                <option></option>
            </select>
            <span class="help-block">Help block with error</span>
        </div>
        <div class="col-lg-4">
            <label><strong> Chức vụ:</strong></label>
            <select class="form-control select2" id="select-search-2" name="chucVu">
                <option></option>
            </select>
            <span class="help-block">Help block with error</span>
        </div>
        <div class="col-lg-4">
            <label><strong> Phòng ban:</strong></label>
            <select class="form-control select2" id="select-search-3" name="phongBan">
                <option></option>
            </select>
            <span class="help-block">Help block with error</span>
        </div>
    </div>


    <div class="row">
        <div class="col-lg-4" style="margin-top: 10px">
            <label><strong> Vai trò</strong></label>
            <select class="form-control select2" id="select-search-4" name="vaiTro">
                <option></option>
            </select>
            <span class="help-block">Help block with error</span>
        </div>
        <div class="col-lg-4" style="margin-top:30px">
            <div class="row">
                <label class="col-md-3"><strong>Trạng thái</strong></label>
                <label  class="col-md-3">Kích hoạt</label>
                <input class="col-md-2" type="radio" name="status" value="0" id="kichHoat">
                <label class="col-md-2">Khóa</label>
                <input class="col-md-2" type="radio" name="status" value="1" id="khoa">
            </div>
            <span class="help-block">Help block with error</span>
        </div>

        <div class="col-lg-4" style="margin-top: 30px">
            <div class="row">
                <label class="col-md-4"><strong>Giới tính</strong></label>
                <label  class="col-md-2">Nam</label>
                <input class="col-md-2" type="radio" name="gender" value="1" id="gTNam">
                <label class="col-md-2">Nữ</label>
                <input class="col-md-2" type="radio" name="gender" value="0" id="gTNu">
            </div>
            <span class="help-block">Help block with error</span>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-4" style="margin-top: 10px">
            <div class="row">
                <label class="col-md-3"><strong> Ảnh đại diện</strong></label>
                <div class="col-md-9">
                    <form class="custom-file" id="form-file">
                        <input type="file" class="custom-file-input form-control" id="customFile-1" name="avatar">
                        <span class="invalid-feedback" id="validateFormError" style="color: red"></span>
                    </form>
                </div>
            </div>
            <div class="row" style="margin-top: 10px">
                <div class="col-md-6" id="ava-img">
                </div>
            </div>
            <span class="help-block">Help block with error</span>
        </div>

        <div class="col-lg-4" style="margin-top:30px">
            <div class="row">
                <label class="col-md-3"><strong>Qr Code</strong></label>
                <div class="col-md-9" id="qrcode"></div>
            </div>
            <span class="help-block">Help block with error</span>
        </div>
    </div>


    <div class="row">
        <div class="col-xs-12 text-center">
            <button class="btn btn-primary" style="width: 100px" id="btn-2"><i class="fa fa-check-square"></i> Sửa</button>
        </div>
    </div>
</section>


<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({ width: 'resolve' });
        $('.js-example-basic-multiple').select2({ width: 'resolve' });
    });
</script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/model/chi_nhanh/ajax_chuc_vu_phong_ban_vai_tro.js"></script>
<script src="resources/model/nhan_vien/ajax_nhan_vien.js"></script>
<script src="resources/model/tai_khoan/ajax_tai_khoan.js"></script>
<script src="resources/model/upload_file/ajax_upload_file.js"></script>
<script src="resources/model/chi_nhanh/ajax_chuc_vu_phong_ban_vai_tro.js"></script>
<script src="resources/pages/tai_khoan/ajax_chi_tiet_tai_khoan.js"></script>
<script src="resources/dist/js/utils/select2Utils.js"></script>
<script src="resources/bootstrap/js/jquery.validate.min.js" type="text/javascript"></script>
