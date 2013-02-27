var settings = {
	defaultPageSize : 5,
	validationParams : {
		rules : {
			username : {
				minlength : 2,
				required : true
			},
			name : {
				required : false
			},
			surname : {
				required : false
			},
			telephoneNumber : {
				required : true
			}
		},
		highlight : function(element) {
			$(element).closest('.control-group').removeClass('success')
					.addClass('error');
		},
		success : function(element) {
			element.addClass('valid').closest('.control-group').removeClass(
					'error').addClass('success');
		}
	}
};

var main = {
	init : function() {
		main.listView.init();
		main.modalAdd.init();
		main.modalEdit.init();
		main.modalDelete.init();
	}
};

main.listView = {
	dom : {},
	init : function() {
		var upper = this;
		this.dom.core = $("#account-template-area");
		this.dom.nextButton = $("#next-button");
		this.dom.prevButton = $("#previous-button");
		this.dom.currentPageButton = $("#active-page-field");
		this.dom.editRowButton = $(".modal-edit");
		this.dom.deleteRowButton = $(".modal-edit");

		this.dom.prevButton.addClass("disabled");
		this.dom.nextButton.addClass("disabled");

		this.dom.nextButton.click(function() {

			if (upper.dom.nextButton.hasClass("disabled")) {
				return;
			}
			upper.activePage++;
			upper.fill(upper.activePage);

		});

		this.dom.currentPageButton.click(function() {
			upper.fill(upper.activePage);
		});

		this.dom.prevButton.click(function() {

			if (upper.dom.prevButton.hasClass("disabled")) {
				return;
			}
			upper.activePage--;
			upper.fill(upper.activePage);

		});

		// initialize fist page
		upper.fill(upper.activePage);
	},
	activePage : 0,
	numberOfRows : 0,
	fill : function(pageNum) {
		var upper = this;
		$.getJSON("ajax/listAllPagified", {
			pageNum : pageNum
		}).success(
				function(data) {

					// fill table
					var template = $('#account-table-body-template').html();
					var html = Mustache.to_html(template, data);
					$('#account-table-body').html(html);

					// set count
					var template = $('#page-num-template').html();
					upper.numberOfRows = Mustache.to_html(template, data);
					
					//lower activePage if required
					if (Math.ceil(upper.numberOfRows / settings.defaultPageSize) < upper.activePage) {
						upper.activePage--;
					}
					$('#active-page-field').html(
							(upper.activePage + 1)
									+ " of "
									+ Math.ceil(upper.numberOfRows
											/ settings.defaultPageSize));

					// reinitialize rows
					upper.dom.editRowButton = $(".modal-edit");
					upper.dom.deleteRowButton = $(".modal-delete");

					// reset click bindings
					upper.dom.editRowButton
							.click(main.modalEdit.modalEditButtonBinding);
					upper.dom.deleteRowButton
							.click(main.modalDelete.modalDeleteButtonBinding);
					
					upper.resetButtonStates();

				});
	},
	resetButtonStates : function() {
		var upper = this;

		upper.dom.nextButton.removeClass("disabled");
		if (upper.numberOfRows == 0 || Math.ceil(upper.numberOfRows / settings.defaultPageSize) == upper.activePage + 1 ) {
			upper.dom.nextButton.addClass("disabled");
		}

		upper.dom.prevButton.removeClass("disabled");
		if (upper.activePage == 0) {
			upper.dom.prevButton.addClass("disabled");
		}
	}
};

main.modalAdd = {
	data : {
		username : null,
		name : null,
		surname : null,
		telehponeNumber : null,
		captchaText : null,
		validationParams : {}
	},
	dom : {},
	init : function() {
		var upper = this;
		this.dom.core = $("#modal-add");
		this.dom.form = $("#add-account-form");
		this.dom.addButton = $("#add-button");
		this.dom.usernameInput = $("#add-inputUsername");
		this.dom.nameInput = $("#add-inputName");
		this.dom.surnameInput = $("#add-inputSurname");
		this.dom.telNumInput = $("#add-inputTelephoneNumber");
		this.dom.captchaText = $("#add-inputCaptcha");
		this.dom.captchaImage = $("#add-captcha-image");
		this.dom.captchaRefresh = $("#captcha-refresh");
		this.dom.successAlert = $('#add-success-alert');
		this.dom.failAlert = $('#add-fail-alert');

		this.dom.core.on("hidden", function() {
			// reset modal popUp
			upper.dom.failAlert.hide();
			upper.dom.successAlert.hide();
			upper.dom.addButton.button('default');
			upper.reloadCaptchaImage();
		});
		
		this.dom.captchaRefresh.click(this.reloadCaptchaImage);

		this.validationParams = settings.validationParams;
		this.validationParams.submitHandler = function() {
			upper.add();
		};

		// validate
		this.dom.form.validate(this.validationParams);
	},
	add : function() {
		var upper = this;
		this.dom.addButton.button('loading');

		// set data fields
		this.data.username = this.dom.usernameInput.val();
		this.data.name = this.dom.nameInput.val();
		this.data.surname = this.dom.surnameInput.val();
		this.data.telehponeNumber = this.dom.telNumInput.val();
		this.data.captchaText = this.dom.captchaText.val();

		$.post("ajax/insert", {
			username : upper.data.username,
			name : upper.data.name,
			surname : upper.data.surname,
			telephoneNumber : upper.data.telehponeNumber,
			verificationText : upper.data.captchaText
		}).success(function() {
			upper.dom.failAlert.fadeOut('slow');
			upper.dom.successAlert.fadeIn('slow');
			upper.dom.form.find("input").val("");
			upper.dom.form.validate().resetForm();
			upper.dom.addButton.button('complete');

			// reset list
			main.listView.fill(main.listView.activePage);

		}).error(function(data) {
			upper.dom.failAlert.text(data.statusText).fadeIn('slow');
			upper.reloadCaptchaImage();
			upper.dom.addButton.button('retry');
		});
	},
	reloadCaptchaImage : function() {
		var upper = this;
		main.modalAdd.dom.captchaImage.attr("src", "/spring-mvc-demo/captcha/getImage");
	},
};

