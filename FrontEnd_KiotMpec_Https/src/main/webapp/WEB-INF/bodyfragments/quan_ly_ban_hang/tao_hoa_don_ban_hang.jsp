<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<!-- Main content -->
<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Danh sách trả hàng khách</span>
        </div>

        <div class="buifmaopct">
            <div class="row">
                <div class="col-md-8">
                    <div class="row">
                        <div class="col-md-5">
                            <div class="caifop1li">
                                <label for="bimo2">Thuộc chi nhánh</label>
                                <select class="js-example-basic-single" name="state" id="bimo2">
                                    <option value=0>Tất cả</option>
                                    <option value=1>Cocacola</option>
                                    <option value=2>Pepsi</option>
                                    <option value=3>Fanta</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="caifop1li form-group">
                                <label for="bimo1">Nhập thông tin tìm kiếm</label>
                                <input type="text" class="form-control" id="bimo1">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label style="opacity: 0">btn</label>
                            <button class="btn btn-primary" id="btn-1" style="display: block"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                        </div>
                    </div>
                    <style>
                        .my-custom-scrollbar {
                            position: relative;
                            height: 600px;
                            overflow: auto;
                        }

                        .table-wrapper-scroll-y {
                            display: block;
                        }
                    </style>
                    <div class="row">

                        <div class="buifmaoptb my-custom-scrollbar table-wrapper-scroll-y">
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
                                <tr>
                                    <td>10</td>
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
                            <div class="page-link">
                                <a href="hang-hoa?id=0"><i class="fas fa-plus-circle"></i></a>
                            </div>
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
                    <h3 id="tenCongTy" class="text-center">Công ty : </h3>
                    <h4 id="maHoaDon">MÃ HÓA ĐƠN :</h4>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="tenChiNhanh">Chi nhánh :</label>
                        </div>

                        <div class="col-md-6">
                            <label id="khachHang">Khách Hàng:</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <label id="ngayBan">Ngày bán :</label>
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
                            <label id="dienThoaiKhach">Điện thoại khách:</label>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-6">
                            <label>Tổng giá gốc hàng mua</label>
                        </div>
                        <div class="col-md-6 text-center">
                            <p id="giaGoc"></p>
                        </div>
                    </div>

                    <div class="row ">
                        <div class="col-md-6 ml-3">
                            <label>Tổng tiền hàng trả </label>
                        </div>
                        <div class="col-md-6 text-center">
                            <p id="phaiTra"></p>
                        </div>
                    </div>

                    <div class="row ">
                        <div class="col-md-6">
                            <label>Cần trả khách</label>
                        </div>
                        <div class="col-md-6 text-center">
                            <p id="canTra">10000</p>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <button class="btn btn-success" style="width: 100px" id="btn-them-nhom-hang"><i class="fa fa-reply-all"></i> Trả hàng
                            </button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-primary" style="width: 100px" id="btn-sua-nhom-hang"><i class="fa fa-print"></i> In</button>
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-danger" style="width: 100px" id="btn-xoa-nhom-hang"><i class="fas fa-fw fa-file-import"></i> Xuất excel</button>
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