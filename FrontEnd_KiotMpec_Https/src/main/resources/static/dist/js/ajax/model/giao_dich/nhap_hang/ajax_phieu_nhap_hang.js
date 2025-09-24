var arrTrangThaiPhieuNhap = ["Lưu tạm", "Chưa hoàn thành", "Hoàn thành"];

async function phieuNhapHangSearch(maPhieuNhap = "", ngayDau = null, ngayCuoi = null, nhaCungCapid = 0,trangThai = -1, page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-nhap-hang/search?ma-phieu-nhap=${maPhieuNhap}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&nha-cung-cap-id=${nhaCungCapid}&trang-thai=${trangThai}&page=${page}&size=${size}`);
}

async function phieuNhapHangDelete(idPhieuNhap) {
    return ajaxDelete(`v1/admin/phieu-nhap-hang/delete?id=${idPhieuNhap}`);
}

async function setTrangThaiPhieuNhapHang(id,trangThai){
    return ajaxPut(`v1/admin/phieu-nhap-hang/trang-thai?id=${id}&trang-thai=${trangThai}`);
}

async function searchPhieuNhapHangByChiNhanh(chiNhanhId,text="",page=1,size=10){
    return ajaxGet(`v1/admin/phieu-nhap-hang-chi-tiet/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function phieuNhapHangFindById(id) {
    return ajaxGet(`v1/admin/phieu-nhap-hang/id?id=${id}`);
}

async function viewTrangThaiPhieuNhap(phieuNhap) {
    let {trangThai} = phieuNhap;
    return trangThai == null ? "" : arrTrangThaiPhieuNhap[trangThai - 0];
}

async function phieuNhapChiTietFindByPhieuNhap(phieuNhapId, page = 1, size = 5) {
    return ajaxGet(`v1/admin/phieu-nhap-hang-chi-tiet/find-by-phieu-nhap?phieu-nhap-id=${phieuNhapId}&page=${page}&size=${size}`);
}

async function findHangHoa(id = 0) {
    return ajaxGet(`v1/admin/hang-hoa/find-by-id?id=${id}`);
}

async function findFirstHH(hangHoaId=0) {
    return ajaxGet(`v1/admin/lich-su-gia-ban/find-distinct-by-hang-hoa-id?hangHoaId=${hangHoaId}`);
}

async function findGiaBanByHangHoaId(donViHangHoaId=0) {
    return ajaxGet(`v1/admin/lich-su-gia-ban/find-gia-ban-hien-tai?don-vi-hang-hoa-id=${donViHangHoaId}`);
}

async function findNhaCungCapById(id= 0) {
    return ajaxGet(`v1/admin/nha-cung-cap/find-by-id?id=${id}`);
}

async function findDonViCoBan(id=0){
    return ajaxGet(`v1/admin/don-vi-hang-hoa/find-don-vi-co-ban-by-hang-hoa-id?hangHoaId=${id}`);
}

async function uploadNhaCungCap(nhaCungCap) {
    return ajaxPost(`v1/admin/nha-cung-cap/upload`,nhaCungCap);
}

async function uploadPhieuNhap(phieuNhap,nguoiDungId,nhaCungCapId) {
    return ajaxPost(`v1/admin/phieu-nhap-hang/upload?nguoi-dung-id=${nguoiDungId}&nha-cung-cap-id=${nhaCungCapId}`,phieuNhap);
}

async function uploadPhieuNhapChiTiet(phieuNhapForm) {
    return ajaxPost(`v1/admin/phieu-nhap-hang-chi-tiet/upload-by-form`,phieuNhapForm);
}

async function uploadPhieuChi(phieuChiRequest,chinhanhId,nguoiDungId,loaiChiId) {
    return ajaxPost(`v1/admin/phieu-chi/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-chi-id=${loaiChiId}`, phieuChiRequest);
}

async function uploadPhieuChiNhapHangTraKhach(phieuNhapHangId,phieuChiId,tienDaTra) {
    return ajaxPost(`v1/admin/phieu-chi-nhap-hang-tra-khach/phieu-nhap-hang/upload?phieu-nhap-hang-id=${phieuNhapHangId}&phieu-chi-id=${phieuChiId}&tien-da-tra=${tienDaTra}`);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}





