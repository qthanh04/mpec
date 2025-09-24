<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<div class="modal fade" tabindex="-1" role="dialog" id="modal-don-vi-giao-hang"  aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Chọn đơn vị giao hàng</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="buifmaoptb table-responsive">
                    <table class="table table-hover table-condensed table-striped" id="table">
                        <tbody>
                        <tr>
                            <th>STT</th>
                            <th>Tên đơn vị</th>
                            <th>Phí</th>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <p style="font-size: 16px">Hãy chọn đơn vị giao hàng mong muốn</p>
                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li">
                            <label style="font-size: 16px">Đơn vị giao hàng</label>
                            <select class="form-control" name="state" id="select-don-vi-giao-hang">

                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirm-btn"><i class="fas fa-check"></i> Xác nhận</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" id="refuse-btn"><i class="fas fa-ban"></i> Hủy</button>
            </div>
        </div>
    </div>
</div>

<section class="content" id="pageBody">
    <div class="row">
        <%--        bắt đầu--%>
        <div class="col-lg-4 danh-muc">
            <div class="buifmaop">
                <div class="buifmaoptitle">
                    <span class="page-title">Thông tin giao hàng </span><span id="ma1"></span>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <hr/>
    </div>
    <div class="col-lg-12 danh-muc">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Bên Giao</span><span id="ma2"></span>
            </div>
        </div>
    </div>
    <div class="col-lg-12 ">
        <div class="col-lg-6">
            <div class="deliver-infor left">
                <label><strong>Địa chỉ</strong></label>
                <input type="text" class="form-control" id="input-text-1" placeholder="Nhập địa chỉ" name="Address">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Số điện thoại</strong></label>
                <input type="text" class="form-control" id="input-text-2" placeholder="Nhập số điện thoại" name="NumberPhone">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Họ tên</strong></label>
                <input type="text" class="form-control" id="input-text-3" placeholder="Nhập họ tên" name="Name">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="deliver-infor right">
                <div class="info-title"></div>
                <label><strong>Thành phố</strong></label>
                <select class="form-control" name="province" id="select-thanh-pho-ben-giao" placeholder="Chọn thành phố">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Quận-Huyện</strong></label>
                <select class="form-control" name="district" id="select-quan-huyen-ben-giao" placeholder="Chọn Quận-Huyện">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Phường-xã</strong></label>
                <select class="form-control" name="state" id="select-phuong-xa-ben-giao" placeholder="Chọn Phường-Xã">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
    <hr/>
    </div>
    <div class="col-lg-12 danh-muc">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Bên nhận</span><span id="ma3"></span>
            </div>
        </div>
    </div>
    <div class="col-lg-12 ">
        <div class="col-lg-6">
            <div class="deliver-infor left">
                <label><strong>Địa chỉ</strong></label>
                <input type="text" class="form-control" id="input-text-4" placeholder="Nhập địa chỉ" name="Address">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Số điện thoại</strong></label>
                <input type="text" class="form-control" id="input-text-5" placeholder="Nhập số điện thoại" name="NumberPhone">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Họ tên</strong></label>
                <input type="text" class="form-control" id="input-text-6" placeholder="Nhập họ tên" name="Name">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="deliver-infor right">
                <div class="info-title"></div>
                <label><strong>Thành phố</strong></label>
                <select class="form-control" name="province" id="select-thanh-pho-ben-nhan" placeholder="Chọn thành phố">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Quận-Huyện</strong></label>
                <select class="form-control" name="district" id="select-quan-huyen-ben-nhan" placeholder="Chọn Quận-Huyện">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Phường-xã</strong></label>
                <select class="form-control" name="state" id="select-phuong-xa-ben-nhan" placeholder="Chọn Phường-Xã">
                </select>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <hr/>
    </div>
    <div class="col-lg-12 danh-muc">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Hàng hóa</span><span id="ma4"></span>
            </div>
        </div>
    </div>
    <div class="col-lg-12 ">
        <div class="col-lg-6">
            <div class="deliver-infor left">
                <label>
                    <strong>Sản phẩm - Số lượng</strong>
                </label>
                <input type="text" class="form-control" id="input-text-7" placeholder="Tên sản Phẩm" name="SanPham">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Tổng khối lượng(gram)</strong></label>
                <input type="text" class="form-control" id="input-text-8" placeholder="Nhập khối lượng" name="KhoiLuong">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Tổng tiền thu hộ COD</strong></label>
                <input type="text" class="form-control" id="input-text-9" placeholder="Nhập tiền COD" name="TienCOD">
            </div>
        </div>
        <div class="col-lg-6">
            <div class="deliver-infor right">
                <div class="info-title"></div>
                <label><strong>Mã sản phẩm</strong></label>
                <input type="text" class="form-control" id="input-text-10"  name="TienCOD">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Kích thước(Cm)</strong></label>
                <div class="row">
                    <div class="col-lg-2">
                        <input type="text" class="form-control" id="input-text-11" placeholder="Dài" name="Dai">
                    </div>
                    <div class="col-lg-2">
                        <input type="text" class="form-control" id="input-text-12" placeholder="Rộng" name="Rong">
                    </div>
                    <div class="col-lg-2">
                        <input type="text" class="form-control" id="input-text-13" placeholder="Cao" name="Cao">
                    </div>
                </div>
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
                <label><strong>Tổng giá trị hàng hóa</strong></label>
                <input type="text" class="form-control" id="input-text-14" placeholder="Nhập giá trị Khai Giá" name="GiaTriHangHoa">
                <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
            </div>
        </div>
    </div>
    <div class="col-xs-12">
        <hr/>
    </div>
    <div class="col-lg-12 danh-muc">
        <div class="buifmaop">
            <div class="buifmaoptitle">
                <span class="page-title">Lưu ý - Ghi chú</span><span id="ma5"></span>
            </div>
        </div>
    </div>

    <div class="col-lg-12 ">
        <div class="deliver-infor left">
            <label><strong>Ghi chú</strong></label>
            <textarea name="note" class="form-control" id="Ghichu" placeholder="Ví dụ: lấy sản phẩm 1 2 cái, lấy sản phẩm 2 1 cái" rows="3"></textarea>
            <p style="color: red; font-size: 12px; margin-top: 0px; margin-bottom: 0px; font-weight: 500; height: 15px;"></p>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 text-center">
                <button class="btn btn-primary" style="width: 120px" id="btn-gia"><i class="fas fa-truck-moving" ></i> Giao hàng</button>
        </div>
    </div>
</section>
<script src="resources/pages/quan_ly_ban_hang/ajax_don_vi_giao_hang.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/model/ban_hang/ajax_don_vi_giao_hang.js"></script>