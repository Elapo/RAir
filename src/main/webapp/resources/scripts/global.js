/**
 * Fix for calendar inputs to make them look material design
 */
// $("[calendarLabel]").each(repositionCalendarLabel);

function edit() {
    $(".ui-calendar").each(function (index) {
        var span = $(this);
        var input = span.find("input");
        input.datepicker({
            onSelect: function (dateText, inst) {
                modifyToMaterial(index, span);
            }
        });
    });
    $(".ui-calendar").each(modifyToMaterial);

    function repositionCalendarLabel(index) {
        console.log("Found label!");
        console.log($(this));

        var calendarSpan = $(this).siblings("span")[0];
        console.log("Span: ");
        console.log(calendarSpan);
        if (calendarSpan) {
            $(this).appendTo(calendarSpan);
        }
    }


    function modifyToMaterial(index, span) {
        console.log("Modifying...");
        var calendarSpan = span;

        var input = calendarSpan.find("input");
        input.addClass("mdl-textfield__input");
        input.removeClass("ui-corner-all");

        var label = calendarSpan.sibling("label");
        if (label) {
            label.appendTo(calendarSpan);
        }
    }
}