<#include "../header.ftl">
<#include "../../html5shiv.ftl">
<#include "../common_styles.ftl">
</head>
<body>
    <#include "../nav.ftl">

<div class="container-fluid" id="app" v-cloak>
    <div class="row">
        <div class="col-md-3">
            <button class="btn btn-primary" type="button" v-bind:disabled="!triggerBtnEnabled" v-on:click="triggerTasks"><i class="icon icon-play-circle"></i> Trigger now</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-bordered table-condensed table-hover table-fixed">
                <caption>CronTasks</caption>
                <thead>
                    <tr>
                        <th>Job</th>
                        <th class="w200">Cron</th>
                        <th class="w150">Next execution</th>
                        <th class="w180">Last scheduled execution</th>
                        <th class="w150">Last actual execution</th>
                        <th class="w150">Last completion</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="task in cronTasks">
                        <td>
                            <div class="checkbox-primary">
                                <input type="checkbox" v-bind:id="task.id" v-bind:value="task.id" v-model="cronTasksCheckedList" />
                                <label v-bind:for="task.id"><span class="text-ellipsis" v-bind:title="task.id">{{task.id}}</span></label>
                            </div>
                        </td>
                        <td class="w200"><code><span class="atv">{{task.expression}}</span></code></td>
                        <td class="w150">{{task.nextExecutionTime | timeStamp('yyyy-MM-dd hh:mm:ss')}}</td>
                        <td class="w180">{{task.lastScheduledExecutionTime | timeStamp('yyyy-MM-dd hh:mm:ss')}}</td>
                        <td class="w150">{{task.lastActualExecutionTime | timeStamp('yyyy-MM-dd hh:mm:ss')}}</td>
                        <td class="w150">{{task.lastCompletionTime | timeStamp('yyyy-MM-dd hh:mm:ss')}}</td>
                    </tr>
                </tbody>
                <tfoot></tfoot>
           </table>
        </div>
        <div class="col-md-12">
            <table class="table table-bordered table-condensed table-hover table-fixed">
                <caption>FixedDelayTasks</caption>
                <thead>
                    <tr>
                        <th>Job</th>
                        <th class="w150">Initial delay</th>
                        <th class="w150">Interval</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="task in fixedDelayTasks">
                        <td>
                            <div class="checkbox-primary">
                                <input type="checkbox" v-bind:id="task.id" v-bind:value="task.id" v-model="fixedDelayTasksCheckedList" />
                                <label v-bind:for="task.id"><span class="text-ellipsis" v-bind:title="task.id">{{task.id}}</span></label>
                            </div>
                        </td>
                        <td class="w150"><code><span class="atv">{{task.initialDelay}} ms</span></code></td>
                        <td class="w150"><code><span class="atv">{{task.interval}} ms</span></code></td>
                    </tr>
                </tbody>
                <tfoot></tfoot>
           </table>
        </div>
        <div class="col-md-12">
            <table class="table table-bordered table-condensed table-hover table-fixed">
                <caption>FixedRateTasks</caption>
                <thead>
                    <tr>
                        <th>Job</th>
                        <th class="w150">Initial delay</th>
                        <th class="w150">Interval</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="task in fixedRateTasks">
                        <td>
                            <div class="checkbox-primary">
                                <input type="checkbox" v-bind:id="task.id" v-bind:value="task.id" v-model="fixedRateTasksCheckedList" />
                                <label v-bind:for="task.id"><span class="text-ellipsis" v-bind:title="task.id">{{task.id}}</span></label>
                            </div>
                        </td>
                        <td class="w150"><code><span class="atv">{{task.initialDelay}} ms</span></code></td>
                        <td class="w150"><code><span class="atv">{{task.interval}} ms</span></code></td>
                    </tr>
                </tbody>
                <tfoot></tfoot>
           </table>
        </div>
    </div>
</div>

<input type="hidden" id="hid_val" data-dashboardpathprefix="${viewData.page_global_dashboardPathPrefix}" value="" />
<#include "../common_scripts.ftl">
<#include "../footer.ftl">