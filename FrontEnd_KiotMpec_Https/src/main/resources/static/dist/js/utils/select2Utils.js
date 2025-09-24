function createSelect2Ajax(selectField, select2SearchingUrl, placeholderString, name, multiple) {
    $(selectField).select2({
        ajax: {
            type: 'GET',
            headers: {
                "Authorization": ss_lg
            },
            dataType: "json",
            url: select2SearchingUrl,
            timeout: 30000,
            data: function (params) {
                let query = {
                    text: params.term != null ? params.term : "",
                    page: 1,
                    size: 5
                };
                return query;
            },
            processResults: function (data) {
                if (data.message == "not found") {
                    let rs = {
                        'text': "Không tìm thấy " + name
                    }
                    return {
                        results: rs
                    };
                } else {
                    let rs = [];
                    $.each(data.data.resultDtoList, function (idx, item) {
                        rs.push({
                            'id': item.id,
                            'text': item.text
                        });
                    });
                    return {results: rs};
                }
            },
            cache: true
        },
        multiple: multiple,
        placeholder: placeholderString,
        minimumInputLength: 0
    });
}