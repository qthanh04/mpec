<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<style>
    .form-group {
        margin-bottom: 10px !important;
        padding-bottom: 2px !important;
    }
</style>
<!-- Modal -->
<!--remove Branch-->
<!-- ============================Main content ================================-->
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
                        Bạn có chắc chắn muốn xóa chi nhánh này không ?
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
</div>
<!--upload Branch-->
<div class="modal fade" id="modal-1" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thêm chi nhánh</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-addressBranch">Địa chỉ </label>
                            <input type="text" class="form-control" id="input-text-addressBranch"
                                   placeholder="Nhập địa chỉ Chi nhánh">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-ipAddressBranch">Địa chỉ IP</label>
                            <input type="text" class="form-control" id="input-text-ipAddressBranch"
                                   placeholder="Nhập địa chỉ IP">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="connectiveStatusSelect">Trạng thái kết nối </label>
                            <select class="form-control" id="connectiveStatusSelect">
                                <option value=1>True</option>
                                <option value=0>False</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="activeStatusSelect">Trạng thái hoạt động </label>
                            <select class="form-control" id="activeStatusSelect">
                                <option value=1>True</option>
                                <option value=0>False</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="btn-confirmedBranch"><i class="far fa-plus"></i> Thêm mới</button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
            </div>
        </div>
    </div>
</div>
<!--update Branch-->
<div class="modal fade" id="modal-editBranch" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel-2">Sửa chi nhánh</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-addressEditedBranch">Địa chỉ </label>
                            <input type="text" class="form-control" id="input-text-addressEditedBranch"
                                   placeholder="Nhập địa chỉ Chi nhánh">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="input-text-ipAddressEditedBranch">Địa chỉ IP</label>
                            <input type="text" class="form-control" id="input-text-ipAddressEditedBranch"
                                   placeholder="Nhập địa chỉ IP">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="connectiveStatusEditedBranchSelect">Trạng thái kết nối </label>
                            <select class="form-control" id="connectiveStatusEditedBranchSelect">
                                <option value=1>True</option>
                                <option value=0>False</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="activeStatusEditedBranchSelect">Trạng thái hoạt động </label>
                            <select class="form-control" id="activeStatusEditedBranchSelect">
                                <option value=1>True</option>
                                <option value=0>False</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" id="btn-confirmedEditedBranch"><i class="fa fa-check-square"></i> Cập nhật</button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
            </div>
        </div>
    </div>
</div>
<!-- Content Wrapper. Contains page content -->
<div class="content" id="pageBody">
    <div class="buifmaoptitle">
        <span class="page-title">Danh sách chi nhánh</span>
    </div>
    <div class="buifmaopct">
        <div class="row">
            <div class="col-xs-5">
                <div class="caifop1li form-group">
                    <input type="text" class="form-control" id="bimo1" placeholder="Nhập thông tin tìm kiếm">
                </div>
            </div>
            <div class="col-xs-2">
                <i class="fas fa-2x fa-microphone" id="start-btn"></i>
            </div>
            <div class="col-xs-2">
                <button class="btn btn-primary" id="btn-1" style="display: block"> <i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
            </div>


        </div>
    </div>

    <%--Table--%>
    <div class="buifmaoptb table-responsive">
        <table class="table table-hover table-condensed table-striped" id="table">
            <tbody>
            <tr>
                <th>STT</th>
                <th>Mã chi nhánh</th>
                <th>Vị trí</th>
                <th>Địa chỉ IP</th>
                <th>Trạng thái</th>
                <th>Chức năng</th>
            </tr>
            <tr>
                <td>1</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                    <button style="background: transparent;border: none; margin-right: 12px;" id="btnEditBranch"
                            data-toggle="modal" data-target="#modal-editBranch"><i class="fas fa-pen"></i></button>
                    <button style="background: transparent;border: none; margin-right: 12px;" id="btn-addBranch"
                            data-toggle="modal" data-target="#modal-1"><i class="fas fa-save"></i></button>
                    <button style="background: transparent;border: none;" id="btnRemoveBranch" data-toggle="modal"
                            data-target="#modal-remove"><i class="fas fa-trash"></i></button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="receivepagi">
        <div class="pagi" id="pagination">
            <div class="paginationjs">
            </div>
        </div>
    </div>
</div>
<script src="resources/pages/quan_ly_cong_ty/ajax_chi_nhanh.js"></script>
<script src="resources/model/chi_nhanh/model_chi_nhanh.js"></script>
<script src="resources/dist/js/ajax/ajax_main.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>