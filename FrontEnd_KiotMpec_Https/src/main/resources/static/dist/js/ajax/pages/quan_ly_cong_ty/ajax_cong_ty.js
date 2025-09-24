//================ Declare variable===================//
let taxCodeInput, phoneNumberInput, emailInput, websiteInput, presentingPersonInput, addressInput, anh;

//=============== Function main ===================//

$(function () {
    //inject Input
    //=========Mapping variabe and id=========//
    anh = $("#ava-img")
    taxCodeInput = $("#input-text-1");
    emailInput = $("#input-text-2");
    presentingPersonInput = $("#input-text-3");
    phoneNumberInput = $("#input-text-4");
    websiteInput = $("#input-text-5");
    addressInput = $("#input-text-7");

    //==========Function constructor========//
    loadCompanyData(1);
});

//=================== Function ===================//
function loadCompanyData(idCompany) {
    findCompanyById(idCompany).then(rs => {
        if (rs.message === "found") {
            rs = rs.data;
            $("#vnCompanyName").text(rs.tenDoanhNghiep);
            $("#enCompanyName").text(rs.tenTiengAnh);
            taxCodeInput.val(rs.maDoanhNghiep);
            phoneNumberInput.val(rs.soDienThoai);
            emailInput.val(rs.email);
            addressInput.val(rs.diaChi);
            presentingPersonInput.val(rs.nguoiDaiDien);
            websiteInput.val(rs.website);
            anh.html(`<img src="${rs.logo}" width="150" class="img-responsive center-block">`);
        }
    }).catch(er => console.log(er));
}