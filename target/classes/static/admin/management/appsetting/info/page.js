const urlPrefix = $('#hid_val').data('urlprefix');
$(function() {
    $('[data-toggle="tooltip"]').tooltip();

    $('#createOneEmpty').click(function() {
        bootbox.confirm("将会创建一个默认空配置项，确定要继续吗？", function(result) {
            if (result) {
                createEmptyBaseInfo();
            }
        });
    });

    var $btnToogleBaseInfo = $('#btnToogleBaseInfo');
    let btnToogleBaseInfoStatus = $.zui.store.pageGet('btn_base_info_toggole_status');
    var showBaseInfo = function() {
        $btnToogleBaseInfo.html('<i class="icon icon-toggle-on"></i>&nbsp;toggle');
        $('#baseInfoForm').parent().removeClass('hidden');
        $('#valueWrapper').removeClass('col-md-9').addClass('col-md-6');
    };
    var hideBaseInfo = function() {
        $btnToogleBaseInfo.html('<i class="icon icon-toggle-off"></i>&nbsp;toggle');
        $('#baseInfoForm').parent().addClass('hidden');
        $('#valueWrapper').removeClass('col-md-6').addClass('col-md-9');
    };
    if (typeof btnToogleBaseInfoStatus === 'undefined' || btnToogleBaseInfoStatus == 1) {
        btnToogleBaseInfoStatus = 1;
        showBaseInfo();
    } else {
        btnToogleBaseInfoStatus = 0;
        hideBaseInfo();
    }
    $btnToogleBaseInfo.click(function() {
        if (btnToogleBaseInfoStatus == 0) {
            btnToogleBaseInfoStatus = 1;
            showBaseInfo();
        } else {
            btnToogleBaseInfoStatus = 0;
            hideBaseInfo();
        }
        $.zui.store.pageSet('btn_base_info_toggole_status', btnToogleBaseInfoStatus);
    });


    // 变更数据类型
    var $valueType = $('#valueType');
    $valueType.change(function() {
        switch ($valueType.val()) {
            case 'json':
                $('#app_setting_class').removeClass('hidden');
                break;
            case 'int':
            case 'number':
            case 'bool':
            case 'datetime':
            case 'string':
            default:
                $('#app_setting_class').addClass('hidden');
                break;
        }
    });

    $('#baseInfoForm input[name="loadType"]').change(function() {
        var loadType = baseInfoForm.loadType.value;
        switch (loadType) {
            case "0":
                $('#className, #uploaderClassFile, #jsonSchema').addClass('hidden');
                $('#className').removeClass('hidden');
                break;
            case "1":
                $('#className, #uploaderClassFile, #jsonSchema').addClass('hidden');
                $('#className').removeClass('hidden');
                $('#uploaderClassFile').removeClass('hidden');
                break;
            case "2":
                $('#className, #uploaderClassFile, #jsonSchema').addClass('hidden');
                $('#jsonSchema').removeClass('hidden');
                break;
        }
    });

    $('#uploaderClassFile').uploader({
        autoUpload: true,
        url: urlPrefix + '/classinfo/uploadfile',
        filters: {
            mime_types: [{
                    title: 'class',
                    extensions: 'class'
                },
                {
                    title: 'jar',
                    extensions: 'jar'
                }
            ],
            max_file_size: '1mb',
            prevent_duplicates: true
        },
        responseHandler: function(responseObject, file) {
            var res = JSON.parse(responseObject.response);
            if (res && res.code === 0) {
                baseInfoForm.classFileName.value = res.data.name;
                baseInfoForm.tempFilePath.value = res.data.filePath;
                return;
            } else {
                var errMsg = file.name + '上传失败，错误信息：' + res.msg;
                console.log(errMsg);
                return errMsg;
            }
        },
        chunk_size: 0,
        previewImageIcon: false
    });

    $("#baseInfoForm").html5Validate(function() {
        modifyBaseInfo();
    }, {
        validate: function() {
            var pass = true;
            if ('json' == $valueType.val()) {
                var loadType = baseInfoForm.loadType.value;
                switch (loadType) {
                    case "0":
                        pass = $('#className').val().length > 0;
                        if (!pass) {
                            $("#className").testRemind("请输入");
                        }
                        break;
                    case "1":
                        if (baseInfoForm.classFileName.value.length > 0) {
                            pass = $('#className').val().length > 0;
                            if (!pass) {
                                $("#className").testRemind("请输入");
                            }
                        } else {
                            pass = false;
                            $('#uploaderClassFile').testRemind("请上传");
                        }
                        break;
                    case "2":
                        pass = $('#jsonSchema').val().length > 0;
                        if (!pass) {
                            $('#jsonSchema').testRemind("请输入");
                        }
                        break;
                }
            }
            return pass;
        }
    });
    var createEmptyBaseInfo = function() {
        utils.formPost(urlPrefix + '/baseinfo/create', {}, function(res) {
            if (res.code == 0) {
                //new $.zui.Messager('Success', { type: 'success' }).show();
                window.location.href = urlPrefix + '/info?id=' + res.data.appSetting.id;
            } else {
                new $.zui.Messager(res.msg, {
                    type: 'danger'
                }).show();
            }
        });
    };
    var modifyBaseInfo = function() {
        var postData = utils.formEach('#baseInfoForm input, #baseInfoForm select');
        postData.loadType = baseInfoForm.loadType.value;
        postData.jsonSchema = $('#jsonSchema').val();
        utils.formPost(urlPrefix + '/baseinfo/modify', postData, function(res) {
            if (res.code == 0) {
                bootbox.alert("修改成功", function() {
                    window.location.reload();
                });
            } else {
                new $.zui.Messager(res.msg, {
                    type: 'danger'
                }).show();
            }
        });
    };

    flatpickr(".flatpickr-input", {
        "time_24hr": true,
        "dateFormat": "Y-m-d H:i:S",
        enableTime: true,
        enableSeconds: true
    });
    $('#valueInfoForm input[type="checkbox"]').change(function() {
        var $this = $(this);
        $this.val($this.prop("checked"));
    });
    $("#valueInfoForm").html5Validate(function() {
        saveSimpleValue();
    }, {
        validate: function() {
            var pass = true;
            return pass;
        }
    });
    var saveSimpleValue = function() {
        var postData = utils.formEach('#valueInfoForm input, #valueInfoForm textarea');
        postData['id'] = baseInfoForm.id.value;
        utils.jsonPost(urlPrefix + '/valueinfo/modify', postData, function(res) {
            if (res && res.code === 0) {
                bootbox.alert("修改成功", function() {
                    window.location.reload();
                });
            } else {
                new $.zui.Messager(res.msg, {
                    type: 'danger'
                }).show();
            }
        });
    };

    // json
    if (typeof entitySchema !== 'undefined') {
        JSONEditor.defaults.options.theme = 'bootstrap3';
        JSONEditor.defaults.options.iconlib = "fontawesome4";

        var recursiveProperty = function(root) {
            if (root.properties) {
                for (var k in root.properties) {
                    var v = root.properties[k];
                    if (typeof v.options === 'undefined') {
                        v.options = {};
                    }
                    if (typeof v.options.inputAttributes === 'undefined') {
                        v.options.inputAttributes = {
                            placeholder: '输入 ' + v.title || ''
                        };
                    }
                    var format = v.format;
                    if (format) {
                        if (format.indexOf('date') > -1 || format.indexOf('time') > -1) {
                            v.options.flatpickr = {
                                "inlineHideInput": true,
                                "wrap": true,
                                "allowInput": false,
                                "time_24hr": true,
                                "dateFormat": "Y-m-d H:i:S"
                            };
                        } else if (format === 'textarea') {
                            v.options.inputAttributes.rows = 16;
                        } else {

                        }
                    }
                    if (v.items) {
                        recursiveProperty(v.items);
                    }
                }
            }
            if (root.required && root.required.length > 0) {
                for (var i = 0, len = root.required.length; i < len; i++) {
                    var prop = root.required[i];
                    if (typeof root.properties[prop] === 'undefined') {
                        // root.properties[prop] = {};
                    } else {
                        // if (typeof root.properties[prop].options === 'undefined') {
                        // 	root.properties[prop].options = {};
                        // }
                        // if (typeof root.properties[prop].options.inputAttributes === 'undefined') {
                        // 	root.properties[prop].options.inputAttributes = {};
                        // }
                        root.properties[prop].options.inputAttributes.required = 'required';
                    }
                }
            }
        };
        recursiveProperty(entitySchema);

        var options = {
            schema: entitySchema,
            disable_collapse: true
        };
        var jsonEditor = new JSONEditor(document.getElementById('editor_holder'), options);
        if (entityValue) {
            jsonEditor.setValue(entityValue);
            try {
                $('#appSettingValue').val(JSON.stringify(entityValue, null, 4));
            } catch (err) {
                console.error(err);
            }
        }
        $("#jsonEditorForm").html5Validate(function() {
            saveJsonEditor();
        }, {
            validate: function() {
                var pass = true;
                return pass;
            }
        });

        var $btnSaveJsonEditor = $('#btnSaveJsonEditor');
        var saveJsonEditor = function() {
            var jsonInfo = JSON.stringify(jsonEditor.getValue());
            $btnSaveJsonEditor.attr('disabled', 'disabled');
            var postData = {
                id: baseInfoForm.id.value,
                value: jsonInfo
            };
            utils.jsonPost(urlPrefix + '/valueinfo/modify', postData, function(res) {
                if (res && res.code === 0) {
                    bootbox.alert("修改成功", function() {
                        window.location.reload();
                    });
                } else {
                    new $.zui.Messager(res.msg, {
                        type: 'danger'
                    }).show();
                }
                $btnSaveJsonEditor.removeAttr('disabled');
            });
        };


        $("#jsonValueInfoForm").html5Validate(function() {
            saveTextValue();
        }, {
            validate: function() {
                var pass = true;
                return pass;
            }
        });
        var $btnSaveTextValue = $('#btnSaveTextValue');
        var saveTextValue = function() {
            var jsonInfo = $('#appSettingValue').val().replace(/\r\n/g, '');
            try {
                JSON.parse(jsonInfo);
                $btnSaveTextValue.attr('disabled', 'disabled');
                var postData = {
                    id: baseInfoForm.id.value,
                    value: jsonInfo
                };
                utils.jsonPost(urlPrefix + '/valueinfo/modify', postData, function(res) {
                    if (res && res.code === 0) {
                        bootbox.alert("修改成功", function() {
                            window.location.reload();
                        });
                    } else {
                        new $.zui.Messager(res.msg, {
                            type: 'danger'
                        }).show();
                    }
                    $btnSaveTextValue.removeAttr('disabled');
                });
            } catch (err) {
                new $.zui.Messager('格式错误，请先检查格式', {
                    type: 'danger'
                }).show();
            }
        };
    }

});