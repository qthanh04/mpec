<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<style>
    .all-browsers {
        margin-top: 20px;
        padding: 5px;
        background-color: lightgray;
        border: 2px solid black;
        padding: 10px;
        border-radius: 25px;
    }

    .all-browsers > h1, .browser {
        margin: 10px;
        padding: 5px;
        cursor: pointer;
        height: 80px
    }

    .all-browsers > h4 {
        cursor: pointer
    }

    .browser {
        background: white;
    }

    .browser > h2, p {
        margin: 4px;
        font-size: 90%;
    }

</style>
<div class="modal fade" id="modal-nhom-hang" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm mới nhóm hàng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="caifop1li form-group">
                    <label>Tên nhóm hàng:</label>
                    <input type="text" class="form-control" id="text-9">
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="caifop1li form-group">
                    <label>Mã nhóm hàng:</label>
                    <input type="text" class="form-control" id="text-11">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-nhom-hang">Lưu</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-thuong-hieu" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm mới thương hiệu</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="caifop1li form-group">
                    <label>Tên thương hiệu:</label>
                    <input type="text" class="form-control" id="text-10">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-thuong-hieu">Lưu</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-don-vi" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm mới đơn vị cơ bản</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="caifop1li form-group">
                    <label>Tên đơn vị:</label>
                    <input type="text" class="form-control" id="text-14">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-don-vi">Lưu</button>
            </div>
        </div>
    </div>
</div>

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Thêm hàng hóa</span>
        </div>
        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Mã hàng:</label>
                        <input type="text" class="form-control" id="text-1" disabled>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Tên hàng hóa:</label>
                        <input type="text" class="form-control" id="text-2">
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li">
                        <label>Nhóm hàng:</label>
                        <select class="js-example-basic-single" name="state" id="select-1">
                            <option value=0>+ Thêm nhóm hàng</option>
                        </select>
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li">
                        <label>Thương hiệu:</label>
                        <select class="js-example-basic-single" name="state" id="select-2">
                            <option value=0>+ Thêm thương hiệu</option>
                        </select>
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li">
                        <label>Đơn vị cơ bản:</label>
                        <select class="js-example-basic-single" name="state" id="select-3">
                            <option value=1>Hộp</option>
                            <option value=0>+ Thêm đơn vị</option>
                        </select>
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Giảm giá: </label>
                        <input type="number" class="form-control" min="0" id="text-7" value="0">
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Mã giảm giá: </label>
                        <input type="text" class="form-control" min="0" id="text-8">
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Mã vạch: </label>
                        <input type="text" class="form-control" min="0" id="text-13">
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Tích điểm: </label>
                        <input type="number" class="form-control" min="0" id="text-5" value="0">
                        <span class="help-block">Help block with error</span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <label>Bar Code: </label>
                        <div id="bar-img"></div>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="view-gach-ngang">
                    <label>Hình ảnh:</label>
                    <hr/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <div class="caifop1li form-group">
                    <label>Hình ảnh 1:</label>
                    <form id="form-file" method="POST" enctype="multipart/form-data">
                        <input type="file" class="form-control" id="file-1" name="files" accept="image/*"
                               placeholder="Chọn file">
                    </form>
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="row">
                    <div class="col-md-6" id="imgHH-1">
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="caifop1li form-group">
                    <label>Hình ảnh 2:</label>
                    <form id="form-file1" method="POST">
                        <input type="file" class="form-control" id="file-2" name="files" accept="*">
                    </form>
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="row">
                    <div class="col-md-6" id="imgHH-2">
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="caifop1li form-group">
                    <label>Hình ảnh 3:</label>
                    <form id="form-file2">
                        <input type="file" class="form-control" id="file-3" name="files" accept="image/*"
                               placeholder="Chọn file">
                    </form>
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="row">
                    <div class="col-md-6" id="imgHH-3">
                    </div>
                </div>
            </div>
        </div>
