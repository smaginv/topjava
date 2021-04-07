const mealAjaxUrl = "ajax/profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

const filterForm = $('#filterForm');

function filter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: filterForm.serialize()
    }).done(function (data) {
        redrawTable(data);
    });
}

function clearFilter() {
    filterForm[0].reset();
    updateTable();
}