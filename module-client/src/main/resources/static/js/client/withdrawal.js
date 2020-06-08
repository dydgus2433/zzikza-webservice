$(document).ready(function () {
    $(".btn-default").on("click", function () {
        location.href = '/edit_memberMain'
    })

    $(".btn-danger").on("click", function () {
        $.ajax({
            url: "/api/withdrawal",
            method: "post"
        }).done(function (data) {
            console.log(data);

            if (data.success) {
                location.href = '/logout';
            } else {
                alert(data.msg);
            }

            console.log("reviews_list", data);
        }).always(function () {
            console.info('DONE');
        });
    })
})