<%--         chi lam tiep--%>
<%--        <div class="row">--%>
<%--            <div class="col-xs-12">--%>
<%--                <article class="all-browsers">--%>
<%--                    <span style="font-size: 20px";margin-right:20px>Thuộc tính</span><span><i--%>
<%--                        class="fa flr fs14 fa-chevron-down"></i></span>--%>
<%--                    <article class="browser">--%>
<%--                        <div class="row" style="height: 25px;">--%>
<%--                            <form class="col-md-4">--%>
<%--                                <div>--%>
<%--                                    <label>Thông số kĩ thuật</label>--%>
<%--                                </div>--%>
<%--                                <select class="form-control select2 select-hang-hoa" id="search-hang-hoa"--%>
<%--                                        placeholder="Chọn hàng hóa">--%>
<%--                                    <option></option>--%>
<%--                                </select>--%>
<%--                            </form>--%>
<%--                            <form class="col-md-4">--%>
<%--                                <div>--%>
<%--                                    <label>Thông số kĩ thuật chi tiết </label>--%>
<%--                                </div>--%>
<%--                                <select class="form-control select2 select-don-vi" id="search-don-vi">--%>
<%--                                    <option></option>--%>
<%--                                </select>--%>
<%--                            </form>--%>
<%--                            <div class="col-md-4">--%>
<%--                                <div>--%>
<%--                                    <label>Giá trị</label>--%>
<%--                                </div>--%>
<%--                                <input type="text" id="giaBanInput" name="gia_ban"--%>
<%--                                       style="height: 35px; border:1px solid #CDCCD5">--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </article>--%>
<%--                    <article class="browser">--%>
<%--                        <h2>Mozilla Firefox</h2>--%>
<%--                        <p>Mozilla Firefox is an open-source web browser developed by Mozilla. Firefox has been the--%>
<%--                            second most popular web browser since January, 2018.</p>--%>
<%--                    </article>--%>
<%--                    <article class="browser">--%>
<%--                        <h2>Microsoft Edge</h2>--%>
<%--                        <p>Microsoft Edge is a web browser developed by Microsoft, released in 2015. Microsoft Edge--%>
<%--                            replaced Internet Explorer.</p>--%>
<%--                    </article>--%>
<%--                </article>--%>
<%--            </div>--%>
<%--        </div>--%>

<%--        <div class="row">--%>
<%--            <div class="col-xs-12">--%>
<%--                <div class="view-gach-ngang">--%>
<%--                    <label>Mô tả</label>--%>
<%--                    <hr/>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="row " id="mo-ta-hang-hoa">--%>
<%--            <div class="col-xs-12">--%>
<%--                <textarea name="mo-ta" id="mo-ta" style="width: 100%"></textarea>--%>
<%--            </div>--%>
<%--        </div>--%>


        <div class="row mgtb-20">
            <div class="col-xs-12 text-center">
                <button class="btn btn-primary" id="luu-hang-hoa">Lưu lại</button>
                <button class="btn btn-default" id="back-history">Quay lại</button>
                <button class="btn btn-primary hidden" data-toggle="modal" data-target="#modal-nhom-hang"
                        id="btn-them-nhom-hang">
                    Thêm nhóm hàng
                </button>
                <button class="btn btn-primary hidden" data-toggle="modal" data-target="#modal-thuong-hieu"
                        id="btn-them-thuong-hieu">
                    Thêm thương hiệu
                </button>
                <button class="btn btn-primary hidden" data-toggle="modal" data-target="#modal-don-vi"
                        id="btn-them-don-vi">
                    Thêm đơn vị
                </button>
            </div>
        </div>
    </div>
</section>
<!-- /.content -->
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({width: 'resolve'});
        $('.js-example-basic-multiple').select2({width: 'resolve'});
    });
</script>
<script type="text/javascript" src="resources/bootstrap/js/nicEdit.js"></script>
<script src="resources/model/hang_hoa/ajax_nhom_hang.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_don_vi.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_thuong_hieu.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_hang_hoa.js" type="text/javascript"></script>
<script src="resources/model/upload_file/ajax_upload_file.js" type="text/javascript"></script>
<script src="resources/pages/quan_ly_hang_hoa/ajax_thong_tin_hang_hoa.js" type="text/javascript"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
