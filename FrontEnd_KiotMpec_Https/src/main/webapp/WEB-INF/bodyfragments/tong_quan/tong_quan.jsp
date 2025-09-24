<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/dist/css/style2.css">
<%--<link rel="stylesheet" href="resources/plugins/assets/vendor/bootstrap/css/bootstrap.min.css">--%>
<link href="resources/plugins/assets/vendor/fonts/circular-std/style.css" rel="stylesheet">
<link rel="stylesheet" href="resources/plugins/assets/libs/css/style.css">
<link rel="stylesheet" href="resources/plugins/assets/vendor/fonts/fontawesome/css/fontawesome-all.css">
<link rel="stylesheet" href="resources/plugins/assets/vendor/vector-map/jqvmap.css">
<link rel="stylesheet" href="resources/plugins/assets/vendor/jvectormap/jquery-jvectormap-2.0.2.css">
<link rel="stylesheet" href="resources/plugins/assets/vendor/fonts/flag-icon-css/flag-icon.min.css">

<div class="container-fluid" id="pageBody">
    <div class="container-fluid dashboard-content">
        <div class="row">
            <div class="span12 text-center" id="sub_title" style="font-size: 36px;">
                TỔNG HỢP THÁNG 01
            </div>
        </div>

        <div class="row">
            <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12 col-12">
                <div class="card border-3 border-top border-top-primary">
                    <div class="card-body">
                        <h5 class="text-muted">Sales</h5>
                        <div class="metric-value d-inline-block">
                            <h1 class="mb-1" id="sales">$12099</h1>
                        </div>
                        <div class="metric-label d-inline-block float-right text-success font-weight-bold">
                            <span class="icon-circle-small icon-box-xs text-success bg-success-light"><i class="fa fa-fw fa-arrow-up"></i></span><span class="ml-1">5.86%</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end sales  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- new customer  -->
            <!-- ============================================================== -->
            <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12 col-12">
                <div class="card border-3 border-top border-top-primary">
                    <div class="card-body">
                        <h5 class="text-muted">New Customer</h5>
                        <div class="metric-value d-inline-block">
                            <h1 class="mb-1" id="new_custormer">1245</h1>
                        </div>
                        <div class="metric-label d-inline-block float-right text-success font-weight-bold">
                            <span class="icon-circle-small icon-box-xs text-success bg-success-light"><i class="fa fa-fw fa-arrow-up"></i></span><span class="ml-1">10%</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end new customer  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- visitor  -->
            <!-- ============================================================== -->
            <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12 col-12">
                <div class="card border-3 border-top border-top-primary">
                    <div class="card-body">
                        <h5 class="text-muted">Visitor</h5>
                        <div class="metric-value d-inline-block">
                            <h1 class="mb-1" id="visitor">13000</h1>
                        </div>
                        <div class="metric-label d-inline-block float-right text-success font-weight-bold">
                            <span class="icon-circle-small icon-box-xs text-success bg-success-light"><i class="fa fa-fw fa-arrow-up"></i></span><span class="ml-1">5%</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end visitor  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- total orders  -->
            <!-- ============================================================== -->
            <div class="col-xl-3 col-lg-3 col-md-6 col-sm-12 col-12">
                <div class="card border-3 border-top border-top-primary">
                    <div class="card-body">
                        <h5 class="text-muted">Total Orders</h5>
                        <div class="metric-value d-inline-block">
                            <h1 class="mb-1" id="total_order">1340</h1>
                        </div>
                        <div class="metric-label d-inline-block float-right text-danger font-weight-bold">
                            <span class="icon-circle-small icon-box-xs text-danger bg-danger-light bg-danger-light "><i class="fa fa-fw fa-arrow-down"></i></span><span class="ml-1">4%</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end total orders  -->
            <!-- ============================================================== -->
            <div class="row">
                <!-- ============================================================== -->
                <!-- line chart  -->
                <!-- ============================================================== -->
                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">

                    <div class="row">
                        <div class="col-md-12">
                            <div class="caifop1li form-group">
                                <span>Chọn ngày</span>
                                <div class="input-group date">
                                    <input type="text" class="form-control border-gray date-vn" id="choose_day">
                                </div>
                                <span class="help-block">Help block with success</span>
                            </div>
                        </div>
                    </div>


                    <div class="card">
                        <h5 class="card-header">Doanh thu trong tuần</h5>
                        <div class="card-body">
                            <canvas id="chartjs_week"></canvas>
                        </div>
                    </div>
                </div>
                <!-- ============================================================== -->
                <!-- end line chart  -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- bar chart  -->
                <!-- ============================================================== -->
                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                    <br>
                    <br>
                    <br>
                    <div class="card">
                        <h5 class="card-header">Doanh thu trong tháng </h5>
                        <div class="card-body">
                            <canvas id="chartjs_month"></canvas>
                        </div>
                    </div>
                </div>
                <!-- ============================================================== -->
                <!-- end bar chart  -->
                <!-- ============================================================== -->
            </div>

            <div class="row">
                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="card">
                        <h5 class="card-header">Doanh thu trong năm</h5>
                        <div class="card-body">
                            <canvas id="chartjs_year"></canvas>
                        </div>
                    </div>
                </div>

                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
                    <div class="card">
                        <h5 class="card-header">Doanh thu theo giờ trong tháng </h5>
                        <div class="card-body">
                            <canvas id="chartjs_hour"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="container-fluid">
        <div class="row">
            <!-- ============================================================== -->

            <!-- ============================================================== -->

            <!-- recent orders  -->
            <!-- ============================================================== -->
            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="card">
                    <h5 class="card-header">Recent Orders</h5>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover table-condensed table-striped" id="table-hoa-don">
                                <tbody>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã hóa đơn</th>
                                    <th>Thời gian</th>
                                    <th>Tên nhân viên</th>
                                    <th>Tên khách hàng</th>
                                    <th>Tổng tiền</th>
                                    <th>Chi nhánh</th>
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
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end recent orders  -->
        </div>
    </div>


    <div class="container-fluid">
        <div class="row">
            <!-- ============================================================== -->

            <!-- ============================================================== -->

            <!-- recent orders  -->
            <!-- ============================================================== -->
            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
                <div class="card">
                    <h5 class="card-header"> Top Revenue By Employee</h5>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-hover table-condensed table-striped" id="table-top-employee">
                                <tbody>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã nhân viên</th>
                                    <th>Tên nhân viên</th>
                                    <th>Tổng doanh thu</th>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- end recent orders  -->

            <!-- ============================================================== -->
            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6">
                <div class="card">
                    <h5 class="card-header"> Top Revenue By Branch</h5>
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table" id="table-revenue-branch">
                                <tbody>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã chi nhánh</th>
                                    <th>Tên chi nhánh</th>
                                    <th>Tổng doanh thu</th>
                                </tr>
                                <tr>
                                    <td>1</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>4</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>5</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

