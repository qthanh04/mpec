async function uploadFilesAWS(selectorInput) {
    let result = null;
    let files = $(selectorInput).get(0).files;
    console.log(files);
    if(files.length > 0) {
        let formData  = new FormData();
        formData.append('file', files[0]);
        console.log(formData);
        await ajaxUploadFile("s3/upload", formData).then(rs => {
            console.log(rs);
            if(rs.message === "uploaded") {
                console.log(rs.data);
                result = rs.data;
            }
        }).catch(err => {
            console.log("loi upload");
            alterDanger("Lỗi tải tập đính kèm");
            console.log(err);
        })
    }
    return result;
}

async function uploadMultiFileAWS(selectorInput) {
    let result = null;
    let files = $(selectorInput).get(0).files;
    console.log(files);
    if(files.length > 0) {
        let formData  = new FormData();
        formData.append('file',files[0]);
        console.log(formData);
        await ajaxUploadFile("s3/upload", formData).then(rs => {
            console.log(rs);
            if(rs.message === "uploaded") {
                console.log(rs.data);
                result = rs.data;
            }
        }).catch(err => {
            console.log("loi upload");
            alterDanger("Lỗi tải tập đính kèm");
            console.log(err);
        })
    } else {
        result = [];
    }
    return result;
}


// async function uploadMultiFile(selectorInput, selectorFormFile) {
//     let result = null;
//     let files = $(selectorInput).get(0).files;
//     if(files.length > 0) {
//         let formData  = new FormData();
//         formData.append('file',files[0]);
//         console.log(formData);
//         await ajaxUploadFile("api/v1/admin/upload-file", formData).then(rs => {
//             console.log(rs);
//             if(rs.message === "uploaded") result = rs.data;
//         }).catch(err => {
//             // alterDanger("Lỗi tải tập đính kèm", TIME_ALTER);
//             console.log(err);
//         })
//     } else {
//         result = [];
//     }
//     return result;
// }

async function uploadFiles(selectorInput) {
    let result = null;
    let files = selectorInput.get(0).files;
    if(files.length > 0) {
        let formData  = new FormData($("#form-file")[0]);
        await ajaxUploadFile("v1/public/upload-multi", formData, 1).then(rs => {
            if(rs.message === "uploaded") result = rs.data;
        }).catch(err => {
            // alterDanger("Lỗi tải tập đính kèm", TIME_ALTER);
            console.log(err);
        })
    } else {
        result = [];
    }
    return result;
}


