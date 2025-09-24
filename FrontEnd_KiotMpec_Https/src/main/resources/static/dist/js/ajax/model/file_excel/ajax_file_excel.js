var url_excel_file = "v1/admin/excel-file";
function searchFileExcel(tenLoai ="",maPhieu ="",ngayDau = null,ngayCuoi = null,page=1,size=10){
    return ajaxGet(`${url_excel_file}/search?ten-loai=${tenLoai}&ma-phieu=${maPhieu}${ngayDau == null ? "" : `&ngay-dau=${ngayDau}`}${ngayCuoi == null ? "" : `&ngay-cuoi=${ngayCuoi}`}&page=${page}&size=${size}`)
}
function fileExcelDeleted(id){
    return ajaxGet(`${url_excel_file}/delete?id=${id}`)
}

// function findFileExcel(tenLoai,maPhieu,ngayDau="",ngayCuoi="",page=1,size=10){
//     return ajaxGet(`${url_excel_file}/search?ten-loai=${tenLoai}&ma-phieu${maPhieu}&ngay-dau=${ngayDau}&ngay-cuoi=${ngayCuoi}&page=${page}&size=${size}`)
// }
