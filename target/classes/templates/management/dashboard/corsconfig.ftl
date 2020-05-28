<#include "../header.ftl">
<#include "../../html5shiv.ftl">
<#include "../common_styles.ftl">
</head>
<body>
    <#include "../nav.ftl">

<div class="container-fluid" id="app" v-cloak>
    <div class="row">
        <div class="col-md-3">
            <button class="btn btn-primary" type="button" v-on:click="createRule"><i class="icon icon-plus"></i> Create Rule</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered table-condensed table-hover table-fixed">
                <caption>${viewData.title}</caption>
                <thead>
                    <tr>
                        <th>Url Path</th>
                        <th>Allowd Origins</th>
                        <th class="w120">Allowd Methods</th>
                        <th class="w120">Allow Credentials</th>
                        <th class="w200">Allowd Headers</th>
                        <th class="w200">Exposed Headers</th>
                        <th class="w120">Max-Age</th>
                        <th class="w50">#</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="rule in corsRules">
                        <td><code><span class="atv">{{rule.urlPath}}</span></code></td>
                        <td><template v-for="el in rule.allowedOrigins"><code><span class="atv">{{el}}</span></code><br/></template></td>
                        <td class="w120"><template v-for="el in rule.allowedMethods"><span class="atv">{{el}}</span><br/></template></td>
                        <td class="w120"><span v-bind:class="'label' + (rule.allowCredentials?'':' label-warning')">{{rule.allowCredentials||'null'}}</span></td>
                        <td class="w200"><template v-for="el in rule.allowedHeaders"><code><span class="atv">{{el}}</span></code><br/></template></td>
                        <td class="w200"><template v-for="el in rule.exposedHeaders"><code><span class="atv">{{el}}</span></code><br/></template></td>
                        <td class="w120"><span v-bind:class="'label' + (rule.allowCredentials?'':' label-warning')">{{rule.maxAge||'null'}}</span></td>
                        <td class="w50"><a href="javascript:;" v-on:click="editRule(rule)">Edit</a></td>
                    </tr>
                </tbody>
                <tfoot></tfoot>
           </table>
        </div>
    </div>

    <div class="modal" id="ruleModal">
      <div class="modal-dialog modal-lg">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
          <h4 class="modal-title">Cors Rule</h4>
        </div>
        <div class="modal-content">
            <form class="form-horizontal">
              <div class="form-group">
                <label for="urlPath" class="col-sm-2">Url Path</label>
                <div class="col-md-6 col-sm-10">
                  <input type="text" class="form-control" id="urlPath" v-model="selectedRule.urlPath" required>
                </div>
              </div>
              <div class="form-group">
                <label for="allowdOrigins" class="col-sm-2">Allowd Origins</label>
                <div class="col-md-6 col-sm-10">
                    <textarea class="form-control" id="allowdOrigins" rows="3" placeholder="来源可以设置多个，每行一个，每行最多能有一个通配符 *" v-model="selectedRule.allowedOriginsText" required></textarea>
                </div>
              </div>
              <div class="form-group">
                <label for="allowdMethods" class="col-sm-2">Allowd Methods</label>
                <div class="col-md-10 col-sm-10">
                    <div class="col-md-2 checkbox-primary" v-for="method in methods">
                        <input type="checkbox" v-bind:id="'allowMethod_'+method" v-bind:value="method" v-model="selectedRule.allowedMethods"><label v-bind:for="'allowMethod_'+method">{{method}}</label>
                    </div>
                </div>
              </div>
             <div class="form-group">
                <label for="allowdCredentials" class="col-sm-2">Allowd Credentials</label>
                <div class="col-md-6 col-sm-10">
                    <div class="switch switch-inline">
                        <input type="checkbox" id="allowdCredentials" v-model="selectedRule.allowCredentials"><label>是否允许发送Cookie</label>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <label for="allowdHeaders" class="col-sm-2">Allowd Headers</label>
                <div class="col-md-6 col-sm-10">
                    <textarea class="form-control" id="allowdHeaders" rows="4" placeholder="允许 Headers 可以设置多个，每行一个，每行最多能有一个通配符 *" v-model="selectedRule.allowedHeadersText"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label for="exposedHeaders" class="col-sm-2">Exposed Headers</label>
                <div class="col-md-6 col-sm-10">
                    <textarea class="form-control" id="exposedHeaders" rows="3" placeholder="暴露 Headers 可以设置多个，每行一个，不允许出现通配符*" v-model="selectedRule.exposedHeadersText"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label for="maxAge" class="col-sm-2">Max-Age</label>
                <div class="col-md-3 col-sm-10">
                  <input type="number" class="form-control" id="maxAge" min="0" max="987654321" step="1" placeholder="缓存时间（秒）" v-model="selectedRule.maxAge" required>
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-primary" disabled="disabled">Submit</button>
                </div>
              </div>
            </form>
        </div>
      </div>
    </div>
</div>

<input type="hidden" id="hid_val" data-dashboardpathprefix="${viewData.page_global_dashboardPathPrefix}" value="" />
<#include "../common_scripts.ftl">
<#include "../footer.ftl">