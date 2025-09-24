<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<%--    modal thay doi trang thai--%>
<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Công Nợ Khách Hàng</span>
        </div>
        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <div class="input-group date">
                            <input type="text" class="form-control border-gray date-vn" id="bimo3"
                                   placeholder="Từ ngày">
                            <span class="help-block">Help block with success</span>
                        </div>

                    </div>
                </div>
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <div class="input-group date">
                            <input type="text" class="form-control border-gray date-vn" id="bimo4"
                                   placeholder="Đến ngày">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <input type="text" class="form-control" id="bimo1" placeholder="Nhập thông tin tìm kiếm">
                        <span class="help-block">Help block with success</span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-6 text-center">
                    <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                </div>
                <div class="col-xs-6 text-center">
                    <button class="btn btn-primary" id="btn-excel"><i class="fa fa-print"></i> In danh sách nhập hàng</button>
                </div>
            </div>
        </div>
    </div>
    <div class="buifmaoptb table-responsive">
        <table class="table table-hover table-condensed table-striped " id="table-cong-no">
            <tbody>
            <tr>
                <th>STT</th>
                <th>Id khách hàng</th>
                <th>Tên khách hàng</th>
                <th>Mã khách hàng</th>
                <th>Tổng tiền mua</th>
                <th>Công nợ</th>
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
    </div>
    <div class="receivepagi">
        <div class="pagi" id="pagination">
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
    });
</script>
<script src="resources/model/cong_no/ajax_cong_no.js"></script>
<script src="resources/pages/cong_no/ajax_cong_no.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
