async function findCongNoNhaCungCap(ngayDau="", ngayCuoi="", page=1, size=10){
    return ajaxGet(`v1/admin/phieu-nhap-hang/top-nha-cung-cap-theo-thang?ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}&page=${page}&size=${size}`);
}
async function searchCongNoNhaCungCap(ngayDau="", ngayCuoi="", text = "", page=1, size=10){
    return ajaxGet(`v1/admin/phieu-nhap-hang/search-top-nha-cung-cap-theo-thang?ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}&text=${text}&page=${page}&size=${size}`);
}