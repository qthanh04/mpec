//================ Declare variable===================//
var monthChart, weekChart, yearChart, hourChart, chooseDay, subTitle, table, table_revenue_employee,
    table_revenue_branch;
var arr = [];

//=============== Function main ===================//

$(function () {

    //=========Mapping variabe and id=========//
    monthChart = $("#chartjs_month");
    weekChart = $("#chartjs_week");
    yearChart = $("#chartjs_year");
    hourChart = $("#chartjs_hour");
    chooseDay = $("#choose_day");
    subTitle = $("#sub_title");
    table = $("#table-hoa-don");
    table_revenue_employee = $("#table-top-employee");
    table_revenue_branch = $("#table-revenue-branch");
    let d = new Date();
    let year = d.getFullYear();
    let month = d.getMonth() + 1;
    let week = d.getWeek();
    let day = d.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (week < 10) {
        week = '0' + week;
    }
    if (day < 10) {
        day = '0' + day;
    }

    let endDay = year + "-" + month + "-"
    let startDay = year + "-" + month + "-01"
    chooseDay.val(year + "-" + month + "-" + day)
    switch (parseInt(month)) {
        case 2:
            if (year % 4 == 0) {
                endDay += "29";
            } else {
                endDay += "28";
            }
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            endDay += "30";
            break;
        default:
            endDay += "31";
            break;
    }
    startDay += "T00:00:00.000"
    endDay += "T00:00:00.000"

    // let str = year + "/" + month + "/" + day;
    let str = day + "/" + month + "/" + year;
    subTitle.text("TỔNG HỢP THÁNG " + month);
    loadCharts(year, month, week, startDay, endDay);
    clickChooseDay(year, month, week, startDay, endDay);
    setViewHoaDon();
    call_topRevenueEmployee(month,year);
    call_topRevenueBranch(month,year);
    call_actionRecent();

    // setInterval(function () {call_actionRecent()}, 5000);//request every x seconds
    call_actionRecent();//nhat-ki-hoat-dong
})

function call_actionRecent(){//nhat-ki-hoat-dong
    setTimeout(function()
    {
        let rs = null;
         $.ajax({
            type: 'GET',
            // headers: {
            //     "Authorization": ss_lg
            // },
            dataType: "json",
            url: "http://localhost:8181/api/v1/public/phieu-hoat-dong/find-all?page=1&size=10",
            success: function (result) {
                rs = result;
                console.log("rs"+JSON.stringify(rs));
            }
        });
    }, 5000); // This will "refresh" every 1 second
}


const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
    'August', 'September', 'October', 'November', 'December']

const weeks = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

Date.prototype.getWeek = function () {

    // 2/1/2000 la chu nhat
    let dayName = 0;
    let index = 0;
    for (i = 2000; i < this.getFullYear(); i++) {
        if (i % 4 == 0) {
            index += 2;
        } else {
            index += 1;
        }
    }
    dayName = index % 7 - 1;
    let date = new Date(Date.UTC(this.getFullYear(), this.getMonth(), this.getDate()));
    let yearStart = new Date(Date.UTC(this.getFullYear(), 0, 1));
    let diff = Math.ceil((((date - yearStart) / 86400000)));
    let numberOfWeek;
    if (diff + dayName < 7) {
        numberOfWeek = 0;
    } else {
        numberOfWeek = Math.ceil((diff - (6 - dayName)) / 7);
    }
    return numberOfWeek;
};

//=============== Function detail ===================//

//=================== Function ===================//
function countNewCus(startDay, endDay) {
    countNewCustormer(startDay, endDay).then(rs => {
        if (rs.message == 'success') {
            $("#new_custormer").text(rs.data);
        } else {
            alterWarning("Server Error");
        }
    })
}

//=================== Function ===================//
function countCustomerTrans(startDay, endDay) {
    countCustormerTransaction(startDay, endDay).then(rs => {
        if (rs.message == 'success') {
            $("#visitor").text(rs.data);
        } else {
            alterWarning("Server Error");
        }
    })
}

