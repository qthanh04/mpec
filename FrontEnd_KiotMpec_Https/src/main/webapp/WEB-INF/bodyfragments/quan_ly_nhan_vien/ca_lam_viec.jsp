<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="resources/dist/css/qlttdn.css">
<link href="resources/plugins/assets/vendor/full-calendar/css/fullcalendar.css" rel="stylesheet"/>
<link href="resources/plugins/assets/vendor/full-calendar/css/fullcalendar.print.css" rel="stylesheet" media="print"/>
<link rel="stylesheet" href="resources/dist/js/utils/selectize.bootstrap3.min.css">
<div class="modal fade" id="modal-dat-lich" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><i class="far fa-plus"></i> Đặt lịch làm việc</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="row">

                    <div class="col-md-3">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label for="chonNgay">Chọn ngày</label>
                                <input type="date" placeholder="YYYY-MM-DD" value="2021/05/09"
                                       class="form-control border-gray date-vn" id="chonNgay">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>

                    <%--                    <div class="col-md-1">--%>
                    <%--                        <div class="caifop1li form-group">--%>
                    <%--                            <div class="input-group">--%>
                    <%--                                <label for="tuNgay">Hoặc</label>--%>
                    <%--                            </div>--%>

                    <%--                        </div>--%>
                    <%--                    </div>--%>

                    <%--                    <div class="col-md-4">--%>
                    <%--                        <div class="caifop1li form-group">--%>
                    <%--                            <div class="input-group">--%>
                    <%--                                <label for="tuNgay">Từ ngày </label>--%>
                    <%--                                <input type="date" class="form-control border-gray date-vn" id="tuNgay">--%>
                    <%--                                <span class="help-block">Help block with success</span>--%>
                    <%--                            </div>--%>

                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <%--                    <div class="col-md-4">--%>
                    <%--                        <div class="caifop1li form-group">--%>
                    <%--                            <div class="input-group">--%>
                    <%--                                <label for="denNgay">Đến ngày </label>--%>
                    <%--                                <input type="date" class="form-control border-gray date-vn" id="denNgay"--%>
                    <%--                                       placeholder="Đến ngày">--%>
                    <%--                                <span class="help-block">Help block with success</span>--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label for="search-ca">Chọn ca</label>
                            <select class="form-control select2 select-hang-hoa" id="search-ca"
                                    placeholder="Chọn ca">
                                <option></option>
                            </select>
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="caifop1li form-group">
                                <label for="ghiChu">Chọn nhân viên</label>
                                <select id="search-nhan-vien" class="selectize-control control-form single"
                                        data-placeholder="Nhập thông tin tìm kiếm nhân viên ">
                                </select>
                                <span class="help-block">Help block with error</span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="ghiChu"
                                   placeholder="Nhập ghi chú">
                        </div>
                    </div>
                </div>
                <hr>

                <div class="buifmaoptb table-responsive">
                    <table id="table-nhan-vien" class="table table-hover table-condensed table-striped">
                        <tr>
                            <th>Mã nhân viên</th>
                            <th>Tên nhân viên</th>
                            <th>Số điện thoại</th>
                            <th>Trạng thái</th>
                        </tr>
                        <tbody>
                        <td>1</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>


            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btn-them-moi"><i class="far fa-plus"></i> Thêm mới</button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="modal-them-ca" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><i class="far fa-plus"></i> Thêm mới ca làm việc</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">


                <div class="row">

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="tenCa"
                                   placeholder="Tên ca">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <input type="text" class="form-control" id="soNhanVien"
                                   placeholder="Số nhân viên tối đa">
                            <span class="help-block">Help block with error</span>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label>Từ </label>
                                <input class="form-control" type="time" value="13:45:00" id="timeCheckIn">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label>Đến </label>
                                <input class="form-control" type="time" value="13:45:00" id="timeCheckOut">
                                <span class="help-block">Help block with success</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <label>Thời gian cho phép nhân viên chấm công <i
                                    class="fas fa-info-circle"></i></label>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label class="col-2 col-form-label">Cho phép checkin từ </label>
                                <input class="form-control" type="time" value="13:45:00" id="checkInStart">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label>Cho phép checkin đến </label>
                                <input class="form-control" type="time" value="13:45:00" id="checkInEnd">
                                <span class="help-block">Help block with success</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label class="col-2 col-form-label">Cho phép checkout từ </label>
                                <input class="form-control" type="time" value="13:45:00" id="checkOutStart">
                                <span class="help-block">Help block with success</span>
                            </div>

                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="caifop1li form-group">
                            <div class="input-group">
                                <label>Cho phép checkout đến </label>
                                <input class="form-control" type="time" value="13:45:00" id="checkOutEnd">
                                <span class="help-block">Help block with success</span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="col-md-12" style="margin-top: 30px">
                    <div class="row">
                        <label class="col-md-3"><strong>Trạng thái</strong></label>
                        <label class="col-md-2">Hoạt động</label>
                        <input class="col-md-1" type="radio" name="trangThai" value="1" id="hd" true>
                        <label class="col-md-3">Ngừng hoạt động</label>
                        <input class="col-md-1" type="radio" name="trangThai" value="0" id="nhd">
                    </div>
                    <span class="help-block">Help block with error</span>
                </div>


            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btn-add-ca-lam-viec"><i class="far fa-plus"></i> Thêm mới</button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fas fa-ban"></i> Đóng</button>
            </div>
        </div>
    </div>
