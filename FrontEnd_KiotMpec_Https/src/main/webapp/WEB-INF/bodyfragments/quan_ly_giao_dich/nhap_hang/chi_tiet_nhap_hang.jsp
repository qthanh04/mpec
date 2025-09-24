    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<link rel="stylesheet" href="resources/dist/js/utils/selectize.bootstrap3.min.css">
<style>
    input.none-arrow::-webkit-outer-spin-button,
    input.none-arrow::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    img:hover {
        box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
    }

    input {
        outline: 0 !important;
        border-width: 0 0 2px !important;
        border-color: #b1bcae !important;
        background: transparent;
        text-align: center;
    }

    input:focus {
        border-color: #BCACBC !important;
    }

    .select2-container--default .select2-selection--single {
        background: transparent;
        outline: 0 !important;
        border-width: 0 0 2px !important;
        border-color: #b1bcae !important;
    }

    .select2-container--default .select2-selection--single .select2-selection__rendered {
        padding-top: 8px !important;


    }

    .selectize-control.single .selectize-input.input-active, .selectize-input {
        background: transparent !important;
    }

</style>
<%--modal them nha cung cap--%>
<div class="modal fade" id="modal-khach-hang" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm mới nhà cung cấp</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="caifop1li form-group">
                    <label>Tên nhà cung cấp:</label>
                    <input type="text" class="form-control" id="ten-khach-hang">
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="caifop1li form-group">
                    <label>Số điện thoại :</label>
                    <input type="number" class="form-control none-arrow" id="sdt-khach-hang">
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="caifop1li form-group">
                    <label>Email:</label>
                    <input type="email" class="form-control" id="email-khach-hang">
                    <span class="help-block">Help block with error</span>
                </div>
                <div class="caifop1li form-group">
                    <label>Địa chỉ :</label>
                    <input type="text" class="form-control" id="dia-chi-khach-hang">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-save-khach-hang">Lưu</button>
            </div>
        </div>
    </div>
</div>

<%--=============================modal confirm khách hàng nợ =======================--%>
<div class="modal" tabindex="-1" role="dialog" id="modal-cho-no">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Số tiền bạn trả ít hơn tổng tiền hóa đơn. Bạn có chắc chắn không?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm-btn">Có</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="refuse-btn">Hủy</button>
            </div>
        </div>
    </div>
</div>

<!-- Main content -->
<section class="content" id="pageBody">
    <div class=" buifmaop">
        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-3">
                    <select id="select-state" class="selectize-control control-form single"
                            data-placeholder="Chọn hàng hóa . . . ">
                    </select>
                </div>
            </div>


            <div class="row">
                <div class="col-md-8">
                    <div class="row">
                        <div class="buifmaoptb">
                            <table class="table table-hover table-condensed table-striped" id="table-hang-hoa">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Hàng Hóa</th>
                                    <th>Đơn vị</th>
                                    <th>Số lượng</th>
                                    <th>Giá nhập</th>
                                    <th>Giảm giá</th>
                                    <th>Thành tiền</th>
                                    <th>Xoá</th>
                                </tr>
                                </thead>

                                <tbody>
                                <tr>
                                    <td>1</td>
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
                                </tr>

                                </tbody>

                            </table>
                        </div>
                        <div class="receivepagi">
                            <div class="pagi" id="pagination1">
                                <div class="paginationjs">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="kth col-md-4">
                    <h3 class="text-center">Thông tin hóa đơn </h3>
                    <div class="row" style="padding-left: 15px">
                        <div class="caifop1li">
                            <div>

                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <select id="select-khach-hang" class="selectize-control control-form single"
                                                data-placeholder="Chọn nhà cung cấp . . . ">
                                        </select>

                                    </div>

                                    <div class="col-md-6 text-right add-nha-cung-cap"><input type="text" id="bimo5"
                                                                                             placeholder="Tên khách hàng"
                                                                                             style="width:10em;margin-right:25px">
                                    </div>
                                </div>

<%--                                <div class="row" style="padding : 10px">--%>
<%--                                    <div class="col-md-6">--%>
<%--                                        <strong class="m-r-1">Nợ cũ:</strong>--%>
<%--                                    </div>--%>
<%--                                    <div class="col-md-6 text-right"><input type="number" class="none-arrow"--%>
<%--                                                                            style="width:10em;margin-right:25px"--%>
<%--                                                                            disabled="true" value="0">--%>
<%--                                    </div>--%>
<%--                                </div>--%>

                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <strong class="m-r-1">Tổng tiền:</strong>
                                    </div>
                                    <div class="col-md-6 text-right"><input id="tong-tien" disabled="true" value="0"
                                                                            style="width:10em;margin-right:25px">
                                    </div>
                                </div>


                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <strong class="m-r-1">Giảm giá:</strong>
                                    </div>
                                    <div class="col-md-6 text-right"><input type="text" min="0" class="giamGiaTong"
                                                                            value="0"
                                                                            id="giamGiaTong" style="width:8em"/>
                                        <span style="margin-right: 25px">VNĐ</span>

                                    </div>
                                </div>

                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <strong class="m-r-1">Tiền phải trả:</strong>
                                    </div>
                                    <div class="col-md-6 text-right"><input type="text"
                                                                            class="none-arrow"
                                                                            id="tien-phai-tra"
                                                                            style="width:10em;margin-right:25px">
                                    </div>
                                </div>

                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <strong class="m-r-1">Tiền đã trả:</strong>
                                    </div>
                                    <div class="col-md-6 text-right"><input type="text"
                                                                            class="none-arrow"
                                                                            id="tien-khach-tra"
                                                                            style="width:10em;margin-right:25px">
                                    </div>
                                </div>

                                <div class="row" style="padding : 10px">
                                    <div class="col-md-6">
                                        <strong class="m-r-1">Tiền trả lại khách:</strong>
                                    </div>
                                    <div class="col-md-6 text-right"><input type="text"
                                                                            id="tien-tra-lai"
                                                                            disabled="true"
                                                                            style="width:10em;margin-right:25px">

                                    </div>
                                </div>


                                <input type="text" class="none-arrow" id="ghi-chu"
                                       style="padding : 10px;margin-right:25px" placeholder="Ghi Chú">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 text-center">
                            <button class="btn btn-success" style="width: 120px" id="btn-thanh-toan"><i class="fas fa-shopping-basket"></i> Thanh Toán</button>
                            <button class="btn btn-primary" style="width: 100px" id="btn-print-pdf" disabled><i class="fa fa-print"></i> In</button>
                            <button class="btn btn-danger" style="width: 100px" id="btn-xoa-hoa-don"><i class="far fa-trash-alt"></i> Xóa</button>
                        </div>
                    </div>
                </div>

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

<style>
    input.none-arrow::-webkit-outer-spin-button,
    input.none-arrow::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>
<script src="resources/model/giao_dich/nhap_hang/ajax_phieu_nhap_hang.js" type="text/javascript"></script>
<script src="resources/model/doi_tac/ajax_nha_cung_cap.js" type="text/javascript"></script>
<script src="resources/model/nhan_vien/ajax_nhan_vien.js" type="text/javascript"></script>
<script src="resources/model/hang_hoa/ajax_hang_hoa.js" type="text/javascript"></script>
<script src="resources/model/giao_dich/nhap_hang/ajax_phieu_nhap_chi_tiet.js"></script>
<script src="resources/dist/js/ajax/pages/quan_ly_giao_dich/nhap_hang/ajax_chi_tiet_nhap_hang.js"
        type="text/javascript"></script>
<script src="resources/model/nhan_vien/ajax_nhan_vien.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/dist/js/utils/selectize.min.js"></script>