async function bieuDoDoanhThuTong(ngayDau, ngayCuoi){
    return ajaxGet(`v1/admin/hoa-don/doanh-thu?ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}`);
}

async function bieuDoDoanhThuThang(year,month){
    return ajaxGet(`v1/admin/hoa-don/doanh-thu-thang/${year}/${month}`);
}

async function bieuDoDoanhThuGioTrongThang(year,month){
    return ajaxGet(`v1/admin/hoa-don/doanh-thu-gio-trong-thang/${year}/${month}`);
}

async function bieuDoDoanhThuNam(year){
    return ajaxGet(`v1/admin/hoa-don/doanh-thu-nam/${year}`);
}

async function bieuDoDoanhThuTuan(year,week){
    return ajaxGet(`v1/admin/hoa-don/doanh-thu-tuan/${year}/${week}`);
}

async function countBill(start,end){
    return ajaxGet(`v1/admin/hoa-don/count-bill?start-date=${start}&end-date=${end}`);
}

async function sumBill(start,end){
    return ajaxGet(`v1/admin/hoa-don/sum-bill?start-date=${start}&end-date=${end}`);
}

async function countCustormer(){
    return ajaxGet(`v1/admin/khach-hang/count`);
}

async function countCustormerTransaction(start,end){
    return ajaxGet(`v1/admin/khach-hang/count-transaction?start-date=${start}&end-date=${end}`);
}

async function countNewCustormer(start,end){
    return ajaxGet(`v1/admin/khach-hang/count-new-custormer?start-date=${start}&end-date=${end}`);
}

async function recentOrder(){
    return ajaxGet(`v1/admin/hoa-don/find-recent-order`);
}

async function topRevenueEmployee(month,year){
    return ajaxGet(`v1/admin/hoa-don/top-doanh-thu-nhan-vien-theo-thang?month=${month}&size=5&top=true&year=${year}`);
}

async function topRevenueBranch(month,year){
    return ajaxGet(`v1/admin/hoa-don/top-doanh-thu-chi-nhanh-theo-thang?month=${month}&size=5&top=true&year=${year}`);
}

