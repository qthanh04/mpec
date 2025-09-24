<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<style>
    input {
        outline: 0!important;
        border-width: 0 0 2px!important;
        border-color: #b1bcae!important;
        background: transparent;
        text-align: center;
    }
    input:focus {
        border-color: #BCACBC!important;
    }

</style>
<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Chi tiết phiếu trả hàng cho nhà cung cấp</span>
        </div>

        <div class="row">
            <div class="col-md-8">
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

            <div class="col-md-4">
                <h3 class="text-center">Thông tin trả hàng</h3>
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
                        <label>Tổng tiền mua : </label>
                        <span class="text-center" id="giaGoc" style="margin-left: 130px"></span>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-12">
                        <label>Tổng tiền hàng trả : </label>
                        <span class="text-center" id="phaiTra" style="margin-left: 110px"></span>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-12">
                        <label>Cần trả khách : </label>
                        <span class="text-center" id="canTra" style="margin-left: 135px"></span>
                    </div>
                </div>

                <div class="row ">
                    <div class="col-md-12">
                        <input type="text" class="none-arrow" id="ly-do" style="padding : 10px;margin-right:25px;width:100%"
                               placeholder="Lý do">
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12 text-center">
                        <button class="btn btn-success" style="width: 120px;margin-top:15px" id="btn-tra-hang"><i class="fa fa-reply-all"></i> Trả hàng</button>
                        <button class="btn btn-primary" style="width: 100px;margin-top:15px" id="btn-print-pdf" disabled><i class="fa fa-print"></i> In</button>
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
<script src="resources/model/phieu_nhap_de_tra/ajax_nhap_chi_tiet_de_tra.js"></script>
<script src="resources/model/phieu_nhap_de_tra/ajax_nhap_de_tra.js"></script>
<script src="resources/pages/quan_ly_giao_dich/tra_hang_cho_nha_cung_cap/ajax_chi_tiet_phieu_nhap_de_tra.js"></script>
<script src="resources/model/nhan_vien/ajax_nhan_vien.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>