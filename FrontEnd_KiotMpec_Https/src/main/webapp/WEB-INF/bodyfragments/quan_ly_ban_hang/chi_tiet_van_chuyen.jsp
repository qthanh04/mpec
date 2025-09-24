<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/18/2021
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Chi tiết vận chuyển</span>
        </div>
        <%--  ====================Row=====================--%>
        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-6">
                    <div class="caifop1li form-group">
                        <input type="text" class="form-control" id="bimo3" placeholder="Nhập thông tin tìm kiếm">
                    </div>
                </div>
                <div class="col-md-6 text-center">
                    <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                </div>
            </div>
            <div class="buifmaoptb table-responsive">
                <table class="table table-hover table-condensed table-striped" id="table">
                    <tbody>
                    <tr>
                        <th>STT</th>
                        <th>Mã hóa đơn</th>
                        <th>Tên đối tác</th>
                        <th>Phí</th>
                        <th>Thời gian lấy</th>
                        <th>Thời gian giao</th>
                        <th>Giá trị hóa đơn</th>
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
                <div class="pagi" id="pagination">
                    <div class="paginationjs">
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="resources/model/ban_hang/ajax_chi_tiet_van_chuyen.js"></script>
<script src="resources/pages/quan_ly_ban_hang/ajax_chi_tiet_van_chuyen.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