//=================== Function ===================//
function countBillByTime(startDay, endDay) {
    countBill(startDay, endDay).then(rs => {
        if (rs.message == 'success') {
            $("#total_order").text(rs.data);
        } else {
            alterWarning("Server Error");
        }
    })
}

//=================== Function ===================//
function sumBillByTime(startDay, endDay) {
    sumBill(startDay, endDay).then(rs => {
        if (rs.message == 'success') {
            $("#sales").text(formatMoney(rs.data) + " VNĐ");
        } else {
            alterWarning("Server Error");
        }
    })
}

//=================== Function ===================//
function clickChooseDay() {
    chooseDay.change(function () {
        let from = chooseDay.val().split("/");
        let date = new Date(from[2], from[1] - 1, from[0]);
        let year = chooseDay.val().substr(6, 4);
        let month = chooseDay.val().substr(3, 2);
        let week = date.getWeek();
        subTitle.text("TỔNG HỢP THÁNG " + month + " NĂM " + year);


        let endDay = year + "-" + month + "-"
        let startDay = year + "-" + month + "-01"

        switch (parseInt(month)) {
            case 2:
                if (year % 4 == 0) {
                    endDay += "29";
                } else {
                    endDay += "28";
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                endDay += "30";
                break;
            default:
                endDay += "31";
                break;
        }
        startDay += "T00:00:00.000"
        endDay += "T00:00:00.000"

        loadCharts(year, month, week, startDay, endDay, date);
    })
}

//=================== Function ===================//
function loadCharts(year, month, week, startDay, endDay) {
    $("#pageBody").LoadingOverlay("show");
    callBieuDoDoanhThuTuan(year, week);
    callBieuDoDoanhThuThang(year, month);
    callBieuDoDoanhThuNam(year);
    callBieuDoDoanhThuGioTrongThang(year, month);
    countCustomerTrans(startDay, endDay);
    countNewCus(startDay, endDay);
    countBillByTime(startDay, endDay);
    sumBillByTime(startDay, endDay);

}

//=================== Function ===================//
function callBieuDoDoanhThuThang(year, month) {
    bieuDoDoanhThuThang(year, month).then(rs => {
        let label = [];
        if (month === 2) {
            if (year % 4 === 0) {
                label = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
                    '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                    '21', '22', '23', '24', '25', '26', '27', '28', '29'];
            } else {
                label = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
                    '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                    '21', '22', '23', '24', '25', '26', '27', '28'];
            }
        } else if (month === 4 || month === 6 || month === 9 || month == 11) {
            label = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
                '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                '21', '22', '23', '24', '25', '26', '27', '28', '29', '30'];
        } else {
            label = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10',
                '11', '12', '13', '14', '15', '16', '17', '18', '19', '20',
                '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
        }
        let revenue = [];
        let lastRevenue = [];
        lastMonth = month;
        lastYear = year;
        if (month > 1) {
            lastMonth -= 1;
        } else {
            lastYear -= 1;
            lastMonth = 12;
        }
        if (rs.message === "found") {
            for (i = 0, j = 0; i < label.length; i++) {
                if (j === rs.data.length) {
                    revenue.push(0);
                } else {
                    let day = rs.data[j].x.substr(8, 10);
                    if (label[i] === day) {
                        revenue.push(rs.data[j].y);
                        j++;
                    } else {
                        revenue.push(0);
                    }
                }
            }
            bieuDoDoanhThuThang(lastYear, lastMonth).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < label.length; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            let day = rs2.data[j].x.substr(8, 10);
                            if (label[i] === day) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else if (rs2.message == 'not found') {
                    for (i = 0; i < label.length; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = monthChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "line",
                    data: {
                        labels: label,
                        datasets: [{
                            label: 'Revenue in current month',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 1
                        }, {
                            label: 'Revenue in last month',
                            data: lastRevenue,
                            backgroundColor: "rgba(255, 255, 255,0.5)",
                            borderColor: "rgba(240,128,128,0.7)",
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in " + months[month - 1],
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })
        } else if (rs.message === "not found") {
            for (i = 0; i < label.length; i++) {
                revenue[i] = 0;
            }
            bieuDoDoanhThuThang(lastYear, lastMonth).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < label.length; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            let day = rs2.data[j].x.substr(8, 10);
                            if (label[i] === day) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else if (rs2.message == 'not found') {
                    for (i = 0; i < label.length; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = monthChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "line",
                    data: {
                        labels: label,
                        datasets: [{
                            label: 'Revenue',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 1
                        }, {
                            label: 'Revenue in last month',
                            data: lastRevenue,
                            backgroundColor: "rgba(255, 255, 255,0.5)",
                            borderColor: "rgba(240,128,128,0.7)",
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in " + months[month - 1] + " of " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })
        }
    })
}

//=================== Function ===================//
function callBieuDoDoanhThuTuan(year, week) {
    bieuDoDoanhThuTuan(year, week).then(rs => {
        let revenue = [];
        let lastRevenue = [];
        lastYear = year;
        lastWeek = week;
        if (week > 1) {
            lastWeek -= 1;
        } else {
            lastYear -= 1;
            lastWeek = 1;
        }
        if (rs.message === "found") {
            for (i = 0, j = 0; i < 7; i++) {
                if (j === rs.data.length) {
                    revenue.push(0);
                } else {
                    if (weeks[i] == rs.data[j].x) {
                        revenue.push(rs.data[j].y);
                        j++;
                    } else {
                        revenue.push(0);
                    }
                }
            }
            bieuDoDoanhThuTuan(lastYear, lastWeek).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 7; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (weeks[i] == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else if (rs2.message == 'not found') {
                    for (i = 0, j = 0; i < 7; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = weekChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: weeks,
                        datasets: [{
                            label: 'Revenue',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)"
                        }, {
                            label: 'Revenue in last week',
                            data: lastRevenue,
                            backgroundColor: "rgba(240,128,128,0.5)",
                            borderColor: "rgba(240,128,128,0.7)"
                        }]
                    },
                    options: {
                        barValueSpacing: 20,
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in week " + (week + 1) + " of " + year,
                        },
                        legend: {
                            display: true,
                            position: 'bottom',
                            labels: {
                                fontColor: '#71748d',
                                fontFamily: 'Circular Std Book',
                                fontSize: 14
                            }
                        },
                        scales: {
                            xAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d'
                                }
                            }],
                            yAxes: [{
                                ticks: {
                                    fontSize: 14,
                                    fontFamily: 'Circular Std Book',
                                    fontColor: '#71748d'
                                }
                            }]
                        }
                    }
                });
            })
        } else if (rs.message === "not found") {
            for (i = 0; i < weeks.length; i++) {
                revenue[i] = 0;
            }
            bieuDoDoanhThuTuan(lastYear, lastWeek).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 7; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (weeks[i] == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else if (rs2.message == 'not found') {
                    for (i = 0, j = 0; i < 7; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = weekChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: weeks,
                        datasets: [{
                            label: 'Revenue',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)"
                        }, {
                            label: 'Revenue in last week',
                            data: lastRevenue,
                            backgroundColor: "rgba(240,128,128,0.5)",
                            borderColor: "rgba(240,128,128,0.7)"
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in week " + (week + 1) + " of " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })
        }
    });
}

//=================== Function ===================//
function callBieuDoDoanhThuNam(year) {
    bieuDoDoanhThuNam(year).then(rs => {
        let revenue = [];
        let lastRevenue = [];
        let lastYear = year - 1;
        if (rs.message === "found") {
            for (i = 0, j = 0; i < 12; i++) {
                if (j === rs.data.length) {
                    revenue.push(0);
                } else {
                    if (i + 1 == rs.data[j].x) {
                        revenue.push(rs.data[j].y);
                        j++;
                    } else {
                        revenue.push(0);
                    }
                }
            }
            bieuDoDoanhThuNam(lastYear).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 12; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (i + 1 == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else {
                    for (i = 0; i < 12; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = yearChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: months,
                        datasets: [{
                            label: 'Revenue in current year',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 1
                        }, {
                            label: 'Revenue in last year',
                            data: lastRevenue,
                            backgroundColor: "rgba(240,128,128,0.5)",
                            borderColor: "rgba(240,128,128,0.7)",
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in year " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })

        } else if (rs.message === "not found") {
            for (i = 0; i < months.length; i++) {
                revenue[i] = 0;
            }
            bieuDoDoanhThuNam(lastYear).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 12; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (i + 1 == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                } else {
                    for (i = 0; i < 12; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = yearChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: months,
                        datasets: [{
                            label: 'Revenue in current year',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)"
                        }, {
                            label: 'Revenue in last year',
                            data: lastRevenue,
                            backgroundColor: "rgba(240,128,128,0.5)"
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue in year " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })


        }
    })
}

//=================== Function ===================//
function callBieuDoDoanhThuGioTrongThang(year, month) {
    bieuDoDoanhThuGioTrongThang(year, month).then(rs => {
        let revenue = [];
        let lastRevenue = [];
        hours = [];
        for (i = 0; i < 24; i++) {
            hours[i] = i;
        }
        lastMonth = month;
        lastYear = year;
        if (month > 1) {
            lastMonth -= 1;
        } else {
            lastYear -= 1;
            lastMonth = 12;
        }
        if (rs.message === "found") {
            for (i = 0, j = 0; i < 24; i++) {
                if (j === rs.data.length) {
                    revenue.push(0);
                } else {
                    if (hours[i] == rs.data[j].x) {
                        revenue.push(rs.data[j].y);
                        j++;
                    } else {
                        revenue.push(0);
                    }
                }
            }
            bieuDoDoanhThuGioTrongThang(lastYear, lastMonth).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 24; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (hours[i] == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                    for (i = 0; i < 10; i++) {
                        hours[i] = '0' + hours[i];
                    }
                } else if (rs2.message === "not found") {
                    for (i = 0; i < hours.length; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = hourChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "line",
                    data: {
                        labels: hours,
                        datasets: [{
                            label: 'Revenue in current month ',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 1
                        }, {
                            label: 'Revenue in last month',
                            data: lastRevenue,
                            backgroundColor: "rgba(255, 255, 255,0.5)",
                            borderColor: "rgba(240,128,128,0.7)",
                            borderWidth: 1
                        }]

                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue by hour in " + months[month - 1] + " of year " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })

        } else if (rs.message === "not found") {
            for (i = 0; i < hours.length; i++) {
                revenue[i] = 0;
            }
            bieuDoDoanhThuGioTrongThang(lastYear, lastMonth).then(rs2 => {
                if (rs2.message == 'found') {
                    for (i = 0, j = 0; i < 24; i++) {
                        if (j === rs2.data.length) {
                            lastRevenue.push(0);
                        } else {
                            if (hours[i] == rs2.data[j].x) {
                                lastRevenue.push(rs2.data[j].y);
                                j++;
                            } else {
                                lastRevenue.push(0);
                            }
                        }
                    }
                    for (i = 0; i < 10; i++) {
                        hours[i] = '0' + hours[i];
                    }
                } else if (rs.message == 'not found') {
                    for (i = 0; i < hours.length; i++) {
                        lastRevenue[i] = 0;
                    }
                }
                let ctx = hourChart.get(0).getContext("2d");
                let myChar = new Chart(ctx, {
                    type: "line",
                    data: {
                        labels: hours,
                        datasets: [{
                            label: 'Revenue in current month',
                            data: revenue,
                            backgroundColor: "rgba(89, 105, 255,0.5)",
                            borderColor: "rgba(89, 105, 255,0.7)",
                            borderWidth: 1
                        }, {
                            label: 'Revenue in last month',
                            data: lastRevenue,
                            backgroundColor: "rgba(255, 255, 255,0.5)",
                            borderColor: "rgba(240,128,128,0.7)",
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        title: {
                            display: true,
                            text: "Revenue by hour in " + months[month - 1] + " of year " + year,
                        },
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
                        // scales: {
                        //     yAxes: [{
                        //         ticks: {
                        //             beginAtZero: true
                        //         }
                        //     }]
                        // }
                    }
                });
            })
        }
    })
    $("#pageBody").LoadingOverlay("hide");
}

//=================== Function ===================//
function setViewHoaDon() {
    let pageNumber = 1;
    recentOrder().then(rs => {
        arr = rs.data;
        //console.log(arr);
        let view = `<thead>
                <th>STT</th>
                <th>Mã hóa đơn</th>
                <th>Thời gian</th>
                <th>Nhân viên</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Tiền khách trả</th>
                <th>Tiền trả lại khách</th>
                <th>Chi Nhánh</th>
                </thead>`;
        let len = arr.length;
        if (len > 0) {
            view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td><a href="hoa-don-chi-tiet?id=${item.id}" target="_blank">${viewField(item.ma)}</a></td>
                    <td>${viewDateTime(item.thoiGian)}</td>
                    <td>${viewField(item.nguoiDung.hoVaTen)}</td>
                    <td>${viewField(item.khachHang.tenKhachHang)}</td>
                    <td>${formatMoney(item.tongTien)}</td>
                    <td>${formatMoney(item.tienKhachTra)}</td>
                    <td>${formatMoney(item.tienTraLaiKhach)}</td>
                    <td>${viewField(item.chiNhanh.diaChi)}</td>
                </tr>`);
        } else {
            view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
        }
        view += `
        <tr>
        <td colspan="9"><a href="danh-sach-hoa-don" class="btn btn-success">View Details</a></td>
        </tr>`
        table.html(view);
    }).catch(err => console.log(err))
}

//=================== Function ===================//
function call_topRevenueEmployee(month,year) {
    let pageNumber = 1;
    topRevenueEmployee(month,year).then(rs => {
        arr = rs.data;
        console.log(arr);
        let view = `<thead>
                <th>STT</th>
                <th>Mã nhân viên</th>
                <th>Tên nhân viên</th>
                <th>Tổng doanh thu</th>
                </thead>`;
        let len = arr.length;
        if (len > 0) {
            view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.ma)}</td>
                    <td>${viewField(item.ten)}</td>
                    <td>${formatMoney(item.tongDoanhThu)}<span>VNĐ</span></td>
                </tr>`);
        } else {
            view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
        }
        table_revenue_employee.html(view);
    }).catch(err => console.log(err))
}

//=================== Function ===================//
function call_topRevenueBranch(month,year) {
    let pageNumber = 1;
    topRevenueBranch(month,year).then(rs => {
        arr = rs.data;
        console.log(arr);
        let view = `<thead>
                <th>STT</th>
                <th>Mã nhân viên</th>
                <th>Tên nhân viên</th>
                <th>Tổng doanh thu</th>
                </thead>`;
        let len = arr.length;
        if (len > 0) {
            view += arr.map((item, index) => `<tr data-index="${index}" class="click-thuong-hieu">
                    <td data-id="${viewField(item.id)}">${(pageNumber - 1) * 10 + index + 1}</td>
                    <td>${viewField(item.ma)}</td>
                    <td>${viewField(item.ten)}</td>
                    <td>${formatMoney(item.tongDoanhThu)}<span>VNĐ</span></td>
                </tr>`);
            if (len < 5) {
                len++;
                for (let i = len; i <= 5; i++) {
                    view += `<tr><td>${(pageNumber - 1) * 10 + i}</td><td></td><td></td><td></td></tr>`
                }
            }
        } else {
            view += `<tr><td colspan="9">Không có thông tin phù hợp</td></tr>`
        }
        table_revenue_branch.html(view);
    }).catch(err => console.log(err))

}