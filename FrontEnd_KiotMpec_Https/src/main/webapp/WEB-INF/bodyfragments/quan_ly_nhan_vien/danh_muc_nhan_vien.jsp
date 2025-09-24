<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- /.modal-remove -->
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
                        Bạn có chắc chắn xóa danh mục này không ?
                    </div>
                </div>
            </div>
            <div class="modal-footer text-right">
                <button type="button" class="btn btn-danger mgr-10" data-dismiss="modal" id="confirm-yes"><i class="fa fa-check-square"></i> Đồng ý xóa
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Không xóa</button>
            </div>
        </div>
    </div>
</div>

<!-- /.modal phong ban -->
<div class="modal fade" id="modal-phong-ban">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Phòng ban</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body chuc-nang-phong-ban " style="height: 170px;">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label><strong>Tên phòng ban:</strong></label>
                            <input type="text" class="form-control" id="text-phong-ban">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label><strong>Mã phòng ban:</strong></label>
                            <input type="text" class="form-control" id="text-ma-phong-ban">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" style="width: 100px" id="btn-them-phong-ban"><i class="far fa-plus"></i> Thêm</button>
                <button class="btn btn-primary" style="width: 100px" id="btn-sua-phong-ban"><i class="fa fa-check-square"></i> Sửa</button>
                <button class="btn btn-danger" style="width: 100px" id="btn-xoa-phong-ban"><i class="far fa-trash-alt"></i> Xóa</button>
            </div>
        </div>
    </div>
</div>

<!-- /.modal chuc vu -->
<div class="modal fade" id="modal-chuc-vu">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Chức vụ</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body chuc-nang-chuc-vu" style="height: 170px;">
                <div class="caifop1li form-group ">
                    <label><strong>Tên chức vụ:</strong></label>
                    <input type="text" class="form-control" id="text-chuc-vu">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" style="width: 100px" id="btn-them-chuc-vu"><i class="far fa-plus"></i> Thêm</button>
                <button class="btn btn-primary" style="width: 100px" id="btn-sua-chuc-vu"><i class="fa fa-check-square"></i> Sửa</button>
                <button class="btn btn-danger" style="width: 100px" id="btn-xoa-chuc-vu"><i class="far fa-trash-alt"></i> Xóa</button>
            </div>
        </div>
    </div>
</div>


<!-- /.modal vai tro -->
<div class="modal fade" id="modal-vai-tro">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Vai trò</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body chuc-nang-vai-tro" style="height: 170px;">
                <div class="caifop1li form-group ">
                    <label><strong>Tên vai trò:</strong></label>
                    <input type="text" class="form-control" id="text-vai-tro">
                    <span class="help-block">Help block with error</span>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" style="width: 100px" id="btn-them-vai-tro"><i class="far fa-plus"></i> Thêm</button>
                <button class="btn btn-primary" style="width: 100px" id="btn-sua-vai-tro"><i class="fa fa-check-square"></i> Sửa</button>
                <button class="btn btn-danger" style="width: 100px" id="btn-xoa-vai-tro"><i class="far fa-trash-alt"></i> Xóa</button>
            </div>
        </div>
    </div>
</div>


<!-- Main content -->
<section class="content" id="pageBody">
    <div class="row">
        <div class="col-lg-4 ">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Danh mục Phòng Ban</span>
                </div>
                <div class="buifmaopct">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input type="text" class="form-control" id="bimo1" placeholder="Tên, mã phòng ban">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <button class="btn btn-primary" id="btn-search-1" style="display: block"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buifmaoptb">
                <table class="table table-hover table-condensed table-striped pointer">
                    <tbody id="table-phong-ban" class="danh-muc-phong-ban">
                    <tr>
                        <th>STT</th>
                        <th>Tên phòng ban</th>
                        <th>Mã phòng ban</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>10</td>
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

        <%--        end--%>
        <div class="col-lg-4 ">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Danh mục chức vụ</span>
                </div>
                <div class="buifmaopct">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input type="text" class="form-control" id="bimo2" placeholder="Tên chức vụ">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <button class="btn btn-primary" style="display: block" id="btn-search-2"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buifmaoptb">
                <table class="table table-hover table-condensed table-striped pointer">
                    <tbody id="table-chuc-vu" class="danh-muc-chuc-vu">
                    <tr>
                        <th>STT</th>
                        <th>Tên chức vụ</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>10</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="receivepagi">
                <div class="pagi" id="pagination2">
                    <div class="paginationjs">
                    </div>
                </div>
            </div>
        </div>
        <%--        END -------------------------------END--%>

        <div class="col-lg-4 ">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Danh mục Vai trò</span>
                </div>
                <div class="buifmaopct">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li form-group">
                                <input type="text" class="form-control" id="bimo3" placeholder="Tên vai trò">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <button class="btn btn-primary" id="btn-search-3" style="display: block"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buifmaoptb">
                <table class="table table-hover table-condensed table-striped pointer">
                    <tbody id="table-vai-tro" class="danh-muc-vai-tro">
                    <tr>
                        <th>STT</th>
                        <th>Tên vai trò</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>10</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="receivepagi">
                <div class="pagi" id="pagination3">
                    <div class="paginationjs">
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
<script src="resources/model/nhan_vien/ajax_phong_ban.js" type="text/javascript"></script>
<script src="resources/model/nhan_vien/ajax_chuc_vu.js" type="text/javascript"></script>
<script src="resources/model/nhan_vien/ajax_vai_tro.js" type="text/javascript"></script>
<script src="resources/pages/quan_ly_thong_tin/ajax_danh_muc_nhan_vien.js" type="text/javascript"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>