main.modalEdit = {
	data : {
		username : 2,
		name : null,
		surname : null,
		telehponeNumber : null,
		validationParams : {}
	},
	dom : {},
	init : function() {
		var upper = this;
		this.dom.core = $("#modal-edit");
		this.dom.form = $("#edit-account-form");
		this.dom.editButton = $("#edit-button");
		this.dom.usernameInput = $("#edit-inputUsername");
		this.dom.nameInput = $("#edit-inputName");
		this.dom.surnameInput = $("#edit-inputSurname");
		this.dom.telNumInput = $("#edit-inputTelephoneNumber");
		this.dom.successAlert = $('#edit-success-alert');
		this.dom.failAlert = $('#edit-fail-alert');

		this.dom.core.on("hidden", function() {
			// reset modal popUp
			upper.dom.failAlert.hide();
			upper.dom.successAlert.hide();
			upper.dom.editButton.button('default');
		});

		// validate
		this.validationParams = settings.validationParams;
		this.validationParams.submitHandler = function() {
			upper.edit();
		};
		
		// validate
		this.dom.form.validate(this.validationParams);

		// get selected user
		$(".modal-edit-button").click(function() {
			upper.data.username = $(this).data('username');
		});
	},
	modalEditButtonBinding : function() {

		// get name data
		main.modalEdit.data.username = $(this).data('username');

		// fill edit modal
		$.get("ajax/findByUsername", {
			username : main.modalEdit.data.username
		}).success(function(data) {
			main.modalEdit.dom.usernameInput.val(data.account.username);
			main.modalEdit.dom.nameInput.val(data.account.name);
			main.modalEdit.dom.surnameInput.val(data.account.surname);
			main.modalEdit.dom.telNumInput.val(data.account.phoneNumber);
		}).error(function(data) {
			main.modalEdit.dom.failAlert.text(data.statusText).fadeIn('slow');
			main.modalEdit.dom.editButton.button('retry');
		});

	},
	edit : function() {
		var upper = this;
		this.dom.editButton.button('loading');

		// set data fields
		this.data.username = this.dom.usernameInput.val();
		this.data.name = this.dom.nameInput.val();
		this.data.surname = this.dom.surnameInput.val();
		this.data.telehponeNumber = this.dom.telNumInput.val();

		$.post("ajax/update", {
			username : upper.data.username,
			name : upper.data.name,
			surname : upper.data.surname,
			telephoneNumber : upper.data.telehponeNumber
		}).success(function() {
			upper.dom.failAlert.fadeOut('slow');
			upper.dom.successAlert.fadeIn('slow');
			upper.dom.form.find("input").val("");
			upper.dom.form.validate().resetForm();
			upper.dom.editButton.button('complete');

			// reset list
			main.listView.fill(main.listView.activePage);
		}).error(function(data) {
			upper.dom.failAlert.text(data.statusText).fadeIn('slow');
			upper.dom.editButton.button('retry');
		});

	}
};

main.modalDelete = {
	data : {
		username : null
	},
	dom : {},
	init : function() {
		var upper = this;
		this.dom.core = $("#modal-delete");
		this.dom.deleteButton = $("#delete-button");
		this.dom.infoAlert = $("#delete-info-alert");
		this.dom.successAlert = $('#delete-success-alert');
		this.dom.failAlert = $('#delete-fail-alert');
		this.dom.deleteButton.click(function() {
			upper.del(upper.data);
		});
		this.dom.core.on("hidden", function() {
			// reset modal popUp
			upper.dom.failAlert.hide();
			upper.dom.successAlert.hide();
			upper.dom.infoAlert.show();
			upper.dom.deleteButton.button('default');
			upper.dom.deleteButton.show();
		});

	},
	modalDeleteButtonBinding : function() {
		// get name data
		main.modalDelete.data.username = $(this).data('username');
	},
	del : function(data) {
		var upper = this;
		upper.dom.deleteButton.button('loading');
		upper.dom.infoAlert.hide();
		$.post("ajax/delete", {
			username : data.username
		}).success(function() {
			upper.dom.failAlert.fadeOut('slow');
			upper.dom.successAlert.fadeIn('slow');
			upper.dom.deleteButton.hide();
			// reset list
			main.listView.fill(main.listView.activePage);
		}).error(function(data) {
			upper.dom.failAlert.text(data.statusText).fadeIn('slow');
			upper.dom.deleteButton.button('retry');
		});
	}
};

$(function() {
	main.init();
});
