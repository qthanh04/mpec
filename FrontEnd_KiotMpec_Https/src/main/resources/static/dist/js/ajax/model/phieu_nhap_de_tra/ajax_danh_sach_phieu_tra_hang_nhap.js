async function phieuTraHangNhapSearch(chiNhanhId = 0, text = "%",page = 1, size = 10) {
    return ajaxGet(`v1/admin/phieu-tra-hang-nhap-chi-tiet/search-by-chi-nhanh-and-text?chi-nhanh-id=${chiNhanhId}&text=${text}&page=${page}&size=${size}`);
}

