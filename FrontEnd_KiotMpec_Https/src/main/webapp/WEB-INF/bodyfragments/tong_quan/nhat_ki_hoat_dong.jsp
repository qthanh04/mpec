<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- ============================Main content ================================-->
<section class="content" id="pageBody">
    <div class="buifmaop" >
        <div class="buifmaop" >
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách nhật kí hoạt động</span>
            </div>
            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo1" placeholder="Mã phiếu">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
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
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo2" placeholder="Hoạt động">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>

                    <div class="col-xs-4 text-center">
                        <button class="btn btn-primary" id="btn-search"> <i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>

                    <div class="col-xs-4 text-center">
                        <button class="btn btn-primary" id="btn-excel"><i class="fa fa-print"></i> In danh sách hoạt động</button>
                    </div>

                </div>
            </div>
        </div>

        <div class="buifmaoptb table-responsive" >
            <table class="table table-hover table-condensed table-striped" id="table-nhat-ki-hoat-dong">
                <tbody>
                <tr>
                    <th>STT</th>
                    <th>Mã phiếu</th>
                    <th>Thời gian</th>
                    <th>Tên nhân viên</th>
                    <th>Hoạt động</th>
                    <th>Giá trị</th>
                </tr>
                <tr>
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
</section>
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({ width: 'resolve' });
        $('.js-example-basic-multiple').select2({ width: 'resolve' });
    });
</script>
<script src="resources/model/tong_quan/ajax_nhat_ki_hoat_dong.js"></script>
<script src="resources/pages/tong_quan/ajax_nhat_ki_hoat_dong.js"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>