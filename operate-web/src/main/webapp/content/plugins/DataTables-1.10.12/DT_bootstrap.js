/* Set the defaults for DataTables initialisation */
$.extend(true, $.fn.dataTable.defaults, {
    "sDom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-3 col-sm-12'i><'col-md-9 col-sm-12'p>>", // horizobtal scrollable datatable
    //"sDom": "<'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // defaukt datatable without  horizobtal scroll
    "sPaginationType": "bootstrap",
    "oLanguage": {
        "sLengthMenu": "_MENU_ records"
    }
});


/* Default class modification */
$.extend($.fn.dataTableExt.oStdClasses, {
    "sWrapper": "dataTables_wrapper form-inline"
});


/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function (oSettings) {
    return {
        "iStart": oSettings._iDisplayStart,
        "iEnd": oSettings.fnDisplayEnd(),
        "iLength": oSettings._iDisplayLength,
        "iTotal": oSettings.fnRecordsTotal(),
        "iFilteredTotal": oSettings.fnRecordsDisplay(),
        "iPage": Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
        "iTotalPages": Math.ceil(oSettings.fnRecordsDisplay() / oSettings._iDisplayLength)
    };
};


/* Bootstrap style pagination control */
$.extend($.fn.dataTableExt.oPagination, {
    "bootstrap": {
        "fnInit": function (oSettings, nPaging, fnDraw) {
            var oLang = oSettings.oLanguage.oPaginate;
            var fnClickHandler = function (e) {
                e.preventDefault();
                if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                    fnDraw(oSettings);
                }
            };

            // pagination with prev, next link icons
            $(nPaging).append(
				'<ul class="pagination">' +
					'<li class="prev disabled"><a href="#" title="' + oLang.sPrevious + '"><i class="fa fa-angle-left"></i></a></li>' +
					'<li class="next disabled"><a href="#" title="' + oLang.sNext + '"><i class="fa fa-angle-right"></i></a></li>' +
				'</ul>'
			);

            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[1]).bind('click.DT', { action: "next" }, fnClickHandler);
        },

        "fnUpdate": function (oSettings, fnDraw) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);

            if (oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if (oPaging.iPage <= iHalf) {
                iStart = 1;
                iEnd = iListLength;
            } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }

            if (oPaging.iTotalPages < 0) {
                $('.pagination', an[i]).css('visibility', 'hidden');
            } else {
                $('.pagination', an[i]).css('visibility', 'visible');
            }

            for (i = 0, iLen = an.length ; i < iLen ; i++) {
                // Remove the middle elements
                $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                // Add the new list items and their event handlers
                for (j = iStart ; j <= iEnd ; j++) {
                    sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
                    $('<li ' + sClass + '><a href="#">' + j + '</a></li>')
						.insertBefore($('li:last', an[i])[0])
						.bind('click', function (e) {
						    e.preventDefault();
						    oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
						    fnDraw(oSettings);
						});
                }

                // Add / remove disabled classes from the static elements
                if (oPaging.iPage === 0) {
                    $('li:first', an[i]).addClass('disabled');
                } else {
                    $('li:first', an[i]).removeClass('disabled');
                }

                if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                    $('li:last', an[i]).addClass('disabled');
                } else {
                    $('li:last', an[i]).removeClass('disabled');
                }
            }
        }
    }
});

