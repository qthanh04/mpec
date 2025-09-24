<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Hoá đơn chi tiết</span>
        </div>
        <%--  ====================Row=====================--%>
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
                                    <th>Tên hàng</th>
                                    <th>Số lượng</th>
                                    <th>Giá bán</th>
                                    <th>Đơn vị</th>
                                    <th>Giảm giá</th>
                                    <th>Tổng tiền</th>
                                </tr>

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
                <%--============Col-4=============--%>
                <div class="col-md-5">
                    <h3 class="text-center">Thông tin Hóa Đơn</h3>
                    <h4 id="maHoaDon">MÃ HÓA ĐƠN : </h4>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="khachHang">Khách Hàng : </label>
                        </div>
                        <div class="col-md-6">
                            <label id="tenChiNhanh">Chi nhánh : </label>
                        </div>


                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="ngayBan">Ngày bán : </label>
                        </div>
                        <div class="col-md-6">
                            <label id="diaChi">Địa chỉ : </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="tenNhanVien">Nhân Viên : </label>
                        </div>
                        <div class="col-md-6">
                            <label id="dienThoaiKhach">Điện thoại khách : </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <label>Tổng tiền </label>
                            <span class="text-center" id="tong-tien" style="margin-left: 110px"></span>
                        </div>
                    </div>

                    <div class="row ">
                        <div class="col-md-12 ml-3">
                            <label>Tiền khách trả </label>
                            <span class="text-center" id="tien-khach-tra" style="margin-left: 85px"></span>
                        </div>
                    </div>

                    <div class="row ">
                        <div class="col-md-12">
                            <label>Tiền trả lại khách</label>
                            <span class="ml-3" id="tien-tra-khach" style="margin-left: 70px"></span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12 ">
                            <div class="col-xs-6 text-center">
                                <button class="btn btn-primary" style="width: 100px" id="btn-print-pdf"><i class="fa fa-print"></i> Print</button>
                            </div>
                            <div class="col-xs-6 text-center">
                                <button class="btn btn-primary" style="width: 120px" id="btn-DVGH"><i class="fas fa-truck-moving" ></i> Giao hàng</button>
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
<script src="resources/pages/quan_ly_ban_hang/ajax_hoa_don_chi_tiet.js"></script>
<script src="resources/model/hoa_don/ajax_hoa_don.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>