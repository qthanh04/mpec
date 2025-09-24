<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Phiếu nhập chi tiết</span>
        </div>

        <div class="container-fluid">
            <div class="row">
                <%--    ========       Col-8=========--%>
                <div class="col-md-7">
                    <div class="row">
                        <div class="buifmaoptb table-responsive">
                            <table class="table table-hover table-condensed table-striped" id="sum_table">
                                <tbody id="table-hang-hoa">
                                <tr>
                                    <th>STT</th>
                                    <th>Mã hàng hóa</th>
                                    <th>Tên hàng hóa</th>
                                    <th>Số lượng</th>
                                    <th>Giá nh</th>
                                    <th>Thành tiền</th>
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
                </div>
                <%--===========Col5===============--%>
                <div class="col-md-5">
                    <h3 class="text-center">Thông tin phiếu nhập</h3>
                    <h4 id="maPhieuNhap">MÃ PHIẾU NHẬP :</h4>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="tenChiNhanh">Chi nhánh :</label>
                        </div>

                        <div class="col-md-6">
                            <label id="nhaCungCap">Nhà cung cấp:</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="ngayNhap">Ngày nhập :</label>
                        </div>
                        <div class="col-md-6">
                            <label id="diaChi">Địa chỉ :</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="tenNhanVien">Nhân Viên :</label>
                        </div>
                        <div class="col-md-6">
                            <label id="dienThoaiNhaCungCap">Điện thoại:</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label>Tổng tiền</label>
                            <span class="text-center" id="giaGoc" style="margin-left: 130px"></span>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-12">
                            <label>Tổng tiền đã trả </label>
                            <span class="text-center" id="phaiTra" style="margin-left: 95px"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label>Tổng tiền phải trả</label>
                            <span class="text-center" id="canTra" style="margin-left: 85px"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12 text-center">
                            <button class="btn btn-primary" style="width: 100px" id="btn-print-pdf"><i class="fa fa-print"></i> Print</button>
                        </div>
                    </div>
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
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/model/giao_dich/nhap_hang/ajax_phieu_nhap_chi_tiet.js"></script>
<script src="resources/pages/quan_ly_giao_dich/nhap_hang/ajax_nhap_hang_chi_tiet.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>