<#include "../header.ftl">
<link href="https://cdn.bootcss.com/zui/1.9.1/lib/bootbox/bootbox.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/zui/1.9.1/lib/uploader/zui.uploader.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/flatpickr/4.5.2/flatpickr.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<#include "../../html5shiv.ftl">
<#include "../common_styles.ftl">
</head>
<body>

<div class="container-fluid mt10">
    <div class="row">
      <div class="col-md-3">
        <div class="pb6">
            <input type="button" class="btn btn-default" id="createOneEmpty" value="Add" data-toggle="tooltip" data-placement="right" title="创建默认空配置项" />
            <button class="btn btn-default pull-right" id="btnToogleBaseInfo" data-toggle="tooltip" data-placement="right" title="切换隐藏或显示" />
                <i class="icon icon-toggle-on"></i>&nbsp;toggle
            </button>
        </div>
        <div class="list-group">
          <#list viewData.appSettings?keys as key>
            <#list viewData.appSettings[key] as item>
                <a href="${rc.requestUri}?id=${item.id}" class="list-group-item <#if item.id == RequestParameters['id']>active</#if>" style="padding: 5px 10px 0px 10px;">
                  <h4 class="list-group-item-heading mb0">${item.mark}</h4>
                  <small class="list-group-item-text text-muted"><#if item.key?? && item.key?length gt 0>${item.key}<#else>undefined</#if> <#if item.env?? && item.env?length gt 0>(${item.env})</#if></small>
                </a>
            </#list>
          </#list>
        </div>
      </div>
      <div class="col-md-3">
        <form id="baseInfoForm" action="${viewData.urlPrefix}/baseInfo/modify" method="post">
          <input type="hidden" name="id" value="${viewData.appSetting.id}"/>
          <div class="form-group">
            <label for="env">环境</label>
            <select class="form-control" id="env">
                <option value=""     <#if viewData.appSetting.env=="">selected="selected"</#if>    >default</option>
                <option value="dev"  <#if viewData.appSetting.env=="dev">selected="selected"</#if> >dev</option>
                <option value="test" <#if viewData.appSetting.env=="test">selected="selected"</#if>>test</option>
                <option value="prod" <#if viewData.appSetting.env=="prod">selected="selected"</#if>>prod</option>
            </select>
          </div>
          <div class="form-group">
            <label for="key">Key</label>
            <input type="text" class="form-control" id="key" value="${viewData.appSetting.key!''}" placeholder="请填写key" required <#if viewData.appSetting.key?? && viewData.appSetting.key!=''>readonly="readonly"</#if> />
          </div>
          <div class="form-group">
            <label for="valueType">数据类型</label>
            <select class="form-control" id="valueType" required>
                <option value="string"   <#if (viewData.appSetting.valueType=="string" || viewData.appSetting.valueType=="")>selected="selected"</#if> >字符串</option>
                <option value="int"      <#if viewData.appSetting.valueType=="int">selected="selected"</#if>>整数</option>
                <option value="number"   <#if viewData.appSetting.valueType=="number">selected="selected"</#if> >数值</option>
                <option value="bool"     <#if viewData.appSetting.valueType=="bool">selected="selected"</#if>>布尔值</option>
                <option value="datetime" <#if viewData.appSetting.valueType=="datetime">selected="selected"</#if>>日期</option>
                <option value="json"     <#if viewData.appSetting.valueType=="json">selected="selected"</#if>>自定义模型</option>
            </select>
          </div>
          <div id="app_setting_class" class="form-group <#if viewData.appSetting.valueType == 'json'><#else>hidden</#if>">
            <label>模型加载方式</label>
            <input type="hidden" name="classId" value="${viewData.appSetting.classId}"/>
            <div class="clearfix"></div>
            <div class="radio-primary col-md-4"><input type="radio" name="loadType" id="LOCAL" value="0" <#if viewData.appSettingClass.loadType == 0>checked="checked"</#if>><label for="LOCAL">内部加载</label></div>
            <div class="radio-primary col-md-4"><input type="radio" name="loadType" id="REMOTE" value="1" <#if viewData.appSettingClass.loadType == 1>checked="checked"</#if>><label for="REMOTE">本地上传</label></div>
            <div class="radio-primary col-md-4"><input type="radio" name="loadType" id="JSON_SCHEMA" value="2" <#if viewData.appSettingClass.loadType == 2>checked="checked"</#if>><label for="JSON_SCHEMA">代码模式</label></div>
            <div class="clearfix"></div>
            <input type="text" class="form-control <#if viewData.appSettingClass.loadType gt 1>hidden</#if>" id="className" value="${viewData.appSettingClass.className!''}" placeholder="请填写加载类名: com.enterprise.xxx.Sample" />

            <div id="uploaderClassFile" class="uploader mt4 <#if viewData.appSettingClass.loadType != 1>hidden</#if>">
              <div class="file-list" data-drag-placeholder="请拖拽字节码文件(.class)到此处"></div>
              <button type="button" class="btn btn-primary uploader-btn-browse"><i class="icon icon-cloud-upload"></i> 选择文件</button>
            </div>
            <input type="hidden" name="contentMd5" value="${viewData.appSettingClass.contentMd5!''}" />
            <input type="hidden" name="classFileName" value="${viewData.appSettingClass.classFileName!''}" />
            <input type="hidden" name="tempFilePath" value="" />

            <textarea class="form-control <#if viewData.appSettingClass.loadType != 2>hidden</#if>" id="jsonSchema" rows="30">${viewData.jsonSchema!''}</textarea>
          </div>
          <div class="form-group">
            <label for="mark">备注</label>
            <input type="text" class="form-control" id="mark" value="${viewData.appSetting.mark}" placeholder="备注" required />
          </div>
          <button type="submit" class="btn btn-primary" disabled>Save</button>
        </form>
      </div>
      <div class="col-md-6" id="valueWrapper">
        <#if viewData.appSetting.key?? && viewData.appSetting.key?length gt 0>
          <#if viewData.appSetting.valueType == 'json'>
            <ul class="nav nav-tabs">
                <li class="active"><a data-tab href="#tabContent0">界面模式</a></li>
                <li class=""><a data-tab href="#tabContent1">原始文本</a></li>
            </ul>
            <div class="tab-content with-padding">
                <div class="tab-pane active" id="tabContent0">
                  <form id="jsonEditorForm" action="${viewData.urlPrefix}/baseInfo/modify" method="post">
                    <div class="col-md-12">
                        <div id="editor_holder"></div>
                        <div class="col-sm-3 with-padding">
                            <input type="submit" class="btn btn-primary" id="btnSaveJsonEditor" value="Save" disabled />
                        </div>
                    </div>
                  </form>
                </div>
                <div class="tab-pane" id="tabContent1">
                    <div class="form-horizontal col-md-12">
                      <form id="jsonValueInfoForm" action="${viewData.urlPrefix}/baseInfo/modify" method="post">
                        <div class="form-group">
                            <label for="appSettingValue" class="col-sm-2">原始数据</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="appSettingValue" rows="20" required>${viewData.appSetting.value!''}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type="submit" class="btn btn-primary" id="btnSaveTextValue" value="Save Text" disabled />
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
            </div>
          <#else>
            <form id="valueInfoForm" action="${viewData.urlPrefix}/baseInfo/modify" method="post">
              <div class="form-group">
                <label for="value">值</label>
                <#if (viewData.appSetting.valueType=="string" || viewData.appSetting.valueType=="")>
                    <textarea class="form-control" id="value" placeholder="请输入 值" rows="16" required>${viewData.appSetting.value}</textarea>
                </#if>
                <#if viewData.appSetting.valueType=="int" >
                    <input type="number" class="form-control" id="value" value="${viewData.appSetting.value}" step="1" min="-214748364" max="214748364" placeholder="请输入整数" required />
                </#if>
                <#if viewData.appSetting.valueType=="number" >
                    <input type="number" class="form-control" id="value" value="${viewData.appSetting.value}" step="0.0001" placeholder="请输入数值" required />
                </#if>
                <#if viewData.appSetting.valueType=="bool" >
                    <div class="clearfix"></div>
                    <div class="switch switch-inline"><input type="checkbox" id="value" <#if viewData.appSetting.value=="true">checked="checked" value="true"<#else>value="false"</#if> ><label>开关</label></div>
                </#if>
                <#if viewData.appSetting.valueType=="datetime" >
                    <input type="text" class="form-control flatpickr-input" id="value" value="${viewData.appSetting.value}" placeholder="请输入 时间" data-schemaformat="datetime-local" readonly="readonly" />
                </#if>
              </div>
              <button type="submit" class="btn btn-primary" disabled>Save</button>
            </form>
          </#if>
        </#if>
      </div>
    </div>