<script>
    (function(window, document, $, undefined) {
        "use strict";
        $(function() {

            if ($('#chartjs_line').length) {
                var ctx = document.getElementById('chartjs_line').getContext('2d');

                var myChart = new Chart(ctx, {
                    type: 'line',

                    data: {
                        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        datasets: [{
                            label: 'Almonds',
                            data: [12, 19, 3, 17, 6, 3, 7],

                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 2
                        }, {
                            label: 'Cashew',
                            data: [2, 29, 5, 5, 2, 3, 10],
                            backgroundColor: "rgba(255, 64, 123,0.5)",
                            borderColor: "rgba(255, 64, 123,0.7)",
                            borderWidth: 2
                        }]

                    },
                    options: {
                        legend: {
                            display: true,
                            position: 'bottom',

                            labels: {
                                fontColor: '#71748d',
                                fontFamily: 'Circular Std Book',
                                fontSize: 14,
                            }
                        },

                        scales: {
                            xAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d',
                                }
                            }],
                            yAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d',
                                }
                            }]
                        }
                    }



                });
            }


            if ($('#chartjs_bar').length) {
                var ctx = document.getElementById("chartjs_bar").getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ["M", "T", "W", "R", "F", "S", "S"],
                        datasets: [{
                            label: 'Almonds',
                            data: [12, 19, 3, 17, 28, 24, 7],
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 2
                        }, {
                            label: 'Cashew',
                            data: [30, 29, 5, 5, 20, 3, 10],
                            backgroundColor: "rgba(255, 64, 123,0.5)",
                            borderColor: "rgba(255, 64, 123,0.7)",
                            borderWidth: 2
                        }]
                    },
                    options: {
                        legend: {
                            display: true,
                            position: 'bottom',

                            labels: {
                                fontColor: '#71748d',
                                fontFamily: 'Circular Std Book',
                                fontSize: 14,
                            }
                        },

                        scales: {
                            xAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d',
                                }
                            }],
                            yAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d',
                                }
                            }]
                        }
                    }


                });
            }



        });

    })(window, document, window.jQuery);
</script>

<script src="resources/pages/tong_quan/ajax_bieu_do_doanh_thu.js"></script>
<script src="resources/model/tong_quan/ajax_bieu_do_doanh_thu.js"></script>
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/plugins/chart.js/Chart.bundle.js"></script>
<script src="resources/plugins/chart.js/Chart.js"></script>

</div>