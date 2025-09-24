// async function searchCongNo(tuNgay,denNgay,page=1,size=10) {
//     return ajaxGet(`v1/admin/hoa-don/top-khach-hang-theo-thang?ngay-dau=${tuNgay}&ngay-cuoi=${denNgay}&page=${page}&size=${size}`);
// }
async function findCongNoKhachHang(ngayDau="", ngayCuoi="", page=1, size=10){
    return ajaxGet(`v1/admin/hoa-don/top-khach-hang-theo-thang?ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}&page=${page}&size=${size}`);
}

async function searchCongNoKhachHang(ngayDau="", ngayCuoi="", text="", page=1, size=10){
    return ajaxGet(`v1/admin/hoa-don/search-top-khach-hang-theo-thang?ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}&text=${text}&page=${page}&size=${size}`);
}