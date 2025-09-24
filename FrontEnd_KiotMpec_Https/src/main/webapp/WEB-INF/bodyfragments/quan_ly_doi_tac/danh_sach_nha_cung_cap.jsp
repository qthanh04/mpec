<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

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
                        Bạn có chắc chắn xóa nhà cung cấp này không ?
                    </div>
                </div>
            </div>
            <div class="modal-footer text-right">
                <button type="button" class="btn btn-danger mgr-10" data-dismiss="modal" id="confirm-yes"><i class="fa fa-check-square"></i> Đồng ý xóa
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Không xóa</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- Main content -->
<section class="content" id="pageBody">
    <div class="row">
        <div class="col-xs-12">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Danh sách nhà cung cấp</span>
                </div>
                <div class="buifmaopct">
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="caifop1li form-group">
                                <input type="text" class="form-control" id="bimo1" placeholder="Nhập thông tin tìm kiếm">
                            </div>
                        </div>

                        <div class="col-xs-2">
                            <i class="fas fa-2x fa-microphone" id="start-btn"></i>
                        </div>

                        <div class="col-xs-4">
                            <button class="btn btn-primary" style="display: block" id="btn-search-1"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                        </div>
                    </div>

                </div>
            </div>
            <%--   =========   table--%>
            <div class="buifmaoptb table-responsive">
                <table class="table table-hover table-condensed table-striped" id="table-nha-cung-cap">
                    <tr>
                        <th>STT</th>
                        <th>Tên nhà cung cấp</th>
                        <th>Địa chỉ</th>
                        <th>Điện thoại</th>
                        <th>Email</th>
                        <th>Facebook</th>
                        <th>Ghi Chú</th>
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
                    <tr>
                        <td>7</td>
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
                    </tr>
                    <tr>
                        <td>9</td>
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
            <div class="chuc-nang-nha-cung-cap">
                <div class="row">
                    <div class="col-xs-12">
                        <hr/>
                    </div>
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Tên nhà cung cấp:</strong></label>
                            <input type="text" class="form-control" id="text-ten-nha-cung-cap">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Địa chỉ:</strong></label>
                            <input type="text" class="form-control" id="text-dia-chi">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Điện thoại:</strong></label>
                            <input type="text" class="form-control" id="text-dien-thoai">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Email:</strong></label>
                            <input type="text" class="form-control" id="text-email">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Facebook:</strong></label>
                            <input type="text" class="form-control" id="text-facebook">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="caifop1li form-group">
                            <label><strong>Ghi chú:</strong></label>
                            <input type="text" class="form-control" id="text-ghi-chu">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 text-center">
                        <button class="btn btn-success" style="width: 100px" id="btn-them-thuong-hieu"><i class="far fa-plus"></i> Thêm</button>
                        <button class="btn btn-primary" style="width: 100px" id="btn-sua-thuong-hieu"><i class="fa fa-check-square"></i> Sửa</button>
                        <button class="btn btn-danger text-left" style="width: 100px" id="btn-xoa-thuong-hieu"><i class="far fa-trash-alt"></i> Xóa</button>
                        <button class="btn btn-primary" id="btn-excel"><i class="fas fa-fw fa-file-import"></i> Xuất Excel</button>
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
<script src="resources/model/doi_tac/ajax_nha_cung_cap.js" type="text/javascript"></script>
<script src="resources/pages/quan_ly_doi_tac/ajax_danh_sach_nha_cung_cap.js" type="text/javascript"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