</div>

<section class="content" id="pageBody">
    <div class="buifmaop">
        <div class="buifmaoptitle">
            <span class="page-title">Quản lý ca làm việc</span>
        </div>

        <div class="row">
            <div class="col-md-7">

            </div>
            <div class="col-md-3">
                <button class="btn btn-primary" id="btn-search-3" style="display: block" data-toggle="modal"
                        data-target="#modal-dat-lich"><i class="far fa-plus"></i> Đặt lịch ca làm việc
                </button>
            </div>

            <div class="col-md-2">
                <button class="btn btn-primary" id="btn-search-4" style="display: block" data-toggle="modal"
                        data-target="#modal-them-ca"><i class="far fa-plus"></i> Thêm mới ca làm việc
                </button>
            </div>

        </div>

        <!-- wrapper  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- simple calendar -->
        <!-- ============================================================== -->
        <div class="row">
            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="card">
                    <div class="card-body">
                        <div id='calendar1'></div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Bảng chi tiết ca làm việc -->

        <div class="row" style="margin-top: 15px;">
            <div class="col-md-4">
                <h4>Bảng ca làm việc</h4>
            </div>
            <div class="col-md-4">
                <div class="caifop1li form-group">
                    <input type="text" class="form-control" id="bimo" placeholder="Nhập thông tin tìm kiếm">
                </div>
            </div>
            <div class="col-md-2 text-center" >
                <button class="btn btn-primary" id="btn-search"><i class="fas fa-fw fa-search"></i> Tìm Kiếm</button>
            </div>
            <div class="col-md-2">
                <button class="btn btn-primary" id="btn-excel"><i class="fa fa-print"></i> Print</button>
            </div>

        </div>

        <div class="buifmaopct">
            <div class="buifmaoptb table-responsive">
                <table class="table table-hover table-condensed table-striped" id="table-ca-lam-viec">
                    <tbody>
                    <tr>
                        <th>STT</th>
                        <th>Người dùng ID</th>
                        <th>Ca làm việc ID</th>
                        <th>Ngày tháng</th>
                        <th>Check in</th>
                        <th>Check out</th>
                        <th>Status check in</th>
                        <th>Status check out</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td></td>
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
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <div class="receivepagi">
                    <div class="pagi" id="pagination">
                        <div class="paginationjs">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- end simple calendar -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- events calendar -->
        <!-- ============================================================== -->
        <div class="row">
            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="card">
                    <div class="card-body">
                        <div id='wrap'>
                            <div id='external-events'>
                                <h4>Danh sách nhân viên </h4>
                                <div class='fc-event'>Nhân viên 1</div>
                                <div class='fc-event bg-secondary border-secondary'>Nhân viên 2</div>
                                <div class='fc-event bg-brand border-brand'>Nhân viên 3</div>
                                <div class='fc-event bg-info border-info'>Nhân viên 4</div>
                                <div class='fc-event bg-success border-success'>Nhân viên 5</div>
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" id='drop-remove'>
                                    <label class="custom-control-label" for="drop-remove">Remove after
                                        drop</label>
                                </div>
                            </div>
                            <div id='calendar'></div>
                            <div style='clear:both'></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ============================================================== -->
        <!-- end events calendar -->
        <!-- ============================================================== -->
    </div>
</section>
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2({width: 'resolve'});
        $('.js-example-basic-multiple').select2({width: 'resolve'});
    });
</script>
<script src="resources/plugins/assets/vendor/slimscroll/jquery.slimscroll.js"></script>
<script src="resources/plugins/assets/vendor/full-calendar/js/moment.min.js"></script>
<script src="resources/plugins/assets/vendor/full-calendar/js/fullcalendar.js"></script>
<script src="resources/plugins/assets/vendor/full-calendar/js/jquery-ui.min.js"></script>
<script src="resources/pages/quan_ly_thong_tin/ajax_ca_lam_viec_nhan_vien.js" type="text/javascript"></script>
<script src="resources/plugins/assets/libs/js/main-js.js"></script>
<script src="resources/dist/js/utils/selectize.min.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>