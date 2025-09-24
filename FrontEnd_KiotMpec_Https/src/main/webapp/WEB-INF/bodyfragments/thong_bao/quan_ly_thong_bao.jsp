<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<section class="content" id="pageBody">
    <!-- ============================Main content ================================-->
    <div class="buifmaop">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách thông báo</span>
            </div>
            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo1" placeholder="Tiêu đề">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo2" placeholder="Người nhận">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo5" placeholder="Từ ngày">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo6" placeholder="Đến ngày">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 text-center">
                        <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="buifmaoptb table-responsive">
            <table class="table table-hover table-condensed table-striped" id="table-bao-cao">
                <tbody>
                <tr>
                    <th>STT</th>
                    <th>Thời gian</th>
                    <th>Tiêu đề</th>
                    <th>Nội dung</th>
                    <th>Người nhận</th>
                    <th>Yêu cầu phản hồi</th>
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
            <div class="page-link">
                <a href="thong-bao" target="_blank"><i class="fas fa-plus-circle"></i></a>
            </div>
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
        $('.js-example-basic-single').select2({width: 'resolve'});
        $('.js-example-basic-multiple').select2({width: 'resolve'});
    });
</script>
<script src="resources/model/thong_bao/ajax_quan_ly_thong_bao.js"></script>
<script src="resources/pages/thong_bao/ajax_quan_ly_thong_bao.js"></script>
<%--<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>--%>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>