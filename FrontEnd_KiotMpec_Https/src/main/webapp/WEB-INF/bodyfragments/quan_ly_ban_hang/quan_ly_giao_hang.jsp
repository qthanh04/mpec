<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<%--    modal chọn đơn vị giao hàng--%>
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Quản Lý Giao Hàng</span>
            </div>
            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo1" placeholder="Mã hóa đơn">
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo2" placeholder="Tên khách hàng">
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo3" placeholder="Tên nhân viên">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <select class="form-control" name="state" id="bimo4">

                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo5" placeholder="Từ ngày">
                                <span class="help-block">Help block with success</span>
                            </div>

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
                    <div class="col-xs-12 text-center">
                        <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="buifmaoptb table-responsive">
            <table class="table table-hover table-condensed table-striped" id="table">
                <tbody>
                <tr>
                    <th>STT</th>
                    <th>Mã hóa đơn</th>
                    <th>Thời gian</th>
                    <th>Tên nhân viên</th>
                    <th>Tên khách hàng</th>
                    <th>Tổng tiền</th>
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
<script src="resources/pages/quan_ly_ban_hang/ajax_quan_ly_giao_hang.js"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/model/ban_hang/ajax_quan_ly_giao_hang.js"></script>

