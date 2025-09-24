<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<style>
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;

    }

    input {
        outline: 0!important;
        border-width: 0 0 2px!important;
        border-color: #b1bcae!important;
        background: transparent;
        text-align: center;
    }
    input:focus {
        border-color: #BCACBC!important;
    }

    input.none-arrow::-webkit-outer-spin-button,
    input.none-arrow::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    img:hover {
        box-shadow: 0 0 2px 1px rgba(0, 140, 186, 0.5);
    }
</style>

<!--Confirm Modal -->
<div class="modal fade" id="confirmModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Xác nhận</h4>
            </div>
            <div class="modal-body">
                <p>Giá bán sản phẩm nhỏ hơn giá nhập. Bạn có chắc chắn không?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="refuse-btn"><i class="fas fa-ban"></i> Không</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm-btn"
                        style="width: 60px;"><i class="fas fa-check"></i> Có</button>
            </div>
        </div>

    </div>
</div>

<!--Confirm Modal 2-->
<div class="modal fade" id="confirmModal2" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Xác nhận</h4>
            </div>
            <div class="modal-body">
                <p>Bạn có muốn cập nhập giá sản phẩm không?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="refuse-btn2"><i class="fas fa-ban"></i> Không</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" id="confirm-btn2"
                        style="width: 60px;"><i class="fas fa-check"></i> Có</button>
            </div>
        </div>

    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog ">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Thêm giá bán hàng hóa mới</h4>
            </div>
            <div class="modal-body" style="height: 170px;">
                <%--tim kiem hang hoa--%>
                <div class="row" style="height: 25px;">
                    <form class="col-md-4">
                        <div>
                            <label>Hàng hóa</label>
                        </div>
                        <select class="form-control select2 select-hang-hoa" id="search-hang-hoa"
                                placeholder="Chọn hàng hóa">
                            <option></option>
                        </select>
                    </form>
                    <div class="col-md-4">
                        <div>
                            <label>Đơn vị</label>
                        </div>
                        <select class="select2 select-don-vi" id="search-don-vi">
                            <option></option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <div>
                            <label>Giá bán</label>
                        </div>
                        <input class="none-arrow" type="text" id="giaBanInput" name="gia_ban" step="any"
                               style="height: 35px; border:1px solid #CDCCD5">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-upload"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>
<!--Sub Modal -->
<div class="modal fade" id="subModal" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Thêm đơn vị hàng hóa mới</h4>
            </div>
            <div class="modal-body" style="height: 170px;">
                <%--tim kiem hang hoa--%>
                <div class="my-4" style="height: 25px;">
                    <div class="col-md-4">
                        <div>
                            <label>Hàng hóa</label>
                        </div>
                        <div>
                            <span id="ten-hang-hoa-moi"></span>
                        </div>
                    </div>
                    <form class="col-md-4 ">
                        <div>
                            <label>Đơn vị</label>
                        </div>
                        <select class="form-control select2 select-hang-hoa" id="search-don-vi-co-ban"
                                placeholder="Chọn đơn vị">
                            <option></option>
                            <option value="1">Hộp</option>
                            <option value="2">Chai</option>
                            <option value="3">Thùng</option>
                            <option value="4">Vỉ</option>
                            <option value="5">Gói</option>
                        </select>
                    </form>
                    <div class="col-md-4">
                        <div>
                            <label>Tỷ lệ</label>
                        </div>
                        <input class="form-control" type="number" min="0" value="1" id="tyLe">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
                <button type="button" class="btn btn-primary" id="btn-upload-don-vi"><i class="fa fa-check-square"></i> Lưu</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-don-vi" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Thêm mới đơn vị</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" style="height: 170px;">
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

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Danh sách thiết lập giá</span>
        </div>
        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-4">
                    <div class="caifop1li">
                        <select class="js-example-basic-single" name="state" id="bimo2">
                        </select>
                    </div>
                </div>


                <div class="col-md-4">
                    <div class="caifop1li form-group">
                        <input type="text" class="form-control" id="bimo1" placeholder="Nhập thông tin tìm kiếm">
                    </div>
                </div>

                <div class="col-xs-2">
                    <i class="fas fa-2x fa-microphone" id="start-btn"></i>
                </div>

                <div class="col-md-2">
                    <button class="btn btn-primary" id="btn-1" style="display: block"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                </div>
            </div>
        </div>
    </div>
    <div class="buifmaoptb table-responsive">
        <table class="table table-hover table-condensed table-striped">
            <tbody id="table-hang-hoa">
            <tr>
                <th>STT</th>
                <th>Mã hàng</th>
                <th>Tên hàng</th>
                <th>Đơn vị</th>
                <th>Giá nhập gần nhất</th>
                <th>Giá bán</th>
            </tr>
            <td>1</td>
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
            </tr>
            <tr>
                <td>3</td>
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
            </tr>
            <tr>
                <td>5</td>
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
            </tr>
            <tr>
                <td>7</td>
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
            </tr>
            <tr>
                <td>9</td>
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
            </tr>
            </tbody>
        </table>
        <div class="page-link">
            <button type="button" class="btn btn-info" id="btn-them" data-toggle="modal" data-target="#myModal"><i
                    class="fas fa-plus-circle"></i></button>
        </div>
    </div>

    <div class="receivepagi">
        <div class="pagi" id="pagination1">
            <div class="paginationjs">
            </div>
        </div>
    </div>
</section>
<!-- /.content -->
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({width: 'resolve'});
        $('.js-example-basic-multiple').select2({width: 'resolve'});
        $("#selUser").select2();
    });
</script>

<script>
    $('.select').select2({
        placeholder: "Chọn hàng hóa",
        allowClear: true,
        maximumInputLength: 10,
        minimumResultsForSearch: 5
    });

    $('.select-don-vi').select2({
        placeholder: "Chọn đơn vị",
        allowClear: true,
        minimumResultsForSearch: 5
    });
</script>

<script src="resources/pages/quan_ly_hang_hoa/ajax_thiet_lap_gia.js"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/model/hang_hoa/ajax_thiet_lap_gia.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6/js/select2.min.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/model/hang_hoa/ajax_don_vi.js" type="text/javascript"></script>



