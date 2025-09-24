<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/30/2021
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách file </span>
            </div>

            <div class="buifmaopct">
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo1" placeholder="Từ ngày">
                            </div>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo2" placeholder="Đến ngày">
                            </div>
                            <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <select type="text" class="form-control" id="bimo3">
                                <option>Hóa đơn</option>
                                <option>Danh sách phiếu nhập</option>
                                <option>Danh sách hàng hóa</option>
                                <option>Phiếu nhập</option>
                                <option>Danh sách phiếu thu</option>
                                <option>Danh sách phiếu chi</option>
                                <option>Phiếu trả nhà cung cấp</option>
                                <option>Phiếu trả khách</option>
                                <option>Danh sách ca làm việc</option>
                                <option>Tất cả</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo4" placeholder="Mã phiếu">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 text-center">
                        <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>
                </div>

                <div class="buifmaoptb table-responsive">
                    <table class="table table-hover table-condensed table-striped" id="table-file-excel">
                        <tbody>
                        <tr>
                            <th>STT</th>
                            <th>Mã phiếu</th>
                            <th>Tên loại</th>
                            <th>Thời gian tạo</th>
                            <th>Kiểu File</th>
                        </tr>
                        <tr>
                            <td>1</td>
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
                        </tr>
                        <tr>
                            <td>3</td>
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
                        </tr>
                        <tr>
                            <td>5</td>
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
                        </tr>
                        <tr>
                            <td>7</td>
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
                        </tr>
                        <tr>
                            <td>9</td>
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
    </div>
</section>
<script src="resources/model/file_excel/ajax_file_excel.js"></script>
<script src="resources/pages/quan_ly_file_excel/ajax_danh_sach_file_excel.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>

