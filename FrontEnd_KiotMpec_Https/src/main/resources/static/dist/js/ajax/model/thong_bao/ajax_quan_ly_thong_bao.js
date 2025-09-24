async function searchThongBao(tieuDe = "", nguoiNhan = "", ngayDau = null, ngayCuoi = null, page = 1, size = 10 ) {
    return ajaxGet(`v1/admin/thong-bao-nguoi-nhan/search?tieu-de=${tieuDe}&nguoi-nhan=${nguoiNhan}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&page=${page}&size=${size}`);
}