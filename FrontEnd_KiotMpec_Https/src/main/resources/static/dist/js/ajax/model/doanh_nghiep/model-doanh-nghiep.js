const urlCompany = "v1/admin/doanh-nghiep/";

async function CompanyUpload(data){
    let postCompanyInfo = ajaxPost("v1/admin/doanh-nghiep/upload", data, 1);
    return postCompanyInfo;
}

function findCompanyById(idCompany,see) {
    return ajaxGet(`${urlCompany}find-by-id?id=${idCompany}`);
}

// let CompanyUpdata = function(data){
//     let putCompanyInfo = ajaxPut("v1/admin/doanh-nghiep/update", data, 1);
//     return putCompanyInfo;
// };

// let findCompanyById1 = function(idCompany){
//     return ajaxGetNoAsync(`${urlCompany}find-by-id?id=${idCompany}`);
// };

// let deleteCompany = function(id){
//     let companyInfo = ajaxPut("v1/admin/doanh-nghiep/delete", id, 1);
// };