/* Bootstrap style full number pagination control */
$.extend($.fn.dataTableExt.oPagination, {
    "bootstrap_full_number": {
        "fnInit": function (oSettings, nPaging, fnDraw) {
            var oLang = oSettings.oLanguage.oPaginate;
            var oPaging = oSettings.oInstance.fnPagingInfo();

            var fnClickHandler = function (e) {
                e.preventDefault();
                if (oSettings.oApi._fnPageChange(oSettings, e.data.action)) {
                    fnDraw(oSettings);
                }
            };
            $(nPaging).append(
                '<ul class="pagination">' +
                    '<li class="prev disabled"><a href="#" title="' + oLang.sFirst + '"><i class="fa fa-angle-double-left"></i></a></li>' +
                    '<li class="prev disabled"><a href="#" title="' + oLang.sPrevious + '"><i class="fa fa-angle-left"></i></a></li>' +
                    '<li class="next disabled"><a href="#" title="' + oLang.sNext + '"><i class="fa fa-angle-right"></i></a></li>' +
                    '<li class="next disabled"><a href="#" title="' + oLang.sLast + '"><i class="fa fa-angle-double-right"></i></a></li>' +
                    '&nbsp;&nbsp;<label class="control-label">' + oLang.sRedirect + '</label><input type="text"value="1" style="width: 50px;height:24px; line-height: 10px;" class="form-control redirect">' +
                    '&nbsp;&nbsp;<label class="control-label">' + oLang.sPage + '</label>' +
                    '&nbsp;&nbsp;<button  type="button" class="btn btn-xs sConfirm">' + oLang.sConfirm + '</button>' +
                '</ul>'
            );
            //datatables分页跳转

            $(nPaging).find(".sConfirm").click(function (e) {
                var ipage = parseInt($(this).parent(".pagination").children(".redirect").val());
                var oPaging = oSettings.oInstance.fnPagingInfo();
                if (isNaN(ipage) || ipage < 1) {
                    ipage = 1;
                } else if (ipage > oPaging.iTotalPages) {
                    ipage = oPaging.iTotalPages;
                }
                $(this).parent(".pagination").children(".redirect").val(ipage)

                ipage--;
                oSettings._iDisplayStart = ipage * oPaging.iLength;
                fnDraw(oSettings);
            });
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "first" }, fnClickHandler);
            $(els[1]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[2]).bind('click.DT', { action: "next" }, fnClickHandler);
            $(els[3]).bind('click.DT', { action: "last" }, fnClickHandler);
        },

        "fnUpdate": function (oSettings, fnDraw) {
            var iListLength = 5;
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var an = oSettings.aanFeatures.p;
            var i, j, sClass, iStart, iEnd, iHalf = Math.floor(iListLength / 2);

            if (oPaging.iTotalPages < iListLength) {
                iStart = 1;
                iEnd = oPaging.iTotalPages;
            }
            else if (oPaging.iPage <= iHalf) {
                iStart = 1;
                iEnd = iListLength;
            } else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
                iStart = oPaging.iTotalPages - iListLength + 1;
                iEnd = oPaging.iTotalPages;
            } else {
                iStart = oPaging.iPage - iHalf + 1;
                iEnd = iStart + iListLength - 1;
            }

            if (oPaging.iTotalPages < 0) {
                $('.pagination', an[i]).css('visibility', 'hidden');
            } else {
                $('.pagination', an[i]).css('visibility', 'visible');
            }

            for (i = 0, iLen = an.length ; i < iLen ; i++) {
                // Remove the middle elements
                $('li:gt(1)', an[i]).filter(':not(.next)').remove();

                // Add the new list items and their event handlers
                for (j = iStart ; j <= iEnd ; j++) {
                    sClass = (j == oPaging.iPage + 1) ? 'class="active"' : '';
                    $('<li ' + sClass + '><a href="#">' + j + '</a></li>')
                        .insertBefore($('li.next:first', an[i])[0])
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(), 10) - 1) * oPaging.iLength;
                            fnDraw(oSettings);
                        });
                }

                // Add / remove disabled classes from the static elements
                if (oPaging.iPage === 0) {
                    $('li.prev', an[i]).addClass('disabled');
                } else {
                    $('li.prev', an[i]).removeClass('disabled');
                }

                if (oPaging.iPage === oPaging.iTotalPages - 1 || oPaging.iTotalPages === 0) {
                    $('li.next', an[i]).addClass('disabled');
                } else {
                    $('li.next', an[i]).removeClass('disabled');
                }
            }
        }
    }
});


/*
 * TableTools Bootstrap compatibility
 * Required TableTools 2.1+
 */
if ($.fn.DataTable.TableTools) {
    // Set the classes that TableTools uses to something suitable for Bootstrap
    $.extend(true, $.fn.DataTable.TableTools.classes, {
        "container": "btn-group",
        "buttons": {
            "normal": "btn default",
            "disabled": "btn disabled"
        },
        "collection": {
            "container": "DTTT_dropdown dropdown-menu",
            "buttons": {
                "normal": "",
                "disabled": "disabled"
            }
        }
    });

    // Have the collection use a bootstrap compatible dropdown
    $.extend(true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
        "collection": {
            "container": "ul",
            "button": "li",
            "liner": "a"
        }
    });
}
//自定定选择行（
$(document).on("click", ".dataTables_scrollBody tbody tr", function () {

    $("tbody tr").removeAttr("clk");
    $(this).attr("clk", "on");
    $(".dataTables_scrollBody tbody tr").each(function (index) {
        if ($(this).attr("clk") == "on") {
            if ($(this).hasClass('active')) {
                $(this).removeClass('selected');
                $(this).removeClass('active');
                $(".DTFC_LeftBodyWrapper tbody tr:eq(" + index + ")").removeClass("selected");
                $(".DTFC_LeftBodyWrapper tbody tr:eq(" + index + ")").removeClass("active");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").removeClass("selected");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").removeClass("active");
            }
            else {
                $("tbody tr.active").removeClass('active');
                $("tbody tr.selected").removeClass('selected');
                $(this).addClass('selected');
                $(this).addClass('active');
                $(".DTFC_LeftBodyWrapper tbody tr:eq(" + index + ")").addClass("selected");
                $(".DTFC_LeftBodyWrapper tbody tr:eq(" + index + ")").addClass("active");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").addClass("selected");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").addClass("active");
            }
        }
    });
});

$(document).on("click", "div.DTFC_LeftWrappe tbody tr", function () {

    $("tbody tr").removeAttr("clk");
    $(this).attr("clk", "on");
    
    $("div.DTFC_LeftWrappe tbody tr").each(function (index) {
        if ($(this).attr("clk") == "on") {
            if ($(this).hasClass('active')) {
                $(this).removeClass('selected');
                $(this).removeClass('active');
                $(".dataTables_scrollBody tbody tr:eq(" + index + ")").removeClass("selected");
                $(".dataTables_scrollBody tbody tr:eq(" + index + ")").removeClass("active");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").removeClass("selected");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").removeClass("active");
            }
            else {
                $("tbody tr.active").removeClass('active');
                $("tbody tr.selected").removeClass('selected');
                $(this).addClass('selected');
                $(this).addClass('active');
                $(".dataTables_scrollBody tbody tr:eq(" + index + ")").addClass("selected");
                $(".dataTables_scrollBody tbody tr:eq(" + index + ")").addClass("active");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").addClass("selected");
                $(".DTFC_RightBodyWrapper tbody tr:eq(" + index + ")").addClass("active");
            }
        }
    });
});
