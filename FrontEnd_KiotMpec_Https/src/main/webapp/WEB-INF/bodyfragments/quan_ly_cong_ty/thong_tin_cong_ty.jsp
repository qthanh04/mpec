<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<style>
    #ava-img {
        width: 100%;
        height: auto;
    }
</style>

<!-- Modal -->
<div class="modal fade in" id="modal-remove">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận thao tác</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12" id="text-confirm">
                        Bạn có chắc chắn xóa?
                    </div>
                </div>
            </div>
            <div class="modal-footer text-right">
                <button type="button" class="btn btn-danger mgr-10" data-dismiss="modal" id="confirm-yes">Đồng ý xóa
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Không xóa</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<div class="modal fade" id="modal-1" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thêm nhóm thiết bị</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Tên nhóm thiết bị:</label>
                    <input type="text" class="form-control" id="input-text-6" placeholder="Nhập tên nhóm thiết bị">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-secondary" id="btn-7">Thêm mới</button>
            </div>
        </div>
    </div>
</div>
<!-- Content Wrapper. Contains page content -->

<div class="content">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-3">

                </div>
                <div class="col-6 text-center">
                    <h4 class=" text-dark" id="vnCompanyName">Tổng công ty Đông Bắc</h4>
                    <h5 id="enCompanyName">Dong Bac Corp</h5>
                </div><!-- /.col -->
            </div><!-- /.row -->

            <div class="row">
                <div id="ava-img">
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="buifmaop" id="pageBody">
        <div class="buifmaop">
            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-1">Mã doanh nghiệp </label>
                            <input type="text" class="form-control" id="input-text-1">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-2">Email :</label>
                            <input type="text" class="form-control" id="input-text-2">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-3">Người đại diện </label>
                            <input type="text" class="form-control" id="input-text-3">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-4">Số điện thoại :</label>
                            <input type="text" class="form-control" id="input-text-4">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-5">Tên miền website</label>
                            <input type="text" class="form-control" id="input-text-5">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-7">Địa chỉ :</label>
                            <input type="text" class="form-control" id="input-text-7">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /.content -->
</div>

<section id="contact-map">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="map-contact__wapper">
                    <iframe
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.4270012298725!2d105.84818131431807!3d21.01559399360487!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab8ce525cbaf%3A0x5663ae2fcc524eb7!2zMTAyIFRyaeG7h3UgVmnhu4d0IFbGsMahbmcsIELDuWkgVGjhu4sgWHXDom4sIEhhaSBCw6AgVHLGsG5nLCBIw6AgTuG7mWksIFZp4buHdCBOYW0!5e0!3m2!1svi!2s!4v1565339506935!5m2!1svi!2s"
                            width="100%" height="100%" frameborder="0" style="border:0" allowfullscreen></iframe>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- /.content-wrapper -->
<style>
    .form-group {
        margin-bottom: 10px !important;
        padding-bottom: 2px !important;
    }

    #contact-map .map-contact__wapper {
        padding: 5px;
        border: 1px solid #a3a3a3;
        height: 272px;
    }
</style>
<script src="dist/js/ajax/model/doanh_nghiep/model-doanh-nghiep.js"></script>
<script src="dist/js/ajax/pages/quan_ly_cong_ty/ajax_cong_ty.js"></script>