</div>

<input type="hidden" id="hid_val" value="" data-urlprefix="${viewData.urlPrefix}" />

<#include "../common_scripts.ftl">
<script src="https://cdn.bootcss.com/zui/1.9.1/lib/bootbox/bootbox.min.js"></script>
<script src="https://cdn.bootcss.com/zui/1.9.1/lib/uploader/zui.uploader.min.js"></script>
<script src="https://cdn.bootcss.com/flatpickr/4.5.2/flatpickr.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@json-editor/json-editor@1.3.5/dist/jsoneditor.min.js"></script>
<script src="${viewData.page_global_serverContextPath}/json-editor/language_zh_CN.js"></script>
<#if viewData.appSetting.key?? && viewData.appSetting.key?length gt 0>
  <#if viewData.appSetting.valueType == 'json'>
    <script>
    var entityValue = null;
    try{
      entityValue = <#if viewData.appSetting.value ?? && viewData.appSetting.value?trim?length gt 0>${viewData.appSetting.value}<#else>{}</#if>;
    } catch(err) {
      console.error(err);
      new $.zui.Messager('初始化异常，请修改原始文本', { type: 'danger' }).show();
    }
    var entitySchema = ${viewData.jsonSchema!'{}'};
    </script>
  <#else>

  </#if>
</#if>
<#include "../footer.ftl">