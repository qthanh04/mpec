async function searchPhieuChi(chinhanhId, maPhieuChi ="", tenLoaiChi ="",tenNhanVien="", ngayDau=null, ngayCuoi=null, trangThai=-1,page=1,size=10) {
    return ajaxGet(`v1/admin/phieu-chi/search?chi-nhanh-id=${chiNhanhId}&ma-phieu-chi=${maPhieuChi}&ten-loai-chi=${tenLoaiChi}&ten-nhan-vien=${tenNhanVien}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&trang-thai=${trangThai}&page=${page}&size${size}`);
}

async function searchPhieuChiByChiNhanh(chiNhanhId,text="",page=1,size=10){
    return ajaxGet(`v1/admin/phieu-chi/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function setTrangThaiPhieuChi(phieuChiId,trangThai) {
    return ajaxPut(`v1/admin/phieu-chi/trang-thai?id=${phieuChiId}&trang-thai=${trangThai}`);
}

async function findHoaDonByIdKhachHang(id, page = 1, size = 9999) {
    return ajaxGet(`v1/admin/hoa-don/find-by-id-khach-hang-for-all?khach-hang-id=${id}&page=${page}&size=${size}`);
}

async function findPhieuNhapHangByIdNhaCungCap(id, page = 1, size = 9999) {
    return ajaxGet(`v1/admin/phieu-nhap-hang/find-by-id-nha-cung-cap?nha-cung-cap-id=${id}&page=${page}&size=${size}`);
}

function uploadPhieuChi(phieuChiRequest,chinhanhId,nguoiDungId,loaiChiId) {
    return ajaxPost(`v1/admin/phieu-chi/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-chi-id=${loaiChiId}`, phieuChiRequest);
}

function uploadPhieuChiSoQuy(phieuChiId,tongTienTra,tienTraKhachObj) {
    return ajaxPost(`v1/admin/phieu-chi-nhap-hang-tra-khach/upload-phieu-chi-tra-khach-hang?phieu-chi-id=${phieuChiId}&tong-tien-tra=${tongTienTra}`, tienTraKhachObj);
}

function uploadPhieuChiNhapHangSoQuy(phieuChiId,tongTienTra,tienTraNhaCungCapList) {
    return ajaxPost(`v1/admin/phieu-chi-nhap-hang-tra-khach/upload-phieu-chi-tra-nha-cung-cap?phieu-chi-id=${phieuChiId}&tong-tien-tra=${tongTienTra}`, tienTraNhaCungCapList);
}

async function uploadPhieuTraKhach(nguoiDungId,phieuTraKhach) {
    return ajaxPost(`v1/admin/phieu-tra-khach/upload?nguoi-dung-id=${nguoiDungId}`, phieuTraKhach);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}

async function creatPhieuChiPhieuNhapHangPDF(phieuChiId,phieuNhapHangId, nguoiDungId) {
    return ajaxGet(`v1/admin/phieu-chi/pdf-phieu-nhap-hang?phieu-chi-id=${phieuChiId}&phieu-nhap-hang-id=${phieuNhapHangId}&nguoi-dung-id=${nguoiDungId}`);
}

async function creatPhieuChiPhieuTraKhachPDF(phieuChiId,hoaDonId, nguoiDungId) {
    return ajaxGet(`v1/admin/phieu-chi/pdf-phieu-tra-khach?phieu-chi-id=${phieuChiId}&hoa-don-id=${hoaDonId}&nguoi-dung-id=${nguoiDungId}`);
}