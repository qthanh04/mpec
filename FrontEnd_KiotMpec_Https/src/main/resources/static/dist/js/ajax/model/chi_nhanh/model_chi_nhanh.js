const urlChiNhanh = "v1/admin/chi-nhanh/";
const urlCompany = "v1/admin/doanh-nghiep/";

async function findBranchById(id){
    return ajaxGet(`v1/admin/chi-nhanh/find-by-id?id=${id}`);
};

async function uploadBranch(data, idCompany){
    return ajaxPost(`v1/admin/chi-nhanh/upload?cong-ty-id=${idCompany}`, data);
};

async function updateBranch(data, idCompany){
    return ajaxPut(`v1/admin/chi-nhanh/update?cong-ty-id=${idCompany}`, data);
};

async function deleteBranch(id) {
    return ajaxDelete(`v1/admin/chi-nhanh/delete?id=${id}`, 1);
}

let findAllBranchesByIdCompany = function(idCompany = 1,page=1,size=10){
    return ajaxGet(`${urlChiNhanh}find-by-companyId?cong-ty-id=${idCompany}&page=${page}&size=${size}`);
};

let chiNhanhSearch = function(text){
    return ajaxGet(`${urlChiNhanh}search?text=${text}`);
};
let chiNhanhSelectSearch = function(text,page=1,size=10){
    return ajaxGet(`${urlChiNhanh}select-search?text=${text}&page=${page}&size=${size}`);
};





