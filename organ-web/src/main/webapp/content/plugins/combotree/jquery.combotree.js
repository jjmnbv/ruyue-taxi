/**
 * combotree
 * 
 */
(function ($) {
    function setSize(target) {
        var setting = $.data(target, 'combotree').setting;
        var combo = $.data(target, 'combotree').combotree;
        var content = $.data(target, 'combotree').content;

        var newsetting = {
            callback: {
                onClick: function (event, treeId, treeNode) {
                    var oldValue = combo.find('input.combotree-value').val();
                    //combo.find('input.combotree-value').val(treeNode.id);
                    combo.find('span.combotree-chosen').text(treeNode.name);
                    combo.find(".combotree-arrow").removeClass("open");
                    $(target).val(treeNode.id);
                    $.data(target, 'selectedNode', treeNode);
                    content.hide();
                    setting.callback.onClick.call(target, event, treeId, treeNode);
                }
            }
        };

        $.fn.zTree.init(content.find('>ul'), $.extend(true, {}, setting, newsetting));

    }

    function init(target) {
        $(target).hide();

        var html = '<div class="combotree-container combotree">';
        html += '   <a tabindex="-1" class="combotree-choice "  href="javascript:void(0)">';
        html += '           <span class="combotree-chosen "></span>';
        html += '           <span class="combotree-arrow">';
        html += '                <b></b>';
        html += '           </span>';
        html += '        </a>';
        html += '    </div>';

        var container = $(html).insertAfter(target);

        var content = $('<div class="combotree-content"><ul class="ztree"></ul></div>').appendTo($(document.body));

        //支持获取树形对象
        var ztreeid = 'ztree' + $.guid++;
        $.data(target,"ztreeid",ztreeid);
        content.find('>ul').attr('id', ztreeid);

        var arrow = container.find(".combotree-arrow");
        function show(event) {
            arrow.addClass("open");
            content.css({
                display: 'block',
                left: container.offset().left,
                top: container.offset().top + container.outerHeight()
            });

            //$(document).unbind("click");

            $(document).bind('click', function (event) {
                var contentId = content.find('>ul').attr("id");
                if (!(event.target.id == contentId || $(event.target).parents("#" + contentId).length > 0)) {
                    arrow.removeClass("open");
                    content.hide();
                }
            });
            event.stopPropagation();
        }
        container.on("click", show);

        return {
            combotree: container,
            content: content
        }
    }

    function setValue(target, node) {
        var setting = $.data(target, 'combotree').setting;
        var combo = $.data(target, 'combotree').combotree;
        var content = $.data(target, 'combotree').content;
        $.data(target, 'selectedNode', node);
        combo.find('span.combotree-chosen').text(node.name);
        $(target).val(node.id);
    }

    function getValue(target) {
        return $.data(target, 'selectedNode');
    }

    function reload(target, url) {
        var opts = $.data(target, 'combotree').options;
        var content = $.data(target, 'combotree').content;
        if (url) {
            opts.url = url;
        }
        content.find('>ul').tree({ url: opts.url }).tree('reload');
    }

    $.fn.combotree = function (setting, param) {
        if (typeof setting == 'string') {
            switch (setting) {
                case 'setValue':
                    return this.each(function () {
                        setValue(this, param);
                    });
                    break;
                case 'getValue':
                    var selectedNode = null;
                    this.each(function () {
                        selectedNode = getValue(this);
                    });
                    return selectedNode;
                    break;
            }
            return;
        }

        return this.each(function () {
            var state = $.data(this, 'combotree');
            if (state) {
                $.extend(true, state.setting, setting);
            } else {
                var r = init(this);
                state = $.data(this, 'combotree', {
                    setting: $.extend(true, {}, $.fn.combotree.defaults, setting),
                    combotree: r.combotree,
                    content: r.content
                });
            }

            setSize(this);
        });
    };

    $.fn.combotree.defaults = {
        callback: {
            onClick: function (event, treeId, treeNode) { }
        }
    };

})(jQuery);