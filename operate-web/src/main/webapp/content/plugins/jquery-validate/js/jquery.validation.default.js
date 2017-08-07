$(function () {
	if (jQuery.validator) {
		jQuery.validator.setDefaults({

			errorElement: 'span', 
			errorClass: 'help-block', 
			focusInvalid: false, 
			ignore: "",
			errorPlacement: function (error, element) {
			    if (element.is(":radio"))
			        error.appendTo(element.parent().parent().parent());
			    else if (element.is(":checkbox"))
			        error.appendTo(element.next());
			    else if (element.next().is("span"))
			        error.appendTo(element.parent().parent());
			    else
			        error.appendTo(element.parent());
			},
			highlight: function (element) { 
				$(element)
                    .closest('.form-group').addClass('has-error');
			},

			unhighlight: function (element) {
				$(element)
                    .closest('.form-group').removeClass('has-error');
			},

			success: function (label) {
				label
                    .closest('.form-group').removeClass('has-error'); 
			},

			submitHandler: function (form) {
				var erroralert = $('.alert-danger', form);
				if (erroralert != null && erroralert != undifined) {
					erroralert.hide();
				}
			}

		});
	}
});

