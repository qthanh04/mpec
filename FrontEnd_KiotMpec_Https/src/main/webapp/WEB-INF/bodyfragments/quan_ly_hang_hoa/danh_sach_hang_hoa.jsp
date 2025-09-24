<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<style>
    .modal-content .hang-hoa{
        width: 800px;
    }
    .nicEdit-panelContain{
        width: 700%!important;
    }
    input.none-arrow::-webkit-outer-spin-button,
    input.none-arrow::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
    img:hover {
        box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
    }
    input {
        outline: 0!important;
        border-width: 0 0 2px!important;
        border-color: #b1bcae!important;
        background: transparent;
    }
    input:focus {
        border-color: #BCACBC!important;
    }
</style>
<%--===========================modal them hang hoa===============================--%>
<div class="modal fade" id="modal-them-hang-hoa" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content hang-hoa">
            <div class="modal-header">
                <h5 class="modal-title">Thêm hàng hóa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="buifmaopct">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Mã hàng tự động" type="text" class="form-control" id="text-1" disabled>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Tên hàng hóa" type="text" class="form-control" id="text-2">
                                <span class="help-block">Help block with error</span>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Mã giảm giá" type="text" class="form-control" min="0" id="text-8">
                                <span class="help-block">Help block with error</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Mã vạch" type="text" class="form-control" min="0" id="text-13">
                                <span class="help-block">Help block with error</span>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Tích điểm" type="number" class="form-control" min="0" id="text-5" >
                                <span class="help-block">Help block with error</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input placeholder="Giảm giá" type="number" class="form-control" min="0" id="text-7" >
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
                                <label>Bar Code: </label>
                                <div id="bar-img"></div>
                            </div>
                        </div>
                    </div>



                    <%--                    <div class="row">--%>
                    <%--                        <div class="col-md-12">--%>
                    <%--                            <div class="caifop1li form-group">--%>
                    <%--                                <label>Mô tả </label>--%>
                    <%--                                <input placeholder="Nhập mô tả" type="text" class="form-control"  id="mo-ta">--%>
                    <%--                                <span class="help-block">Help block with error</span>--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>

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

                <%--                <textarea id="mo-ta" style="width:100%; height:100%;"></textarea>--%>
            </div>
            <div class="row mgtb-20">
                <div class="col-xs-12 text-center">
                    <button class="btn btn-primary hidden" data-toggle="modal" href="#modal-nhom-hang"
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
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="luu-hang-hoa"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>
<%--===========================modal nhom hang===============================--%>
<div class="modal fade" id="modal-nhom-hang" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
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
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-nhom-hang"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>
<%--===========================modal thuong hieu===============================--%>
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
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-thuong-hieu"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>
<%--===========================modal don vi===============================--%>
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
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-don-vi"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>
<%--===========================modal chon file===============================--%>
<div class="modal fade" id="modal-chon-file" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chọn file dữ liệu</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label>Tải về file mẫu</label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <form id="form-file4">
                                <input type="file" class="form-control" id="file-4" name="files" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       placeholder="Chọn file">
                            </form>
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-import-excel"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách hàng hóa</span>
            </div>

            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-3">
                        <div class="caifop1li">
                            <select class="js-example-basic-single" name="state" id="bimo2">
                            </select>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="row">
                            <div class="col-xs-7">
                                <div class="caifop1li form-group">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="bimo1" placeholder="Nhập thông tin tìm kiếm">
                                    </div>
                                </div>

                            </div>
                            <div class="col-xs-2">
                                <i class="fas fa-2x fa-microphone" id="start-btn"></i>
                            </div>
                            <div class="col-xs-2">
                                <button class="btn btn-primary" id="btn-1" style="display: block"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                            </div>
                            <div class="col-xs-1">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-1">
                    </div>

                    <div class="col-md-5">
                        <div class="row">
                            <div class="col-xs-4">
                                <button class="btn btn-primary" id="btn-hh" style="display: block" data-toggle="modal"
                                        data-target="#modal-them-hang-hoa"><i class="far fa-plus"></i> Thêm hàng hóa</button>
                            </div>
                            <div class="col-xs-4">
                                <button class="btn btn-primary" id="btn-2" style="display: block" data-toggle="modal"
                                        data-target="#modal-chon-file"> <i class="fas fa-fw fa-file-import"></i> Nhập dữ liệu </button>
                            </div>
                            <div class="col-xs-4">
                                <button class="btn btn-primary" id="btn-3" style="display: block"><i class="fas fa-fw fa-file-import"></i> Xuất dữ liệu</button>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>

        <!--start-table -->
        <div class="buifmaoptb table-responsive">
            <table class="table table-hover table-condensed table-striped">
                <tbody id="table-hang-hoa">
                <tr>
                    <th>STT</th>
                    <th>Mã hàng</th>
                    <th>Tên hàng</th>
                    <th>Giá bán</th>
                    <th>Giá vốn</th>
                    <th>Đơn vị</th>
                    <th> Thương Hiệu</th>
                    <th> Nhóm hàng</th>
                    <th>Mã Vạch</th>
                </tr>

                <td>1</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>7</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>8</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>9</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>10</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
            <div class="page-link">
                <a href="hang-hoa?id=0" target="_blank"><i class="fas fa-plus-circle"></i></a>
            </div>
        </div>

        <div class="receivepagi">
            <div class="pagi" id="pagination1">
                <div class="paginationjs">
                </div>
            </div>
        </div>
    </div>

</section>
<!--end-table -->
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({width: 'resolve'});
        $('.js-example-basic-multiple').select2({width: 'resolve'});
    });
</script>

<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/pages/quan_ly_hang_hoa/ajax_danh_sach_hang_hoa.js"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/model/hang_hoa/ajax_ds_hang_hoa.js" type="text/javascript"></script>
<script src="resources/model/upload_file/ajax_upload_file.js" type="text/javascript"></script>
<script type="text/javascript" src="resources/bootstrap/js/nicEdit.js"></script>
<script src="resources/model/hang_hoa/ajax_nhom_hang.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_don_vi.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_thuong_hieu.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_hang_hoa.js" type="text/javascript"></script>
<script src="resources/pages/quan_ly_hang_hoa/ajax_thong_tin_hang_hoa.js" type="text/javascript"></script>