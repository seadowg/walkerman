function rsvpsNew() {
    $("[name=rsvp_guests_yes]").click(function() {
        if (this.value === "true") {
            $("#rsvp_guests_form").show();
        } else {
             $("#rsvp_guests_form").hide();
        }
    });

}