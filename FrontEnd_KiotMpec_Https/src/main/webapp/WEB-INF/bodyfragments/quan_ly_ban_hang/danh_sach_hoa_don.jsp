<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--==================CSS===============================--%>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">

<%--    modal thay doi trang thai--%>
<div class="modal" tabindex="-1" role="dialog" id="modal-trang-thai">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Thay đổi trạng thái hóa đơn</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p style="font-size: 16px">Đổi trạng thái mới cho hóa đơn bạn đã chọn</p>
                    <p id="trang-thai-cu" style="font-size: 16px"></p>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="caifop1li">
                                <label style="font-size: 16px">Trạng thái mới</label>
                                <select class="js-example-basic-single" name="state" id="select-trang-thai">
                                    <option value="0">Lưu tạm</option>
                                    <option value="1">Đang Giao</option>
                                    <option value="2">Đã Giao</option>
                                    <option value="3">Đang Đóng Gói</option>
                                    <option value="4">Khách Hủy</option>
                                    <option value="5">Đơn Vị Giao Hủy</option>
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

    <!-- ============================Main content ================================-->
<section class="content" id="pageBody">
    <div class="buifmaop" >
        <div class="buifmaop" >
            <div class="buifmaoptitle">
                <span class="page-title">Danh sách hóa đơn</span>
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
                             <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="bimo3" placeholder="Tên nhân viên">
                             <span class="help-block">Help block with success</span>
                        </div>
                    </div>
                </div>

                 <div class="row">
                    <div class="col-md-4">
                        <div class="caifop1li form-group">
                            <div class="input-group date">
                                <input type="text" class="form-control border-gray date-vn" id="bimo5" placeholder="Từ ngày">
                            </div>
                            <span class="help-block">Help block with success</span>
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
                    <div class="col-md-4">
                                      <div class="caifop1li">
                                               <select class="form-control" name="state" id="bimo4">
                                                   <option value="-1">Tất cả trạng thái</option>
                                                   <option value="0">Lưu tạm</option>
                                                   <option value="1">Chưa hoàn thành</option>
                                                   <option value="2">Hoàn thành</option>
                                               </select>
                                               <span class="help-block">Help block with success</span>
                                           </div>
                                       </div>
                </div>


                <div class="row">
                    <div class="col-xs-4">
                        <div class="caifop1li">
                            <select class="js-example-basic-single" name="state" id="bimo0">
                            </select>
                        </div>
                    </div>

                    <div class="col-xs-4 text-center">
                        <button class="btn btn-primary" id="btn-search"> <i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
                    </div>

                     <div class="col-xs-4 text-center">
                                        <button class="btn btn-primary" id="btn-excel"><i class="fa fa-print"></i> In danh sách hóa đơn</button>
                    </div>

                </div>
            </div>
        </div>

        <div class="buifmaoptb table-responsive" >
            <table class="table table-hover table-condensed table-striped" id="table-hoa-don">
                <tbody>
                <tr>
                    <th>STT</th>
                    <th>Mã hóa đơn</th>
                    <th>Thời gian</th>
                    <th>Tên nhân viên</th>
                    <th>Tên khách hàng</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
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
                </tbody>
            </table>
            <div class="page-link">
                <a href="chi-tiet-hoa-don?id=0" target="_blank"><i class="fas fa-plus-circle" ></i></a>
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
        $('.js-example-basic-single').select2({ width: 'resolve' });
        $('.js-example-basic-multiple').select2({ width: 'resolve' });
    });
</script>
<script src="resources/model/ban_hang/ajax_danh_sach_hoa_don.js"></script>
<script src="resources/pages/quan_ly_ban_hang/ajax_danh_sach_hoa_don.js"></script>
<script src="resources/model/chi_nhanh/ajax_chi_nhanh.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>