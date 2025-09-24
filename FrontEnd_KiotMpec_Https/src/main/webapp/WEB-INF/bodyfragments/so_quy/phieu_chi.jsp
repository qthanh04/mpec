<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<link rel="stylesheet" href="resources/dist/js/utils/selectize.bootstrap3.min.css">
<style>
    @media (min-width: 768px){
        .modal-dialog {
            width: 700px!important;
            margin: 30px auto;
        }
    }

    input.none-arrow::-webkit-outer-spin-button,
    input.none-arrow::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    .modal-content {
        min-width: 90px;
        min-height: 50px;
        width: 1000px;
        display: block;
        touch-action: pan-y;
        border-radius: 10px;
        z-index: 10003;
        opacity: 1;
        top: 20px !important;
        position: absolute;
        left: 56%;
        transform: translate(-50%);
    }

    .modal-title {
        left: 28px;
        padding: 0;
        font-size: 18px;
        color: #111;
        font-weight: 700;
        right: 48px;
        overflow: visible;
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

    .select2-container--default .select2-selection--single {
        background: transparent;
        outline: 0!important;
        border-width: 0 0 2px!important;
        border-color: #b1bcae!important;
    }

    .select2-container--default .select2-selection--single .select2-selection__rendered {
        padding-top: 8px!important;
    }


</style>
<%--===========================modal===============================--%>
<div class="modal fade" id="modal-phieu-chi" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Lập phiếu chi(tiền mặt)</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="col-md-6">
                        <select id="select-loai-chi" class="selectize-control control-form single"
                                data-placeholder="Chọn loại chi . . . ">
                        </select>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li">
                            <select class="form-control" name="state" id="select-doi-tuong">
                                <option value="1">Khách hàng</option>
                                <option value="2">Nhà cung cấp</option>
                            </select>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="timeCheckIn">
                                <span class="help-block">Help block with success</span>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-6">
                        <select id="select-khach-hang" class="selectize-control control-form single"
                                data-placeholder="Tên người nhận . . . ">
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="gia-tri" min="0" value="0"
                                   placeholder="Giá trị">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="ghi-chu"
                                   placeholder="Ghi chú">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="buifmaoptb table-responsive">
                    <table id="table-doi-tac" class="table table-hover table-condensed table-striped">
                        <tr>
                            <th>Mã hóa đơn</th>
                            <th>Thời gian</th>
                            <th>Tổng tiền</th>
                            <th>Tiền đã trả</th>
                            <th>Còn cần thu</th>
                            <th>Tiền thu</th>
                        </tr>
                        <tbody>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save">Lưu và in</button>
            </div>
        </div>
    </div>
</div>
<!-- ============================Main content ================================-->
<section class="content" id="pageBody">
    <div class="buifmaop" >
        <div class="buifmaop" >
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách phiếu chi</span>
            </div>
            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo1" placeholder="Mã phiếu chi">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo2" placeholder="Tên loại chi">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo3" placeholder="Tên nhân viên">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo5" placeholder="Từ ngày">
                            </div>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo6" placeholder="Đến ngày">
                            </div>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li">
                            <select class="form-control" name="state" id="bimo4">
                                <option value="-1">Tất cả trạng thái</option>
                                <option value="0">Đã thanh toán</option>
                                <option value="1">Chưa thanh toán</option>
                                <option value="2">Đang thanh toán</option>
                            </select>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-3">
                        <div class="caifop1li">
                            <select class="js-example-basic-single" name="state" id="bimo0">
                            </select>
                        </div>
                    </div>

                    <div class="col-xs-3 text-center">
                        <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>

                    <div class="col-xs-3 text-center">
                        <button class="btn btn-primary" id="btn-creat_new" data-toggle="modal"
                                data-target="#modal-phieu-chi"><i class="far fa-plus"></i> Tạo phiếu chi</button>
                    </div>

                    <div class="col-xs-3 text-center">
                        <button class="btn btn-primary" id="btn-excel"><i class="fa fa-print"></i> In danh sách phiếu chi</button>
                    </div>

                </div>
            </div>
        </div>

        <div class="buifmaoptb table-responsive" >
            <table class="table table-hover table-condensed table-striped" id="table-phieu-chi">
                <tbody>
                <tr>
                    <th>STT</th>
                    <th>Mã phiếu chi</th>
                    <th>Thời gian</th>
                    <th>Loại chi</th>
                    <th>Nhân viên</th>
                    <th>Giá trị</th>
                    <th>Trạng thái</th>
                </tr>
                <tr>
                    <td>1</td>
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
                </tr>
                <tr>
                    <td>3</td>
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
                </tr>
                <tr>
                    <td>5</td>
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
                </tr>
                </tbody>
            </table>
            <%--            <div class="page-link">--%>
            <%--                <a href="chi-tiet-hoa-don?id=0" target="_blank"><i class="fas fa-plus-circle" ></i></a>--%>
            <%--            </div>--%>
        </div>
        <div class="receivepagi">
            <div class="pagi" id="pagination">
                <div class="paginationjs">
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({ width: 'resolve' });
        $('.js-example-basic-multiple').select2({ width: 'resolve' });
    });
</script>
<script src="resources/model/so_quy/ajax_phieu_chi.js"></script>
<script src="resources/pages/so_quy/ajax_phieu_chi.js"></script>
<script src="resources/model/doi_tac/ajax_nha_cung_cap.js" type="text/javascript"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/dist/js/utils/selectize.min.js"></script>