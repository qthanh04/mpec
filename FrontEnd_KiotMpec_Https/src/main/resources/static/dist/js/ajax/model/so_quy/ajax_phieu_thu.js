async function searchPhieuThu(chinhanhId, maPhieuThu = "", tenLoaiThu = "", tenNhanVien = "", ngayDau = null, ngayCuoi = null, trangThai = -1, page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-thu/search?chi-nhanh-id=${chiNhanhId}&ma-phieu-thu=${maPhieuThu}&ten-loai-thu=${tenLoaiThu}&ten-nhan-vien=${tenNhanVien}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&trang-thai=${trangThai}&page=${page}&size${size}`);
}

async function searchPhieuThuByChiNhanh(chiNhanhId, text = "", page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-thu/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

async function setTrangThaiPhieuThu(phieuThuId, trangThai) {
    return ajaxPut(`v1/admin/phieu-thu/trang-thai?id=${phieuThuId}&trang-thai=${trangThai}`);
}

async function findHoaDonByIdKhachHang(id, page = 1, size = 9999) {
    return ajaxGet(`v1/admin/hoa-don/find-by-id-khach-hang?khach-hang-id=${id}&page=${page}&size=${size}`);
}

async function findHoaDonByIdNhaCungCap(id, page = 1, size = 9999) {
    return ajaxGet(`v1/admin/phieu-tra-hang-nhap/find-by-id-nha-cung-cap?nha-cung-cap-id=${id}&page=${page}&size=${size}`);
}

function uploadPhieuThu(phieuThuRequest,chinhanhId,nguoiDungId,loaiThuId) {
    return ajaxPost(`v1/admin/phieu-thu/upload?chi-nhanh-id=${chinhanhId}&nguoi-dung-id=${nguoiDungId}&loai-thu-id=${loaiThuId}`, phieuThuRequest);
}

function uploadPhieuThuSoQuy(phieuThuId,tongTienTra,tienKhachTraObj) {
    return ajaxPost(`v1/admin/phieu-thu-hoa-don-tra-hang-nhap/upload-phieu-thu?phieu-thu-id=${phieuThuId}&tong-tien-tra=${tongTienTra}`, tienKhachTraObj);
}

function uploadPhieuThuTraHangSoQuy(phieuTraHangNhapId,tongTienTra,tienNhaCungCapTraList) {
    return ajaxPost(`v1/admin/phieu-thu-hoa-don-tra-hang-nhap/upload-phieu-tra-hang-nhap?phieu-tra-hang-id=${phieuTraHangNhapId}&tong-tien-tra=${tongTienTra}`, tienNhaCungCapTraList);
}

async function uploadPhieuHoatDong(phieuHoatDong,nguoiDungId,hoaDonId,phieuTraKhachId,phieuNhapHangId,phieuTraHangNhapId, phieuThuId, phieuChiId, hoatDongId) {
    return ajaxPost(`v1/admin/phieu-hoat-dong/upload?nhan-vien-id=${nguoiDungId}&hoa-don-id=${hoaDonId}&phieu-tra-khach-id=${phieuTraKhachId}&phieu-nhap-hang-id=${phieuNhapHangId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&phieu-thu-id=${phieuThuId}&phieu-chi-id=${phieuChiId}&hoat-dong-id=${hoatDongId}`, phieuHoatDong);
}

async function creatPhieuThuPDF(phieuThuId,hoaDonId, nguoiDungId) {
    return ajaxGet(`v1/admin/phieu-thu/pdf?phieu-thu-id=${phieuThuId}&hoa-don-id=${hoaDonId}&nguoi-dung-id=${nguoiDungId}`);
}

async function creatPhieuThuPhieuTraHangNhapPDF(phieuThuId,phieuTraHangNhapId, nguoiDungId) {
    return ajaxGet(`v1/admin/phieu-thu/pdf-phieu-tra-hang-nhap?phieu-thu-id=${phieuThuId}&phieu-tra-hang-nhap-id=${phieuTraHangNhapId}&nguoi-dung-id=${nguoiDungId}`);
}